/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.ResultSet;

interface RowCallbackHandler {
    public void processRow(ResultSet var1);
}

