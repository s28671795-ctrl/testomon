/*
 * Decompiled with CFR 0.152.
 */
package liquibase.lockservice;

import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.LockException;
import liquibase.lockservice.DatabaseChangeLogLock;
import liquibase.servicelocator.PrioritizedService;

public interface LockService
extends PrioritizedService {
    public boolean supports(Database var1);

    public void setDatabase(Database var1);

    public void setChangeLogLockWaitTime(long var1);

    public void setChangeLogLockRecheckTime(long var1);

    public boolean hasChangeLogLock();

    public void waitForLock() throws LockException;

    public boolean acquireLock() throws LockException;

    public void releaseLock() throws LockException;

    public DatabaseChangeLogLock[] listLocks() throws LockException;

    public void forceReleaseLock() throws LockException, DatabaseException;

    public void reset();

    public void init() throws DatabaseException;

    public void destroy() throws DatabaseException;
}

