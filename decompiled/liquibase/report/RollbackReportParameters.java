/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import java.util.Date;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.logging.mdc.customobjects.ExceptionDetails;
import liquibase.report.ChangesetInfo;
import liquibase.report.CustomData;
import liquibase.report.DatabaseInfo;
import liquibase.report.OperationInfo;
import liquibase.report.RuntimeInfo;
import liquibase.report.UpdateRollbackReportParameters;

public class RollbackReportParameters
implements UpdateRollbackReportParameters {
    private String changelogArgValue;
    private String jdbcUrl;
    private Integer rollbackCount;
    private String rollbackTag;
    private String rollbackDate;
    private String failedChangeset;
    private String deploymentId;
    private ChangesetDetails changesetDetails;
    private Boolean success = Boolean.TRUE;
    private String commandTitle = "Rollback";
    private final DatabaseInfo databaseInfo = new DatabaseInfo();
    private final RuntimeInfo runtimeInfo = new RuntimeInfo();
    private final OperationInfo operationInfo = new OperationInfo();
    private final CustomData customData = new CustomData();
    private final ChangesetInfo changesetInfo = new ChangesetInfo();
    private final Date date = new Date();

    public void setupDatabaseInfo(Database database) throws DatabaseException {
        this.getDatabaseInfo().setDatabaseType(database.getDatabaseProductName());
        this.getDatabaseInfo().setVersion(database.getDatabaseProductVersion());
        this.getDatabaseInfo().setDatabaseUrl(database.getConnection().getURL());
        this.setJdbcUrl(database.getConnection().getURL());
    }

    public void setRollbackException(ExceptionDetails exceptionDetails) {
        this.getOperationInfo().setException(String.format("%s\n%s\n%s\n\n%s", exceptionDetails.getFormattedPrimaryException(), exceptionDetails.getFormattedPrimaryExceptionReason(), exceptionDetails.getFormattedPrimaryExceptionSource(), exceptionDetails.getPrimaryExceptionReason()));
    }

    @Override
    public String getChangelogArgValue() {
        return this.changelogArgValue;
    }

    @Override
    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public Integer getRollbackCount() {
        return this.rollbackCount;
    }

    public String getRollbackTag() {
        return this.rollbackTag;
    }

    public String getRollbackDate() {
        return this.rollbackDate;
    }

    public String getFailedChangeset() {
        return this.failedChangeset;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public ChangesetDetails getChangesetDetails() {
        return this.changesetDetails;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    @Override
    public String getCommandTitle() {
        return this.commandTitle;
    }

    public DatabaseInfo getDatabaseInfo() {
        return this.databaseInfo;
    }

    @Override
    public RuntimeInfo getRuntimeInfo() {
        return this.runtimeInfo;
    }

    @Override
    public OperationInfo getOperationInfo() {
        return this.operationInfo;
    }

    @Override
    public CustomData getCustomData() {
        return this.customData;
    }

    public ChangesetInfo getChangesetInfo() {
        return this.changesetInfo;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    public void setChangelogArgValue(String changelogArgValue) {
        this.changelogArgValue = changelogArgValue;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setRollbackCount(Integer rollbackCount) {
        this.rollbackCount = rollbackCount;
    }

    public void setRollbackTag(String rollbackTag) {
        this.rollbackTag = rollbackTag;
    }

    public void setRollbackDate(String rollbackDate) {
        this.rollbackDate = rollbackDate;
    }

    public void setFailedChangeset(String failedChangeset) {
        this.failedChangeset = failedChangeset;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public void setChangesetDetails(ChangesetDetails changesetDetails) {
        this.changesetDetails = changesetDetails;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setCommandTitle(String commandTitle) {
        this.commandTitle = commandTitle;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RollbackReportParameters)) {
            return false;
        }
        RollbackReportParameters other = (RollbackReportParameters)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$rollbackCount = this.getRollbackCount();
        Integer other$rollbackCount = other.getRollbackCount();
        if (this$rollbackCount == null ? other$rollbackCount != null : !((Object)this$rollbackCount).equals(other$rollbackCount)) {
            return false;
        }
        Boolean this$success = this.getSuccess();
        Boolean other$success = other.getSuccess();
        if (this$success == null ? other$success != null : !((Object)this$success).equals(other$success)) {
            return false;
        }
        String this$changelogArgValue = this.getChangelogArgValue();
        String other$changelogArgValue = other.getChangelogArgValue();
        if (this$changelogArgValue == null ? other$changelogArgValue != null : !this$changelogArgValue.equals(other$changelogArgValue)) {
            return false;
        }
        String this$jdbcUrl = this.getJdbcUrl();
        String other$jdbcUrl = other.getJdbcUrl();
        if (this$jdbcUrl == null ? other$jdbcUrl != null : !this$jdbcUrl.equals(other$jdbcUrl)) {
            return false;
        }
        String this$rollbackTag = this.getRollbackTag();
        String other$rollbackTag = other.getRollbackTag();
        if (this$rollbackTag == null ? other$rollbackTag != null : !this$rollbackTag.equals(other$rollbackTag)) {
            return false;
        }
        String this$rollbackDate = this.getRollbackDate();
        String other$rollbackDate = other.getRollbackDate();
        if (this$rollbackDate == null ? other$rollbackDate != null : !this$rollbackDate.equals(other$rollbackDate)) {
            return false;
        }
        String this$failedChangeset = this.getFailedChangeset();
        String other$failedChangeset = other.getFailedChangeset();
        if (this$failedChangeset == null ? other$failedChangeset != null : !this$failedChangeset.equals(other$failedChangeset)) {
            return false;
        }
        String this$deploymentId = this.getDeploymentId();
        String other$deploymentId = other.getDeploymentId();
        if (this$deploymentId == null ? other$deploymentId != null : !this$deploymentId.equals(other$deploymentId)) {
            return false;
        }
        ChangesetDetails this$changesetDetails = this.getChangesetDetails();
        ChangesetDetails other$changesetDetails = other.getChangesetDetails();
        if (this$changesetDetails == null ? other$changesetDetails != null : !this$changesetDetails.equals(other$changesetDetails)) {
            return false;
        }
        String this$commandTitle = this.getCommandTitle();
        String other$commandTitle = other.getCommandTitle();
        if (this$commandTitle == null ? other$commandTitle != null : !this$commandTitle.equals(other$commandTitle)) {
            return false;
        }
        DatabaseInfo this$databaseInfo = this.getDatabaseInfo();
        DatabaseInfo other$databaseInfo = other.getDatabaseInfo();
        if (this$databaseInfo == null ? other$databaseInfo != null : !((Object)this$databaseInfo).equals(other$databaseInfo)) {
            return false;
        }
        RuntimeInfo this$runtimeInfo = this.getRuntimeInfo();
        RuntimeInfo other$runtimeInfo = other.getRuntimeInfo();
        if (this$runtimeInfo == null ? other$runtimeInfo != null : !((Object)this$runtimeInfo).equals(other$runtimeInfo)) {
            return false;
        }
        OperationInfo this$operationInfo = this.getOperationInfo();
        OperationInfo other$operationInfo = other.getOperationInfo();
        if (this$operationInfo == null ? other$operationInfo != null : !((Object)this$operationInfo).equals(other$operationInfo)) {
            return false;
        }
        CustomData this$customData = this.getCustomData();
        CustomData other$customData = other.getCustomData();
        if (this$customData == null ? other$customData != null : !((Object)this$customData).equals(other$customData)) {
            return false;
        }
        ChangesetInfo this$changesetInfo = this.getChangesetInfo();
        ChangesetInfo other$changesetInfo = other.getChangesetInfo();
        if (this$changesetInfo == null ? other$changesetInfo != null : !((Object)this$changesetInfo).equals(other$changesetInfo)) {
            return false;
        }
        Date this$date = this.getDate();
        Date other$date = other.getDate();
        return !(this$date == null ? other$date != null : !((Object)this$date).equals(other$date));
    }

    protected boolean canEqual(Object other) {
        return other instanceof RollbackReportParameters;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $rollbackCount = this.getRollbackCount();
        result = result * 59 + ($rollbackCount == null ? 43 : ((Object)$rollbackCount).hashCode());
        Boolean $success = this.getSuccess();
        result = result * 59 + ($success == null ? 43 : ((Object)$success).hashCode());
        String $changelogArgValue = this.getChangelogArgValue();
        result = result * 59 + ($changelogArgValue == null ? 43 : $changelogArgValue.hashCode());
        String $jdbcUrl = this.getJdbcUrl();
        result = result * 59 + ($jdbcUrl == null ? 43 : $jdbcUrl.hashCode());
        String $rollbackTag = this.getRollbackTag();
        result = result * 59 + ($rollbackTag == null ? 43 : $rollbackTag.hashCode());
        String $rollbackDate = this.getRollbackDate();
        result = result * 59 + ($rollbackDate == null ? 43 : $rollbackDate.hashCode());
        String $failedChangeset = this.getFailedChangeset();
        result = result * 59 + ($failedChangeset == null ? 43 : $failedChangeset.hashCode());
        String $deploymentId = this.getDeploymentId();
        result = result * 59 + ($deploymentId == null ? 43 : $deploymentId.hashCode());
        ChangesetDetails $changesetDetails = this.getChangesetDetails();
        result = result * 59 + ($changesetDetails == null ? 43 : $changesetDetails.hashCode());
        String $commandTitle = this.getCommandTitle();
        result = result * 59 + ($commandTitle == null ? 43 : $commandTitle.hashCode());
        DatabaseInfo $databaseInfo = this.getDatabaseInfo();
        result = result * 59 + ($databaseInfo == null ? 43 : ((Object)$databaseInfo).hashCode());
        RuntimeInfo $runtimeInfo = this.getRuntimeInfo();
        result = result * 59 + ($runtimeInfo == null ? 43 : ((Object)$runtimeInfo).hashCode());
        OperationInfo $operationInfo = this.getOperationInfo();
        result = result * 59 + ($operationInfo == null ? 43 : ((Object)$operationInfo).hashCode());
        CustomData $customData = this.getCustomData();
        result = result * 59 + ($customData == null ? 43 : ((Object)$customData).hashCode());
        ChangesetInfo $changesetInfo = this.getChangesetInfo();
        result = result * 59 + ($changesetInfo == null ? 43 : ((Object)$changesetInfo).hashCode());
        Date $date = this.getDate();
        result = result * 59 + ($date == null ? 43 : ((Object)$date).hashCode());
        return result;
    }

    public String toString() {
        return "RollbackReportParameters(changelogArgValue=" + this.getChangelogArgValue() + ", jdbcUrl=" + this.getJdbcUrl() + ", rollbackCount=" + this.getRollbackCount() + ", rollbackTag=" + this.getRollbackTag() + ", rollbackDate=" + this.getRollbackDate() + ", failedChangeset=" + this.getFailedChangeset() + ", deploymentId=" + this.getDeploymentId() + ", changesetDetails=" + this.getChangesetDetails() + ", success=" + this.getSuccess() + ", commandTitle=" + this.getCommandTitle() + ", databaseInfo=" + this.getDatabaseInfo() + ", runtimeInfo=" + this.getRuntimeInfo() + ", operationInfo=" + this.getOperationInfo() + ", customData=" + this.getCustomData() + ", changesetInfo=" + this.getChangesetInfo() + ", date=" + this.getDate() + ")";
    }

    public static class ChangesetDetails {
        private String id;
        private String author;
        private String path;

        public String getId() {
            return this.id;
        }

        public String getAuthor() {
            return this.author;
        }

        public String getPath() {
            return this.path;
        }

        public ChangesetDetails(String id, String author, String path) {
            this.id = id;
            this.author = author;
            this.path = path;
        }
    }
}

