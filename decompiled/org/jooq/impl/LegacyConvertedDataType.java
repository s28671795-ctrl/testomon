/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.Nullability;
import org.jooq.impl.AbstractDataTypeX;
import org.jooq.impl.ChainedConverterBinding;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.Internal;
import org.jooq.impl.QOM;

@Deprecated
final class LegacyConvertedDataType<T, U>
extends DefaultDataType<U> {
    private final AbstractDataTypeX<T> delegate;

    LegacyConvertedDataType(AbstractDataTypeX<T> delegate, Binding<? super T, U> binding) {
        super(null, binding.converter().toType(), binding, delegate.getQualifiedName(), delegate.getTypeName(), delegate.getCastTypeName(), delegate.precisionDefined() ? Integer.valueOf(delegate.precision()) : null, delegate.scaleDefined() ? Integer.valueOf(delegate.scale()) : null, delegate.lengthDefined() ? Integer.valueOf(delegate.length()) : null, delegate.nullability(), delegate.defaultValue());
        this.delegate = delegate;
    }

    @Override
    DefaultDataType<U> construct(Integer newPrecision, Integer newScale, Integer newLength, Nullability newNullability, boolean newReadonly, Generator<?, ?, U> newGeneratedAlwaysAs, QOM.GenerationOption newGenerationOption, QOM.GenerationLocation newGenerationLocation, Collation newCollation, CharacterSet newCharacterSet, boolean newIdentity, Field<U> newDefaultValue) {
        return new LegacyConvertedDataType(this.delegate.construct(newPrecision, newScale, newLength, newNullability, newReadonly, newGeneratedAlwaysAs, newGenerationOption, newGenerationLocation, newCollation, newCharacterSet, newIdentity, newDefaultValue), this.getBinding());
    }

    @Override
    public int getSQLType() {
        return this.delegate.getSQLType();
    }

    @Override
    public String getTypeName(Configuration configuration) {
        return this.delegate.getTypeName(configuration);
    }

    @Override
    public String getCastTypeName(Configuration configuration) {
        return this.delegate.getCastTypeName(configuration);
    }

    @Override
    public U convert(Object object) {
        if (this.getConverter().toType().isInstance(object)) {
            return (U)object;
        }
        return (U)this.getConverter().from(this.delegate.convert(object), Internal.converterContext());
    }

    @Override
    public <X> DataType<X> asConvertedDataType(Converter<? super U, X> converter) {
        return super.asConvertedDataType((Binding)new ChainedConverterBinding(this.getBinding(), converter));
    }
}

