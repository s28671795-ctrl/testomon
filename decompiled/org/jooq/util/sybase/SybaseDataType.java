/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.sybase;

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
public class SybaseDataType {
    private static final SQLDialect FAMILY = SQLDialect.SYBASE;
    public static final DataType<UByte> UNSIGNEDTINYINT = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "unsigned tinyint");
    public static final DataType<UByte> TINYINT = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "tinyint");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<UShort> UNSIGNEDSMALLLINT = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "unsigned smallint");
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<UInteger> UNSIGNEDINT = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "unsigned int");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<ULong> UNSIGNEDBIGINT = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "unsigned bigint");
    public static final DataType<Double> DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric");
    public static final DataType<Boolean> BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "bit");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char");
    public static final DataType<String> LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "long nvarchar");
    public static final DataType<String> LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "long varchar");
    public static final DataType<String> NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "nchar");
    public static final DataType<String> NTEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "ntext");
    public static final DataType<String> NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "nvarchar");
    public static final DataType<String> TEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "text");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Timestamp> DATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "binary");
    public static final DataType<byte[]> LONGBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "long binary");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbinary");
    protected static final DataType<byte[]> __BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "binary");
    protected static final DataType<Boolean> __BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "bit");
    protected static final DataType<Byte> __BYTE = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "tinyint");
    protected static final DataType<Byte> __BYTESIGNED = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "signed tinyint");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "text");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "binary");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal");
    public static final DataType<BigDecimal> MONEY = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "money");
    public static final DataType<BigDecimal> SMALLMONEY = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "smallmoney");
    public static final DataType<String> UNIQUEIDENTIFIERSTR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "uniqueidentifierstr");
    public static final DataType<String> XML = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "xml");
    public static final DataType<UUID> UNIQUEIDENTIFIER = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "uniqueidentifier");
    public static final DataType<Timestamp> DATETIMEOFFSET = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetimeoffset");
    public static final DataType<Timestamp> SMALLDATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "smalldatetime");
    public static final DataType<Timestamp> TIMESTAMPWITHTIMEZONE = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestampwithtimezone");
    public static final DataType<byte[]> IMAGE = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "image");
    public static final DataType<byte[]> VARBIT = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbit");
    public static final DataType<byte[]> LONGVARBIT = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "longvarbit");
}

