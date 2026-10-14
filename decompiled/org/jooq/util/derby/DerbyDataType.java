/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.derby;

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
public class DerbyDataType {
    private static final SQLDialect FAMILY = SQLDialect.DERBY;
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Double> DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double");
    public static final DataType<Double> DOUBLEPRECISION = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double precision");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<BigDecimal> DEC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "dec");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar", "varchar(32672)");
    public static final DataType<String> LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "long varchar");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char", "char(1)");
    public static final DataType<String> CHARACTER = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "character", "character(1)");
    public static final DataType<String> CLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "clob");
    public static final DataType<String> CHARACTERLARGEOBJECT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "character large object");
    public static final DataType<String> CHARVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "char varying", "char varying(32672)");
    public static final DataType<String> CHARACTERVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "character varying", "character varying(32672)");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "boolean");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob");
    public static final DataType<byte[]> BINARYLARGEOBJECT = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "binary large object");
    protected static final DataType<byte[]> __BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "blob");
    protected static final DataType<Boolean> __BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "boolean");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "blob");
    protected static final DataType<String> __NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "char", "char(1)");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "clob");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "long varchar");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "varchar", "varchar(32672)");
    protected static final DataType<Byte> __TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "smallint");
    protected static final DataType<byte[]> __VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "blob");
    protected static final DataType<UByte> __TINYINTUNSIGNED = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "smallint");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "int");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "bigint");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "decimal", "decimal(20)");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "clob");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "blob");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal", "decimal(31)");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "varchar", "varchar(36)");
    public static final DataType<byte[]> CHARFORBITDATA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "char for bit data");
    public static final DataType<byte[]> CHARACTERFORBITDATA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "character for bit data");
    public static final DataType<byte[]> LONGVARCHARFORBITDATA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "long varchar for bit data");
    public static final DataType<byte[]> VARCHARFORBITDATA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varchar for bit data", "varchar(32672) for bit data");
    public static final DataType<byte[]> CHARVARYINGFORBITDATA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "char varying for bit data", "char varying(32672) for bit data");
    public static final DataType<byte[]> CHARACTERVARYINGFORBITDATA = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "character varying for bit data", "character varying (32672) for bit data");
    public static final DataType<String> ORGAPACHEDERBYCATALOGTYPEDESCRIPTOR = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "org.apache.derby.catalog.TypeDescriptor");
    public static final DataType<String> ORGAPACHEDERBYCATALOGINDEXDESCRIPTOR = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "org.apache.derby.catalog.IndexDescriptor");
    public static final DataType<String> JAVAIOSERIALIZABLE = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "java.io.Serializable");
}

