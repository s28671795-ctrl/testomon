/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.DataType;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractParam;
import org.jooq.tools.JooqLogger;

abstract class AbstractParamX<T>
extends AbstractField<T>
implements Param<T> {
    private static final JooqLogger log = JooqLogger.getLogger(AbstractParam.class);

    AbstractParamX(Name name, DataType<T> type) {
        super(name, type);
    }

    @Override
    @Deprecated
    public final void setValue(T value) {
        log.warn("Deprecation", "org.jooq.Param will soon be made immutable. It is recommended to no longer use its deprecated, mutating methods.", new UnsupportedOperationException("Param.setValue"));
        this.setConverted0(value);
    }

    final void setValue0(T value) {
        this.setConverted0(value);
    }

    @Override
    @Deprecated
    public final void setConverted(Object value) {
        log.warn("Deprecation", "org.jooq.Param will soon be made immutable. It is recommended to no longer use its deprecated, mutating methods.", new UnsupportedOperationException("Param.setConverted"));
        this.setConverted0(value);
    }

    abstract void setConverted0(Object var1);

    @Override
    @Deprecated
    public final void setInline(boolean inline2) {
        log.warn("Deprecation", "org.jooq.Param will soon be made immutable. It is recommended to no longer use its deprecated, mutating methods.", new UnsupportedOperationException("Param.setInline"));
        this.setInline0(inline2);
    }

    abstract void setInline0(boolean var1);
}

