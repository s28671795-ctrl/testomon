/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.SQLInput;
import org.jetbrains.annotations.NotNull;
import org.jooq.BindingScope;
import org.jooq.Converter;
import org.jooq.ExecuteScope;

public interface BindingGetSQLInputContext<U>
extends ExecuteScope,
BindingScope {
    @NotNull
    public SQLInput input();

    public void value(U var1);

    @NotNull
    public <T> BindingGetSQLInputContext<T> convert(Converter<? super T, ? extends U> var1);
}

