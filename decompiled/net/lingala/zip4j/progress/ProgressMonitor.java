/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.progress;

public class ProgressMonitor {
    private State state;
    private long totalWork;
    private long workCompleted;
    private int percentDone;
    private Task currentTask;
    private String fileName;
    private Result result;
    private Exception exception;
    private boolean cancelAllTasks;
    private boolean pause;

    public ProgressMonitor() {
        this.reset();
    }

    public void updateWorkCompleted(long workCompleted) {
        this.workCompleted += workCompleted;
        if (this.totalWork > 0L) {
            this.percentDone = (int)(this.workCompleted * 100L / this.totalWork);
            if (this.percentDone > 100) {
                this.percentDone = 100;
            }
        }
        while (this.pause) {
            try {
                Thread.sleep(150L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    public void endProgressMonitor() {
        this.result = Result.SUCCESS;
        this.percentDone = 100;
        this.reset();
    }

    public void endProgressMonitor(Exception e) {
        this.result = Result.ERROR;
        this.exception = e;
        this.reset();
    }

    public void fullReset() {
        this.reset();
        this.fileName = null;
        this.totalWork = 0L;
        this.workCompleted = 0L;
        this.percentDone = 0;
    }

    private void reset() {
        this.currentTask = Task.NONE;
        this.state = State.READY;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getTotalWork() {
        return this.totalWork;
    }

    public void setTotalWork(long totalWork) {
        this.totalWork = totalWork;
    }

    public long getWorkCompleted() {
        return this.workCompleted;
    }

    public int getPercentDone() {
        return this.percentDone;
    }

    public void setPercentDone(int percentDone) {
        this.percentDone = percentDone;
    }

    public Task getCurrentTask() {
        return this.currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Exception getException() {
        return this.exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean isCancelAllTasks() {
        return this.cancelAllTasks;
    }

    public void setCancelAllTasks(boolean cancelAllTasks) {
        this.cancelAllTasks = cancelAllTasks;
    }

    public boolean isPause() {
        return this.pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public static enum Task {
        NONE,
        ADD_ENTRY,
        REMOVE_ENTRY,
        CALCULATE_CRC,
        EXTRACT_ENTRY,
        MERGE_ZIP_FILES,
        SET_COMMENT,
        RENAME_FILE;

    }

    public static enum Result {
        SUCCESS,
        WORK_IN_PROGRESS,
        ERROR,
        CANCELLED;

    }

    public static enum State {
        READY,
        BUSY;

    }
}

