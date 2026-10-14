/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.lang.ref.Reference;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.security.SecureFileStore;
import org.h2.store.DataHandler;
import org.h2.store.fs.FileUtils;

public class FileStore {
    public static final int HEADER_LENGTH = 48;
    private static final String HEADER = "-- H2 0.5/B --      ".substring(0, 15) + "\n";
    private static final boolean ASSERT;
    protected String name;
    private final DataHandler handler;
    private FileChannel file;
    private long filePos;
    private long fileLength;
    private Reference<?> autoDeleteReference;
    private boolean checkedWriting = true;
    private final String mode;
    private FileLock lock;

    protected FileStore(DataHandler dataHandler, String string, String string2) {
        this.handler = dataHandler;
        this.name = string;
        try {
            boolean bl = FileUtils.exists(string);
            if (bl && !FileUtils.canWrite(string)) {
                string2 = "r";
            } else {
                FileUtils.createDirectories(FileUtils.getParent(string));
            }
            this.file = FileUtils.open(string, string2);
            if (bl) {
                this.fileLength = this.file.size();
            }
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, "name: " + string + " mode: " + string2);
        }
        this.mode = string2;
    }

    public static FileStore open(DataHandler dataHandler, String string, String string2) {
        return FileStore.open(dataHandler, string, string2, null, null, 0);
    }

    public static FileStore open(DataHandler dataHandler, String string, String string2, String string3, byte[] byArray) {
        return FileStore.open(dataHandler, string, string2, string3, byArray, 1024);
    }

    public static FileStore open(DataHandler dataHandler, String string, String string2, String string3, byte[] byArray, int n) {
        FileStore fileStore = string3 == null ? new FileStore(dataHandler, string, string2) : new SecureFileStore(dataHandler, string, string2, string3, byArray, n);
        return fileStore;
    }

    protected byte[] generateSalt() {
        return HEADER.getBytes(StandardCharsets.UTF_8);
    }

    protected void initKey(byte[] byArray) {
    }

    public void setCheckedWriting(boolean bl) {
        this.checkedWriting = bl;
    }

    private void checkWritingAllowed() {
        if (this.handler != null && this.checkedWriting) {
            this.handler.checkWritingAllowed();
        }
    }

    private void checkPowerOff() {
        if (this.handler != null) {
            this.handler.checkPowerOff();
        }
    }

    public void init() {
        int n = 16;
        byte[] byArray = HEADER.getBytes(StandardCharsets.UTF_8);
        if (this.length() < 48L) {
            this.checkedWriting = false;
            this.writeDirect(byArray, 0, n);
            byte[] byArray2 = this.generateSalt();
            this.writeDirect(byArray2, 0, n);
            this.initKey(byArray2);
            this.write(byArray, 0, n);
            this.checkedWriting = true;
        } else {
            this.seek(0L);
            byte[] byArray3 = new byte[n];
            this.readFullyDirect(byArray3, 0, n);
            if (!Arrays.equals(byArray3, byArray)) {
                throw DbException.get(90048, this.name);
            }
            byte[] byArray4 = new byte[n];
            this.readFullyDirect(byArray4, 0, n);
            this.initKey(byArray4);
            this.readFully(byArray3, 0, 16);
            if (!Arrays.equals(byArray3, byArray)) {
                throw DbException.get(90049, this.name);
            }
        }
    }

    public void close() {
        if (this.file != null) {
            try {
                FileStore.trace("close", this.name, this.file);
                this.file.close();
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, this.name);
            }
            finally {
                this.file = null;
            }
        }
    }

    public void closeSilently() {
        try {
            this.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void closeAndDeleteSilently() {
        if (this.file != null) {
            this.closeSilently();
            this.handler.getTempFileDeleter().deleteFile(this.autoDeleteReference, this.name);
            this.name = null;
        }
    }

    public void readFullyDirect(byte[] byArray, int n, int n2) {
        this.readFully(byArray, n, n2);
    }

    public void readFully(byte[] byArray, int n, int n2) {
        if (n2 < 0 || n2 % 16 != 0) {
            throw DbException.getInternalError("unaligned read " + this.name + " len " + n2);
        }
        this.checkPowerOff();
        try {
            FileUtils.readFully(this.file, ByteBuffer.wrap(byArray, n, n2));
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, this.name);
        }
        this.filePos += (long)n2;
    }

    public void seek(long l) {
        if (l % 16L != 0L) {
            throw DbException.getInternalError("unaligned seek " + this.name + " pos " + l);
        }
        try {
            if (l != this.filePos) {
                this.file.position(l);
                this.filePos = l;
            }
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, this.name);
        }
    }

    protected void writeDirect(byte[] byArray, int n, int n2) {
        this.write(byArray, n, n2);
    }

    public void write(byte[] byArray, int n, int n2) {
        if (n2 < 0 || n2 % 16 != 0) {
            throw DbException.getInternalError("unaligned write " + this.name + " len " + n2);
        }
        this.checkWritingAllowed();
        this.checkPowerOff();
        try {
            FileUtils.writeFully(this.file, ByteBuffer.wrap(byArray, n, n2));
        }
        catch (IOException iOException) {
            this.closeFileSilently();
            throw DbException.convertIOException(iOException, this.name);
        }
        this.filePos += (long)n2;
        this.fileLength = Math.max(this.filePos, this.fileLength);
    }

    public void setLength(long l) {
        if (l % 16L != 0L) {
            throw DbException.getInternalError("unaligned setLength " + this.name + " pos " + l);
        }
        this.checkPowerOff();
        this.checkWritingAllowed();
        try {
            if (l > this.fileLength) {
                long l2 = this.filePos;
                this.file.position(l - 1L);
                FileUtils.writeFully(this.file, ByteBuffer.wrap(new byte[1]));
                this.file.position(l2);
            } else {
                this.file.truncate(l);
            }
            this.fileLength = l;
        }
        catch (IOException iOException) {
            this.closeFileSilently();
            throw DbException.convertIOException(iOException, this.name);
        }
    }

    public long length() {
        long l = this.fileLength;
        if (ASSERT) {
            try {
                l = this.file.size();
                if (l != this.fileLength) {
                    throw DbException.getInternalError("file " + this.name + " length " + l + " expected " + this.fileLength);
                }
                if (l % 16L != 0L) {
                    long l2 = l + 16L - l % 16L;
                    this.file.truncate(l2);
                    this.fileLength = l2;
                    throw DbException.getInternalError("unaligned file length " + this.name + " len " + l);
                }
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, this.name);
            }
        }
        return l;
    }

    public long getFilePointer() {
        if (ASSERT) {
            try {
                if (this.file.position() != this.filePos) {
                    throw DbException.getInternalError(this.file.position() + " " + this.filePos);
                }
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, this.name);
            }
        }
        return this.filePos;
    }

    public void sync() {
        try {
            this.file.force(true);
        }
        catch (IOException iOException) {
            this.closeFileSilently();
            throw DbException.convertIOException(iOException, this.name);
        }
    }

    public void autoDelete() {
        if (this.autoDeleteReference == null) {
            this.autoDeleteReference = this.handler.getTempFileDeleter().addFile(this.name, this);
        }
    }

    public void stopAutoDelete() {
        this.handler.getTempFileDeleter().stopAutoDelete(this.autoDeleteReference, this.name);
        this.autoDeleteReference = null;
    }

    public void closeFile() throws IOException {
        this.file.close();
        this.file = null;
    }

    private void closeFileSilently() {
        try {
            this.file.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void openFile() throws IOException {
        if (this.file == null) {
            this.file = FileUtils.open(this.name, this.mode);
            this.file.position(this.filePos);
        }
    }

    private static void trace(String string, String string2, Object object) {
        if (SysProperties.TRACE_IO) {
            System.out.println("FileStore." + string + " " + string2 + " " + object);
        }
    }

    public synchronized boolean tryLock() {
        try {
            this.lock = this.file.tryLock();
            return this.lock != null;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public synchronized void releaseLock() {
        if (this.file != null && this.lock != null) {
            try {
                this.lock.release();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.lock = null;
        }
    }

    static {
        boolean bl = false;
        if (!$assertionsDisabled) {
            bl = true;
            if (!true) {
                throw new AssertionError();
            }
        }
        ASSERT = bl;
    }
}

