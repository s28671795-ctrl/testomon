/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.tasks.RemoveFilesFromZipTask;
import net.lingala.zip4j.util.BitUtils;
import net.lingala.zip4j.util.CrcUtil;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.Zip4jUtil;

public abstract class AbstractAddFileToZipTask<T>
extends AsyncZipTask<T> {
    private final ZipModel zipModel;
    private final char[] password;
    private final HeaderWriter headerWriter;

    AbstractAddFileToZipTask(ZipModel zipModel, char[] password, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
        this.password = password;
        this.headerWriter = headerWriter;
    }

    void addFilesToZip(List<File> filesToAdd, ProgressMonitor progressMonitor, ZipParameters zipParameters, Zip4jConfig zip4jConfig) throws IOException {
        FileUtils.assertFilesExist(filesToAdd, zipParameters.getSymbolicLinkAction());
        byte[] readBuff = new byte[zip4jConfig.getBufferSize()];
        List<File> updatedFilesToAdd = this.removeFilesIfExists(filesToAdd, zipParameters, progressMonitor, zip4jConfig);
        try (SplitOutputStream splitOutputStream = new SplitOutputStream(this.zipModel.getZipFile(), this.zipModel.getSplitLength());
             ZipOutputStream zipOutputStream = this.initializeOutputStream(splitOutputStream, zip4jConfig);){
            for (File fileToAdd : updatedFilesToAdd) {
                this.verifyIfTaskIsCancelled();
                ZipParameters clonedZipParameters = this.cloneAndAdjustZipParameters(zipParameters, fileToAdd, progressMonitor);
                progressMonitor.setFileName(fileToAdd.getAbsolutePath());
                if (FileUtils.isSymbolicLink(fileToAdd) && this.addSymlink(clonedZipParameters)) {
                    this.addSymlinkToZip(fileToAdd, zipOutputStream, clonedZipParameters, splitOutputStream);
                    if (ZipParameters.SymbolicLinkAction.INCLUDE_LINK_ONLY.equals((Object)clonedZipParameters.getSymbolicLinkAction())) continue;
                }
                this.addFileToZip(fileToAdd, zipOutputStream, clonedZipParameters, splitOutputStream, progressMonitor, readBuff);
            }
        }
    }

    private void addSymlinkToZip(File fileToAdd, ZipOutputStream zipOutputStream, ZipParameters zipParameters, SplitOutputStream splitOutputStream) throws IOException {
        ZipParameters clonedZipParameters = new ZipParameters(zipParameters);
        clonedZipParameters.setFileNameInZip(this.replaceFileNameInZip(zipParameters.getFileNameInZip(), fileToAdd.getName()));
        clonedZipParameters.setEncryptFiles(false);
        clonedZipParameters.setCompressionMethod(CompressionMethod.STORE);
        zipOutputStream.putNextEntry(clonedZipParameters);
        String symLinkTarget = FileUtils.readSymbolicLink(fileToAdd);
        zipOutputStream.write(symLinkTarget.getBytes());
        this.closeEntry(zipOutputStream, splitOutputStream, fileToAdd, true);
    }

    private void addFileToZip(File fileToAdd, ZipOutputStream zipOutputStream, ZipParameters zipParameters, SplitOutputStream splitOutputStream, ProgressMonitor progressMonitor, byte[] readBuff) throws IOException {
        zipOutputStream.putNextEntry(zipParameters);
        if (fileToAdd.exists() && !fileToAdd.isDirectory()) {
            try (FileInputStream inputStream2 = new FileInputStream(fileToAdd);){
                int readLen;
                while ((readLen = ((InputStream)inputStream2).read(readBuff)) != -1) {
                    zipOutputStream.write(readBuff, 0, readLen);
                    progressMonitor.updateWorkCompleted(readLen);
                    this.verifyIfTaskIsCancelled();
                }
            }
        }
        this.closeEntry(zipOutputStream, splitOutputStream, fileToAdd, false);
    }

    private void closeEntry(ZipOutputStream zipOutputStream, SplitOutputStream splitOutputStream, File fileToAdd, boolean isSymlink) throws IOException {
        FileHeader fileHeader = zipOutputStream.closeEntry();
        byte[] fileAttributes = FileUtils.getFileAttributes(fileToAdd);
        if (!isSymlink) {
            fileAttributes[3] = BitUtils.unsetBit(fileAttributes[3], 5);
        }
        fileHeader.setExternalFileAttributes(fileAttributes);
        this.updateLocalFileHeader(fileHeader, splitOutputStream);
    }

    long calculateWorkForFiles(List<File> filesToAdd, ZipParameters zipParameters) throws ZipException {
        long totalWork = 0L;
        for (File fileToAdd : filesToAdd) {
            if (!fileToAdd.exists()) continue;
            totalWork = zipParameters.isEncryptFiles() && zipParameters.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD ? (totalWork += fileToAdd.length() * 2L) : (totalWork += fileToAdd.length());
            String relativeFileName = FileUtils.getRelativeFileName(fileToAdd, zipParameters);
            FileHeader fileHeader = HeaderUtil.getFileHeader(this.getZipModel(), relativeFileName);
            if (fileHeader == null) continue;
            totalWork += this.getZipModel().getZipFile().length() - fileHeader.getCompressedSize();
        }
        return totalWork;
    }

    ZipOutputStream initializeOutputStream(SplitOutputStream splitOutputStream, Zip4jConfig zip4jConfig) throws IOException {
        if (this.zipModel.getZipFile().exists()) {
            splitOutputStream.seek(HeaderUtil.getOffsetStartOfCentralDirectory(this.zipModel));
        }
        return new ZipOutputStream(splitOutputStream, this.password, zip4jConfig, this.zipModel);
    }

    void verifyZipParameters(ZipParameters parameters) throws ZipException {
        if (parameters == null) {
            throw new ZipException("cannot validate zip parameters");
        }
        if (parameters.getCompressionMethod() != CompressionMethod.STORE && parameters.getCompressionMethod() != CompressionMethod.DEFLATE) {
            throw new ZipException("unsupported compression type");
        }
        if (parameters.isEncryptFiles()) {
            if (parameters.getEncryptionMethod() == EncryptionMethod.NONE) {
                throw new ZipException("Encryption method has to be set, when encrypt files flag is set");
            }
            if (this.password == null || this.password.length <= 0) {
                throw new ZipException("input password is empty or null");
            }
        } else {
            parameters.setEncryptionMethod(EncryptionMethod.NONE);
        }
    }

    void updateLocalFileHeader(FileHeader fileHeader, SplitOutputStream splitOutputStream) throws IOException {
        this.headerWriter.updateLocalFileHeader(fileHeader, this.getZipModel(), splitOutputStream);
    }

    private ZipParameters cloneAndAdjustZipParameters(ZipParameters zipParameters, File fileToAdd, ProgressMonitor progressMonitor) throws IOException {
        ZipParameters clonedZipParameters = new ZipParameters(zipParameters);
        if (fileToAdd.isDirectory()) {
            clonedZipParameters.setEntrySize(0L);
        } else {
            clonedZipParameters.setEntrySize(fileToAdd.length());
        }
        if (zipParameters.getLastModifiedFileTime() <= 0L) {
            clonedZipParameters.setLastModifiedFileTime(fileToAdd.lastModified());
        }
        clonedZipParameters.setWriteExtendedLocalFileHeader(false);
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(zipParameters.getFileNameInZip())) {
            String relativeFileName = FileUtils.getRelativeFileName(fileToAdd, zipParameters);
            clonedZipParameters.setFileNameInZip(relativeFileName);
        }
        if (fileToAdd.isDirectory()) {
            clonedZipParameters.setCompressionMethod(CompressionMethod.STORE);
            clonedZipParameters.setEncryptionMethod(EncryptionMethod.NONE);
            clonedZipParameters.setEncryptFiles(false);
        } else {
            if (clonedZipParameters.isEncryptFiles() && clonedZipParameters.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD) {
                progressMonitor.setCurrentTask(ProgressMonitor.Task.CALCULATE_CRC);
                clonedZipParameters.setEntryCRC(CrcUtil.computeFileCrc(fileToAdd, progressMonitor));
                progressMonitor.setCurrentTask(ProgressMonitor.Task.ADD_ENTRY);
            }
            if (fileToAdd.length() == 0L) {
                clonedZipParameters.setCompressionMethod(CompressionMethod.STORE);
            }
        }
        return clonedZipParameters;
    }

    private List<File> removeFilesIfExists(List<File> files, ZipParameters zipParameters, ProgressMonitor progressMonitor, Zip4jConfig zip4jConfig) throws ZipException {
        ArrayList<File> filesToAdd = new ArrayList<File>(files);
        if (!this.zipModel.getZipFile().exists()) {
            return filesToAdd;
        }
        for (File file : files) {
            String fileName;
            FileHeader fileHeader;
            if (!Zip4jUtil.isStringNotNullAndNotEmpty(file.getName())) {
                filesToAdd.remove(file);
            }
            if ((fileHeader = HeaderUtil.getFileHeader(this.zipModel, fileName = FileUtils.getRelativeFileName(file, zipParameters))) == null) continue;
            if (zipParameters.isOverrideExistingFilesInZip()) {
                progressMonitor.setCurrentTask(ProgressMonitor.Task.REMOVE_ENTRY);
                this.removeFile(fileHeader, progressMonitor, zip4jConfig);
                this.verifyIfTaskIsCancelled();
                progressMonitor.setCurrentTask(ProgressMonitor.Task.ADD_ENTRY);
                continue;
            }
            filesToAdd.remove(file);
        }
        return filesToAdd;
    }

    void removeFile(FileHeader fileHeader, ProgressMonitor progressMonitor, Zip4jConfig zip4jConfig) throws ZipException {
        AsyncZipTask.AsyncTaskParameters asyncTaskParameters = new AsyncZipTask.AsyncTaskParameters(null, false, progressMonitor);
        RemoveFilesFromZipTask removeFilesFromZipTask = new RemoveFilesFromZipTask(this.zipModel, this.headerWriter, asyncTaskParameters);
        RemoveFilesFromZipTask.RemoveFilesFromZipTaskParameters parameters = new RemoveFilesFromZipTask.RemoveFilesFromZipTaskParameters(Collections.singletonList(fileHeader.getFileName()), zip4jConfig);
        removeFilesFromZipTask.execute(parameters);
    }

    private String replaceFileNameInZip(String fileInZipWithPath, String newFileName) {
        if (fileInZipWithPath.contains("/")) {
            return fileInZipWithPath.substring(0, fileInZipWithPath.lastIndexOf("/") + 1) + newFileName;
        }
        return newFileName;
    }

    private boolean addSymlink(ZipParameters zipParameters) {
        return ZipParameters.SymbolicLinkAction.INCLUDE_LINK_ONLY.equals((Object)zipParameters.getSymbolicLinkAction()) || ZipParameters.SymbolicLinkAction.INCLUDE_LINK_AND_LINKED_FILE.equals((Object)zipParameters.getSymbolicLinkAction());
    }

    @Override
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.ADD_ENTRY;
    }

    protected ZipModel getZipModel() {
        return this.zipModel;
    }
}

