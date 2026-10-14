/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.CallableStatement;
import org.jetbrains.annotations.NotNull;
import org.jooq.BindingScope;
import org.jooq.Converter;
import org.jooq.ExecuteScope;

public interface BindingGetStatementContext<U>
extends ExecuteScope,
BindingScope {
    @NotNull
    public CallableStatement statement();

    public int index();

    public void value(U var1);

    @NotNull
    public <T> BindingGetStatementContext<T> convert(Converter<? super T, ? extends U> var1);
}

