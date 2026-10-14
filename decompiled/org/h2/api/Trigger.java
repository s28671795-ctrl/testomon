/*
 * Decompiled with CFR 0.152.
 */
package org.h2.api;

import java.sql.Connection;
import java.sql.SQLException;

public interface Trigger {
    public static final int INSERT = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 4;
    public static final int SELECT = 8;

    default public void init(Connection connection, String string, String string2, String string3, boolean bl, int n) throws SQLException {
    }

    public void fire(Connection var1, Object[] var2, Object[] var3) throws SQLException;

    default public void close() throws SQLException {
    }

    default public void remove() throws SQLException {
    }
}

