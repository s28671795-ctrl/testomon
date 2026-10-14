/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.microsoft.sqlserver.jdbc.Geography
 *  com.microsoft.sqlserver.jdbc.Geometry
 *  com.microsoft.sqlserver.jdbc.ISQLServerPreparedStatement
 *  com.microsoft.sqlserver.jdbc.ISQLServerResultSet
 *  oracle.jdbc.OracleConnection
 *  oracle.jdbc.OraclePreparedStatement
 *  oracle.sql.OracleJdbc2SQLInput
 *  oracle.sql.OracleSQLOutput
 *  oracle.sql.TIMESTAMPTZ
 */
package org.jooq.impl;

import com.microsoft.sqlserver.jdbc.ISQLServerPreparedStatement;
import com.microsoft.sqlserver.jdbc.ISQLServerResultSet;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.sql.OracleJdbc2SQLInput;
import oracle.sql.OracleSQLOutput;
import oracle.sql.TIMESTAMPTZ;
import org.jooq.ArrayOrAssociativeArrayRecord;
import org.jooq.ArrayRecord;
import org.jooq.AssociativeArrayRecord;
import org.jooq.Attachable;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingScope;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ConverterContext;
import org.jooq.Converters;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.ExecuteScope;
import org.jooq.Field;
import org.jooq.Geography;
import org.jooq.Geometry;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.Package;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QualifiedRecord;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.RowId;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Scope;
import org.jooq.Source;
import org.jooq.Spatial;
import org.jooq.UDT;
import org.jooq.UDTField;
import org.jooq.UDTRecord;
import org.jooq.XML;
import org.jooq.conf.NestedCollectionEmulation;
import org.jooq.conf.ParamType;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.MappingException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.ArrayRecordImpl;
import org.jooq.impl.BlobBinding;
import org.jooq.impl.Cast;
import org.jooq.impl.ClobBinding;
import org.jooq.impl.Convert;
import org.jooq.impl.ConvertedDataType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBindingGetResultSetContext;
import org.jooq.impl.DefaultBindingGetStatementContext;
import org.jooq.impl.DefaultBindingSQLContext;
import org.jooq.impl.DefaultBindingSetStatementContext;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.Internal;
import org.jooq.impl.InternalRecord;
import org.jooq.impl.JSONReader;
import org.jooq.impl.Keywords;
import org.jooq.impl.ListHandler;
import org.jooq.impl.NClobBinding;
import org.jooq.impl.Names;
import org.jooq.impl.R2DBC;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.RowIdImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.TableDataType;
import org.jooq.impl.ThrowingSupplier;
import org.jooq.impl.TimestampToJavaUtilDateConverter;
import org.jooq.impl.Tools;
import org.jooq.impl.UDTDataType;
import org.jooq.impl.UDTImpl;
import org.jooq.impl.UDTRecordImpl;
import org.jooq.impl.XMLHandler;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.Longs;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.jdbc.MockArray;
import org.jooq.tools.jdbc.MockResultSet;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.YearToMonth;
import org.jooq.types.YearToSecond;
import org.jooq.util.postgres.PostgresUtils;

public class DefaultBinding<T, U>
implements Binding<T, U> {
    static final JooqLogger log = JooqLogger.getLogger(DefaultBinding.class);
    private static final Set<SQLDialect> REQUIRE_JDBC_DATE_LITERAL = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    private static final long PG_DATE_POSITIVE_INFINITY = 9223372036825200000L;
    private static final long PG_DATE_NEGATIVE_INFINITY = -9223372036832400000L;
    final Binding<T, U> delegate;

    public static final <T, U> Binding<T, U> binding(Converter<T, U> converter) {
        return DefaultBinding.binding(DefaultDataType.getDataType(SQLDialect.DEFAULT, converter.fromType()), converter);
    }

    public static final <T> Binding<T, T> binding(DataType<T> dataType) {
        return DefaultBinding.binding(dataType, Converters.identity(dataType.getType()));
    }

    static final <T, U> Binding<T, U> binding(DataType<T> dataType, Converter<T, U> converter) {
        Class<T> type = converter.fromType();
        if (type == BigDecimal.class) {
            return new DefaultBigDecimalBinding<U>((DataType<BigDecimal>)dataType, converter);
        }
        if (type == BigInteger.class) {
            return new DefaultBigIntegerBinding<U>((DataType<BigInteger>)dataType, converter);
        }
        if (type == Blob.class) {
            return new DefaultBlobBinding<U>((DataType<Blob>)dataType, converter);
        }
        if (type == Boolean.class) {
            return new DefaultBooleanBinding<U>((DataType<Boolean>)dataType, converter);
        }
        if (type == Byte.class || type == Byte.TYPE) {
            return new DefaultByteBinding<U>((DataType<Byte>)dataType, converter);
        }
        if (type == byte[].class) {
            return new DefaultBytesBinding<U>((DataType<byte[]>)dataType, converter);
        }
        if (type == Clob.class) {
            return new DefaultClobBinding<U>((DataType<Clob>)dataType, converter);
        }
        if (type == java.sql.Date.class) {
            return new DefaultDateBinding<U>((DataType<java.sql.Date>)dataType, converter);
        }
        if (type == DayToSecond.class) {
            return new DefaultDayToSecondBinding<U>((DataType<DayToSecond>)dataType, converter);
        }
        if (type == Double.class || type == Double.TYPE) {
            return new DefaultDoubleBinding<U>((DataType<Double>)dataType, converter);
        }
        if (type == Float.class || type == Float.TYPE) {
            return new DefaultFloatBinding<U>((DataType<Float>)dataType, converter);
        }
        if (type == Geometry.class) {
            return new DefaultGeometryBinding<U>((DataType<Geometry>)dataType, converter);
        }
        if (type == Geography.class) {
            return new DefaultGeographyBinding<U>((DataType<Geography>)dataType, converter);
        }
        if (type == Integer.class || type == Integer.TYPE) {
            return new DefaultIntegerBinding<U>((DataType<Integer>)dataType, converter);
        }
        if (type == JSON.class) {
            return new DefaultJSONBinding<U>((DataType<JSON>)dataType, converter);
        }
        if (type == JSONB.class) {
            return new DefaultJSONBBinding<U>((DataType<JSONB>)dataType, converter);
        }
        if (type == XML.class) {
            return new DefaultXMLBinding<U>((DataType<XML>)dataType, converter);
        }
        if (type == LocalDate.class) {
            return new DelegatingBinding(dataType, ContextConverter.ofNullable(java.sql.Date.class, LocalDate.class, (BiFunction<java.sql.Date, ConverterContext, LocalDate> & Serializable)(t2, x) -> t2.toLocalDate(), (BiFunction<LocalDate, ConverterContext, java.sql.Date> & Serializable)(t2, x) -> java.sql.Date.valueOf(t2)), (ContextConverter)converter, c -> new DefaultDateBinding(SQLDataType.DATE, c));
        }
        if (type == LocalDateTime.class) {
            return new DelegatingBinding(dataType, ContextConverter.ofNullable(Timestamp.class, LocalDateTime.class, (BiFunction<Timestamp, ConverterContext, LocalDateTime> & Serializable)(t2, x) -> t2.toLocalDateTime(), (BiFunction<LocalDateTime, ConverterContext, Timestamp> & Serializable)(t2, x) -> Timestamp.valueOf(t2)), (ContextConverter)converter, c -> new DefaultTimestampBinding(SQLDataType.TIMESTAMP, c));
        }
        if (type == LocalTime.class) {
            return new DelegatingBinding(dataType, ContextConverter.ofNullable(Time.class, LocalTime.class, (BiFunction<Time, ConverterContext, LocalTime> & Serializable)(t2, x) -> t2.toLocalTime(), (BiFunction<LocalTime, ConverterContext, Time> & Serializable)(t2, x) -> Time.valueOf(t2)), (ContextConverter)converter, c -> new DefaultTimeBinding(SQLDataType.TIME, c));
        }
        if (type == Long.class || type == Long.TYPE) {
            return new DefaultLongBinding<U>((DataType<Long>)dataType, converter);
        }
        if (type == OffsetDateTime.class) {
            return new DefaultOffsetDateTimeBinding<U>((DataType<OffsetDateTime>)dataType, converter);
        }
        if (type == OffsetTime.class) {
            return new DefaultOffsetTimeBinding<U>((DataType<OffsetTime>)dataType, converter);
        }
        if (type == Instant.class) {
            return new DefaultInstantBinding<U>((DataType<Instant>)dataType, converter);
        }
        if (type == RowId.class) {
            return new DefaultRowIdBinding<U>((DataType<RowId>)dataType, converter);
        }
        if (type == Short.class || type == Short.TYPE) {
            return new DefaultShortBinding<U>((DataType<Short>)dataType, converter);
        }
        if (type == String.class) {
            if (dataType.isNString()) {
                return new DefaultNStringBinding<U>((DataType<String>)dataType, converter);
            }
            return new DefaultStringBinding<U>((DataType<String>)dataType, converter);
        }
        if (type == Time.class) {
            return new DefaultTimeBinding<U>((DataType<Time>)dataType, converter);
        }
        if (type == Timestamp.class) {
            return new DefaultTimestampBinding<U>((DataType<Timestamp>)dataType, converter);
        }
        if (type == Date.class) {
            return new DefaultTimestampBinding<U>((DataType<Timestamp>)dataType, Converters.of(TimestampToJavaUtilDateConverter.INSTANCE, converter));
        }
        if (type == UByte.class) {
            return new DelegatingBinding(dataType, ContextConverter.ofNullable(Short.class, UByte.class, (BiFunction<Short, ConverterContext, UByte> & Serializable)(t2, x) -> UByte.valueOf(t2), (BiFunction<UByte, ConverterContext, Short> & Serializable)(t2, x) -> t2.shortValue()), (ContextConverter)converter, c -> new DefaultShortBinding(SQLDataType.SMALLINT, c));
        }
        if (type == UInteger.class) {
            return new DelegatingBinding(dataType, ContextConverter.ofNullable(Long.class, UInteger.class, (BiFunction<Long, ConverterContext, UInteger> & Serializable)(t2, x) -> UInteger.valueOf(t2), (BiFunction<UInteger, ConverterContext, Long> & Serializable)(t2, x) -> t2.longValue()), (ContextConverter)converter, c -> new DefaultLongBinding(SQLDataType.BIGINT, c));
        }
        if (type == ULong.class) {
            return new DelegatingBinding(dataType, ContextConverter.ofNullable(BigInteger.class, ULong.class, (BiFunction<BigInteger, ConverterContext, ULong> & Serializable)(t2, x) -> ULong.valueOf(t2), (BiFunction<ULong, ConverterContext, BigInteger> & Serializable)(t2, x) -> t2.toBigInteger()), (ContextConverter)converter, c -> new DefaultBigIntegerBinding(SQLDataType.DECIMAL_INTEGER, c));
        }
        if (type == UShort.class) {
            return new DelegatingBinding(dataType, ContextConverter.ofNullable(Integer.class, UShort.class, (BiFunction<Integer, ConverterContext, UShort> & Serializable)(t2, x) -> UShort.valueOf(t2), (BiFunction<UShort, ConverterContext, Integer> & Serializable)(t2, x) -> t2.intValue()), (ContextConverter)converter, c -> new DefaultIntegerBinding(SQLDataType.INTEGER, c));
        }
        if (type == UUID.class) {
            return new DefaultUUIDBinding<U>((DataType<UUID>)dataType, converter);
        }
        if (type == YearToSecond.class) {
            return new DefaultYearToSecondBinding<U>((DataType<YearToSecond>)dataType, converter);
        }
        if (type == YearToMonth.class) {
            return new DefaultYearToMonthBinding<U>((DataType<YearToMonth>)dataType, converter);
        }
        if (type == Year.class) {
            return new DefaultYearBinding<U>((DataType<Year>)dataType, converter);
        }
        if (type.isArray()) {
            return new DefaultArrayBinding<U>((DataType<Object[]>)dataType, converter);
        }
        if (ArrayRecord.class.isAssignableFrom(type)) {
            return new DefaultArrayRecordBinding<U>((DataType<ArrayRecord<?>>)dataType, converter);
        }
        if (AssociativeArrayRecord.class.isAssignableFrom(type)) {
            return new DefaultAssociativeArrayRecordBinding<U>((DataType<AssociativeArrayRecord<?, ?>>)dataType, converter);
        }
        if (EnumType.class.isAssignableFrom(type)) {
            return new DefaultEnumTypeBinding<U>((DataType<EnumType>)dataType, converter);
        }
        if (Record.class.isAssignableFrom(type)) {
            return new DefaultRecordBinding<U>((DataType<Record>)dataType, converter);
        }
        if (Result.class.isAssignableFrom(type)) {
            return new DefaultResultBinding<U>((DataType<Result<?>>)dataType, converter);
        }
        return new DefaultOtherBinding<U>(dataType, converter);
    }

    @Deprecated(forRemoval=true)
    public DefaultBinding(Converter<T, U> converter) {
        this.delegate = DefaultBinding.binding(converter);
    }

    public DefaultBinding(Binding<T, U> delegate) {
        this.delegate = delegate;
    }

    static final <T, X, U> Binding<T, U> newBinding(final Converter<X, U> converter, DataType<T> dataType, final Binding<T, X> binding) {
        Binding<Object, Object> theBinding = converter == null && binding == null ? dataType.getBinding() : (converter == null ? binding : (binding == null ? DefaultBinding.binding(dataType, ContextConverter.scoped(converter)) : new Binding<T, U>(){
            final ContextConverter<T, U> theConverter;
            {
                this.theConverter = Converters.of(binding.converter(), converter);
            }

            @Override
            public Converter<T, U> converter() {
                return this.theConverter;
            }

            @Override
            public void sql(BindingSQLContext<U> ctx) throws SQLException {
                binding.sql(ctx.convert(converter));
            }

            @Override
            public void register(BindingRegisterContext<U> ctx) throws SQLException {
                binding.register(ctx.convert(converter));
            }

            @Override
            public void set(BindingSetStatementContext<U> ctx) throws SQLException {
                binding.set(ctx.convert(converter));
            }

            @Override
            public void set(BindingSetSQLOutputContext<U> ctx) throws SQLException {
                binding.set(ctx.convert(converter));
            }

            @Override
            public void get(BindingGetResultSetContext<U> ctx) throws SQLException {
                binding.get(ctx.convert(converter));
            }

            @Override
            public void get(BindingGetStatementContext<U> ctx) throws SQLException {
                binding.get(ctx.convert(converter));
            }

            @Override
            public void get(BindingGetSQLInputContext<U> ctx) throws SQLException {
                binding.get(ctx.convert(converter));
            }
        }));
        return theBinding;
    }

    static final Map<String, Class<?>> typeMap(Class<?> type, Scope scope) {
        return DefaultBinding.typeMap(type, scope, new HashMap());
    }

    static final Map<String, Class<?>> typeMap(Class<?> type, Scope scope, Map<String, Class<?>> result) {
        try {
            if (QualifiedRecord.class.isAssignableFrom(type)) {
                Class<?> t2 = type;
                result.put(Tools.getMappedUDTName(scope, t2), t2);
                for (Field<?> field2 : Tools.getRecordQualifier(t2).fields()) {
                    DefaultBinding.typeMap(field2.getType(), scope, result);
                }
            } else if (ArrayRecord.class.isAssignableFrom(type)) {
                Class<?> t3 = type;
                result.put(Tools.getMappedArrayNameString(scope, t3), t3);
                ArrayRecord r = (ArrayRecord)t3.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                Class e = r.getDataType().getType();
                DefaultBinding.typeMap(e, scope, result);
            }
        }
        catch (Exception e) {
            throw new MappingException("Error while collecting type map", e);
        }
        return result;
    }

    private static final long parse(Class<? extends Date> type, String date) throws SQLException {
        Long number = Longs.tryParse(date);
        if (number != null) {
            return number;
        }
        date = StringUtils.replace(date, "T", " ");
        if (type == Timestamp.class) {
            return Timestamp.valueOf(date).getTime();
        }
        if (type == java.sql.Date.class) {
            return java.sql.Date.valueOf(date.split(" ")[0]).getTime();
        }
        if (type == Time.class) {
            return Time.valueOf(date).getTime();
        }
        throw new SQLException("Could not parse date " + date);
    }

    @Override
    public Converter<T, U> converter() {
        return this.delegate.converter();
    }

    @Override
    public void sql(BindingSQLContext<U> ctx) throws SQLException {
        this.delegate.sql(ctx);
    }

    @Override
    public void register(BindingRegisterContext<U> ctx) throws SQLException {
        this.delegate.register(ctx);
    }

    @Override
    public void set(BindingSetStatementContext<U> ctx) throws SQLException {
        this.delegate.set(ctx);
    }

    @Override
    public void set(BindingSetSQLOutputContext<U> ctx) throws SQLException {
        this.delegate.set(ctx);
    }

    @Override
    public void get(BindingGetResultSetContext<U> ctx) throws SQLException {
        this.delegate.get(ctx);
    }

    @Override
    public void get(BindingGetStatementContext<U> ctx) throws SQLException {
        this.delegate.get(ctx);
    }

    @Override
    public void get(BindingGetSQLInputContext<U> ctx) throws SQLException {
        this.delegate.get(ctx);
    }

    public String toString() {
        return this.delegate.toString();
    }

    static final class DefaultBigDecimalBinding<U>
    extends InternalBinding<BigDecimal, U> {
        private static final Set<SQLDialect> BIND_AS_STRING = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.SQLITE);

        DefaultBigDecimalBinding(DataType<BigDecimal> dataType, Converter<BigDecimal, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, BigDecimal value) {
            ctx.render().sql(value.toString());
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setBigDecimal(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, BigDecimal value) throws SQLException {
            if (BIND_AS_STRING.contains((Object)ctx.dialect())) {
                ctx.statement().setString(ctx.index(), value.toString());
            } else {
                ctx.statement().setBigDecimal(ctx.index(), value);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, BigDecimal value) throws SQLException {
            ctx.output().writeBigDecimal(value);
        }

        @Override
        final BigDecimal get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.SQLITE) {
                return Convert.convert((Object)ctx.resultSet().getString(ctx.index()), BigDecimal.class);
            }
            return ctx.resultSet().getBigDecimal(ctx.index());
        }

        @Override
        final BigDecimal get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return ctx.statement().getBigDecimal(ctx.index());
        }

        @Override
        final BigDecimal get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return ctx.input().readBigDecimal();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 3;
        }
    }

    static final class DefaultBigIntegerBinding<U>
    extends InternalBinding<BigInteger, U> {
        private static final Set<SQLDialect> BIND_AS_STRING = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.SQLITE);

        DefaultBigIntegerBinding(DataType<BigInteger> dataType, Converter<BigInteger, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, BigInteger value) {
            ctx.render().sql(value.toString());
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setBigDecimal(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, BigInteger value) throws SQLException {
            if (BIND_AS_STRING.contains((Object)ctx.dialect())) {
                ctx.statement().setString(ctx.index(), value.toString());
            } else {
                ctx.statement().setBigDecimal(ctx.index(), new BigDecimal(value));
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, BigInteger value) throws SQLException {
            ctx.output().writeBigDecimal(new BigDecimal(value));
        }

        @Override
        final BigInteger get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.SQLITE) {
                return Convert.convert((Object)ctx.resultSet().getString(ctx.index()), BigInteger.class);
            }
            BigDecimal b = ctx.resultSet().getBigDecimal(ctx.index());
            return b == null ? null : b.toBigInteger();
        }

        @Override
        final BigInteger get0(BindingGetStatementContext<U> ctx) throws SQLException {
            BigDecimal d = ctx.statement().getBigDecimal(ctx.index());
            return d == null ? null : d.toBigInteger();
        }

        @Override
        final BigInteger get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            BigDecimal d = ctx.input().readBigDecimal();
            return d == null ? null : d.toBigInteger();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 3;
        }
    }

    static final class DefaultBlobBinding<U>
    extends InternalBinding<Blob, U> {
        DefaultBlobBinding(DataType<Blob> dataType, Converter<Blob, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setBlob(ctx.index(), (Blob)null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Blob value) throws SQLException {
            ctx.statement().setBlob(ctx.index(), value);
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Blob value) throws SQLException {
            ctx.output().writeBlob(value);
        }

        @Override
        final Blob get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return ctx.resultSet().getBlob(ctx.index());
        }

        @Override
        final Blob get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return ctx.statement().getBlob(ctx.index());
        }

        @Override
        final Blob get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return ctx.input().readBlob();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            switch (configuration.family()) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case SYBASE: 
                case VERTICA: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    return -2;
                }
            }
            return 2004;
        }
    }

    static final class DefaultBooleanBinding<U>
    extends InternalBinding<Boolean, U> {
        private static final Set<SQLDialect> BIND_AS_1_0 = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
        @Pro
        private static final Set<SQLDialect> SET_NULL_AS_OBJECT = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.ORACLE);

        DefaultBooleanBinding(DataType<Boolean> dataType, Converter<Boolean, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Boolean value) {
            if (BIND_AS_1_0.contains((Object)ctx.dialect())) {
                ctx.render().sql(value != false ? "1" : "0");
            } else if (ctx.family() == SQLDialect.INFORMIX) {
                ctx.render().visit(value != false ? DSL.cast(DSL.inline('t'), SQLDataType.BOOLEAN) : DSL.cast(DSL.inline('f'), SQLDataType.BOOLEAN));
            } else {
                ctx.render().visit(value != false ? Keywords.K_TRUE : Keywords.K_FALSE);
            }
        }

        @Override
        final void register0(BindingRegisterContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE) {
                ctx.statement().registerOutParameter(ctx.index(), 4);
            } else {
                super.register0(ctx);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Boolean value) throws SQLException {
            switch (ctx.family()) {
                case ASE: {
                    if (Tools.isJtds(ctx.statement())) {
                        ctx.statement().setBoolean(ctx.index(), value);
                        break;
                    }
                    ctx.statement().setInt(ctx.index(), value != false ? 1 : 0);
                    break;
                }
                case ORACLE: 
                case ACCESS: {
                    ctx.statement().setInt(ctx.index(), value != false ? 1 : 0);
                    break;
                }
                case BIGQUERY: {
                    if (value == null) {
                        ctx.statement().setString(ctx.index(), null);
                        break;
                    }
                    ctx.statement().setBoolean(ctx.index(), value);
                    break;
                }
                default: {
                    ctx.statement().setBoolean(ctx.index(), value);
                }
            }
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (SET_NULL_AS_OBJECT.contains((Object)ctx.dialect()) && !R2DBC.isR2dbc(ctx.statement())) {
                ctx.statement().setObject(ctx.index(), null);
            } else if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Boolean value) throws SQLException {
            ctx.output().writeBoolean(value);
        }

        @Override
        final Boolean get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.resultSet(), (Boolean)ctx.resultSet().getBoolean(ctx.index()));
        }

        @Override
        final Boolean get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.statement(), (Boolean)ctx.statement().getBoolean(ctx.index()));
        }

        @Override
        final Boolean get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.input(), (Boolean)ctx.input().readBoolean());
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) throws SQLException {
            switch (configuration.family()) {
                case ASE: {
                    if (Tools.isJtds(statement)) {
                        return 16;
                    }
                    return 4;
                }
                case BIGQUERY: {
                    return 12;
                }
            }
            return 16;
        }
    }

    static final class DefaultByteBinding<U>
    extends InternalBinding<Byte, U> {
        DefaultByteBinding(DataType<Byte> dataType, Converter<Byte, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setBigDecimal(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Byte value) {
            ctx.render().sql(value.byteValue());
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Byte value) throws SQLException {
            ctx.statement().setByte(ctx.index(), value);
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Byte value) throws SQLException {
            ctx.output().writeByte(value);
        }

        @Override
        final Byte get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.resultSet(), Byte.valueOf(ctx.resultSet().getByte(ctx.index())));
        }

        @Override
        final Byte get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.statement(), Byte.valueOf(ctx.statement().getByte(ctx.index())));
        }

        @Override
        final Byte get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.input(), Byte.valueOf(ctx.input().readByte()));
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (configuration.family() == SQLDialect.BIGQUERY) {
                return 12;
            }
            if (configuration.family() == SQLDialect.EXASOL) {
                return 5;
            }
            return -6;
        }
    }

    static final class DefaultBytesBinding<U>
    extends InternalBinding<byte[], U> {
        private final BlobBinding blobs = new BlobBinding();

        DefaultBytesBinding(DataType<byte[]> dataType, Converter<byte[], U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case BIGQUERY: {
                    ctx.statement().setBytes(ctx.index(), null);
                    break;
                }
                default: {
                    super.setNull0(ctx);
                }
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, byte[] value) {
            switch (ctx.family()) {
                case H2: 
                case HSQLDB: 
                case AURORA_MYSQL: 
                case INGRES: 
                case MEMSQL: 
                case MARIADB: 
                case MYSQL: 
                case SQLITE: {
                    ((RenderContext)((RenderContext)ctx.render().sql("X'")).sql(Tools.convertBytesToHex(value))).sql('\'');
                    break;
                }
                case DERBY: {
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_CAST)).sql("(X'")).sql(Tools.convertBytesToHex(value))).sql("' ")).visit(Keywords.K_AS)).sql(' ')).visit(SQLDataType.BLOB)).sql(')');
                    break;
                }
                case SYBASE: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case ASE: {
                    ((RenderContext)ctx.render().sql("0x")).sql(Tools.convertBytesToHex(value));
                    break;
                }
                case DB2: {
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_BLOB)).sql("(X'")).sql(Tools.convertBytesToHex(value))).sql("')");
                    break;
                }
                case ORACLE: {
                    ((RenderContext)((RenderContext)ctx.render().sql("hextoraw('")).sql(Tools.convertBytesToHex(value))).sql("')");
                    break;
                }
                case TERADATA: {
                    ((RenderContext)((RenderContext)ctx.render().sql('\'')).sql(Tools.convertBytesToHex(value))).sql("'XBV");
                    break;
                }
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    Cast.renderCast(ctx.render(), c -> c.sql("E'").sql(PostgresUtils.toPGString(value)).sql("'"), c -> c.visit(Names.N_BYTEA));
                    break;
                }
                default: {
                    ((RenderContext)((RenderContext)ctx.render().sql("X'")).sql(Tools.convertBytesToHex(value))).sql('\'');
                }
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, byte[] value) throws SQLException {
            switch (ctx.family()) {
                case H2: {
                    this.blobs.set((BindingSetStatementContext<byte[]>)new DefaultBindingSetStatementContext<byte[]>(ctx.executeContext(), ctx.statement(), ctx.index(), value));
                    break;
                }
                default: {
                    ctx.statement().setBytes(ctx.index(), value);
                }
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, byte[] value) throws SQLException {
            if (this.dataType.isLob()) {
                ctx.output().writeBlob(BlobBinding.newBlob(ctx, value, null));
            } else {
                ctx.output().writeBytes(value);
            }
        }

        @Override
        final byte[] get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case H2: {
                    DefaultBindingGetResultSetContext<byte[]> x = new DefaultBindingGetResultSetContext<byte[]>(ctx.executeContext(), ctx.resultSet(), ctx.index());
                    this.blobs.get((BindingGetResultSetContext<byte[]>)x);
                    return x.value();
                }
            }
            return ctx.resultSet().getBytes(ctx.index());
        }

        @Override
        final byte[] get0(BindingGetStatementContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case H2: {
                    DefaultBindingGetStatementContext<byte[]> x = new DefaultBindingGetStatementContext<byte[]>(ctx.executeContext(), ctx.statement(), ctx.index());
                    this.blobs.get((BindingGetStatementContext<byte[]>)x);
                    return x.value();
                }
            }
            return ctx.statement().getBytes(ctx.index());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        final byte[] get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            if (this.dataType.isLob()) {
                Blob blob = null;
                try {
                    blob = ctx.input().readBlob();
                    byte[] byArray = blob == null ? null : blob.getBytes(1L, Tools.asInt(blob.length()));
                    return byArray;
                }
                finally {
                    JDBCUtils.safeFree(blob);
                }
            }
            return ctx.input().readBytes();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            switch (configuration.family()) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case SYBASE: 
                case VERTICA: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    return -2;
                }
            }
            return 2004;
        }
    }

    static final class DefaultClobBinding<U>
    extends InternalBinding<Clob, U> {
        DefaultClobBinding(DataType<Clob> dataType, Converter<Clob, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setClob(ctx.index(), (Clob)null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Clob value) throws SQLException {
            ctx.statement().setClob(ctx.index(), value);
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Clob value) throws SQLException {
            ctx.output().writeClob(value);
        }

        @Override
        final Clob get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return ctx.resultSet().getClob(ctx.index());
        }

        @Override
        final Clob get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return ctx.statement().getClob(ctx.index());
        }

        @Override
        final Clob get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return ctx.input().readClob();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 2005;
        }
    }

    static final class DefaultDateBinding<U>
    extends InternalBinding<java.sql.Date, U> {
        private static final Set<SQLDialect> INLINE_AS_STRING_LITERAL = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.SQLITE, SQLDialect.SYBASE);

        DefaultDateBinding(DataType<java.sql.Date> dataType, Converter<java.sql.Date, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, java.sql.Date value) {
            if (INLINE_AS_STRING_LITERAL.contains((Object)ctx.dialect())) {
                ((RenderContext)((RenderContext)ctx.render().sql('\'')).sql(DefaultDateBinding.escape(value, ctx.render()))).sql('\'');
            } else if (ctx.family() == SQLDialect.ACCESS) {
                ((RenderContext)((RenderContext)ctx.render().sql('#')).sql(new SimpleDateFormat("yyyy/MM/dd").format(value))).sql('#');
            } else if (ctx.family() == SQLDialect.INFORMIX) {
                ((RenderContext)((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_DATETIME)).sql('(')).sql(DefaultDateBinding.escape(value, ctx.render()))).sql(") ")).visit(Keywords.K_YEAR_TO_DAY);
            } else if (ctx.family() == SQLDialect.SQLSERVER || ctx.family() == SQLDialect.SQLDATAWAREHOUSE) {
                ctx.render().visit(new Cast.CastNative<java.sql.Date>(DSL.inline(DefaultDateBinding.escape(value, ctx.render())), SQLDataType.DATE));
            } else if (ctx.family() == SQLDialect.DERBY) {
                ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_DATE)).sql("('")).sql(DefaultDateBinding.escape(value, ctx.render()))).sql("')");
            } else if (REQUIRE_JDBC_DATE_LITERAL.contains((Object)ctx.dialect())) {
                ((RenderContext)((RenderContext)ctx.render().sql("{d '")).sql(DefaultDateBinding.escape(value, ctx.render()))).sql("'}");
            } else {
                ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_DATE)).sql(" '")).sql(this.format(value, ctx.render()))).sql('\'');
            }
        }

        private final String format(java.sql.Date value, RenderContext render) {
            if (render.family() == SQLDialect.POSTGRES) {
                if (value.getTime() == 9223372036825200000L) {
                    return "infinity";
                }
                if (value.getTime() == -9223372036832400000L) {
                    return "-infinity";
                }
            }
            if (value.getYear() + 1900 >= 10000) {
                return value.getYear() + 1900 + "-" + StringUtils.leftPad("" + (value.getMonth() + 1), 2, '0') + "-" + StringUtils.leftPad("" + value.getDate(), 2, '0');
            }
            return DefaultDateBinding.escape(value, render);
        }

        @Override
        final void sqlBind0(BindingSQLContext<U> ctx, java.sql.Date value) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE) {
                ctx.render().visit(new Cast.CastNative<java.sql.Date>(DSL.field(ctx.variable()), SQLDataType.DATE));
            } else {
                super.sqlBind0(ctx, value);
            }
        }

        @Override
        final void register0(BindingRegisterContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE) {
                ctx.statement().registerOutParameter(ctx.index(), 93);
            } else {
                super.register0(ctx);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, java.sql.Date value) throws SQLException {
            if (ctx.family() == SQLDialect.SQLITE) {
                ctx.statement().setString(ctx.index(), value.toString());
            } else if (ctx.family() == SQLDialect.ORACLE) {
                ctx.statement().setTimestamp(ctx.index(), new Timestamp(value.getTime()));
            } else {
                ctx.statement().setDate(ctx.index(), value);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, java.sql.Date value) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE) {
                ctx.output().writeTimestamp(new Timestamp(value.getTime()));
            } else {
                ctx.output().writeDate(value);
            }
        }

        @Override
        final java.sql.Date get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            SQLDialect family = ctx.family();
            if (family == SQLDialect.SQLITE) {
                String date = ctx.resultSet().getString(ctx.index());
                return date == null ? null : new java.sql.Date(DefaultBinding.parse(java.sql.Date.class, date));
            }
            if (SQLDialect.ORACLE == family) {
                Timestamp timestamp = ctx.resultSet().getTimestamp(ctx.index());
                return timestamp == null ? null : new java.sql.Date(timestamp.getTime());
            }
            return ctx.resultSet().getDate(ctx.index());
        }

        @Override
        final java.sql.Date get0(BindingGetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE) {
                Timestamp timestamp = ctx.statement().getTimestamp(ctx.index());
                return timestamp == null ? null : new java.sql.Date(timestamp.getTime());
            }
            return ctx.statement().getDate(ctx.index());
        }

        @Override
        final java.sql.Date get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE) {
                Timestamp timestamp = ctx.input().readTimestamp();
                return timestamp == null ? null : new java.sql.Date(timestamp.getTime());
            }
            return ctx.input().readDate();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (configuration.family() == SQLDialect.ORACLE) {
                return 93;
            }
            return 91;
        }
    }

    static final class DefaultDayToSecondBinding<U>
    extends InternalBinding<DayToSecond, U> {
        private static final Set<SQLDialect> REQUIRE_PG_INTERVAL = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.YUGABYTEDB);
        private static final Set<SQLDialect> REQUIRE_STANDARD_INTERVAL = SQLDialect.supportedBy(SQLDialect.H2);

        DefaultDayToSecondBinding(DataType<DayToSecond> dataType, Converter<DayToSecond, U> converter) {
            super(dataType, converter);
        }

        @Override
        void sqlInline0(BindingSQLContext<U> ctx, DayToSecond value) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                ctx.render().visit(DSL.inline(PostgresUtils.toPGInterval(value).toString()));
            } else {
                super.sqlInline0(ctx, value);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, DayToSecond value) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                ctx.statement().setString(ctx.index(), PostgresUtils.toPGInterval(value).toString());
            } else {
                ctx.statement().setString(ctx.index(), this.renderDTS(ctx, value));
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, DayToSecond value) throws SQLException {
            ctx.output().writeString(this.renderDTS(ctx, value));
        }

        @Override
        final DayToSecond get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                return PostgresUtils.toDayToSecond(ctx.resultSet().getString(ctx.index()));
            }
            return this.parseDTS(ctx, ctx.resultSet().getString(ctx.index()));
        }

        @Override
        final DayToSecond get0(BindingGetStatementContext<U> ctx) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                return PostgresUtils.toDayToSecond(ctx.statement().getString(ctx.index()));
            }
            return this.parseDTS(ctx, ctx.statement().getString(ctx.index()));
        }

        @Override
        final DayToSecond get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return this.parseDTS(ctx, ctx.input().readString());
        }

        private final DayToSecond parseDTS(Scope scope, String string) {
            if (string == null) {
                return null;
            }
            if (REQUIRE_STANDARD_INTERVAL.contains((Object)scope.dialect()) && string.startsWith("INTERVAL")) {
                return (DayToSecond)((Param)scope.dsl().parser().parseField(string)).getValue();
            }
            return DayToSecond.valueOf(string);
        }

        private final String renderDTS(Scope scope, DayToSecond dts) {
            if (dts == null) {
                return null;
            }
            if (REQUIRE_STANDARD_INTERVAL.contains((Object)scope.dialect())) {
                return "INTERVAL '" + dts.toString() + "' DAY TO SECOND";
            }
            return dts.toString();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 12;
        }
    }

    static final class DefaultDoubleBinding<U>
    extends InternalBinding<Double, U> {
        static final Set<SQLDialect> REQUIRES_LITERAL_CAST = SQLDialect.supportedBy(SQLDialect.COCKROACHDB, SQLDialect.H2);

        DefaultDoubleBinding(DataType<Double> dataType, Converter<Double, U> converter) {
            super(dataType, converter);
        }

        @Pro
        static final OraclePreparedStatement oraclePreparedStatement(Configuration configuration, PreparedStatement stmt) {
            PreparedStatement result = stmt;
            if (result instanceof OraclePreparedStatement) {
                OraclePreparedStatement o = (OraclePreparedStatement)result;
                return o;
            }
            return configuration.unwrapperProvider().provide().unwrap(result, OraclePreparedStatement.class);
        }

        static final Field<?> nan(BindingSQLContext<?> ctx, DataType<?> type) {
            switch (ctx.family()) {
                case FIREBIRD: {
                    return DSL.log(DSL.inline(1), DSL.inline(1));
                }
                case HSQLDB: {
                    return DSL.inline(0.0).div(DSL.field("0.0e0", type));
                }
            }
            return DSL.inline("NaN").cast(type);
        }

        static final Field<?> infinity(BindingSQLContext<?> ctx, DataType<?> type, boolean negative) {
            switch (ctx.family()) {
                case FIREBIRD: {
                    return DSL.log(negative ? DSL.inline(0.5) : DSL.inline(1.5), DSL.inline(1));
                }
                case HSQLDB: {
                    return DSL.inline(negative ? -1.0 : 1.0).div(DSL.field("0.0e0", type));
                }
            }
            return DSL.inline(negative ? "-Infinity" : "Infinity").cast(type);
        }

        static final Double fixInfinity(Scope scope, ThrowingSupplier<Double, SQLException> doubleSupplier, ThrowingSupplier<String, SQLException> stringSupplier) throws SQLException {
            return DefaultDoubleBinding.fixInfinity(scope, doubleSupplier, stringSupplier, () -> Double.POSITIVE_INFINITY, () -> Double.NEGATIVE_INFINITY);
        }

        static final <T> T fixInfinity(Scope scope, ThrowingSupplier<T, SQLException> doubleSupplier, ThrowingSupplier<String, SQLException> stringSupplier, Supplier<T> positive, Supplier<T> negative) throws SQLException {
            try {
                return doubleSupplier.get();
            }
            catch (SQLException e) {
                if (scope.family() == SQLDialect.COCKROACHDB) {
                    String s2 = stringSupplier.get();
                    if ("Inf".equalsIgnoreCase(s2) || "+Inf".equalsIgnoreCase(s2)) {
                        return positive.get();
                    }
                    if ("-Inf".equalsIgnoreCase(s2)) {
                        return negative.get();
                    }
                }
                throw e;
            }
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setBigDecimal(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Double value) {
            if (value.isNaN()) {
                ctx.render().visit(DefaultDoubleBinding.nan(ctx, SQLDataType.DOUBLE));
            } else if (value == Double.POSITIVE_INFINITY) {
                ctx.render().visit(DefaultDoubleBinding.infinity(ctx, SQLDataType.DOUBLE, false));
            } else if (value == Double.NEGATIVE_INFINITY) {
                ctx.render().visit(DefaultDoubleBinding.infinity(ctx, SQLDataType.DOUBLE, true));
            } else if (REQUIRES_LITERAL_CAST.contains((Object)ctx.dialect())) {
                ctx.render().visit(DSL.field(ctx.render().doubleFormat().format(value)).cast(SQLDataType.DOUBLE));
            } else {
                ctx.render().sql(value);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Double value) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE && !R2DBC.isR2dbc(ctx.statement())) {
                DefaultDoubleBinding.oraclePreparedStatement(ctx.configuration(), ctx.statement()).setBinaryDouble(ctx.index(), value.doubleValue());
            } else {
                ctx.statement().setDouble(ctx.index(), value);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Double value) throws SQLException {
            ctx.output().writeDouble(value);
        }

        @Override
        final Double get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.resultSet(), DefaultDoubleBinding.fixInfinity(ctx, () -> ctx.resultSet().getDouble(ctx.index()), () -> ctx.resultSet().getString(ctx.index())));
        }

        @Override
        final Double get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.statement(), DefaultDoubleBinding.fixInfinity(ctx, () -> ctx.statement().getDouble(ctx.index()), () -> ctx.statement().getString(ctx.index())));
        }

        @Override
        final Double get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.input(), DefaultDoubleBinding.fixInfinity(ctx, () -> ctx.input().readDouble(), () -> ctx.input().readString()));
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (configuration.family() == SQLDialect.ORACLE) {
                return 101;
            }
            if (configuration.family() == SQLDialect.BIGQUERY) {
                return 12;
            }
            return 8;
        }
    }

    static final class DefaultFloatBinding<U>
    extends InternalBinding<Float, U> {
        DefaultFloatBinding(DataType<Float> dataType, Converter<Float, U> converter) {
            super(dataType, converter);
        }

        static final Float fixInfinity(Scope scope, ThrowingSupplier<Float, SQLException> doubleSupplier, ThrowingSupplier<String, SQLException> stringSupplier) throws SQLException {
            return DefaultDoubleBinding.fixInfinity(scope, doubleSupplier, stringSupplier, () -> Float.valueOf(Float.POSITIVE_INFINITY), () -> Float.valueOf(Float.NEGATIVE_INFINITY));
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setBigDecimal(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Float value) {
            if (value.isNaN()) {
                ctx.render().visit(DefaultDoubleBinding.nan(ctx, SQLDataType.REAL));
            } else if ((double)value.floatValue() == Double.POSITIVE_INFINITY) {
                ctx.render().visit(DefaultDoubleBinding.infinity(ctx, SQLDataType.REAL, false));
            } else if ((double)value.floatValue() == Double.NEGATIVE_INFINITY) {
                ctx.render().visit(DefaultDoubleBinding.infinity(ctx, SQLDataType.REAL, true));
            } else if (DefaultDoubleBinding.REQUIRES_LITERAL_CAST.contains((Object)ctx.dialect())) {
                ctx.render().visit(DSL.field(ctx.render().floatFormat().format(value)).cast(SQLDataType.REAL));
            } else {
                ctx.render().sql(value.floatValue());
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Float value) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE && !R2DBC.isR2dbc(ctx.statement())) {
                DefaultDoubleBinding.oraclePreparedStatement(ctx.configuration(), ctx.statement()).setBinaryFloat(ctx.index(), value.floatValue());
            } else {
                ctx.statement().setFloat(ctx.index(), value.floatValue());
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Float value) throws SQLException {
            ctx.output().writeFloat(value.floatValue());
        }

        @Override
        final Float get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.resultSet(), DefaultFloatBinding.fixInfinity(ctx, () -> Float.valueOf(ctx.resultSet().getFloat(ctx.index())), () -> ctx.resultSet().getString(ctx.index())));
        }

        @Override
        final Float get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.statement(), DefaultFloatBinding.fixInfinity(ctx, () -> Float.valueOf(ctx.statement().getFloat(ctx.index())), () -> ctx.statement().getString(ctx.index())));
        }

        @Override
        final Float get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.input(), DefaultFloatBinding.fixInfinity(ctx, () -> Float.valueOf(ctx.input().readFloat()), () -> ctx.input().readString()));
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (configuration.family() == SQLDialect.ORACLE) {
                return 100;
            }
            if (configuration.family() == SQLDialect.BIGQUERY) {
                return 12;
            }
            return 6;
        }
    }

    @Pro
    static final class DefaultGeometryBinding<U>
    extends AbstractSpatialBinding<Geometry, U> {
        DefaultGeometryBinding(DataType<Geometry> dataType, Converter<Geometry, U> converter) {
            super(dataType, converter);
        }

        @Override
        final Geometry construct(String wkt) {
            return Geometry.geometry(wkt);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Geometry value) throws SQLException {
            switch (ctx.family()) {
                case SNOWFLAKE: 
                case AURORA_MYSQL: 
                case MARIADB: 
                case MYSQL: {
                    if (P_HEX.matcher(value.data()).matches()) {
                        ((RenderContext)ctx.render().visit(Names.N_ST_GEOMFROMWKB)).sql('(');
                        this.wkb.sqlInline0(ctx, (byte[])this.wkbConverter.from(value, ctx.converterContext()));
                        ctx.render().sql(')');
                        break;
                    }
                    ((RenderContext)ctx.render().visit(Names.N_ST_GEOMFROMTEXT)).sql('(');
                    super.sqlInline0(ctx, value);
                    ctx.render().sql(')');
                    break;
                }
                case ORACLE: {
                    if (P_HEX.matcher(value.data()).matches()) {
                        ctx.render().sql("sdo_geometry(wkb => ");
                        this.wkb.sqlInline0(ctx, (byte[])this.wkbConverter.from(value, ctx.converterContext()));
                        ctx.render().sql(", srid => null)");
                        break;
                    }
                    ctx.render().sql("sdo_geometry(wkt => ");
                    super.sqlInline0(ctx, value);
                    ctx.render().sql(", srid => null)");
                    break;
                }
                case SQLSERVER: {
                    ((RenderContext)ctx.render().visit(Keywords.K_CAST)).sql('(');
                    super.sqlInline0(ctx, value);
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)ctx.render().sql(' ')).visit(Keywords.K_AS)).sql(' ')).visit(Keywords.K_GEOMETRY)).sql(')');
                    break;
                }
                default: {
                    super.sqlInline0(ctx, value);
                }
            }
        }

        @Override
        final void sqlBind0(BindingSQLContext<U> ctx, Geometry value) throws SQLException {
            if (value == null) {
                super.sqlBind0(ctx, value);
                return;
            }
            switch (ctx.family()) {
                case SNOWFLAKE: 
                case AURORA_MYSQL: 
                case MARIADB: 
                case MYSQL: {
                    if (P_HEX.matcher(value.data()).matches()) {
                        ((RenderContext)ctx.render().visit(Names.N_ST_GEOMFROMWKB)).sql('(');
                        this.wkb.sqlBind0(ctx, (byte[])this.wkbConverter.from(value, ctx.converterContext()));
                        ctx.render().sql(')');
                        break;
                    }
                    ((RenderContext)ctx.render().visit(Names.N_ST_GEOMFROMTEXT)).sql('(');
                    super.sqlBind0(ctx, value);
                    ctx.render().sql(')');
                    break;
                }
                case ORACLE: {
                    if (P_HEX.matcher(value.data()).matches()) {
                        ctx.render().sql("sdo_geometry(wkb => ");
                        this.wkb.sqlBind0(ctx, (byte[])this.wkbConverter.from(value, ctx.converterContext()));
                        ctx.render().sql(", srid => null)");
                        break;
                    }
                    ctx.render().sql("sdo_geometry(wkt => ");
                    super.sqlBind0(ctx, value);
                    ctx.render().sql(", srid => null)");
                    break;
                }
                default: {
                    super.sqlBind0(ctx, value);
                }
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Geometry value) throws SQLException {
            switch (ctx.family()) {
                case SNOWFLAKE: 
                case AURORA_MYSQL: 
                case MARIADB: 
                case MYSQL: {
                    if (P_HEX.matcher(value.data()).matches()) {
                        ctx.statement().setBytes(ctx.index(), (byte[])this.wkbConverter.from(value, ctx.converterContext()));
                        break;
                    }
                    ctx.statement().setString(ctx.index(), value.data());
                    break;
                }
                case SQLSERVER: {
                    ctx.statement().unwrap(ISQLServerPreparedStatement.class).setGeometry(ctx.index(), com.microsoft.sqlserver.jdbc.Geometry.parse((String)value.data()));
                    break;
                }
                default: {
                    ctx.statement().setString(ctx.index(), value.data());
                }
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Geometry value) throws SQLException {
            ctx.output().writeString(value.data());
        }

        @Override
        final Geometry get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case SNOWFLAKE: 
                case AURORA_MYSQL: 
                case MARIADB: 
                case MYSQL: {
                    byte[] bytes = ctx.resultSet().getBytes(ctx.index());
                    return bytes == null ? null : Geometry.geometry(Tools.convertBytesToHex(bytes, 4, bytes.length - 4));
                }
                case ORACLE: {
                    return DefaultExecuteContext.localExecuteContext(ctx.executeContext(), () -> DefaultGeometryBinding.sdoToWkt((SdoGeometryRecord)ctx.resultSet().getObject(ctx.index(), SDO_TYPE_MAP)));
                }
                case SQLSERVER: {
                    com.microsoft.sqlserver.jdbc.Geometry g2 = ctx.resultSet().unwrap(ISQLServerResultSet.class).getGeometry(ctx.index());
                    return g2 == null ? null : Geometry.geometry(g2.asTextZM());
                }
            }
            String string = ctx.resultSet().getString(ctx.index());
            return string == null ? null : Geometry.geometry(string);
        }

        private static final Geometry sdoToWkt(SdoGeometryRecord g2) {
            if (g2 == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            int gtype = g2.get(SdoGeometry.SDO_GTYPE, Integer.TYPE);
            SdoElemInfoArray e = (SdoElemInfoArray)g2.get(SdoGeometry.SDO_ELEM_INFO);
            SdoOrdinateArray o = (SdoOrdinateArray)g2.get(SdoGeometry.SDO_ORDINATES);
            int d = gtype / 1000;
            int l = gtype % 1000 / 100;
            int tt = gtype % 100;
            switch (tt) {
                case 0: {
                    return null;
                }
                case 1: {
                    SdoPointTypeRecord point = (SdoPointTypeRecord)g2.get(SdoGeometry.SDO_POINT);
                    BigDecimal x = (BigDecimal)point.get(SdoPointType.X);
                    BigDecimal y = (BigDecimal)point.get(SdoPointType.Y);
                    BigDecimal z = (BigDecimal)point.get(SdoPointType.Z);
                    if (z == null) {
                        return Geometry.geometry("point (" + String.valueOf(x) + " " + String.valueOf(y) + ")");
                    }
                    return Geometry.geometry("point (" + String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(z) + ")");
                }
            }
            DefaultGeometryBinding.sdoToWkt(new SdoToWktContext(sb, o, e, d), tt);
            return Geometry.geometry(sb.toString());
        }

        private static final void sdoToWkt(SdoToWktContext ctx, int tt) {
            switch (tt) {
                case 1: {
                    ctx.sb.append("point (");
                    DefaultGeometryBinding.sdoToWktSingle(ctx, ", ");
                    ctx.sb.append(")");
                    break;
                }
                case 2: {
                    ctx.sb.append("linestring (");
                    DefaultGeometryBinding.sdoToWktSingle(ctx, ", ");
                    ctx.sb.append(')');
                    break;
                }
                case 3: {
                    ctx.sb.append("polygon ((");
                    DefaultGeometryBinding.sdoToWktMulti(ctx, 2003, -1);
                    ctx.sb.append("))");
                    break;
                }
                case 4: {
                    ctx.sb.append("geometrycollection (");
                    String separator = "";
                    while (ctx.e2 < ctx.e.size()) {
                        ctx.sb.append(separator);
                        int eType = ((BigDecimal)ctx.e.get(ctx.e2 + 1)).intValue();
                        int eInterpretation = ((BigDecimal)ctx.e.get(ctx.e2 + 2)).intValue();
                        switch (eType) {
                            case 1: {
                                if (eInterpretation > 1) {
                                    DefaultGeometryBinding.sdoToWkt(ctx, 5);
                                    break;
                                }
                                DefaultGeometryBinding.sdoToWkt(ctx, 1);
                                break;
                            }
                            case 2: {
                                DefaultGeometryBinding.sdoToWkt(ctx, 2);
                                break;
                            }
                            case 1003: {
                                DefaultGeometryBinding.sdoToWkt(ctx, 3);
                                break;
                            }
                            case 1005: {
                                DefaultGeometryBinding.sdoToWkt(ctx, 7);
                                break;
                            }
                            case 1007: {
                                DefaultGeometryBinding.sdoToWkt(ctx, 8);
                            }
                        }
                        separator = ", ";
                    }
                    ctx.sb.append(")");
                    break;
                }
                case 5: {
                    ctx.sb.append("multipoint ((");
                    DefaultGeometryBinding.sdoToWktSingle(ctx, "), (");
                    ctx.sb.append("))");
                    break;
                }
                case 6: {
                    ctx.sb.append("multilinestring ((");
                    DefaultGeometryBinding.sdoToWktMulti(ctx, 2, -1);
                    ctx.sb.append("))");
                    break;
                }
                case 7: {
                    ctx.sb.append("multipolygon (((");
                    DefaultGeometryBinding.sdoToWktMulti(ctx, 2003, 1003);
                    ctx.sb.append(")))");
                    break;
                }
                case 8: {
                    break;
                }
            }
        }

        @Pro
        private static final void sdoToWktSingle(SdoToWktContext ctx, String elementSeparator) {
            ctx.nextE();
            DefaultGeometryBinding.sdoToWktAppendO(ctx, elementSeparator);
        }

        @Pro
        private static final void sdoToWktMulti(SdoToWktContext ctx, int whileEType1, int whileEType2) {
            int nextEType = -1;
            while (ctx.nextE()) {
                if (nextEType != -1) {
                    if (nextEType == whileEType2) {
                        ctx.sb.append(")), ((");
                    } else {
                        ctx.sb.append("), (");
                    }
                }
                DefaultGeometryBinding.sdoToWktAppendO(ctx, ", ");
                if (ctx.e2 < ctx.e.size() && ((nextEType = ((BigDecimal)ctx.e.get(ctx.e2 + 1)).intValue()) == whileEType1 || nextEType == whileEType2)) continue;
            }
        }

        private static final void sdoToWktAppendO(SdoToWktContext ctx, String elementSeparator) {
            for (int i = ctx.o1; i < ctx.o2; ++i) {
                if (i > ctx.o1) {
                    if (i % ctx.d == 0) {
                        ctx.sb.append(elementSeparator);
                    } else {
                        ctx.sb.append(" ");
                    }
                }
                ctx.sb.append(ctx.o.get(i));
            }
        }

        @Pro
        private static final void sdoToWktRecursive(StringBuilder sb, SdoOrdinateArray o, SdoElemInfoArray e, int d) {
            throw new ThreadDeath();
        }

        @Override
        final Geometry get0(BindingGetStatementContext<U> ctx) throws SQLException {
            String string = ctx.statement().getString(ctx.index());
            return string == null ? null : Geometry.geometry(string);
        }

        @Override
        final Geometry get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            String string = ctx.input().readString();
            return string == null ? null : Geometry.geometry(string);
        }

        static class SdoToWktContext {
            final StringBuilder sb;
            final SdoOrdinateArray o;
            final SdoElemInfoArray e;
            final int d;
            int o1;
            int o2;
            int e1;
            int e2;

            SdoToWktContext(StringBuilder sb, SdoOrdinateArray o, SdoElemInfoArray e, int d) {
                this.sb = sb;
                this.o = o;
                this.e = e;
                this.d = d;
            }

            boolean nextE() {
                if (this.e2 == 0) {
                    this.e2 = 3;
                } else {
                    this.e1 += 3;
                    this.e2 += 3;
                }
                if (this.e1 < this.e.size()) {
                    this.o1 = ((BigDecimal)this.e.get(this.e1)).intValue() - 1;
                    this.o2 = this.e2 < this.e.size() ? ((BigDecimal)this.e.get(this.e2)).intValue() - 1 : this.o.size();
                    return true;
                }
                return false;
            }

            public String toString() {
                return this.sb.toString();
            }
        }
    }

    @Pro
    static final class DefaultGeographyBinding<U>
    extends AbstractSpatialBinding<Geography, U> {
        DefaultGeographyBinding(DataType<Geography> dataType, Converter<Geography, U> converter) {
            super(dataType, converter);
        }

        @Override
        final Geography construct(String wkt) {
            return Geography.geography(wkt);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Geography value) throws SQLException {
            switch (ctx.family()) {
                case SNOWFLAKE: 
                case AURORA_MYSQL: 
                case MARIADB: 
                case MYSQL: {
                    ((RenderContext)ctx.render().visit(Names.N_ST_GEOMFROMTEXT)).sql('(');
                    super.sqlInline0(ctx, value);
                    ctx.render().sql(')');
                    break;
                }
                case SQLSERVER: {
                    ((RenderContext)ctx.render().visit(Keywords.K_CAST)).sql('(');
                    super.sqlInline0(ctx, value);
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)ctx.render().sql(' ')).visit(Keywords.K_AS)).sql(' ')).visit(Keywords.K_GEOGRAPHY)).sql(')');
                    break;
                }
                default: {
                    super.sqlInline0(ctx, value);
                }
            }
        }

        @Override
        final void sqlBind0(BindingSQLContext<U> ctx, Geography value) throws SQLException {
            if (value == null) {
                super.sqlBind0(ctx, value);
                return;
            }
            switch (ctx.family()) {
                case SNOWFLAKE: 
                case AURORA_MYSQL: 
                case MARIADB: 
                case MYSQL: {
                    ((RenderContext)ctx.render().visit(Names.N_ST_GEOMFROMTEXT)).sql('(');
                    super.sqlBind0(ctx, value);
                    ctx.render().sql(')');
                    break;
                }
                default: {
                    super.sqlBind0(ctx, value);
                }
            }
        }

        @Override
        void set0(BindingSetStatementContext<U> ctx, Geography value) throws SQLException {
            switch (ctx.family()) {
                case SQLSERVER: {
                    ctx.statement().unwrap(ISQLServerPreparedStatement.class).setGeography(ctx.index(), com.microsoft.sqlserver.jdbc.Geography.parse((String)value.data()));
                    break;
                }
                default: {
                    ctx.statement().setString(ctx.index(), value.data());
                }
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Geography value) throws SQLException {
            ctx.output().writeString(value.data());
        }

        @Override
        final Geography get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case SQLSERVER: {
                    com.microsoft.sqlserver.jdbc.Geography g2 = ctx.resultSet().unwrap(ISQLServerResultSet.class).getGeography(ctx.index());
                    return g2 == null ? null : Geography.geography(g2.asTextZM());
                }
            }
            String string = ctx.resultSet().getString(ctx.index());
            return string == null ? null : Geography.geography(string);
        }

        @Override
        final Geography get0(BindingGetStatementContext<U> ctx) throws SQLException {
            String string = ctx.statement().getString(ctx.index());
            return string == null ? null : Geography.geography(string);
        }

        @Override
        final Geography get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            String string = ctx.input().readString();
            return string == null ? null : Geography.geography(string);
        }
    }

    static final class DefaultIntegerBinding<U>
    extends InternalBinding<Integer, U> {
        DefaultIntegerBinding(DataType<Integer> dataType, Converter<Integer, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setBigDecimal(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Integer value) {
            ctx.render().sql(value);
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Integer value) throws SQLException {
            ctx.statement().setInt(ctx.index(), value);
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Integer value) throws SQLException {
            ctx.output().writeInt(value);
        }

        @Override
        final Integer get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.resultSet(), Integer.valueOf(ctx.resultSet().getInt(ctx.index())));
        }

        @Override
        final Integer get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.statement(), Integer.valueOf(ctx.statement().getInt(ctx.index())));
        }

        @Override
        final Integer get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.input(), Integer.valueOf(ctx.input().readInt()));
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (configuration.family() == SQLDialect.BIGQUERY) {
                return 12;
            }
            return 4;
        }
    }

    static final class DefaultJSONBinding<U>
    extends InternalBinding<JSON, U> {
        static final Pattern P_UNDEFINED = Pattern.compile("(?:\"(?:\\\\\"|[^\"])*\"|(\\bundefined\\b))");

        DefaultJSONBinding(DataType<JSON> dataType, Converter<JSON, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        void sqlInline0(BindingSQLContext<U> ctx, JSON value) throws SQLException {
            super.sqlInline0(ctx, value);
            if (ctx.family() == SQLDialect.H2 && value != null) {
                ((RenderContext)((RenderContext)((RenderContext)ctx.render().sql(' ')).visit(Keywords.K_FORMAT)).sql(' ')).visit(Keywords.K_JSON);
            }
        }

        @Override
        void sqlBind0(BindingSQLContext<U> ctx, JSON value) throws SQLException {
            super.sqlBind0(ctx, value);
            if (ctx.family() == SQLDialect.H2 && value != null) {
                ((RenderContext)((RenderContext)((RenderContext)ctx.render().sql(' ')).visit(Keywords.K_FORMAT)).sql(' ')).visit(Keywords.K_JSON);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, JSON value) throws SQLException {
            ctx.statement().setString(ctx.index(), value.data());
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, JSON value) throws SQLException {
            ctx.output().writeString(value.data());
        }

        @Override
        final JSON get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            String string = DefaultJSONBinding.patchSnowflakeJSON(ctx, ctx.resultSet().getString(ctx.index()));
            return string == null ? null : JSON.valueOf(string);
        }

        @Override
        final JSON get0(BindingGetStatementContext<U> ctx) throws SQLException {
            String string = DefaultJSONBinding.patchSnowflakeJSON(ctx, ctx.statement().getString(ctx.index()));
            return string == null ? null : JSON.valueOf(string);
        }

        @Override
        final JSON get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            String string = DefaultJSONBinding.patchSnowflakeJSON(ctx, ctx.input().readString());
            return string == null ? null : JSON.valueOf(string);
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 12;
        }

        static final String patchSnowflakeJSON(Scope ctx, String json) {
            if (ctx.family() == SQLDialect.SNOWFLAKE) {
                if (json == null) {
                    return null;
                }
                if (json.contains("undefined")) {
                    Matcher m4 = P_UNDEFINED.matcher(json);
                    StringBuffer sb = new StringBuffer();
                    while (m4.find()) {
                        m4.appendReplacement(sb, m4.group(1) == null ? Matcher.quoteReplacement(m4.group()) : "null");
                    }
                    m4.appendTail(sb);
                    return sb.toString();
                }
            }
            return json;
        }
    }

    static final class DefaultJSONBBinding<U>
    extends InternalBinding<JSONB, U> {
        static final Set<SQLDialect> EMULATE_AS_BLOB = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.SQLITE, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);

        DefaultJSONBBinding(DataType<JSONB> dataType, Converter<JSONB, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        void sqlInline0(BindingSQLContext<U> ctx, JSONB value) throws SQLException {
            if (EMULATE_AS_BLOB.contains((Object)ctx.dialect())) {
                this.bytes(ctx.configuration()).sqlInline0(ctx, this.bytesConverter(ctx.configuration()).to(value, ctx.converterContext()));
            } else {
                super.sqlInline0(ctx, value);
                if (ctx.family() == SQLDialect.H2 && value != null) {
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().sql(' ')).visit(Keywords.K_FORMAT)).sql(' ')).visit(Keywords.K_JSON);
                }
            }
        }

        @Override
        void sqlBind0(BindingSQLContext<U> ctx, JSONB value) throws SQLException {
            super.sqlBind0(ctx, value);
            if (ctx.family() == SQLDialect.H2 && value != null) {
                ((RenderContext)((RenderContext)((RenderContext)ctx.render().sql(' ')).visit(Keywords.K_FORMAT)).sql(' ')).visit(Keywords.K_JSON);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, JSONB value) throws SQLException {
            if (EMULATE_AS_BLOB.contains((Object)ctx.dialect())) {
                this.bytes(ctx.configuration()).set0(ctx, this.bytesConverter(ctx.configuration()).to(value, ctx.converterContext()));
            } else {
                ctx.statement().setString(ctx.index(), value.data());
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, JSONB value) throws SQLException {
            if (EMULATE_AS_BLOB.contains((Object)ctx.dialect())) {
                this.bytes(ctx.configuration()).set0(ctx, this.bytesConverter(ctx.configuration()).to(value, ctx.converterContext()));
            } else {
                ctx.output().writeString(value.data());
            }
        }

        @Override
        final JSONB get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (EMULATE_AS_BLOB.contains((Object)ctx.dialect())) {
                return this.bytesConverter(ctx.configuration()).from(this.bytes(ctx.configuration()).get0(ctx), ctx.converterContext());
            }
            String string = DefaultJSONBinding.patchSnowflakeJSON(ctx, ctx.resultSet().getString(ctx.index()));
            return string == null ? null : JSONB.valueOf(string);
        }

        @Override
        final JSONB get0(BindingGetStatementContext<U> ctx) throws SQLException {
            if (EMULATE_AS_BLOB.contains((Object)ctx.dialect())) {
                return this.bytesConverter(ctx.configuration()).from(this.bytes(ctx.configuration()).get0(ctx), ctx.converterContext());
            }
            String string = DefaultJSONBinding.patchSnowflakeJSON(ctx, ctx.statement().getString(ctx.index()));
            return string == null ? null : JSONB.valueOf(string);
        }

        @Override
        final JSONB get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            if (EMULATE_AS_BLOB.contains((Object)ctx.dialect())) {
                return this.bytesConverter(ctx.configuration()).from(this.bytes(ctx.configuration()).get0(ctx), ctx.converterContext());
            }
            String string = DefaultJSONBinding.patchSnowflakeJSON(ctx, ctx.input().readString());
            return string == null ? null : JSONB.valueOf(string);
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (EMULATE_AS_BLOB.contains((Object)configuration.dialect())) {
                return this.bytes(configuration).sqltype(statement, configuration);
            }
            return 12;
        }

        private final ContextConverter<byte[], JSONB> bytesConverter(Configuration configuration) {
            return ContextConverter.ofNullable(byte[].class, JSONB.class, (t2, x) -> JSONB.valueOf(new String((byte[])t2, configuration.charsetProvider().provide())), (u, x) -> u.toString().getBytes(configuration.charsetProvider().provide()));
        }

        private final DefaultBytesBinding<U> bytes(Configuration configuration) {
            return new DefaultBytesBinding<JSONB>(SQLDataType.BLOB, (Converter<byte[], JSONB>)this.bytesConverter(configuration));
        }
    }

    static final class DefaultXMLBinding<U>
    extends InternalBinding<XML, U> {
        DefaultXMLBinding(DataType<XML> dataType, Converter<XML, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, XML value) throws SQLException {
            ctx.statement().setString(ctx.index(), value.toString());
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, XML value) throws SQLException {
            ctx.output().writeString(value.toString());
        }

        @Override
        final XML get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            String string = ctx.resultSet().getString(ctx.index());
            return string == null ? null : XML.valueOf(string);
        }

        @Override
        final XML get0(BindingGetStatementContext<U> ctx) throws SQLException {
            String string = ctx.statement().getString(ctx.index());
            return string == null ? null : XML.valueOf(string);
        }

        @Override
        final XML get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            String string = ctx.input().readString();
            return string == null ? null : XML.valueOf(string);
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 12;
        }
    }

    static final class DelegatingBinding<X, T, U>
    extends InternalBinding<X, U> {
        private final ContextConverter<T, X> delegatingConverter;
        private final InternalBinding<T, U> delegatingBinding;

        DelegatingBinding(DataType<X> originalDataType, ContextConverter<T, X> delegatingConverter, ContextConverter<X, U> originalConverter, Function<? super ContextConverter<T, U>, ? extends InternalBinding<T, U>> f) {
            super(originalDataType, originalConverter);
            this.delegatingConverter = delegatingConverter;
            this.delegatingBinding = f.apply(Converters.of(delegatingConverter, originalConverter));
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, X value) throws SQLException {
            this.delegatingBinding.sqlInline0(ctx, this.delegatingConverter.to(value, ctx.converterContext()));
        }

        @Override
        final void sqlBind0(BindingSQLContext<U> ctx, X value) throws SQLException {
            this.delegatingBinding.sqlBind0(ctx, this.delegatingConverter.to(value, ctx.converterContext()));
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, X value) throws SQLException {
            this.delegatingBinding.set0(ctx, this.delegatingConverter.to(value, ctx.converterContext()));
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            this.delegatingBinding.setNull0(ctx);
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, X value) throws SQLException {
            this.delegatingBinding.set0(ctx, this.delegatingConverter.to(value, ctx.converterContext()));
        }

        @Override
        final X get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return this.delegatingConverter.from(this.delegatingBinding.get0(ctx), ctx.converterContext());
        }

        @Override
        final X get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return this.delegatingConverter.from(this.delegatingBinding.get0(ctx), ctx.converterContext());
        }

        @Override
        final X get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return this.delegatingConverter.from(this.delegatingBinding.get0(ctx), ctx.converterContext());
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) throws SQLException {
            return this.delegatingBinding.sqltype(statement, configuration);
        }
    }

    static final class DefaultLongBinding<U>
    extends InternalBinding<Long, U> {
        DefaultLongBinding(DataType<Long> dataType, Converter<Long, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setBigDecimal(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Long value) {
            ctx.render().sql(value);
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Long value) throws SQLException {
            if (ctx.family() == SQLDialect.ACCESS) {
                ctx.statement().setString(ctx.index(), value.toString());
            } else {
                ctx.statement().setLong(ctx.index(), value);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Long value) throws SQLException {
            ctx.output().writeLong(value);
        }

        @Override
        final Long get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.resultSet(), Long.valueOf(ctx.resultSet().getLong(ctx.index())));
        }

        @Override
        final Long get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.statement(), Long.valueOf(ctx.statement().getLong(ctx.index())));
        }

        @Override
        final Long get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.input(), Long.valueOf(ctx.input().readLong()));
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (configuration.family() == SQLDialect.BIGQUERY) {
                return 12;
            }
            return -5;
        }
    }

    static final class DefaultOffsetDateTimeBinding<U>
    extends InternalBinding<OffsetDateTime, U> {
        private static final DateTimeFormatter F_TIMESTAMPTZ = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 10, SignStyle.NORMAL).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).appendLiteral(' ').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).appendOffset("+HH:MM", "+00:00").toFormatter();
        private static final DateTimeFormatter ERA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnnnnnZZZZZ G", Locale.US);

        DefaultOffsetDateTimeBinding(DataType<OffsetDateTime> dataType, Converter<OffsetDateTime, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, OffsetDateTime value) {
            SQLDialect family = ctx.family();
            switch (family) {
                case HSQLDB: 
                case BIGQUERY: 
                case ORACLE: {
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIMESTAMP)).sql(" '")).sql(DefaultOffsetDateTimeBinding.escape(DefaultOffsetDateTimeBinding.format(value, family), ctx.render()))).sql('\'');
                    break;
                }
                case SQLITE: {
                    ((RenderContext)((RenderContext)ctx.render().sql('\'')).sql(DefaultOffsetDateTimeBinding.escape(DefaultOffsetDateTimeBinding.format(value, family), ctx.render()))).sql('\'');
                    break;
                }
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: {
                    ctx.render().visit(new Cast.CastNative(DSL.inline(DefaultOffsetDateTimeBinding.escape(DefaultOffsetDateTimeBinding.formatISO(value), ctx.render())), Keywords.K_DATETIMEOFFSET));
                    break;
                }
                default: {
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIMESTAMP_WITH_TIME_ZONE)).sql(" '")).sql(DefaultOffsetDateTimeBinding.escape(DefaultOffsetDateTimeBinding.format(value, family), ctx.render()))).sql('\'');
                }
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, OffsetDateTime value) throws SQLException {
            SQLDialect family = ctx.family();
            if (!Boolean.FALSE.equals(ctx.settings().isBindOffsetDateTimeType())) {
                ctx.statement().setObject(ctx.index(), value);
            } else if (family == SQLDialect.ORACLE) {
                ctx.statement().setObject(ctx.index(), DefaultOffsetDateTimeBinding.oracleTSTZ(DefaultExecuteContext.localTargetConnection(ctx), value));
            } else {
                ctx.statement().setString(ctx.index(), DefaultOffsetDateTimeBinding.format(value, family));
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, OffsetDateTime value) throws SQLException {
            if (!Boolean.FALSE.equals(ctx.settings().isBindOffsetDateTimeType())) {
                ctx.output().writeObject(value, JDBCType.TIMESTAMP_WITH_TIMEZONE);
            } else if (ctx.family() == SQLDialect.ORACLE) {
                ((OracleSQLOutput)ctx.output()).writeObject(DefaultOffsetDateTimeBinding.oracleTSTZ(DefaultExecuteContext.localTargetConnection(ctx), value));
            } else {
                throw new UnsupportedOperationException("Type " + String.valueOf(this.dataType) + " is not supported");
            }
        }

        @Override
        final OffsetDateTime get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (!Boolean.FALSE.equals(ctx.settings().isBindOffsetDateTimeType())) {
                return ctx.resultSet().getObject(ctx.index(), OffsetDateTime.class);
            }
            return OffsetDateTimeParser.offsetDateTime(ctx.resultSet().getString(ctx.index()));
        }

        @Override
        final OffsetDateTime get0(BindingGetStatementContext<U> ctx) throws SQLException {
            if (!Boolean.FALSE.equals(ctx.settings().isBindOffsetDateTimeType())) {
                return ctx.statement().getObject(ctx.index(), OffsetDateTime.class);
            }
            return OffsetDateTimeParser.offsetDateTime(ctx.statement().getString(ctx.index()));
        }

        @Override
        final OffsetDateTime get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            if (!Boolean.FALSE.equals(ctx.settings().isBindOffsetDateTimeType())) {
                return ctx.input().readObject(OffsetDateTime.class);
            }
            if (ctx.family() == SQLDialect.ORACLE) {
                Object tsTz = ((OracleJdbc2SQLInput)ctx.input()).readOracleObject();
                if (tsTz == null) {
                    return null;
                }
                return ((TIMESTAMPTZ)tsTz).offsetDateTimeValue(DefaultExecuteContext.localTargetConnection(ctx));
            }
            throw new UnsupportedOperationException("Type " + String.valueOf(this.dataType) + " is not supported");
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (!Boolean.FALSE.equals(configuration.settings().isBindOffsetDateTimeType())) {
                return 2014;
            }
            if (configuration.family() == SQLDialect.ORACLE) {
                return -101;
            }
            return 12;
        }

        @Pro
        private static final Object oracleTSTZ(Connection connection, OffsetDateTime val) throws SQLException {
            return new TIMESTAMPTZ(connection, Timestamp.from(val.toInstant()), (ZoneId)val.getOffset());
        }

        @Pro
        private static final Object oracleTSTZ(Connection connection, Instant instant) throws SQLException {
            return new TIMESTAMPTZ(connection, Timestamp.from(instant), (ZoneId)ZoneOffset.UTC);
        }

        private static final String format(OffsetDateTime val, SQLDialect family) {
            if (family == SQLDialect.POSTGRES) {
                if (val.toEpochSecond() * 1000L == 9223372036825200000L) {
                    return "infinity";
                }
                if (val.toEpochSecond() * 1000L == -9223372036832400000L) {
                    return "-infinity";
                }
            }
            if (family == SQLDialect.POSTGRES && val.getYear() <= 0) {
                return DefaultOffsetDateTimeBinding.formatEra(val);
            }
            return val.format(F_TIMESTAMPTZ);
        }

        private static final String formatISO(OffsetDateTime val) {
            return val.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        private static final String formatEra(OffsetDateTime val) {
            return val.format(ERA);
        }
    }

    static final class DefaultOffsetTimeBinding<U>
    extends InternalBinding<OffsetTime, U> {
        DefaultOffsetTimeBinding(DataType<OffsetTime> dataType, Converter<OffsetTime, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, OffsetTime value) {
            switch (ctx.family()) {
                case HSQLDB: {
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIME)).sql(" '")).sql(DefaultOffsetTimeBinding.escape(DefaultOffsetTimeBinding.format(value), ctx.render()))).sql('\'');
                    break;
                }
                case SQLITE: {
                    ((RenderContext)((RenderContext)ctx.render().sql('\'')).sql(DefaultOffsetTimeBinding.escape(DefaultOffsetTimeBinding.format(value), ctx.render()))).sql('\'');
                    break;
                }
                case ORACLE: {
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIMESTAMP)).sql(" '1970-01-01 ")).sql(DefaultOffsetTimeBinding.escape(DefaultOffsetTimeBinding.format(value), ctx.render()))).sql('\'');
                }
                default: {
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIME_WITH_TIME_ZONE)).sql(" '")).sql(DefaultOffsetTimeBinding.escape(DefaultOffsetTimeBinding.format(value), ctx.render()))).sql('\'');
                }
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, OffsetTime value) throws SQLException {
            if (Boolean.FALSE.equals(ctx.settings().isBindOffsetTimeType())) {
                Object string = DefaultOffsetTimeBinding.format(value);
                if (ctx.family() == SQLDialect.ORACLE) {
                    string = "1970-01-01 " + (String)string;
                }
                ctx.statement().setString(ctx.index(), (String)string);
            } else {
                ctx.statement().setObject(ctx.index(), value);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, OffsetTime value) throws SQLException {
            throw new UnsupportedOperationException("Type " + String.valueOf(this.dataType) + " is not supported");
        }

        @Override
        final OffsetTime get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (!Boolean.FALSE.equals(ctx.settings().isBindOffsetTimeType())) {
                return ctx.resultSet().getObject(ctx.index(), OffsetTime.class);
            }
            return OffsetDateTimeParser.offsetTime(ctx.resultSet().getString(ctx.index()));
        }

        @Override
        final OffsetTime get0(BindingGetStatementContext<U> ctx) throws SQLException {
            if (!Boolean.FALSE.equals(ctx.settings().isBindOffsetTimeType())) {
                return ctx.statement().getObject(ctx.index(), OffsetTime.class);
            }
            return OffsetDateTimeParser.offsetTime(ctx.statement().getString(ctx.index()));
        }

        @Override
        final OffsetTime get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            throw new UnsupportedOperationException("Type " + String.valueOf(this.dataType) + " is not supported");
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (!Boolean.FALSE.equals(configuration.settings().isBindOffsetTimeType())) {
                return 2013;
            }
            if (configuration.family() == SQLDialect.ORACLE) {
                return -101;
            }
            return 12;
        }

        private static final String format(OffsetTime val) {
            return StringUtils.replace(val.format(DateTimeFormatter.ISO_OFFSET_TIME), "Z", "+00:00");
        }
    }

    static final class DefaultInstantBinding<U>
    extends InternalBinding<Instant, U> {
        private static final ContextConverter<OffsetDateTime, Instant> CONVERTER = ContextConverter.ofNullable(OffsetDateTime.class, Instant.class, (BiFunction<OffsetDateTime, ConverterContext, Instant> & Serializable)(t2, x) -> t2.toInstant(), (BiFunction<Instant, ConverterContext, OffsetDateTime> & Serializable)(i, x) -> OffsetDateTime.ofInstant(i, ZoneOffset.UTC));
        private final DefaultOffsetDateTimeBinding<U> delegate = new DefaultOffsetDateTimeBinding(SQLDataType.OFFSETDATETIME, Converters.of(CONVERTER, this.converter()));

        DefaultInstantBinding(DataType<Instant> dataType, Converter<Instant, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            this.delegate.setNull0(ctx);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Instant value) throws SQLException {
            this.delegate.sqlInline0(ctx, CONVERTER.to(value, ctx.converterContext()));
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Instant value) throws SQLException {
            this.delegate.set0(ctx, CONVERTER.to(value, ctx.converterContext()));
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Instant value) throws SQLException {
            this.delegate.set0(ctx, CONVERTER.to(value, ctx.converterContext()));
        }

        @Override
        final Instant get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return CONVERTER.from((OffsetDateTime)this.delegate.get0((BindingGetResultSetContext)ctx), ctx.converterContext());
        }

        @Override
        final Instant get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return CONVERTER.from((OffsetDateTime)this.delegate.get0((BindingGetStatementContext)ctx), ctx.converterContext());
        }

        @Override
        final Instant get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return CONVERTER.from((OffsetDateTime)this.delegate.get0((BindingGetSQLInputContext)ctx), ctx.converterContext());
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) throws SQLException {
            return this.delegate.sqltype(statement, configuration);
        }
    }

    static final class DefaultRowIdBinding<U>
    extends InternalBinding<RowId, U> {
        DefaultRowIdBinding(DataType<RowId> dataType, Converter<RowId, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, RowId value) throws SQLException {
            ctx.statement().setObject(ctx.index(), value.value());
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, RowId value) throws SQLException {
            throw new DataTypeException("Type " + String.valueOf(this.dataType) + " is not supported");
        }

        @Override
        final RowId get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return new RowIdImpl(ctx.resultSet().getObject(ctx.index()));
        }

        @Override
        final RowId get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return new RowIdImpl(ctx.statement().getObject(ctx.index()));
        }

        @Override
        final RowId get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            throw new DataTypeException("Type " + String.valueOf(this.dataType) + " is not supported");
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return -8;
        }
    }

    static final class DefaultShortBinding<U>
    extends InternalBinding<Short, U> {
        DefaultShortBinding(DataType<Short> dataType, Converter<Short, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setBigDecimal(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Short value) {
            ctx.render().sql(value.shortValue());
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Short value) throws SQLException {
            ctx.statement().setShort(ctx.index(), value);
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Short value) throws SQLException {
            ctx.output().writeShort(value);
        }

        @Override
        final Short get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.resultSet(), Short.valueOf(ctx.resultSet().getShort(ctx.index())));
        }

        @Override
        final Short get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.statement(), Short.valueOf(ctx.statement().getShort(ctx.index())));
        }

        @Override
        final Short get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.input(), Short.valueOf(ctx.input().readShort()));
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (configuration.family() == SQLDialect.BIGQUERY) {
                return 12;
            }
            return 5;
        }
    }

    static final class DefaultNStringBinding<U>
    extends InternalBinding<String, U> {
        private final DefaultStringBinding<U> fallback;

        DefaultNStringBinding(DataType<String> dataType, Converter<String, U> converter) {
            super(dataType, converter);
            this.fallback = new DefaultStringBinding<U>(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setNString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        void sqlInline0(BindingSQLContext<U> ctx, String value) throws SQLException {
            if (NO_SUPPORT_NVARCHAR.contains((Object)ctx.dialect())) {
                this.fallback.sqlInline0(ctx, value);
            } else {
                ctx.render().sql('N');
                super.sqlInline0(ctx, value);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, String value) throws SQLException {
            if (NO_SUPPORT_NVARCHAR.contains((Object)ctx.dialect())) {
                this.fallback.set0(ctx, value);
            } else {
                ctx.statement().setNString(ctx.index(), value);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, String value) throws SQLException {
            if (NO_SUPPORT_NVARCHAR.contains((Object)ctx.dialect())) {
                this.fallback.set0(ctx, value);
            } else if (this.dataType.isLob() && ctx.family() == SQLDialect.ORACLE) {
                ctx.output().writeNClob(NClobBinding.newNClob(ctx, value));
            } else {
                ctx.output().writeNString(value);
            }
        }

        @Override
        final String get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (NO_SUPPORT_NVARCHAR.contains((Object)ctx.dialect())) {
                return this.fallback.get0((BindingGetResultSetContext)ctx);
            }
            return ctx.resultSet().getNString(ctx.index());
        }

        @Override
        final String get0(BindingGetStatementContext<U> ctx) throws SQLException {
            if (NO_SUPPORT_NVARCHAR.contains((Object)ctx.dialect())) {
                return this.fallback.get0((BindingGetStatementContext)ctx);
            }
            return ctx.statement().getNString(ctx.index());
        }

        @Override
        final String get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            if (NO_SUPPORT_NVARCHAR.contains((Object)ctx.dialect())) {
                return this.fallback.get0((BindingGetSQLInputContext)ctx);
            }
            return ctx.input().readNString();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (NO_SUPPORT_NVARCHAR.contains((Object)configuration.dialect())) {
                return this.fallback.sqltype(statement, configuration);
            }
            if (this.dataType.isLob() && configuration.family() == SQLDialect.ORACLE) {
                return 2011;
            }
            return -9;
        }
    }

    static final class DefaultStringBinding<U>
    extends InternalBinding<String, U> {
        DefaultStringBinding(DataType<String> dataType, Converter<String, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, String value) throws SQLException {
            if (ctx.family() == SQLDialect.DERBY) {
                this.sqlInlineWorkaround6516(ctx, value, 8192, "");
            } else if (this.dataType.isLob() && ctx.family() == SQLDialect.DB2) {
                this.sqlInlineWorkaround6516(ctx, value, 8192, "to_clob");
            } else if (this.dataType.isLob() && ctx.family() == SQLDialect.ORACLE) {
                this.sqlInlineWorkaround6516(ctx, value, 100, "to_clob");
            } else {
                super.sqlInline0(ctx, value);
            }
        }

        private final void sqlInlineWorkaround6516(BindingSQLContext<U> ctx, String value, int limit, String prefix) throws SQLException {
            int l = value.length();
            if (l > limit) {
                ctx.render().sql('(');
                for (int i = 0; i < l; i += limit) {
                    if (i > 0) {
                        ctx.render().sql(" || ");
                    }
                    ((RenderContext)ctx.render().sql(prefix)).sql("(");
                    super.sqlInline0(ctx, value.substring(i, Math.min(l, i + limit)));
                    ctx.render().sql(')');
                }
                ctx.render().sql(')');
            } else {
                super.sqlInline0(ctx, value);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, String value) throws SQLException {
            ctx.statement().setString(ctx.index(), value);
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, String value) throws SQLException {
            if (this.dataType.isLob() && ctx.family() == SQLDialect.ORACLE) {
                ctx.output().writeClob(ClobBinding.newClob(ctx, value));
            } else {
                ctx.output().writeString(value);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        final String get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.TERADATA && (this.dataType.isLob() || ctx.resultSet().getMetaData().getColumnType(ctx.index()) == 2005)) {
                String string;
                Clob clob = null;
                try {
                    clob = ctx.resultSet().getClob(ctx.index());
                    string = clob == null ? null : clob.getSubString(1L, Tools.asInt(clob.length()));
                }
                catch (Throwable throwable) {
                    JDBCUtils.safeFree(clob);
                    throw throwable;
                }
                JDBCUtils.safeFree(clob);
                return string;
            }
            return ctx.resultSet().getString(ctx.index());
        }

        @Override
        final String get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return ctx.statement().getString(ctx.index());
        }

        @Override
        final String get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return ctx.input().readString();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            if (this.dataType.isLob() && configuration.family() == SQLDialect.ORACLE) {
                return 2005;
            }
            return 12;
        }
    }

    static final class DefaultTimeBinding<U>
    extends InternalBinding<Time, U> {
        DefaultTimeBinding(DataType<Time> dataType, Converter<Time, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Time value) {
            switch (ctx.family()) {
                case SYBASE: 
                case ASE: 
                case SQLITE: {
                    ((RenderContext)((RenderContext)ctx.render().sql('\'')).sql(new SimpleDateFormat("HH:mm:ss").format(value))).sql('\'');
                    break;
                }
                case ACCESS: {
                    ((RenderContext)((RenderContext)ctx.render().sql("{t '")).sql(DefaultTimeBinding.escape(value, ctx.render()))).sql("'}");
                    break;
                }
                case INFORMIX: {
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_DATETIME)).sql('(')).sql(DefaultTimeBinding.escape(value, ctx.render()))).sql(") ")).visit(Keywords.K_HOUR_TO_SECOND);
                    break;
                }
                case DERBY: {
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIME)).sql("('")).sql(DefaultTimeBinding.escape(value, ctx.render()))).sql("')");
                    break;
                }
                case EXASOL: 
                case ORACLE: {
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIMESTAMP)).sql(" '1970-01-01 ")).sql(DefaultTimeBinding.escape(value, ctx.render()))).sql('\'');
                    break;
                }
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: {
                    ctx.render().visit(new Cast.CastNative<Time>(DSL.inline(DefaultTimeBinding.escape(value, ctx.render())), SQLDataType.TIME));
                    break;
                }
                default: {
                    if (REQUIRE_JDBC_DATE_LITERAL.contains((Object)ctx.dialect())) {
                        ((RenderContext)((RenderContext)ctx.render().sql("{t '")).sql(DefaultTimeBinding.escape(value, ctx.render()))).sql("'}");
                        break;
                    }
                    ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIME)).sql(" '")).sql(DefaultTimeBinding.escape(value, ctx.render()))).sql('\'');
                }
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Time value) throws SQLException {
            if (ctx.family() == SQLDialect.SQLITE) {
                ctx.statement().setString(ctx.index(), value.toString());
            } else {
                ctx.statement().setTime(ctx.index(), value);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Time value) throws SQLException {
            ctx.output().writeTime(value);
        }

        @Override
        final Time get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.SQLITE) {
                String time = ctx.resultSet().getString(ctx.index());
                return time == null ? null : new Time(DefaultBinding.parse(Time.class, time));
            }
            return ctx.resultSet().getTime(ctx.index());
        }

        @Override
        final Time get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return ctx.statement().getTime(ctx.index());
        }

        @Override
        final Time get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return ctx.input().readTime();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 92;
        }
    }

    static final class DefaultTimestampBinding<U>
    extends InternalBinding<Timestamp, U> {
        private static final Set<SQLDialect> INLINE_AS_STRING_LITERAL = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.SQLITE, SQLDialect.SYBASE);

        DefaultTimestampBinding(DataType<Timestamp> dataType, Converter<Timestamp, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setTimestamp(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Timestamp value) {
            if (INLINE_AS_STRING_LITERAL.contains((Object)ctx.dialect())) {
                ((RenderContext)((RenderContext)ctx.render().sql('\'')).sql(DefaultTimestampBinding.escape(value, ctx.render()))).sql('\'');
            } else if (ctx.family() == SQLDialect.ACCESS) {
                ((RenderContext)((RenderContext)ctx.render().sql('#')).sql(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(value))).sql('#');
            } else if (ctx.family() == SQLDialect.INFORMIX) {
                ((RenderContext)((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_DATETIME)).sql('(')).sql(DefaultTimestampBinding.escape(value, ctx.render()))).sql(") ")).visit(Keywords.K_YEAR_TO_FRACTION);
            } else if (ctx.family() == SQLDialect.BIGQUERY) {
                ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_DATETIME)).sql(" '")).sql(DefaultTimestampBinding.escape(value, ctx.render()))).sql('\'');
            } else if (ctx.family() == SQLDialect.SQLSERVER || ctx.family() == SQLDialect.SQLDATAWAREHOUSE) {
                ctx.render().visit(new Cast.CastNative(DSL.inline(DefaultTimestampBinding.escape(value, ctx.render())), Keywords.K_DATETIME2));
            } else if (ctx.family() == SQLDialect.DERBY) {
                ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIMESTAMP)).sql("('")).sql(DefaultTimestampBinding.escape(value, ctx.render()))).sql("')");
            } else if (ctx.family() == SQLDialect.CUBRID) {
                ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_DATETIME)).sql(" '")).sql(DefaultTimestampBinding.escape(value, ctx.render()))).sql('\'');
            } else if (REQUIRE_JDBC_DATE_LITERAL.contains((Object)ctx.dialect())) {
                ((RenderContext)((RenderContext)ctx.render().sql("{ts '")).sql(DefaultTimestampBinding.escape(value, ctx.render()))).sql("'}");
            } else {
                ((RenderContext)((RenderContext)((RenderContext)ctx.render().visit(Keywords.K_TIMESTAMP)).sql(" '")).sql(this.format(value, ctx.render()))).sql('\'');
            }
        }

        private final String format(Timestamp value, RenderContext render) {
            if (render.family() == SQLDialect.POSTGRES) {
                if (value.getTime() == 9223372036825200000L) {
                    return "infinity";
                }
                if (value.getTime() == -9223372036832400000L) {
                    return "-infinity";
                }
            }
            return DefaultTimestampBinding.escape(value, render);
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Timestamp value) throws SQLException {
            if (ctx.family() == SQLDialect.SQLITE) {
                ctx.statement().setString(ctx.index(), value.toString());
            } else {
                ctx.statement().setTimestamp(ctx.index(), value);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Timestamp value) throws SQLException {
            ctx.output().writeTimestamp(value);
        }

        @Override
        final Timestamp get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.SQLITE) {
                String timestamp = ctx.resultSet().getString(ctx.index());
                return timestamp == null ? null : new Timestamp(DefaultBinding.parse(Timestamp.class, timestamp));
            }
            return ctx.resultSet().getTimestamp(ctx.index());
        }

        @Override
        final Timestamp get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return ctx.statement().getTimestamp(ctx.index());
        }

        @Override
        final Timestamp get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return ctx.input().readTimestamp();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 93;
        }
    }

    static final class DefaultUUIDBinding<U>
    extends InternalBinding<UUID, U> {
        DefaultUUIDBinding(DataType<UUID> dataType, Converter<UUID, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.BIGQUERY) {
                ctx.statement().setString(ctx.index(), null);
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, UUID value) throws SQLException {
            switch (ctx.family()) {
                case H2: 
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    ctx.statement().setObject(ctx.index(), value);
                    break;
                }
                default: {
                    ctx.statement().setString(ctx.index(), value.toString());
                }
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, UUID value) throws SQLException {
            ctx.output().writeString(value.toString());
        }

        @Override
        final UUID get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case H2: 
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    Object o = ctx.resultSet().getObject(ctx.index());
                    if (o == null) {
                        return null;
                    }
                    if (o instanceof UUID) {
                        UUID u = (UUID)o;
                        return u;
                    }
                    return Convert.convert((Object)o.toString(), UUID.class);
                }
            }
            return Convert.convert((Object)ctx.resultSet().getString(ctx.index()), UUID.class);
        }

        @Override
        final UUID get0(BindingGetStatementContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case H2: 
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    return (UUID)ctx.statement().getObject(ctx.index());
                }
            }
            return Convert.convert((Object)ctx.statement().getString(ctx.index()), UUID.class);
        }

        @Override
        final UUID get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return Convert.convert((Object)ctx.input().readString(), UUID.class);
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            switch (configuration.family()) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    return 1111;
                }
            }
            return 12;
        }
    }

    static final class DefaultYearToSecondBinding<U>
    extends InternalBinding<YearToSecond, U> {
        private static final Set<SQLDialect> REQUIRE_PG_INTERVAL = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.YUGABYTEDB);

        DefaultYearToSecondBinding(DataType<YearToSecond> dataType, Converter<YearToSecond, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, YearToSecond value) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                ctx.render().visit(DSL.inline(PostgresUtils.toPGInterval(value).toString()));
            } else {
                super.sqlInline0(ctx, value);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, YearToSecond value) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                ctx.statement().setString(ctx.index(), PostgresUtils.toPGInterval(value).toString());
            } else {
                ctx.statement().setString(ctx.index(), value.toString());
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, YearToSecond value) throws SQLException {
            ctx.output().writeString(value.toString());
        }

        @Override
        final YearToSecond get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                Object object = ctx.resultSet().getObject(ctx.index());
                return object == null ? null : PostgresUtils.toYearToSecond(object);
            }
            String string = ctx.resultSet().getString(ctx.index());
            return string == null ? null : YearToSecond.valueOf(string);
        }

        @Override
        final YearToSecond get0(BindingGetStatementContext<U> ctx) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                Object object = ctx.statement().getObject(ctx.index());
                return object == null ? null : PostgresUtils.toYearToSecond(object);
            }
            String string = ctx.statement().getString(ctx.index());
            return string == null ? null : YearToSecond.valueOf(string);
        }

        @Override
        final YearToSecond get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            String string = ctx.input().readString();
            return string == null ? null : YearToSecond.valueOf(string);
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 12;
        }
    }

    static final class DefaultYearToMonthBinding<U>
    extends InternalBinding<YearToMonth, U> {
        private static final Set<SQLDialect> REQUIRE_PG_INTERVAL = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.YUGABYTEDB);
        private static final Set<SQLDialect> REQUIRE_STANDARD_INTERVAL = SQLDialect.supportedBy(SQLDialect.H2);

        DefaultYearToMonthBinding(DataType<YearToMonth> dataType, Converter<YearToMonth, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, YearToMonth value) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                ctx.render().visit(DSL.inline(PostgresUtils.toPGInterval(value).toString()));
            } else {
                super.sqlInline0(ctx, value);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, YearToMonth value) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                ctx.statement().setString(ctx.index(), PostgresUtils.toPGInterval(value).toString());
            } else {
                ctx.statement().setString(ctx.index(), this.renderYTM(ctx, value));
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, YearToMonth value) throws SQLException {
            ctx.output().writeString(this.renderYTM(ctx, value));
        }

        @Override
        final YearToMonth get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                return PostgresUtils.toYearToMonth(ctx.resultSet().getString(ctx.index()));
            }
            return this.parseYTM(ctx, ctx.resultSet().getString(ctx.index()));
        }

        @Override
        final YearToMonth get0(BindingGetStatementContext<U> ctx) throws SQLException {
            if (REQUIRE_PG_INTERVAL.contains((Object)ctx.dialect())) {
                return PostgresUtils.toYearToMonth(ctx.statement().getString(ctx.index()));
            }
            return this.parseYTM(ctx, ctx.statement().getString(ctx.index()));
        }

        @Override
        final YearToMonth get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return this.parseYTM(ctx, ctx.input().readString());
        }

        private final YearToMonth parseYTM(Scope scope, String string) {
            if (string == null) {
                return null;
            }
            if (REQUIRE_STANDARD_INTERVAL.contains((Object)scope.dialect()) && string.startsWith("INTERVAL")) {
                return (YearToMonth)((Param)scope.dsl().parser().parseField(string)).getValue();
            }
            return YearToMonth.valueOf(string);
        }

        private final String renderYTM(Scope scope, YearToMonth ytm) {
            if (ytm == null) {
                return null;
            }
            if (REQUIRE_STANDARD_INTERVAL.contains((Object)scope.dialect())) {
                return "INTERVAL '" + ytm.toString() + "' YEAR TO MONTH";
            }
            return ytm.toString();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 12;
        }
    }

    static final class DefaultYearBinding<U>
    extends InternalBinding<Year, U> {
        DefaultYearBinding(DataType<Year> dataType, Converter<Year, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Year value) {
            ctx.render().sql(value.getValue());
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Year value) throws SQLException {
            ctx.statement().setInt(ctx.index(), value.getValue());
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Year value) throws SQLException {
            ctx.output().writeInt(value.getValue());
        }

        @Override
        final Year get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.resultSet(), Year.of(ctx.resultSet().getInt(ctx.index())));
        }

        @Override
        final Year get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.statement(), Year.of(ctx.statement().getInt(ctx.index())));
        }

        @Override
        final Year get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return JDBCUtils.wasNull(ctx.input(), Year.of(ctx.input().readInt()));
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 5;
        }
    }

    static final class DefaultArrayBinding<U>
    extends InternalBinding<Object[], U> {
        private static final Set<SQLDialect> REQUIRES_ARRAY_CAST = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
        @Pro
        private static final Set<SQLDialect> ARRAY_AS_ROWS = SQLDialect.supportedUntil(SQLDialect.H2_1_4_200);

        DefaultArrayBinding(DataType<Object[]> dataType, Converter<Object[], U> converter) {
            super(dataType, converter);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Object[] value) throws SQLException {
            String separator = "";
            if (REQUIRES_ARRAY_CAST.contains((Object)ctx.dialect())) {
                DataType<Object[]> arrayType = this.dataType.getType() == Object[].class ? DefaultDataType.getDataType(ctx.dialect(), this.deriveArrayTypeFromComponentType(value)) : this.dataType;
                ctx.render().visit(DSL.cast(DSL.inline(PostgresUtils.toPGArrayString(value)), arrayType));
            } else if (ARRAY_AS_ROWS.contains((Object)ctx.dialect())) {
                ctx.render().sql('(');
                for (Object o : value) {
                    ctx.render().sql(separator);
                    DefaultBinding.binding(this.dataType.getArrayComponentDataType()).sql(new DefaultBindingSQLContext<Object>(ctx.configuration(), ctx.data(), ctx.render(), o));
                    separator = ", ";
                }
                ctx.render().sql(')');
            } else {
                boolean squareBrackets = ctx.family() != SQLDialect.HANA;
                ctx.render().visit(Keywords.K_ARRAY);
                ctx.render().sql(squareBrackets ? (char)'[' : '(');
                for (Object o : value) {
                    ctx.render().sql(separator);
                    DefaultBinding.binding(this.dataType.getArrayComponentDataType()).sql(new DefaultBindingSQLContext<Object>(ctx.configuration(), ctx.data(), ctx.render(), o));
                    separator = ", ";
                }
                ctx.render().sql(squareBrackets ? (char)']' : ')');
            }
        }

        private final Class<? extends Object[]> deriveArrayTypeFromComponentType(Object[] value) {
            for (Object o : value) {
                if (o == null) continue;
                return Internal.arrayType(o.getClass());
            }
            return String[].class;
        }

        @Override
        final void sqlBind0(BindingSQLContext<U> ctx, Object[] value) throws SQLException {
            Cast.renderCastIf(ctx.render(), c -> super.sqlBind0(ctx, value), c -> {
                if (EnumType.class.isAssignableFrom(this.dataType.getType().getComponentType())) {
                    DefaultEnumTypeBinding.pgRenderEnumCast(ctx.render(), this.dataType.getType(), DefaultEnumTypeBinding.pgEnumValue(this.dataType.getType()));
                } else {
                    ctx.render().sql(this.dataType.getCastTypeName(ctx.render().configuration()));
                }
            }, () -> REQUIRES_ARRAY_CAST.contains((Object)ctx.family()));
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Object[] value) throws SQLException {
            switch (ctx.family()) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    ctx.statement().setString(ctx.index(), PostgresUtils.toPGArrayString(value));
                    break;
                }
                case HSQLDB: {
                    Object[] a = value;
                    Class<Object> t2 = this.dataType.getType();
                    if (t2 == UUID[].class) {
                        a = Convert.convertArray(a, byte[][].class);
                        t2 = byte[][].class;
                    }
                    ctx.statement().setArray(ctx.index(), new MockArray<Object>(ctx.family(), a, t2));
                    break;
                }
                case H2: {
                    ctx.statement().setObject(ctx.index(), value);
                    break;
                }
                default: {
                    throw new SQLDialectNotSupportedException("Cannot bind ARRAY types in dialect " + String.valueOf((Object)ctx.family()));
                }
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Object[] value) throws SQLException {
            ctx.output().writeArray(new MockArray<Object>(ctx.family(), value, this.dataType.getType()));
        }

        @Override
        final Object[] get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    return (Object[])DefaultArrayBinding.pgGetArray(ctx, ctx.resultSet(), this.dataType, ctx.index());
                }
                case HSQLDB: {
                    if (ctx.resultSet().getObject(ctx.index()) == null) {
                        return null;
                    }
                    return DefaultArrayBinding.convertArray(ctx.resultSet().getArray(ctx.index()), this.dataType.getType());
                }
            }
            return DefaultArrayBinding.convertArray(ctx.resultSet().getArray(ctx.index()), this.dataType.getType());
        }

        @Override
        final Object[] get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return DefaultArrayBinding.convertArray(ctx.statement().getObject(ctx.index()), this.dataType.getType());
        }

        @Override
        final Object[] get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            java.sql.Array array = ctx.input().readArray();
            return array == null ? null : (Object[])array.getArray();
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 2003;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private static final <T> T pgGetArray(ExecuteScope ctx, ResultSet rs, DataType<T> dataType, int index) throws SQLException {
            java.sql.Array array = null;
            try {
                Object[] objectArray;
                array = rs.getArray(index);
                if (array == null) {
                    T t2 = null;
                    return t2;
                }
                DataType<?> cdt = dataType.getArrayComponentDataType();
                try {
                    if (cdt.isBinary() || cdt.isUDT()) {
                        throw new ControlFlowSignal("GOTO the next array deserialisation strategy");
                    }
                    objectArray = DefaultArrayBinding.convertArray(array, dataType.getType());
                }
                catch (Exception e) {
                    ArrayList result = new ArrayList();
                    try (ResultSet arrayRs = array.getResultSet();){
                        Binding<?, ?> binding = DefaultBinding.binding(dataType.getArrayComponentDataType());
                        DefaultBindingGetResultSetContext out = new DefaultBindingGetResultSetContext(ctx.executeContext(), arrayRs, 2);
                        while (arrayRs.next()) {
                            binding.get(out);
                            result.add(out.value());
                        }
                    }
                    catch (Exception fatal) {
                        String string = null;
                        try {
                            string = rs.getString(index);
                        }
                        catch (SQLException sQLException) {
                            // empty catch block
                        }
                        log.error("Cannot parse array", string, fatal);
                        T t3 = null;
                        JDBCUtils.safeFree(array);
                        return t3;
                    }
                    Object[] objectArray2 = DefaultArrayBinding.convertArray(result.toArray(), dataType.getType());
                    JDBCUtils.safeFree(array);
                    return (T)objectArray2;
                }
                return (T)objectArray;
            }
            finally {
                JDBCUtils.safeFree(array);
            }
        }

        private static final Object[] convertArray(Object array, Class<? extends Object[]> type) throws SQLException {
            if (array instanceof Object[]) {
                return Convert.convert(array, type);
            }
            if (array instanceof java.sql.Array) {
                java.sql.Array a = (java.sql.Array)array;
                return DefaultArrayBinding.convertArray(a, type);
            }
            return null;
        }

        private static final Object[] convertArray(java.sql.Array array, Class<? extends Object[]> type) throws SQLException {
            if (array != null) {
                return Convert.convert(array.getArray(), type);
            }
            return null;
        }
    }

    @Pro
    static final class DefaultArrayRecordBinding<U>
    extends AbstractArrayOrAssociativeArrayRecordBinding<ArrayRecord<?>, U> {
        DefaultArrayRecordBinding(DataType<ArrayRecord<?>> dataType, Converter<ArrayRecord<?>, U> converter) {
            super(dataType, converter);
        }

        @Override
        final ArrayRecord<?> set(ExecuteScope ctx, ArrayRecord<?> target, java.sql.Array source2) throws SQLException {
            return this.set0(ctx, target, source2);
        }

        private final <T> ArrayRecord<T> set0(ExecuteScope ctx, ArrayRecord<T> target, java.sql.Array source2) throws SQLException {
            if (source2 == null) {
                target.clear();
            } else {
                target.addAll(Tools.map((Object[])source2.getArray(DefaultBinding.typeMap(target.getClass(), ctx)), o -> {
                    if (o instanceof java.sql.Array) {
                        java.sql.Array a = (java.sql.Array)o;
                        return this.set(ctx, (ArrayRecord)Reflect.onClass(target.getDataType().getType()).create().get(), a);
                    }
                    return target.getDataType().convert(o);
                }));
            }
            if (Tools.attachRecords(ctx.configuration())) {
                target.attach(ctx.configuration());
            }
            return target;
        }
    }

    @Pro
    static final class DefaultAssociativeArrayRecordBinding<U>
    extends AbstractArrayOrAssociativeArrayRecordBinding<AssociativeArrayRecord<?, ?>, U> {
        DefaultAssociativeArrayRecordBinding(DataType<AssociativeArrayRecord<?, ?>> dataType, Converter<AssociativeArrayRecord<?, ?>, U> converter) {
            super(dataType, converter);
        }

        @Override
        final AssociativeArrayRecord<?, ?> set(ExecuteScope ctx, AssociativeArrayRecord<?, ?> target, java.sql.Array source2) throws SQLException {
            return this.set0(ctx, target, source2);
        }

        private final <K, V> AssociativeArrayRecord<K, V> set0(ExecuteScope ctx, AssociativeArrayRecord<K, V> target, java.sql.Array source2) throws SQLException {
            int i = 1;
            if (source2 == null) {
                target.clear();
            } else {
                for (Object o2 : Tools.map((Object[])source2.getArray(DefaultBinding.typeMap(target.getClass(), ctx)), o -> {
                    if (o instanceof java.sql.Array) {
                        java.sql.Array a = (java.sql.Array)o;
                        return this.set(ctx, (AssociativeArrayRecord)Reflect.onClass(target.getDataType().getType()).create().get(), a);
                    }
                    return target.getDataType().convert(o);
                })) {
                    target.put(i++, o2);
                }
            }
            if (Tools.attachRecords(ctx.configuration())) {
                target.attach(ctx.configuration());
            }
            return target;
        }
    }

    static final class DefaultEnumTypeBinding<U>
    extends InternalBinding<EnumType, U> {
        private static final Set<SQLDialect> REQUIRE_ENUM_CAST = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);

        DefaultEnumTypeBinding(DataType<EnumType> dataType, Converter<EnumType, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, EnumType value) throws SQLException {
            EnumType enumValue = DefaultEnumTypeBinding.pgEnumValue(this.dataType.getType());
            Cast.renderCastIf(ctx.render(), c -> DefaultBinding.binding(SQLDataType.VARCHAR).sql(new DefaultBindingSQLContext<String>(ctx.configuration(), ctx.data(), ctx.render(), value.getLiteral())), c -> DefaultEnumTypeBinding.pgRenderEnumCast(c, this.dataType.getType(), enumValue), () -> REQUIRE_ENUM_CAST.contains((Object)ctx.dialect()) && enumValue.getSchema() != null);
        }

        @Override
        final void sqlBind0(BindingSQLContext<U> ctx, EnumType value) throws SQLException {
            EnumType enumValue = DefaultEnumTypeBinding.pgEnumValue(this.dataType.getType());
            Cast.renderCastIf(ctx.render(), c -> super.sqlBind0(ctx, value), c -> DefaultEnumTypeBinding.pgRenderEnumCast(c, this.dataType.getType(), enumValue), () -> REQUIRE_ENUM_CAST.contains((Object)ctx.dialect()) && enumValue.getSchema() != null);
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, EnumType value) throws SQLException {
            ctx.statement().setString(ctx.index(), value.getLiteral());
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, EnumType value) throws SQLException {
            ctx.output().writeString(value.getLiteral());
        }

        @Override
        final EnumType get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return DefaultEnumTypeBinding.getEnumType(this.dataType.getType(), ctx.resultSet().getString(ctx.index()));
        }

        @Override
        final EnumType get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return DefaultEnumTypeBinding.getEnumType(this.dataType.getType(), ctx.statement().getString(ctx.index()));
        }

        @Override
        final EnumType get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return DefaultEnumTypeBinding.getEnumType(this.dataType.getType(), ctx.input().readString());
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 12;
        }

        static final EnumType pgEnumValue(Class<?> type) {
            Class<?> enumType = type.isArray() ? type.getComponentType() : type;
            EnumType[] enums = Tools.enums(enumType);
            if (enums == null || enums.length == 0) {
                throw new IllegalArgumentException("Not a valid EnumType : " + String.valueOf(type));
            }
            return enums[0];
        }

        static final void pgRenderEnumCast(Context<?> ctx, Class<?> type, EnumType value) {
            Schema schema = value.getSchema();
            if (schema != null) {
                schema = DSL.using(ctx.configuration()).map(schema);
                if (schema != null && Boolean.TRUE.equals(ctx.configuration().settings().isRenderSchema())) {
                    ctx.visit(schema);
                    ctx.sql('.');
                }
                ctx.visit(DSL.name(value.getName()));
                if (type.isArray()) {
                    ctx.sql("[]");
                }
            }
        }

        static final <E extends EnumType> E getEnumType(Class<? extends E> type, String literal) {
            try {
                return (E)Tools.findAny(Tools.enums(type), e -> e.getLiteral().equals(literal));
            }
            catch (Exception e2) {
                throw new DataTypeException("Unknown enum literal found : " + literal);
            }
        }
    }

    static final class DefaultRecordBinding<U>
    extends InternalBinding<Record, U> {
        static final Set<SQLDialect> REQUIRE_RECORD_CAST = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);

        DefaultRecordBinding(DataType<Record> dataType, Converter<Record, U> converter) {
            super(dataType, converter);
        }

        @Override
        void sqlBind0(BindingSQLContext<U> ctx, Record value) throws SQLException {
            Cast.renderCastIf(ctx.render(), c -> super.sqlBind0(ctx, value), c -> this.pgRenderRecordCast(ctx.render()), () -> REQUIRE_RECORD_CAST.contains((Object)ctx.dialect()));
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, Record value) throws SQLException {
            Cast.renderCastIf(ctx.render(), c -> {
                if (REQUIRE_RECORD_CAST.contains((Object)ctx.dialect())) {
                    ctx.render().visit(DSL.inline(PostgresUtils.toPGString(value)));
                } else {
                    ctx.render().sql("[UDT]");
                }
            }, c -> this.pgRenderRecordCast(ctx.render()), () -> REQUIRE_RECORD_CAST.contains((Object)ctx.dialect()));
        }

        @Override
        final void register0(BindingRegisterContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE) {
                ctx.statement().registerOutParameter(ctx.index(), 2002, Tools.getMappedUDTName(ctx, this.dataType.getType()));
            } else {
                super.register0(ctx);
            }
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Record value) throws SQLException {
            if (REQUIRE_RECORD_CAST.contains((Object)ctx.dialect()) && value != null) {
                ctx.statement().setString(ctx.index(), PostgresUtils.toPGString(value));
            } else {
                DefaultExecuteContext.localExecuteContext(ctx.executeContext(), () -> {
                    ctx.statement().setObject(ctx.index(), value);
                    return null;
                });
            }
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            if (ctx.family() == SQLDialect.ORACLE) {
                ctx.statement().setNull(ctx.index(), this.sqltype(ctx.statement(), ctx.configuration()), Tools.getMappedUDTName(ctx, this.dataType.getType()));
            } else {
                super.setNull0(ctx);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Record value) throws SQLException {
            if (!(value instanceof QualifiedRecord)) {
                throw new UnsupportedOperationException("Type " + String.valueOf(this.dataType) + " is not supported");
            }
            QualifiedRecord q = (QualifiedRecord)value;
            ctx.output().writeObject(q);
        }

        @Override
        final Record get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    return DefaultRecordBinding.pgNewRecord(ctx, this.dataType.getType(), (AbstractRow)this.dataType.getRow(), ctx.resultSet().getObject(ctx.index()));
                }
            }
            if (UDTRecord.class.isAssignableFrom(this.dataType.getType())) {
                return DefaultExecuteContext.localExecuteContext(ctx.executeContext(), () -> (Record)ctx.resultSet().getObject(ctx.index(), DefaultBinding.typeMap(this.dataType.getType(), ctx)));
            }
            return DefaultRecordBinding.readMultiset(ctx, this.dataType);
        }

        @Override
        final Record get0(BindingGetStatementContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    return DefaultRecordBinding.pgNewRecord(ctx, this.dataType.getType(), (AbstractRow)this.dataType.getRow(), ctx.statement().getObject(ctx.index()));
                }
            }
            return DefaultExecuteContext.localExecuteContext(ctx.executeContext(), () -> (Record)ctx.statement().getObject(ctx.index(), DefaultBinding.typeMap(this.dataType.getType(), ctx)));
        }

        @Override
        final Record get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return (Record)ctx.input().readObject();
        }

        static final <R extends Record> R readMultiset(BindingGetResultSetContext<?> ctx, DataType<R> type) throws SQLException {
            Result<R> result;
            AbstractRow row = (AbstractRow)type.getRow();
            if (row.size() == 1 && Tools.emulateMultiset(ctx.configuration()) != NestedCollectionEmulation.NATIVE) {
                result = new ResultImpl(ctx.configuration(), row);
                result.add(Tools.newRecord(true, type.getRecordType(), row, ctx.configuration()).operate(r -> {
                    DefaultBindingGetResultSetContext c = new DefaultBindingGetResultSetContext(ctx.executeContext(), ctx.resultSet(), ctx.index());
                    r.field(0).getBinding().get(c);
                    r.fromArray(c.value());
                    return r;
                }));
            } else {
                result = DefaultResultBinding.readMultiset(ctx, row, type.getType(), s2 -> s2 != null && (s2.startsWith("[") || s2.startsWith("{")) ? "[" + s2 + "]" : null, s2 -> s2 != null && s2.startsWith("<") ? "<result>" + s2 + "</result>" : null, s2 -> {
                    List<Struct> list;
                    if (s2 instanceof Struct) {
                        Struct x = (Struct)s2;
                        list = Arrays.asList(x);
                    } else {
                        list = null;
                    }
                    return list;
                });
            }
            return (R)(Tools.isEmpty(result) ? null : (Record)result.get(0));
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 2002;
        }

        final void pgRenderRecordCast(Context<?> ctx) {
            DataType dataType = this.dataType;
            if (dataType instanceof UDTDataType) {
                UDTDataType u = (UDTDataType)dataType;
                ctx.visit(u.udt);
            } else {
                dataType = this.dataType;
                if (dataType instanceof TableDataType) {
                    TableDataType t2 = (TableDataType)dataType;
                    ctx.visit(t2.table);
                } else if (this.dataType.isUDT()) {
                    ctx.visit(Tools.getRecordQualifier(this.dataType));
                } else {
                    ctx.visit(this.dataType.getQualifiedName());
                }
            }
        }

        private static final <T, U> U pgFromString(BindingScope ctx, Field<U> field2, String string) {
            ContextConverter<?, Object> converter = field2.getConverter();
            Class type = Reflect.wrapper(converter.fromType());
            if (string == null) {
                return null;
            }
            if (type != Blob.class) {
                if (type == Boolean.class) {
                    return (U)converter.from(Convert.convert((Object)string, Boolean.class), ctx.converterContext());
                }
                if (type == BigInteger.class) {
                    return (U)converter.from(new BigInteger(string), ctx.converterContext());
                }
                if (type == BigDecimal.class) {
                    return (U)converter.from(new BigDecimal(string), ctx.converterContext());
                }
                if (type == Byte.class) {
                    return (U)converter.from(Byte.valueOf(string), ctx.converterContext());
                }
                if (type == byte[].class) {
                    return (U)converter.from(PostgresUtils.toBytes(string), ctx.converterContext());
                }
                if (type != Clob.class) {
                    if (type == java.sql.Date.class) {
                        return (U)converter.from(java.sql.Date.valueOf(string), ctx.converterContext());
                    }
                    if (type == Double.class) {
                        return (U)converter.from(Double.valueOf(string), ctx.converterContext());
                    }
                    if (type == Float.class) {
                        return (U)converter.from(Float.valueOf(string), ctx.converterContext());
                    }
                    if (type == Geometry.class) {
                        return (U)converter.from(Geometry.valueOf(string), ctx.converterContext());
                    }
                    if (type == Geography.class) {
                        return (U)converter.from(Geography.valueOf(string), ctx.converterContext());
                    }
                    if (type == Integer.class) {
                        return (U)converter.from(Integer.valueOf(string), ctx.converterContext());
                    }
                    if (type == Long.class) {
                        return (U)converter.from(Long.valueOf(string), ctx.converterContext());
                    }
                    if (type == Short.class) {
                        return (U)converter.from(Short.valueOf(string), ctx.converterContext());
                    }
                    if (type == String.class) {
                        return (U)converter.from(string, ctx.converterContext());
                    }
                    if (type == Time.class) {
                        return (U)converter.from(Time.valueOf(string), ctx.converterContext());
                    }
                    if (type == Timestamp.class) {
                        return (U)converter.from(Timestamp.valueOf(Convert.patchIso8601Timestamp(string, false)), ctx.converterContext());
                    }
                    if (type == LocalTime.class) {
                        return (U)converter.from(LocalTime.parse(string), ctx.converterContext());
                    }
                    if (type == LocalDate.class) {
                        return (U)converter.from(LocalDate.parse(string), ctx.converterContext());
                    }
                    if (type == LocalDateTime.class) {
                        return (U)converter.from(LocalDateTime.parse(Convert.patchIso8601Timestamp(string, true)), ctx.converterContext());
                    }
                    if (type == OffsetTime.class) {
                        return (U)converter.from(OffsetDateTimeParser.offsetTime(string), ctx.converterContext());
                    }
                    if (type == OffsetDateTime.class) {
                        return (U)converter.from(OffsetDateTimeParser.offsetDateTime(string), ctx.converterContext());
                    }
                    if (type == Instant.class) {
                        return (U)converter.from(OffsetDateTimeParser.offsetDateTime(string).toInstant(), ctx.converterContext());
                    }
                    if (type == JSON.class) {
                        return (U)converter.from(JSON.json(string), ctx.converterContext());
                    }
                    if (type == JSONB.class) {
                        return (U)converter.from(JSONB.jsonb(string), ctx.converterContext());
                    }
                    if (type == UByte.class) {
                        return (U)converter.from(UByte.valueOf(string), ctx.converterContext());
                    }
                    if (type == UShort.class) {
                        return (U)converter.from(UShort.valueOf(string), ctx.converterContext());
                    }
                    if (type == UInteger.class) {
                        return (U)converter.from(UInteger.valueOf(string), ctx.converterContext());
                    }
                    if (type == ULong.class) {
                        return (U)converter.from(ULong.valueOf(string), ctx.converterContext());
                    }
                    if (type == UUID.class) {
                        return (U)converter.from(UUID.fromString(string), ctx.converterContext());
                    }
                    if (type == XML.class) {
                        return (U)converter.from(XML.xml(string), ctx.converterContext());
                    }
                    if (type == Year.class) {
                        return (U)converter.from(Year.parse(string), ctx.converterContext());
                    }
                    if (type == YearToMonth.class) {
                        return (U)converter.from(PostgresUtils.toYearToMonth(string), ctx.converterContext());
                    }
                    if (type == YearToSecond.class) {
                        return (U)converter.from(PostgresUtils.toYearToSecond(string), ctx.converterContext());
                    }
                    if (type == DayToSecond.class) {
                        return (U)converter.from(PostgresUtils.toDayToSecond(string), ctx.converterContext());
                    }
                    if (type.isArray()) {
                        return (U)converter.from(DefaultRecordBinding.pgNewArray(ctx, field2, type, string), ctx.converterContext());
                    }
                    if (!ArrayRecord.class.isAssignableFrom(type)) {
                        if (EnumType.class.isAssignableFrom(type)) {
                            return (U)converter.from(DefaultEnumTypeBinding.getEnumType(type, string), ctx.converterContext());
                        }
                        if (Result.class.isAssignableFrom(type)) {
                            if (string.startsWith("<")) {
                                return (U)converter.from(DefaultResultBinding.readMultisetXML(ctx, (AbstractRow)field2.getDataType().getRow(), field2.getDataType().getRecordType(), string), ctx.converterContext());
                            }
                            return (U)converter.from(DefaultResultBinding.readMultisetJSON(ctx, (AbstractRow)field2.getDataType().getRow(), field2.getDataType().getRecordType(), string), ctx.converterContext());
                        }
                        if (Record.class.isAssignableFrom(type) && (!InternalRecord.class.isAssignableFrom(type) || type == converter.fromType())) {
                            return (U)converter.from(DefaultRecordBinding.pgNewRecord(ctx, type, (AbstractRow)field2.getDataType().getRow(), string), ctx.converterContext());
                        }
                        if (type == Object.class) {
                            return (U)converter.from(string, ctx.converterContext());
                        }
                        if (type != Reflect.wrapper(converter.toType())) {
                            return (U)converter.from(DefaultRecordBinding.pgFromString(ctx, DSL.field("converted_field", ConvertedDataType.delegate(field2.getDataType())), string), ctx.converterContext());
                        }
                    }
                }
            }
            throw new UnsupportedOperationException("Class " + String.valueOf(type) + " is not supported");
        }

        static final Record pgNewRecord(BindingScope ctx, Class<?> type, AbstractRow<?> fields, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Record) {
                Record r2 = (Record)object;
                return r2;
            }
            String s2 = object.toString();
            List<String> values2 = PostgresUtils.toPGObject(s2);
            if (fields == null && Record.class.isAssignableFrom(type)) {
                fields = Tools.row0(Tools.fields(values2.size(), SQLDataType.VARCHAR));
            }
            return Tools.newRecord(true, type, fields).operate(r -> {
                Row row = r.fieldsRow();
                for (int i = 0; i < row.size(); ++i) {
                    DefaultRecordBinding.pgSetValue(ctx, r, row.field(i), (String)values2.get(i));
                }
                r.changed(false);
                return r;
            });
        }

        private static final <T> void pgSetValue(BindingScope ctx, Record record, Field<T> field2, String value) {
            record.set(field2, DefaultRecordBinding.pgFromString(ctx, field2, value));
        }

        private static final Object[] pgNewArray(BindingScope ctx, Field<?> field2, Class<?> type, String string) {
            if (string == null) {
                return null;
            }
            DataType t2 = field2.getDataType();
            try {
                return Tools.map(PostgresUtils.toPGArray(string), v -> DefaultRecordBinding.pgFromString(ctx, DSL.field("array_element", ConvertedDataType.delegate(t2).getArrayComponentDataType()), v), size -> (Object[])Array.newInstance(type.getComponentType(), size));
            }
            catch (Exception e) {
                if (type.getComponentType().getSimpleName().equals("UnknownType")) {
                    throw new DataTypeException("Error while creating array for UnknownType. Please provide an explicit Class<U> type to your converter, see https://github.com/jOOQ/jOOQ/issues/11823", e);
                }
                throw new DataTypeException("Error while creating array", e);
            }
        }
    }

    static final class DefaultResultBinding<U>
    extends InternalBinding<Result<?>, U> {
        DefaultResultBinding(DataType<Result<?>> dataType, Converter<Result<?>, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Result<?> value) throws SQLException {
            throw new UnsupportedOperationException("Cannot bind a value of type Result to a PreparedStatement");
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Result<?> value) throws SQLException {
            throw new UnsupportedOperationException("Cannot bind a value of type Result to a SQLOutput");
        }

        @Override
        final Result<?> get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            Field<?> field2 = Tools.uncoerce(ctx.field());
            if (field2.getDataType().isMultiset()) {
                return DefaultResultBinding.readMultiset(ctx, field2.getDataType());
            }
            return ctx.configuration().dsl().fetch(Convert.convert(ctx.resultSet().getObject(ctx.index()), ResultSet.class));
        }

        static final <R extends Record> Result<R> readMultiset(BindingGetResultSetContext<?> ctx, DataType<Result<R>> type) throws SQLException {
            return DefaultResultBinding.readMultiset(ctx, (AbstractRow)type.getRow(), type.getRecordType(), Function.identity(), Function.identity(), Function.identity());
        }

        static final <R extends Record> Result<R> readMultiset(BindingGetResultSetContext<?> ctx, AbstractRow<R> row, Class<R> recordType, Function<String, String> jsonStringPatch, Function<String, String> xmlStringPatch, Function<Object, List<Struct>> nativePatch) throws SQLException {
            NestedCollectionEmulation emulation = Tools.emulateMultiset(ctx.configuration());
            switch (emulation) {
                case JSON: 
                case JSONB: {
                    if (emulation == NestedCollectionEmulation.JSONB && DefaultJSONBBinding.EMULATE_AS_BLOB.contains((Object)ctx.dialect())) {
                        byte[] bytes = ctx.resultSet().getBytes(ctx.index());
                        return Tools.apply(jsonStringPatch.apply(bytes == null ? null : Source.of(bytes, ctx.configuration().charsetProvider().provide()).readString()), s2 -> DefaultResultBinding.readMultisetJSON(ctx, row, recordType, s2));
                    }
                    return Tools.apply(jsonStringPatch.apply(ctx.resultSet().getString(ctx.index())), s2 -> DefaultResultBinding.readMultisetJSON(ctx, row, recordType, s2));
                }
                case XML: {
                    return Tools.apply(xmlStringPatch.apply(ctx.resultSet().getString(ctx.index())), s2 -> DefaultResultBinding.readMultisetXML(ctx, row, recordType, s2));
                }
                case NATIVE: {
                    return Tools.apply(nativePatch.apply(ctx.resultSet().getObject(ctx.index())), l -> DefaultResultBinding.readMultisetList(ctx, row, recordType, l));
                }
            }
            throw new UnsupportedOperationException("Multiset emulation not yet supported: " + String.valueOf((Object)emulation));
        }

        @Pro
        static <R extends Record> Result<R> readMultisetList(Scope ctx, AbstractRow<R> row, Class<R> recordType, List<Struct> l) throws SQLException {
            return new ListHandler<R>(ctx.dsl(), row, recordType).read(l);
        }

        static final <R extends Record> Result<R> readMultisetXML(Scope ctx, AbstractRow<R> row, Class<R> recordType, String s2) {
            if (s2.startsWith("<")) {
                return new XMLHandler<R>(ctx.dsl(), row, recordType).read(s2);
            }
            return DefaultResultBinding.readMultisetScalar(ctx, row, recordType, s2);
        }

        static final <R extends Record> Result<R> readMultisetJSON(Scope ctx, AbstractRow<R> row, Class<R> recordType, String s2) {
            if (s2.startsWith("{") || s2.startsWith("[")) {
                return new JSONReader<R>(ctx.dsl(), row, recordType, true).read(new StringReader(DefaultJSONBinding.patchSnowflakeJSON(ctx, s2)), true);
            }
            return DefaultResultBinding.readMultisetScalar(ctx, row, recordType, s2);
        }

        static final <R extends Record> Result<R> readMultisetScalar(Scope ctx, AbstractRow<R> row, Class<R> recordType, String s2) {
            ResultImpl result = new ResultImpl(ctx.configuration(), (AbstractRow)row);
            result.add(Tools.newRecord(true, recordType, row, ctx.configuration()).operate(r -> {
                r.from(Arrays.asList(s2));
                return r;
            }));
            return result;
        }

        @Override
        final Result<?> get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return ctx.configuration().dsl().fetch(Convert.convert(ctx.statement().getObject(ctx.index()), ResultSet.class));
        }

        @Override
        final Result<?> get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            throw new UnsupportedOperationException("Cannot get a value of type Result from a SQLInput");
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            switch (configuration.family()) {
                case H2: 
                case ORACLE: {
                    return -10;
                }
            }
            return 1111;
        }
    }

    static final class DefaultOtherBinding<U>
    extends InternalBinding<Object, U> {
        DefaultOtherBinding(DataType<Object> dataType, Converter<Object, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Object value) throws SQLException {
            InternalBinding b = (InternalBinding)DefaultBinding.binding(DefaultDataType.getDataType(ctx.dialect(), value.getClass()));
            if (b instanceof DefaultOtherBinding) {
                ctx.statement().setObject(ctx.index(), value);
            } else {
                b.set0(ctx, b.dataType.convert(value));
            }
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case BIGQUERY: {
                    ctx.statement().setString(ctx.index(), null);
                    break;
                }
                case TERADATA: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: {
                    ctx.statement().setNull(ctx.index(), 1111);
                    break;
                }
                case SYBASE: {
                    ctx.statement().setNull(ctx.index(), 12);
                    break;
                }
                default: {
                    ctx.statement().setObject(ctx.index(), null);
                }
            }
        }

        @Override
        void sqlInline0(BindingSQLContext<U> ctx, Object value) throws SQLException {
            Binding<Object, Object> b = DefaultBinding.binding(DefaultDataType.getDataType(SQLDialect.DEFAULT, value.getClass(), SQLDataType.OTHER));
            if (b instanceof DefaultOtherBinding) {
                super.sqlInline0(ctx, value);
            } else if (b instanceof InternalBinding) {
                InternalBinding i = (InternalBinding)b;
                i.sqlInline0(ctx, value);
            } else {
                super.sqlInline0(ctx, value);
            }
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Object value) throws SQLException {
            throw new DataTypeException("Type " + String.valueOf(this.dataType) + " is not supported");
        }

        @Override
        final Object get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return DefaultOtherBinding.unlob(ctx.resultSet().getObject(ctx.index()));
        }

        @Override
        final Object get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return DefaultOtherBinding.unlob(ctx.statement().getObject(ctx.index()));
        }

        @Override
        final Object get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return DefaultOtherBinding.unlob(ctx.input().readObject());
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            return 1111;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private static final Object unlob(Object object) throws SQLException {
            if (object instanceof Blob) {
                Blob blob = (Blob)object;
                try {
                    byte[] byArray = blob.getBytes(1L, Tools.asInt(blob.length()));
                    return byArray;
                }
                finally {
                    JDBCUtils.safeFree(blob);
                }
            }
            if (object instanceof Clob) {
                Clob clob = (Clob)object;
                try {
                    String string = clob.getSubString(1L, Tools.asInt(clob.length()));
                    return string;
                }
                finally {
                    JDBCUtils.safeFree(clob);
                }
            }
            return object;
        }
    }

    public static class SdoPointTypeRecord
    extends UDTRecordImpl<SdoPointTypeRecord> {
        public SdoPointTypeRecord() {
            super(SdoPointType.SDO_POINT_TYPE);
        }
    }

    static class SdoPointType
    extends UDTImpl<SdoPointTypeRecord>
    implements Package {
        static final SdoPointType SDO_POINT_TYPE = new SdoPointType();
        static final UDTField<SdoPointTypeRecord, BigDecimal> X = SdoPointType.createField(DSL.name("X"), SQLDataType.NUMERIC, SDO_POINT_TYPE, "");
        static final UDTField<SdoPointTypeRecord, BigDecimal> Y = SdoPointType.createField(DSL.name("Y"), SQLDataType.NUMERIC, SDO_POINT_TYPE, "");
        static final UDTField<SdoPointTypeRecord, BigDecimal> Z = SdoPointType.createField(DSL.name("Z"), SQLDataType.NUMERIC, SDO_POINT_TYPE, "");

        @Override
        public Class<SdoPointTypeRecord> getRecordType() {
            return SdoPointTypeRecord.class;
        }

        private SdoPointType() {
            super("SDO_POINT_TYPE", null, null, false);
        }

        @Override
        public Schema getSchema() {
            return Mdsys.MDSYS != null ? Mdsys.MDSYS : new SchemaImpl(DSL.name("MDSYS"));
        }
    }

    public static class SdoGeometryRecord
    extends UDTRecordImpl<SdoGeometryRecord> {
        public SdoGeometryRecord() {
            super(SdoGeometry.SDO_GEOMETRY);
        }
    }

    static class SdoGeometry
    extends UDTImpl<SdoGeometryRecord>
    implements Package {
        private static final long serialVersionUID = 1824411362L;
        static final SdoGeometry SDO_GEOMETRY = new SdoGeometry();
        static final UDTField<SdoGeometryRecord, BigDecimal> SDO_GTYPE = SdoGeometry.createField(DSL.name("SDO_GTYPE"), SQLDataType.NUMERIC, SDO_GEOMETRY, "");
        static final UDTField<SdoGeometryRecord, BigDecimal> SDO_SRID = SdoGeometry.createField(DSL.name("SDO_SRID"), SQLDataType.NUMERIC, SDO_GEOMETRY, "");
        static final UDTField<SdoGeometryRecord, SdoPointTypeRecord> SDO_POINT = SdoGeometry.createField(DSL.name("SDO_POINT"), SdoPointType.SDO_POINT_TYPE.getDataType(), SDO_GEOMETRY, "");
        static final UDTField<SdoGeometryRecord, SdoElemInfoArray> SDO_ELEM_INFO = SdoGeometry.createField(DSL.name("SDO_ELEM_INFO"), SQLDataType.NUMERIC.asArrayDataType(SdoElemInfoArray.class), SDO_GEOMETRY, "");
        static final UDTField<SdoGeometryRecord, SdoOrdinateArray> SDO_ORDINATES = SdoGeometry.createField(DSL.name("SDO_ORDINATES"), SQLDataType.NUMERIC.asArrayDataType(SdoOrdinateArray.class), SDO_GEOMETRY, "");

        @Override
        public Class<SdoGeometryRecord> getRecordType() {
            return SdoGeometryRecord.class;
        }

        private SdoGeometry() {
            super("SDO_GEOMETRY", null, null, false);
        }

        @Override
        public Schema getSchema() {
            return Mdsys.MDSYS != null ? Mdsys.MDSYS : new SchemaImpl(DSL.name("MDSYS"));
        }
    }

    public static class SdoOrdinateArray
    extends ArrayRecordImpl<BigDecimal> {
        public SdoOrdinateArray() {
            super(Mdsys.MDSYS, "SDO_ORDINATE_ARRAY", SQLDataType.NUMERIC);
        }
    }

    public static class SdoElemInfoArray
    extends ArrayRecordImpl<BigDecimal> {
        public SdoElemInfoArray() {
            super(Mdsys.MDSYS, "SDO_ELEM_INFO_ARRAY", SQLDataType.NUMERIC);
        }
    }

    static class Mdsys
    extends SchemaImpl {
        public static final Mdsys MDSYS = new Mdsys();

        private Mdsys() {
            super("MDSYS", null);
        }

        @Override
        public final List<UDT<?>> getUDTs() {
            return Arrays.asList(SdoGeometry.SDO_GEOMETRY);
        }
    }

    @Pro
    static abstract class AbstractSpatialBinding<T extends Spatial, U>
    extends InternalBinding<T, U> {
        static final Map<String, Class<?>> SDO_TYPE_MAP = DefaultBinding.typeMap(SdoGeometryRecord.class, Tools.CTX.get());
        static final Pattern P_HEX = Pattern.compile("^[0-9A-Fa-f]*$");
        final ContextConverter<T, byte[]> wkbConverter;
        final DefaultBytesBinding<U> wkb;

        AbstractSpatialBinding(DataType<T> dataType, Converter<T, U> converter) {
            super(dataType, converter);
            this.wkbConverter = ContextConverter.ofNullable(dataType.getType(), byte[].class, (t2, x) -> Tools.convertHexToBytes(t2.data()), (u, x) -> this.construct(Tools.convertBytesToHex(u)));
            this.wkb = new DefaultBytesBinding(SQLDataType.VARBINARY, this.wkbConverter.inverse().andThen(converter));
        }

        abstract T construct(String var1);

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            switch (ctx.family()) {
                case ORACLE: {
                    ctx.statement().setNull(ctx.index(), this.sqltype(ctx.statement(), ctx.configuration()), "MDSYS.SDO_GEOMETRY");
                    break;
                }
                default: {
                    ctx.statement().setString(ctx.index(), null);
                }
            }
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) throws SQLException {
            switch (configuration.family()) {
                case ORACLE: {
                    return 2002;
                }
            }
            return 12;
        }
    }

    static final class CommercialOnlyBinding<U>
    extends InternalBinding<Object, U> {
        CommercialOnlyBinding(DataType<Object> dataType, Converter<Object, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, Object value) throws SQLException {
            ctx.configuration().requireCommercial(() -> "The out of the box binding for " + this.dataType.getName() + " is available in the commercial jOOQ distribution only. Alternatively, you can implement your own custom binding.");
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, Object value) throws SQLException {
            ctx.configuration().requireCommercial(() -> "The out of the box binding for " + this.dataType.getName() + " is available in the commercial jOOQ distribution only. Alternatively, you can implement your own custom binding.");
        }

        @Override
        final Object get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            ctx.configuration().requireCommercial(() -> "The out of the box binding for " + this.dataType.getName() + " is available in the commercial jOOQ distribution only. Alternatively, you can implement your own custom binding.");
            return null;
        }

        @Override
        final Object get0(BindingGetStatementContext<U> ctx) throws SQLException {
            ctx.configuration().requireCommercial(() -> "The out of the box binding for " + this.dataType.getName() + " is available in the commercial jOOQ distribution only. Alternatively, you can implement your own custom binding.");
            return null;
        }

        @Override
        final Object get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            ctx.configuration().requireCommercial(() -> "The out of the box binding for " + this.dataType.getName() + " is available in the commercial jOOQ distribution only. Alternatively, you can implement your own custom binding.");
            return null;
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) throws SQLException {
            return 1111;
        }
    }

    static final class OffsetDateTimeParser {
        OffsetDateTimeParser() {
        }

        static final OffsetTime offsetTime(String string) {
            if (string == null) {
                return null;
            }
            int[] position = new int[]{0};
            return OffsetTime.of(OffsetDateTimeParser.parseLocalTime(string, position), OffsetDateTimeParser.parseOffset(string, position));
        }

        static final OffsetDateTime offsetDateTime(String string) {
            if (string == null) {
                return null;
            }
            int[] position = new int[]{0};
            LocalDate d = OffsetDateTimeParser.parseLocalDate(string, position);
            OffsetDateTimeParser.parseAnyChar(string, position, " T");
            LocalTime t2 = OffsetDateTimeParser.parseLocalTime(string, position);
            ZoneOffset offset = OffsetDateTimeParser.parseOffset(string, position);
            return OffsetDateTimeParser.parseBCIf(string, position) ? OffsetDateTime.of(d.withYear(1 - d.getYear()), t2, offset) : OffsetDateTime.of(d, t2, offset);
        }

        static final LocalDate parseLocalDate(String string, int[] position) {
            int year = OffsetDateTimeParser.parseInt(string, position, 10);
            OffsetDateTimeParser.parseChar(string, position, '-');
            int month = OffsetDateTimeParser.parseInt(string, position, 2);
            OffsetDateTimeParser.parseChar(string, position, '-');
            int day = OffsetDateTimeParser.parseInt(string, position, 2);
            return LocalDate.of(year, month, day);
        }

        static final LocalTime parseLocalTime(String string, int[] position) {
            int hour = OffsetDateTimeParser.parseInt(string, position, 2);
            if (hour == 24) {
                hour %= 24;
            }
            OffsetDateTimeParser.parseChar(string, position, ':');
            int minute = OffsetDateTimeParser.parseInt(string, position, 2);
            int second = 0;
            int nano = 0;
            if (OffsetDateTimeParser.parseCharIf(string, position, ':')) {
                second = OffsetDateTimeParser.parseInt(string, position, 2);
                if (OffsetDateTimeParser.parseCharIf(string, position, '.')) {
                    nano = OffsetDateTimeParser.parseInt(string, position, 9, true);
                }
            }
            return LocalTime.of(hour, minute, second, nano);
        }

        private static final ZoneOffset parseOffset(String string, int[] position) {
            int offsetHours = 0;
            int offsetMinutes = 0;
            int offsetSeconds = 0;
            if (!OffsetDateTimeParser.parseCharIf(string, position, 'Z')) {
                boolean plus2;
                while (OffsetDateTimeParser.parseCharIf(string, position, ' ')) {
                }
                boolean minus2 = OffsetDateTimeParser.parseCharIf(string, position, '-');
                boolean bl = plus2 = !minus2 && OffsetDateTimeParser.parseCharIf(string, position, '+');
                if (minus2 || plus2) {
                    offsetHours = OffsetDateTimeParser.parseInt(string, position, 1);
                    if (Character.isDigit(string.charAt(position[0]))) {
                        offsetHours = offsetHours * 10 + OffsetDateTimeParser.parseInt(string, position, 1);
                    }
                    if (OffsetDateTimeParser.parseCharIf(string, position, ':')) {
                        offsetMinutes = OffsetDateTimeParser.parseInt(string, position, 2);
                    }
                    if (OffsetDateTimeParser.parseCharIf(string, position, ':')) {
                        offsetSeconds = OffsetDateTimeParser.parseInt(string, position, 2);
                    }
                    if (minus2) {
                        offsetHours = -offsetHours;
                        offsetMinutes = -offsetMinutes;
                        offsetSeconds = -offsetSeconds;
                    }
                }
            }
            return ZoneOffset.ofHoursMinutesSeconds(offsetHours, offsetMinutes, offsetSeconds);
        }

        private static final void parseAnyChar(String string, int[] position, String expected) {
            for (int i = 0; i < expected.length(); ++i) {
                if (string.charAt(position[0]) != expected.charAt(i)) continue;
                position[0] = position[0] + 1;
                return;
            }
            throw new IllegalArgumentException("Expected any of \"" + expected + "\" at position " + position[0] + " in " + string);
        }

        private static final boolean parseBCIf(String string, int[] position) {
            return OffsetDateTimeParser.parseCharIf(string, position, ' ') && OffsetDateTimeParser.parseCharIf(string, position, 'B') && OffsetDateTimeParser.parseCharIf(string, position, 'C');
        }

        private static final boolean parseCharIf(String string, int[] position, char expected) {
            boolean result;
            boolean bl = result = string.length() > position[0] && string.charAt(position[0]) == expected;
            if (result) {
                position[0] = position[0] + 1;
            }
            return result;
        }

        private static final void parseChar(String string, int[] position, char expected) {
            if (!OffsetDateTimeParser.parseCharIf(string, position, expected)) {
                throw new IllegalArgumentException("Expected '" + expected + "' at position " + position[0] + " in " + string);
            }
        }

        private static final int parseInt(String string, int[] position, int maxLength) {
            return OffsetDateTimeParser.parseInt(string, position, maxLength, false);
        }

        private static final int parseInt(String string, int[] position, int maxLength, boolean rightPad) {
            int digit;
            int length;
            int result = 0;
            int pos = position[0];
            for (length = 0; length < maxLength && pos + length < string.length() && (digit = string.charAt(pos + length) - 48) >= 0 && digit < 10; ++length) {
                result = result * 10 + digit;
            }
            if (rightPad && length < maxLength && result > 0) {
                for (int i = length; i < maxLength; ++i) {
                    result *= 10;
                }
            }
            position[0] = pos + length;
            return result;
        }
    }

    @Pro
    static abstract class AbstractArrayOrAssociativeArrayRecordBinding<A extends ArrayOrAssociativeArrayRecord<?>, U>
    extends InternalBinding<A, U> {
        AbstractArrayOrAssociativeArrayRecordBinding(DataType<A> dataType, Converter<A, U> converter) {
            super(dataType, converter);
        }

        @Override
        final void sqlInline0(BindingSQLContext<U> ctx, A value) {
            ctx.render().sql(AbstractArrayOrAssociativeArrayRecordBinding.escape(value, ctx.render()), true);
        }

        @Override
        final void register0(BindingRegisterContext<U> ctx) throws SQLException {
            ctx.statement().registerOutParameter(ctx.index(), 2003, Tools.getMappedArrayNameString(ctx, this.dataType.getType()));
        }

        @Override
        final void set0(BindingSetStatementContext<U> ctx, A value) throws SQLException {
            switch (ctx.family()) {
                case SQLSERVER: {
                    ctx.statement().unwrap(ISQLServerPreparedStatement.class).setStructured(ctx.index(), Tools.getMappedArrayNameString(ctx, value), this.createSQLServerTable(ctx.configuration(), value));
                    break;
                }
                default: {
                    DefaultExecuteContext.localExecuteContext(ctx.executeContext(), () -> ctx.statement().setArray(ctx.index(), ctx.autoFree(this.createOracleARRAY(ctx, value))));
                }
            }
        }

        @Override
        final void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            ctx.statement().setNull(ctx.index(), this.sqltype(ctx.statement(), ctx.configuration()), Tools.getMappedArrayNameString(ctx, this.dataType.getType()));
        }

        @Override
        final void set0(BindingSetSQLOutputContext<U> ctx, A value) throws SQLException {
            DefaultExecuteContext.localExecuteContext(ctx.executeContext(), () -> ctx.output().writeArray(ctx.autoFree(this.createOracleARRAY(ctx, value))));
        }

        @Override
        final A get0(BindingGetResultSetContext<U> ctx) throws SQLException {
            return (A)this.getArrayRecord(ctx, ctx.resultSet().getArray(ctx.index()), this.dataType.getType());
        }

        @Override
        final A get0(BindingGetStatementContext<U> ctx) throws SQLException {
            return (A)this.getArrayRecord(ctx, ctx.statement().getArray(ctx.index()), this.dataType.getType());
        }

        @Override
        final A get0(BindingGetSQLInputContext<U> ctx) throws SQLException {
            return (A)this.getArrayRecord(ctx, ctx.input().readArray(), this.dataType.getType());
        }

        @Override
        final int sqltype(Statement statement, Configuration configuration) {
            switch (configuration.family()) {
                case SQLSERVER: {
                    return -153;
                }
            }
            return 2003;
        }

        abstract A set(ExecuteScope var1, A var2, java.sql.Array var3) throws SQLException;

        private final A getArrayRecord(ExecuteScope ctx, java.sql.Array array, Class<? extends A> type) throws SQLException {
            if (array == null) {
                return null;
            }
            return (A)DefaultExecuteContext.localExecuteContext(ctx.executeContext(), () -> this.set(ctx, Tools.newArrayRecord(type), array));
        }

        private final java.sql.Array createOracleARRAY(BindingScope scope, A array) throws SQLException {
            return ((OracleConnection)DefaultExecuteContext.localTargetConnection(scope)).createARRAY(Tools.getMappedArrayNameString((Scope)scope, array), (Object)AbstractArrayOrAssociativeArrayRecordBinding.unnest(scope, array));
        }

        private final ResultSet createSQLServerTable(Configuration configuration, A array) {
            UDTRecord udt = (UDTRecord)Tools.newRecord(false, array.getDataType().getType()).operate(null);
            Result<Record> r = DSL.using(configuration).newResult(udt.getUDT().fields());
            r.addAll((List)array);
            return new MockResultSet(r);
        }

        private static final Object[] unnest(BindingScope scope, ArrayOrAssociativeArrayRecord<?> array) throws SQLException {
            if (array == null) {
                return null;
            }
            Object[] result = array.toList().toArray();
            if (result != null) {
                for (int i = 0; i < result.length; ++i) {
                    Object object = result[i];
                    if (object instanceof ArrayOrAssociativeArrayRecord) {
                        ArrayOrAssociativeArrayRecord r = (ArrayOrAssociativeArrayRecord)object;
                        result[i] = AbstractArrayOrAssociativeArrayRecordBinding.unnest(scope, r);
                        continue;
                    }
                    result[i] = AbstractArrayOrAssociativeArrayRecordBinding.fix6967(scope, array.getDataType().getConverter().to(result[i], scope.converterContext()));
                }
            }
            return result;
        }

        private static final Object fix6967(Scope scope, Object object) throws SQLException {
            if (object instanceof LocalDate) {
                LocalDate ld = (LocalDate)object;
                return java.sql.Date.valueOf(ld);
            }
            if (object instanceof LocalDateTime) {
                LocalDateTime ldt = (LocalDateTime)object;
                return Timestamp.valueOf(ldt);
            }
            if (object instanceof LocalTime) {
                LocalTime lt = (LocalTime)object;
                return Time.valueOf(lt);
            }
            if (object instanceof OffsetDateTime) {
                OffsetDateTime odt = (OffsetDateTime)object;
                return DefaultOffsetDateTimeBinding.oracleTSTZ(DefaultExecuteContext.localTargetConnection(scope), odt);
            }
            if (object instanceof Instant) {
                Instant i = (Instant)object;
                return DefaultOffsetDateTimeBinding.oracleTSTZ(DefaultExecuteContext.localTargetConnection(scope), i);
            }
            return object;
        }
    }

    static abstract class InternalBinding<T, U>
    implements Binding<T, U> {
        static final Set<SQLDialect> NEEDS_PRECISION_SCALE_ON_BIGDECIMAL = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2_2_0_202, SQLDialect.HSQLDB, SQLDialect.REDSHIFT, SQLDialect.TERADATA);
        static final Set<SQLDialect> REQUIRES_JSON_CAST = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
        static final Set<SQLDialect> NO_SUPPORT_ENUM_CAST = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
        static final Set<SQLDialect> NO_SUPPORT_NVARCHAR = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.INFORMIX, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB);
        @Pro
        static final Set<SQLDialect> NO_SUPPORT_REPEATED_ESCAPING = SQLDialect.supportedBy(SQLDialect.BIGQUERY);
        final DataType<T> dataType;
        final ContextConverter<T, U> converter;
        final boolean attachable;

        InternalBinding(DataType<T> dataType, Converter<T, U> converter) {
            this.dataType = dataType;
            this.converter = ContextConverter.scoped(converter);
            this.attachable = Attachable.class.isAssignableFrom(converter.toType()) || !Modifier.isFinal(converter.toType().getModifiers());
        }

        @Override
        public final ContextConverter<T, U> converter() {
            return this.converter;
        }

        private final boolean shouldCast(BindingSQLContext<U> ctx, T converted) {
            if (ctx.render().paramType() == ParamType.INLINED) {
                if (converted == null) {
                    switch (ctx.family()) {
                        case DERBY: {
                            return true;
                        }
                    }
                }
            } else if (!(converted instanceof EnumType)) {
                switch (ctx.family()) {
                    case DERBY: 
                    case DB2: 
                    case INFORMIX: 
                    case FIREBIRD: 
                    case H2: 
                    case HSQLDB: 
                    case IGNITE: 
                    case CUBRID: 
                    case AURORA_POSTGRES: 
                    case BIGQUERY: 
                    case COCKROACHDB: 
                    case EXASOL: 
                    case REDSHIFT: 
                    case SYBASE: 
                    case TERADATA: 
                    case VERTICA: 
                    case POSTGRES: 
                    case YUGABYTEDB: {
                        return true;
                    }
                }
            }
            if (this.dataType.isInterval()) {
                switch (ctx.family()) {
                    case H2: 
                    case HSQLDB: 
                    case AURORA_POSTGRES: 
                    case COCKROACHDB: 
                    case REDSHIFT: 
                    case POSTGRES: 
                    case YUGABYTEDB: 
                    case ORACLE: {
                        return true;
                    }
                }
            }
            if (this.dataType.isJSON() || this.dataType.isXML()) {
                switch (ctx.family()) {
                    case AURORA_POSTGRES: 
                    case COCKROACHDB: 
                    case REDSHIFT: 
                    case POSTGRES: 
                    case YUGABYTEDB: 
                    case SNOWFLAKE: {
                        return true;
                    }
                }
            }
            if (this.dataType.isSpatial()) {
                switch (ctx.family()) {
                    case AURORA_POSTGRES: 
                    case COCKROACHDB: 
                    case REDSHIFT: 
                    case POSTGRES: 
                    case YUGABYTEDB: {
                        return true;
                    }
                }
            }
            if (this.dataType.isTime()) {
                switch (ctx.family()) {
                    case SQLDATAWAREHOUSE: 
                    case SQLSERVER: {
                        return true;
                    }
                }
            }
            return false;
        }

        private final void sqlCast(BindingSQLContext<U> ctx, T converted) throws SQLException {
            DataType<T> sqlDataType = this.dataType.getSQLDataType();
            SQLDialect family = ctx.family();
            if (converted != null && this.dataType.getType() == BigDecimal.class && NEEDS_PRECISION_SCALE_ON_BIGDECIMAL.contains((Object)ctx.dialect())) {
                int precision;
                int scale = ((BigDecimal)converted).scale();
                if (scale >= (precision = ((BigDecimal)converted).precision())) {
                    precision = scale + 1;
                }
                this.sqlCast(ctx, converted, this.dataType, null, precision, scale);
            } else if (SQLDataType.ROWID == sqlDataType) {
                this.sql(ctx, converted);
            } else if (SQLDataType.OTHER == sqlDataType) {
                if (converted != null) {
                    this.sqlCast(ctx, converted, DefaultDataType.getDataType(family, converted.getClass()), null, null, null);
                } else {
                    ctx.render().sql(ctx.variable());
                }
            } else if (!(!REQUIRES_JSON_CAST.contains((Object)ctx.dialect()) || sqlDataType != null && (sqlDataType.isTemporal() || sqlDataType == SQLDataType.UUID || sqlDataType.isXML() || sqlDataType.isSpatial() || sqlDataType.isJSON()))) {
                this.sql(ctx, converted);
            } else if (ctx.dialect().precedes(SQLDialect.FIREBIRD_2_5) && (sqlDataType == SQLDataType.VARCHAR || sqlDataType == SQLDataType.CHAR)) {
                if (this.dataType.lengthDefined()) {
                    this.sqlCast(ctx, converted, this.dataType, this.dataType.length(), null, null);
                } else {
                    this.sqlCast(ctx, converted, this.dataType, InternalBinding.getValueLength((String)converted), null, null);
                }
            } else if (SQLDialect.SYBASE == family && sqlDataType == SQLDataType.LONGVARCHAR) {
                this.sqlCast(ctx, converted, this.dataType, null, null, null);
            } else if (!NO_SUPPORT_ENUM_CAST.contains((Object)ctx.dialect()) && this.dataType.isEnum()) {
                this.sqlCast(ctx, converted, Tools.emulateEnumType(this.dataType), this.dataType.lengthDefined() ? Integer.valueOf(this.dataType.length()) : null, this.dataType.precisionDefined() ? Integer.valueOf(this.dataType.precision()) : null, this.dataType.scaleDefined() ? Integer.valueOf(this.dataType.scale()) : null);
            } else {
                this.sqlCast(ctx, converted, this.dataType, this.dataType.lengthDefined() ? Integer.valueOf(this.dataType.length()) : null, this.dataType.precisionDefined() ? Integer.valueOf(this.dataType.precision()) : null, this.dataType.scaleDefined() ? Integer.valueOf(this.dataType.scale()) : null);
            }
        }

        private static final int getValueLength(String string) {
            if (string == null) {
                return 1;
            }
            int length = string.length();
            for (int i = 0; i < length; ++i) {
                if (string.charAt(i) <= '\u007f') continue;
                return Math.min(32672, 4 * length);
            }
            return Math.min(32672, length);
        }

        private final void sqlCast(BindingSQLContext<U> ctx, T converted, DataType<?> t2, Integer length, Integer precision, Integer scale) throws SQLException {
            switch (ctx.family()) {
                case SNOWFLAKE: {
                    if (t2.isJSON()) {
                        ((RenderContext)ctx.render().visit(Names.N_PARSE_JSON)).sql('(');
                        this.sql(ctx, converted);
                        ctx.render().sql(')');
                        break;
                    }
                    this.sqlCast0(ctx, converted, t2, length, precision, scale);
                    break;
                }
                default: {
                    this.sqlCast0(ctx, converted, t2, length, precision, scale);
                }
            }
        }

        private final void sqlCast0(BindingSQLContext<U> ctx, T converted, DataType<?> t2, Integer length, Integer precision, Integer scale) throws SQLException {
            ((RenderContext)ctx.render().visit(Keywords.K_CAST)).sql('(');
            this.sql(ctx, converted);
            ((RenderContext)((RenderContext)((RenderContext)((RenderContext)ctx.render().sql(' ')).visit(Keywords.K_AS)).sql(' ')).sql(DefaultDataType.set(t2, length, precision, scale).getCastTypeName(ctx.configuration()))).sql(')');
        }

        @Override
        public final void sql(BindingSQLContext<U> ctx) throws SQLException {
            Object converted = this.converter().to(ctx.value(), ctx.converterContext());
            switch (ctx.render().castMode()) {
                case NEVER: {
                    this.sql(ctx, converted);
                    return;
                }
                case ALWAYS: {
                    this.sqlCast(ctx, converted);
                    return;
                }
            }
            if (this.shouldCast(ctx, converted)) {
                this.sqlCast(ctx, converted);
            } else {
                this.sql(ctx, converted);
            }
        }

        private final void sql(BindingSQLContext<U> ctx, T value) throws SQLException {
            if (ctx.render().paramType() == ParamType.INLINED) {
                if (value == null) {
                    ctx.render().visit(Keywords.K_NULL);
                } else {
                    this.sqlInline0(ctx, value);
                }
            } else {
                this.sqlBind0(ctx, value);
            }
        }

        static final String escape(Object val, Context<?> ctx) {
            String result = val.toString();
            if (Tools.needsBackslashEscaping(ctx.configuration())) {
                result = StringUtils.replace(result, "\\", "\\\\");
            }
            if (NO_SUPPORT_REPEATED_ESCAPING.contains((Object)ctx.dialect())) {
                return StringUtils.replace(result, "'", "\\'");
            }
            return StringUtils.replace(result, "'", "''");
        }

        @Override
        public final void register(BindingRegisterContext<U> ctx) throws SQLException {
            if (!Boolean.FALSE.equals(ctx.settings().isExecuteLogging()) && log.isTraceEnabled()) {
                log.trace((Object)("Registering variable " + ctx.index()), String.valueOf(this.dataType));
            }
            this.register0(ctx);
        }

        @Override
        public final void set(BindingSetStatementContext<U> ctx) throws SQLException {
            Object value = this.converter().to(ctx.value(), ctx.converterContext());
            if (!Boolean.FALSE.equals(ctx.settings().isExecuteLogging()) && log.isTraceEnabled()) {
                if (value != null && value.getClass().isArray() && value.getClass() != byte[].class) {
                    log.trace((Object)("Binding variable " + ctx.index()), String.valueOf(Arrays.asList((Object[])value)) + " (" + String.valueOf(this.dataType) + ")");
                } else {
                    log.trace((Object)("Binding variable " + ctx.index()), String.valueOf(value) + " (" + String.valueOf(this.dataType) + ")");
                }
            }
            if (value == null) {
                this.setNull0(ctx);
            } else {
                this.set0(ctx, value);
            }
        }

        @Override
        public final void set(BindingSetSQLOutputContext<U> ctx) throws SQLException {
            Object value = this.converter().to(ctx.value(), ctx.converterContext());
            if (value == null) {
                ctx.output().writeObject(null);
            } else {
                this.set0(ctx, value);
            }
        }

        @Override
        public final void get(BindingGetResultSetContext<U> ctx) throws SQLException {
            Object value = this.converter().from(this.get0(ctx), ctx.converterContext());
            if (this.attachable) {
                value = InternalBinding.attach(value, ctx.configuration());
            }
            ctx.value(value);
        }

        @Override
        public final void get(BindingGetStatementContext<U> ctx) throws SQLException {
            Object value = this.converter().from(this.get0(ctx), ctx.converterContext());
            if (this.attachable) {
                value = InternalBinding.attach(value, ctx.configuration());
            }
            ctx.value(value);
        }

        @Override
        public final void get(BindingGetSQLInputContext<U> ctx) throws SQLException {
            Object value = this.converter().from(this.get0(ctx), ctx.converterContext());
            if (this.attachable) {
                value = InternalBinding.attach(value, ctx.configuration());
            }
            ctx.value(value);
        }

        private static final <U> U attach(U value, Configuration configuration) {
            if (value instanceof Attachable && Tools.attachRecords(configuration)) {
                ((Attachable)value).attach(configuration);
            }
            return value;
        }

        void setNull0(BindingSetStatementContext<U> ctx) throws SQLException {
            PreparedStatement preparedStatement = ctx.statement();
            if (preparedStatement instanceof R2DBC.R2DBCPreparedStatement) {
                R2DBC.R2DBCPreparedStatement s2 = (R2DBC.R2DBCPreparedStatement)preparedStatement;
                s2.setNull(ctx.index(), this.dataType);
            } else {
                ctx.statement().setNull(ctx.index(), this.sqltype(ctx.statement(), ctx.configuration()));
            }
        }

        void register0(BindingRegisterContext<U> ctx) throws SQLException {
            ctx.statement().registerOutParameter(ctx.index(), this.sqltype(ctx.statement(), ctx.configuration()));
        }

        void sqlInline0(BindingSQLContext<U> ctx, T value) throws SQLException {
            ((RenderContext)((RenderContext)ctx.render().sql('\'')).sql(InternalBinding.escape(value, ctx.render()), true)).sql('\'');
        }

        void sqlBind0(BindingSQLContext<U> ctx, T value) throws SQLException {
            ctx.render().sql(ctx.variable());
        }

        abstract void set0(BindingSetStatementContext<U> var1, T var2) throws SQLException;

        abstract void set0(BindingSetSQLOutputContext<U> var1, T var2) throws SQLException;

        abstract T get0(BindingGetResultSetContext<U> var1) throws SQLException;

        abstract T get0(BindingGetStatementContext<U> var1) throws SQLException;

        abstract T get0(BindingGetSQLInputContext<U> var1) throws SQLException;

        abstract int sqltype(Statement var1, Configuration var2) throws SQLException;

        public String toString() {
            return "AbstractBinding [type=" + String.valueOf(this.dataType) + ", converter=" + String.valueOf(this.converter) + "]";
        }
    }
}

