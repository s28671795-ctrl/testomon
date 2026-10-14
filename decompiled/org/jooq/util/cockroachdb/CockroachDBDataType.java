/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.cockroachdb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.Year;
import java.util.UUID;
import org.jooq.DataType;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.SQLDialect;
import org.jooq.impl.BuiltInDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.YearToMonth;
import org.jooq.types.YearToSecond;

@Deprecated(forRemoval=true, since="3.11")
public class CockroachDBDataType {
    private static final SQLDialect FAMILY = SQLDialect.COCKROACHDB;
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Short> INT2 = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "int2");
    public static final DataType<Integer> INT4 = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int4");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Long> INT8 = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "int8");
    public static final DataType<Long> INT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "int");
    public static final DataType<Long> INTEGER = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "integer");
    public static final DataType<Double> DOUBLEPRECISION = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double precision");
    public static final DataType<Double> FLOAT8 = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float8");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<Float> FLOAT4 = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "float4");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "boolean");
    public static final DataType<Boolean> BOOL = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "bool");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric(p, s)");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal(p, s)");
    public static final DataType<BigDecimal> DEC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "dec(p, s)");
    public static final DataType<String> STRING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "string(l)");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar(l)");
    public static final DataType<String> CHARVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "charvarying(l)");
    public static final DataType<String> CHARACTERVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "character varying(l)");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char(l)");
    public static final DataType<String> CHARACTER = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "character(l)");
    public static final DataType<String> TEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "text");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Time> TIMEWITHOUTTIMEZONE = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time without time zone");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp");
    public static final DataType<Timestamp> TIMESTAMPWITHOUTTIMEZONE = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp without time zone");
    public static final DataType<OffsetDateTime> TIMESTAMPTZ = new BuiltInDataType<OffsetDateTime>(FAMILY, SQLDataType.TIMESTAMPWITHTIMEZONE, "timestamptz");
    public static final DataType<OffsetDateTime> TIMESTAMPWITHTIMEZONE = new BuiltInDataType<OffsetDateTime>(FAMILY, SQLDataType.TIMESTAMPWITHTIMEZONE, "timestamp with time zone");
    public static final DataType<Instant> INSTANT = new BuiltInDataType<Instant>(FAMILY, SQLDataType.INSTANT, "timestamptz");
    public static final DataType<byte[]> BYTES = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "bytes");
    public static final DataType<byte[]> BYTEA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "bytea");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob");
    public static final DataType<YearToSecond> INTERVAL = new BuiltInDataType<YearToSecond>(FAMILY, SQLDataType.INTERVAL, "interval");
    public static final DataType<YearToMonth> INTERVALYEARTOMONTH = new BuiltInDataType<YearToMonth>(FAMILY, SQLDataType.INTERVALYEARTOMONTH, "interval year to month");
    public static final DataType<DayToSecond> INTERVALDAYTOSECOND = new BuiltInDataType<DayToSecond>(FAMILY, SQLDataType.INTERVALDAYTOSECOND, "interval day to second");
    protected static final DataType<byte[]> __BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "bytes");
    protected static final DataType<Boolean> __BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "boolean");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "bytes");
    protected static final DataType<String> __LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "varchar(l)");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "char(l)");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "text");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "varchar(l)");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "varchar(l)");
    protected static final DataType<Byte> __TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "smallint");
    protected static final DataType<byte[]> __VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "bytes");
    protected static final DataType<UByte> __TINYINTUNSIGNED = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "smallint");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "int4");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "bigint");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "decimal(p, s)");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal(p, s)");
    public static final DataType<Short> SERIAL2 = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "serial2");
    public static final DataType<Short> SMALLSERIAL = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallserial");
    public static final DataType<Integer> SERIAL4 = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "serial4");
    public static final DataType<Long> SERIAL = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "serial");
    public static final DataType<Long> BIGSERIAL = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigserial");
    public static final DataType<Long> SERIAL8 = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "serial8");
    public static final DataType<String> BITVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "bit varying");
    public static final DataType<String> VARBIT = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varbit");
    public static final DataType<String> BIT = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "bit");
    public static final DataType<UUID> UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "uuid");
    public static final DataType<JSON> JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "json");
    public static final DataType<JSONB> JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "jsonb");
}

