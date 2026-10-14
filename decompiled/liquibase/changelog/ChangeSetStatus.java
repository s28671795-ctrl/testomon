/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.util.Date;
import java.util.Set;
import liquibase.ChecksumVersion;
import liquibase.change.CheckSum;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.exception.LiquibaseException;

public class ChangeSetStatus {
    private final ChangeSet changeSet;
    private final CheckSum currentCheckSum;
    private String description;
    private String comments;
    private boolean willRun;
    private Set<ChangeSetFilterResult> filterResults;
    private CheckSum storedCheckSum;
    private Date dateLastExecuted;
    private boolean previouslyRan;
    private RanChangeSet ranChangeSet;

    public ChangeSetStatus(ChangeSet changeSet) {
        this.changeSet = changeSet;
        ChecksumVersion version = changeSet.getStoredCheckSum() != null ? ChecksumVersion.enumFromChecksumVersion(changeSet.getStoredCheckSum().getVersion()) : ChecksumVersion.latest();
        this.currentCheckSum = changeSet.generateCheckSum(version);
        this.description = changeSet.getDescription();
        this.comments = changeSet.getComments();
    }

    public ChangeSetStatus(ChangeSet changeSet, boolean skipChangeSetStatusGeneration) throws LiquibaseException {
        if (!skipChangeSetStatusGeneration) {
            throw new LiquibaseException(String.format("ChangeSetStatus for ChangeSet %s cannot generated", changeSet.toString()));
        }
        this.changeSet = changeSet;
        this.currentCheckSum = null;
        this.description = null;
        this.comments = null;
    }

    public ChangeSet getChangeSet() {
        return this.changeSet;
    }

    public CheckSum getCurrentCheckSum() {
        return this.currentCheckSum;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean getWillRun() {
        return this.willRun;
    }

    public void setWillRun(boolean willRun) {
        this.willRun = willRun;
    }

    public Set<ChangeSetFilterResult> getFilterResults() {
        return this.filterResults;
    }

    public void setFilterResults(Set<ChangeSetFilterResult> filterResults) {
        this.filterResults = filterResults;
    }

    public boolean isFilteredBy(Class<? extends ChangeSetFilter> filterType) {
        if (!this.willRun) {
            return false;
        }
        if (this.filterResults == null) {
            return false;
        }
        for (ChangeSetFilterResult result : this.filterResults) {
            if (!result.getFilter().equals(filterType)) continue;
            return true;
        }
        return false;
    }

    public CheckSum getStoredCheckSum() {
        return this.storedCheckSum;
    }

    public void setStoredCheckSum(CheckSum storedCheckSum) {
        this.storedCheckSum = storedCheckSum;
    }

    public Date getDateLastExecuted() {
        return this.dateLastExecuted;
    }

    public void setDateLastExecuted(Date dateLastExecuted) {
        this.dateLastExecuted = dateLastExecuted;
    }

    public boolean getPreviouslyRan() {
        return this.previouslyRan;
    }

    public void setPreviouslyRan(boolean previouslyRan) {
        this.previouslyRan = previouslyRan;
    }

    public RanChangeSet getRanChangeSet() {
        return this.ranChangeSet;
    }

    public void setRanChangeSet(RanChangeSet ranChangeSet) {
        this.ranChangeSet = ranChangeSet;
    }
}

