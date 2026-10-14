/*
 * Decompiled with CFR 0.152.
 */
package liquibase.lockservice;

import java.util.Date;

public class DatabaseChangeLogLock {
    private final int id;
    private final Date lockGranted;
    private final String lockedBy;

    public DatabaseChangeLogLock(int id, Date lockGranted, String lockedBy) {
        this.id = id;
        this.lockGranted = new Date(lockGranted.getTime());
        this.lockedBy = lockedBy;
    }

    public int getId() {
        return this.id;
    }

    public Date getLockGranted() {
        return (Date)this.lockGranted.clone();
    }

    public String getLockedBy() {
        return this.lockedBy;
    }
}

