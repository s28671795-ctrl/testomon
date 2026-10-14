/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import java.util.Date;
import liquibase.report.ChangesetInfo;
import liquibase.report.CustomData;
import liquibase.report.DatabaseInfo;
import liquibase.report.OperationInfo;
import liquibase.report.RuntimeInfo;
import liquibase.report.UpdateRollbackReportParameters;

public class UpdateReportParameters
implements UpdateRollbackReportParameters {
    private String changelogArgValue;
    private String jdbcUrl;
    private String tag;
    private String commandTitle = "Update";
    private Boolean success = Boolean.TRUE;
    private final DatabaseInfo databaseInfo = new DatabaseInfo();
    private final RuntimeInfo runtimeInfo = new RuntimeInfo();
    private final OperationInfo operationInfo = new OperationInfo();
    private final CustomData customData = new CustomData();
    private final ChangesetInfo changesetInfo = new ChangesetInfo();
    private final Date date = new Date();

    @Override
    public String getChangelogArgValue() {
        return this.changelogArgValue;
    }

    @Override
    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public String getTag() {
        return this.tag;
    }

    @Override
    public String getCommandTitle() {
        return this.commandTitle;
    }

    public Boolean getSuccess() {
        return this.success;
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

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCommandTitle(String commandTitle) {
        this.commandTitle = commandTitle;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UpdateReportParameters)) {
            return false;
        }
        UpdateReportParameters other = (UpdateReportParameters)o;
        if (!other.canEqual(this)) {
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
        String this$tag = this.getTag();
        String other$tag = other.getTag();
        if (this$tag == null ? other$tag != null : !this$tag.equals(other$tag)) {
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
        return other instanceof UpdateReportParameters;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Boolean $success = this.getSuccess();
        result = result * 59 + ($success == null ? 43 : ((Object)$success).hashCode());
        String $changelogArgValue = this.getChangelogArgValue();
        result = result * 59 + ($changelogArgValue == null ? 43 : $changelogArgValue.hashCode());
        String $jdbcUrl = this.getJdbcUrl();
        result = result * 59 + ($jdbcUrl == null ? 43 : $jdbcUrl.hashCode());
        String $tag = this.getTag();
        result = result * 59 + ($tag == null ? 43 : $tag.hashCode());
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
        return "UpdateReportParameters(changelogArgValue=" + this.getChangelogArgValue() + ", jdbcUrl=" + this.getJdbcUrl() + ", tag=" + this.getTag() + ", commandTitle=" + this.getCommandTitle() + ", success=" + this.getSuccess() + ", databaseInfo=" + this.getDatabaseInfo() + ", runtimeInfo=" + this.getRuntimeInfo() + ", operationInfo=" + this.getOperationInfo() + ", customData=" + this.getCustomData() + ", changesetInfo=" + this.getChangesetInfo() + ", date=" + this.getDate() + ")";
    }
}

