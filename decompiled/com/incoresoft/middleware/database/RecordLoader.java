/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.database;

import java.sql.ResultSet;

public interface RecordLoader {
    public String selectQuery();

    public void load(ResultSet var1);
}

