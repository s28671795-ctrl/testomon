/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class AddFilesToZipTask
extends AbstractAddFileToZipTask<AddFilesToZipTaskParameters> {
    public AddFilesToZipTask(ZipModel zipModel, char[] password, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, password, headerWriter, asyncTaskParameters);
    }

    @Override
    protected void executeTask(AddFilesToZipTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
        this.verifyZipParameters(taskParameters.zipParameters);
        List<File> filesToAdd = this.determineActualFilesToAdd(taskParameters);
        this.addFilesToZip(filesToAdd, progressMonitor, taskParameters.zipParameters, taskParameters.zip4jConfig);
    }

    @Override
    protected long calculateTotalWork(AddFilesToZipTaskParameters taskParameters) throws ZipException {
        return this.calculateWorkForFiles(taskParameters.filesToAdd, taskParameters.zipParameters);
    }

    private List<File> determineActualFilesToAdd(AddFilesToZipTaskParameters taskParameters) throws ZipException {
        ArrayList<File> filesToAdd = new ArrayList<File>();
        for (File inputFile : taskParameters.filesToAdd) {
            filesToAdd.add(inputFile);
            boolean isSymLink = FileUtils.isSymbolicLink(inputFile);
            ZipParameters.SymbolicLinkAction symbolicLinkAction = taskParameters.zipParameters.getSymbolicLinkAction();
            if (!isSymLink || ZipParameters.SymbolicLinkAction.INCLUDE_LINK_ONLY.equals((Object)symbolicLinkAction)) continue;
            filesToAdd.addAll(FileUtils.getFilesInDirectoryRecursive(inputFile, taskParameters.zipParameters));
        }
        return filesToAdd;
    }

    @Override
    protected ProgressMonitor.Task getTask() {
        return super.getTask();
    }

    public static class AddFilesToZipTaskParameters
    extends AbstractZipTaskParameters {
        private final List<File> filesToAdd;
        private final ZipParameters zipParameters;

        public AddFilesToZipTaskParameters(List<File> filesToAdd, ZipParameters zipParameters, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.filesToAdd = filesToAdd;
            this.zipParameters = zipParameters;
        }
    }
}

