/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;
import net.lingala.zip4j.util.FileUtils;

public class NumberedSplitRandomAccessFile
extends RandomAccessFile {
    private long splitLength;
    private File[] allSortedSplitFiles;
    private RandomAccessFile randomAccessFile;
    private byte[] singleByteBuffer = new byte[1];
    private int currentOpenSplitFileCounter = 0;
    private String rwMode;

    public NumberedSplitRandomAccessFile(String name, String mode) throws IOException {
        this(new File(name), mode);
    }

    public NumberedSplitRandomAccessFile(File file, String mode) throws IOException {
        this(file, mode, FileUtils.getAllSortedNumberedSplitFiles(file));
    }

    public NumberedSplitRandomAccessFile(File file, String mode, File[] allSortedSplitFiles) throws IOException {
        super(file, mode);
        super.close();
        if (RandomAccessFileMode.WRITE.getValue().equals(mode)) {
            throw new IllegalArgumentException("write mode is not allowed for NumberedSplitRandomAccessFile");
        }
        this.assertAllSplitFilesExist(allSortedSplitFiles);
        this.randomAccessFile = new RandomAccessFile(file, mode);
        this.allSortedSplitFiles = allSortedSplitFiles;
        this.splitLength = file.length();
        this.rwMode = mode;
    }

    @Override
    public int read() throws IOException {
        int readLen = this.read(this.singleByteBuffer);
        if (readLen == -1) {
            return -1;
        }
        return this.singleByteBuffer[0] & 0xFF;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int readLen = this.randomAccessFile.read(b, off, len);
        if (readLen == -1) {
            if (this.currentOpenSplitFileCounter == this.allSortedSplitFiles.length - 1) {
                return -1;
            }
            this.openRandomAccessFileForIndex(this.currentOpenSplitFileCounter + 1);
            return this.read(b, off, len);
        }
        return readLen;
    }

    @Override
    public void write(int b) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void seek(long pos) throws IOException {
        int splitPartOfPosition = (int)(pos / this.splitLength);
        if (splitPartOfPosition != this.currentOpenSplitFileCounter) {
            this.openRandomAccessFileForIndex(splitPartOfPosition);
        }
        this.randomAccessFile.seek(pos - (long)splitPartOfPosition * this.splitLength);
    }

    @Override
    public long getFilePointer() throws IOException {
        return this.randomAccessFile.getFilePointer();
    }

    @Override
    public long length() throws IOException {
        return this.randomAccessFile.length();
    }

    @Override
    public void close() throws IOException {
        if (this.randomAccessFile != null) {
            this.randomAccessFile.close();
        }
        super.close();
    }

    public void seekInCurrentPart(long pos) throws IOException {
        this.randomAccessFile.seek(pos);
    }

    public void openLastSplitFileForReading() throws IOException {
        this.openRandomAccessFileForIndex(this.allSortedSplitFiles.length - 1);
    }

    private void openRandomAccessFileForIndex(int splitCounter) throws IOException {
        if (this.currentOpenSplitFileCounter == splitCounter) {
            return;
        }
        if (splitCounter > this.allSortedSplitFiles.length - 1) {
            throw new IOException("split counter greater than number of split files");
        }
        if (this.randomAccessFile != null) {
            this.randomAccessFile.close();
        }
        this.randomAccessFile = new RandomAccessFile(this.allSortedSplitFiles[splitCounter], this.rwMode);
        this.currentOpenSplitFileCounter = splitCounter;
    }

    private void assertAllSplitFilesExist(File[] allSortedSplitFiles) throws IOException {
        int splitCounter = 1;
        for (File splitFile : allSortedSplitFiles) {
            String fileExtension = FileUtils.getFileExtension(splitFile);
            try {
                if (splitCounter != Integer.parseInt(fileExtension)) {
                    throw new IOException("Split file number " + splitCounter + " does not exist");
                }
                ++splitCounter;
            }
            catch (NumberFormatException e) {
                throw new IOException("Split file extension not in expected format. Found: " + fileExtension + " expected of format: .001, .002, etc");
            }
        }
    }
}

