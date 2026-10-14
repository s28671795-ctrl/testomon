/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.filter.ChangeSetFilterResult;

public interface ChangeSetFilter {
    public ChangeSetFilterResult accepts(ChangeSet var1);

    default public String getMdcName() {
        return this.getClass().getSimpleName();
    }

    default public String getDisplayName() {
        return this.getClass().getSimpleName().replace("ChangeSetFilter", "Filter");
    }
}

