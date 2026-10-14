/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jooq.SQLDialect;

public enum SQLDialectCategory {
    OTHER,
    MYSQL,
    POSTGRES,
    TSQL;

    private EnumSet<SQLDialect> dialects;
    private EnumSet<SQLDialect> families;

    @NotNull
    public final Set<SQLDialect> dialects() {
        if (this.dialects == null) {
            this.dialects = this.filter(SQLDialect.values());
        }
        return Collections.unmodifiableSet(this.dialects);
    }

    @NotNull
    public final Set<SQLDialect> families() {
        if (this.families == null) {
            this.families = this.filter(SQLDialect.families());
        }
        return Collections.unmodifiableSet(this.families);
    }

    private final EnumSet<SQLDialect> filter(SQLDialect[] values2) {
        EnumSet<SQLDialect> set = EnumSet.noneOf(SQLDialect.class);
        for (SQLDialect family : values2) {
            if (family.category() != this) continue;
            set.add(family);
        }
        return set;
    }
}

