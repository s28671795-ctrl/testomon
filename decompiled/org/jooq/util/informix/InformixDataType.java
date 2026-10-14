/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.informix;

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

@Deprecated(forRemoval=true, since="3.11")
public class InformixDataType {
    private static final SQLDialect FAMILY = SQLDialect.INFORMIX;
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "boolean");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Long> INT8 = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "int8");
    public static final DataType<Float> SMALLFLOAT = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "smallfloat");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "float");
    public static final DataType<Double> DOUBLE_PRECISION = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double precision");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<BigDecimal> DEC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "dec");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric");
    public static final DataType<String> LVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "lvarchar");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar", "lvarchar(254)");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char");
    public static final DataType<String> CHARACTER = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "character");
    public static final DataType<String> LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "lvarchar", "clob");
    public static final DataType<String> NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "nchar");
    public static final DataType<String> NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "nvarchar", "nvarchar(254)");
    public static final DataType<String> TEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "text");
    public static final DataType<String> CLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "clob");
    public static final DataType<byte[]> BYTE = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "byte");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Timestamp> DATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime", "datetime year to fraction(5)");
    protected static final DataType<byte[]> __BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "blob");
    protected static final DataType<Boolean> __BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "boolean");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "blob");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "clob");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "long varchar", "clob");
    protected static final DataType<BigDecimal> __NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "decimal", "decimal");
    protected static final DataType<Byte> __TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "smallint");
    protected static final DataType<Time> __TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "datetime", "datetime hour to second");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "text");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "blob");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal", "decimal(31)");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "varchar", "varchar(36)");
    public static final DataType<Integer> SERIAL = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "serial");
    public static final DataType<Long> SERIAL8 = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "serial8");
    public static final DataType<BigDecimal> MONEY = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "money");
}

