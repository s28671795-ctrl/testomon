/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.Nullability;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractDataType;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class ArrayDataType<T>
extends DefaultDataType<T[]> {
    final DataType<T> elementType;

    public ArrayDataType(DataType<T> elementType) {
        super(null, elementType.getArrayType(), elementType.getTypeName() + " array", elementType.getCastTypeName() + " array");
        this.elementType = elementType;
    }

    ArrayDataType(AbstractDataType<T[]> t2, DataType<T> elementType, Integer precision, Integer scale, Integer length, Nullability nullability, boolean readonly, Generator<?, ?, T[]> generatedAlwaysAs, QOM.GenerationOption generationOption, QOM.GenerationLocation generationLocation, Collation collation, CharacterSet characterSet, boolean identity, Field<T[]> defaultValue) {
        super(t2, precision, scale, length, nullability, readonly, generatedAlwaysAs, generationOption, generationLocation, collation, characterSet, identity, defaultValue);
        this.elementType = elementType;
    }

    @Override
    DefaultDataType<T[]> construct(Integer newPrecision, Integer newScale, Integer newLength, Nullability newNullability, boolean newReadonly, Generator<?, ?, T[]> newGeneratedAlwaysAs, QOM.GenerationOption newGenerationOption, QOM.GenerationLocation newGenerationLocation, Collation newCollation, CharacterSet newCharacterSet, boolean newIdentity, Field<T[]> newDefaultValue) {
        return new ArrayDataType<T>(this, (AbstractDataType)this.elementType, newPrecision, newScale, newLength, newNullability, newReadonly, newGeneratedAlwaysAs, newGenerationOption, newGenerationLocation, newCollation, newCharacterSet, newIdentity, newDefaultValue);
    }

    @Override
    public final String getTypeName() {
        return this.getTypeName(Tools.CONFIG.get());
    }

    @Override
    public final String getTypeName(Configuration configuration) {
        String typeName = this.elementType.getTypeName(configuration);
        return ArrayDataType.getArrayType(configuration, typeName);
    }

    @Override
    public final String getCastTypeName() {
        return this.getCastTypeName(Tools.CONFIG.get());
    }

    @Override
    public final String getCastTypeName(Configuration configuration) {
        String castTypeName = this.elementType.getCastTypeName(configuration);
        return ArrayDataType.getArrayType(configuration, castTypeName);
    }

    @Override
    public final Class<?> getArrayComponentType() {
        return this.elementType.getType();
    }

    @Override
    public final DataType<?> getArrayComponentDataType() {
        return this.elementType;
    }

    @Override
    public final Class<?> getArrayBaseType() {
        return this.getArrayBaseDataType().getType();
    }

    @Override
    public final DataType<?> getArrayBaseDataType() {
        DataType<?> t2;
        DataType<T[]> result = this;
        while ((t2 = result.getArrayComponentDataType()) != null) {
            result = t2;
        }
        return result;
    }

    private static String getArrayType(Configuration configuration, String dataType) {
        switch (configuration.family()) {
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                return dataType + "[]";
            }
            case H2: {
                if (configuration.dialect().precedes(SQLDialect.H2_1_4_200)) {
                    return "array";
                }
                return dataType + " array";
            }
        }
        return dataType + " array";
    }
}

