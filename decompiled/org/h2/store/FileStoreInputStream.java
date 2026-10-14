/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.io.InputStream;
import org.h2.message.DbException;
import org.h2.store.Data;
import org.h2.store.FileStore;
import org.h2.tools.CompressTool;
import org.h2.util.Utils;

public class FileStoreInputStream
extends InputStream {
    private FileStore store;
    private final Data page;
    private int remainingInBuffer;
    private final CompressTool compress;
    private boolean endOfFile;
    private final boolean alwaysClose;

    public FileStoreInputStream(FileStore fileStore, boolean bl, boolean bl2) {
        this.store = fileStore;
        this.alwaysClose = bl2;
        this.compress = bl ? CompressTool.getInstance() : null;
        this.page = Data.create(16);
        try {
            if (fileStore.length() <= 48L) {
                this.close();
            } else {
                this.fillBuffer();
            }
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, fileStore.name);
        }
    }

    @Override
    public int available() {
        return this.remainingInBuffer <= 0 ? 0 : this.remainingInBuffer;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (n2 == 0) {
            return 0;
        }
        int n4 = 0;
        while (n2 > 0 && (n3 = this.readBlock(byArray, n, n2)) >= 0) {
            n4 += n3;
            n += n3;
            n2 -= n3;
        }
        return n4 == 0 ? -1 : n4;
    }

    private int readBlock(byte[] byArray, int n, int n2) throws IOException {
        this.fillBuffer();
        if (this.endOfFile) {
            return -1;
        }
        int n3 = Math.min(this.remainingInBuffer, n2);
        this.page.read(byArray, n, n3);
        this.remainingInBuffer -= n3;
        return n3;
    }

    private void fillBuffer() throws IOException {
        if (this.remainingInBuffer > 0 || this.endOfFile) {
            return;
        }
        this.page.reset();
        this.store.openFile();
        if (this.store.length() == this.store.getFilePointer()) {
            this.close();
            return;
        }
        this.store.readFully(this.page.getBytes(), 0, 16);
        this.page.reset();
        this.remainingInBuffer = this.page.readInt();
        if (this.remainingInBuffer < 0) {
            this.close();
            return;
        }
        this.page.checkCapacity(this.remainingInBuffer);
        if (this.compress != null) {
            this.page.checkCapacity(4);
            this.page.readInt();
        }
        this.page.setPos(this.page.length() + this.remainingInBuffer);
        this.page.fillAligned();
        int n = this.page.length() - 16;
        this.page.reset();
        this.page.readInt();
        this.store.readFully(this.page.getBytes(), 16, n);
        this.page.reset();
        this.page.readInt();
        if (this.compress != null) {
            int n2 = this.page.readInt();
            byte[] byArray = Utils.newBytes(this.remainingInBuffer);
            this.page.read(byArray, 0, this.remainingInBuffer);
            this.page.reset();
            this.page.checkCapacity(n2);
            CompressTool.expand(byArray, this.page.getBytes(), 0);
            this.remainingInBuffer = n2;
        }
        if (this.alwaysClose) {
            this.store.closeFile();
        }
    }

    @Override
    public void close() {
        if (this.store != null) {
            try {
                this.store.close();
                this.endOfFile = true;
            }
            finally {
                this.store = null;
            }
        }
    }

    protected void finalize() {
        this.close();
    }

    @Override
    public int read() throws IOException {
        this.fillBuffer();
        if (this.endOfFile) {
            return -1;
        }
        int n = this.page.readByte() & 0xFF;
        --this.remainingInBuffer;
        return n;
    }
}

