/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.apibuilder;

import io.javalin.http.Context;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH&\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\f\u00c0\u0006\u0001"}, d2={"Lio/javalin/apibuilder/CrudHandler;", "", "create", "", "ctx", "Lio/javalin/http/Context;", "delete", "resourceId", "", "getAll", "getOne", "update", "javalin"})
public interface CrudHandler {
    public void getAll(@NotNull Context var1);

    public void getOne(@NotNull Context var1, @NotNull String var2);

    public void create(@NotNull Context var1);

    public void update(@NotNull Context var1, @NotNull String var2);

    public void delete(@NotNull Context var1, @NotNull String var2);
}

