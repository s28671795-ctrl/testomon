/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.database;

import com.incoresoft.middleware.model.Model;
import org.jooq.Converter;
import org.jooq.RecordMapper;
import org.jooq.UpdatableRecord;

public interface CRUDConverter<R extends UpdatableRecord<R>, M extends Model>
extends Converter<R, M>,
RecordMapper<R, M> {
    @Override
    default public M map(R r) {
        return (M)((Model)this.from(r));
    }
}

