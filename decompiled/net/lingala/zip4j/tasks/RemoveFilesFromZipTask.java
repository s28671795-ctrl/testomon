/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.model.EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AbstractModifyFileTask;
import net.lingala.zip4j.tasks.AbstractZipTaskParameters;
import net.lingala.zip4j.tasks.AsyncZipTask;

public class RemoveFilesFromZipTask
extends AbstractModifyFileTask<RemoveFilesFromZipTaskParameters> {
    private final ZipModel zipModel;
    private final HeaderWriter headerWriter;

    public RemoveFilesFromZipTask(ZipModel zipModel, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
        this.headerWriter = headerWriter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void executeTask(RemoveFilesFromZipTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
        if (this.zipModel.isSplitArchive()) {
            throw new ZipException("This is a split archive. Zip file format does not allow updating split/spanned files");
        }
        List<String> entriesToRemove = this.filterNonExistingEntries(taskParameters.filesToRemove);
        if (entriesToRemove.isEmpty()) {
            return;
        }
        File temporaryZipFile = this.getTemporaryFile(this.zipModel.getZipFile().getPath());
        boolean successFlag = false;
        try (SplitOutputStream outputStream2 = new SplitOutputStream(temporaryZipFile);
             RandomAccessFile inputStream2 = new RandomAccessFile(this.zipModel.getZipFile(), RandomAccessFileMode.READ.getValue());){
            long currentFileCopyPointer = 0L;
            List<FileHeader> sortedFileHeaders = this.cloneAndSortFileHeadersByOffset(this.zipModel.getCentralDirectory().getFileHeaders());
            for (FileHeader fileHeader : sortedFileHeaders) {
                long lengthOfCurrentEntry = this.getOffsetOfNextEntry(sortedFileHeaders, fileHeader, this.zipModel) - outputStream2.getFilePointer();
                if (this.shouldEntryBeRemoved(fileHeader, entriesToRemove)) {
                    this.updateHeaders(sortedFileHeaders, fileHeader, lengthOfCurrentEntry);
                    if (!this.zipModel.getCentralDirectory().getFileHeaders().remove(fileHeader)) {
                        throw new ZipException("Could not remove entry from list of central directory headers");
                    }
                    currentFileCopyPointer += lengthOfCurrentEntry;
                } else {
                    currentFileCopyPointer += super.copyFile(inputStream2, outputStream2, currentFileCopyPointer, lengthOfCurrentEntry, progressMonitor, taskParameters.zip4jConfig.getBufferSize());
                }
                this.verifyIfTaskIsCancelled();
            }
            this.headerWriter.finalizeZipFile(this.zipModel, outputStream2, taskParameters.zip4jConfig.getCharset());
            successFlag = true;
        }
        finally {
            this.cleanupFile(successFlag, this.zipModel.getZipFile(), temporaryZipFile);
        }
    }

    @Override
    protected long calculateTotalWork(RemoveFilesFromZipTaskParameters taskParameters) {
        return this.zipModel.getZipFile().length();
    }

    private List<String> filterNonExistingEntries(List<String> filesToRemove) throws ZipException {
        ArrayList<String> filteredFilesToRemove = new ArrayList<String>();
        for (String fileToRemove : filesToRemove) {
            if (HeaderUtil.getFileHeader(this.zipModel, fileToRemove) == null) continue;
            filteredFilesToRemove.add(fileToRemove);
        }
        return filteredFilesToRemove;
    }

    private boolean shouldEntryBeRemoved(FileHeader fileHeaderToBeChecked, List<String> fileNamesToBeRemoved) {
        for (String fileNameToBeRemoved : fileNamesToBeRemoved) {
            if (fileNameToBeRemoved.endsWith("/") && fileHeaderToBeChecked.getFileName().startsWith(fileNameToBeRemoved)) {
                return true;
            }
            if (!fileHeaderToBeChecked.getFileName().equals(fileNameToBeRemoved)) continue;
            return true;
        }
        return false;
    }

    private void updateHeaders(List<FileHeader> sortedFileHeaders, FileHeader fileHeaderThatWasRemoved, long offsetToSubtract) throws ZipException {
        this.updateOffsetsForAllSubsequentFileHeaders(sortedFileHeaders, this.zipModel, fileHeaderThatWasRemoved, this.negate(offsetToSubtract));
        EndOfCentralDirectoryRecord endOfCentralDirectoryRecord = this.zipModel.getEndOfCentralDirectoryRecord();
        endOfCentralDirectoryRecord.setOffsetOfStartOfCentralDirectory(endOfCentralDirectoryRecord.getOffsetOfStartOfCentralDirectory() - offsetToSubtract);
        endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(endOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectory() - 1);
        if (endOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectoryOnThisDisk() > 0) {
            endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(endOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectoryOnThisDisk() - 1);
        }
        if (this.zipModel.isZip64Format()) {
            this.zipModel.getZip64EndOfCentralDirectoryRecord().setOffsetStartCentralDirectoryWRTStartDiskNumber(this.zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber() - offsetToSubtract);
            this.zipModel.getZip64EndOfCentralDirectoryRecord().setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(this.zipModel.getZip64EndOfCentralDirectoryRecord().getTotalNumberOfEntriesInCentralDirectory() - 1L);
            this.zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(this.zipModel.getZip64EndOfCentralDirectoryLocator().getOffsetZip64EndOfCentralDirectoryRecord() - offsetToSubtract);
        }
    }

    private long negate(long val) {
        if (val == Long.MIN_VALUE) {
            throw new ArithmeticException("long overflow");
        }
        return -val;
    }

    @Override
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.REMOVE_ENTRY;
    }

    public static class RemoveFilesFromZipTaskParameters
    extends AbstractZipTaskParameters {
        private final List<String> filesToRemove;

        public RemoveFilesFromZipTaskParameters(List<String> filesToRemove, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.filesToRemove = filesToRemove;
        }
    }
}

