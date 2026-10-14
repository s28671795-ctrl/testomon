/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.QueryPartInternal;
import org.jooq.impl.AbstractBindContext;
import org.jooq.tools.StringUtils;

final class ParamCollector
extends AbstractBindContext {
    final Map<String, Param<?>> resultFlat = new LinkedHashMap();
    final Map<String, List<Param<?>>> result = new LinkedHashMap();
    final List<Map.Entry<String, Param<?>>> resultList = new ArrayList();
    private final boolean includeInlinedParams;

    ParamCollector(Configuration configuration, boolean includeInlinedParams) {
        super(configuration, null, null);
        this.includeInlinedParams = includeInlinedParams;
    }

    @Override
    protected final void bindInternal(QueryPartInternal internal) {
        if (internal instanceof Param) {
            Param param2 = (Param)((Object)internal);
            if (this.includeInlinedParams || !param2.isInline()) {
                String i = String.valueOf(this.nextIndex());
                String paramName = param2.getParamName();
                if (StringUtils.isBlank(paramName)) {
                    this.resultFlat.put(i, param2);
                    this.resultList.add(new AbstractMap.SimpleImmutableEntry<String, Param>(i, param2));
                    this.result(i).add(param2);
                } else {
                    this.resultFlat.put(param2.getParamName(), param2);
                    this.resultList.add(new AbstractMap.SimpleImmutableEntry<String, Param>(param2.getParamName(), param2));
                    this.result(param2.getParamName()).add(param2);
                }
            }
        } else {
            super.bindInternal(internal);
        }
    }

    private final List<Param<?>> result(String key) {
        return this.result.computeIfAbsent(key, k -> new ArrayList());
    }

    @Override
    protected final BindContext bindValue0(Object value, Field<?> field2) throws SQLException {
        throw new UnsupportedOperationException();
    }
}

