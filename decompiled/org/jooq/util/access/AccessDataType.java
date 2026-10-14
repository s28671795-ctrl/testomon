/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.access;

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
public class AccessDataType {
    private static final SQLDialect FAMILY = SQLDialect.ACCESS;
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "binary");
    public static final DataType<Boolean> BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "bit");
    public static final DataType<UByte> TINYINT = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "tinyint");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric");
    public static final DataType<String> TEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "text");
    public static final DataType<String> LONGTEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "longtext");
    public static final DataType<String> LONGCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "longchar");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "character");
    public static final DataType<Timestamp> DATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime");
    protected static final DataType<byte[]> __VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "binary");
    protected static final DataType<Long> __BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "numeric");
    protected static final DataType<BigDecimal> __DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "numeric");
    protected static final DataType<Date> __DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "datetime");
    protected static final DataType<Time> __TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "datetime");
    protected static final DataType<String> __VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "text");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "text");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "text");
    protected static final DataType<String> __NTEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "longtext");
    protected static final DataType<byte[]> __BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "binary");
    protected static final DataType<Boolean> __BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "bit");
    protected static final DataType<Double> __DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "float");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "binary");
    protected static final DataType<String> __LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "longtext");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "longtext");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "longtext");
    protected static final DataType<Byte> __BYTE = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "signed tinyint", "smallint");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "longtext");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "binary");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "numeric");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "integer");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "numeric");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "numeric");
    public static final DataType<Integer> COUNTER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "counter", "integer");
    public static final DataType<BigDecimal> MONEY = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "money");
    public static final DataType<byte[]> IMAGE = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "image");
    public static final DataType<UUID> UNIQUEIDENTIFIER = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "uniqueidentifier");
}

