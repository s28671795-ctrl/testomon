/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;

public class CountChangeSetFilter
implements ChangeSetFilter {
    private final int changeSetsToAllow;
    private int changeSetsSeen;

    public CountChangeSetFilter(int changeSetsToAllow) {
        this.changeSetsToAllow = changeSetsToAllow;
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        ++this.changeSetsSeen;
        if (this.changeSetsSeen <= this.changeSetsToAllow) {
            return new ChangeSetFilterResult(true, "One of " + this.changeSetsToAllow + " changesets to run", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        String plurality = "changesets";
        if (this.changeSetsToAllow == 1) {
            plurality = "changeset";
        }
        return new ChangeSetFilterResult(false, "Only running " + this.changeSetsToAllow + " " + plurality, this.getClass(), this.getMdcName(), this.getDisplayName());
    }

    @Override
    public String getMdcName() {
        return "afterCount";
    }

    @Override
    public String getDisplayName() {
        return "After count";
    }
}

