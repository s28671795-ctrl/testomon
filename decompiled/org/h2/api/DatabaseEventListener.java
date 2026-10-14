/*
 * Decompiled with CFR 0.152.
 */
package org.h2.api;

import java.sql.SQLException;
import java.util.EventListener;

public interface DatabaseEventListener
extends EventListener {
    public static final int STATE_SCAN_FILE = 0;
    public static final int STATE_CREATE_INDEX = 1;
    public static final int STATE_RECOVER = 2;
    public static final int STATE_BACKUP_FILE = 3;
    public static final int STATE_RECONNECTED = 4;
    public static final int STATE_STATEMENT_START = 5;
    public static final int STATE_STATEMENT_END = 6;
    public static final int STATE_STATEMENT_PROGRESS = 7;

    default public void init(String string) {
    }

    default public void opened() {
    }

    default public void exceptionThrown(SQLException sQLException, String string) {
    }

    default public void setProgress(int n, String string, long l, long l2) {
    }

    default public void closingDatabase() {
    }
}

