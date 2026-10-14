/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Attachable;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.conf.ParamType;

public interface AttachableQueryPart
extends Attachable,
QueryPart {
    @NotNull
    public String getSQL();

    @NotNull
    public String getSQL(ParamType var1);

    @NotNull
    public List<Object> getBindValues();

    @NotNull
    public Map<String, Param<?>> getParams();

    @Nullable
    public Param<?> getParam(String var1);
}

