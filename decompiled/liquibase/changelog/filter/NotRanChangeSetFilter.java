/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.List;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;

public class NotRanChangeSetFilter
implements ChangeSetFilter {
    public List<RanChangeSet> ranChangeSets;

    public NotRanChangeSetFilter(List<RanChangeSet> ranChangeSets) {
        this.ranChangeSets = ranChangeSets;
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        for (RanChangeSet ranChangeSet : this.ranChangeSets) {
            if (!ranChangeSet.isSameAs(changeSet)) continue;
            return new ChangeSetFilterResult(false, "Changeset already ran", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(true, "Changeset not yet ran", this.getClass(), this.getMdcName(), this.getDisplayName());
    }
}

