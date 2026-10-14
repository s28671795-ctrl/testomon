/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.ingres;

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
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

@Deprecated(forRemoval=true, since="3.11")
public class IngresDataType {
    private static final SQLDialect FAMILY = SQLDialect.INGRES;
    public static final DataType<Byte> TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "tinyint");
    public static final DataType<Byte> INTEGER1 = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "integer1");
    public static final DataType<Byte> I1 = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "i1");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Short> INTEGER2 = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "integer2");
    public static final DataType<Short> I2 = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "i2");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<Integer> INTEGER4 = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer4");
    public static final DataType<Integer> I4 = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "i4");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Long> INTEGER8 = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "integer8");
    public static final DataType<Long> I8 = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "i8");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float");
    public static final DataType<Double> FLOAT8 = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "float8");
    public static final DataType<Double> DOUBLEPRECISION = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double precision");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<Float> FLOAT4 = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "float4");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "boolean");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal", "decimal(38, 19)");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar");
    public static final DataType<String> CHARACTERVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "character varying");
    public static final DataType<String> LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "long varchar");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char");
    public static final DataType<String> CHARACTER = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "character");
    public static final DataType<String> C = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "c");
    public static final DataType<String> CLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "clob");
    public static final DataType<String> CHARLARGEOBJECT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "char large object");
    public static final DataType<String> CHARACTERLARGEOBJECT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "character large object");
    public static final DataType<String> NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "nvarchar");
    public static final DataType<String> LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "long nvarchar");
    public static final DataType<String> NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "nchar");
    public static final DataType<String> NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "nclob");
    public static final DataType<String> NCHARLARGEOBJECT = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "nchar large object");
    public static final DataType<String> NATIONALCHARACTERLARGEOBJECT = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "national character large object");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob");
    public static final DataType<byte[]> BINARYLARGEOBJECT = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "binary large object");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "binary");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbinary");
    public static final DataType<byte[]> LONGBYTE = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "long byte");
    public static final DataType<Boolean> BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "boolean");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "decimal", "decimal(38, 19)");
    protected static final DataType<UByte> __TINYINTUNSIGNED = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "smallint");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "integer");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "bigint");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "decimal", "decimal(20)");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "clob");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "blob");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal", "decimal(38, 0)");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "varchar");
    public static final DataType<String> TEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "text");
    public static final DataType<Date> ANSIDATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "ansidate");
    public static final DataType<Date> INGRESDATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "ingresdate");
    public static final DataType<Time> TIMEWITHTIMEZONE = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time with time zone");
    public static final DataType<Time> TIMEWITHOUTTIMEZONE = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time without time zone");
    public static final DataType<Time> TIMEWITHLOCALTIMEZONE = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time with local time zone");
    public static final DataType<Timestamp> TIMESTAMPWITHTIMEZONE = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp with time zone");
    public static final DataType<Timestamp> TIMESTAMPWITHOUTTIMEZONE = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp without time zone");
    public static final DataType<Timestamp> TIMESTAMPWITHLOCALTIMEZONE = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp with local time zone");
    public static final DataType<byte[]> BYTE = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "byte");
    public static final DataType<byte[]> VARBYTE = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbyte");
}

