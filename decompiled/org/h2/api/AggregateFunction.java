/*
 * Decompiled with CFR 0.152.
 */
package org.h2.api;

import java.sql.Connection;
import java.sql.SQLException;

public interface AggregateFunction {
    default public void init(Connection connection) throws SQLException {
    }

    public int getType(int[] var1) throws SQLException;

    public void add(Object var1) throws SQLException;

    public Object getResult() throws SQLException;
}

