/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.SQLOutput;
import org.jetbrains.annotations.NotNull;
import org.jooq.BindingScope;
import org.jooq.Converter;
import org.jooq.ExecuteScope;
import org.jooq.ResourceManagingScope;

public interface BindingSetSQLOutputContext<U>
extends ResourceManagingScope,
ExecuteScope,
BindingScope {
    @NotNull
    public SQLOutput output();

    public U value();

    @NotNull
    public <T> BindingSetSQLOutputContext<T> convert(Converter<? extends T, ? super U> var1);
}

