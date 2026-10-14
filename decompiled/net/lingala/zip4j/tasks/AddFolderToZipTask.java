/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.IOException;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AbstractAddFileToZipTask;
import net.lingala.zip4j.tasks.AbstractZipTaskParameters;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.util.FileUtils;

public class AddFolderToZipTask
extends AbstractAddFileToZipTask<AddFolderToZipTaskParameters> {
    public AddFolderToZipTask(ZipModel zipModel, char[] password, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, password, headerWriter, asyncTaskParameters);
    }

    @Override
    protected void executeTask(AddFolderToZipTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
        List<File> filesToAdd = this.getFilesToAdd(taskParameters);
        this.setDefaultFolderPath(taskParameters);
        this.addFilesToZip(filesToAdd, progressMonitor, taskParameters.zipParameters, taskParameters.zip4jConfig);
    }

    @Override
    protected long calculateTotalWork(AddFolderToZipTaskParameters taskParameters) throws ZipException {
        List<File> filesToAdd = this.getFilesToAdd(taskParameters);
        if (taskParameters.zipParameters.isIncludeRootFolder()) {
            filesToAdd.add(taskParameters.folderToAdd);
        }
        return this.calculateWorkForFiles(filesToAdd, taskParameters.zipParameters);
    }

    private void setDefaultFolderPath(AddFolderToZipTaskParameters taskParameters) throws IOException {
        File parentFile;
        File folderToAdd = taskParameters.folderToAdd;
        String rootFolderPath = taskParameters.zipParameters.isIncludeRootFolder() ? ((parentFile = folderToAdd.getCanonicalFile().getParentFile()) == null ? folderToAdd.getCanonicalPath() : folderToAdd.getCanonicalFile().getParentFile().getCanonicalPath()) : folderToAdd.getCanonicalPath();
        taskParameters.zipParameters.setDefaultFolderPath(rootFolderPath);
    }

    private List<File> getFilesToAdd(AddFolderToZipTaskParameters taskParameters) throws ZipException {
        List<File> filesToAdd = FileUtils.getFilesInDirectoryRecursive(taskParameters.folderToAdd, taskParameters.zipParameters);
        if (taskParameters.zipParameters.isIncludeRootFolder()) {
            filesToAdd.add(taskParameters.folderToAdd);
        }
        return filesToAdd;
    }

    public static class AddFolderToZipTaskParameters
    extends AbstractZipTaskParameters {
        private final File folderToAdd;
        private final ZipParameters zipParameters;

        public AddFolderToZipTaskParameters(File folderToAdd, ZipParameters zipParameters, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.folderToAdd = folderToAdd;
            this.zipParameters = zipParameters;
        }
    }
}

