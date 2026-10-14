/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.IdentityHashMap;
import org.jooq.Binding;
import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.Name;
import org.jooq.Nullability;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractDataType;
import org.jooq.impl.QOM;
import org.jooq.tools.StringUtils;

final class DataTypeProxy<T>
extends AbstractDataType<T> {
    private AbstractDataType<T> type;
    private final Integer overridePrecision;
    private final Integer overrideScale;
    private final Integer overrideLength;
    private final Nullability overrideNullability;
    private final Boolean overrideReadonly;
    private final Generator<?, ?, T> overrideGeneratedAlwaysAs;
    private final QOM.GenerationOption overrideGenerationOption;
    private final QOM.GenerationLocation overrideGenerationLocation;
    private final Collation overrideCollation;
    private final CharacterSet overrideCharacterSet;
    private final Boolean overrideIdentity;
    private final Field<T> overrideDefaultValue;

    DataTypeProxy(AbstractDataType<T> type) {
        this(type, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    private DataTypeProxy(AbstractDataType<T> type, Integer overridePrecision, Integer overrideScale, Integer overrideLength, Nullability overrideNullability, Boolean overrideReadonly, Generator<?, ?, T> overrideGeneratedAlwaysAs, QOM.GenerationOption overrideGenerationOption, QOM.GenerationLocation overrideGenerationLocation, Collation overrideCollation, CharacterSet overrideCharacterSet, Boolean overrideIdentity, Field<T> overrideDefaultValue) {
        super(type.getQualifiedName(), type.getCommentPart());
        this.type = type;
        this.overridePrecision = overridePrecision;
        this.overrideScale = overrideScale;
        this.overrideLength = overrideLength;
        this.overrideNullability = overrideNullability;
        this.overrideReadonly = overrideReadonly;
        this.overrideGeneratedAlwaysAs = overrideGeneratedAlwaysAs;
        this.overrideGenerationOption = overrideGenerationOption;
        this.overrideGenerationLocation = overrideGenerationLocation;
        this.overrideCollation = overrideCollation;
        this.overrideCharacterSet = overrideCharacterSet;
        this.overrideIdentity = overrideIdentity;
        this.overrideDefaultValue = overrideDefaultValue;
    }

    final AbstractDataType<T> type() {
        return this.type;
    }

    final void type(AbstractDataType<T> t2) {
        if (t2 instanceof DataTypeProxy) {
            AbstractDataType<T> abstractDataType;
            DataTypeProxy p = (DataTypeProxy)t2;
            IdentityHashMap<DataTypeProxy, DataTypeProxy> m4 = new IdentityHashMap<DataTypeProxy, DataTypeProxy>();
            m4.put(this, this);
            m4.put(p, p);
            while ((abstractDataType = p.type()) instanceof DataTypeProxy) {
                DataTypeProxy p2 = (DataTypeProxy)abstractDataType;
                if (m4.put(p2, p2) != null) {
                    return;
                }
                p = p2;
            }
        }
        this.type = t2;
    }

    @Override
    public final Name getQualifiedName() {
        return this.type.getQualifiedName();
    }

    @Override
    public final DataType<T> getSQLDataType() {
        return this.type.getSQLDataType();
    }

    @Override
    public final DataType<T> getDataType(Configuration configuration) {
        return this.type.getDataType(configuration);
    }

    @Override
    public final Binding<?, T> getBinding() {
        return this.type.getBinding();
    }

    @Override
    public final Class<T> getType() {
        return this.type.getType();
    }

    @Override
    public final SQLDialect getDialect() {
        return this.type.getDialect();
    }

    @Override
    public final Nullability nullability() {
        return StringUtils.defaultIfNull(this.overrideNullability, this.type.nullability());
    }

    @Override
    public final DataType<T> nullability(Nullability n) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, this.overrideLength, n, this.overrideReadonly, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, this.overrideGenerationLocation, this.overrideCollation, this.overrideCharacterSet, this.overrideIdentity, this.overrideDefaultValue);
    }

    @Override
    public final boolean readonly() {
        return StringUtils.defaultIfNull(this.overrideReadonly, this.type.readonly());
    }

    @Override
    public final DataType<T> readonly(boolean r) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, this.overrideLength, this.overrideNullability, r, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, this.overrideGenerationLocation, this.overrideCollation, this.overrideCharacterSet, this.overrideIdentity, this.overrideDefaultValue);
    }

    @Override
    public final Generator<?, ?, T> generatedAlwaysAsGenerator() {
        return StringUtils.defaultIfNull(this.overrideGeneratedAlwaysAs, this.type.generatedAlwaysAsGenerator());
    }

    @Override
    public final DataType<T> generatedAlwaysAs(Generator<?, ?, T> g2) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, this.overrideLength, this.overrideNullability, this.overrideReadonly, g2, this.overrideGenerationOption, this.overrideGenerationLocation, this.overrideCollation, this.overrideCharacterSet, this.overrideIdentity, this.overrideDefaultValue);
    }

    @Override
    public final QOM.GenerationOption generationOption() {
        return StringUtils.defaultIfNull(this.overrideGenerationOption, this.type.generationOption());
    }

    @Override
    public final DataType<T> generationOption(QOM.GenerationOption g2) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, this.overrideLength, this.overrideNullability, this.overrideReadonly, this.overrideGeneratedAlwaysAs, g2, this.overrideGenerationLocation, this.overrideCollation, this.overrideCharacterSet, this.overrideIdentity, this.overrideDefaultValue);
    }

    @Override
    public final QOM.GenerationLocation generationLocation() {
        return StringUtils.defaultIfNull(this.overrideGenerationLocation, this.type.generationLocation());
    }

    @Override
    public final DataType<T> generationLocation(QOM.GenerationLocation g2) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, this.overrideLength, this.overrideNullability, this.overrideReadonly, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, g2, this.overrideCollation, this.overrideCharacterSet, this.overrideIdentity, this.overrideDefaultValue);
    }

    @Override
    public final Collation collation() {
        return StringUtils.defaultIfNull(this.overrideCollation, this.type.collation());
    }

    @Override
    public final DataType<T> collation(Collation c) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, this.overrideLength, this.overrideNullability, this.overrideReadonly, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, this.overrideGenerationLocation, c, this.overrideCharacterSet, this.overrideIdentity, this.overrideDefaultValue);
    }

    @Override
    public final CharacterSet characterSet() {
        return StringUtils.defaultIfNull(this.overrideCharacterSet, this.type.characterSet());
    }

    @Override
    public final DataType<T> characterSet(CharacterSet c) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, this.overrideLength, this.overrideNullability, this.overrideReadonly, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, this.overrideGenerationLocation, this.overrideCollation, c, this.overrideIdentity, this.overrideDefaultValue);
    }

    @Override
    public final boolean identity() {
        return StringUtils.defaultIfNull(this.overrideIdentity, this.type.identity());
    }

    @Override
    public final DataType<T> identity(boolean i) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, this.overrideLength, this.overrideNullability, this.overrideReadonly, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, this.overrideGenerationLocation, this.overrideCollation, this.overrideCharacterSet, i, this.overrideDefaultValue);
    }

    @Override
    public final Field<T> default_() {
        return StringUtils.defaultIfNull(this.overrideDefaultValue, this.type.default_());
    }

    @Override
    public final DataType<T> default_(Field<T> d) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, this.overrideLength, this.overrideNullability, this.overrideReadonly, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, this.overrideGenerationLocation, this.overrideCollation, this.overrideCharacterSet, this.overrideIdentity, d);
    }

    @Override
    final String typeName0() {
        return this.type.typeName0();
    }

    @Override
    final String castTypePrefix0() {
        return this.type.castTypePrefix0();
    }

    @Override
    final String castTypeSuffix0() {
        return this.type.castTypeSuffix0();
    }

    @Override
    final String castTypeName0() {
        return this.type.castTypeName0();
    }

    @Override
    final Class<?> tType0() {
        return this.type.tType0();
    }

    @Override
    final Class<T> uType0() {
        return this.type.uType0();
    }

    @Override
    final Integer precision0() {
        return StringUtils.defaultIfNull(this.overridePrecision, this.type.precision0());
    }

    @Override
    final AbstractDataType<T> precision1(Integer p, Integer s2) {
        return new DataTypeProxy<T>(this, p, s2, this.overrideLength, this.overrideNullability, this.overrideReadonly, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, this.overrideGenerationLocation, this.overrideCollation, this.overrideCharacterSet, this.overrideIdentity, this.overrideDefaultValue);
    }

    @Override
    final Integer scale0() {
        return StringUtils.defaultIfNull(this.overrideScale, this.type.scale0());
    }

    @Override
    final AbstractDataType<T> scale1(Integer s2) {
        return new DataTypeProxy<T>(this, this.overridePrecision, s2, this.overrideLength, this.overrideNullability, this.overrideReadonly, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, this.overrideGenerationLocation, this.overrideCollation, this.overrideCharacterSet, this.overrideIdentity, this.overrideDefaultValue);
    }

    @Override
    final Integer length0() {
        return StringUtils.defaultIfNull(this.overrideLength, this.type.length0());
    }

    @Override
    final AbstractDataType<T> length1(Integer l) {
        return new DataTypeProxy<T>(this, this.overridePrecision, this.overrideScale, l, this.overrideNullability, this.overrideReadonly, this.overrideGeneratedAlwaysAs, this.overrideGenerationOption, this.overrideGenerationLocation, this.overrideCollation, this.overrideCharacterSet, this.overrideIdentity, this.overrideDefaultValue);
    }
}

