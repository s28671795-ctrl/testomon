/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import liquibase.RuntimeEnvironment;
import liquibase.Scope;
import liquibase.change.core.TagDatabaseChange;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.filter.CountChangeSetFilter;
import liquibase.changelog.filter.UpToTagChangeSetFilter;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.changelog.visitor.SkippedChangeSetVisitor;
import liquibase.exception.LiquibaseException;

public class StatusChangeLogIterator
extends ChangeLogIterator {
    private String tag;

    public StatusChangeLogIterator(DatabaseChangeLog databaseChangeLog, ChangeSetFilter ... changeSetFilters) {
        super(databaseChangeLog, changeSetFilters);
    }

    public StatusChangeLogIterator(DatabaseChangeLog databaseChangeLog, String tag, ChangeSetFilter ... changeSetFilters) {
        super(databaseChangeLog, changeSetFilters);
        this.tag = tag;
    }

    @Override
    public void run(ChangeSetVisitor visitor, RuntimeEnvironment env) throws LiquibaseException {
        this.databaseChangeLog.setRuntimeEnvironment(env);
        try {
            Scope.child(Scope.Attr.databaseChangeLog, (Object)this.databaseChangeLog, () -> {
                ArrayList<ChangeSet> changeSetList = new ArrayList<ChangeSet>(this.databaseChangeLog.getChangeSets());
                if (visitor.getDirection().equals((Object)ChangeSetVisitor.Direction.REVERSE)) {
                    Collections.reverse(changeSetList);
                }
                for (ChangeSet changeSet : changeSetList) {
                    AtomicBoolean shouldVisit = new AtomicBoolean(true);
                    LinkedHashSet<ChangeSetFilterResult> reasonsAccepted = new LinkedHashSet<ChangeSetFilterResult>();
                    LinkedHashSet<ChangeSetFilterResult> reasonsDenied = new LinkedHashSet<ChangeSetFilterResult>();
                    if (this.changeSetFilters != null) {
                        shouldVisit.set(this.iterateFilters(changeSet, reasonsAccepted, reasonsDenied));
                    }
                    if (shouldVisit.get()) {
                        visitor.visit(changeSet, this.databaseChangeLog, env.getTargetDatabase(), reasonsAccepted);
                        this.markSeen(changeSet);
                        continue;
                    }
                    if (!(visitor instanceof SkippedChangeSetVisitor)) continue;
                    ((SkippedChangeSetVisitor)((Object)visitor)).skipped(changeSet, this.databaseChangeLog, env.getTargetDatabase(), reasonsDenied);
                }
            });
        }
        catch (Exception e) {
            throw new LiquibaseException(e);
        }
        finally {
            this.databaseChangeLog.setRuntimeEnvironment(null);
        }
    }

    private boolean alreadyDeniedForTagChangeAndTagFilter(Set<ChangeSetFilterResult> reasonsDenied, ChangeSet changeSet, ChangeSetFilter filter2) {
        return !reasonsDenied.isEmpty() && !this.hasTagDatabaseChange(changeSet) && filter2 instanceof UpToTagChangeSetFilter;
    }

    private boolean alreadyDeniedForCountFilter(Set<ChangeSetFilterResult> reasonsDenied, ChangeSetFilter filter2) {
        return !reasonsDenied.isEmpty() && filter2 instanceof CountChangeSetFilter;
    }

    private boolean iterateFilters(ChangeSet changeSet, Set<ChangeSetFilterResult> reasonsAccepted, Set<ChangeSetFilterResult> reasonsDenied) {
        boolean shouldVisit = true;
        boolean tagDatabaseAlreadyFound = false;
        for (ChangeSetFilter filter2 : this.changeSetFilters) {
            if (tagDatabaseAlreadyFound && filter2 instanceof UpToTagChangeSetFilter || this.alreadyDeniedForTagChangeAndTagFilter(reasonsDenied, changeSet, filter2) || this.alreadyDeniedForCountFilter(reasonsDenied, filter2)) continue;
            ChangeSetFilterResult acceptsResult = filter2.accepts(changeSet);
            if (acceptsResult.isAccepted()) {
                reasonsAccepted.add(acceptsResult);
                continue;
            }
            shouldVisit = false;
            reasonsDenied.add(acceptsResult);
            if (acceptsResult.getMessage().toLowerCase().contains("changeset already ran")) break;
            if (tagDatabaseAlreadyFound) continue;
            tagDatabaseAlreadyFound = this.hasTagDatabaseChange(changeSet);
        }
        return shouldVisit;
    }

    private boolean hasTagDatabaseChange(ChangeSet changeSet) {
        if (this.tag == null) {
            return false;
        }
        return changeSet.getChanges().stream().anyMatch(change -> {
            if (!(change instanceof TagDatabaseChange)) {
                return false;
            }
            TagDatabaseChange tagDatabaseChange = (TagDatabaseChange)change;
            return tagDatabaseChange.getTag().equals(this.tag);
        });
    }
}

