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
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.QOM;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.Tools;

final class MultisetDataType<R extends Record>
extends DefaultDataType<Result<R>> {
    final AbstractRow<R> row;
    final Class<? extends R> recordType;

    MultisetDataType(AbstractRow<R> row, Class<? extends R> recordType) {
        super(null, Result.class, "multiset", "multiset");
        this.row = row;
        this.recordType = recordType != null ? recordType : Record.class;
    }

    MultisetDataType(DefaultDataType<Result<R>> t2, AbstractRow<R> row, Class<? extends R> recordType, Integer precision, Integer scale, Integer length, Nullability nullability, boolean readonly, Generator<?, ?, Result<R>> generatedAlwaysAs, QOM.GenerationOption generationOption, QOM.GenerationLocation generationLocation, Collation collation, CharacterSet characterSet, boolean identity, Field<Result<R>> defaultValue) {
        super(t2, precision, scale, length, nullability, readonly, generatedAlwaysAs, generationOption, generationLocation, collation, characterSet, identity, defaultValue);
        this.row = row;
        this.recordType = recordType;
    }

    @Override
    DefaultDataType<Result<R>> construct(Integer newPrecision, Integer newScale, Integer newLength, Nullability newNullability, boolean newReadonly, Generator<?, ?, Result<R>> newGeneratedAlwaysAs, QOM.GenerationOption newGenerationOption, QOM.GenerationLocation newGenerationLocation, Collation newCollation, CharacterSet newCharacterSet, boolean newIdentity, Field<Result<R>> newDefaultValue) {
        return new MultisetDataType<R>(this, this.row, this.recordType, newPrecision, newScale, newLength, newNullability, newReadonly, newGeneratedAlwaysAs, newGenerationOption, newGenerationLocation, newCollation, newCharacterSet, newIdentity, newDefaultValue);
    }

    @Override
    public final Row getRow() {
        return this.row;
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return this.recordType;
    }

    @Override
    public Result<R> convert(Object object) {
        if (object instanceof Result && ((Result)object).fieldsRow().equals(this.row)) {
            return (Result)object;
        }
        if (object instanceof List) {
            List l = (List)object;
            ResultImpl<Record> result = new ResultImpl<Record>(Tools.CONFIG.get(), this.row);
            for (Object record : l) {
                result.add(Tools.newRecord(true, this.recordType, this.row, Tools.CONFIG.get()).operate(r -> {
                    if (record instanceof Record) {
                        ((AbstractRecord)r).fromArray(((Record)record).intoArray());
                    } else if (record instanceof Map) {
                        r.from(((Map)record).entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(Map.Entry::getValue).collect(Collectors.toList()));
                    } else {
                        r.from(record);
                    }
                    return r;
                }));
            }
            return result;
        }
        if (object == null) {
            return new ResultImpl(Tools.CONFIG.get(), (AbstractRow)this.row);
        }
        return (Result)super.convert(object);
    }
}

