/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.List;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.filter.RanChangeSetFilter;

public class ActuallyExecutedChangeSetFilter
extends RanChangeSetFilter {
    public ActuallyExecutedChangeSetFilter(List<RanChangeSet> ranChangeSets) {
        super(ranChangeSets);
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        RanChangeSet ranChangeSet = this.getRanChangeSet(changeSet);
        if (ranChangeSet != null && (ranChangeSet.getExecType() == null || ranChangeSet.getExecType().equals((Object)ChangeSet.ExecType.EXECUTED) || ranChangeSet.getExecType().equals((Object)ChangeSet.ExecType.RERAN))) {
            return new ChangeSetFilterResult(true, "Changeset was executed previously", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(false, "Changeset was not previously executed", this.getClass(), this.getMdcName(), this.getDisplayName());
    }
}

