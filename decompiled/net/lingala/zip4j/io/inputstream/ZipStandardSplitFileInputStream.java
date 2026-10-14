/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import net.lingala.zip4j.io.inputstream.SplitFileInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;

public class ZipStandardSplitFileInputStream
extends SplitFileInputStream {
    protected RandomAccessFile randomAccessFile;
    protected File zipFile;
    private int lastSplitZipFileNumber;
    private boolean isSplitZipArchive;
    private int currentSplitFileCounter = 0;
    private byte[] singleByteArray = new byte[1];

    public ZipStandardSplitFileInputStream(File zipFile, boolean isSplitZipArchive, int lastSplitZipFileNumber) throws FileNotFoundException {
        this.randomAccessFile = new RandomAccessFile(zipFile, RandomAccessFileMode.READ.getValue());
        this.zipFile = zipFile;
        this.isSplitZipArchive = isSplitZipArchive;
        this.lastSplitZipFileNumber = lastSplitZipFileNumber;
        if (isSplitZipArchive) {
            this.currentSplitFileCounter = lastSplitZipFileNumber;
        }
    }

    @Override
    public int read() throws IOException {
        int readLen = this.read(this.singleByteArray);
        if (readLen == -1) {
            return -1;
        }
        return this.singleByteArray[0];
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int readLen = this.randomAccessFile.read(b, off, len);
        if ((readLen != len || readLen == -1) && this.isSplitZipArchive) {
            int newlyRead;
            this.openRandomAccessFileForIndex(this.currentSplitFileCounter + 1);
            ++this.currentSplitFileCounter;
            if (readLen < 0) {
                readLen = 0;
            }
            if ((newlyRead = this.randomAccessFile.read(b, readLen, len - readLen)) > 0) {
                readLen += newlyRead;
            }
        }
        return readLen;
    }

    @Override
    public void prepareExtractionForFileHeader(FileHeader fileHeader) throws IOException {
        if (this.isSplitZipArchive && this.currentSplitFileCounter != fileHeader.getDiskNumberStart()) {
            this.openRandomAccessFileForIndex(fileHeader.getDiskNumberStart());
            this.currentSplitFileCounter = fileHeader.getDiskNumberStart();
        }
        this.randomAccessFile.seek(fileHeader.getOffsetLocalHeader());
    }

    @Override
    public void close() throws IOException {
        if (this.randomAccessFile != null) {
            this.randomAccessFile.close();
        }
    }

    protected void openRandomAccessFileForIndex(int zipFileIndex) throws IOException {
        File nextSplitFile = this.getNextSplitFile(zipFileIndex);
        if (!nextSplitFile.exists()) {
            throw new FileNotFoundException("zip split file does not exist: " + nextSplitFile);
        }
        this.randomAccessFile.close();
        this.randomAccessFile = new RandomAccessFile(nextSplitFile, RandomAccessFileMode.READ.getValue());
    }

    protected File getNextSplitFile(int zipFileIndex) throws IOException {
        if (zipFileIndex == this.lastSplitZipFileNumber) {
            return this.zipFile;
        }
        String currZipFileNameWithPath = this.zipFile.getCanonicalPath();
        String extensionSubString = ".z0";
        if (zipFileIndex >= 9) {
            extensionSubString = ".z";
        }
        return new File(currZipFileNameWithPath.substring(0, currZipFileNameWithPath.lastIndexOf(".")) + extensionSubString + (zipFileIndex + 1));
    }
}

