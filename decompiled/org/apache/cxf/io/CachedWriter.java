/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.io;

import java.io.BufferedOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
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
import org.apache.cxf.io.CacheSizeExceededException;
import org.apache.cxf.io.CachedWriterCallback;
import org.apache.cxf.io.CipherPair;

public class CachedWriter
extends Writer {
    private static final File DEFAULT_TEMP_DIR;
    private static int defaultThreshold;
    private static long defaultMaxSize;
    private static String defaultCipherTransformation;
    protected boolean outputLocked;
    protected Writer currentStream;
    private boolean cosClosed;
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
    private List<CachedWriterCallback> callbacks;
    private List<Object> streamList = new ArrayList<Object>();

    public CachedWriter() {
        this(defaultThreshold);
        this.inmem = true;
    }

    public CachedWriter(long threshold) {
        this.threshold = threshold;
        this.currentStream = new LoadingCharArrayWriter();
        this.inmem = true;
        this.readBusProperties();
    }

    private void readBusProperties() {
        Bus b = BusFactory.getThreadDefaultBus(false);
        if (b != null) {
            File f;
            String v = CachedWriter.getBusProperty(b, "bus.io.CachedOutputStream.Threshold", null);
            if (v != null && this.threshold == (long)defaultThreshold) {
                this.threshold = Integer.parseInt(v);
            }
            if ((v = CachedWriter.getBusProperty(b, "bus.io.CachedOutputStream.MaxSize", null)) != null) {
                this.maxSize = Integer.parseInt(v);
            }
            if ((v = CachedWriter.getBusProperty(b, "bus.io.CachedOutputStream.CipherTransformation", null)) != null) {
                this.cipherTransformation = v;
            }
            if ((v = CachedWriter.getBusProperty(b, "bus.io.CachedOutputStream.OutputDirectory", null)) != null && (f = new File(v)).exists() && f.isDirectory()) {
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

    public void registerCallback(CachedWriterCallback cb) {
        if (null == this.callbacks) {
            this.callbacks = new ArrayList<CachedWriterCallback>();
        }
        this.callbacks.add(cb);
    }

    public void deregisterCallback(CachedWriterCallback cb) {
        if (null != this.callbacks) {
            this.callbacks.remove(cb);
        }
    }

    public List<CachedWriterCallback> getCallbacks() {
        return this.callbacks == null ? null : Collections.unmodifiableList(this.callbacks);
    }

    protected void doFlush() throws IOException {
    }

    @Override
    public void flush() throws IOException {
        if (!this.cosClosed) {
            this.currentStream.flush();
        }
        if (null != this.callbacks) {
            for (CachedWriterCallback cb : this.callbacks) {
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
            for (CachedWriterCallback cb : this.callbacks) {
                cb.onClose(this);
            }
        }
        this.doClose();
        this.streamList.remove(this.currentStream);
    }

    @Override
    public void close() throws IOException {
        if (!this.cosClosed) {
            this.currentStream.flush();
        }
        this.outputLocked = true;
        if (null != this.callbacks) {
            for (CachedWriterCallback cb : this.callbacks) {
                cb.onClose(this);
            }
        }
        this.doClose();
        this.currentStream.close();
        this.maybeDeleteTempFile(this.currentStream);
        if (this.ciphers != null) {
            this.ciphers.clean();
        }
        this.postClose();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CachedWriter) {
            return this.currentStream.equals(((CachedWriter)obj).currentStream);
        }
        return this.currentStream.equals(obj);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void resetOut(Writer out, boolean copyOldContent) throws IOException {
        if (out == null) {
            out = new LoadingCharArrayWriter();
        }
        if (this.currentStream instanceof CachedWriter) {
            CachedWriter ac = (CachedWriter)this.currentStream;
            Reader in = ac.getReader();
            IOUtils.copyAndCloseInput(in, out);
        } else if (this.inmem) {
            if (!(this.currentStream instanceof LoadingCharArrayWriter)) throw new IOException("Unknown format of currentStream");
            LoadingCharArrayWriter byteOut = (LoadingCharArrayWriter)this.currentStream;
            if (copyOldContent && byteOut.size() > 0) {
                byteOut.writeTo(out);
            }
        } else {
            this.currentStream.close();
            if (copyOldContent) {
                InputStreamReader fin = this.createInputStreamReader(this.tempFile);
                IOUtils.copyAndCloseInput(fin, out);
            }
            this.streamList.remove(this.currentStream);
            this.deleteTempFile();
            this.inmem = true;
        }
        this.currentStream = out;
        this.outputLocked = false;
    }

    public long size() {
        return this.totalLength;
    }

    public char[] getChars() throws IOException {
        this.flush();
        if (this.inmem) {
            if (this.currentStream instanceof LoadingCharArrayWriter) {
                return ((LoadingCharArrayWriter)this.currentStream).toCharArray();
            }
            throw new IOException("Unknown format of currentStream");
        }
        try (InputStreamReader fin = this.createInputStreamReader(this.tempFile);){
            CharArrayWriter out = new CharArrayWriter((int)this.tempFile.length());
            char[] bytes = new char[1024];
            int x = fin.read(bytes);
            while (x != -1) {
                out.write(bytes, 0, x);
                x = fin.read(bytes);
            }
            char[] cArray = out.toCharArray();
            return cArray;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeCacheTo(Writer out) throws IOException {
        this.flush();
        if (this.inmem) {
            if (!(this.currentStream instanceof LoadingCharArrayWriter)) throw new IOException("Unknown format of currentStream");
            ((LoadingCharArrayWriter)this.currentStream).writeTo(out);
            return;
        }
        try (InputStreamReader fin = this.createInputStreamReader(this.tempFile);){
            char[] bytes = new char[1024];
            int x = fin.read(bytes);
            while (x != -1) {
                out.write(bytes, 0, x);
                x = fin.read(bytes);
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeCacheTo(StringBuilder out, long limit) throws IOException {
        this.flush();
        if (this.totalLength < limit || limit == -1L) {
            this.writeCacheTo(out);
            return;
        }
        long count = 0L;
        if (this.inmem) {
            if (!(this.currentStream instanceof LoadingCharArrayWriter)) throw new IOException("Unknown format of currentStream");
            LoadingCharArrayWriter s2 = (LoadingCharArrayWriter)this.currentStream;
            out.append(s2.rawCharArray(), 0, (int)limit);
            return;
        }
        try (InputStreamReader fin = this.createInputStreamReader(this.tempFile);){
            char[] bytes = new char[1024];
            long x = fin.read(bytes);
            while (x != -1L) {
                if (count + x > limit) {
                    x = limit - count;
                }
                out.append(bytes, 0, (int)x);
                if ((count += x) >= limit) {
                    x = -1L;
                    continue;
                }
                x = fin.read(bytes);
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeCacheTo(StringBuilder out) throws IOException {
        this.flush();
        if (this.inmem) {
            if (!(this.currentStream instanceof LoadingCharArrayWriter)) throw new IOException("Unknown format of currentStream");
            LoadingCharArrayWriter lcaw = (LoadingCharArrayWriter)this.currentStream;
            out.append(lcaw.rawCharArray(), 0, lcaw.size());
            return;
        }
        try (InputStreamReader r = this.createInputStreamReader(this.tempFile);){
            char[] chars = new char[1024];
            int x = r.read(chars);
            while (x != -1) {
                out.append(chars, 0, x);
                x = r.read(chars);
            }
            return;
        }
    }

    public Writer getOut() {
        return this.currentStream;
    }

    public int hashCode() {
        return this.currentStream.hashCode();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder().append('[').append(CachedWriter.class.getName()).append(" Content: ");
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
        if (this.inmem && this.totalLength > this.threshold && this.currentStream instanceof LoadingCharArrayWriter) {
            this.createFileOutputStream();
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if (!this.outputLocked) {
            this.onWrite();
            this.totalLength += (long)len;
            this.enforceLimits();
            this.currentStream.write(cbuf, off, len);
        }
    }

    private void createFileOutputStream() throws IOException {
        if (this.tempFileFailed) {
            return;
        }
        LoadingCharArrayWriter bout = (LoadingCharArrayWriter)this.currentStream;
        try {
            this.tempFile = this.outputDir == null ? FileUtils.createTempFile("cos", "tmp") : FileUtils.createTempFile("cos", "tmp", this.outputDir, false);
            this.currentStream = this.createOutputStreamWriter(this.tempFile);
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

    public Reader getReader() throws IOException {
        this.flush();
        if (this.inmem) {
            if (this.currentStream instanceof LoadingCharArrayWriter) {
                LoadingCharArrayWriter lcaw = (LoadingCharArrayWriter)this.currentStream;
                return new CharArrayReader(lcaw.rawCharArray(), 0, lcaw.size());
            }
            return null;
        }
        try {
            InputStream fileInputStream = new FileInputStream(this.tempFile){
                boolean closed;

                @Override
                public void close() throws IOException {
                    if (!this.closed) {
                        super.close();
                        CachedWriter.this.maybeDeleteTempFile(this);
                    }
                    this.closed = true;
                }
            };
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
            return new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
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

    private void maybeDeleteTempFile(Object stream) {
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
            }
            this.deleteTempFile();
            this.currentStream = new LoadingCharArrayWriter();
            this.inmem = true;
        }
    }

    public void setOutputDir(File outputDir) throws IOException {
        this.outputDir = outputDir;
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
            String s2 = System.getProperty("org.apache.cxf.io.CachedOutputStream.MaxSize");
            if (s2 == null) {
                s2 = System.getProperty("org.apache.cxf.io.CachedWriter.MaxSize", "-1");
            }
            l = Long.parseLong(s2);
        }
        defaultMaxSize = l;
    }

    public static void setDefaultThreshold(int i) {
        if (i == -1) {
            i = SystemPropertyAction.getInteger("org.apache.cxf.io.CachedOutputStream.Threshold", -1);
            if (i == -1) {
                i = SystemPropertyAction.getInteger("org.apache.cxf.io.CachedWriter.Threshold", -1);
            }
            if (i <= 0) {
                i = 65536;
            }
        }
        defaultThreshold = i;
    }

    public static void setDefaultCipherTransformation(String n) {
        if (n == null) {
            n = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.io.CachedOutputStream.CipherTransformation");
        }
        defaultCipherTransformation = n;
    }

    private OutputStreamWriter createOutputStreamWriter(File file) throws IOException {
        FilterOutputStream out = new BufferedOutputStream(Files.newOutputStream(file.toPath(), new OpenOption[0]));
        if (this.cipherTransformation != null) {
            try {
                if (this.ciphers == null) {
                    this.ciphers = new CipherPair(this.cipherTransformation);
                }
            }
            catch (GeneralSecurityException e) {
                ((OutputStream)out).close();
                throw new IOException(e.getMessage(), e);
            }
            out = new CipherOutputStream(out, this.ciphers.getEncryptor()){

                @Override
                public void close() throws IOException {
                    if (!CachedWriter.this.cosClosed) {
                        super.close();
                        CachedWriter.this.cosClosed = true;
                    }
                }
            };
        }
        return new OutputStreamWriter(out, StandardCharsets.UTF_8){

            @Override
            public void close() throws IOException {
                if (!CachedWriter.this.cosClosed) {
                    super.close();
                    CachedWriter.this.cosClosed = true;
                }
            }
        };
    }

    private InputStreamReader createInputStreamReader(File file) throws IOException {
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
        return new InputStreamReader(in, StandardCharsets.UTF_8);
    }

    static {
        File f;
        String s2 = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.io.CachedOutputStream.OutputDirectory");
        if (s2 == null) {
            s2 = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.io.CachedWriter.OutputDirectory");
        }
        DEFAULT_TEMP_DIR = s2 != null ? ((f = new File(s2)).exists() && f.isDirectory() ? f : null) : null;
        CachedWriter.setDefaultThreshold(-1);
        CachedWriter.setDefaultMaxSize(-1L);
        CachedWriter.setDefaultCipherTransformation(null);
    }

    static class LoadingCharArrayWriter
    extends CharArrayWriter {
        LoadingCharArrayWriter() {
            super(1024);
        }

        public char[] rawCharArray() {
            return this.buf;
        }
    }
}

