/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.bigquery;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Year;
import java.util.UUID;
import org.jooq.DataType;
import org.jooq.JSONB;
import org.jooq.SQLDialect;
import org.jooq.impl.BuiltInDataType;
import org.jooq.impl.SQLDataType;

@Deprecated(forRemoval=true, since="3.11")
public class BigQueryDataType {
    private static final SQLDialect FAMILY = SQLDialect.BIGQUERY;
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "int64");
    public static final DataType<Byte> TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "int64");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "int64");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int64");
    public static final DataType<Double> DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "float64");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float64");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "float64");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "bool");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "string");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "bytes");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "bytes");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "bytes");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime");
    protected static final DataType<String> __CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "string");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "string");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "string");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "string");
    protected static final DataType<BigDecimal> __NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "decimal");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "string");
    protected static final DataType<String> __LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "string");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "bytes");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "int64");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "string");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "json");
}

