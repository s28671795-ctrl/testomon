/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateTriggerEventOrStep;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Support;

@Pro
public interface CreateTriggerEventOfStep
extends CreateTriggerEventOrStep {
    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep of(String ... var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep of(Name ... var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep of(Field<?> ... var1);

    @Support(value={SQLDialect.DB2, SQLDialect.HSQLDB, SQLDialect.ORACLE})
    @NotNull
    @CheckReturnValue
    public CreateTriggerEventOrStep of(Collection<? extends Field<?>> var1);
}

