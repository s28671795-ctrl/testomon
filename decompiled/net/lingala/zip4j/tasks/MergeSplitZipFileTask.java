/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.model.EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AbstractZipTaskParameters;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.RawIO;

public class MergeSplitZipFileTask
extends AsyncZipTask<MergeSplitZipFileTaskParameters> {
    private final ZipModel zipModel;
    private final RawIO rawIO = new RawIO();

    public MergeSplitZipFileTask(ZipModel zipModel, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
    }

    @Override
    protected void executeTask(MergeSplitZipFileTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
        if (!this.zipModel.isSplitArchive()) {
            ZipException e = new ZipException("archive not a split zip file");
            progressMonitor.endProgressMonitor(e);
            throw e;
        }
        try (FileOutputStream outputStream2 = new FileOutputStream(taskParameters.outputZipFile);){
            long totalBytesWritten = 0L;
            int totalNumberOfSplitFiles = this.zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk();
            if (totalNumberOfSplitFiles <= 0) {
                throw new ZipException("zip archive not a split zip file");
            }
            int splitSignatureOverhead = 0;
            for (int i = 0; i <= totalNumberOfSplitFiles; ++i) {
                try (RandomAccessFile randomAccessFile = this.createSplitZipFileStream(this.zipModel, i);){
                    int start2 = 0;
                    long end = randomAccessFile.length();
                    if (i == 0) {
                        if ((long)this.rawIO.readIntLittleEndian(randomAccessFile) == HeaderSignature.SPLIT_ZIP.getValue()) {
                            splitSignatureOverhead = 4;
                            start2 = 4;
                        } else {
                            randomAccessFile.seek(0L);
                        }
                    }
                    if (i == totalNumberOfSplitFiles) {
                        end = this.zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory();
                    }
                    FileUtils.copyFile(randomAccessFile, outputStream2, start2, end, progressMonitor, taskParameters.zip4jConfig.getBufferSize());
                    this.updateFileHeaderOffsetsForIndex(this.zipModel.getCentralDirectory().getFileHeaders(), i == 0 ? 0L : (totalBytesWritten += end - (long)start2), i, splitSignatureOverhead);
                    this.verifyIfTaskIsCancelled();
                    continue;
                }
            }
            this.updateHeadersForMergeSplitFileAction(this.zipModel, totalBytesWritten, outputStream2, taskParameters.zip4jConfig.getCharset());
            progressMonitor.endProgressMonitor();
        }
        catch (CloneNotSupportedException e) {
            throw new ZipException(e);
        }
    }

    @Override
    protected long calculateTotalWork(MergeSplitZipFileTaskParameters taskParameters) {
        if (!this.zipModel.isSplitArchive()) {
            return 0L;
        }
        long totalSize = 0L;
        for (int i = 0; i <= this.zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk(); ++i) {
            totalSize += this.getNextSplitZipFile(this.zipModel, i).length();
        }
        return totalSize;
    }

    private void updateFileHeaderOffsetsForIndex(List<FileHeader> fileHeaders, long offsetToAdd, int index, int splitSignatureOverhead) {
        for (FileHeader fileHeader : fileHeaders) {
            if (fileHeader.getDiskNumberStart() != index) continue;
            fileHeader.setOffsetLocalHeader(fileHeader.getOffsetLocalHeader() + offsetToAdd - (long)splitSignatureOverhead);
            fileHeader.setDiskNumberStart(0);
        }
    }

    private File getNextSplitZipFile(ZipModel zipModel, int partNumber) {
        if (partNumber == zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk()) {
            return zipModel.getZipFile();
        }
        String splitZipExtension = ".z0";
        if (partNumber >= 9) {
            splitZipExtension = ".z";
        }
        String rootZipFile = zipModel.getZipFile().getPath();
        String nextSplitZipFileName = zipModel.getZipFile().getPath().substring(0, rootZipFile.lastIndexOf(".")) + splitZipExtension + (partNumber + 1);
        return new File(nextSplitZipFileName);
    }

    private RandomAccessFile createSplitZipFileStream(ZipModel zipModel, int partNumber) throws FileNotFoundException {
        File splitFile = this.getNextSplitZipFile(zipModel, partNumber);
        return new RandomAccessFile(splitFile, RandomAccessFileMode.READ.getValue());
    }

    private void updateHeadersForMergeSplitFileAction(ZipModel zipModel, long totalBytesWritten, OutputStream outputStream2, Charset charset) throws IOException, CloneNotSupportedException {
        ZipModel newZipModel = (ZipModel)zipModel.clone();
        newZipModel.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(totalBytesWritten);
        this.updateSplitZipModel(newZipModel, totalBytesWritten);
        HeaderWriter headerWriter = new HeaderWriter();
        headerWriter.finalizeZipFileWithoutValidations(newZipModel, outputStream2, charset);
    }

    private void updateSplitZipModel(ZipModel zipModel, long totalFileSize) {
        zipModel.setSplitArchive(false);
        this.updateSplitEndCentralDirectory(zipModel);
        if (zipModel.isZip64Format()) {
            this.updateSplitZip64EndCentralDirLocator(zipModel, totalFileSize);
            this.updateSplitZip64EndCentralDirRec(zipModel, totalFileSize);
        }
    }

    private void updateSplitEndCentralDirectory(ZipModel zipModel) {
        int numberOfFileHeaders = zipModel.getCentralDirectory().getFileHeaders().size();
        EndOfCentralDirectoryRecord endOfCentralDirectoryRecord = zipModel.getEndOfCentralDirectoryRecord();
        endOfCentralDirectoryRecord.setNumberOfThisDisk(0);
        endOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDir(0);
        endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(numberOfFileHeaders);
        endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(numberOfFileHeaders);
    }

    private void updateSplitZip64EndCentralDirLocator(ZipModel zipModel, long totalFileSize) {
        if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
            return;
        }
        Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator = zipModel.getZip64EndOfCentralDirectoryLocator();
        zip64EndOfCentralDirectoryLocator.setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(0);
        zip64EndOfCentralDirectoryLocator.setOffsetZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryLocator.getOffsetZip64EndOfCentralDirectoryRecord() + totalFileSize);
        zip64EndOfCentralDirectoryLocator.setTotalNumberOfDiscs(1);
    }

    private void updateSplitZip64EndCentralDirRec(ZipModel zipModel, long totalFileSize) {
        if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
            return;
        }
        Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = zipModel.getZip64EndOfCentralDirectoryRecord();
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDisk(0);
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDirectory(0);
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(zipModel.getEndOfCentralDirectoryRecord().getTotalNumberOfEntriesInCentralDirectory());
        zip64EndOfCentralDirectoryRecord.setOffsetStartCentralDirectoryWRTStartDiskNumber(zip64EndOfCentralDirectoryRecord.getOffsetStartCentralDirectoryWRTStartDiskNumber() + totalFileSize);
    }

    @Override
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.MERGE_ZIP_FILES;
    }

    public static class MergeSplitZipFileTaskParameters
    extends AbstractZipTaskParameters {
        private File outputZipFile;

        public MergeSplitZipFileTaskParameters(File outputZipFile, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.outputZipFile = outputZipFile;
        }
    }
}

