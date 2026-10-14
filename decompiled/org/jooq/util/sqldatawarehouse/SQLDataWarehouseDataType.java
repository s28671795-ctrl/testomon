/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.sqldatawarehouse;

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
import org.jooq.SQLDialect;
import org.jooq.impl.BuiltInDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

@Deprecated(forRemoval=true, since="3.11")
public class SQLDataWarehouseDataType {
    private static final SQLDialect FAMILY = SQLDialect.SQLDATAWAREHOUSE;
    public static final DataType<UByte> TINYINT = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "tinyint");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<Boolean> BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "bit");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Timestamp> DATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar", "varchar(8000)");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char");
    public static final DataType<String> TEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "varchar(8000)");
    public static final DataType<String> NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "nvarchar", "nvarchar(4000)");
    public static final DataType<String> NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "nchar");
    public static final DataType<String> NTEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "nvarchar(4000)");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbinary", "varbinary(8000)");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "binary");
    protected static final DataType<byte[]> __BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "binary");
    protected static final DataType<Boolean> __BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "bit");
    protected static final DataType<Double> __DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "float");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "varbinary", "varbinary(8000)");
    protected static final DataType<String> __LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "varchar", "varchar(8000)");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "nvarchar(4000)");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "varchar", "nvarchar(4000)");
    protected static final DataType<Byte> __BYTE = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "signed tinyint", "tinyint");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "numeric");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "int");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "bigint");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "numeric");
    public static final DataType<Timestamp> SMALLDATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "smalldatetime");
    public static final DataType<Timestamp> DATETIME2 = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime2");
    public static final DataType<OffsetDateTime> DATETIMEOFFSET = new BuiltInDataType<OffsetDateTime>(FAMILY, SQLDataType.TIMESTAMPWITHTIMEZONE, "datetimeoffset");
    public static final DataType<Instant> INSTANT = new BuiltInDataType<Instant>(FAMILY, SQLDataType.INSTANT, "datetimeoffset");
    public static final DataType<BigDecimal> MONEY = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "money");
    public static final DataType<BigDecimal> SMALLMONEY = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "smallmoney");
    public static final DataType<byte[]> IMAGE = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "image");
    public static final DataType<UUID> UNIQUEIDENTIFIER = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "uniqueidentifier");
    public static final DataType<Long> ROWVERSION = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "rowversion");
    public static final DataType<byte[]> TIMESTAMP = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "timestamp");
}

