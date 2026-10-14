/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.ResultSet;
import org.jetbrains.annotations.NotNull;
import org.jooq.BindingScope;
import org.jooq.Converter;
import org.jooq.ExecuteScope;
import org.jooq.Field;

public interface BindingGetResultSetContext<U>
extends ExecuteScope,
BindingScope {
    @NotNull
    public ResultSet resultSet();

    public int index();

    @NotNull
    public Field<U> field();

    public void value(U var1);

    @NotNull
    public <T> BindingGetResultSetContext<T> convert(Converter<? super T, ? extends U> var1);
}

