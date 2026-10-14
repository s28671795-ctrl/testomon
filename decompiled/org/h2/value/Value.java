/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.h2.api.IntervalQualifier;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Mode;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.store.DataHandler;
import org.h2.util.Bits;
import org.h2.util.DateTimeUtils;
import org.h2.util.HasSQL;
import org.h2.util.IntervalUtils;
import org.h2.util.JdbcUtils;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.util.geometry.GeoJsonUtils;
import org.h2.util.json.JsonConstructorUtils;
import org.h2.value.CompareMode;
import org.h2.value.DataType;
import org.h2.value.ExtTypeInfoEnum;
import org.h2.value.ExtTypeInfoGeometry;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Typed;
import org.h2.value.ValueArray;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBinary;
import org.h2.value.ValueBlob;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueChar;
import org.h2.value.ValueClob;
import org.h2.value.ValueDate;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueDouble;
import org.h2.value.ValueEnum;
import org.h2.value.ValueGeometry;
import org.h2.value.ValueInteger;
import org.h2.value.ValueInterval;
import org.h2.value.ValueJavaObject;
import org.h2.value.ValueJson;
import org.h2.value.ValueLob;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueReal;
import org.h2.value.ValueRow;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueTinyint;
import org.h2.value.ValueUuid;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;
import org.h2.value.ValueVarcharIgnoreCase;
import org.h2.value.VersionedValue;
import org.h2.value.lob.LobData;
import org.h2.value.lob.LobDataDatabase;
import org.h2.value.lob.LobDataInMemory;

public abstract class Value
extends VersionedValue<Value>
implements HasSQL,
Typed {
    public static final int UNKNOWN = -1;
    public static final int NULL = 0;
    public static final int CHAR = 1;
    public static final int VARCHAR = 2;
    public static final int CLOB = 3;
    public static final int VARCHAR_IGNORECASE = 4;
    public static final int BINARY = 5;
    public static final int VARBINARY = 6;
    public static final int BLOB = 7;
    public static final int BOOLEAN = 8;
    public static final int TINYINT = 9;
    public static final int SMALLINT = 10;
    public static final int INTEGER = 11;
    public static final int BIGINT = 12;
    public static final int NUMERIC = 13;
    public static final int REAL = 14;
    public static final int DOUBLE = 15;
    public static final int DECFLOAT = 16;
    public static final int DATE = 17;
    public static final int TIME = 18;
    public static final int TIME_TZ = 19;
    public static final int TIMESTAMP = 20;
    public static final int TIMESTAMP_TZ = 21;
    public static final int INTERVAL_YEAR = 22;
    public static final int INTERVAL_MONTH = 23;
    public static final int INTERVAL_DAY = 24;
    public static final int INTERVAL_HOUR = 25;
    public static final int INTERVAL_MINUTE = 26;
    public static final int INTERVAL_SECOND = 27;
    public static final int INTERVAL_YEAR_TO_MONTH = 28;
    public static final int INTERVAL_DAY_TO_HOUR = 29;
    public static final int INTERVAL_DAY_TO_MINUTE = 30;
    public static final int INTERVAL_DAY_TO_SECOND = 31;
    public static final int INTERVAL_HOUR_TO_MINUTE = 32;
    public static final int INTERVAL_HOUR_TO_SECOND = 33;
    public static final int INTERVAL_MINUTE_TO_SECOND = 34;
    public static final int JAVA_OBJECT = 35;
    public static final int ENUM = 36;
    public static final int GEOMETRY = 37;
    public static final int JSON = 38;
    public static final int UUID = 39;
    public static final int ARRAY = 40;
    public static final int ROW = 41;
    public static final int TYPE_COUNT = 42;
    static final int GROUP_NULL = 0;
    static final int GROUP_CHARACTER_STRING = 1;
    static final int GROUP_BINARY_STRING = 2;
    static final int GROUP_BOOLEAN = 3;
    static final int GROUP_NUMERIC = 4;
    static final int GROUP_DATETIME = 5;
    static final int GROUP_INTERVAL_YM = 6;
    static final int GROUP_INTERVAL_DT = 7;
    static final int GROUP_OTHER = 8;
    static final int GROUP_COLLECTION = 9;
    static final byte[] GROUPS = new byte[]{0, 1, 1, 1, 1, 2, 2, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 7, 7, 7, 7, 6, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 9, 9};
    private static final String[] NAMES = new String[]{"UNKNOWN", "NULL", "CHARACTER", "CHARACTER VARYING", "CHARACTER LARGE OBJECT", "VARCHAR_IGNORECASE", "BINARY", "BINARY VARYING", "BINARY LARGE OBJECT", "BOOLEAN", "TINYINT", "SMALLINT", "INTEGER", "BIGINT", "NUMERIC", "REAL", "DOUBLE PRECISION", "DECFLOAT", "DATE", "TIME", "TIME WITH TIME ZONE", "TIMESTAMP", "TIMESTAMP WITH TIME ZONE", "INTERVAL YEAR", "INTERVAL MONTH", "INTERVAL DAY", "INTERVAL HOUR", "INTERVAL MINUTE", "INTERVAL SECOND", "INTERVAL YEAR TO MONTH", "INTERVAL DAY TO HOUR", "INTERVAL DAY TO MINUTE", "INTERVAL DAY TO SECOND", "INTERVAL HOUR TO MINUTE", "INTERVAL HOUR TO SECOND", "INTERVAL MINUTE TO SECOND", "JAVA_OBJECT", "ENUM", "GEOMETRY", "JSON", "UUID", "ARRAY", "ROW"};
    public static final Value[] EMPTY_VALUES = new Value[0];
    private static SoftReference<Value[]> softCache;
    static final BigDecimal MAX_LONG_DECIMAL;
    public static final BigDecimal MIN_LONG_DECIMAL;
    static final int CONVERT_TO = 0;
    static final int CAST_TO = 1;
    static final int ASSIGN_TO = 2;

    public static String getTypeName(int n) {
        return NAMES[n + 1];
    }

    static void rangeCheck(long l, long l2, long l3) {
        if ((l | l2) < 0L || l2 > l3 - l) {
            if (l < 0L || l > l3) {
                throw DbException.getInvalidValueException("offset", l + 1L);
            }
            throw DbException.getInvalidValueException("length", l2);
        }
    }

    @Override
    public abstract TypeInfo getType();

    public abstract int getValueType();

    public int getMemory() {
        return 24;
    }

    public abstract int hashCode();

    public abstract boolean equals(Object var1);

    public static int getHigherOrder(int n, int n2) {
        if (n == n2) {
            if (n == -1) {
                throw DbException.get(50004, "?, ?");
            }
            return n;
        }
        if (n < n2) {
            int n3 = n;
            n = n2;
            n2 = n3;
        }
        if (n == -1) {
            if (n2 == 0) {
                throw DbException.get(50004, "?, NULL");
            }
            return n2;
        }
        if (n2 == -1) {
            if (n == 0) {
                throw DbException.get(50004, "NULL, ?");
            }
            return n;
        }
        if (n2 == 0) {
            return n;
        }
        return Value.getHigherOrderKnown(n, n2);
    }

    private static int getHigherOrderNonNull(int n, int n2) {
        if (n == n2) {
            return n;
        }
        if (n < n2) {
            int n3 = n;
            n = n2;
            n2 = n3;
        }
        return Value.getHigherOrderKnown(n, n2);
    }

    static int getHigherOrderKnown(int n, int n2) {
        byte by = GROUPS[n];
        byte by2 = GROUPS[n2];
        switch (by) {
            case 3: {
                if (by2 != 2) break;
                throw Value.getDataTypeCombinationException(8, n2);
            }
            case 4: {
                return Value.getHigherNumeric(n, n2, by2);
            }
            case 5: {
                return Value.getHigherDateTime(n, n2, by2);
            }
            case 6: {
                return Value.getHigherIntervalYearMonth(n, n2, by2);
            }
            case 7: {
                return Value.getHigherIntervalDayTime(n, n2, by2);
            }
            case 8: {
                return Value.getHigherOther(n, n2, by2);
            }
        }
        return n;
    }

    private static int getHigherNumeric(int n, int n2, int n3) {
        if (n3 == 4) {
            switch (n) {
                case 14: {
                    switch (n2) {
                        case 11: {
                            return 15;
                        }
                        case 12: 
                        case 13: {
                            return 16;
                        }
                    }
                    break;
                }
                case 15: {
                    switch (n2) {
                        case 12: 
                        case 13: {
                            return 16;
                        }
                    }
                }
            }
        } else if (n3 == 2) {
            throw Value.getDataTypeCombinationException(n, n2);
        }
        return n;
    }

    private static int getHigherDateTime(int n, int n2, int n3) {
        if (n3 == 1) {
            return n;
        }
        if (n3 != 5) {
            throw Value.getDataTypeCombinationException(n, n2);
        }
        switch (n) {
            case 18: {
                if (n2 != 17) break;
                return 20;
            }
            case 19: {
                if (n2 != 17) break;
                return 21;
            }
            case 20: {
                if (n2 != 19) break;
                return 21;
            }
        }
        return n;
    }

    private static int getHigherIntervalYearMonth(int n, int n2, int n3) {
        switch (n3) {
            case 6: {
                if (n == 23 && n2 == 22) {
                    return 28;
                }
            }
            case 1: 
            case 4: {
                return n;
            }
        }
        throw Value.getDataTypeCombinationException(n, n2);
    }

    private static int getHigherIntervalDayTime(int n, int n2, int n3) {
        switch (n3) {
            case 7: {
                break;
            }
            case 1: 
            case 4: {
                return n;
            }
            default: {
                throw Value.getDataTypeCombinationException(n, n2);
            }
        }
        switch (n) {
            case 25: {
                return 29;
            }
            case 26: {
                if (n2 == 24) {
                    return 30;
                }
                return 32;
            }
            case 27: {
                if (n2 == 24) {
                    return 31;
                }
                if (n2 == 25) {
                    return 33;
                }
                return 34;
            }
            case 29: {
                if (n2 == 26) {
                    return 30;
                }
                if (n2 != 27) break;
                return 31;
            }
            case 30: {
                if (n2 != 27) break;
                return 31;
            }
            case 32: {
                switch (n2) {
                    case 24: 
                    case 29: 
                    case 30: {
                        return 30;
                    }
                    case 27: {
                        return 33;
                    }
                    case 31: {
                        return 31;
                    }
                }
                break;
            }
            case 33: {
                switch (n2) {
                    case 24: 
                    case 29: 
                    case 30: 
                    case 31: {
                        return 31;
                    }
                }
                break;
            }
            case 34: {
                switch (n2) {
                    case 24: 
                    case 29: 
                    case 30: 
                    case 31: {
                        return 31;
                    }
                    case 25: 
                    case 32: 
                    case 33: {
                        return 33;
                    }
                }
            }
        }
        return n;
    }

    private static int getHigherOther(int n, int n2, int n3) {
        block0 : switch (n) {
            case 35: {
                if (n3 == 2) break;
                throw Value.getDataTypeCombinationException(n, n2);
            }
            case 36: {
                if (n3 == 1 || n3 == 4 && n2 <= 11) break;
                throw Value.getDataTypeCombinationException(n, n2);
            }
            case 37: {
                if (n3 == 1 || n3 == 2) break;
                throw Value.getDataTypeCombinationException(n, n2);
            }
            case 38: {
                switch (n3) {
                    case 5: 
                    case 6: 
                    case 7: 
                    case 8: {
                        throw Value.getDataTypeCombinationException(n, n2);
                    }
                }
                break;
            }
            case 39: {
                switch (n3) {
                    case 1: 
                    case 2: {
                        break block0;
                    }
                    case 8: {
                        if (n2 == 35) break block0;
                    }
                    default: {
                        throw Value.getDataTypeCombinationException(n, n2);
                    }
                }
            }
        }
        return n;
    }

    private static DbException getDataTypeCombinationException(int n, int n2) {
        return DbException.get(22018, Value.getTypeName(n) + ", " + Value.getTypeName(n2));
    }

    static Value cache(Value value) {
        if (SysProperties.OBJECT_CACHE) {
            int n;
            Value value2;
            Value[] valueArray;
            int n2 = value.hashCode();
            if (softCache == null || (valueArray = softCache.get()) == null) {
                valueArray = new Value[SysProperties.OBJECT_CACHE_SIZE];
                softCache = new SoftReference<Value[]>(valueArray);
            }
            if ((value2 = valueArray[n = n2 & SysProperties.OBJECT_CACHE_SIZE - 1]) != null && value2.getValueType() == value.getValueType() && value.equals(value2)) {
                return value2;
            }
            valueArray[n] = value;
        }
        return value;
    }

    public static void clearCache() {
        softCache = null;
    }

    public abstract String getString();

    public Reader getReader() {
        return new StringReader(this.getString());
    }

    public Reader getReader(long l, long l2) {
        String string = this.getString();
        long l3 = l - 1L;
        Value.rangeCheck(l3, l2, string.length());
        int n = (int)l3;
        return new StringReader(string.substring(n, n + (int)l2));
    }

    public byte[] getBytes() {
        throw this.getDataConversionError(6);
    }

    public byte[] getBytesNoCopy() {
        return this.getBytes();
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.getBytesNoCopy());
    }

    public InputStream getInputStream(long l, long l2) {
        byte[] byArray = this.getBytesNoCopy();
        long l3 = l - 1L;
        Value.rangeCheck(l3, l2, byArray.length);
        return new ByteArrayInputStream(byArray, (int)l3, (int)l2);
    }

    public boolean getBoolean() {
        return this.convertToBoolean().getBoolean();
    }

    public byte getByte() {
        return this.convertToTinyint(null).getByte();
    }

    public short getShort() {
        return this.convertToSmallint(null).getShort();
    }

    public int getInt() {
        return this.convertToInt(null).getInt();
    }

    public long getLong() {
        return this.convertToBigint(null).getLong();
    }

    public BigDecimal getBigDecimal() {
        throw this.getDataConversionError(13);
    }

    public float getFloat() {
        throw this.getDataConversionError(14);
    }

    public double getDouble() {
        throw this.getDataConversionError(15);
    }

    public Value add(Value value) {
        throw this.getUnsupportedExceptionForOperation("+");
    }

    public int getSignum() {
        throw this.getUnsupportedExceptionForOperation("SIGNUM");
    }

    public Value negate() {
        throw this.getUnsupportedExceptionForOperation("NEG");
    }

    public Value subtract(Value value) {
        throw this.getUnsupportedExceptionForOperation("-");
    }

    public Value divide(Value value, TypeInfo typeInfo) {
        throw this.getUnsupportedExceptionForOperation("/");
    }

    public Value multiply(Value value) {
        throw this.getUnsupportedExceptionForOperation("*");
    }

    public Value modulus(Value value) {
        throw this.getUnsupportedExceptionForOperation("%");
    }

    public final Value convertTo(int n) {
        return this.convertTo(n, null);
    }

    public final Value convertTo(TypeInfo typeInfo) {
        return this.convertTo(typeInfo, null, 0, null);
    }

    public final Value convertTo(int n, CastDataProvider castDataProvider) {
        switch (n) {
            case 40: {
                return this.convertToAnyArray(castDataProvider);
            }
            case 41: {
                return this.convertToAnyRow();
            }
        }
        return this.convertTo(TypeInfo.getTypeInfo(n), castDataProvider, 0, null);
    }

    public final Value convertTo(TypeInfo typeInfo, CastDataProvider castDataProvider) {
        return this.convertTo(typeInfo, castDataProvider, 0, null);
    }

    public final Value convertTo(TypeInfo typeInfo, CastDataProvider castDataProvider, Object object) {
        return this.convertTo(typeInfo, castDataProvider, 0, object);
    }

    public final ValueJson convertToAnyJson() {
        return this != ValueNull.INSTANCE ? this.convertToJson(TypeInfo.TYPE_JSON, 0, null) : ValueJson.NULL;
    }

    public final ValueArray convertToAnyArray(CastDataProvider castDataProvider) {
        if (this.getValueType() == 40) {
            return (ValueArray)this;
        }
        return ValueArray.get(this.getType(), new Value[]{this}, castDataProvider);
    }

    public final ValueRow convertToAnyRow() {
        if (this.getValueType() == 41) {
            return (ValueRow)this;
        }
        return ValueRow.get(new Value[]{this});
    }

    public final Value castTo(TypeInfo typeInfo, CastDataProvider castDataProvider) {
        return this.convertTo(typeInfo, castDataProvider, 1, null);
    }

    public final Value convertForAssignTo(TypeInfo typeInfo, CastDataProvider castDataProvider, Object object) {
        return this.convertTo(typeInfo, castDataProvider, 2, object);
    }

    private Value convertTo(TypeInfo typeInfo, CastDataProvider castDataProvider, int n, Object object) {
        int n2;
        int n3 = this.getValueType();
        if (n3 == 0 || n3 == (n2 = typeInfo.getValueType()) && n == 0 && typeInfo.getExtTypeInfo() == null && n3 != 1) {
            return this;
        }
        switch (n2) {
            case 0: {
                return ValueNull.INSTANCE;
            }
            case 1: {
                return this.convertToChar(typeInfo, castDataProvider, n, object);
            }
            case 2: {
                return this.convertToVarchar(typeInfo, castDataProvider, n, object);
            }
            case 3: {
                return this.convertToClob(typeInfo, n, object);
            }
            case 4: {
                return this.convertToVarcharIgnoreCase(typeInfo, n, object);
            }
            case 5: {
                return this.convertToBinary(typeInfo, n, object);
            }
            case 6: {
                return this.convertToVarbinary(typeInfo, n, object);
            }
            case 7: {
                return this.convertToBlob(typeInfo, n, object);
            }
            case 8: {
                return this.convertToBoolean();
            }
            case 9: {
                return this.convertToTinyint(object);
            }
            case 10: {
                return this.convertToSmallint(object);
            }
            case 11: {
                return this.convertToInt(object);
            }
            case 12: {
                return this.convertToBigint(object);
            }
            case 13: {
                return this.convertToNumeric(typeInfo, castDataProvider, n, object);
            }
            case 14: {
                return this.convertToReal();
            }
            case 15: {
                return this.convertToDouble();
            }
            case 16: {
                return this.convertToDecfloat(typeInfo, n);
            }
            case 17: {
                return this.convertToDate(castDataProvider);
            }
            case 18: {
                return this.convertToTime(typeInfo, castDataProvider, n);
            }
            case 19: {
                return this.convertToTimeTimeZone(typeInfo, castDataProvider, n);
            }
            case 20: {
                return this.convertToTimestamp(typeInfo, castDataProvider, n);
            }
            case 21: {
                return this.convertToTimestampTimeZone(typeInfo, castDataProvider, n);
            }
            case 22: 
            case 23: 
            case 28: {
                return this.convertToIntervalYearMonth(typeInfo, n, object);
            }
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: {
                return this.convertToIntervalDayTime(typeInfo, n, object);
            }
            case 35: {
                return this.convertToJavaObject(typeInfo, n, object);
            }
            case 36: {
                return this.convertToEnum((ExtTypeInfoEnum)typeInfo.getExtTypeInfo(), castDataProvider);
            }
            case 37: {
                return this.convertToGeometry((ExtTypeInfoGeometry)typeInfo.getExtTypeInfo());
            }
            case 38: {
                return this.convertToJson(typeInfo, n, object);
            }
            case 39: {
                return this.convertToUuid();
            }
            case 40: {
                return this.convertToArray(typeInfo, castDataProvider, n, object);
            }
            case 41: {
                return this.convertToRow(typeInfo, castDataProvider, n, object);
            }
        }
        throw this.getDataConversionError(n2);
    }

    public ValueChar convertToChar() {
        return this.convertToChar(TypeInfo.getTypeInfo(1), null, 0, null);
    }

    private ValueChar convertToChar(TypeInfo typeInfo, CastDataProvider castDataProvider, int n, Object object) {
        int n2;
        int n3;
        int n4 = this.getValueType();
        switch (n4) {
            case 7: 
            case 35: {
                throw this.getDataConversionError(typeInfo.getValueType());
            }
        }
        String string = this.getString();
        if (n == 0) {
            for (n3 = n2 = string.length(); n3 > 0 && string.charAt(n3 - 1) == ' '; --n3) {
            }
        } else {
            int n5 = MathUtils.convertLongToInt(typeInfo.getPrecision());
            if (castDataProvider == null || castDataProvider.getMode().charPadding == Mode.CharPadding.ALWAYS) {
                if (n3 != n5) {
                    if (n3 < n5) {
                        return ValueChar.get(StringUtils.pad(string, n5, null, true));
                    }
                    if (n == 1) {
                        n3 = n5;
                    } else {
                        do {
                            if (string.charAt(--n3) == ' ') continue;
                            throw this.getValueTooLongException(typeInfo, object);
                        } while (n3 > n5);
                    }
                }
            } else {
                if (n == 1 && n3 > n5) {
                    n3 = n5;
                }
                while (n3 > 0 && string.charAt(n3 - 1) == ' ') {
                    --n3;
                }
                if (n == 2 && n3 > n5) {
                    throw this.getValueTooLongException(typeInfo, object);
                }
            }
        }
        if (n2 != n3) {
            string = string.substring(0, n3);
        } else if (n4 == 1) {
            return (ValueChar)this;
        }
        return ValueChar.get(string);
    }

    private Value convertToVarchar(TypeInfo typeInfo, CastDataProvider castDataProvider, int n, Object object) {
        int n2 = this.getValueType();
        switch (n2) {
            case 7: 
            case 35: {
                throw this.getDataConversionError(typeInfo.getValueType());
            }
        }
        if (n != 0) {
            String string = this.getString();
            int n3 = MathUtils.convertLongToInt(typeInfo.getPrecision());
            if (string.length() > n3) {
                if (n != 1) {
                    throw this.getValueTooLongException(typeInfo, object);
                }
                return ValueVarchar.get(string.substring(0, n3), castDataProvider);
            }
        }
        return n2 == 2 ? this : ValueVarchar.get(this.getString(), castDataProvider);
    }

    private ValueClob convertToClob(TypeInfo typeInfo, int n, Object object) {
        ValueClob valueClob;
        switch (this.getValueType()) {
            case 3: {
                valueClob = (ValueClob)this;
                break;
            }
            case 35: {
                throw this.getDataConversionError(typeInfo.getValueType());
            }
            case 7: {
                LobData lobData = ((ValueBlob)this).lobData;
                if (lobData instanceof LobDataInMemory) {
                    byte[] byArray = ((LobDataInMemory)lobData).getSmall();
                    byte[] byArray2 = new String(byArray, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
                    if (Arrays.equals(byArray2, byArray)) {
                        byArray2 = byArray;
                    }
                    valueClob = ValueClob.createSmall(byArray2);
                    break;
                }
                if (lobData instanceof LobDataDatabase) {
                    valueClob = lobData.getDataHandler().getLobStorage().createClob(this.getReader(), -1L);
                    break;
                }
            }
            default: {
                valueClob = ValueClob.createSmall(this.getString());
            }
        }
        if (n != 0) {
            if (n == 1) {
                valueClob = valueClob.convertPrecision(typeInfo.getPrecision());
            } else if (valueClob.charLength() > typeInfo.getPrecision()) {
                throw valueClob.getValueTooLongException(typeInfo, object);
            }
        }
        return valueClob;
    }

    private Value convertToVarcharIgnoreCase(TypeInfo typeInfo, int n, Object object) {
        int n2 = this.getValueType();
        switch (n2) {
            case 7: 
            case 35: {
                throw this.getDataConversionError(typeInfo.getValueType());
            }
        }
        if (n != 0) {
            String string = this.getString();
            int n3 = MathUtils.convertLongToInt(typeInfo.getPrecision());
            if (string.length() > n3) {
                if (n != 1) {
                    throw this.getValueTooLongException(typeInfo, object);
                }
                return ValueVarcharIgnoreCase.get(string.substring(0, n3));
            }
        }
        return n2 == 4 ? this : ValueVarcharIgnoreCase.get(this.getString());
    }

    private ValueBinary convertToBinary(TypeInfo typeInfo, int n, Object object) {
        int n2;
        byte[] byArray;
        int n3;
        ValueBinary valueBinary;
        if (this.getValueType() == 5) {
            valueBinary = (ValueBinary)this;
        } else {
            try {
                valueBinary = ValueBinary.getNoCopy(this.getBytesNoCopy());
            }
            catch (DbException dbException) {
                if (dbException.getErrorCode() == 22018) {
                    throw this.getDataConversionError(5);
                }
                throw dbException;
            }
        }
        if (n != 0 && (n3 = (byArray = valueBinary.getBytesNoCopy()).length) != (n2 = MathUtils.convertLongToInt(typeInfo.getPrecision()))) {
            if (n == 2 && n3 > n2) {
                throw valueBinary.getValueTooLongException(typeInfo, object);
            }
            valueBinary = ValueBinary.getNoCopy(Arrays.copyOf(byArray, n2));
        }
        return valueBinary;
    }

    private ValueVarbinary convertToVarbinary(TypeInfo typeInfo, int n, Object object) {
        ValueVarbinary valueVarbinary = this.getValueType() == 6 ? (ValueVarbinary)this : ValueVarbinary.getNoCopy(this.getBytesNoCopy());
        if (n != 0) {
            byte[] byArray = valueVarbinary.getBytesNoCopy();
            int n2 = byArray.length;
            int n3 = MathUtils.convertLongToInt(typeInfo.getPrecision());
            if (n == 1) {
                if (n2 > n3) {
                    valueVarbinary = ValueVarbinary.getNoCopy(Arrays.copyOf(byArray, n3));
                }
            } else if (n2 > n3) {
                throw valueVarbinary.getValueTooLongException(typeInfo, object);
            }
        }
        return valueVarbinary;
    }

    private ValueBlob convertToBlob(TypeInfo typeInfo, int n, Object object) {
        ValueBlob valueBlob;
        switch (this.getValueType()) {
            case 7: {
                valueBlob = (ValueBlob)this;
                break;
            }
            case 3: {
                DataHandler dataHandler = ((ValueLob)this).lobData.getDataHandler();
                if (dataHandler != null) {
                    valueBlob = dataHandler.getLobStorage().createBlob(this.getInputStream(), -1L);
                    break;
                }
            }
            default: {
                try {
                    valueBlob = ValueBlob.createSmall(this.getBytesNoCopy());
                    break;
                }
                catch (DbException dbException) {
                    if (dbException.getErrorCode() == 22018) {
                        throw this.getDataConversionError(7);
                    }
                    throw dbException;
                }
            }
        }
        if (n != 0) {
            if (n == 1) {
                valueBlob = valueBlob.convertPrecision(typeInfo.getPrecision());
            } else if (valueBlob.octetLength() > typeInfo.getPrecision()) {
                throw valueBlob.getValueTooLongException(typeInfo, object);
            }
        }
        return valueBlob;
    }

    public final ValueBoolean convertToBoolean() {
        switch (this.getValueType()) {
            case 8: {
                return (ValueBoolean)this;
            }
            case 1: 
            case 2: 
            case 4: {
                return ValueBoolean.get(this.getBoolean());
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: {
                return ValueBoolean.get(this.getSignum() != 0);
            }
            default: {
                throw this.getDataConversionError(8);
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    public final ValueTinyint convertToTinyint(Object object) {
        switch (this.getValueType()) {
            case 9: {
                return (ValueTinyint)this;
            }
            case 1: 
            case 2: 
            case 4: 
            case 8: {
                return ValueTinyint.get(this.getByte());
            }
            case 10: 
            case 11: 
            case 36: {
                return ValueTinyint.get(Value.convertToByte(this.getInt(), object));
            }
            case 12: 
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
            case 34: {
                return ValueTinyint.get(Value.convertToByte(this.getLong(), object));
            }
            case 13: 
            case 16: {
                return ValueTinyint.get(Value.convertToByte(Value.convertToLong(this.getBigDecimal(), object), object));
            }
            case 14: 
            case 15: {
                return ValueTinyint.get(Value.convertToByte(Value.convertToLong(this.getDouble(), object), object));
            }
            case 5: 
            case 6: {
                byte[] byArray = this.getBytesNoCopy();
                if (byArray.length == 1) {
                    return ValueTinyint.get(byArray[0]);
                }
            }
            default: {
                throw this.getDataConversionError(9);
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    public final ValueSmallint convertToSmallint(Object object) {
        switch (this.getValueType()) {
            case 10: {
                return (ValueSmallint)this;
            }
            case 1: 
            case 2: 
            case 4: 
            case 8: 
            case 9: {
                return ValueSmallint.get(this.getShort());
            }
            case 11: 
            case 36: {
                return ValueSmallint.get(Value.convertToShort(this.getInt(), object));
            }
            case 12: 
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
            case 34: {
                return ValueSmallint.get(Value.convertToShort(this.getLong(), object));
            }
            case 13: 
            case 16: {
                return ValueSmallint.get(Value.convertToShort(Value.convertToLong(this.getBigDecimal(), object), object));
            }
            case 14: 
            case 15: {
                return ValueSmallint.get(Value.convertToShort(Value.convertToLong(this.getDouble(), object), object));
            }
            case 5: 
            case 6: {
                byte[] byArray = this.getBytesNoCopy();
                if (byArray.length == 2) {
                    return ValueSmallint.get((short)((byArray[0] << 8) + (byArray[1] & 0xFF)));
                }
            }
            default: {
                throw this.getDataConversionError(10);
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    public final ValueInteger convertToInt(Object object) {
        switch (this.getValueType()) {
            case 11: {
                return (ValueInteger)this;
            }
            case 1: 
            case 2: 
            case 4: 
            case 8: 
            case 9: 
            case 10: 
            case 36: {
                return ValueInteger.get(this.getInt());
            }
            case 12: 
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
            case 34: {
                return ValueInteger.get(Value.convertToInt(this.getLong(), object));
            }
            case 13: 
            case 16: {
                return ValueInteger.get(Value.convertToInt(Value.convertToLong(this.getBigDecimal(), object), object));
            }
            case 14: 
            case 15: {
                return ValueInteger.get(Value.convertToInt(Value.convertToLong(this.getDouble(), object), object));
            }
            case 5: 
            case 6: {
                byte[] byArray = this.getBytesNoCopy();
                if (byArray.length == 4) {
                    return ValueInteger.get(Bits.readInt(byArray, 0));
                }
            }
            default: {
                throw this.getDataConversionError(11);
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    public final ValueBigint convertToBigint(Object object) {
        switch (this.getValueType()) {
            case 12: {
                return (ValueBigint)this;
            }
            case 1: 
            case 2: 
            case 4: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
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
            case 36: {
                return ValueBigint.get(this.getLong());
            }
            case 13: 
            case 16: {
                return ValueBigint.get(Value.convertToLong(this.getBigDecimal(), object));
            }
            case 14: 
            case 15: {
                return ValueBigint.get(Value.convertToLong(this.getDouble(), object));
            }
            case 5: 
            case 6: {
                byte[] byArray = this.getBytesNoCopy();
                if (byArray.length == 8) {
                    return ValueBigint.get(Bits.readLong(byArray, 0));
                }
            }
            default: {
                throw this.getDataConversionError(12);
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    private ValueNumeric convertToNumeric(TypeInfo typeInfo, CastDataProvider castDataProvider, int n, Object object) {
        ValueNumeric valueNumeric;
        switch (this.getValueType()) {
            case 13: {
                valueNumeric = (ValueNumeric)this;
                break;
            }
            case 8: {
                valueNumeric = this.getBoolean() ? ValueNumeric.ONE : ValueNumeric.ZERO;
                break;
            }
            default: {
                BigDecimal bigDecimal = this.getBigDecimal();
                int n2 = typeInfo.getScale();
                int n3 = bigDecimal.scale();
                if (n3 < 0 || n3 > 100000 || n != 0 && n3 != n2 && (n3 >= n2 || !castDataProvider.getMode().convertOnlyToSmallerScale)) {
                    bigDecimal = ValueNumeric.setScale(bigDecimal, n2);
                }
                if (n != 0 && (long)bigDecimal.precision() > typeInfo.getPrecision() - (long)n2 + (long)bigDecimal.scale()) {
                    throw this.getValueTooLongException(typeInfo, object);
                }
                return ValueNumeric.get(bigDecimal);
            }
            case 0: {
                throw DbException.getInternalError();
            }
        }
        if (n != 0) {
            BigDecimal bigDecimal;
            int n4 = typeInfo.getScale();
            BigDecimal bigDecimal2 = valueNumeric.getBigDecimal();
            int n5 = bigDecimal2.scale();
            if (!(n5 == n4 || n5 < n4 && castDataProvider.getMode().convertOnlyToSmallerScale)) {
                valueNumeric = ValueNumeric.get(ValueNumeric.setScale(bigDecimal2, n4));
            }
            if ((long)(bigDecimal = valueNumeric.getBigDecimal()).precision() > typeInfo.getPrecision() - (long)n4 + (long)bigDecimal.scale()) {
                throw valueNumeric.getValueTooLongException(typeInfo, object);
            }
        }
        return valueNumeric;
    }

    public final ValueReal convertToReal() {
        switch (this.getValueType()) {
            case 14: {
                return (ValueReal)this;
            }
            case 8: {
                return this.getBoolean() ? ValueReal.ONE : ValueReal.ZERO;
            }
            default: {
                return ValueReal.get(this.getFloat());
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    public final ValueDouble convertToDouble() {
        switch (this.getValueType()) {
            case 15: {
                return (ValueDouble)this;
            }
            case 8: {
                return this.getBoolean() ? ValueDouble.ONE : ValueDouble.ZERO;
            }
            default: {
                return ValueDouble.get(this.getDouble());
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    private ValueDecfloat convertToDecfloat(TypeInfo typeInfo, int n) {
        int n2;
        BigDecimal bigDecimal;
        int n3;
        ValueDecfloat valueDecfloat;
        switch (this.getValueType()) {
            case 16: {
                valueDecfloat = (ValueDecfloat)this;
                if (valueDecfloat.value != null) break;
                return valueDecfloat;
            }
            case 1: 
            case 2: 
            case 4: {
                String string = this.getString().trim();
                try {
                    valueDecfloat = ValueDecfloat.get(new BigDecimal(string));
                    break;
                }
                catch (NumberFormatException numberFormatException) {
                    switch (string) {
                        case "-Infinity": {
                            return ValueDecfloat.NEGATIVE_INFINITY;
                        }
                        case "Infinity": 
                        case "+Infinity": {
                            return ValueDecfloat.POSITIVE_INFINITY;
                        }
                        case "NaN": 
                        case "-NaN": 
                        case "+NaN": {
                            return ValueDecfloat.NAN;
                        }
                    }
                    throw this.getDataConversionError(16);
                }
            }
            case 8: {
                valueDecfloat = this.getBoolean() ? ValueDecfloat.ONE : ValueDecfloat.ZERO;
                break;
            }
            case 14: {
                float f = this.getFloat();
                if (Float.isFinite(f)) {
                    valueDecfloat = ValueDecfloat.get(new BigDecimal(Float.toString(f)));
                    break;
                }
                if (f == Float.POSITIVE_INFINITY) {
                    return ValueDecfloat.POSITIVE_INFINITY;
                }
                if (f == Float.NEGATIVE_INFINITY) {
                    return ValueDecfloat.NEGATIVE_INFINITY;
                }
                return ValueDecfloat.NAN;
            }
            case 15: {
                double d = this.getDouble();
                if (Double.isFinite(d)) {
                    valueDecfloat = ValueDecfloat.get(new BigDecimal(Double.toString(d)));
                    break;
                }
                if (d == Double.POSITIVE_INFINITY) {
                    return ValueDecfloat.POSITIVE_INFINITY;
                }
                if (d == Double.NEGATIVE_INFINITY) {
                    return ValueDecfloat.NEGATIVE_INFINITY;
                }
                return ValueDecfloat.NAN;
            }
            default: {
                try {
                    valueDecfloat = ValueDecfloat.get(this.getBigDecimal());
                    break;
                }
                catch (DbException dbException) {
                    if (dbException.getErrorCode() == 22018) {
                        throw this.getDataConversionError(16);
                    }
                    throw dbException;
                }
            }
            case 0: {
                throw DbException.getInternalError();
            }
        }
        if (n != 0 && (n3 = (bigDecimal = valueDecfloat.value).precision()) > (n2 = (int)typeInfo.getPrecision())) {
            valueDecfloat = ValueDecfloat.get(bigDecimal.setScale(bigDecimal.scale() - n3 + n2, RoundingMode.HALF_UP));
        }
        return valueDecfloat;
    }

    public final ValueDate convertToDate(CastDataProvider castDataProvider) {
        switch (this.getValueType()) {
            case 17: {
                return (ValueDate)this;
            }
            case 20: {
                return ValueDate.fromDateValue(((ValueTimestamp)this).getDateValue());
            }
            case 21: {
                ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)this;
                long l = valueTimestampTimeZone.getTimeNanos();
                long l2 = DateTimeUtils.getEpochSeconds(valueTimestampTimeZone.getDateValue(), l, valueTimestampTimeZone.getTimeZoneOffsetSeconds());
                return ValueDate.fromDateValue(DateTimeUtils.dateValueFromLocalSeconds(l2 + (long)castDataProvider.currentTimeZone().getTimeZoneOffsetUTC(l2)));
            }
            case 1: 
            case 2: 
            case 4: {
                return ValueDate.parse(this.getString().trim());
            }
            default: {
                throw this.getDataConversionError(17);
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    private ValueTime convertToTime(TypeInfo typeInfo, CastDataProvider castDataProvider, int n) {
        int n2;
        long l;
        long l2;
        ValueTime valueTime;
        switch (this.getValueType()) {
            case 18: {
                valueTime = (ValueTime)this;
                break;
            }
            case 19: {
                valueTime = ValueTime.fromNanos(this.getLocalTimeNanos(castDataProvider));
                break;
            }
            case 20: {
                valueTime = ValueTime.fromNanos(((ValueTimestamp)this).getTimeNanos());
                break;
            }
            case 21: {
                ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)this;
                l2 = valueTimestampTimeZone.getTimeNanos();
                l = DateTimeUtils.getEpochSeconds(valueTimestampTimeZone.getDateValue(), l2, valueTimestampTimeZone.getTimeZoneOffsetSeconds());
                valueTime = ValueTime.fromNanos(DateTimeUtils.nanosFromLocalSeconds(l + (long)castDataProvider.currentTimeZone().getTimeZoneOffsetUTC(l)) + l2 % 1000000000L);
                break;
            }
            case 1: 
            case 2: 
            case 4: {
                valueTime = ValueTime.parse(this.getString().trim(), castDataProvider);
                break;
            }
            default: {
                throw this.getDataConversionError(18);
            }
        }
        if (n != 0 && (n2 = typeInfo.getScale()) < 9 && (l = DateTimeUtils.convertScale(l2 = valueTime.getNanos(), n2, 86400000000000L)) != l2) {
            valueTime = ValueTime.fromNanos(l);
        }
        return valueTime;
    }

    private ValueTimeTimeZone convertToTimeTimeZone(TypeInfo typeInfo, CastDataProvider castDataProvider, int n) {
        long l;
        int n2;
        long l2;
        ValueTimeTimeZone valueTimeTimeZone;
        switch (this.getValueType()) {
            case 19: {
                valueTimeTimeZone = (ValueTimeTimeZone)this;
                break;
            }
            case 18: {
                valueTimeTimeZone = ValueTimeTimeZone.fromNanos(((ValueTime)this).getNanos(), castDataProvider.currentTimestamp().getTimeZoneOffsetSeconds());
                break;
            }
            case 20: {
                Value value = (ValueTimestamp)this;
                l2 = ((ValueTimestamp)value).getTimeNanos();
                valueTimeTimeZone = ValueTimeTimeZone.fromNanos(l2, castDataProvider.currentTimeZone().getTimeZoneOffsetLocal(((ValueTimestamp)value).getDateValue(), l2));
                break;
            }
            case 21: {
                Value value = (ValueTimestampTimeZone)this;
                valueTimeTimeZone = ValueTimeTimeZone.fromNanos(((ValueTimestampTimeZone)value).getTimeNanos(), ((ValueTimestampTimeZone)value).getTimeZoneOffsetSeconds());
                break;
            }
            case 1: 
            case 2: 
            case 4: {
                valueTimeTimeZone = ValueTimeTimeZone.parse(this.getString().trim(), castDataProvider);
                break;
            }
            default: {
                throw this.getDataConversionError(19);
            }
        }
        if (n != 0 && (n2 = typeInfo.getScale()) < 9 && (l = DateTimeUtils.convertScale(l2 = valueTimeTimeZone.getNanos(), n2, 86400000000000L)) != l2) {
            valueTimeTimeZone = ValueTimeTimeZone.fromNanos(l, valueTimeTimeZone.getTimeZoneOffsetSeconds());
        }
        return valueTimeTimeZone;
    }

    private ValueTimestamp convertToTimestamp(TypeInfo typeInfo, CastDataProvider castDataProvider, int n) {
        int n2;
        long l;
        long l2;
        ValueTimestamp valueTimestamp;
        switch (this.getValueType()) {
            case 20: {
                valueTimestamp = (ValueTimestamp)this;
                break;
            }
            case 18: {
                valueTimestamp = ValueTimestamp.fromDateValueAndNanos(castDataProvider.currentTimestamp().getDateValue(), ((ValueTime)this).getNanos());
                break;
            }
            case 19: {
                valueTimestamp = ValueTimestamp.fromDateValueAndNanos(castDataProvider.currentTimestamp().getDateValue(), this.getLocalTimeNanos(castDataProvider));
                break;
            }
            case 17: {
                return ValueTimestamp.fromDateValueAndNanos(((ValueDate)this).getDateValue(), 0L);
            }
            case 21: {
                ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)this;
                l2 = valueTimestampTimeZone.getTimeNanos();
                l = DateTimeUtils.getEpochSeconds(valueTimestampTimeZone.getDateValue(), l2, valueTimestampTimeZone.getTimeZoneOffsetSeconds());
                l += (long)castDataProvider.currentTimeZone().getTimeZoneOffsetUTC(l);
                valueTimestamp = ValueTimestamp.fromDateValueAndNanos(DateTimeUtils.dateValueFromLocalSeconds(l), DateTimeUtils.nanosFromLocalSeconds(l) + l2 % 1000000000L);
                break;
            }
            case 1: 
            case 2: 
            case 4: {
                valueTimestamp = ValueTimestamp.parse(this.getString().trim(), castDataProvider);
                break;
            }
            default: {
                throw this.getDataConversionError(20);
            }
        }
        if (n != 0 && (n2 = typeInfo.getScale()) < 9) {
            l2 = valueTimestamp.getDateValue();
            l = valueTimestamp.getTimeNanos();
            long l3 = DateTimeUtils.convertScale(l, n2, l2 == 512000000415L ? 86400000000000L : Long.MAX_VALUE);
            if (l3 != l) {
                if (l3 >= 86400000000000L) {
                    l3 -= 86400000000000L;
                    l2 = DateTimeUtils.incrementDateValue(l2);
                }
                valueTimestamp = ValueTimestamp.fromDateValueAndNanos(l2, l3);
            }
        }
        return valueTimestamp;
    }

    private long getLocalTimeNanos(CastDataProvider castDataProvider) {
        ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)this;
        int n = castDataProvider.currentTimestamp().getTimeZoneOffsetSeconds();
        return DateTimeUtils.normalizeNanosOfDay(valueTimeTimeZone.getNanos() + (long)(n - valueTimeTimeZone.getTimeZoneOffsetSeconds()) * 1000000000L);
    }

    private ValueTimestampTimeZone convertToTimestampTimeZone(TypeInfo typeInfo, CastDataProvider castDataProvider, int n) {
        int n2;
        long l;
        long l2;
        ValueTimestampTimeZone valueTimestampTimeZone;
        switch (this.getValueType()) {
            case 21: {
                valueTimestampTimeZone = (ValueTimestampTimeZone)this;
                break;
            }
            case 18: {
                long l3 = castDataProvider.currentTimestamp().getDateValue();
                long l4 = ((ValueTime)this).getNanos();
                valueTimestampTimeZone = ValueTimestampTimeZone.fromDateValueAndNanos(l3, l4, castDataProvider.currentTimeZone().getTimeZoneOffsetLocal(l3, l4));
                break;
            }
            case 19: {
                ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)this;
                valueTimestampTimeZone = ValueTimestampTimeZone.fromDateValueAndNanos(castDataProvider.currentTimestamp().getDateValue(), valueTimeTimeZone.getNanos(), valueTimeTimeZone.getTimeZoneOffsetSeconds());
                break;
            }
            case 17: {
                long l5 = ((ValueDate)this).getDateValue();
                return ValueTimestampTimeZone.fromDateValueAndNanos(l5, 0L, castDataProvider.currentTimeZone().getTimeZoneOffsetLocal(l5, 0L));
            }
            case 20: {
                ValueTimestamp valueTimestamp = (ValueTimestamp)this;
                l2 = valueTimestamp.getDateValue();
                l = valueTimestamp.getTimeNanos();
                valueTimestampTimeZone = ValueTimestampTimeZone.fromDateValueAndNanos(l2, l, castDataProvider.currentTimeZone().getTimeZoneOffsetLocal(l2, l));
                break;
            }
            case 1: 
            case 2: 
            case 4: {
                valueTimestampTimeZone = ValueTimestampTimeZone.parse(this.getString().trim(), castDataProvider);
                break;
            }
            default: {
                throw this.getDataConversionError(21);
            }
        }
        if (n != 0 && (n2 = typeInfo.getScale()) < 9) {
            l2 = valueTimestampTimeZone.getDateValue();
            l = valueTimestampTimeZone.getTimeNanos();
            long l6 = DateTimeUtils.convertScale(l, n2, l2 == 512000000415L ? 86400000000000L : Long.MAX_VALUE);
            if (l6 != l) {
                if (l6 >= 86400000000000L) {
                    l6 -= 86400000000000L;
                    l2 = DateTimeUtils.incrementDateValue(l2);
                }
                valueTimestampTimeZone = ValueTimestampTimeZone.fromDateValueAndNanos(l2, l6, valueTimestampTimeZone.getTimeZoneOffsetSeconds());
            }
        }
        return valueTimestampTimeZone;
    }

    private ValueInterval convertToIntervalYearMonth(TypeInfo typeInfo, int n, Object object) {
        ValueInterval valueInterval = this.convertToIntervalYearMonth(typeInfo.getValueType(), object);
        if (n != 0 && !valueInterval.checkPrecision(typeInfo.getPrecision())) {
            throw valueInterval.getValueTooLongException(typeInfo, object);
        }
        return valueInterval;
    }

    private ValueInterval convertToIntervalYearMonth(int n, Object object) {
        long l;
        switch (this.getValueType()) {
            case 9: 
            case 10: 
            case 11: {
                l = this.getInt();
                break;
            }
            case 12: {
                l = this.getLong();
                break;
            }
            case 14: 
            case 15: {
                if (n == 28) {
                    return IntervalUtils.intervalFromAbsolute(IntervalQualifier.YEAR_TO_MONTH, this.getBigDecimal().multiply(BigDecimal.valueOf(12L)).setScale(0, RoundingMode.HALF_UP).toBigInteger());
                }
                l = Value.convertToLong(this.getDouble(), object);
                break;
            }
            case 13: 
            case 16: {
                if (n == 28) {
                    return IntervalUtils.intervalFromAbsolute(IntervalQualifier.YEAR_TO_MONTH, this.getBigDecimal().multiply(BigDecimal.valueOf(12L)).setScale(0, RoundingMode.HALF_UP).toBigInteger());
                }
                l = Value.convertToLong(this.getBigDecimal(), object);
                break;
            }
            case 1: 
            case 2: 
            case 4: {
                String string = this.getString();
                try {
                    return (ValueInterval)IntervalUtils.parseFormattedInterval(IntervalQualifier.valueOf(n - 22), string).convertTo(n);
                }
                catch (Exception exception) {
                    throw DbException.get(22007, exception, "INTERVAL", string);
                }
            }
            case 22: 
            case 23: 
            case 28: {
                return IntervalUtils.intervalFromAbsolute(IntervalQualifier.valueOf(n - 22), IntervalUtils.intervalToAbsolute((ValueInterval)this));
            }
            default: {
                throw this.getDataConversionError(n);
            }
        }
        boolean bl = false;
        if (l < 0L) {
            bl = true;
            l = -l;
        }
        return ValueInterval.from(IntervalQualifier.valueOf(n - 22), bl, l, 0L);
    }

    private ValueInterval convertToIntervalDayTime(TypeInfo typeInfo, int n, Object object) {
        ValueInterval valueInterval = this.convertToIntervalDayTime(typeInfo.getValueType(), object);
        if (n != 0) {
            valueInterval = valueInterval.setPrecisionAndScale(typeInfo, object);
        }
        return valueInterval;
    }

    private ValueInterval convertToIntervalDayTime(int n, Object object) {
        long l;
        switch (this.getValueType()) {
            case 9: 
            case 10: 
            case 11: {
                l = this.getInt();
                break;
            }
            case 12: {
                l = this.getLong();
                break;
            }
            case 14: 
            case 15: {
                if (n > 26) {
                    return this.convertToIntervalDayTime(this.getBigDecimal(), n);
                }
                l = Value.convertToLong(this.getDouble(), object);
                break;
            }
            case 13: 
            case 16: {
                if (n > 26) {
                    return this.convertToIntervalDayTime(this.getBigDecimal(), n);
                }
                l = Value.convertToLong(this.getBigDecimal(), object);
                break;
            }
            case 1: 
            case 2: 
            case 4: {
                String string = this.getString();
                try {
                    return (ValueInterval)IntervalUtils.parseFormattedInterval(IntervalQualifier.valueOf(n - 22), string).convertTo(n);
                }
                catch (Exception exception) {
                    throw DbException.get(22007, exception, "INTERVAL", string);
                }
            }
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: {
                return IntervalUtils.intervalFromAbsolute(IntervalQualifier.valueOf(n - 22), IntervalUtils.intervalToAbsolute((ValueInterval)this));
            }
            default: {
                throw this.getDataConversionError(n);
            }
        }
        boolean bl = false;
        if (l < 0L) {
            bl = true;
            l = -l;
        }
        return ValueInterval.from(IntervalQualifier.valueOf(n - 22), bl, l, 0L);
    }

    private ValueInterval convertToIntervalDayTime(BigDecimal bigDecimal, int n) {
        long l;
        switch (n) {
            case 27: {
                l = 1000000000L;
                break;
            }
            case 29: 
            case 30: 
            case 31: {
                l = 86400000000000L;
                break;
            }
            case 32: 
            case 33: {
                l = 3600000000000L;
                break;
            }
            case 34: {
                l = 60000000000L;
                break;
            }
            default: {
                throw this.getDataConversionError(n);
            }
        }
        return IntervalUtils.intervalFromAbsolute(IntervalQualifier.valueOf(n - 22), bigDecimal.multiply(BigDecimal.valueOf(l)).setScale(0, RoundingMode.HALF_UP).toBigInteger());
    }

    public final ValueJavaObject convertToJavaObject(TypeInfo typeInfo, int n, Object object) {
        ValueJavaObject valueJavaObject;
        switch (this.getValueType()) {
            case 35: {
                valueJavaObject = (ValueJavaObject)this;
                break;
            }
            case 5: 
            case 6: 
            case 7: {
                valueJavaObject = ValueJavaObject.getNoCopy(this.getBytesNoCopy());
                break;
            }
            default: {
                throw this.getDataConversionError(35);
            }
            case 0: {
                throw DbException.getInternalError();
            }
        }
        if (n != 0 && (long)valueJavaObject.getBytesNoCopy().length > typeInfo.getPrecision()) {
            throw valueJavaObject.getValueTooLongException(typeInfo, object);
        }
        return valueJavaObject;
    }

    public final ValueEnum convertToEnum(ExtTypeInfoEnum extTypeInfoEnum, CastDataProvider castDataProvider) {
        switch (this.getValueType()) {
            case 36: {
                ValueEnum valueEnum = (ValueEnum)this;
                if (extTypeInfoEnum.equals(valueEnum.getEnumerators())) {
                    return valueEnum;
                }
                return extTypeInfoEnum.getValue(valueEnum.getString(), castDataProvider);
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 16: {
                return extTypeInfoEnum.getValue(this.getInt(), castDataProvider);
            }
            case 1: 
            case 2: 
            case 4: {
                return extTypeInfoEnum.getValue(this.getString(), castDataProvider);
            }
            default: {
                throw this.getDataConversionError(36);
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    public final ValueGeometry convertToGeometry(ExtTypeInfoGeometry extTypeInfoGeometry) {
        Integer n;
        int n2;
        ValueGeometry valueGeometry;
        switch (this.getValueType()) {
            case 37: {
                valueGeometry = (ValueGeometry)this;
                break;
            }
            case 5: 
            case 6: 
            case 7: {
                valueGeometry = ValueGeometry.getFromEWKB(this.getBytesNoCopy());
                break;
            }
            case 38: {
                n2 = 0;
                if (extTypeInfoGeometry != null && (n = extTypeInfoGeometry.getSrid()) != null) {
                    n2 = n;
                }
                try {
                    valueGeometry = ValueGeometry.get(GeoJsonUtils.geoJsonToEwkb(this.getBytesNoCopy(), n2));
                    break;
                }
                catch (RuntimeException runtimeException) {
                    throw DbException.get(22018, this.getTraceSQL());
                }
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                valueGeometry = ValueGeometry.get(this.getString());
                break;
            }
            default: {
                throw this.getDataConversionError(37);
            }
            case 0: {
                throw DbException.getInternalError();
            }
        }
        if (extTypeInfoGeometry != null) {
            n2 = extTypeInfoGeometry.getType();
            n = extTypeInfoGeometry.getSrid();
            if (n2 != 0 && valueGeometry.getTypeAndDimensionSystem() != n2 || n != null && valueGeometry.getSRID() != n.intValue()) {
                StringBuilder stringBuilder = ExtTypeInfoGeometry.toSQL(new StringBuilder(), valueGeometry.getTypeAndDimensionSystem(), valueGeometry.getSRID()).append(" -> ");
                extTypeInfoGeometry.getSQL(stringBuilder, 3);
                throw DbException.get(22018, stringBuilder.toString());
            }
        }
        return valueGeometry;
    }

    private ValueJson convertToJson(TypeInfo typeInfo, int n, Object object) {
        ValueJson valueJson;
        switch (this.getValueType()) {
            case 38: {
                valueJson = (ValueJson)this;
                break;
            }
            case 8: {
                valueJson = ValueJson.get(this.getBoolean());
                break;
            }
            case 9: 
            case 10: 
            case 11: {
                valueJson = ValueJson.get(this.getInt());
                break;
            }
            case 12: {
                valueJson = ValueJson.get(this.getLong());
                break;
            }
            case 13: 
            case 14: 
            case 15: 
            case 16: {
                valueJson = ValueJson.get(this.getBigDecimal());
                break;
            }
            case 5: 
            case 6: 
            case 7: {
                valueJson = ValueJson.fromJson(this.getBytesNoCopy());
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 17: 
            case 18: 
            case 19: 
            case 36: 
            case 39: {
                valueJson = ValueJson.get(this.getString());
                break;
            }
            case 20: {
                valueJson = ValueJson.get(((ValueTimestamp)this).getISOString());
                break;
            }
            case 21: {
                valueJson = ValueJson.get(((ValueTimestampTimeZone)this).getISOString());
                break;
            }
            case 37: {
                ValueGeometry valueGeometry = (ValueGeometry)this;
                valueJson = ValueJson.getInternal(GeoJsonUtils.ewkbToGeoJson(valueGeometry.getBytesNoCopy(), valueGeometry.getDimensionSystem()));
                break;
            }
            case 40: {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(91);
                for (Value value : ((ValueArray)this).getList()) {
                    JsonConstructorUtils.jsonArrayAppend(byteArrayOutputStream, value, 0);
                }
                byteArrayOutputStream.write(93);
                valueJson = ValueJson.getInternal(byteArrayOutputStream.toByteArray());
                break;
            }
            default: {
                throw this.getDataConversionError(38);
            }
        }
        if (n != 0 && (long)valueJson.getBytesNoCopy().length > typeInfo.getPrecision()) {
            throw valueJson.getValueTooLongException(typeInfo, object);
        }
        return valueJson;
    }

    public final ValueUuid convertToUuid() {
        switch (this.getValueType()) {
            case 39: {
                return (ValueUuid)this;
            }
            case 5: 
            case 6: {
                return ValueUuid.get(this.getBytesNoCopy());
            }
            case 35: {
                return JdbcUtils.deserializeUuid(this.getBytesNoCopy());
            }
            case 1: 
            case 2: 
            case 4: {
                return ValueUuid.get(this.getString());
            }
            default: {
                throw this.getDataConversionError(39);
            }
            case 0: 
        }
        throw DbException.getInternalError();
    }

    private ValueArray convertToArray(TypeInfo typeInfo, CastDataProvider castDataProvider, int n, Object object) {
        int n2;
        int n3;
        Value[] valueArray;
        ValueArray valueArray2;
        TypeInfo typeInfo2 = (TypeInfo)typeInfo.getExtTypeInfo();
        int n4 = this.getValueType();
        if (n4 == 40) {
            valueArray2 = (ValueArray)this;
        } else {
            switch (n4) {
                case 7: {
                    valueArray = new Value[]{ValueVarbinary.get(this.getBytesNoCopy())};
                    break;
                }
                case 3: {
                    valueArray = new Value[]{ValueVarchar.get(this.getString())};
                    break;
                }
                default: {
                    valueArray = new Value[]{this};
                }
            }
            valueArray2 = ValueArray.get(valueArray, castDataProvider);
        }
        if (typeInfo2 != null) {
            valueArray = valueArray2.getList();
            n3 = valueArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                Value value = valueArray[n2];
                Value value2 = value.convertTo(typeInfo2, castDataProvider, n, object);
                if (value == value2) continue;
                Value[] valueArray3 = new Value[n3];
                System.arraycopy(valueArray, 0, valueArray3, 0, n2);
                valueArray3[n2] = value2;
                while (++n2 < n3) {
                    valueArray3[n2] = valueArray[n2].convertTo(typeInfo2, castDataProvider, n, object);
                }
                valueArray2 = ValueArray.get(typeInfo2, valueArray3, castDataProvider);
                break;
            }
        }
        if (n != 0) {
            valueArray = valueArray2.getList();
            n3 = valueArray.length;
            if (n == 1) {
                n2 = MathUtils.convertLongToInt(typeInfo.getPrecision());
                if (n3 > n2) {
                    valueArray2 = ValueArray.get(valueArray2.getComponentType(), Arrays.copyOf(valueArray, n2), castDataProvider);
                }
            } else if ((long)n3 > typeInfo.getPrecision()) {
                throw valueArray2.getValueTooLongException(typeInfo, object);
            }
        }
        return valueArray2;
    }

    private Value convertToRow(TypeInfo typeInfo, CastDataProvider castDataProvider, int n, Object object) {
        ValueRow valueRow = this.getValueType() == 41 ? (ValueRow)this : ValueRow.get(new Value[]{this});
        ExtTypeInfoRow extTypeInfoRow = (ExtTypeInfoRow)typeInfo.getExtTypeInfo();
        if (extTypeInfoRow != null) {
            Set<Map.Entry<String, TypeInfo>> set;
            Value[] valueArray = valueRow.getList();
            int n2 = valueArray.length;
            if (n2 != (set = extTypeInfoRow.getFields()).size()) {
                throw this.getDataConversionError(typeInfo);
            }
            Iterator<Map.Entry<String, TypeInfo>> iterator2 = set.iterator();
            for (int i = 0; i < n2; ++i) {
                Value value = valueArray[i];
                TypeInfo typeInfo2 = iterator2.next().getValue();
                Value value2 = value.convertTo(typeInfo2, castDataProvider, n, object);
                if (value == value2) continue;
                Value[] valueArray2 = new Value[n2];
                System.arraycopy(valueArray, 0, valueArray2, 0, i);
                valueArray2[i] = value2;
                while (++i < n2) {
                    valueArray2[i] = valueArray[i].convertTo(typeInfo2, castDataProvider, n, object);
                }
                valueRow = ValueRow.get(typeInfo, valueArray2);
                break;
            }
        }
        return valueRow;
    }

    final DbException getDataConversionError(int n) {
        throw DbException.get(22018, Value.getTypeName(this.getValueType()) + " to " + Value.getTypeName(n));
    }

    final DbException getDataConversionError(TypeInfo typeInfo) {
        throw DbException.get(22018, Value.getTypeName(this.getValueType()) + " to " + typeInfo.getTraceSQL());
    }

    final DbException getValueTooLongException(TypeInfo typeInfo, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        if (object != null) {
            stringBuilder.append(object).append(' ');
        }
        typeInfo.getSQL(stringBuilder, 3);
        return DbException.getValueTooLongException(stringBuilder.toString(), this.getTraceSQL(), this.getType().getPrecision());
    }

    public abstract int compareTypeSafe(Value var1, CompareMode var2, CastDataProvider var3);

    public final int compareTo(Value value, CastDataProvider castDataProvider, CompareMode compareMode) {
        if (this == value) {
            return 0;
        }
        if (this == ValueNull.INSTANCE) {
            return -1;
        }
        if (value == ValueNull.INSTANCE) {
            return 1;
        }
        return Value.compareToNotNullable(this, value, castDataProvider, compareMode);
    }

    private static int compareToNotNullable(Value value, Value value2, CastDataProvider castDataProvider, CompareMode compareMode) {
        int n;
        int n2 = value.getValueType();
        if (n2 != (n = value2.getValueType()) || n2 == 36) {
            int n3 = Value.getHigherOrderNonNull(n2, n);
            if (DataType.isNumericType(n3)) {
                return Value.compareNumeric(value, value2, n2, n, n3);
            }
            if (n3 == 36) {
                ExtTypeInfoEnum extTypeInfoEnum = ExtTypeInfoEnum.getEnumeratorsForBinaryOperation(value, value2);
                return Integer.compare(value.convertToEnum(extTypeInfoEnum, castDataProvider).getInt(), value2.convertToEnum(extTypeInfoEnum, castDataProvider).getInt());
            }
            if (n3 <= 7) {
                if (n3 <= 3) {
                    if (n2 == 1 || n == 1) {
                        n3 = 1;
                    }
                } else if (n3 >= 5 && (n2 == 5 || n == 5)) {
                    n3 = 5;
                }
            }
            value = value.convertTo(n3, castDataProvider);
            value2 = value2.convertTo(n3, castDataProvider);
        }
        return value.compareTypeSafe(value2, compareMode, castDataProvider);
    }

    private static int compareNumeric(Value value, Value value2, int n, int n2, int n3) {
        if (DataType.isNumericType(n) && DataType.isNumericType(n2)) {
            switch (n3) {
                case 9: 
                case 10: 
                case 11: {
                    return Integer.compare(value.getInt(), value2.getInt());
                }
                case 12: {
                    return Long.compare(value.getLong(), value2.getLong());
                }
                case 13: {
                    return value.getBigDecimal().compareTo(value2.getBigDecimal());
                }
                case 14: {
                    return Float.compare(value.getFloat(), value2.getFloat());
                }
                case 15: {
                    return Double.compare(value.getDouble(), value2.getDouble());
                }
            }
        }
        return value.convertToDecfloat(null, 0).compareTypeSafe(value2.convertToDecfloat(null, 0), null, null);
    }

    public int compareWithNull(Value value, boolean bl, CastDataProvider castDataProvider, CompareMode compareMode) {
        if (this == ValueNull.INSTANCE || value == ValueNull.INSTANCE) {
            return Integer.MIN_VALUE;
        }
        return Value.compareToNotNullable(this, value, castDataProvider, compareMode);
    }

    public boolean containsNull() {
        return false;
    }

    public Value getValueWithFirstNull(Value value) {
        return this == ValueNull.INSTANCE ? (value == ValueNull.INSTANCE ? null : ValueNull.INSTANCE) : (value == ValueNull.INSTANCE ? ValueNull.INSTANCE : this.getValueWithFirstNullImpl(value));
    }

    Value getValueWithFirstNullImpl(Value value) {
        return this;
    }

    private static byte convertToByte(long l, Object object) {
        if (l > 127L || l < -128L) {
            throw DbException.get(22004, Long.toString(l), Value.getColumnName(object));
        }
        return (byte)l;
    }

    private static short convertToShort(long l, Object object) {
        if (l > 32767L || l < -32768L) {
            throw DbException.get(22004, Long.toString(l), Value.getColumnName(object));
        }
        return (short)l;
    }

    public static int convertToInt(long l, Object object) {
        if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
            throw DbException.get(22004, Long.toString(l), Value.getColumnName(object));
        }
        return (int)l;
    }

    private static long convertToLong(double d, Object object) {
        if (d > 9.223372036854776E18 || d < -9.223372036854776E18) {
            throw DbException.get(22004, Double.toString(d), Value.getColumnName(object));
        }
        return Math.round(d);
    }

    private static long convertToLong(BigDecimal bigDecimal, Object object) {
        if (bigDecimal.compareTo(MAX_LONG_DECIMAL) > 0 || bigDecimal.compareTo(MIN_LONG_DECIMAL) < 0) {
            throw DbException.get(22004, bigDecimal.toString(), Value.getColumnName(object));
        }
        return bigDecimal.setScale(0, RoundingMode.HALF_UP).longValue();
    }

    private static String getColumnName(Object object) {
        return object == null ? "" : object.toString();
    }

    public String toString() {
        return this.getTraceSQL();
    }

    protected final DbException getUnsupportedExceptionForOperation(String string) {
        return DbException.getUnsupportedException(Value.getTypeName(this.getValueType()) + ' ' + string);
    }

    public long charLength() {
        return this.getString().length();
    }

    public long octetLength() {
        return this.getBytesNoCopy().length;
    }

    public final boolean isTrue() {
        return this != ValueNull.INSTANCE ? this.getBoolean() : false;
    }

    public final boolean isFalse() {
        return this != ValueNull.INSTANCE && !this.getBoolean();
    }

    static {
        MAX_LONG_DECIMAL = BigDecimal.valueOf(Long.MAX_VALUE);
        MIN_LONG_DECIMAL = BigDecimal.valueOf(Long.MIN_VALUE);
    }
}

