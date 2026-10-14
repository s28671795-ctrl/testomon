/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.tasks;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

public abstract class AsyncZipTask<T> {
    private final ProgressMonitor progressMonitor;
    private final boolean runInThread;
    private final ExecutorService executorService;

    public AsyncZipTask(AsyncTaskParameters asyncTaskParameters) {
        this.progressMonitor = asyncTaskParameters.progressMonitor;
        this.runInThread = asyncTaskParameters.runInThread;
        this.executorService = asyncTaskParameters.executorService;
    }

    public void execute(final T taskParameters) throws ZipException {
        if (this.runInThread && ProgressMonitor.State.BUSY.equals((Object)this.progressMonitor.getState())) {
            throw new ZipException("invalid operation - Zip4j is in busy state");
        }
        this.initProgressMonitor();
        if (this.runInThread) {
            long totalWorkToBeDone = this.calculateTotalWork(taskParameters);
            this.progressMonitor.setTotalWork(totalWorkToBeDone);
            this.executorService.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        AsyncZipTask.this.performTaskWithErrorHandling(taskParameters, AsyncZipTask.this.progressMonitor);
                    }
                    catch (ZipException zipException) {
                    }
                    finally {
                        AsyncZipTask.this.executorService.shutdown();
                    }
                }
            });
        } else {
            this.performTaskWithErrorHandling(taskParameters, this.progressMonitor);
        }
    }

    private void performTaskWithErrorHandling(T taskParameters, ProgressMonitor progressMonitor) throws ZipException {
        try {
            this.executeTask(taskParameters, progressMonitor);
            progressMonitor.endProgressMonitor();
        }
        catch (ZipException e) {
            progressMonitor.endProgressMonitor(e);
            throw e;
        }
        catch (Exception e) {
            progressMonitor.endProgressMonitor(e);
            throw new ZipException(e);
        }
    }

    protected void verifyIfTaskIsCancelled() throws ZipException {
        if (!this.progressMonitor.isCancelAllTasks()) {
            return;
        }
        this.progressMonitor.setResult(ProgressMonitor.Result.CANCELLED);
        this.progressMonitor.setState(ProgressMonitor.State.READY);
        throw new ZipException("Task cancelled", ZipException.Type.TASK_CANCELLED_EXCEPTION);
    }

    private void initProgressMonitor() {
        this.progressMonitor.fullReset();
        this.progressMonitor.setState(ProgressMonitor.State.BUSY);
        this.progressMonitor.setCurrentTask(this.getTask());
    }

    protected abstract void executeTask(T var1, ProgressMonitor var2) throws IOException;

    protected abstract long calculateTotalWork(T var1) throws ZipException;

    protected abstract ProgressMonitor.Task getTask();

    public static class AsyncTaskParameters {
        private final ProgressMonitor progressMonitor;
        private final boolean runInThread;
        private final ExecutorService executorService;

        public AsyncTaskParameters(ExecutorService executorService2, boolean runInThread, ProgressMonitor progressMonitor) {
            this.executorService = executorService2;
            this.runInThread = runInThread;
            this.progressMonitor = progressMonitor;
        }
    }
}

