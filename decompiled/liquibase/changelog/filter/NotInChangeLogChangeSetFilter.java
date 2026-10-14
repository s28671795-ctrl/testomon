/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.HashSet;
import java.util.Set;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;

public class NotInChangeLogChangeSetFilter
implements ChangeSetFilter {
    private final Set<ChangeSet> changeSets;

    public NotInChangeLogChangeSetFilter(DatabaseChangeLog databaseChangeLog) {
        this.changeSets = new HashSet<ChangeSet>(databaseChangeLog.getChangeSets());
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        if (this.changeSets.contains(changeSet)) {
            return new ChangeSetFilterResult(false, "Changeset is in change log", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(true, "Changeset is not in change log", this.getClass(), this.getMdcName(), this.getDisplayName());
    }
}

