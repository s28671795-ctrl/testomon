/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import liquibase.changelog.ChangeSet;

public class PendingChangesetInfo {
    private String changesetAuthor;
    private String changesetId;
    private String changelogFile;
    private String comment;
    private String labels;
    private String contexts;
    private String reason;
    private ChangeSet changeSet;

    public String getChangesetAuthor() {
        return this.changesetAuthor;
    }

    public String getChangesetId() {
        return this.changesetId;
    }

    public String getChangelogFile() {
        return this.changelogFile;
    }

    public String getComment() {
        return this.comment;
    }

    public String getLabels() {
        return this.labels;
    }

    public String getContexts() {
        return this.contexts;
    }

    public String getReason() {
        return this.reason;
    }

    public ChangeSet getChangeSet() {
        return this.changeSet;
    }

    public void setChangesetAuthor(String changesetAuthor) {
        this.changesetAuthor = changesetAuthor;
    }

    public void setChangesetId(String changesetId) {
        this.changesetId = changesetId;
    }

    public void setChangelogFile(String changelogFile) {
        this.changelogFile = changelogFile;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public void setContexts(String contexts) {
        this.contexts = contexts;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setChangeSet(ChangeSet changeSet) {
        this.changeSet = changeSet;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PendingChangesetInfo)) {
            return false;
        }
        PendingChangesetInfo other = (PendingChangesetInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$changesetAuthor = this.getChangesetAuthor();
        String other$changesetAuthor = other.getChangesetAuthor();
        if (this$changesetAuthor == null ? other$changesetAuthor != null : !this$changesetAuthor.equals(other$changesetAuthor)) {
            return false;
        }
        String this$changesetId = this.getChangesetId();
        String other$changesetId = other.getChangesetId();
        if (this$changesetId == null ? other$changesetId != null : !this$changesetId.equals(other$changesetId)) {
            return false;
        }
        String this$changelogFile = this.getChangelogFile();
        String other$changelogFile = other.getChangelogFile();
        if (this$changelogFile == null ? other$changelogFile != null : !this$changelogFile.equals(other$changelogFile)) {
            return false;
        }
        String this$comment = this.getComment();
        String other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) {
            return false;
        }
        String this$labels = this.getLabels();
        String other$labels = other.getLabels();
        if (this$labels == null ? other$labels != null : !this$labels.equals(other$labels)) {
            return false;
        }
        String this$contexts = this.getContexts();
        String other$contexts = other.getContexts();
        if (this$contexts == null ? other$contexts != null : !this$contexts.equals(other$contexts)) {
            return false;
        }
        String this$reason = this.getReason();
        String other$reason = other.getReason();
        if (this$reason == null ? other$reason != null : !this$reason.equals(other$reason)) {
            return false;
        }
        ChangeSet this$changeSet = this.getChangeSet();
        ChangeSet other$changeSet = other.getChangeSet();
        return !(this$changeSet == null ? other$changeSet != null : !((Object)this$changeSet).equals(other$changeSet));
    }

    protected boolean canEqual(Object other) {
        return other instanceof PendingChangesetInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $changesetAuthor = this.getChangesetAuthor();
        result = result * 59 + ($changesetAuthor == null ? 43 : $changesetAuthor.hashCode());
        String $changesetId = this.getChangesetId();
        result = result * 59 + ($changesetId == null ? 43 : $changesetId.hashCode());
        String $changelogFile = this.getChangelogFile();
        result = result * 59 + ($changelogFile == null ? 43 : $changelogFile.hashCode());
        String $comment = this.getComment();
        result = result * 59 + ($comment == null ? 43 : $comment.hashCode());
        String $labels = this.getLabels();
        result = result * 59 + ($labels == null ? 43 : $labels.hashCode());
        String $contexts = this.getContexts();
        result = result * 59 + ($contexts == null ? 43 : $contexts.hashCode());
        String $reason = this.getReason();
        result = result * 59 + ($reason == null ? 43 : $reason.hashCode());
        ChangeSet $changeSet = this.getChangeSet();
        result = result * 59 + ($changeSet == null ? 43 : ((Object)$changeSet).hashCode());
        return result;
    }

    public String toString() {
        return "PendingChangesetInfo(changesetAuthor=" + this.getChangesetAuthor() + ", changesetId=" + this.getChangesetId() + ", changelogFile=" + this.getChangelogFile() + ", comment=" + this.getComment() + ", labels=" + this.getLabels() + ", contexts=" + this.getContexts() + ", reason=" + this.getReason() + ", changeSet=" + this.getChangeSet() + ")";
    }

    public PendingChangesetInfo(String changesetAuthor, String changesetId, String changelogFile, String comment, String labels, String contexts, String reason, ChangeSet changeSet) {
        this.changesetAuthor = changesetAuthor;
        this.changesetId = changesetId;
        this.changelogFile = changelogFile;
        this.comment = comment;
        this.labels = labels;
        this.contexts = contexts;
        this.reason = reason;
        this.changeSet = changeSet;
    }
}

