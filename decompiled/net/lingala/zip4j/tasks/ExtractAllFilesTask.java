/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.IOException;
import java.io.InputStream;
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
import net.lingala.zip4j.util.UnzipUtil;

public class ExtractAllFilesTask
extends AbstractExtractFileTask<ExtractAllFilesTaskParameters> {
    private final char[] password;
    private SplitFileInputStream splitInputStream;

    public ExtractAllFilesTask(ZipModel zipModel, char[] password, UnzipParameters unzipParameters, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, unzipParameters, asyncTaskParameters);
        this.password = password;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void executeTask(ExtractAllFilesTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
        try (ZipInputStream zipInputStream = this.prepareZipInputStream(taskParameters.zip4jConfig);){
            for (FileHeader fileHeader : this.getZipModel().getCentralDirectory().getFileHeaders()) {
                if (fileHeader.getFileName().startsWith("__MACOSX")) {
                    progressMonitor.updateWorkCompleted(fileHeader.getUncompressedSize());
                    continue;
                }
                this.splitInputStream.prepareExtractionForFileHeader(fileHeader);
                byte[] readBuff = new byte[taskParameters.zip4jConfig.getBufferSize()];
                this.extractFile(zipInputStream, fileHeader, taskParameters.outputPath, null, progressMonitor, readBuff);
                this.verifyIfTaskIsCancelled();
            }
        }
        finally {
            if (this.splitInputStream != null) {
                this.splitInputStream.close();
            }
        }
    }

    @Override
    protected long calculateTotalWork(ExtractAllFilesTaskParameters taskParameters) {
        return HeaderUtil.getTotalUncompressedSizeOfAllFileHeaders(this.getZipModel().getCentralDirectory().getFileHeaders());
    }

    private ZipInputStream prepareZipInputStream(Zip4jConfig zip4jConfig) throws IOException {
        this.splitInputStream = UnzipUtil.createSplitInputStream(this.getZipModel());
        FileHeader fileHeader = this.getFirstFileHeader(this.getZipModel());
        if (fileHeader != null) {
            this.splitInputStream.prepareExtractionForFileHeader(fileHeader);
        }
        return new ZipInputStream((InputStream)this.splitInputStream, this.password, zip4jConfig);
    }

    private FileHeader getFirstFileHeader(ZipModel zipModel) {
        if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null || zipModel.getCentralDirectory().getFileHeaders().size() == 0) {
            return null;
        }
        return zipModel.getCentralDirectory().getFileHeaders().get(0);
    }

    public static class ExtractAllFilesTaskParameters
    extends AbstractZipTaskParameters {
        private final String outputPath;

        public ExtractAllFilesTaskParameters(String outputPath, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.outputPath = outputPath;
        }
    }
}

