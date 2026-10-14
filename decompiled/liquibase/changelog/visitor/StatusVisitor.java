/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.ChangeSetStatus;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.filter.NotInChangeLogChangeSetFilter;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.changelog.visitor.SkippedChangeSetVisitor;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;

public class StatusVisitor
implements ChangeSetVisitor,
SkippedChangeSetVisitor {
    private final LinkedHashMap<ChangeSet, ChangeSetStatus> changeSetStatuses = new LinkedHashMap();
    private final List<RanChangeSet> ranChangeSets;

    public StatusVisitor(Database database) throws LiquibaseException {
        this.ranChangeSets = new ArrayList<RanChangeSet>(Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database).getRanChangeSets());
    }

    @Override
    public ChangeSetVisitor.Direction getDirection() {
        return ChangeSetVisitor.Direction.FORWARD;
    }

    @Override
    public void visit(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
        ChangeSetStatus status = this.addStatus(changeSet);
        status.setWillRun(true);
        status.setFilterResults(filterResults);
    }

    @Override
    public void skipped(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
        boolean needsChangeSetToBeSkipped = filterResults.stream().anyMatch(filter2 -> !filter2.isAccepted());
        ChangeSetStatus status = new ChangeSetStatus(changeSet, needsChangeSetToBeSkipped);
        status.setWillRun(false);
        status.setFilterResults(filterResults);
        this.removeRanChangeSets(changeSet, status);
        this.changeSetStatuses.put(changeSet, status);
    }

    protected ChangeSetStatus addStatus(ChangeSet changeSet) {
        ChangeSetStatus status = new ChangeSetStatus(changeSet);
        this.removeRanChangeSets(changeSet, status);
        this.changeSetStatuses.put(changeSet, status);
        return status;
    }

    private void removeRanChangeSets(ChangeSet changeSet, ChangeSetStatus status) {
        RanChangeSet ranChangeSetToRemove = null;
        for (RanChangeSet ranChangeSet : this.ranChangeSets) {
            if (!ranChangeSet.isSameAs(changeSet)) continue;
            status.setPreviouslyRan(true);
            status.setDateLastExecuted(ranChangeSet.getDateExecuted());
            status.setStoredCheckSum(ranChangeSet.getLastCheckSum());
            status.setRanChangeSet(ranChangeSet);
            ranChangeSetToRemove = ranChangeSet;
            break;
        }
        if (ranChangeSetToRemove != null) {
            this.ranChangeSets.remove(ranChangeSetToRemove);
        }
    }

    public ChangeSetStatus getStatus(ChangeSet changeSet) {
        return this.changeSetStatuses.get(changeSet);
    }

    public List<ChangeSetStatus> getStatuses() {
        ArrayList<ChangeSetStatus> returnList = new ArrayList<ChangeSetStatus>();
        for (RanChangeSet changeSet : this.ranChangeSets) {
            ChangeSetStatus status = new ChangeSetStatus(new ChangeSet(changeSet.getId(), changeSet.getAuthor(), false, false, changeSet.getChangeLog(), null, null, null));
            status.setPreviouslyRan(true);
            status.setDateLastExecuted(changeSet.getDateExecuted());
            status.setStoredCheckSum(changeSet.getLastCheckSum());
            status.setComments(changeSet.getComments());
            status.setDescription(changeSet.getDescription());
            status.setWillRun(false);
            status.setFilterResults(new HashSet<ChangeSetFilterResult>(Collections.singletonList(new ChangeSetFilterResult(false, "Changeset is not in change log", NotInChangeLogChangeSetFilter.class))));
            status.setRanChangeSet(changeSet);
            returnList.add(status);
        }
        returnList.addAll(this.changeSetStatuses.values());
        return returnList;
    }

    public List<ChangeSetStatus> getChangeSetsToRun() {
        ArrayList<ChangeSetStatus> returnList = new ArrayList<ChangeSetStatus>();
        for (ChangeSetStatus status : this.changeSetStatuses.values()) {
            if (!status.getWillRun()) continue;
            returnList.add(status);
        }
        return returnList;
    }

    public List<ChangeSetStatus> getChangeSetsToSkip() {
        ArrayList<ChangeSetStatus> returnList = new ArrayList<ChangeSetStatus>();
        for (ChangeSetStatus status : this.changeSetStatuses.values()) {
            if (status.getWillRun()) continue;
            returnList.add(status);
        }
        return returnList;
    }
}

