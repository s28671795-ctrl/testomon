/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.sqlite;

import org.jooq.Field;
import org.jooq.impl.DSL;

@Deprecated(forRemoval=true, since="3.15")
public class SQLiteDSL
extends DSL {
    protected SQLiteDSL() {
    }

    public static Field<Long> rowid() {
        return SQLiteDSL.field("_rowid_", Long.class);
    }
}

