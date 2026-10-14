/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.IOException;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.model.EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AbstractZipTaskParameters;
import net.lingala.zip4j.tasks.AsyncZipTask;

public class SetCommentTask
extends AsyncZipTask<SetCommentTaskTaskParameters> {
    private final ZipModel zipModel;

    public SetCommentTask(ZipModel zipModel, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
    }

    @Override
    protected void executeTask(SetCommentTaskTaskParameters taskParameters, ProgressMonitor progressMonitor) throws IOException {
        if (taskParameters.comment == null) {
            throw new ZipException("comment is null, cannot update Zip file with comment");
        }
        EndOfCentralDirectoryRecord endOfCentralDirectoryRecord = this.zipModel.getEndOfCentralDirectoryRecord();
        endOfCentralDirectoryRecord.setComment(taskParameters.comment);
        try (SplitOutputStream outputStream2 = new SplitOutputStream(this.zipModel.getZipFile());){
            if (this.zipModel.isZip64Format()) {
                outputStream2.seek(this.zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber());
            } else {
                outputStream2.seek(endOfCentralDirectoryRecord.getOffsetOfStartOfCentralDirectory());
            }
            HeaderWriter headerWriter = new HeaderWriter();
            headerWriter.finalizeZipFileWithoutValidations(this.zipModel, outputStream2, taskParameters.zip4jConfig.getCharset());
        }
    }

    @Override
    protected long calculateTotalWork(SetCommentTaskTaskParameters taskParameters) {
        return 0L;
    }

    @Override
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.SET_COMMENT;
    }

    public static class SetCommentTaskTaskParameters
    extends AbstractZipTaskParameters {
        private String comment;

        public SetCommentTaskTaskParameters(String comment, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.comment = comment;
        }
    }
}

