/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.sql.SQLException;

public interface SimpleRowSource {
    public Object[] readRow() throws SQLException;

    public void close();

    public void reset() throws SQLException;
}

