/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.CreateTypeFinalStep;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface CreateTypeStep {
    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTypeFinalStep asEnum();

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTypeFinalStep asEnum(String ... var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTypeFinalStep asEnum(Field<String> ... var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTypeFinalStep asEnum(Collection<?> var1);
}

