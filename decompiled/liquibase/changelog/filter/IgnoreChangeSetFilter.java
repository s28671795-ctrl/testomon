/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;

public class IgnoreChangeSetFilter
implements ChangeSetFilter {
    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        if (changeSet.isIgnore()) {
            return new ChangeSetFilterResult(false, "Changeset is ignored", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        if (changeSet.isInheritableIgnore()) {
            return new ChangeSetFilterResult(false, "Changeset is ignored", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(true, "Changeset is not ignored", this.getClass(), this.getMdcName(), this.getDisplayName());
    }

    @Override
    public String getMdcName() {
        return "ignored";
    }

    @Override
    public String getDisplayName() {
        return "Ignored";
    }
}

