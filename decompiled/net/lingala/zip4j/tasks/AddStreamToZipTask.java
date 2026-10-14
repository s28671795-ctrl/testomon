/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.IOException;
import java.io.InputStream;
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
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AbstractAddFileToZipTask;
import net.lingala.zip4j.tasks.AbstractZipTaskParameters;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.util.Zip4jUtil;

public class AddStreamToZipTask
extends AbstractAddFileToZipTask<AddStreamToZipTaskParameters> {
    public AddStreamToZipTask(ZipModel zipModel, char[] password, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, password, headerWriter, asyncTaskParameters);
    }

    @Override
    protected void executeTask(AddStreamToZipTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
        this.verifyZipParameters(taskParameters.zipParameters);
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(taskParameters.zipParameters.getFileNameInZip())) {
            throw new ZipException("fileNameInZip has to be set in zipParameters when adding stream");
        }
        this.removeFileIfExists(this.getZipModel(), taskParameters.zip4jConfig, taskParameters.zipParameters.getFileNameInZip(), progressMonitor);
        taskParameters.zipParameters.setWriteExtendedLocalFileHeader(true);
        if (taskParameters.zipParameters.getCompressionMethod().equals((Object)CompressionMethod.STORE)) {
            taskParameters.zipParameters.setEntrySize(0L);
        }
        try (SplitOutputStream splitOutputStream = new SplitOutputStream(this.getZipModel().getZipFile(), this.getZipModel().getSplitLength());
             ZipOutputStream zipOutputStream = this.initializeOutputStream(splitOutputStream, taskParameters.zip4jConfig);){
            FileHeader fileHeader;
            byte[] readBuff = new byte[taskParameters.zip4jConfig.getBufferSize()];
            ZipParameters zipParameters = taskParameters.zipParameters;
            zipOutputStream.putNextEntry(zipParameters);
            if (!zipParameters.getFileNameInZip().endsWith("/") && !zipParameters.getFileNameInZip().endsWith("\\")) {
                int readLen;
                while ((readLen = taskParameters.inputStream.read(readBuff)) != -1) {
                    zipOutputStream.write(readBuff, 0, readLen);
                }
            }
            if (CompressionMethod.STORE.equals((Object)Zip4jUtil.getCompressionMethod(fileHeader = zipOutputStream.closeEntry()))) {
                this.updateLocalFileHeader(fileHeader, splitOutputStream);
            }
        }
    }

    @Override
    protected long calculateTotalWork(AddStreamToZipTaskParameters taskParameters) {
        return 0L;
    }

    private void removeFileIfExists(ZipModel zipModel, Zip4jConfig zip4jConfig, String fileNameInZip, ProgressMonitor progressMonitor) throws ZipException {
        FileHeader fileHeader = HeaderUtil.getFileHeader(zipModel, fileNameInZip);
        if (fileHeader != null) {
            this.removeFile(fileHeader, progressMonitor, zip4jConfig);
        }
    }

    public static class AddStreamToZipTaskParameters
    extends AbstractZipTaskParameters {
        private final InputStream inputStream;
        private final ZipParameters zipParameters;

        public AddStreamToZipTaskParameters(InputStream inputStream2, ZipParameters zipParameters, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.inputStream = inputStream2;
            this.zipParameters = zipParameters;
        }
    }
}

