/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.sqlserver;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.impl.DSL;

public class SQLServerDSL
extends DSL {
    protected SQLServerDSL() {
    }

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    public static Field<String> soundex(String string) {
        return SQLServerDSL.soundex(SQLServerDSL.val((Object)string, String.class));
    }

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    public static Field<String> soundex(Field<String> field2) {
        return SQLServerDSL.function("soundex", String.class, field2);
    }

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> difference(String value1, String value2) {
        return SQLServerDSL.difference(SQLServerDSL.val((Object)value1, String.class), SQLServerDSL.val((Object)value2, String.class));
    }

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> difference(Field<String> value1, String value2) {
        return SQLServerDSL.difference(SQLServerDSL.nullSafe(value1), SQLServerDSL.val((Object)value2, String.class));
    }

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> difference(String value1, Field<String> value2) {
        return SQLServerDSL.difference(SQLServerDSL.val((Object)value1, String.class), SQLServerDSL.nullSafe(value2));
    }

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    public static Field<Integer> difference(Field<String> value1, Field<String> value2) {
        return SQLServerDSL.function("difference", Integer.class, value1, value2);
    }
}

