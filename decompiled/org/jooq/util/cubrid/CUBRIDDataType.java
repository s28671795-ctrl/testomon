/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.cubrid;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
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
public class CUBRIDDataType {
    private static final SQLDialect FAMILY = SQLDialect.CUBRID;
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<Short> SHORT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "short");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<BigDecimal> DEC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "dec");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "numeric");
    public static final DataType<Float> FLOAT = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "float");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<Double> DOUBLE = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double");
    public static final DataType<Double> DOUBLEPRECISION = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double precision");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar");
    public static final DataType<String> CHARVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "char varying");
    public static final DataType<String> CHARACTERVARYING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "character varying");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char", "varchar");
    public static final DataType<String> CHARACTER = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "character", "varchar");
    public static final DataType<String> STRING = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "string");
    public static final DataType<String> NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "nchar");
    public static final DataType<String> CLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "clob");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Timestamp> DATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "timestamp");
    public static final DataType<byte[]> BITVARYING = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "bit varying");
    public static final DataType<byte[]> VARBIT = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbit");
    public static final DataType<byte[]> BIT = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "bit");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "blob");
    protected static final DataType<Boolean> __BOOL = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "bit", "bit(1)");
    protected static final DataType<Boolean> __BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "bit", "bit(1)");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "varchar");
    protected static final DataType<String> __NCLOB = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "clob");
    protected static final DataType<String> __NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "varchar");
    protected static final DataType<String> __LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "varchar");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "blob");
    protected static final DataType<Byte> __TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "smallint");
    protected static final DataType<Double> __FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double");
    protected static final DataType<BigDecimal> __NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "decimal");
    protected static final DataType<UByte> __TINYINTUNSIGNED = new BuiltInDataType<UByte>(FAMILY, SQLDataType.TINYINTUNSIGNED, "smallint");
    protected static final DataType<UShort> __SMALLINTUNSIGNED = new BuiltInDataType<UShort>(FAMILY, SQLDataType.SMALLINTUNSIGNED, "int");
    protected static final DataType<UInteger> __INTEGERUNSIGNED = new BuiltInDataType<UInteger>(FAMILY, SQLDataType.INTEGERUNSIGNED, "bigint");
    protected static final DataType<ULong> __BIGINTUNSIGNED = new BuiltInDataType<ULong>(FAMILY, SQLDataType.BIGINTUNSIGNED, "decimal");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal", "decimal");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "varchar");
    public static final DataType<Double> MONETARY = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "monetary");
    public static final DataType<String> ENUM = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "enum", "varchar");
    public static final DataType<Object> OBJECT = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "object");
    public static final DataType<Object> OID = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "oid");
    public static final DataType<Object> ELO = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "elo");
    public static final DataType<Object> MULTISET = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "multiset");
    public static final DataType<Object> SEQUENCE = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "sequence");
    public static final DataType<Object> SET = new BuiltInDataType<Object>(FAMILY, SQLDataType.OTHER, "set");
}

