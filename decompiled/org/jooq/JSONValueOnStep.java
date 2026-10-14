/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.JSONValueDefaultStep;
import org.jooq.JSONValueReturningStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONValueOnStep<J>
extends JSONValueReturningStep<J> {
    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public JSONValueOnStep<J> errorOnEmpty();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public JSONValueOnStep<J> errorOnError();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public JSONValueOnStep<J> nullOnEmpty();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public JSONValueOnStep<J> nullOnError();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public JSONValueDefaultStep<J> default_(Field<?> var1);
}

