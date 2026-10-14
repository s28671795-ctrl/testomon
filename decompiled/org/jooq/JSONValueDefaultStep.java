/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.JSONValueOnStep;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface JSONValueDefaultStep<J> {
    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public JSONValueOnStep<J> onEmpty();

    @Support(value={SQLDialect.DB2, SQLDialect.ORACLE12C})
    @NotNull
    @Pro
    public JSONValueOnStep<J> onError();
}

