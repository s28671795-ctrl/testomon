/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.hsqldb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.util.UUID;
import org.jooq.DataType;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.Result;
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
public class HSQLDBDataType {
    private static final SQLDialect FAMILY = SQLDialect.HSQLDB;
    public static final DataType<Byte> TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "tinyint");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Double> DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double");
    public static final DataType<Double> DOUBLEPRECISION = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double precision");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "boolean");
    public static final DataType<Boolean> BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "bit");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal(p, s)");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric(p, s)");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar(l)", "varchar(32672)");
    public static final DataType<String> LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "longvarchar(l)");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char(l)");
    public static final DataType<String> CHARACTER = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "character(l)");
    public static final DataType<String> CHARACTERVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "character varying(l)", "character varying(32672)");
    public static final DataType<String> CLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "clob");
    public static final DataType<String> CHARLARGEOBJECT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "char large object", "clob");
    public static final DataType<String> CHARACTERLARGEOBJECT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "character large object", "clob");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time(p)");
    public static final DataType<Time> TIMEWITHOUTTIMEZONE = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time(p) without time zone");
    public static final DataType<OffsetTime> TIMEWITHTIMEZONE = new BuiltInDataType<OffsetTime>(FAMILY, SQLDataType.TIMEWITHTIMEZONE, "time(p) with time zone");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp(p)");
    public static final DataType<Timestamp> TIMESTAMPWITHOUTTIMEZONE = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp(p) without time zone");
    public static final DataType<Timestamp> DATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime(p)");
    public static final DataType<OffsetDateTime> TIMESTAMPWITHTIMEZONE = new BuiltInDataType<OffsetDateTime>(FAMILY, SQLDataType.TIMESTAMPWITHTIMEZONE, "timestamp(p) with time zone");
    public static final DataType<Instant> INSTANT = new BuiltInDataType<Instant>(FAMILY, SQLDataType.INSTANT, "timestamp(p) with time zone");
    public static final DataType<byte[]> LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "longvarbinary(l)");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbinary(l)", "varbinary(32672)");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "binary(l)");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob");
    public static final DataType<byte[]> BINARYLARGEOBJECT = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "binary large object", "blob");
    public static final DataType<Object> OTHER = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "other");
    public static final DataType<YearToSecond> INTERVAL = new BuiltInDataType<YearToSecond>(FAMILY, SQLDataType.INTERVAL, "interval");
    public static final DataType<YearToMonth> INTERVALYEARTOMONTH = new BuiltInDataType<YearToMonth>(FAMILY, SQLDataType.INTERVALYEARTOMONTH, "interval year to month");
    public static final DataType<DayToSecond> INTERVALDAYTOSECOND = new BuiltInDataType<DayToSecond>(FAMILY, SQLDataType.INTERVALDAYTOSECOND, "interval day to second", "interval day(9) to second");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "char(l)");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "clob");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "longvarchar(l)");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "varchar(l)", "varchar(32672)");
    protected static final DataType<UByte> __TINYINTUNSIGNED = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "smallint");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "int");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "bigint");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "decimal(p, s)");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "clob");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "blob");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal(p, s)");
    public static final DataType<UUID> UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "uuid");
    public static final DataType<String> VARCHARIGNORECASE = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar_ignorecase(l)", "varchar_ignorecase(32672)");
    public static final DataType<Object> OBJECT = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "object");
    public static final DataType<Result<Record>> ROW = new BuiltInDataType<Result<Record>>(FAMILY, SQLDataType.RESULT, "row");
}

