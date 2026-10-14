/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONExistsOnStep
extends Condition {
    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public Condition errorOnError();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public Condition falseOnError();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public Condition trueOnError();

    @Support(value={SQLDialect.DB2})
    @NotNull
    @Pro
    public Condition unknownOnError();
}

