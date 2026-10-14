/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.ase;

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
public class ASEDataType {
    private static final SQLDialect FAMILY = SQLDialect.ASE;
    public static final DataType<Byte> TINYINT = new BuiltInDataType<Byte>(FAMILY, SQLDataType.TINYINT, "tinyint");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "smallint");
    public static final DataType<Short> UNSIGNEDSMALLLINT = new BuiltInDataType<Short>(FAMILY, SQLDataType.SMALLINT, "unsigned smallint");
    public static final DataType<Integer> INT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "int");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "integer");
    public static final DataType<Integer> UNSIGNEDINT = new BuiltInDataType<Integer>(FAMILY, SQLDataType.INTEGER, "unsigned int");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "bigint");
    public static final DataType<Long> UNSIGNEDBIGINT = new BuiltInDataType<Long>(FAMILY, SQLDataType.BIGINT, "unsigned bigint");
    public static final DataType<Double> DOUBLE_PRECISION = new BuiltInDataType<Double>(FAMILY, SQLDataType.DOUBLE, "double precision");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(FAMILY, SQLDataType.FLOAT, "float");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(FAMILY, SQLDataType.REAL, "real");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "decimal");
    public static final DataType<BigDecimal> DEC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "dec");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.NUMERIC, "numeric");
    public static final DataType<Boolean> BIT = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BIT, "bit");
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "varchar");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.CHAR, "char");
    public static final DataType<String> NCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "nchar");
    public static final DataType<String> NVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "nvarchar");
    public static final DataType<String> TEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.CLOB, "text");
    public static final DataType<String> LONGVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGVARCHAR, "text");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(FAMILY, SQLDataType.DATE, "date");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "time");
    public static final DataType<Timestamp> DATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "datetime");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "binary");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.VARBINARY, "varbinary");
    protected static final DataType<byte[]> __BLOB = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BLOB, "binary");
    protected static final DataType<byte[]> __LONGVARBINARY = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.LONGVARBINARY, "varbinary");
    protected static final DataType<Boolean> __BOOLEAN = new BuiltInDataType<Boolean>(FAMILY, SQLDataType.BOOLEAN, "bit");
    protected static final DataType<String> __LONGNVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.LONGNVARCHAR, "unitext");
    protected static final DataType<JSON> __JSON = new BuiltInDataType<JSON>(FAMILY, SQLDataType.JSON, "text");
    protected static final DataType<JSONB> __JSONB = new BuiltInDataType<JSONB>(FAMILY, SQLDataType.JSONB, "binary");
    protected static final DataType<Year> __YEAR = new BuiltInDataType<Year>(FAMILY, SQLDataType.YEAR, "smallint");
    protected static final DataType<BigInteger> __BIGINTEGER = new BuiltInDataType<BigInteger>(FAMILY, SQLDataType.DECIMAL_INTEGER, "decimal");
    protected static final DataType<UUID> __UUID = new BuiltInDataType<UUID>(FAMILY, SQLDataType.UUID, "varchar");
    public static final DataType<String> UNICHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NCHAR, "unichar");
    public static final DataType<String> UNITEXT = new BuiltInDataType<String>(FAMILY, SQLDataType.NCLOB, "unitext");
    public static final DataType<String> UNIVARCHAR = new BuiltInDataType<String>(FAMILY, SQLDataType.NVARCHAR, "univarchar");
    public static final DataType<String> SYSNAME = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "sysname");
    public static final DataType<String> LONGSYSNAME = new BuiltInDataType<String>(FAMILY, SQLDataType.VARCHAR, "longsysname");
    public static final DataType<BigDecimal> MONEY = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "money");
    public static final DataType<BigDecimal> SMALLMONEY = new BuiltInDataType<BigDecimal>(FAMILY, SQLDataType.DECIMAL, "smallmoney");
    public static final DataType<Timestamp> SMALLDATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "smalldatetime");
    public static final DataType<Timestamp> BIGDATETIME = new BuiltInDataType<Timestamp>(FAMILY, SQLDataType.TIMESTAMP, "bigdatetime");
    public static final DataType<Time> BIGTIME = new BuiltInDataType<Time>(FAMILY, SQLDataType.TIME, "bigtime");
    public static final DataType<byte[]> IMAGE = new BuiltInDataType<byte[]>(FAMILY, SQLDataType.BINARY, "image");

    private ASEDataType() {
    }
}

