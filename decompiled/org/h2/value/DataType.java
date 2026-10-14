/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.sql.JDBCType;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.HashMap;
import java.util.Map;
import org.h2.api.H2Type;
import org.h2.api.IntervalQualifier;
import org.h2.engine.Mode;
import org.h2.message.DbException;
import org.h2.util.StringUtils;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;

public class DataType {
    private static final HashMap<String, DataType> TYPES_BY_NAME = new HashMap(128);
    static final DataType[] TYPES_BY_VALUE_TYPE = new DataType[42];
    public int type;
    public int sqlType;
    public long minPrecision;
    public long maxPrecision;
    public int minScale;
    public int maxScale;
    public String prefix;
    public String suffix;
    public String params;
    public boolean caseSensitive;
    public boolean supportsPrecision;
    public boolean supportsScale;
    public long defaultPrecision;
    public int defaultScale;
    public boolean specialPrecisionScale;

    private static void addInterval(int n) {
        IntervalQualifier intervalQualifier = IntervalQualifier.valueOf(n - 22);
        String string = intervalQualifier.toString();
        DataType dataType = new DataType();
        dataType.prefix = "INTERVAL '";
        dataType.suffix = "' " + string;
        dataType.supportsPrecision = true;
        dataType.defaultPrecision = 2L;
        dataType.minPrecision = 1L;
        dataType.maxPrecision = 18L;
        if (intervalQualifier.hasSeconds()) {
            dataType.supportsScale = true;
            dataType.defaultScale = 6;
            dataType.maxScale = 9;
            dataType.params = "PRECISION,SCALE";
        } else {
            dataType.params = "PRECISION";
        }
        DataType.add(n, 1111, dataType, ("INTERVAL " + string).intern());
    }

    private static void add(int n, int n2, DataType dataType, String ... stringArray) {
        dataType.type = n;
        dataType.sqlType = n2;
        if (TYPES_BY_VALUE_TYPE[n] == null) {
            DataType.TYPES_BY_VALUE_TYPE[n] = dataType;
        }
        for (String string : stringArray) {
            TYPES_BY_NAME.put(string, dataType);
        }
    }

    public static DataType createNumeric(int n, int n2) {
        DataType dataType = new DataType();
        dataType.maxPrecision = dataType.minPrecision = (long)n;
        dataType.defaultPrecision = dataType.minPrecision;
        dataType.maxScale = dataType.minScale = n2;
        dataType.defaultScale = dataType.minScale;
        return dataType;
    }

    public static DataType createDate(int n, int n2, String string, boolean bl, int n3, int n4) {
        DataType dataType = new DataType();
        dataType.prefix = string + " '";
        dataType.suffix = "'";
        dataType.maxPrecision = n;
        dataType.defaultPrecision = dataType.minPrecision = (long)n2;
        if (bl) {
            dataType.params = "SCALE";
            dataType.supportsScale = true;
            dataType.maxScale = n4;
            dataType.defaultScale = n3;
        }
        return dataType;
    }

    private static DataType createString(boolean bl, boolean bl2) {
        return DataType.createString(bl, bl2, "'", "'");
    }

    private static DataType createBinary(boolean bl) {
        return DataType.createString(false, bl, "X'", "'");
    }

    private static DataType createString(boolean bl, boolean bl2, String string, String string2) {
        DataType dataType = new DataType();
        dataType.prefix = string;
        dataType.suffix = string2;
        dataType.params = "LENGTH";
        dataType.caseSensitive = bl;
        dataType.supportsPrecision = true;
        dataType.minPrecision = 1L;
        dataType.maxPrecision = 1000000000L;
        dataType.defaultPrecision = bl2 ? 1L : 1000000000L;
        return dataType;
    }

    private static DataType createLob(boolean bl) {
        DataType dataType = bl ? DataType.createString(true, false) : DataType.createBinary(false);
        dataType.maxPrecision = Long.MAX_VALUE;
        dataType.defaultPrecision = Long.MAX_VALUE;
        return dataType;
    }

    private static DataType createGeometry() {
        DataType dataType = new DataType();
        dataType.prefix = "'";
        dataType.suffix = "'";
        dataType.params = "TYPE,SRID";
        dataType.maxPrecision = Long.MAX_VALUE;
        dataType.defaultPrecision = Long.MAX_VALUE;
        return dataType;
    }

    public static DataType getDataType(int n) {
        if (n == -1) {
            throw DbException.get(50004, "?");
        }
        if (n >= 0 && n < 42) {
            return TYPES_BY_VALUE_TYPE[n];
        }
        return TYPES_BY_VALUE_TYPE[0];
    }

    public static int convertTypeToSQLType(TypeInfo typeInfo) {
        int n = typeInfo.getValueType();
        switch (n) {
            case 13: {
                return typeInfo.getExtTypeInfo() != null ? 3 : 2;
            }
            case 14: 
            case 15: {
                if (typeInfo.getDeclaredPrecision() < 0L) break;
                return 6;
            }
        }
        return DataType.getDataType((int)n).sqlType;
    }

    public static int convertSQLTypeToValueType(int n, String string) {
        switch (n) {
            case -2: {
                if (!string.equalsIgnoreCase("UUID")) break;
                return 39;
            }
            case 1111: {
                DataType dataType = TYPES_BY_NAME.get(StringUtils.toUpperEnglish(string));
                if (dataType == null) break;
                return dataType.type;
            }
        }
        return DataType.convertSQLTypeToValueType(n);
    }

    public static int getValueTypeFromResultSet(ResultSetMetaData resultSetMetaData, int n) throws SQLException {
        return DataType.convertSQLTypeToValueType(resultSetMetaData.getColumnType(n), resultSetMetaData.getColumnTypeName(n));
    }

    public static boolean isBinaryColumn(ResultSetMetaData resultSetMetaData, int n) throws SQLException {
        switch (resultSetMetaData.getColumnType(n)) {
            case -2: {
                if (resultSetMetaData.getColumnTypeName(n).equals("UUID")) break;
            }
            case -4: 
            case -3: 
            case 2000: 
            case 2004: {
                return true;
            }
        }
        return false;
    }

    public static int convertSQLTypeToValueType(SQLType sQLType) {
        if (sQLType instanceof H2Type) {
            return sQLType.getVendorTypeNumber();
        }
        if (sQLType instanceof JDBCType) {
            return DataType.convertSQLTypeToValueType(sQLType.getVendorTypeNumber());
        }
        throw DbException.get(50004, sQLType == null ? "<null>" : DataType.unknownSqlTypeToString(new StringBuilder(), sQLType).toString());
    }

    public static int convertSQLTypeToValueType(int n) {
        switch (n) {
            case -15: 
            case 1: {
                return 1;
            }
            case -16: 
            case -9: 
            case -1: 
            case 12: {
                return 2;
            }
            case 2: 
            case 3: {
                return 13;
            }
            case -7: 
            case 16: {
                return 8;
            }
            case 4: {
                return 11;
            }
            case 5: {
                return 10;
            }
            case -6: {
                return 9;
            }
            case -5: {
                return 12;
            }
            case 7: {
                return 14;
            }
            case 6: 
            case 8: {
                return 15;
            }
            case -2: {
                return 5;
            }
            case -4: 
            case -3: {
                return 6;
            }
            case 1111: {
                return -1;
            }
            case 2000: {
                return 35;
            }
            case 91: {
                return 17;
            }
            case 92: {
                return 18;
            }
            case 93: {
                return 20;
            }
            case 2013: {
                return 19;
            }
            case 2014: {
                return 21;
            }
            case 2004: {
                return 7;
            }
            case 2005: 
            case 2011: {
                return 3;
            }
            case 0: {
                return 0;
            }
            case 2003: {
                return 40;
            }
        }
        throw DbException.get(50004, Integer.toString(n));
    }

    public static String sqlTypeToString(SQLType sQLType) {
        if (sQLType == null) {
            return "null";
        }
        if (sQLType instanceof JDBCType) {
            return "JDBCType." + sQLType.getName();
        }
        if (sQLType instanceof H2Type) {
            return sQLType.toString();
        }
        return DataType.unknownSqlTypeToString(new StringBuilder("/* "), sQLType).append(" */ null").toString();
    }

    private static StringBuilder unknownSqlTypeToString(StringBuilder stringBuilder, SQLType sQLType) {
        return stringBuilder.append(StringUtils.quoteJavaString(sQLType.getVendor())).append('/').append(StringUtils.quoteJavaString(sQLType.getName())).append(" [").append(sQLType.getVendorTypeNumber()).append(']');
    }

    public static DataType getTypeByName(String string, Mode mode) {
        DataType dataType = mode.typeByNameMap.get(string);
        if (dataType == null) {
            dataType = TYPES_BY_NAME.get(string);
        }
        return dataType;
    }

    public static boolean isIndexable(TypeInfo typeInfo) {
        switch (typeInfo.getValueType()) {
            case -1: 
            case 0: 
            case 3: 
            case 7: {
                return false;
            }
            case 40: {
                return DataType.isIndexable((TypeInfo)typeInfo.getExtTypeInfo());
            }
            case 41: {
                ExtTypeInfoRow extTypeInfoRow = (ExtTypeInfoRow)typeInfo.getExtTypeInfo();
                for (Map.Entry<String, TypeInfo> entry : extTypeInfoRow.getFields()) {
                    if (DataType.isIndexable(entry.getValue())) continue;
                    return false;
                }
                break;
            }
        }
        return true;
    }

    public static boolean areStableComparable(TypeInfo typeInfo, TypeInfo typeInfo2) {
        int n = typeInfo.getValueType();
        int n2 = typeInfo2.getValueType();
        switch (n) {
            case -1: 
            case 0: 
            case 3: 
            case 7: 
            case 41: {
                return false;
            }
            case 17: 
            case 20: {
                return n2 == 17 || n2 == 20;
            }
            case 18: 
            case 19: 
            case 21: {
                return n == n2;
            }
            case 40: {
                if (n2 == 40) {
                    return DataType.areStableComparable((TypeInfo)typeInfo.getExtTypeInfo(), (TypeInfo)typeInfo2.getExtTypeInfo());
                }
                return false;
            }
        }
        switch (n2) {
            case -1: 
            case 0: 
            case 3: 
            case 7: 
            case 41: {
                return false;
            }
        }
        return true;
    }

    public static boolean isDateTimeType(int n) {
        return n >= 17 && n <= 21;
    }

    public static boolean isIntervalType(int n) {
        return n >= 22 && n <= 34;
    }

    public static boolean isYearMonthIntervalType(int n) {
        return n == 22 || n == 23 || n == 28;
    }

    public static boolean isLargeObject(int n) {
        return n == 7 || n == 3;
    }

    public static boolean isNumericType(int n) {
        return n >= 9 && n <= 16;
    }

    public static boolean isBinaryStringType(int n) {
        return n >= 5 && n <= 7;
    }

    public static boolean isCharacterStringType(int n) {
        return n >= 1 && n <= 4;
    }

    public static boolean isStringType(int n) {
        return n == 2 || n == 1 || n == 4;
    }

    public static boolean isBinaryStringOrSpecialBinaryType(int n) {
        switch (n) {
            case 5: 
            case 6: 
            case 7: 
            case 35: 
            case 37: 
            case 38: 
            case 39: {
                return true;
            }
        }
        return false;
    }

    public static boolean hasTotalOrdering(int n) {
        switch (n) {
            case 5: 
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 14: 
            case 15: 
            case 17: 
            case 18: 
            case 20: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 39: {
                return true;
            }
        }
        return false;
    }

    public static long addPrecision(long l, long l2) {
        long l3 = l + l2;
        if ((l | l2 | l3) < 0L) {
            return Long.MAX_VALUE;
        }
        return l3;
    }

    public static Object getDefaultForPrimitiveType(Class<?> clazz) {
        if (clazz == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (clazz == Byte.TYPE) {
            return (byte)0;
        }
        if (clazz == Character.TYPE) {
            return Character.valueOf('\u0000');
        }
        if (clazz == Short.TYPE) {
            return (short)0;
        }
        if (clazz == Integer.TYPE) {
            return 0;
        }
        if (clazz == Long.TYPE) {
            return 0L;
        }
        if (clazz == Float.TYPE) {
            return Float.valueOf(0.0f);
        }
        if (clazz == Double.TYPE) {
            return 0.0;
        }
        throw DbException.getInternalError("primitive=" + clazz.toString());
    }

    static {
        DataType dataType = new DataType();
        dataType.minPrecision = 1L;
        dataType.maxPrecision = 1L;
        dataType.defaultPrecision = 1L;
        DataType.add(0, 0, dataType, "NULL");
        DataType.add(1, 1, DataType.createString(true, true), "CHARACTER", "CHAR", "NCHAR", "NATIONAL CHARACTER", "NATIONAL CHAR");
        DataType.add(2, 12, DataType.createString(true, false), "CHARACTER VARYING", "VARCHAR", "CHAR VARYING", "NCHAR VARYING", "NATIONAL CHARACTER VARYING", "NATIONAL CHAR VARYING", "VARCHAR2", "NVARCHAR", "NVARCHAR2", "VARCHAR_CASESENSITIVE", "TID", "LONGVARCHAR", "LONGNVARCHAR", "TINYTEXT", "TEXT", "MEDIUMTEXT", "LONGTEXT", "NTEXT");
        DataType.add(3, 2005, DataType.createLob(true), "CHARACTER LARGE OBJECT", "CLOB", "CHAR LARGE OBJECT", "NCLOB", "NCHAR LARGE OBJECT", "NATIONAL CHARACTER LARGE OBJECT");
        DataType.add(4, 12, DataType.createString(false, false), "VARCHAR_IGNORECASE");
        DataType.add(5, -2, DataType.createBinary(true), "BINARY");
        DataType.add(6, -3, DataType.createBinary(false), "BINARY VARYING", "VARBINARY", "RAW", "BYTEA", "LONG RAW", "LONGVARBINARY");
        DataType.add(7, 2004, DataType.createLob(false), "BINARY LARGE OBJECT", "BLOB", "TINYBLOB", "MEDIUMBLOB", "LONGBLOB", "IMAGE");
        DataType.add(8, 16, DataType.createNumeric(1, 0), "BOOLEAN", "BIT", "BOOL");
        DataType.add(9, -6, DataType.createNumeric(8, 0), "TINYINT");
        DataType.add(10, 5, DataType.createNumeric(16, 0), "SMALLINT", "INT2");
        DataType.add(11, 4, DataType.createNumeric(32, 0), "INTEGER", "INT", "MEDIUMINT", "INT4", "SIGNED");
        DataType.add(12, -5, DataType.createNumeric(64, 0), "BIGINT", "INT8", "LONG");
        dataType = new DataType();
        dataType.minPrecision = 1L;
        dataType.maxPrecision = 100000L;
        dataType.defaultPrecision = 100000L;
        dataType.defaultScale = 0;
        dataType.maxScale = 100000;
        dataType.minScale = 0;
        dataType.params = "PRECISION,SCALE";
        dataType.supportsPrecision = true;
        dataType.supportsScale = true;
        DataType.add(13, 2, dataType, "NUMERIC", "DECIMAL", "DEC");
        DataType.add(14, 7, DataType.createNumeric(24, 0), "REAL", "FLOAT4");
        DataType.add(15, 8, DataType.createNumeric(53, 0), "DOUBLE PRECISION", "DOUBLE", "FLOAT8");
        DataType.add(15, 6, DataType.createNumeric(53, 0), "FLOAT");
        dataType = new DataType();
        dataType.minPrecision = 1L;
        dataType.maxPrecision = 100000L;
        dataType.defaultPrecision = 100000L;
        dataType.params = "PRECISION";
        dataType.supportsPrecision = true;
        DataType.add(16, 2, dataType, "DECFLOAT");
        DataType.add(17, 91, DataType.createDate(10, 10, "DATE", false, 0, 0), "DATE");
        DataType.add(18, 92, DataType.createDate(18, 8, "TIME", true, 0, 9), "TIME", "TIME WITHOUT TIME ZONE");
        DataType.add(19, 2013, DataType.createDate(24, 14, "TIME WITH TIME ZONE", true, 0, 9), "TIME WITH TIME ZONE");
        DataType.add(20, 93, DataType.createDate(29, 26, "TIMESTAMP", true, 6, 9), "TIMESTAMP", "TIMESTAMP WITHOUT TIME ZONE", "DATETIME", "DATETIME2", "SMALLDATETIME");
        DataType.add(21, 2014, DataType.createDate(35, 32, "TIMESTAMP WITH TIME ZONE", true, 6, 9), "TIMESTAMP WITH TIME ZONE");
        for (int i = 22; i <= 34; ++i) {
            DataType.addInterval(i);
        }
        DataType.add(35, 2000, DataType.createBinary(false), "JAVA_OBJECT", "OBJECT", "OTHER");
        dataType = DataType.createString(false, false);
        dataType.supportsPrecision = false;
        dataType.params = "ELEMENT [,...]";
        DataType.add(36, 1111, dataType, "ENUM");
        DataType.add(37, 1111, DataType.createGeometry(), "GEOMETRY");
        DataType.add(38, 1111, DataType.createString(true, false, "JSON '", "'"), "JSON");
        dataType = new DataType();
        dataType.suffix = "'";
        dataType.prefix = "'";
        dataType.minPrecision = 16L;
        dataType.maxPrecision = 16L;
        dataType.defaultPrecision = 16L;
        DataType.add(39, -2, dataType, "UUID");
        dataType = new DataType();
        dataType.prefix = "ARRAY[";
        dataType.suffix = "]";
        dataType.params = "CARDINALITY";
        dataType.supportsPrecision = true;
        dataType.maxPrecision = 65536L;
        dataType.defaultPrecision = 65536L;
        DataType.add(40, 2003, dataType, "ARRAY");
        dataType = new DataType();
        dataType.prefix = "ROW(";
        dataType.suffix = ")";
        dataType.params = "NAME DATA_TYPE [,...]";
        DataType.add(41, 1111, dataType, "ROW");
    }
}

