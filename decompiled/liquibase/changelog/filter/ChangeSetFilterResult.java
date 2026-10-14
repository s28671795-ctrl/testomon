/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import liquibase.changelog.filter.ChangeSetFilter;

public class ChangeSetFilterResult {
    private final boolean accepted;
    private final String message;
    private final Class<? extends ChangeSetFilter> filter;
    private final String mdcName;
    private final String displayName;

    public ChangeSetFilterResult(boolean accepted, String message, Class<? extends ChangeSetFilter> filter2) {
        this(accepted, message, filter2, filter2 == null ? null : filter2.getSimpleName(), filter2 == null ? null : filter2.getSimpleName());
    }

    public ChangeSetFilterResult(boolean accepted, String message, Class<? extends ChangeSetFilter> filter2, String mdcName, String displayName) {
        this.accepted = accepted;
        this.message = message;
        this.filter = filter2;
        this.mdcName = mdcName;
        this.displayName = displayName;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public String getMessage() {
        return this.message;
    }

    public Class<? extends ChangeSetFilter> getFilter() {
        return this.filter;
    }

    public String getMdcName() {
        return this.mdcName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String toString() {
        if (this.accepted) {
            return "Will run because " + this.message;
        }
        return "Will NOT run because " + this.message;
    }
}

