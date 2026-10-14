/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.util.Date;
import liquibase.ChecksumVersion;
import liquibase.ContextExpression;
import liquibase.Labels;
import liquibase.change.CheckSum;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;

public class RanChangeSet {
    private String changeLog;
    private String storedChangeLog;
    private String id;
    private String author;
    private CheckSum lastCheckSum;
    private Date dateExecuted;
    private String tag;
    private ChangeSet.ExecType execType;
    private String description;
    private String comments;
    private Integer orderExecuted;
    private ContextExpression contextExpression;
    private Labels labels;
    private String deploymentId;
    private String liquibaseVersion;

    public RanChangeSet(ChangeSet changeSet) {
        this(changeSet, null, null, null);
    }

    public RanChangeSet(ChangeSet changeSet, ChangeSet.ExecType execType, ContextExpression contexts, Labels labels) {
        this(changeSet.getFilePath(), changeSet.getId(), changeSet.getAuthor(), changeSet.generateCheckSum(changeSet.getStoredCheckSum() != null ? ChecksumVersion.enumFromChecksumVersion(changeSet.getStoredCheckSum().getVersion()) : ChecksumVersion.latest()), new Date(), null, execType, changeSet.getDescription(), changeSet.getComments(), contexts, labels, null, changeSet.getStoredFilePath());
    }

    public RanChangeSet(String changeLog, String id, String author, CheckSum lastCheckSum, Date dateExecuted, String tag, ChangeSet.ExecType execType, String description, String comments, ContextExpression contextExpression, Labels labels, String deploymentId) {
        this(changeLog, id, author, lastCheckSum, dateExecuted, tag, execType, description, comments, contextExpression, labels, deploymentId, null);
    }

    public RanChangeSet(String changeLog, String id, String author, CheckSum lastCheckSum, Date dateExecuted, String tag, ChangeSet.ExecType execType, String description, String comments, ContextExpression contextExpression, Labels labels, String deploymentId, String storedChangeLog) {
        this.changeLog = changeLog;
        this.storedChangeLog = storedChangeLog;
        this.id = id;
        this.author = author;
        this.lastCheckSum = lastCheckSum;
        this.dateExecuted = dateExecuted == null ? null : new Date(dateExecuted.getTime());
        this.tag = tag;
        this.execType = execType;
        this.description = description;
        this.comments = comments;
        this.contextExpression = contextExpression;
        this.labels = labels;
        this.deploymentId = deploymentId;
    }

    public Date getDateExecuted() {
        if (this.dateExecuted == null) {
            return null;
        }
        return (Date)this.dateExecuted.clone();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RanChangeSet that = (RanChangeSet)o;
        return this.author.equals(that.author) && this.changeLog.equals(that.changeLog) && this.id.equals(that.id);
    }

    public int hashCode() {
        int result = this.changeLog.hashCode();
        result = 29 * result + this.id.hashCode();
        result = 29 * result + this.author.hashCode();
        return result;
    }

    public String toString() {
        return DatabaseChangeLog.normalizePath(this.getChangeLog()) + "::" + this.getId() + "::" + this.getAuthor();
    }

    public boolean isSameAs(ChangeSet changeSet) {
        return this.getId().equalsIgnoreCase(changeSet.getId()) && this.getAuthor().equalsIgnoreCase(changeSet.getAuthor()) && this.isSamePath(changeSet.getFilePath());
    }

    private boolean isSamePath(String filePath) {
        String normalizedFilePath = DatabaseChangeLog.normalizePath(this.getChangeLog());
        return normalizedFilePath.equalsIgnoreCase(DatabaseChangeLog.normalizePath(filePath));
    }

    public RanChangeSet() {
    }

    public String getChangeLog() {
        return this.changeLog;
    }

    public String getStoredChangeLog() {
        return this.storedChangeLog;
    }

    public String getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public CheckSum getLastCheckSum() {
        return this.lastCheckSum;
    }

    public String getTag() {
        return this.tag;
    }

    public ChangeSet.ExecType getExecType() {
        return this.execType;
    }

    public String getDescription() {
        return this.description;
    }

    public String getComments() {
        return this.comments;
    }

    public Integer getOrderExecuted() {
        return this.orderExecuted;
    }

    public ContextExpression getContextExpression() {
        return this.contextExpression;
    }

    public Labels getLabels() {
        return this.labels;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public String getLiquibaseVersion() {
        return this.liquibaseVersion;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public void setStoredChangeLog(String storedChangeLog) {
        this.storedChangeLog = storedChangeLog;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLastCheckSum(CheckSum lastCheckSum) {
        this.lastCheckSum = lastCheckSum;
    }

    public void setDateExecuted(Date dateExecuted) {
        this.dateExecuted = dateExecuted;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setExecType(ChangeSet.ExecType execType) {
        this.execType = execType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setOrderExecuted(Integer orderExecuted) {
        this.orderExecuted = orderExecuted;
    }

    public void setContextExpression(ContextExpression contextExpression) {
        this.contextExpression = contextExpression;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public void setLiquibaseVersion(String liquibaseVersion) {
        this.liquibaseVersion = liquibaseVersion;
    }
}

