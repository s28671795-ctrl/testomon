/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.OutputStream;
import java.util.Arrays;
import org.h2.store.Data;
import org.h2.store.FileStore;
import org.h2.tools.CompressTool;

public class FileStoreOutputStream
extends OutputStream {
    private FileStore store;
    private final Data page;
    private final String compressionAlgorithm;
    private final CompressTool compress;
    private final byte[] buffer = new byte[]{0};

    public FileStoreOutputStream(FileStore fileStore, String string) {
        this.store = fileStore;
        if (string != null) {
            this.compress = CompressTool.getInstance();
            this.compressionAlgorithm = string;
        } else {
            this.compress = null;
            this.compressionAlgorithm = null;
        }
        this.page = Data.create(16);
    }

    @Override
    public void write(int n) {
        this.buffer[0] = (byte)n;
        this.write(this.buffer);
    }

    @Override
    public void write(byte[] byArray) {
        this.write(byArray, 0, byArray.length);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) {
        if (n2 > 0) {
            this.page.reset();
            if (this.compress != null) {
                if (n != 0 || n2 != byArray.length) {
                    byArray = Arrays.copyOfRange(byArray, n, n + n2);
                    n = 0;
                }
                int n3 = n2;
                byArray = this.compress.compress(byArray, this.compressionAlgorithm);
                n2 = byArray.length;
                this.page.checkCapacity(8 + n2);
                this.page.writeInt(n2);
                this.page.writeInt(n3);
                this.page.write(byArray, n, n2);
            } else {
                this.page.checkCapacity(4 + n2);
                this.page.writeInt(n2);
                this.page.write(byArray, n, n2);
            }
            this.page.fillAligned();
            this.store.write(this.page.getBytes(), 0, this.page.length());
        }
    }

    @Override
    public void close() {
        if (this.store != null) {
            try {
                this.store.close();
            }
            finally {
                this.store = null;
            }
        }
    }
}

