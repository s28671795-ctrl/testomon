/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import org.jooq.ArrayRecord;
import org.jooq.AssociativeArrayRecord;
import org.jooq.Binding;
import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Comment;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Domain;
import org.jooq.EmbeddableRecord;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.GeneratorStatementType;
import org.jooq.Geography;
import org.jooq.Geometry;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.Nullability;
import org.jooq.Pro;
import org.jooq.QualifiedRecord;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.XML;
import org.jooq.impl.AbstractDataTypeX;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.ArrayDataType;
import org.jooq.impl.Convert;
import org.jooq.impl.ConvertedDataType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.DefaultGeneratorContext;
import org.jooq.impl.Internal;
import org.jooq.impl.LazyName;
import org.jooq.impl.MultisetDataType;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.types.Interval;
import org.jooq.types.UNumber;

abstract class AbstractDataType<T>
extends AbstractNamed
implements DataType<T>,
QOM.UEmpty {
    static final Set<SQLDialect> NO_SUPPORT_TIMESTAMP_PRECISION = SQLDialect.supportedBy(SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA);

    AbstractDataType(Name name, Comment comment) {
        super(name, comment);
    }

    @Override
    public abstract DataType<T> nullability(Nullability var1);

    @Override
    public final DataType<T> nullable(boolean n) {
        return this.nullability(Nullability.of(n));
    }

    @Override
    public final boolean nullable() {
        return this.nullability().nullable();
    }

    @Override
    public final DataType<T> null_() {
        return this.nullable(true);
    }

    @Override
    public final DataType<T> notNull() {
        return this.nullable(false);
    }

    @Override
    public abstract boolean readonly();

    @Override
    public final boolean readonlyInternal() {
        return this.readonlyInternal(Tools.CONFIG.get());
    }

    @Override
    public final boolean readonlyInternal(Configuration configuration) {
        return this.readonly() && !this.computedOnClientStored(configuration);
    }

    @Override
    public abstract DataType<T> readonly(boolean var1);

    @Override
    public final boolean computed() {
        return this.generatedAlwaysAsGenerator() != null;
    }

    @Override
    public final boolean computedOnServer() {
        return this.computedOnServer(Tools.CONFIG.get());
    }

    @Override
    public final boolean computedOnServer(Configuration configuration) {
        return this.computed() && this.generationLocation(configuration) == QOM.GenerationLocation.SERVER;
    }

    @Override
    public final boolean computedOnClient() {
        return this.computedOnClient(Tools.CONFIG.get());
    }

    @Override
    public final boolean computedOnClient(Configuration configuration) {
        return this.computed() && this.generationLocation(configuration) == QOM.GenerationLocation.CLIENT;
    }

    @Override
    public final boolean computedOnClientStored() {
        return this.computedOnClientStored(Tools.CONFIG.get());
    }

    @Override
    public final boolean computedOnClientStored(Configuration configuration) {
        return this.computedOnClient(configuration) && this.generationOption(configuration) != QOM.GenerationOption.VIRTUAL && (this.generatedAlwaysAsGenerator().supports(GeneratorStatementType.INSERT) || this.generatedAlwaysAsGenerator().supports(GeneratorStatementType.UPDATE));
    }

    @Override
    public final boolean computedOnClientStoredOn(GeneratorStatementType statementType) {
        return this.computedOnClientStoredOn(statementType, Tools.CONFIG.get());
    }

    @Override
    public final boolean computedOnClientStoredOn(GeneratorStatementType statementType, Configuration configuration) {
        return this.computedOnClient(configuration) && this.generationOption(configuration) != QOM.GenerationOption.VIRTUAL && this.generatedAlwaysAsGenerator().supports(statementType);
    }

    @Override
    public final boolean computedOnClientVirtual() {
        return this.computedOnClientVirtual(Tools.CONFIG.get());
    }

    @Override
    public final boolean computedOnClientVirtual(Configuration configuration) {
        return this.computedOnClient(configuration) && this.generationOption(configuration) == QOM.GenerationOption.VIRTUAL && this.generatedAlwaysAsGenerator().supports(GeneratorStatementType.SELECT);
    }

    @Override
    public final DataType<T> generatedAlwaysAs(T g2) {
        return this.generatedAlwaysAs(Tools.field(g2, this));
    }

    @Override
    public final DataType<T> generatedAlwaysAs(Field<T> generatedAlwaysAsValue) {
        return this.generatedAlwaysAs(t2 -> generatedAlwaysAsValue);
    }

    @Override
    public abstract DataType<T> generatedAlwaysAs(Generator<?, ?, T> var1);

    @Override
    public final Field<T> generatedAlwaysAs() {
        Generator s2 = this.generatedAlwaysAsGenerator();
        return s2 == null ? null : (Field)s2.apply(new DefaultGeneratorContext(Tools.CONFIG.get()));
    }

    @Override
    public abstract Generator<?, ?, T> generatedAlwaysAsGenerator();

    @Override
    public final DataType<T> stored() {
        return this.generationOption(QOM.GenerationOption.STORED);
    }

    @Override
    public final DataType<T> virtual() {
        return this.generationOption(QOM.GenerationOption.VIRTUAL);
    }

    @Override
    public abstract DataType<T> generationOption(QOM.GenerationOption var1);

    @Override
    public abstract QOM.GenerationOption generationOption();

    final QOM.GenerationOption generationOption(Configuration configuration) {
        return Boolean.TRUE.equals(Tools.settings(configuration).isEmulateComputedColumns()) ? QOM.GenerationOption.STORED : this.generationOption();
    }

    @Override
    public abstract DataType<T> generationLocation(QOM.GenerationLocation var1);

    @Override
    public abstract QOM.GenerationLocation generationLocation();

    final QOM.GenerationLocation generationLocation(Configuration configuration) {
        return Boolean.TRUE.equals(Tools.settings(configuration).isEmulateComputedColumns()) ? QOM.GenerationLocation.CLIENT : this.generationLocation();
    }

    @Override
    public abstract DataType<T> collation(Collation var1);

    @Override
    public abstract DataType<T> characterSet(CharacterSet var1);

    @Override
    public abstract DataType<T> identity(boolean var1);

    @Override
    public final DataType<T> defaultValue(T d) {
        return this.default_(d);
    }

    @Override
    public final DataType<T> defaultValue(Field<T> d) {
        return this.default_(d);
    }

    @Override
    public final Field<T> defaultValue() {
        return this.default_();
    }

    @Override
    public final DataType<T> default_(T d) {
        return this.default_(Tools.field(d, this));
    }

    @Override
    public abstract DataType<T> default_(Field<T> var1);

    @Override
    @Deprecated
    public final DataType<T> defaulted(boolean d) {
        return this.defaultValue(d ? Tools.field(null, this) : null);
    }

    @Override
    public final boolean defaulted() {
        return this.defaultValue() != null;
    }

    @Override
    public final int precision() {
        Integer precision = this.precision0();
        return precision == null ? 0 : precision;
    }

    @Override
    public final DataType<T> precision(int p) {
        return this.precision0(p, this.scale());
    }

    @Override
    public final DataType<T> precision(int p, int s2) {
        return this.precision0(p, s2);
    }

    final AbstractDataType<T> precision0(Integer p, Integer s2) {
        if (AbstractDataType.eq(this.precision0(), p) && AbstractDataType.eq(this.scale0(), s2)) {
            return this;
        }
        if (this.isLob()) {
            return this;
        }
        return this.precision1(p, s2);
    }

    abstract AbstractDataType<T> precision1(Integer var1, Integer var2);

    @Override
    public final boolean hasPrecision() {
        Class<?> tType = this.tType0();
        return tType == BigInteger.class || tType == BigDecimal.class || tType == Timestamp.class || tType == Time.class || tType == LocalDateTime.class || tType == LocalTime.class || tType == OffsetDateTime.class || tType == OffsetTime.class || tType == Instant.class;
    }

    @Override
    public final boolean precisionDefined() {
        return this.precision0() != null && this.hasPrecision();
    }

    @Override
    public final int scale() {
        Integer scale = this.scale0();
        return scale == null ? 0 : scale;
    }

    @Override
    public final DataType<T> scale(int s2) {
        return this.scale0(s2);
    }

    final AbstractDataType<T> scale0(Integer s2) {
        if (AbstractDataType.eq(this.scale0(), s2)) {
            return this;
        }
        if (this.isLob()) {
            return this;
        }
        return this.scale1(s2);
    }

    abstract AbstractDataType<T> scale1(Integer var1);

    @Override
    public final boolean hasScale() {
        return this.tType0() == BigDecimal.class;
    }

    @Override
    public final boolean scaleDefined() {
        return this.scale0() != null && this.hasScale();
    }

    @Override
    public final DataType<T> length(int l) {
        return this.length0(l);
    }

    final AbstractDataType<T> length0(Integer l) {
        if (AbstractDataType.eq(this.length0(), l)) {
            return this;
        }
        if (this.isLob()) {
            return this;
        }
        return this.length1(l);
    }

    abstract AbstractDataType<T> length1(Integer var1);

    @Override
    public final int length() {
        Integer length = this.length0();
        return length == null ? 0 : length;
    }

    @Override
    public final boolean hasLength() {
        Class<?> tType = this.tType0();
        return (tType == byte[].class || tType == String.class) && !this.isLob();
    }

    @Override
    public final boolean lengthDefined() {
        return this.length0() != null && this.hasLength();
    }

    @Override
    public int getSQLType() {
        return this.getSQLType(DSL.using(this.getDialect()).configuration());
    }

    @Override
    public final int getSQLType(Configuration configuration) {
        Class<?> tType = this.tType0();
        if (tType == Blob.class) {
            return 2004;
        }
        if (tType == Boolean.class) {
            return 16;
        }
        if (tType == BigInteger.class) {
            return -5;
        }
        if (tType == BigDecimal.class) {
            return 3;
        }
        if (tType == Byte.class) {
            return -6;
        }
        if (tType == byte[].class) {
            return 2004;
        }
        if (tType == Clob.class) {
            return 2005;
        }
        if (Tools.isDate(tType)) {
            switch (configuration.family()) {
                case ORACLE: {
                    return 93;
                }
            }
            return 91;
        }
        if (tType == Double.class) {
            return 8;
        }
        if (tType == Float.class) {
            return 6;
        }
        if (tType == Integer.class) {
            return 4;
        }
        if (tType == Long.class) {
            return -5;
        }
        if (tType == Short.class) {
            return 5;
        }
        if (tType == String.class) {
            return 12;
        }
        if (Tools.isTime(tType)) {
            return 92;
        }
        if (Tools.isTimestamp(tType)) {
            return 93;
        }
        if (tType == OffsetTime.class) {
            return 12;
        }
        if (tType == OffsetDateTime.class) {
            return 12;
        }
        if (tType == Instant.class) {
            return 12;
        }
        if (tType.isArray()) {
            return 2003;
        }
        if (ArrayRecord.class.isAssignableFrom(tType)) {
            switch (configuration.family()) {
                case SQLSERVER: {
                    return -153;
                }
            }
            return 2003;
        }
        if (AssociativeArrayRecord.class.isAssignableFrom(tType)) {
            return -14;
        }
        if (EnumType.class.isAssignableFrom(tType)) {
            return 12;
        }
        if (QualifiedRecord.class.isAssignableFrom(tType)) {
            return 2002;
        }
        if (Result.class.isAssignableFrom(tType)) {
            switch (configuration.family()) {
                case ORACLE: 
                case H2: {
                    return -10;
                }
            }
            return 1111;
        }
        return 1111;
    }

    @Override
    public Domain<T> getDomain() {
        return null;
    }

    @Override
    public final ContextConverter<?, T> getConverter() {
        return ContextConverter.scoped(this.getBinding().converter());
    }

    @Override
    public String getTypeName() {
        return this.getTypeName0(Tools.CONFIG_UNQUOTED.get());
    }

    private final String getTypeName0(Configuration configuration) {
        if (this.isEnum() || this.isUDT()) {
            return this.renderedTypeName0(configuration);
        }
        return this.typeName0();
    }

    @Override
    public String getTypeName(Configuration configuration) {
        return ((AbstractDataType)this.getDataType(configuration)).getTypeName0(configuration);
    }

    @Override
    public String getCastTypeName() {
        return this.getCastTypeName0(Tools.CONFIG_UNQUOTED.get());
    }

    private final String getCastTypeName0(Configuration configuration) {
        SQLDialect dialect = configuration.dialect();
        if (this.isEnum() || this.isUDT()) {
            return this.renderedTypeName0(configuration);
        }
        if (this.lengthDefined() && this.length() > 0) {
            if (this.isBinary() && Tools.NO_SUPPORT_BINARY_TYPE_LENGTH.contains((Object)dialect)) {
                return this.castTypeName0();
            }
            if (this.isString() && Tools.NO_SUPPORT_STRING_LENGTH.contains((Object)dialect)) {
                return this.castTypeName0();
            }
            return this.castTypePrefix0() + "(" + this.length() + ")" + this.castTypeSuffix0();
        }
        if (this.precisionDefined() && (this.isTimestamp() || this.precision() > 0)) {
            if (this.isTimestamp() && NO_SUPPORT_TIMESTAMP_PRECISION.contains((Object)dialect)) {
                return this.castTypePrefix0() + this.castTypeSuffix0();
            }
            if (this.isTimestamp() && dialect == SQLDialect.INFORMIX && this.precision() == 0) {
                return "datetime year to second";
            }
            if (Tools.NO_SUPPORT_NUMERIC_PRECISION.contains((Object)dialect)) {
                return this.castTypeName0();
            }
            if (this.scaleDefined() && this.scale() > 0) {
                return this.castTypePrefix0() + "(" + this.precision() + ", " + this.scale() + ")" + this.castTypeSuffix0();
            }
            return this.castTypePrefix0() + "(" + this.precision() + ")" + this.castTypeSuffix0();
        }
        return this.castTypeName0();
    }

    @Override
    public String getCastTypeName(Configuration configuration) {
        return ((AbstractDataType)this.getDataType(configuration)).getCastTypeName0(configuration);
    }

    private final String renderedTypeName0(Configuration configuration) {
        return configuration.dsl().render(this);
    }

    @Override
    public final Class<T[]> getArrayType() {
        return Internal.arrayType(this.getType());
    }

    @Override
    public DataType<T[]> getArrayDataType() {
        return new ArrayDataType(this);
    }

    @Override
    public final DataType<T[]> array() {
        return this.getArrayDataType();
    }

    @Override
    public Class<?> getArrayComponentType() {
        return null;
    }

    @Override
    public DataType<?> getArrayComponentDataType() {
        return null;
    }

    @Override
    public Class<?> getArrayBaseType() {
        return this.getType();
    }

    @Override
    public DataType<?> getArrayBaseDataType() {
        return this;
    }

    @Override
    public Row getRow() {
        return null;
    }

    @Override
    public Class<? extends Record> getRecordType() {
        return null;
    }

    @Override
    @Pro
    public final <A extends ArrayRecord<?>> DataType<A> asArrayDataType(Class<A> arrayDataType) {
        return new DefaultDataType<A>(this.getDialect(), arrayDataType, this.typeName0(), this.castTypeName0());
    }

    @Override
    @Pro
    public final <A extends AssociativeArrayRecord<?, ?>> DataType<A> asAssociativeArrayDataType(Class<A> arrayDataType) {
        return new DefaultDataType<A>(this.getDialect(), arrayDataType, this.typeName0(), this.castTypeName0());
    }

    @Override
    public final <E extends EnumType> DataType<E> asEnumDataType(Class<E> enumDataType) {
        EnumType e = Tools.enums(enumDataType)[0];
        return new DefaultDataType<E>(this.getDialect(), null, enumDataType, null, AbstractDataType.lazyName(e), e.getName(), e.getName(), this.precision0(), this.scale0(), this.length0(), this.nullability(), this.readonly(), this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), this.collation(), this.characterSet(), this.identity(), this.defaultValue());
    }

    static final <E extends EnumType> Name lazyName(E e) {
        return new LazyName(() -> AbstractDataType.lazyName(e::getSchema, () -> DSL.name(e.getName())));
    }

    static final Name lazyName(Supplier<Schema> schema, Supplier<Name> name) {
        Schema s2 = schema.get();
        return s2 == null ? name.get() : s2.getQualifiedName().append(name.get());
    }

    @Override
    public <U> DataType<U> asConvertedDataType(Converter<? super T, U> converter) {
        return this.asConvertedDataType(DefaultBinding.newBinding(converter, this, null));
    }

    @Override
    public <U> DataType<U> asConvertedDataType(Binding<? super T, U> newBinding) {
        if (this.getBinding() == newBinding) {
            return this;
        }
        if (newBinding == null) {
            newBinding = DefaultBinding.binding(this);
        }
        return new ConvertedDataType<T, U>((AbstractDataTypeX)this, newBinding);
    }

    @Override
    public T convert(Object object) {
        if (object == null) {
            return null;
        }
        if (object.getClass() == this.getType()) {
            return (T)object;
        }
        return Convert.convert(object, this.getType());
    }

    @Override
    public final T[] convert(Object ... objects) {
        return Tools.map(objects, o -> this.convert(o), l -> (Object[])Array.newInstance(this.getType(), l));
    }

    @Override
    public final List<T> convert(Collection<?> objects) {
        return Tools.map(objects, o -> this.convert(o));
    }

    @Override
    public final boolean isNumeric() {
        return Number.class.isAssignableFrom(this.tType0()) && !this.isInterval();
    }

    @Override
    public final boolean isInteger() {
        Class<?> tType = this.tType0();
        return UNumber.class.isAssignableFrom(tType) || Byte.class == tType || Short.class == tType || Integer.class == tType || Long.class == tType;
    }

    @Override
    public final boolean isFloat() {
        Class<?> tType = this.tType0();
        return Float.class == tType || Double.class == tType;
    }

    @Override
    public final boolean isBoolean() {
        return this.tType0() == Boolean.class;
    }

    @Override
    public final boolean isString() {
        return this.tType0() == String.class;
    }

    @Override
    public final boolean isNString() {
        AbstractDataType t2 = (AbstractDataType)this.getSQLDataType();
        return t2 == SQLDataType.NCHAR || t2 == SQLDataType.NCLOB || t2 == SQLDataType.NVARCHAR || SQLDataType.NCHAR == null && "nchar".equals(t2.typeName0()) || SQLDataType.NCLOB == null && "nclob".equals(t2.typeName0()) || SQLDataType.NVARCHAR == null && "nvarchar".equals(t2.typeName0());
    }

    @Override
    public final boolean isDateTime() {
        Class<?> tType = this.tType0();
        return java.util.Date.class.isAssignableFrom(tType) || Temporal.class.isAssignableFrom(tType);
    }

    @Override
    public final boolean isDate() {
        Class<?> tType = this.tType0();
        return Date.class.isAssignableFrom(tType) || LocalDate.class.isAssignableFrom(tType);
    }

    @Override
    public final boolean isTimestamp() {
        Class<?> tType = this.tType0();
        return Timestamp.class.isAssignableFrom(tType) || LocalDateTime.class.isAssignableFrom(tType);
    }

    @Override
    public final boolean isTime() {
        Class<?> tType = this.tType0();
        return Time.class.isAssignableFrom(tType) || LocalTime.class.isAssignableFrom(tType);
    }

    @Override
    public final boolean isTemporal() {
        return this.isDateTime() || this.isInterval();
    }

    @Override
    public final boolean isInterval() {
        return Interval.class.isAssignableFrom(this.tType0());
    }

    @Override
    public final boolean isLob() {
        AbstractDataType t2 = (AbstractDataType)this.getSQLDataType();
        if (t2 == this) {
            return this.typeName0().endsWith("lob");
        }
        return t2 == SQLDataType.BLOB || t2 == SQLDataType.CLOB || t2 == SQLDataType.NCLOB || SQLDataType.BLOB == null && "blob".equals(t2.typeName0()) || SQLDataType.CLOB == null && "clob".equals(t2.typeName0()) || SQLDataType.NCLOB == null && "nclob".equals(t2.typeName0());
    }

    @Override
    public final boolean isBinary() {
        return this.tType0() == byte[].class;
    }

    @Override
    public final boolean isArray() {
        Class<?> tType = this.tType0();
        return ArrayRecord.class.isAssignableFrom(tType) || !this.isBinary() && tType.isArray();
    }

    @Override
    public final boolean isAssociativeArray() {
        return AssociativeArrayRecord.class.isAssignableFrom(this.tType0());
    }

    @Override
    public final boolean isEmbeddable() {
        return EmbeddableRecord.class.isAssignableFrom(this.tType0());
    }

    @Override
    public final boolean isUDT() {
        return QualifiedRecord.class.isAssignableFrom(this.tType0());
    }

    @Override
    public boolean isRecord() {
        return Record.class.isAssignableFrom(this.tType0());
    }

    @Override
    public boolean isMultiset() {
        return this instanceof MultisetDataType;
    }

    @Override
    public final boolean isEnum() {
        return EnumType.class.isAssignableFrom(this.tType0());
    }

    @Override
    public final boolean isJSON() {
        Class<?> tType = this.tType0();
        return tType == JSON.class || tType == JSONB.class;
    }

    @Override
    public final boolean isXML() {
        return this.tType0() == XML.class;
    }

    @Override
    public final boolean isSpatial() {
        Class<?> tType = this.tType0();
        return tType == Geometry.class || tType == Geography.class;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                DataType t2 = this.getSQLDataType();
                ctx.sql("SQLDataType.").sql(t2 == null ? "OTHER" : t2.getTypeName().toUpperCase().replace(" ", ""));
                if (this.lengthDefined()) {
                    ctx.sql('(').sql(this.length()).sql(')');
                    break;
                }
                if (!this.precisionDefined()) break;
                if (this.scaleDefined()) {
                    ctx.sql('(').sql(this.precision()).sql(", ").sql(this.scale()).sql(')');
                    break;
                }
                ctx.sql('(').sql(this.precision()).sql(')');
                break;
            }
            default: {
                Tools.visitMappedSchema(ctx, this.getQualifiedName());
            }
        }
    }

    private static final boolean eq(Integer i1, Integer i2) {
        return i1 == i2 || i1 != null && i2 != null && i1.intValue() == i2.intValue();
    }

    abstract String typeName0();

    abstract String castTypePrefix0();

    abstract String castTypeSuffix0();

    abstract String castTypeName0();

    abstract Class<?> tType0();

    abstract Class<T> uType0();

    abstract Integer precision0();

    abstract Integer scale0();

    abstract Integer length0();

    @Override
    public String toString() {
        return this.getCastTypeName() + " /* " + this.getType().getName() + " */";
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.getDialect() == null ? 0 : this.getDialect().hashCode());
        result = 31 * result + this.length();
        result = 31 * result + this.precision();
        result = 31 * result + this.scale();
        result = 31 * result + this.getType().hashCode();
        result = 31 * result + (this.tType0() == null ? 0 : this.tType0().hashCode());
        result = 31 * result + (this.typeName0() == null ? 0 : this.typeName0().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractDataType)) {
            return false;
        }
        AbstractDataType other = (AbstractDataType)obj;
        if (this.getDialect() != other.getDialect()) {
            return false;
        }
        if (!AbstractDataType.eq(this.length0(), other.length0())) {
            return false;
        }
        if (!AbstractDataType.eq(this.precision0(), other.precision0())) {
            return false;
        }
        if (!AbstractDataType.eq(this.scale0(), other.scale0())) {
            return false;
        }
        if (!this.getType().equals(other.getType())) {
            return false;
        }
        if (this.tType0() == null ? other.tType0() != null : !this.tType0().equals(other.tType0())) {
            return false;
        }
        return !(this.typeName0() == null ? other.typeName0() != null : !this.typeName0().equals(other.typeName0()));
    }
}

