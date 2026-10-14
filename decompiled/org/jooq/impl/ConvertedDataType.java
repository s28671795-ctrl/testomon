/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.Map;
import org.jooq.Binding;
import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Converters;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.Nullability;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractDataTypeX;
import org.jooq.impl.ChainedConverterBinding;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.Internal;
import org.jooq.impl.LegacyConvertedDataType;
import org.jooq.impl.QOM;

final class ConvertedDataType<T, U>
extends AbstractDataTypeX<U> {
    final AbstractDataTypeX<T> delegate;
    final Binding<? super T, U> binding;

    ConvertedDataType(AbstractDataTypeX<T> delegate, Binding<? super T, U> binding) {
        super(delegate.getQualifiedName(), delegate.getCommentPart());
        this.delegate = delegate;
        this.binding = binding;
        new LegacyConvertedDataType<T, U>(delegate, binding);
    }

    @Override
    AbstractDataTypeX<U> construct(Integer newPrecision, Integer newScale, Integer newLength, Nullability newNullability, boolean newReadonly, Generator<?, ?, U> newGeneratedAlwaysAs, QOM.GenerationOption newGenerationOption, QOM.GenerationLocation newGenerationLocation, Collation newCollation, CharacterSet newCharacterSet, boolean newIdentity, Field<U> newDefaultValue) {
        return (AbstractDataTypeX)this.delegate.construct(newPrecision, newScale, newLength, newNullability, newReadonly, newGeneratedAlwaysAs, newGenerationOption, newGenerationLocation, newCollation, newCharacterSet, newIdentity, newDefaultValue).asConvertedDataType(this.binding);
    }

    @Override
    public final Row getRow() {
        return this.delegate.getRow();
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        return this.delegate.getRecordType();
    }

    @Override
    public final boolean isRecord() {
        return this.delegate.isRecord();
    }

    @Override
    public final boolean isMultiset() {
        return this.delegate.isMultiset();
    }

    @Override
    public final DataType<U> getSQLDataType() {
        return this.delegate.getSQLDataType();
    }

    @Override
    public final DataType<U[]> getArrayDataType() {
        if (this.getBinding() instanceof DefaultBinding.InternalBinding) {
            return this.delegate.getArrayDataType().asConvertedDataType(Converters.forArrays(this.binding.converter()));
        }
        throw new DataTypeException("Cannot create array data types from custom data types with custom bindings.");
    }

    @Override
    public final DataType<?> getArrayComponentDataType() {
        DataType<?> d = this.delegate.getArrayComponentDataType();
        return d == null ? null : d.asConvertedDataType(Converters.forArrayComponents(this.binding.converter()));
    }

    @Override
    public final Class<?> getArrayComponentType() {
        DataType<?> d = this.getArrayComponentDataType();
        return d == null ? null : d.getType();
    }

    @Override
    public final DataType<U> getDataType(Configuration configuration) {
        return this.delegate.getDataType(configuration);
    }

    @Override
    public final String getCastTypeName() {
        return this.delegate.getCastTypeName();
    }

    @Override
    public final String getCastTypeName(Configuration configuration) {
        return this.delegate.getCastTypeName(configuration);
    }

    @Override
    public final String getTypeName() {
        return this.delegate.getTypeName();
    }

    @Override
    public final String getTypeName(Configuration configuration) {
        return this.delegate.getTypeName(configuration);
    }

    @Override
    public final Binding<?, U> getBinding() {
        return this.binding;
    }

    @Override
    public final Class<U> getType() {
        return this.binding.converter().toType();
    }

    @Override
    public final SQLDialect getDialect() {
        return this.delegate.getDialect();
    }

    @Override
    public final Nullability nullability() {
        return this.delegate.nullability();
    }

    @Override
    public final boolean readonly() {
        return this.delegate.readonly();
    }

    @Override
    public final Generator<?, ?, U> generatedAlwaysAsGenerator() {
        return this.delegate.generatedAlwaysAsGenerator();
    }

    @Override
    public final QOM.GenerationOption generationOption() {
        return this.delegate.generationOption();
    }

    @Override
    public final QOM.GenerationLocation generationLocation() {
        return this.delegate.generationLocation();
    }

    @Override
    public final Collation collation() {
        return this.delegate.collation();
    }

    @Override
    public final CharacterSet characterSet() {
        return this.delegate.characterSet();
    }

    @Override
    public final boolean identity() {
        return this.delegate.identity();
    }

    @Override
    public final Field<U> default_() {
        return this.delegate.default_();
    }

    @Override
    final String typeName0() {
        return this.delegate.typeName0();
    }

    @Override
    final String castTypePrefix0() {
        return this.delegate.castTypePrefix0();
    }

    @Override
    final String castTypeSuffix0() {
        return this.delegate.castTypeSuffix0();
    }

    @Override
    final String castTypeName0() {
        return this.delegate.castTypeName0();
    }

    @Override
    final Class<?> tType0() {
        return this.delegate.tType0();
    }

    @Override
    final Class<U> uType0() {
        return this.binding.converter().toType();
    }

    @Override
    final Integer precision0() {
        return this.delegate.precision0();
    }

    @Override
    final Integer scale0() {
        return this.delegate.scale0();
    }

    @Override
    final Integer length0() {
        return this.delegate.length0();
    }

    @Override
    public final U convert(Object object) {
        if (this.getConverter().toType().isInstance(object)) {
            return (U)object;
        }
        if (this.delegate.isMultiset() && !(object instanceof Result)) {
            return (U)object;
        }
        if (this.delegate.isRecord() && !(object instanceof Record) && !(object instanceof List) && !(object instanceof Map)) {
            return (U)object;
        }
        return (U)this.getConverter().from(this.delegate.convert(object), Internal.converterContext());
    }

    @Override
    public final <X> DataType<X> asConvertedDataType(Converter<? super U, X> converter) {
        return super.asConvertedDataType(new ChainedConverterBinding(this.getBinding(), converter));
    }

    final DataType<T> delegate() {
        DataType<T> dataType;
        AbstractDataTypeX<T> abstractDataTypeX = this.delegate;
        if (abstractDataTypeX instanceof ConvertedDataType) {
            ConvertedDataType c = (ConvertedDataType)abstractDataTypeX;
            dataType = c.delegate();
        } else {
            dataType = this.delegate;
        }
        return dataType;
    }

    static final DataType<?> delegate(DataType<?> type) {
        DataType<Object> dataType;
        if (type instanceof ConvertedDataType) {
            ConvertedDataType c = (ConvertedDataType)type;
            dataType = c.delegate();
        } else {
            dataType = type;
        }
        return dataType;
    }
}

