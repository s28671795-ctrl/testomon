/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.time.Instant;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;

public interface Scope {
    @NotNull
    public Instant creationTime();

    @NotNull
    public Configuration configuration();

    @NotNull
    public DSLContext dsl();

    @NotNull
    public Settings settings();

    @NotNull
    public SQLDialect dialect();

    @NotNull
    public SQLDialect family();

    @NotNull
    public Map<Object, Object> data();

    @Nullable
    public Object data(Object var1);

    @Nullable
    public Object data(Object var1, Object var2);
}

