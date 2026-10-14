/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.io.outputstream.OutputStreamWithSplitZipSupport;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.RawIO;

public class SplitOutputStream
extends OutputStream
implements OutputStreamWithSplitZipSupport {
    private RandomAccessFile raf;
    private long splitLength;
    private File zipFile;
    private int currSplitFileCounter;
    private long bytesWrittenForThisPart;
    private RawIO rawIO = new RawIO();

    public SplitOutputStream(File file) throws FileNotFoundException, ZipException {
        this(file, -1L);
    }

    public SplitOutputStream(File file, long splitLength) throws FileNotFoundException, ZipException {
        if (splitLength >= 0L && splitLength < 65536L) {
            throw new ZipException("split length less than minimum allowed split length of 65536 Bytes");
        }
        this.raf = new RandomAccessFile(file, RandomAccessFileMode.WRITE.getValue());
        this.splitLength = splitLength;
        this.zipFile = file;
        this.currSplitFileCounter = 0;
        this.bytesWrittenForThisPart = 0L;
    }

    @Override
    public void write(int b) throws IOException {
        this.write(new byte[]{(byte)b});
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (len <= 0) {
            return;
        }
        if (this.splitLength == -1L) {
            this.raf.write(b, off, len);
            this.bytesWrittenForThisPart += (long)len;
            return;
        }
        if (this.bytesWrittenForThisPart >= this.splitLength) {
            this.startNextSplitFile();
            this.raf.write(b, off, len);
            this.bytesWrittenForThisPart = len;
        } else if (this.bytesWrittenForThisPart + (long)len > this.splitLength) {
            if (this.isHeaderData(b)) {
                this.startNextSplitFile();
                this.raf.write(b, off, len);
                this.bytesWrittenForThisPart = len;
            } else {
                this.raf.write(b, off, (int)(this.splitLength - this.bytesWrittenForThisPart));
                this.startNextSplitFile();
                this.raf.write(b, off + (int)(this.splitLength - this.bytesWrittenForThisPart), (int)((long)len - (this.splitLength - this.bytesWrittenForThisPart)));
                this.bytesWrittenForThisPart = (long)len - (this.splitLength - this.bytesWrittenForThisPart);
            }
        } else {
            this.raf.write(b, off, len);
            this.bytesWrittenForThisPart += (long)len;
        }
    }

    private void startNextSplitFile() throws IOException {
        String zipFileWithoutExt = FileUtils.getZipFileNameWithoutExtension(this.zipFile.getName());
        String zipFileName = this.zipFile.getAbsolutePath();
        String parentPath = this.zipFile.getParent() == null ? "" : this.zipFile.getParent() + System.getProperty("file.separator");
        String fileExtension = ".z0" + (this.currSplitFileCounter + 1);
        if (this.currSplitFileCounter >= 9) {
            fileExtension = ".z" + (this.currSplitFileCounter + 1);
        }
        File currSplitFile = new File(parentPath + zipFileWithoutExt + fileExtension);
        this.raf.close();
        if (currSplitFile.exists()) {
            throw new IOException("split file: " + currSplitFile.getName() + " already exists in the current directory, cannot rename this file");
        }
        if (!this.zipFile.renameTo(currSplitFile)) {
            throw new IOException("cannot rename newly created split file");
        }
        this.zipFile = new File(zipFileName);
        this.raf = new RandomAccessFile(this.zipFile, RandomAccessFileMode.WRITE.getValue());
        ++this.currSplitFileCounter;
    }

    private boolean isHeaderData(byte[] buff) {
        int signature = this.rawIO.readIntLittleEndian(buff);
        for (HeaderSignature headerSignature : HeaderSignature.values()) {
            if (headerSignature == HeaderSignature.SPLIT_ZIP || headerSignature.getValue() != (long)signature) continue;
            return true;
        }
        return false;
    }

    public boolean checkBufferSizeAndStartNextSplitFile(int bufferSize) throws ZipException {
        if (bufferSize < 0) {
            throw new ZipException("negative buffersize for checkBufferSizeAndStartNextSplitFile");
        }
        if (!this.isBufferSizeFitForCurrSplitFile(bufferSize)) {
            try {
                this.startNextSplitFile();
                this.bytesWrittenForThisPart = 0L;
                return true;
            }
            catch (IOException e) {
                throw new ZipException(e);
            }
        }
        return false;
    }

    private boolean isBufferSizeFitForCurrSplitFile(int bufferSize) {
        if (this.splitLength >= 65536L) {
            return this.bytesWrittenForThisPart + (long)bufferSize <= this.splitLength;
        }
        return true;
    }

    public void seek(long pos) throws IOException {
        this.raf.seek(pos);
    }

    public int skipBytes(int n) throws IOException {
        return this.raf.skipBytes(n);
    }

    @Override
    public void close() throws IOException {
        this.raf.close();
    }

    @Override
    public long getFilePointer() throws IOException {
        return this.raf.getFilePointer();
    }

    public boolean isSplitZipFile() {
        return this.splitLength != -1L;
    }

    public long getSplitLength() {
        return this.splitLength;
    }

    @Override
    public int getCurrentSplitFileCounter() {
        return this.currSplitFileCounter;
    }
}

