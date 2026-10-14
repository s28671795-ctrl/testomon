/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Map;
import org.jooq.Configuration;
import org.jooq.ConverterContext;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.DataMap;

final class DefaultConverterContext
extends AbstractScope
implements ConverterContext {
    DefaultConverterContext(Configuration configuration) {
        super(configuration, new DataMap());
    }

    DefaultConverterContext(Configuration configuration, Map<Object, Object> data) {
        super(configuration, data);
    }
}

