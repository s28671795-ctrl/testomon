/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.mysql;

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
public class MySQLDataType {
    private static final SQLDialect FAMILY = SQLDialect.MYSQL;
    public static final DataType<Byte> TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "tinyint", "signed");
    public static final DataType<UByte> TINYINTUNSIGNED = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "tinyint unsigned", "unsigned");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint", "signed");
    public static final DataType<UShort> SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "smallint unsigned", "unsigned");
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int", "signed");
    public static final DataType<UInteger> INTUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "int unsigned", "unsigned");
    public static final DataType<Integer> MEDIUMINT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "mediumint", "signed");
    public static final DataType<UInteger> MEDIUMINTUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "mediumint unsigned", "unsigned");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer", "signed");
    public static final DataType<UInteger> INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "integer unsigned", "unsigned");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint", "signed");
    public static final DataType<ULong> BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "bigint unsigned", "unsigned");
    public static final DataType<Double> DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double", "decimal");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float", "decimal");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real", "decimal");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "boolean", "unsigned");
    public static final DataType<Boolean> BOOL = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "bool", "unsigned");
    public static final DataType<Boolean> BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "bit", "unsigned");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal", "decimal");
    public static final DataType<BigDecimal> DEC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "dec", "decimal");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar", "char");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char", "char");
    public static final DataType<String> TEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "text", "char");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob", "binary");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "binary", "binary");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbinary", "binary");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date", "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time", "time");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp", "datetime");
    public static final DataType<Timestamp> DATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime", "datetime");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "char", "char");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "clob", "char");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "varchar", "char");
    protected static final DataType<BigDecimal> __NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "decimal", "decimal");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "varchar", "char");
    protected static final DataType<String> __LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "varchar", "char");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "varbinary", "binary");
    protected static final DataType<BigDecimal> __DECIMALUNSIGNED = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal unsigned", "decimal");
    protected static final DataType<Double> __DOUBLEUNSIGNED = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double unsigned", "decimal");
    protected static final DataType<Double> __FLOATUNSIGNED = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float unsigned", "decimal");
    protected static final DataType<Float> __REALUNSIGNED = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real unsigned", "decimal");
    public static final DataType<String> TINYTEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "tinytext", "char");
    public static final DataType<String> MEDIUMTEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "mediumtext", "char");
    public static final DataType<String> LONGTEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "longtext", "char");
    public static final DataType<String> ENUM = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "enum", "char");
    public static final DataType<String> SET = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "set", "char");
    public static final DataType<byte[]> TINYBLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "tinyblob", "binary");
    public static final DataType<byte[]> MEDIUMBLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "mediumblob", "binary");
    public static final DataType<byte[]> LONGBLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "longblob", "binary");
    public static final DataType<Year> YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "year", "signed");
    public static final DataType<JSON> JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "json");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal", "decimal");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "varchar", "char");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "json");
}

