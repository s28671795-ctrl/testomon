/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.PreparedStatement;

interface SqlTypeValue {
    public static final int TYPE_UNKNOWN = Integer.MIN_VALUE;

    public void setTypeValue(PreparedStatement var1, int var2, int var3, String var4);
}

