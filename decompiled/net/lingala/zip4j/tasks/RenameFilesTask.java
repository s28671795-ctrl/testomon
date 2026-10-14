/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AbstractModifyFileTask;
import net.lingala.zip4j.tasks.AbstractZipTaskParameters;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.util.RawIO;
import net.lingala.zip4j.util.Zip4jUtil;

public class RenameFilesTask
extends AbstractModifyFileTask<RenameFilesTaskParameters> {
    private final ZipModel zipModel;
    private final HeaderWriter headerWriter;
    private final RawIO rawIO;

    public RenameFilesTask(ZipModel zipModel, HeaderWriter headerWriter, RawIO rawIO, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
        this.headerWriter = headerWriter;
        this.rawIO = rawIO;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void executeTask(RenameFilesTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
        Map<String, String> fileNamesMap = this.filterNonExistingEntriesAndAddSeparatorIfNeeded(taskParameters.fileNamesMap);
        if (fileNamesMap.size() == 0) {
            return;
        }
        File temporaryFile = this.getTemporaryFile(this.zipModel.getZipFile().getPath());
        boolean successFlag = false;
        try (RandomAccessFile inputStream2 = new RandomAccessFile(this.zipModel.getZipFile(), RandomAccessFileMode.WRITE.getValue());
             SplitOutputStream outputStream2 = new SplitOutputStream(temporaryFile);){
            long currentFileCopyPointer = 0L;
            Charset charset = taskParameters.zip4jConfig.getCharset();
            List<FileHeader> sortedFileHeaders = this.cloneAndSortFileHeadersByOffset(this.zipModel.getCentralDirectory().getFileHeaders());
            for (FileHeader fileHeader : sortedFileHeaders) {
                Map.Entry<String, String> fileNameMapForThisEntry = this.getCorrespondingEntryFromMap(fileHeader, fileNamesMap);
                progressMonitor.setFileName(fileHeader.getFileName());
                long lengthToCopy = this.getOffsetOfNextEntry(sortedFileHeaders, fileHeader, this.zipModel) - outputStream2.getFilePointer();
                if (fileNameMapForThisEntry == null) {
                    currentFileCopyPointer += this.copyFile(inputStream2, outputStream2, currentFileCopyPointer, lengthToCopy, progressMonitor, taskParameters.zip4jConfig.getBufferSize());
                } else {
                    String newFileName = this.getNewFileName(fileNameMapForThisEntry.getValue(), fileNameMapForThisEntry.getKey(), fileHeader.getFileName());
                    byte[] newFileNameBytes = HeaderUtil.getBytesFromString(newFileName, charset);
                    int headersOffset = newFileNameBytes.length - fileHeader.getFileNameLength();
                    currentFileCopyPointer = this.copyEntryAndChangeFileName(newFileNameBytes, fileHeader, currentFileCopyPointer, lengthToCopy, inputStream2, outputStream2, progressMonitor, taskParameters.zip4jConfig.getBufferSize());
                    this.updateHeadersInZipModel(sortedFileHeaders, fileHeader, newFileName, newFileNameBytes, headersOffset);
                }
                this.verifyIfTaskIsCancelled();
            }
            this.headerWriter.finalizeZipFile(this.zipModel, outputStream2, charset);
            successFlag = true;
        }
        finally {
            this.cleanupFile(successFlag, this.zipModel.getZipFile(), temporaryFile);
        }
    }

    @Override
    protected long calculateTotalWork(RenameFilesTaskParameters taskParameters) {
        return this.zipModel.getZipFile().length();
    }

    @Override
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.RENAME_FILE;
    }

    private long copyEntryAndChangeFileName(byte[] newFileNameBytes, FileHeader fileHeader, long start2, long totalLengthOfEntry, RandomAccessFile inputStream2, OutputStream outputStream2, ProgressMonitor progressMonitor, int bufferSize) throws IOException {
        long currentFileCopyPointer = start2;
        currentFileCopyPointer += this.copyFile(inputStream2, outputStream2, currentFileCopyPointer, 26L, progressMonitor, bufferSize);
        this.rawIO.writeShortLittleEndian(outputStream2, newFileNameBytes.length);
        currentFileCopyPointer += 2L;
        currentFileCopyPointer += this.copyFile(inputStream2, outputStream2, currentFileCopyPointer, 2L, progressMonitor, bufferSize);
        outputStream2.write(newFileNameBytes);
        long remainingLengthToCopy = totalLengthOfEntry - ((currentFileCopyPointer += (long)fileHeader.getFileNameLength()) - start2);
        currentFileCopyPointer += this.copyFile(inputStream2, outputStream2, currentFileCopyPointer, remainingLengthToCopy, progressMonitor, bufferSize);
        return currentFileCopyPointer;
    }

    private Map.Entry<String, String> getCorrespondingEntryFromMap(FileHeader fileHeaderToBeChecked, Map<String, String> fileNamesMap) {
        for (Map.Entry<String, String> fileHeaderToBeRenamed : fileNamesMap.entrySet()) {
            if (!fileHeaderToBeChecked.getFileName().startsWith(fileHeaderToBeRenamed.getKey())) continue;
            return fileHeaderToBeRenamed;
        }
        return null;
    }

    private void updateHeadersInZipModel(List<FileHeader> sortedFileHeaders, FileHeader fileHeader, String newFileName, byte[] newFileNameBytes, int headersOffset) throws ZipException {
        FileHeader fileHeaderToBeChanged = HeaderUtil.getFileHeader(this.zipModel, fileHeader.getFileName());
        if (fileHeaderToBeChanged == null) {
            throw new ZipException("could not find any header with name: " + fileHeader.getFileName());
        }
        fileHeaderToBeChanged.setFileName(newFileName);
        fileHeaderToBeChanged.setFileNameLength(newFileNameBytes.length);
        this.updateOffsetsForAllSubsequentFileHeaders(sortedFileHeaders, this.zipModel, fileHeaderToBeChanged, headersOffset);
        this.zipModel.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(this.zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory() + (long)headersOffset);
        if (this.zipModel.isZip64Format()) {
            this.zipModel.getZip64EndOfCentralDirectoryRecord().setOffsetStartCentralDirectoryWRTStartDiskNumber(this.zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber() + (long)headersOffset);
            this.zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(this.zipModel.getZip64EndOfCentralDirectoryLocator().getOffsetZip64EndOfCentralDirectoryRecord() + (long)headersOffset);
        }
    }

    private Map<String, String> filterNonExistingEntriesAndAddSeparatorIfNeeded(Map<String, String> inputFileNamesMap) throws ZipException {
        HashMap<String, String> fileNamesMapToBeChanged = new HashMap<String, String>();
        for (Map.Entry<String, String> allNamesToBeChanged : inputFileNamesMap.entrySet()) {
            FileHeader fileHeaderToBeChanged;
            if (!Zip4jUtil.isStringNotNullAndNotEmpty(allNamesToBeChanged.getKey()) || (fileHeaderToBeChanged = HeaderUtil.getFileHeader(this.zipModel, allNamesToBeChanged.getKey())) == null) continue;
            if (fileHeaderToBeChanged.isDirectory() && !allNamesToBeChanged.getValue().endsWith("/")) {
                fileNamesMapToBeChanged.put(allNamesToBeChanged.getKey(), allNamesToBeChanged.getValue() + "/");
                continue;
            }
            fileNamesMapToBeChanged.put(allNamesToBeChanged.getKey(), allNamesToBeChanged.getValue());
        }
        return fileNamesMapToBeChanged;
    }

    private String getNewFileName(String newFileName, String oldFileName, String fileNameFromHeaderToBeChanged) throws ZipException {
        if (fileNameFromHeaderToBeChanged.equals(oldFileName)) {
            return newFileName;
        }
        if (fileNameFromHeaderToBeChanged.startsWith(oldFileName)) {
            String fileNameWithoutOldName = fileNameFromHeaderToBeChanged.substring(oldFileName.length());
            return newFileName + fileNameWithoutOldName;
        }
        throw new ZipException("old file name was neither an exact match nor a partial match");
    }

    public static class RenameFilesTaskParameters
    extends AbstractZipTaskParameters {
        private final Map<String, String> fileNamesMap;

        public RenameFilesTaskParameters(Map<String, String> fileNamesMap, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.fileNamesMap = fileNamesMap;
        }
    }
}

