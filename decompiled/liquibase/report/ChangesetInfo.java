/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import liquibase.changelog.ChangeSet;
import liquibase.report.IndividualChangesetInfo;
import liquibase.report.PendingChangesetInfo;
import liquibase.util.CollectionUtil;
import liquibase.util.StringUtil;

public class ChangesetInfo {
    private int changesetCount;
    private int pendingChangesetCount;
    private int failedChangesetCount;
    private final List<IndividualChangesetInfo> changesetInfoList = new ArrayList<IndividualChangesetInfo>();
    private final List<PendingChangesetInfo> pendingChangesetInfoList = new ArrayList<PendingChangesetInfo>();

    public void addAllToChangesetInfoList(List<ChangeSet> changeSets, boolean isRollback) {
        if (changeSets != null) {
            for (ChangeSet deployedChangeSet : changeSets) {
                String changesetOutcome = isRollback ? (deployedChangeSet.getRollbackExecType() == null ? "" : deployedChangeSet.getRollbackExecType().toString()) : (deployedChangeSet.getExecType() == null ? "" : deployedChangeSet.getExecType().toString());
                boolean success = true;
                String errorMsg = deployedChangeSet.getErrorMsg();
                if (StringUtil.isNotEmpty(errorMsg)) {
                    changesetOutcome = ChangeSet.ExecType.FAILED.value;
                    success = false;
                }
                List<String> generatedSql = deployedChangeSet.getGeneratedSql().stream().filter(sql -> sql != null && !sql.isEmpty()).collect(Collectors.toList());
                this.changesetInfoList.add(new IndividualChangesetInfo(this.changesetInfoList.size() + 1, deployedChangeSet.getAuthor(), deployedChangeSet.getId(), deployedChangeSet.getFilePath(), deployedChangeSet.getComments(), success, changesetOutcome, errorMsg, deployedChangeSet.getLabels() == null ? null : deployedChangeSet.getLabels().toString(), deployedChangeSet.getContextFilter() == null ? null : deployedChangeSet.getContextFilter().getOriginalString(), ChangesetInfo.buildAttributesString(deployedChangeSet), generatedSql));
            }
        }
    }

    private static List<String> buildAttributesString(ChangeSet changeSet) {
        ArrayList<String> attributes = new ArrayList<String>();
        if (changeSet.getFailOnError() != null && !changeSet.getFailOnError().booleanValue()) {
            attributes.add("failOnError = false");
        }
        if (changeSet.isAlwaysRun()) {
            attributes.add("alwaysRun");
        }
        if (changeSet.isRunOnChange()) {
            attributes.add("runOnChange");
        }
        if (!changeSet.isRunInTransaction()) {
            attributes.add("runInTransaction = false");
        }
        if (StringUtil.isNotEmpty(changeSet.getRunOrder())) {
            attributes.add("runOrder = " + changeSet.getRunOrder());
        }
        if (StringUtil.isNotEmpty(changeSet.getRunWith())) {
            attributes.add("runWith = " + changeSet.getRunWith());
        }
        if (StringUtil.isNotEmpty(changeSet.getRunWithSpoolFile())) {
            attributes.add("runWithSpoolFile = " + changeSet.getRunWithSpoolFile());
        }
        if (!CollectionUtil.createIfNull(changeSet.getDbmsSet()).isEmpty()) {
            attributes.add("dbms = " + StringUtil.join(changeSet.getDbmsSet(), ", "));
        }
        return attributes;
    }

    public void addAllToPendingChangesetInfoList(Map<ChangeSet, String> pendingChanges) {
        if (pendingChanges != null) {
            pendingChanges.forEach((changeSet, reason) -> {
                PendingChangesetInfo pendingChangesetInfo = new PendingChangesetInfo(changeSet.getAuthor(), changeSet.getId(), changeSet.getFilePath(), changeSet.getComments(), changeSet.getLabels() == null ? null : changeSet.getLabels().toString(), changeSet.getContextFilter() == null ? null : changeSet.getContextFilter().getOriginalString(), (String)reason, (ChangeSet)changeSet);
                this.pendingChangesetInfoList.add(pendingChangesetInfo);
            });
        }
    }

    public int getChangesetCount() {
        return this.changesetCount;
    }

    public int getPendingChangesetCount() {
        return this.pendingChangesetCount;
    }

    public int getFailedChangesetCount() {
        return this.failedChangesetCount;
    }

    public List<IndividualChangesetInfo> getChangesetInfoList() {
        return this.changesetInfoList;
    }

    public List<PendingChangesetInfo> getPendingChangesetInfoList() {
        return this.pendingChangesetInfoList;
    }

    public void setChangesetCount(int changesetCount) {
        this.changesetCount = changesetCount;
    }

    public void setPendingChangesetCount(int pendingChangesetCount) {
        this.pendingChangesetCount = pendingChangesetCount;
    }

    public void setFailedChangesetCount(int failedChangesetCount) {
        this.failedChangesetCount = failedChangesetCount;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChangesetInfo)) {
            return false;
        }
        ChangesetInfo other = (ChangesetInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getChangesetCount() != other.getChangesetCount()) {
            return false;
        }
        if (this.getPendingChangesetCount() != other.getPendingChangesetCount()) {
            return false;
        }
        if (this.getFailedChangesetCount() != other.getFailedChangesetCount()) {
            return false;
        }
        List<IndividualChangesetInfo> this$changesetInfoList = this.getChangesetInfoList();
        List<IndividualChangesetInfo> other$changesetInfoList = other.getChangesetInfoList();
        if (this$changesetInfoList == null ? other$changesetInfoList != null : !((Object)this$changesetInfoList).equals(other$changesetInfoList)) {
            return false;
        }
        List<PendingChangesetInfo> this$pendingChangesetInfoList = this.getPendingChangesetInfoList();
        List<PendingChangesetInfo> other$pendingChangesetInfoList = other.getPendingChangesetInfoList();
        return !(this$pendingChangesetInfoList == null ? other$pendingChangesetInfoList != null : !((Object)this$pendingChangesetInfoList).equals(other$pendingChangesetInfoList));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ChangesetInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getChangesetCount();
        result = result * 59 + this.getPendingChangesetCount();
        result = result * 59 + this.getFailedChangesetCount();
        List<IndividualChangesetInfo> $changesetInfoList = this.getChangesetInfoList();
        result = result * 59 + ($changesetInfoList == null ? 43 : ((Object)$changesetInfoList).hashCode());
        List<PendingChangesetInfo> $pendingChangesetInfoList = this.getPendingChangesetInfoList();
        result = result * 59 + ($pendingChangesetInfoList == null ? 43 : ((Object)$pendingChangesetInfoList).hashCode());
        return result;
    }

    public String toString() {
        return "ChangesetInfo(changesetCount=" + this.getChangesetCount() + ", pendingChangesetCount=" + this.getPendingChangesetCount() + ", failedChangesetCount=" + this.getFailedChangesetCount() + ", changesetInfoList=" + this.getChangesetInfoList() + ", pendingChangesetInfoList=" + this.getPendingChangesetInfoList() + ")";
    }
}

