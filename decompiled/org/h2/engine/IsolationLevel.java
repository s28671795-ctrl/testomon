/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import org.h2.message.DbException;

public enum IsolationLevel {
    READ_UNCOMMITTED(1, 0),
    READ_COMMITTED(2, 3),
    REPEATABLE_READ(4, 1),
    SNAPSHOT(6, 1),
    SERIALIZABLE(8, 1);

    private final String sql = this.name().replace('_', ' ').intern();
    private final int jdbc;
    private final int lockMode;

    public static IsolationLevel fromJdbc(int n) {
        switch (n) {
            case 1: {
                return READ_UNCOMMITTED;
            }
            case 2: {
                return READ_COMMITTED;
            }
            case 4: {
                return REPEATABLE_READ;
            }
            case 6: {
                return SNAPSHOT;
            }
            case 8: {
                return SERIALIZABLE;
            }
        }
        throw DbException.getInvalidValueException("isolation level", n);
    }

    public static IsolationLevel fromLockMode(int n) {
        switch (n) {
            case 0: {
                return READ_UNCOMMITTED;
            }
            default: {
                return READ_COMMITTED;
            }
            case 1: 
            case 2: 
        }
        return SERIALIZABLE;
    }

    public static IsolationLevel fromSql(String string) {
        switch (string) {
            case "READ UNCOMMITTED": {
                return READ_UNCOMMITTED;
            }
            case "READ COMMITTED": {
                return READ_COMMITTED;
            }
            case "REPEATABLE READ": {
                return REPEATABLE_READ;
            }
            case "SNAPSHOT": {
                return SNAPSHOT;
            }
            case "SERIALIZABLE": {
                return SERIALIZABLE;
            }
        }
        throw DbException.getInvalidValueException("isolation level", string);
    }

    private IsolationLevel(int n2, int n3) {
        this.jdbc = n2;
        this.lockMode = n3;
    }

    public String getSQL() {
        return this.sql;
    }

    public int getJdbc() {
        return this.jdbc;
    }

    public int getLockMode() {
        return this.lockMode;
    }

    public boolean allowNonRepeatableRead() {
        return this.ordinal() < REPEATABLE_READ.ordinal();
    }
}

