/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.ArrayRecord;
import org.jooq.AssociativeArrayRecord;
import org.jooq.Binding;
import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Configuration;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.Converters;
import org.jooq.Domain;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.GeneratorStatementType;
import org.jooq.Named;
import org.jooq.Nullability;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.QOM;

public interface DataType<T>
extends Named {
    @Nullable
    public DataType<T> getSQLDataType();

    @NotNull
    public DataType<T> getDataType(Configuration var1);

    public int getSQLType();

    public int getSQLType(Configuration var1);

    @NotNull
    public Binding<?, T> getBinding();

    @NotNull
    public ContextConverter<?, T> getConverter();

    @NotNull
    public Class<T> getType();

    @Nullable
    public Domain<T> getDomain();

    @Nullable
    public Row getRow();

    @Nullable
    public Class<? extends Record> getRecordType();

    @NotNull
    public Class<T[]> getArrayType();

    @NotNull
    public DataType<T[]> getArrayDataType() throws DataTypeException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public DataType<T[]> array() throws DataTypeException;

    @Nullable
    public Class<?> getArrayComponentType();

    @Nullable
    public DataType<?> getArrayComponentDataType();

    @NotNull
    public Class<?> getArrayBaseType();

    @NotNull
    public DataType<?> getArrayBaseDataType();

    @NotNull
    @Pro
    public <A extends ArrayRecord<?>> DataType<A> asArrayDataType(Class<A> var1);

    @NotNull
    @Pro
    public <A extends AssociativeArrayRecord<?, ?>> DataType<A> asAssociativeArrayDataType(Class<A> var1);

    @NotNull
    public <E extends EnumType> DataType<E> asEnumDataType(Class<E> var1);

    @NotNull
    public <U> DataType<U> asConvertedDataType(Converter<? super T, U> var1);

    @NotNull
    default public <U> DataType<U> asConvertedDataType(Class<U> toType, Function<? super T, ? extends U> from, Function<? super U, ? extends T> to) {
        return this.asConvertedDataType(Converter.of(this.getType(), toType, from, to));
    }

    @NotNull
    default public <U> DataType<U> asConvertedDataTypeFrom(Class<U> toType, Function<? super T, ? extends U> from) {
        return this.asConvertedDataType(Converter.from(this.getType(), toType, from));
    }

    @NotNull
    default public <U> DataType<U> asConvertedDataTypeFrom(Function<? super T, ? extends U> from) {
        return this.asConvertedDataType(Converter.from(this.getType(), Converters.UnknownType.class, from));
    }

    @NotNull
    default public <U> DataType<U> asConvertedDataTypeTo(Class<U> toType, Function<? super U, ? extends T> to) {
        return this.asConvertedDataType(Converter.to(this.getType(), toType, to));
    }

    @NotNull
    default public <U> DataType<U> asConvertedDataTypeTo(Function<? super U, ? extends T> to) {
        return this.asConvertedDataType(Converter.to(this.getType(), Converters.UnknownType.class, to));
    }

    @NotNull
    public <U> DataType<U> asConvertedDataType(Binding<? super T, U> var1);

    @NotNull
    public String getTypeName();

    @NotNull
    public String getTypeName(Configuration var1);

    @NotNull
    public String getCastTypeName();

    @NotNull
    public String getCastTypeName(Configuration var1);

    @Nullable
    public SQLDialect getDialect();

    public T convert(Object var1);

    public T @NotNull [] convert(Object ... var1);

    @NotNull
    public List<T> convert(Collection<?> var1);

    @Support
    @NotNull
    public DataType<T> nullability(Nullability var1);

    @NotNull
    public Nullability nullability();

    @Support
    @NotNull
    public DataType<T> nullable(boolean var1);

    public boolean nullable();

    @Support
    @NotNull
    public DataType<T> readonly(boolean var1);

    public boolean readonly();

    public boolean readonlyInternal();

    public boolean readonlyInternal(Configuration var1);

    public boolean computed();

    public boolean computedOnServer();

    public boolean computedOnServer(Configuration var1);

    public boolean computedOnClient();

    public boolean computedOnClient(Configuration var1);

    public boolean computedOnClientStored();

    public boolean computedOnClientStored(Configuration var1);

    public boolean computedOnClientStoredOn(GeneratorStatementType var1);

    public boolean computedOnClientStoredOn(GeneratorStatementType var1, Configuration var2);

    public boolean computedOnClientVirtual();

    public boolean computedOnClientVirtual(Configuration var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER})
    @NotNull
    public DataType<T> generatedAlwaysAs(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER})
    @NotNull
    public DataType<T> generatedAlwaysAs(Field<T> var1);

    @NotNull
    public DataType<T> generatedAlwaysAs(Generator<?, ?, T> var1);

    @Nullable
    public Field<T> generatedAlwaysAs();

    @Nullable
    public Generator<?, ?, T> generatedAlwaysAsGenerator();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER})
    @NotNull
    public DataType<T> stored();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER})
    @NotNull
    public DataType<T> virtual();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE12C, SQLDialect.POSTGRES_12, SQLDialect.SQLSERVER})
    @NotNull
    public DataType<T> generationOption(QOM.GenerationOption var1);

    @Support
    @NotNull
    public QOM.GenerationOption generationOption();

    @Support
    @NotNull
    public DataType<T> generationLocation(QOM.GenerationLocation var1);

    @Support
    @NotNull
    public QOM.GenerationLocation generationLocation();

    @Support
    @NotNull
    public DataType<T> null_();

    @Support
    @NotNull
    public DataType<T> notNull();

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public DataType<T> collation(Collation var1);

    @Nullable
    public Collation collation();

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    public DataType<T> characterSet(CharacterSet var1);

    @Nullable
    public CharacterSet characterSet();

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public DataType<T> identity(boolean var1);

    public boolean identity();

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public DataType<T> defaultValue(T var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public DataType<T> defaultValue(Field<T> var1);

    @Nullable
    public Field<T> defaultValue();

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public DataType<T> default_(T var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public DataType<T> default_(Field<T> var1);

    @Nullable
    public Field<T> default_();

    @Deprecated(forRemoval=true, since="3.8")
    @NotNull
    public DataType<T> defaulted(boolean var1);

    public boolean defaulted();

    @Support
    @NotNull
    public DataType<T> precision(int var1);

    @Support
    @NotNull
    public DataType<T> precision(int var1, int var2);

    public int precision();

    public boolean hasPrecision();

    public boolean precisionDefined();

    @Support
    @NotNull
    public DataType<T> scale(int var1);

    public int scale();

    public boolean hasScale();

    public boolean scaleDefined();

    @Support
    @NotNull
    public DataType<T> length(int var1);

    public int length();

    public boolean hasLength();

    public boolean lengthDefined();

    public boolean isNumeric();

    public boolean isInteger();

    public boolean isFloat();

    public boolean isBoolean();

    public boolean isString();

    public boolean isNString();

    public boolean isDateTime();

    public boolean isDate();

    public boolean isTimestamp();

    public boolean isTime();

    public boolean isTemporal();

    public boolean isInterval();

    public boolean isBinary();

    public boolean isLob();

    public boolean isArray();

    public boolean isAssociativeArray();

    public boolean isEmbeddable();

    public boolean isUDT();

    public boolean isRecord();

    public boolean isMultiset();

    public boolean isEnum();

    public boolean isJSON();

    public boolean isXML();

    public boolean isSpatial();
}

