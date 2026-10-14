/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.RecordUnmapper;
import org.jooq.RecordUnmapperProvider;
import org.jooq.impl.DefaultRecordUnmapper;

public class DefaultRecordUnmapperProvider
implements RecordUnmapperProvider,
Serializable {
    private final Configuration configuration;

    public DefaultRecordUnmapperProvider() {
        this(null);
    }

    protected DefaultRecordUnmapperProvider(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final <E, R extends Record> RecordUnmapper<E, R> provide(Class<? extends E> type, RecordType<R> rowType) {
        return new DefaultRecordUnmapper<E, R>(type, rowType, this.configuration);
    }
}

