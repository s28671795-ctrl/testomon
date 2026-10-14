/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;
import org.jooq.impl.Cache;
import org.jooq.impl.CacheType;
import org.jooq.impl.DefaultRecordMapper;

public class DefaultRecordMapperProvider
implements RecordMapperProvider,
Serializable {
    private final Configuration configuration;

    public DefaultRecordMapperProvider() {
        this(null);
    }

    protected DefaultRecordMapperProvider(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> rowType, Class<? extends E> type) {
        if (this.configuration != null && Boolean.TRUE.equals(this.configuration.settings().isCacheRecordMappers())) {
            return Cache.run(this.configuration, () -> new DefaultRecordMapper(rowType, type, this.configuration), CacheType.CACHE_RECORD_MAPPERS, () -> Cache.key(rowType, type));
        }
        return new DefaultRecordMapper<R, E>(rowType, type, this.configuration);
    }
}

