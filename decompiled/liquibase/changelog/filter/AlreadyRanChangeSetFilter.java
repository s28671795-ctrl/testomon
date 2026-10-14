/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.List;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.filter.RanChangeSetFilter;

public class AlreadyRanChangeSetFilter
extends RanChangeSetFilter {
    public AlreadyRanChangeSetFilter(List<RanChangeSet> ranChangeSets) {
        super(ranChangeSets);
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        if (this.getRanChangeSet(changeSet) != null) {
            return new ChangeSetFilterResult(true, "Changeset already ran", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(false, "Changeset has not run", this.getClass(), this.getMdcName(), this.getDisplayName());
    }

    @Override
    public String getMdcName() {
        return "alreadyRan";
    }
}

