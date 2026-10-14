/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.firebird;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
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
public class FirebirdDataType {
    private static final SQLDialect FAMILY = SQLDialect.FIREBIRD;
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Long> INT64 = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "int64");
    public static final DataType<Double> DOUBLEPRECISION = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double precision");
    public static final DataType<Double> DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double");
    public static final DataType<Double> D_FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "d_float");
    public static final DataType<Float> FLOAT = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "float");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "boolean");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar", "varchar(4000)");
    public static final DataType<String> CHARACTERVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "character varying", "varchar(4000)");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char");
    public static final DataType<String> CHARACTER = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "character");
    public static final DataType<String> CLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "blob sub_type text");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob");
    public static final DataType<Boolean> BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "bit");
    public static final DataType<Object> OTHER = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "other");
    public static final DataType<YearToSecond> INTERVAL = new BuiltInDataType<YearToSecond>(FAMILY, SQLDataType.INTERVAL, "interval");
    public static final DataType<YearToMonth> INTERVALYEARTOMONTH = new BuiltInDataType<YearToMonth>(FAMILY, SQLDataType.INTERVALYEARTOMONTH, "interval year to month");
    public static final DataType<DayToSecond> INTERVALDAYTOSECOND = new BuiltInDataType<DayToSecond>(FAMILY, SQLDataType.INTERVALDAYTOSECOND, "interval day to second");
    protected static final DataType<byte[]> __BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "blob");
    protected static final DataType<Double> __FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "double precision");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "blob sub_type text");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "blob");
    protected static final DataType<String> __LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "varchar", "varchar(4000)");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "char");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "clob");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "varchar", "varchar(4000)");
    protected static final DataType<Byte> __TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "smallint");
    protected static final DataType<byte[]> __VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "blob");
    protected static final DataType<UByte> __TINYINTUNSIGNED = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "smallint");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "int");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "bigint");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "decimal", "varchar(20)");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "blob sub_type text");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "blob");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "varchar", "varchar(36)");
    protected static final DataType<byte[]> BLOBSUBTYPEBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob sub_type binary");
    protected static final DataType<byte[]> BLOBSUBTYPE0 = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob sub_type 0");
    protected static final DataType<String> BLOBSUBTYPETEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "blob sub_type text");
    protected static final DataType<String> BLOBSUBTYPE1 = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "blob sub_type 1");
}

