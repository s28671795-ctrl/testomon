/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.snowflake;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.Year;
import org.jooq.DataType;
import org.jooq.Geography;
import org.jooq.Geometry;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.SQLDialect;
import org.jooq.impl.BuiltInDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

@Deprecated(forRemoval=true, since="3.11")
public class SnowflakeDataType {
    private static final SQLDialect FAMILY = SQLDialect.SNOWFLAKE;
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "boolean");
    public static final DataType<BigDecimal> NUMBER = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "number(p, s)");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "number(p, s)");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar(l)");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char(l)");
    public static final DataType<String> TEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "text");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time(p)");
    public static final DataType<Time> TIMEWITHOUTTIMEZONE = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time(p)");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp_ntz(p)");
    public static final DataType<Timestamp> TIMESTAMPWITHOUTTIMEZONE = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp(p)");
    public static final DataType<OffsetDateTime> TIMESTAMP_TZ = new BuiltInDataType<OffsetDateTime>(FAMILY, SQLDataType.TIMESTAMPWITHTIMEZONE, "timestamp_tz(p)");
    public static final DataType<OffsetDateTime> TIMESTAMP_LTZ = new BuiltInDataType<OffsetDateTime>(FAMILY, SQLDataType.TIMESTAMPWITHTIMEZONE, "timestamp_ltz(p)");
    public static final DataType<OffsetDateTime> TIMESTAMPWITHTIMEZONE = new BuiltInDataType<OffsetDateTime>(FAMILY, SQLDataType.TIMESTAMPWITHTIMEZONE, "timestamp_tz(p)");
    public static final DataType<OffsetDateTime> TIMESTAMPTZ = new BuiltInDataType<OffsetDateTime>(FAMILY, SQLDataType.TIMESTAMPWITHTIMEZONE, "timestamp_tz(p)");
    public static final DataType<Instant> INSTANT = new BuiltInDataType<Instant>(FAMILY, SQLDataType.INSTANT, "timestamp_tz(p)");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbinary(l)");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "binary(l)");
    public static final DataType<Geometry> GEOMETRY = new BuiltInDataType<Geometry>(FAMILY, SQLDataType.GEOMETRY, "geometry");
    public static final DataType<Geography> GEOGRAPHY = new BuiltInDataType<Geography>(FAMILY, SQLDataType.GEOGRAPHY, "geography");
    public static final DataType<JSON> VARIANT = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "variant");
    public static final DataType<JSON> ARRAY = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "array");
    public static final DataType<JSON> OBJECT = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "object");
    protected static final DataType<Long> __BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "number(p, s)", "number(19)");
    protected static final DataType<Boolean> __BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "number(p, s)", "number(1)");
    protected static final DataType<byte[]> __BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "varbinary");
    protected static final DataType<Double> __DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "float");
    protected static final DataType<Integer> __INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "number(p, s)", "number(10)");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "binary(l)");
    protected static final DataType<String> __LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "varchar(l)");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "varchar(l)");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "char");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "varchar");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "varchar(l)");
    protected static final DataType<Float> __REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    protected static final DataType<Short> __SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "number(p, s)", "number(5)");
    protected static final DataType<Time> __TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "timestamp(p)");
    protected static final DataType<Byte> __TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "number(p, s)", "number(3)");
    protected static final DataType<UByte> __TINYINTUNSIGNED = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "number(p, s)", "number(3)");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "number(p, s)", "number(5)");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "number(p, s)", "number(10)");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "number(p, s)", "number(20)");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "number(p, s)", "number(4)");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "variant");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal(p, s)");
}

