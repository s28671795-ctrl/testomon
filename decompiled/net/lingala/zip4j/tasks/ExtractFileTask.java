/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.io.inputstream.SplitFileInputStream;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AbstractExtractFileTask;
import net.lingala.zip4j.tasks.AbstractZipTaskParameters;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.UnzipUtil;
import net.lingala.zip4j.util.Zip4jUtil;

public class ExtractFileTask
extends AbstractExtractFileTask<ExtractFileTaskParameters> {
    private char[] password;
    private SplitFileInputStream splitInputStream;

    public ExtractFileTask(ZipModel zipModel, char[] password, UnzipParameters unzipParameters, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, unzipParameters, asyncTaskParameters);
        this.password = password;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void executeTask(ExtractFileTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
        List<FileHeader> fileHeadersUnderDirectory = this.getFileHeadersToExtract(taskParameters.fileToExtract);
        try (ZipInputStream zipInputStream = this.createZipInputStream(taskParameters.zip4jConfig);){
            byte[] readBuff = new byte[taskParameters.zip4jConfig.getBufferSize()];
            for (FileHeader fileHeader : fileHeadersUnderDirectory) {
                this.splitInputStream.prepareExtractionForFileHeader(fileHeader);
                String newFileName = this.determineNewFileName(taskParameters.newFileName, taskParameters.fileToExtract, fileHeader);
                this.extractFile(zipInputStream, fileHeader, taskParameters.outputPath, newFileName, progressMonitor, readBuff);
            }
        }
        finally {
            if (this.splitInputStream != null) {
                this.splitInputStream.close();
            }
        }
    }

    @Override
    protected long calculateTotalWork(ExtractFileTaskParameters taskParameters) throws ZipException {
        List<FileHeader> fileHeadersUnderDirectory = this.getFileHeadersToExtract(taskParameters.fileToExtract);
        return HeaderUtil.getTotalUncompressedSizeOfAllFileHeaders(fileHeadersUnderDirectory);
    }

    private List<FileHeader> getFileHeadersToExtract(String fileNameToExtract) throws ZipException {
        if (!FileUtils.isZipEntryDirectory(fileNameToExtract)) {
            FileHeader fileHeader = HeaderUtil.getFileHeader(this.getZipModel(), fileNameToExtract);
            if (fileHeader == null) {
                throw new ZipException("No file found with name " + fileNameToExtract + " in zip file", ZipException.Type.FILE_NOT_FOUND);
            }
            return Collections.singletonList(fileHeader);
        }
        return HeaderUtil.getFileHeadersUnderDirectory(this.getZipModel().getCentralDirectory().getFileHeaders(), fileNameToExtract);
    }

    private ZipInputStream createZipInputStream(Zip4jConfig zip4jConfig) throws IOException {
        this.splitInputStream = UnzipUtil.createSplitInputStream(this.getZipModel());
        return new ZipInputStream((InputStream)this.splitInputStream, this.password, zip4jConfig);
    }

    private String determineNewFileName(String newFileName, String fileNameToExtract, FileHeader fileHeaderBeingExtracted) {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(newFileName)) {
            return newFileName;
        }
        if (!FileUtils.isZipEntryDirectory(fileNameToExtract)) {
            return newFileName;
        }
        String fileSeparator = "/";
        if (newFileName.endsWith("/")) {
            fileSeparator = "";
        }
        return fileHeaderBeingExtracted.getFileName().replaceFirst(fileNameToExtract, newFileName + fileSeparator);
    }

    public static class ExtractFileTaskParameters
    extends AbstractZipTaskParameters {
        private String outputPath;
        private String fileToExtract;
        private String newFileName;

        public ExtractFileTaskParameters(String outputPath, String fileToExtract, String newFileName, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.outputPath = outputPath;
            this.fileToExtract = fileToExtract;
            this.newFileName = newFileName;
        }
    }
}

