/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.helpers.FileUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.io.CacheSizeExceededException;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.io.CipherPair;
import org.apache.cxf.io.Transferable;

public class CachedOutputStream
extends OutputStream {
    private static final File DEFAULT_TEMP_DIR;
    private static int defaultThreshold;
    private static long defaultMaxSize;
    private static String defaultCipherTransformation;
    private static boolean thresholdSysPropSet;
    protected boolean outputLocked;
    protected OutputStream currentStream;
    private long threshold = defaultThreshold;
    private long maxSize = defaultMaxSize;
    private File outputDir = DEFAULT_TEMP_DIR;
    private String cipherTransformation = defaultCipherTransformation;
    private long totalLength;
    private boolean inmem;
    private boolean tempFileFailed;
    private File tempFile;
    private boolean allowDeleteOfFile = true;
    private CipherPair ciphers;
    private List<CachedOutputStreamCallback> callbacks;
    private List<Object> streamList = new ArrayList<Object>();

    public CachedOutputStream() {
        this(defaultThreshold);
    }

    public CachedOutputStream(long threshold) {
        this.threshold = threshold;
        this.currentStream = new LoadingByteArrayOutputStream(2048);
        this.inmem = true;
        this.readBusProperties();
    }

    private void readBusProperties() {
        Bus b = BusFactory.getThreadDefaultBus(false);
        if (b != null) {
            File f;
            String v = CachedOutputStream.getBusProperty(b, "bus.io.CachedOutputStream.Threshold", null);
            if (v != null && this.threshold == (long)defaultThreshold) {
                this.threshold = Integer.parseInt(v);
            }
            if ((v = CachedOutputStream.getBusProperty(b, "bus.io.CachedOutputStream.MaxSize", null)) != null) {
                this.maxSize = Integer.parseInt(v);
            }
            if ((v = CachedOutputStream.getBusProperty(b, "bus.io.CachedOutputStream.CipherTransformation", null)) != null) {
                this.cipherTransformation = v;
            }
            if ((v = CachedOutputStream.getBusProperty(b, "bus.io.CachedOutputStream.OutputDirectory", null)) != null && (f = new File(v)).exists() && f.isDirectory()) {
                this.outputDir = f;
            }
        }
    }

    private static String getBusProperty(Bus b, String key, String dflt) {
        String v = (String)b.getProperty(key);
        return v != null ? v : dflt;
    }

    public void holdTempFile() {
        this.allowDeleteOfFile = false;
    }

    public void releaseTempFileHold() {
        this.allowDeleteOfFile = true;
    }

    public void registerCallback(CachedOutputStreamCallback cb) {
        if (null == this.callbacks) {
            this.callbacks = new ArrayList<CachedOutputStreamCallback>();
        }
        this.callbacks.add(cb);
    }

    public void deregisterCallback(CachedOutputStreamCallback cb) {
        if (null != this.callbacks) {
            this.callbacks.remove(cb);
        }
    }

    public List<CachedOutputStreamCallback> getCallbacks() {
        return this.callbacks == null ? null : Collections.unmodifiableList(this.callbacks);
    }

    protected void doFlush() throws IOException {
    }

    @Override
    public void flush() throws IOException {
        this.currentStream.flush();
        if (null != this.callbacks) {
            for (CachedOutputStreamCallback cb : this.callbacks) {
                cb.onFlush(this);
            }
        }
        this.doFlush();
    }

    protected void doClose() throws IOException {
    }

    protected void postClose() throws IOException {
    }

    public void lockOutputStream() throws IOException {
        if (this.outputLocked) {
            return;
        }
        this.currentStream.flush();
        this.outputLocked = true;
        if (null != this.callbacks) {
            for (CachedOutputStreamCallback cb : this.callbacks) {
                cb.onClose(this);
            }
        }
        this.doClose();
        this.streamList.remove(this.currentStream);
    }

    @Override
    public void close() throws IOException {
        this.currentStream.flush();
        this.outputLocked = true;
        if (null != this.callbacks) {
            for (CachedOutputStreamCallback cb : this.callbacks) {
                cb.onClose(this);
            }
        }
        this.doClose();
        this.currentStream.close();
        if (this.ciphers != null) {
            this.ciphers.clean();
        }
        if (!this.maybeDeleteTempFile(this.currentStream)) {
            this.postClose();
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CachedOutputStream) {
            return this.currentStream.equals(((CachedOutputStream)obj).currentStream);
        }
        return this.currentStream.equals(obj);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void resetOut(OutputStream out, boolean copyOldContent) throws IOException {
        if (out == null) {
            out = new LoadingByteArrayOutputStream();
        }
        if (this.currentStream instanceof CachedOutputStream) {
            CachedOutputStream ac = (CachedOutputStream)this.currentStream;
            InputStream in = ac.getInputStream();
            IOUtils.copyAndCloseInput(in, out);
        } else if (this.inmem) {
            if (!(this.currentStream instanceof ByteArrayOutputStream)) throw new IOException("Unknown format of currentStream");
            ByteArrayOutputStream byteOut = (ByteArrayOutputStream)this.currentStream;
            if (copyOldContent && byteOut.size() > 0) {
                byteOut.writeTo(out);
            }
        } else {
            try {
                this.currentStream.close();
                if (copyOldContent) {
                    InputStream fin = this.createInputStream(this.tempFile);
                    IOUtils.copyAndCloseInput(fin, out);
                }
            }
            finally {
                this.streamList.remove(this.currentStream);
                this.deleteTempFile();
                this.inmem = true;
            }
        }
        this.currentStream = out;
        this.outputLocked = false;
    }

    public static void copyStream(InputStream in, OutputStream out, int bufferSize) throws IOException {
        IOUtils.copyAndCloseInput(in, out, bufferSize);
    }

    public long size() {
        return this.totalLength;
    }

    public byte[] getBytes() throws IOException {
        this.flush();
        if (this.inmem) {
            if (this.currentStream instanceof ByteArrayOutputStream) {
                return ((ByteArrayOutputStream)this.currentStream).toByteArray();
            }
            throw new IOException("Unknown format of currentStream");
        }
        try (InputStream fin = this.createInputStream(this.tempFile);){
            byte[] byArray = IOUtils.readBytesFromStream(fin);
            return byArray;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeCacheTo(OutputStream out) throws IOException {
        this.flush();
        if (this.inmem) {
            if (!(this.currentStream instanceof ByteArrayOutputStream)) throw new IOException("Unknown format of currentStream");
            ((ByteArrayOutputStream)this.currentStream).writeTo(out);
            return;
        } else {
            InputStream fin = this.createInputStream(this.tempFile);
            IOUtils.copyAndCloseInput(fin, out);
        }
    }

    public void writeCacheTo(StringBuilder out, long limit) throws IOException {
        this.writeCacheTo(out, StandardCharsets.UTF_8.name(), limit);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeCacheTo(StringBuilder out, String charsetName, long limit) throws IOException {
        this.flush();
        if (this.totalLength < limit || limit == -1L) {
            this.writeCacheTo(out, charsetName);
            return;
        }
        long count = 0L;
        if (this.inmem) {
            if (this.currentStream instanceof LoadingByteArrayOutputStream) {
                LoadingByteArrayOutputStream lout = (LoadingByteArrayOutputStream)this.currentStream;
                out.append(IOUtils.newStringFromBytes(lout.getRawBytes(), charsetName, 0, (int)limit));
                return;
            } else {
                if (!(this.currentStream instanceof ByteArrayOutputStream)) throw new IOException("Unknown format of currentStream");
                byte[] bytes = ((ByteArrayOutputStream)this.currentStream).toByteArray();
                out.append(IOUtils.newStringFromBytes(bytes, charsetName, 0, (int)limit));
            }
            return;
        }
        try (InputStream fin = this.createInputStream(this.tempFile);
             InputStreamReader reader = new InputStreamReader(fin, charsetName);){
            char[] bytes = new char[1024];
            long x = reader.read(bytes);
            while (x != -1L) {
                if (count + x > limit) {
                    x = limit - count;
                }
                out.append(bytes, 0, (int)x);
                if ((count += x) >= limit) {
                    x = -1L;
                    continue;
                }
                x = reader.read(bytes);
            }
            return;
        }
    }

    public void writeCacheTo(StringBuilder out) throws IOException {
        this.writeCacheTo(out, StandardCharsets.UTF_8.name());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeCacheTo(StringBuilder out, String charsetName) throws IOException {
        this.flush();
        if (this.inmem) {
            if (this.currentStream instanceof LoadingByteArrayOutputStream) {
                LoadingByteArrayOutputStream lout = (LoadingByteArrayOutputStream)this.currentStream;
                out.append(IOUtils.newStringFromBytes(lout.getRawBytes(), charsetName, 0, lout.size()));
                return;
            } else {
                if (!(this.currentStream instanceof ByteArrayOutputStream)) throw new IOException("Unknown format of currentStream");
                byte[] bytes = ((ByteArrayOutputStream)this.currentStream).toByteArray();
                out.append(IOUtils.newStringFromBytes(bytes, charsetName));
            }
            return;
        }
        try (InputStream fin = this.createInputStream(this.tempFile);
             InputStreamReader reader = new InputStreamReader(fin, charsetName);){
            char[] bytes = new char[1024];
            int x = reader.read(bytes);
            while (x != -1) {
                out.append(bytes, 0, x);
                x = reader.read(bytes);
            }
            return;
        }
    }

    public OutputStream getOut() {
        return this.currentStream;
    }

    public int hashCode() {
        return this.currentStream.hashCode();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder().append('[').append(CachedOutputStream.class.getName()).append(" Content: ");
        try {
            this.writeCacheTo(builder);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return builder.append(']').toString();
    }

    protected void onWrite() throws IOException {
    }

    private void enforceLimits() throws IOException {
        if (this.maxSize > 0L && this.totalLength > this.maxSize) {
            throw new CacheSizeExceededException();
        }
        if (this.inmem && this.totalLength > this.threshold && this.currentStream instanceof ByteArrayOutputStream) {
            this.createFileOutputStream();
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (!this.outputLocked) {
            this.onWrite();
            this.totalLength += (long)len;
            this.enforceLimits();
            this.currentStream.write(b, off, len);
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (!this.outputLocked) {
            this.onWrite();
            this.totalLength += (long)b.length;
            this.enforceLimits();
            this.currentStream.write(b);
        }
    }

    @Override
    public void write(int b) throws IOException {
        if (!this.outputLocked) {
            this.onWrite();
            ++this.totalLength;
            this.enforceLimits();
            this.currentStream.write(b);
        }
    }

    private void createFileOutputStream() throws IOException {
        if (this.tempFileFailed) {
            return;
        }
        ByteArrayOutputStream bout = (ByteArrayOutputStream)this.currentStream;
        try {
            this.tempFile = this.outputDir == null ? FileUtils.createTempFile("cos", "tmp") : FileUtils.createTempFile("cos", "tmp", this.outputDir, false);
            this.currentStream = this.createOutputStream(this.tempFile);
            bout.writeTo(this.currentStream);
            this.inmem = false;
            this.streamList.add(this.currentStream);
        }
        catch (Exception ex) {
            this.tempFileFailed = true;
            if (this.currentStream != bout) {
                this.currentStream.close();
            }
            this.deleteTempFile();
            this.inmem = true;
            this.currentStream = bout;
        }
    }

    public File getTempFile() {
        return this.tempFile != null && this.tempFile.exists() ? this.tempFile : null;
    }

    public InputStream getInputStream() throws IOException {
        this.flush();
        if (this.inmem) {
            if (this.currentStream instanceof LoadingByteArrayOutputStream) {
                return ((LoadingByteArrayOutputStream)this.currentStream).createInputStream();
            }
            if (this.currentStream instanceof ByteArrayOutputStream) {
                return new ByteArrayInputStream(((ByteArrayOutputStream)this.currentStream).toByteArray());
            }
            return null;
        }
        try {
            InputStream fileInputStream = new TransferableFileInputStream(this.tempFile);
            this.streamList.add(fileInputStream);
            if (this.cipherTransformation != null) {
                fileInputStream = new CipherInputStream(fileInputStream, this.ciphers.getDecryptor()){
                    boolean closed;

                    @Override
                    public void close() throws IOException {
                        if (!this.closed) {
                            super.close();
                            this.closed = true;
                        }
                    }
                };
            }
            return fileInputStream;
        }
        catch (FileNotFoundException e) {
            throw new IOException("Cached file was deleted, " + e.toString());
        }
    }

    private synchronized void deleteTempFile() {
        if (this.tempFile != null) {
            File file = this.tempFile;
            this.tempFile = null;
            FileUtils.delete(file);
        }
    }

    private boolean maybeDeleteTempFile(Object stream) {
        boolean postClosedInvoked = false;
        this.streamList.remove(stream);
        if (!this.inmem && this.tempFile != null && this.streamList.isEmpty() && this.allowDeleteOfFile) {
            if (this.currentStream != null) {
                try {
                    this.currentStream.close();
                    this.postClose();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                postClosedInvoked = true;
            }
            this.deleteTempFile();
            this.currentStream = new LoadingByteArrayOutputStream(1024);
            this.inmem = true;
        }
        return postClosedInvoked;
    }

    public void setOutputDir(File outputDir) throws IOException {
        this.outputDir = outputDir;
    }

    public long getThreshold() {
        return this.threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public void setCipherTransformation(String cipherTransformation) {
        this.cipherTransformation = cipherTransformation;
    }

    public static void setDefaultMaxSize(long l) {
        if (l == -1L) {
            String s2 = SystemPropertyAction.getProperty("org.apache.cxf.io.CachedOutputStream.MaxSize", "-1");
            l = Long.parseLong(s2);
        }
        defaultMaxSize = l;
    }

    public static void setDefaultThreshold(int i) {
        if (i == -1) {
            i = SystemPropertyAction.getInteger("org.apache.cxf.io.CachedOutputStream.Threshold", -1);
            if (i <= 0) {
                i = 131072;
            } else {
                thresholdSysPropSet = true;
            }
        }
        defaultThreshold = i;
    }

    public static boolean isThresholdSysPropSet() {
        return thresholdSysPropSet;
    }

    public static void setDefaultCipherTransformation(String n) {
        if (n == null) {
            n = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.io.CachedOutputStream.CipherTransformation");
        }
        defaultCipherTransformation = n;
    }

    private OutputStream createOutputStream(File file) throws IOException {
        FilterOutputStream out = new BufferedOutputStream(Files.newOutputStream(file.toPath(), new OpenOption[0]));
        if (this.cipherTransformation != null) {
            try {
                if (this.ciphers == null) {
                    this.ciphers = new CipherPair(this.cipherTransformation);
                }
            }
            catch (GeneralSecurityException e) {
                throw new IOException(e.getMessage(), e);
            }
            out = new CipherOutputStream(out, this.ciphers.getEncryptor()){
                boolean closed;

                @Override
                public void close() throws IOException {
                    if (!this.closed) {
                        super.close();
                        this.closed = true;
                    }
                }
            };
        }
        return out;
    }

    private InputStream createInputStream(File file) throws IOException {
        InputStream in = Files.newInputStream(file.toPath(), new OpenOption[0]);
        if (this.cipherTransformation != null) {
            in = new CipherInputStream(in, this.ciphers.getDecryptor()){
                boolean closed;

                @Override
                public void close() throws IOException {
                    if (!this.closed) {
                        super.close();
                        this.closed = true;
                    }
                }
            };
        }
        return in;
    }

    static {
        File f;
        String s2 = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.io.CachedOutputStream.OutputDirectory");
        DEFAULT_TEMP_DIR = s2 != null ? ((f = new File(s2)).exists() && f.isDirectory() ? f : null) : null;
        CachedOutputStream.setDefaultThreshold(-1);
        CachedOutputStream.setDefaultMaxSize(-1L);
        CachedOutputStream.setDefaultCipherTransformation(null);
    }

    private class TransferableFileInputStream
    extends FileInputStream
    implements Transferable {
        private boolean closed;
        private File sourceFile;

        TransferableFileInputStream(File sourceFile) throws FileNotFoundException {
            super(sourceFile);
            this.sourceFile = sourceFile;
        }

        @Override
        public void close() throws IOException {
            if (!this.closed) {
                super.close();
                CachedOutputStream.this.maybeDeleteTempFile(this);
            }
            this.closed = true;
        }

        @Override
        public void transferTo(File destinationFile) throws IOException {
            if (this.closed) {
                throw new IOException("Stream closed");
            }
            boolean transfered = this.sourceFile.renameTo(destinationFile);
            if (!transfered) {
                try (OutputStream out = Files.newOutputStream(destinationFile.toPath(), new OpenOption[0]);){
                    IOUtils.copyAndCloseInput(this, out);
                }
            }
        }
    }
}

