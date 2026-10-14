/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import net.lingala.zip4j.io.inputstream.NumberedSplitRandomAccessFile;
import net.lingala.zip4j.io.inputstream.SplitFileInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;

public class NumberedSplitFileInputStream
extends SplitFileInputStream {
    private RandomAccessFile randomAccessFile;

    public NumberedSplitFileInputStream(File zipFile) throws IOException {
        this.randomAccessFile = new NumberedSplitRandomAccessFile(zipFile, RandomAccessFileMode.READ.getValue());
    }

    @Override
    public int read() throws IOException {
        return this.randomAccessFile.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.randomAccessFile.read(b, off, len);
    }

    @Override
    public void prepareExtractionForFileHeader(FileHeader fileHeader) throws IOException {
        this.randomAccessFile.seek(fileHeader.getOffsetLocalHeader());
    }

    @Override
    public void close() throws IOException {
        if (this.randomAccessFile != null) {
            this.randomAccessFile.close();
        }
    }
}

