/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.vertica;

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
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.BuiltInDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

@Deprecated(forRemoval=true, since="3.11")
public class VerticaDataType {
    private static final SQLDialect FAMILY = SQLDialect.VERTICA;
    public static final DataType<Byte> TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "tinyint");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Double> DOUBLEPRECISION = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double precision");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "boolean");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar");
    public static final DataType<String> LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "long varchar");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp");
    public static final DataType<Timestamp> DATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime");
    public static final DataType<Timestamp> SMALLDATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "smalldatetime");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbinary");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "binary");
    public static final DataType<byte[]> LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "long varbinary");
    public static final DataType<Object> OTHER = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "other");
    protected static final DataType<Boolean> __BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "boolean");
    protected static final DataType<byte[]> __BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "long varbinary");
    protected static final DataType<String> __CLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "long varchar");
    protected static final DataType<String> __CHARLARGEOBJECT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "long varchar");
    protected static final DataType<String> __CHARACTERLARGEOBJECT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "long varchar");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "char");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "clob");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "long varchar");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "varchar", "varchar(32672)");
    protected static final DataType<UByte> __TINYINTUNSIGNED = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "smallint");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "int");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "bigint");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "decimal");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "long varchar");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "long varbinary");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "varchar", "varchar(36)");
    public static final DataType<byte[]> BYTEA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "bytea");
    public static final DataType<byte[]> RAW = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "raw");
    public static final DataType<String> VARCHARIGNORECASE = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar_ignorecase");
    public static final DataType<Object> OBJECT = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "object");
    public static final DataType<Result<Record>> ROW = new BuiltInDataType<Result<Record>>(FAMILY, SQLDataType.RESULT, "row");
}

