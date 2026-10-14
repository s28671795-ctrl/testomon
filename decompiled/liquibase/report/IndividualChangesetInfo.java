/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import java.util.List;
import liquibase.util.CollectionUtil;

public class IndividualChangesetInfo {
    private int index;
    private String changesetAuthor;
    private String changesetId;
    private String changelogFile;
    private String comment;
    private Boolean success;
    private String changesetOutcome;
    private String errorMsg;
    private String labels;
    private String contexts;
    private List<String> attributes;
    private List<String> generatedSql;

    public boolean hasAttributes() {
        return !CollectionUtil.createIfNull(this.attributes).isEmpty();
    }

    public int getIndex() {
        return this.index;
    }

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

    public Boolean getSuccess() {
        return this.success;
    }

    public String getChangesetOutcome() {
        return this.changesetOutcome;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getLabels() {
        return this.labels;
    }

    public String getContexts() {
        return this.contexts;
    }

    public List<String> getAttributes() {
        return this.attributes;
    }

    public List<String> getGeneratedSql() {
        return this.generatedSql;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setChangesetOutcome(String changesetOutcome) {
        this.changesetOutcome = changesetOutcome;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public void setContexts(String contexts) {
        this.contexts = contexts;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void setGeneratedSql(List<String> generatedSql) {
        this.generatedSql = generatedSql;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IndividualChangesetInfo)) {
            return false;
        }
        IndividualChangesetInfo other = (IndividualChangesetInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getIndex() != other.getIndex()) {
            return false;
        }
        Boolean this$success = this.getSuccess();
        Boolean other$success = other.getSuccess();
        if (this$success == null ? other$success != null : !((Object)this$success).equals(other$success)) {
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
        String this$changesetOutcome = this.getChangesetOutcome();
        String other$changesetOutcome = other.getChangesetOutcome();
        if (this$changesetOutcome == null ? other$changesetOutcome != null : !this$changesetOutcome.equals(other$changesetOutcome)) {
            return false;
        }
        String this$errorMsg = this.getErrorMsg();
        String other$errorMsg = other.getErrorMsg();
        if (this$errorMsg == null ? other$errorMsg != null : !this$errorMsg.equals(other$errorMsg)) {
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
        List<String> this$attributes = this.getAttributes();
        List<String> other$attributes = other.getAttributes();
        if (this$attributes == null ? other$attributes != null : !((Object)this$attributes).equals(other$attributes)) {
            return false;
        }
        List<String> this$generatedSql = this.getGeneratedSql();
        List<String> other$generatedSql = other.getGeneratedSql();
        return !(this$generatedSql == null ? other$generatedSql != null : !((Object)this$generatedSql).equals(other$generatedSql));
    }

    protected boolean canEqual(Object other) {
        return other instanceof IndividualChangesetInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getIndex();
        Boolean $success = this.getSuccess();
        result = result * 59 + ($success == null ? 43 : ((Object)$success).hashCode());
        String $changesetAuthor = this.getChangesetAuthor();
        result = result * 59 + ($changesetAuthor == null ? 43 : $changesetAuthor.hashCode());
        String $changesetId = this.getChangesetId();
        result = result * 59 + ($changesetId == null ? 43 : $changesetId.hashCode());
        String $changelogFile = this.getChangelogFile();
        result = result * 59 + ($changelogFile == null ? 43 : $changelogFile.hashCode());
        String $comment = this.getComment();
        result = result * 59 + ($comment == null ? 43 : $comment.hashCode());
        String $changesetOutcome = this.getChangesetOutcome();
        result = result * 59 + ($changesetOutcome == null ? 43 : $changesetOutcome.hashCode());
        String $errorMsg = this.getErrorMsg();
        result = result * 59 + ($errorMsg == null ? 43 : $errorMsg.hashCode());
        String $labels = this.getLabels();
        result = result * 59 + ($labels == null ? 43 : $labels.hashCode());
        String $contexts = this.getContexts();
        result = result * 59 + ($contexts == null ? 43 : $contexts.hashCode());
        List<String> $attributes = this.getAttributes();
        result = result * 59 + ($attributes == null ? 43 : ((Object)$attributes).hashCode());
        List<String> $generatedSql = this.getGeneratedSql();
        result = result * 59 + ($generatedSql == null ? 43 : ((Object)$generatedSql).hashCode());
        return result;
    }

    public String toString() {
        return "IndividualChangesetInfo(index=" + this.getIndex() + ", changesetAuthor=" + this.getChangesetAuthor() + ", changesetId=" + this.getChangesetId() + ", changelogFile=" + this.getChangelogFile() + ", comment=" + this.getComment() + ", success=" + this.getSuccess() + ", changesetOutcome=" + this.getChangesetOutcome() + ", errorMsg=" + this.getErrorMsg() + ", labels=" + this.getLabels() + ", contexts=" + this.getContexts() + ", attributes=" + this.getAttributes() + ", generatedSql=" + this.getGeneratedSql() + ")";
    }

    public IndividualChangesetInfo(int index, String changesetAuthor, String changesetId, String changelogFile, String comment, Boolean success, String changesetOutcome, String errorMsg, String labels, String contexts, List<String> attributes, List<String> generatedSql) {
        this.index = index;
        this.changesetAuthor = changesetAuthor;
        this.changesetId = changesetId;
        this.changelogFile = changelogFile;
        this.comment = comment;
        this.success = success;
        this.changesetOutcome = changesetOutcome;
        this.errorMsg = errorMsg;
        this.labels = labels;
        this.contexts = contexts;
        this.attributes = attributes;
        this.generatedSql = generatedSql;
    }
}

