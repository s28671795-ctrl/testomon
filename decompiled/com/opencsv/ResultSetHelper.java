/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHelper {
    public String[] getColumnNames(ResultSet var1) throws SQLException;

    public String[] getColumnValues(ResultSet var1) throws SQLException, IOException;

    public String[] getColumnValues(ResultSet var1, boolean var2) throws SQLException, IOException;

    public String[] getColumnValues(ResultSet var1, boolean var2, String var3, String var4) throws SQLException, IOException;
}

