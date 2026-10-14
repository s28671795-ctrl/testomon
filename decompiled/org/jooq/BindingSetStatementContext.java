/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.PreparedStatement;
import org.jetbrains.annotations.NotNull;
import org.jooq.BindingScope;
import org.jooq.Converter;
import org.jooq.ExecuteScope;
import org.jooq.ResourceManagingScope;

public interface BindingSetStatementContext<U>
extends ResourceManagingScope,
ExecuteScope,
BindingScope {
    @NotNull
    public PreparedStatement statement();

    public int index();

    public U value();

    @NotNull
    public <T> BindingSetStatementContext<T> convert(Converter<? extends T, ? super U> var1);
}

