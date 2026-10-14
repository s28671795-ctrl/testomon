/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.db2;

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
public class DB2DataType {
    private static final SQLDialect FAMILY = SQLDialect.DB2;
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<Double> DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<BigDecimal> DECFLOAT = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decfloat");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar", "varchar(32672)");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char", "char(1)");
    public static final DataType<String> CHARACTER = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "character", "character(1)");
    public static final DataType<String> LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "long varchar", "clob");
    public static final DataType<String> CLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "clob");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp");
    protected static final DataType<byte[]> __BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "blob");
    protected static final DataType<Boolean> __BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "smallint");
    protected static final DataType<Boolean> __BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "smallint");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "blob");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "char", "varchar(32672)");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "clob");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "long varchar", "clob");
    protected static final DataType<BigDecimal> __NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "decimal", "decimal");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "varchar", "varchar(32672)");
    protected static final DataType<Byte> __TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "smallint");
    protected static final DataType<byte[]> __VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "blob");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "clob");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "blob");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal", "decimal(31)");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "varchar", "varchar(36)");
    public static final DataType<String> XML = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "xml");
    public static final DataType<String> DBCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "dbclob");
    public static final DataType<String> GRAPHIC = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "graphic");
    public static final DataType<String> VARGRAPHIC = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "vargraphic");
    public static final DataType<byte[]> CHARFORBITDATA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "char for bit data");
    public static final DataType<byte[]> VARCHARFORBITDATA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "varchar(32672) for bit data");
}

