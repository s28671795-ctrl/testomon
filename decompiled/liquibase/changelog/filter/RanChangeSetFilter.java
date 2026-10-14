/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.List;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;

public abstract class RanChangeSetFilter
implements ChangeSetFilter {
    public List<RanChangeSet> ranChangeSets;

    public RanChangeSetFilter(List<RanChangeSet> ranChangeSets) {
        this.ranChangeSets = ranChangeSets;
    }

    public RanChangeSet getRanChangeSet(ChangeSet changeSet) {
        for (RanChangeSet ranChangeSet : this.ranChangeSets) {
            if (!ranChangeSet.isSameAs(changeSet)) continue;
            return ranChangeSet;
        }
        return null;
    }
}

