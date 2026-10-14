/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

public class OperationInfo {
    private String command;
    private String operationOutcome;
    private String operationOutcomeErrorMsg;
    private String exception;
    private String updateSummaryMsg;
    private Integer rowsAffected;
    private Boolean rollbackOnError = Boolean.FALSE;
    private String labels;
    private String contexts;

    public String getCommand() {
        return this.command;
    }

    public String getOperationOutcome() {
        return this.operationOutcome;
    }

    public String getOperationOutcomeErrorMsg() {
        return this.operationOutcomeErrorMsg;
    }

    public String getException() {
        return this.exception;
    }

    public String getUpdateSummaryMsg() {
        return this.updateSummaryMsg;
    }

    public Integer getRowsAffected() {
        return this.rowsAffected;
    }

    public Boolean getRollbackOnError() {
        return this.rollbackOnError;
    }

    public String getLabels() {
        return this.labels;
    }

    public String getContexts() {
        return this.contexts;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setOperationOutcome(String operationOutcome) {
        this.operationOutcome = operationOutcome;
    }

    public void setOperationOutcomeErrorMsg(String operationOutcomeErrorMsg) {
        this.operationOutcomeErrorMsg = operationOutcomeErrorMsg;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public void setUpdateSummaryMsg(String updateSummaryMsg) {
        this.updateSummaryMsg = updateSummaryMsg;
    }

    public void setRowsAffected(Integer rowsAffected) {
        this.rowsAffected = rowsAffected;
    }

    public void setRollbackOnError(Boolean rollbackOnError) {
        this.rollbackOnError = rollbackOnError;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public void setContexts(String contexts) {
        this.contexts = contexts;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OperationInfo)) {
            return false;
        }
        OperationInfo other = (OperationInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$rowsAffected = this.getRowsAffected();
        Integer other$rowsAffected = other.getRowsAffected();
        if (this$rowsAffected == null ? other$rowsAffected != null : !((Object)this$rowsAffected).equals(other$rowsAffected)) {
            return false;
        }
        Boolean this$rollbackOnError = this.getRollbackOnError();
        Boolean other$rollbackOnError = other.getRollbackOnError();
        if (this$rollbackOnError == null ? other$rollbackOnError != null : !((Object)this$rollbackOnError).equals(other$rollbackOnError)) {
            return false;
        }
        String this$command = this.getCommand();
        String other$command = other.getCommand();
        if (this$command == null ? other$command != null : !this$command.equals(other$command)) {
            return false;
        }
        String this$operationOutcome = this.getOperationOutcome();
        String other$operationOutcome = other.getOperationOutcome();
        if (this$operationOutcome == null ? other$operationOutcome != null : !this$operationOutcome.equals(other$operationOutcome)) {
            return false;
        }
        String this$operationOutcomeErrorMsg = this.getOperationOutcomeErrorMsg();
        String other$operationOutcomeErrorMsg = other.getOperationOutcomeErrorMsg();
        if (this$operationOutcomeErrorMsg == null ? other$operationOutcomeErrorMsg != null : !this$operationOutcomeErrorMsg.equals(other$operationOutcomeErrorMsg)) {
            return false;
        }
        String this$exception = this.getException();
        String other$exception = other.getException();
        if (this$exception == null ? other$exception != null : !this$exception.equals(other$exception)) {
            return false;
        }
        String this$updateSummaryMsg = this.getUpdateSummaryMsg();
        String other$updateSummaryMsg = other.getUpdateSummaryMsg();
        if (this$updateSummaryMsg == null ? other$updateSummaryMsg != null : !this$updateSummaryMsg.equals(other$updateSummaryMsg)) {
            return false;
        }
        String this$labels = this.getLabels();
        String other$labels = other.getLabels();
        if (this$labels == null ? other$labels != null : !this$labels.equals(other$labels)) {
            return false;
        }
        String this$contexts = this.getContexts();
        String other$contexts = other.getContexts();
        return !(this$contexts == null ? other$contexts != null : !this$contexts.equals(other$contexts));
    }

    protected boolean canEqual(Object other) {
        return other instanceof OperationInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $rowsAffected = this.getRowsAffected();
        result = result * 59 + ($rowsAffected == null ? 43 : ((Object)$rowsAffected).hashCode());
        Boolean $rollbackOnError = this.getRollbackOnError();
        result = result * 59 + ($rollbackOnError == null ? 43 : ((Object)$rollbackOnError).hashCode());
        String $command = this.getCommand();
        result = result * 59 + ($command == null ? 43 : $command.hashCode());
        String $operationOutcome = this.getOperationOutcome();
        result = result * 59 + ($operationOutcome == null ? 43 : $operationOutcome.hashCode());
        String $operationOutcomeErrorMsg = this.getOperationOutcomeErrorMsg();
        result = result * 59 + ($operationOutcomeErrorMsg == null ? 43 : $operationOutcomeErrorMsg.hashCode());
        String $exception = this.getException();
        result = result * 59 + ($exception == null ? 43 : $exception.hashCode());
        String $updateSummaryMsg = this.getUpdateSummaryMsg();
        result = result * 59 + ($updateSummaryMsg == null ? 43 : $updateSummaryMsg.hashCode());
        String $labels = this.getLabels();
        result = result * 59 + ($labels == null ? 43 : $labels.hashCode());
        String $contexts = this.getContexts();
        result = result * 59 + ($contexts == null ? 43 : $contexts.hashCode());
        return result;
    }

    public String toString() {
        return "OperationInfo(command=" + this.getCommand() + ", operationOutcome=" + this.getOperationOutcome() + ", operationOutcomeErrorMsg=" + this.getOperationOutcomeErrorMsg() + ", exception=" + this.getException() + ", updateSummaryMsg=" + this.getUpdateSummaryMsg() + ", rowsAffected=" + this.getRowsAffected() + ", rollbackOnError=" + this.getRollbackOnError() + ", labels=" + this.getLabels() + ", contexts=" + this.getContexts() + ")";
    }
}

