/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jooq.AttachableQueryPart;
import org.jooq.Configuration;
import org.jooq.Param;
import org.jooq.RenderContext;
import org.jooq.conf.ParamType;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.Tools;

abstract class AbstractAttachableQueryPart
extends AbstractQueryPart
implements AttachableQueryPart {
    private Configuration configuration;

    AbstractAttachableQueryPart(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final void attach(Configuration c) {
        this.configuration = c;
    }

    @Override
    public final void detach() {
        this.attach(null);
    }

    @Override
    public final Configuration configuration() {
        return this.configuration;
    }

    @NotNull
    final Configuration configurationOrDefault() {
        return Tools.configuration(this);
    }

    @NotNull
    final Configuration configurationOrThrow() {
        return Tools.configurationOrThrow(this);
    }

    @Override
    public final List<Object> getBindValues() {
        return this.create().extractBindValues(this);
    }

    @Override
    public final Map<String, Param<?>> getParams() {
        return this.create().extractParams(this);
    }

    @Override
    public final Param<?> getParam(String name) {
        return this.create().extractParam(this, name);
    }

    @Override
    public final String getSQL() {
        return this.getSQL(SettingsTools.getParamType(Tools.settings(this.configuration())));
    }

    @Override
    public final String getSQL(ParamType paramType) {
        switch (paramType) {
            case INDEXED: {
                return this.create().render(this);
            }
            case INLINED: {
                return this.create().renderInlined(this);
            }
            case NAMED: {
                return this.create().renderNamedParams(this);
            }
            case NAMED_OR_INLINED: {
                return this.create().renderNamedOrInlinedParams(this);
            }
            case FORCE_INDEXED: {
                return ((RenderContext)((RenderContext)this.create().renderContext().paramType(paramType)).visit(this)).render();
            }
        }
        throw new IllegalArgumentException("ParamType not supported: " + String.valueOf((Object)paramType));
    }
}

