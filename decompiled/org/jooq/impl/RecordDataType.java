/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.Nullability;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class RecordDataType<R extends Record>
extends DefaultDataType<R> {
    final AbstractRow<R> row;

    RecordDataType(Row row) {
        this(row, Tools.recordType(row.size()), "record");
    }

    RecordDataType(Row row, Class<R> recordType, String name) {
        super(null, recordType, name, name);
        this.row = (AbstractRow)row;
    }

    RecordDataType(DefaultDataType<R> t2, AbstractRow<R> row, Integer precision, Integer scale, Integer length, Nullability nullability, boolean readonly, Generator<?, ?, R> generatedAlwaysAs, QOM.GenerationOption generationOption, QOM.GenerationLocation generationLocation, Collation collation, CharacterSet characterSet, boolean identity, Field<R> defaultValue) {
        super(t2, precision, scale, length, nullability, readonly, generatedAlwaysAs, generationOption, generationLocation, collation, characterSet, identity, defaultValue);
        this.row = row;
    }

    @Override
    DefaultDataType<R> construct(Integer newPrecision, Integer newScale, Integer newLength, Nullability newNullability, boolean newReadonly, Generator<?, ?, R> newGeneratedAlwaysAs, QOM.GenerationOption newGenerationOption, QOM.GenerationLocation newGenerationLocation, Collation newCollation, CharacterSet newCharacterSet, boolean newIdentity, Field<R> newDefaultValue) {
        return new RecordDataType<R>(this, this.row, newPrecision, newScale, newLength, newNullability, newReadonly, newGeneratedAlwaysAs, newGenerationOption, newGenerationLocation, newCollation, newCharacterSet, newIdentity, newDefaultValue);
    }

    @Override
    public final Row getRow() {
        return this.row;
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return this.getType();
    }

    @Override
    public R convert(Object object) {
        if (object instanceof Record && ((Record)object).fieldsRow().equals(this.row)) {
            return (R)((Record)object);
        }
        if (object instanceof Record || object instanceof Map || object instanceof List) {
            return (R)Tools.newRecord(true, this.getRecordType(), this.row, Tools.CONFIG.get()).operate(r -> {
                if (object instanceof Record) {
                    ((AbstractRecord)r).fromArray(((Record)object).intoArray());
                } else if (object instanceof Map) {
                    r.from(((Map)object).entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(Map.Entry::getValue).collect(Collectors.toList()));
                } else {
                    r.from(object);
                }
                return r;
            });
        }
        return (R)((Record)super.convert(object));
    }
}

