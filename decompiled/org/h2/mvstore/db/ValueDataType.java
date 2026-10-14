/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.h2.api.IntervalQualifier;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Database;
import org.h2.message.DbException;
import org.h2.mode.DefaultNullOrdering;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.BasicDataType;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.MetaType;
import org.h2.mvstore.type.StatefulDataType;
import org.h2.result.RowFactory;
import org.h2.result.SearchRow;
import org.h2.store.DataHandler;
import org.h2.util.Utils;
import org.h2.value.CompareMode;
import org.h2.value.ExtTypeInfoEnum;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBinary;
import org.h2.value.ValueBlob;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueChar;
import org.h2.value.ValueClob;
import org.h2.value.ValueCollectionBase;
import org.h2.value.ValueDate;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueDouble;
import org.h2.value.ValueGeometry;
import org.h2.value.ValueInteger;
import org.h2.value.ValueInterval;
import org.h2.value.ValueJavaObject;
import org.h2.value.ValueJson;
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
import org.h2.value.lob.LobData;
import org.h2.value.lob.LobDataDatabase;
import org.h2.value.lob.LobDataInMemory;

public final class ValueDataType
extends BasicDataType<Value>
implements StatefulDataType<Database> {
    private static final byte NULL = 0;
    private static final byte TINYINT = 2;
    private static final byte SMALLINT = 3;
    private static final byte INTEGER = 4;
    private static final byte BIGINT = 5;
    private static final byte NUMERIC = 6;
    private static final byte DOUBLE = 7;
    private static final byte REAL = 8;
    private static final byte TIME = 9;
    private static final byte DATE = 10;
    private static final byte TIMESTAMP = 11;
    private static final byte VARBINARY = 12;
    private static final byte VARCHAR = 13;
    private static final byte VARCHAR_IGNORECASE = 14;
    private static final byte BLOB = 15;
    private static final byte CLOB = 16;
    private static final byte ARRAY = 17;
    private static final byte JAVA_OBJECT = 19;
    private static final byte UUID = 20;
    private static final byte CHAR = 21;
    private static final byte GEOMETRY = 22;
    private static final byte TIMESTAMP_TZ_OLD = 24;
    private static final byte ENUM = 25;
    private static final byte INTERVAL = 26;
    private static final byte ROW = 27;
    private static final byte INT_0_15 = 32;
    private static final byte BIGINT_0_7 = 48;
    private static final byte NUMERIC_0_1 = 56;
    private static final byte NUMERIC_SMALL_0 = 58;
    private static final byte NUMERIC_SMALL = 59;
    private static final byte DOUBLE_0_1 = 60;
    private static final byte REAL_0_1 = 62;
    private static final byte BOOLEAN_FALSE = 64;
    private static final byte BOOLEAN_TRUE = 65;
    private static final byte INT_NEG = 66;
    private static final byte BIGINT_NEG = 67;
    private static final byte VARCHAR_0_31 = 68;
    private static final int VARBINARY_0_31 = 100;
    private static final int JSON = 134;
    private static final int TIMESTAMP_TZ = 135;
    private static final int TIME_TZ = 136;
    private static final int BINARY = 137;
    private static final int DECFLOAT = 138;
    final DataHandler handler;
    final CastDataProvider provider;
    final CompareMode compareMode;
    final int[] sortTypes;
    private RowFactory rowFactory;
    private static final Factory FACTORY = new Factory();

    public ValueDataType() {
        this(null, CompareMode.getInstance(null, 0), null, null);
    }

    public ValueDataType(Database database, int[] nArray) {
        this(database, database.getCompareMode(), database, nArray);
    }

    public ValueDataType(CastDataProvider castDataProvider, CompareMode compareMode, DataHandler dataHandler, int[] nArray) {
        this.provider = castDataProvider;
        this.compareMode = compareMode;
        this.handler = dataHandler;
        this.sortTypes = nArray;
    }

    public RowFactory getRowFactory() {
        return this.rowFactory;
    }

    public void setRowFactory(RowFactory rowFactory) {
        this.rowFactory = rowFactory;
    }

    public Value[] createStorage(int n) {
        return new Value[n];
    }

    @Override
    public int compare(Value value, Value value2) {
        if (value == value2) {
            return 0;
        }
        if (value instanceof SearchRow && value2 instanceof SearchRow) {
            return this.compare((SearchRow)value, (SearchRow)value2);
        }
        if (value instanceof ValueCollectionBase && value2 instanceof ValueCollectionBase) {
            Value[] valueArray = ((ValueCollectionBase)value).getList();
            Value[] valueArray2 = ((ValueCollectionBase)value2).getList();
            int n = valueArray.length;
            int n2 = valueArray2.length;
            int n3 = Math.min(n, n2);
            for (int i = 0; i < n3; ++i) {
                int n4 = this.sortTypes == null ? 0 : this.sortTypes[i];
                Value value3 = valueArray[i];
                Value value4 = valueArray2[i];
                if (value3 == null || value4 == null) {
                    return this.compareValues(valueArray[n3 - 1], valueArray2[n3 - 1], 0);
                }
                int n5 = this.compareValues(value3, value4, n4);
                if (n5 == 0) continue;
                return n5;
            }
            if (n3 < n) {
                return -1;
            }
            if (n3 < n2) {
                return 1;
            }
            return 0;
        }
        return this.compareValues(value, value2, 0);
    }

    @Override
    private int compare(SearchRow searchRow, SearchRow searchRow2) {
        if (searchRow == searchRow2) {
            return 0;
        }
        int[] nArray = this.rowFactory.getIndexes();
        if (nArray == null) {
            int n = searchRow.getColumnCount();
            assert (n == searchRow2.getColumnCount()) : n + " != " + searchRow2.getColumnCount();
            for (int i = 0; i < n; ++i) {
                int n2 = this.compareValues(searchRow.getValue(i), searchRow2.getValue(i), this.sortTypes[i]);
                if (n2 == 0) continue;
                return n2;
            }
            return 0;
        }
        assert (this.sortTypes.length == nArray.length);
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            Value value = searchRow.getValue(n);
            Value value2 = searchRow2.getValue(n);
            if (value == null || value2 == null) break;
            int n3 = this.compareValues(searchRow.getValue(n), searchRow2.getValue(n), this.sortTypes[i]);
            if (n3 == 0) continue;
            return n3;
        }
        long l = searchRow.getKey();
        long l2 = searchRow2.getKey();
        return l == SearchRow.MATCH_ALL_ROW_KEY || l2 == SearchRow.MATCH_ALL_ROW_KEY ? 0 : Long.compare(l, l2);
    }

    public int compareValues(Value value, Value value2, int n) {
        boolean bl;
        if (value == value2) {
            return 0;
        }
        boolean bl2 = bl = value == ValueNull.INSTANCE;
        if (bl || value2 == ValueNull.INSTANCE) {
            return DefaultNullOrdering.LOW.compareNull(bl, n);
        }
        int n2 = value.compareTo(value2, this.provider, this.compareMode);
        if ((n & 1) != 0) {
            n2 = -n2;
        }
        return n2;
    }

    @Override
    public int getMemory(Value value) {
        return value == null ? 0 : value.getMemory();
    }

    @Override
    public Value read(ByteBuffer byteBuffer) {
        return this.readValue(byteBuffer, null);
    }

    @Override
    public void write(WriteBuffer writeBuffer, Value value) {
        if (value == ValueNull.INSTANCE) {
            writeBuffer.put((byte)0);
            return;
        }
        int n = value.getValueType();
        switch (n) {
            case 8: {
                writeBuffer.put(value.getBoolean() ? (byte)65 : 64);
                break;
            }
            case 9: {
                writeBuffer.put((byte)2).put(value.getByte());
                break;
            }
            case 10: {
                writeBuffer.put((byte)3).putShort(value.getShort());
                break;
            }
            case 11: 
            case 36: {
                int n2 = value.getInt();
                if (n2 < 0) {
                    writeBuffer.put((byte)66).putVarInt(-n2);
                    break;
                }
                if (n2 < 16) {
                    writeBuffer.put((byte)(32 + n2));
                    break;
                }
                writeBuffer.put(n == 11 ? (byte)4 : 25).putVarInt(n2);
                break;
            }
            case 12: {
                ValueDataType.writeLong(writeBuffer, value.getLong());
                break;
            }
            case 13: {
                BigDecimal bigDecimal = value.getBigDecimal();
                if (BigDecimal.ZERO.equals(bigDecimal)) {
                    writeBuffer.put((byte)56);
                    break;
                }
                if (BigDecimal.ONE.equals(bigDecimal)) {
                    writeBuffer.put((byte)57);
                    break;
                }
                int n3 = bigDecimal.scale();
                BigInteger bigInteger = bigDecimal.unscaledValue();
                int n4 = bigInteger.bitLength();
                if (n4 <= 63) {
                    if (n3 == 0) {
                        writeBuffer.put((byte)58).putVarLong(bigInteger.longValue());
                        break;
                    }
                    writeBuffer.put((byte)59).putVarInt(n3).putVarLong(bigInteger.longValue());
                    break;
                }
                byte[] byArray = bigInteger.toByteArray();
                writeBuffer.put((byte)6).putVarInt(n3).putVarInt(byArray.length).put(byArray);
                break;
            }
            case 16: {
                ValueDecfloat valueDecfloat = (ValueDecfloat)value;
                writeBuffer.put((byte)-118);
                if (valueDecfloat.isFinite()) {
                    BigDecimal bigDecimal = valueDecfloat.getBigDecimal();
                    byte[] byArray = bigDecimal.unscaledValue().toByteArray();
                    writeBuffer.putVarInt(bigDecimal.scale()).putVarInt(byArray.length).put(byArray);
                    break;
                }
                int n5 = valueDecfloat == ValueDecfloat.NEGATIVE_INFINITY ? -3 : (valueDecfloat == ValueDecfloat.POSITIVE_INFINITY ? -2 : -1);
                writeBuffer.putVarInt(0).putVarInt(n5);
                break;
            }
            case 18: {
                ValueDataType.writeTimestampTime(writeBuffer.put((byte)9), ((ValueTime)value).getNanos());
                break;
            }
            case 19: {
                ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value;
                long l = valueTimeTimeZone.getNanos();
                writeBuffer.put((byte)-120).putVarInt((int)(l / 1000000000L)).putVarInt((int)(l % 1000000000L));
                ValueDataType.writeTimeZone(writeBuffer, valueTimeTimeZone.getTimeZoneOffsetSeconds());
                break;
            }
            case 17: {
                writeBuffer.put((byte)10).putVarLong(((ValueDate)value).getDateValue());
                break;
            }
            case 20: {
                ValueTimestamp valueTimestamp = (ValueTimestamp)value;
                writeBuffer.put((byte)11).putVarLong(valueTimestamp.getDateValue());
                ValueDataType.writeTimestampTime(writeBuffer, valueTimestamp.getTimeNanos());
                break;
            }
            case 21: {
                ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value;
                writeBuffer.put((byte)-121).putVarLong(valueTimestampTimeZone.getDateValue());
                ValueDataType.writeTimestampTime(writeBuffer, valueTimestampTimeZone.getTimeNanos());
                ValueDataType.writeTimeZone(writeBuffer, valueTimestampTimeZone.getTimeZoneOffsetSeconds());
                break;
            }
            case 35: {
                ValueDataType.writeBinary((byte)19, writeBuffer, value);
                break;
            }
            case 6: {
                byte[] byArray = value.getBytesNoCopy();
                int n6 = byArray.length;
                if (n6 < 32) {
                    writeBuffer.put((byte)(100 + n6)).put(byArray);
                    break;
                }
                writeBuffer.put((byte)12).putVarInt(n6).put(byArray);
                break;
            }
            case 5: {
                ValueDataType.writeBinary((byte)-119, writeBuffer, value);
                break;
            }
            case 39: {
                ValueUuid valueUuid = (ValueUuid)value;
                writeBuffer.put((byte)20).putLong(valueUuid.getHigh()).putLong(valueUuid.getLow());
                break;
            }
            case 2: {
                String string = value.getString();
                int n7 = string.length();
                if (n7 < 32) {
                    writeBuffer.put((byte)(68 + n7)).putStringData(string, n7);
                    break;
                }
                ValueDataType.writeString(writeBuffer.put((byte)13), string);
                break;
            }
            case 4: {
                ValueDataType.writeString(writeBuffer.put((byte)14), value.getString());
                break;
            }
            case 1: {
                ValueDataType.writeString(writeBuffer.put((byte)21), value.getString());
                break;
            }
            case 15: {
                double d = value.getDouble();
                if (d == 1.0) {
                    writeBuffer.put((byte)61);
                    break;
                }
                long l = Double.doubleToLongBits(d);
                if (l == 0L) {
                    writeBuffer.put((byte)60);
                    break;
                }
                writeBuffer.put((byte)7).putVarLong(Long.reverse(l));
                break;
            }
            case 14: {
                float f = value.getFloat();
                if (f == 1.0f) {
                    writeBuffer.put((byte)63);
                    break;
                }
                int n8 = Float.floatToIntBits(f);
                if (n8 == 0) {
                    writeBuffer.put((byte)62);
                    break;
                }
                writeBuffer.put((byte)8).putVarInt(Integer.reverse(n8));
                break;
            }
            case 7: {
                writeBuffer.put((byte)15);
                ValueBlob valueBlob = (ValueBlob)value;
                LobData lobData = valueBlob.getLobData();
                if (lobData instanceof LobDataDatabase) {
                    LobDataDatabase lobDataDatabase = (LobDataDatabase)lobData;
                    writeBuffer.putVarInt(-3).putVarInt(lobDataDatabase.getTableId()).putVarLong(lobDataDatabase.getLobId()).putVarLong(valueBlob.octetLength());
                    break;
                }
                byte[] byArray = ((LobDataInMemory)lobData).getSmall();
                writeBuffer.putVarInt(byArray.length).put(byArray);
                break;
            }
            case 3: {
                writeBuffer.put((byte)16);
                ValueClob valueClob = (ValueClob)value;
                LobData lobData = valueClob.getLobData();
                if (lobData instanceof LobDataDatabase) {
                    LobDataDatabase lobDataDatabase = (LobDataDatabase)lobData;
                    writeBuffer.putVarInt(-3).putVarInt(lobDataDatabase.getTableId()).putVarLong(lobDataDatabase.getLobId()).putVarLong(valueClob.octetLength()).putVarLong(valueClob.charLength());
                    break;
                }
                byte[] byArray = ((LobDataInMemory)lobData).getSmall();
                writeBuffer.putVarInt(byArray.length).put(byArray).putVarLong(valueClob.charLength());
                break;
            }
            case 40: 
            case 41: {
                Value[] valueArray = ((ValueCollectionBase)value).getList();
                writeBuffer.put(n == 40 ? (byte)17 : 27).putVarInt(valueArray.length);
                for (Value value2 : valueArray) {
                    this.write(writeBuffer, value2);
                }
                break;
            }
            case 37: {
                ValueDataType.writeBinary((byte)22, writeBuffer, value);
                break;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: {
                ValueInterval valueInterval = (ValueInterval)value;
                int n9 = n - 22;
                if (valueInterval.isNegative()) {
                    n9 ^= 0xFFFFFFFF;
                }
                writeBuffer.put((byte)26).put((byte)n9).putVarLong(valueInterval.getLeading());
                break;
            }
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: {
                ValueInterval valueInterval = (ValueInterval)value;
                int n10 = n - 22;
                if (valueInterval.isNegative()) {
                    n10 ^= 0xFFFFFFFF;
                }
                writeBuffer.put((byte)26).put((byte)n10).putVarLong(valueInterval.getLeading()).putVarLong(valueInterval.getRemaining());
                break;
            }
            case 38: {
                ValueDataType.writeBinary((byte)-122, writeBuffer, value);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + value.getValueType());
            }
        }
    }

    private static void writeBinary(byte by, WriteBuffer writeBuffer, Value value) {
        byte[] byArray = value.getBytesNoCopy();
        writeBuffer.put(by).putVarInt(byArray.length).put(byArray);
    }

    public static void writeLong(WriteBuffer writeBuffer, long l) {
        if (l < 0L) {
            writeBuffer.put((byte)67).putVarLong(-l);
        } else if (l < 8L) {
            writeBuffer.put((byte)(48L + l));
        } else {
            writeBuffer.put((byte)5).putVarLong(l);
        }
    }

    private static void writeString(WriteBuffer writeBuffer, String string) {
        int n = string.length();
        writeBuffer.putVarInt(n).putStringData(string, n);
    }

    private static void writeTimestampTime(WriteBuffer writeBuffer, long l) {
        long l2 = l / 1000000L;
        writeBuffer.putVarLong(l2).putVarInt((int)(l - l2 * 1000000L));
    }

    private static void writeTimeZone(WriteBuffer writeBuffer, int n) {
        if (n % 900 == 0) {
            writeBuffer.put((byte)(n / 900));
        } else if (n > 0) {
            writeBuffer.put((byte)127).putVarInt(n);
        } else {
            writeBuffer.put((byte)-128).putVarInt(-n);
        }
    }

    Value readValue(ByteBuffer byteBuffer, TypeInfo typeInfo) {
        int n = byteBuffer.get() & 0xFF;
        switch (n) {
            case 0: {
                return ValueNull.INSTANCE;
            }
            case 65: {
                return ValueBoolean.TRUE;
            }
            case 64: {
                return ValueBoolean.FALSE;
            }
            case 66: {
                return ValueInteger.get(-DataUtils.readVarInt(byteBuffer));
            }
            case 4: {
                return ValueInteger.get(DataUtils.readVarInt(byteBuffer));
            }
            case 67: {
                return ValueBigint.get(-DataUtils.readVarLong(byteBuffer));
            }
            case 5: {
                return ValueBigint.get(DataUtils.readVarLong(byteBuffer));
            }
            case 2: {
                return ValueTinyint.get(byteBuffer.get());
            }
            case 3: {
                return ValueSmallint.get(byteBuffer.getShort());
            }
            case 56: {
                return ValueNumeric.ZERO;
            }
            case 57: {
                return ValueNumeric.ONE;
            }
            case 58: {
                return ValueNumeric.get(BigDecimal.valueOf(DataUtils.readVarLong(byteBuffer)));
            }
            case 59: {
                int n2 = DataUtils.readVarInt(byteBuffer);
                return ValueNumeric.get(BigDecimal.valueOf(DataUtils.readVarLong(byteBuffer), n2));
            }
            case 6: {
                int n3 = DataUtils.readVarInt(byteBuffer);
                return ValueNumeric.get(new BigDecimal(new BigInteger(ValueDataType.readVarBytes(byteBuffer)), n3));
            }
            case 138: {
                int n4 = DataUtils.readVarInt(byteBuffer);
                int n5 = DataUtils.readVarInt(byteBuffer);
                switch (n5) {
                    case -3: {
                        return ValueDecfloat.NEGATIVE_INFINITY;
                    }
                    case -2: {
                        return ValueDecfloat.POSITIVE_INFINITY;
                    }
                    case -1: {
                        return ValueDecfloat.NAN;
                    }
                }
                byte[] byArray = Utils.newBytes(n5);
                byteBuffer.get(byArray, 0, n5);
                return ValueDecfloat.get(new BigDecimal(new BigInteger(byArray), n4));
            }
            case 10: {
                return ValueDate.fromDateValue(DataUtils.readVarLong(byteBuffer));
            }
            case 9: {
                return ValueTime.fromNanos(ValueDataType.readTimestampTime(byteBuffer));
            }
            case 136: {
                return ValueTimeTimeZone.fromNanos((long)DataUtils.readVarInt(byteBuffer) * 1000000000L + (long)DataUtils.readVarInt(byteBuffer), ValueDataType.readTimeZone(byteBuffer));
            }
            case 11: {
                return ValueTimestamp.fromDateValueAndNanos(DataUtils.readVarLong(byteBuffer), ValueDataType.readTimestampTime(byteBuffer));
            }
            case 24: {
                return ValueTimestampTimeZone.fromDateValueAndNanos(DataUtils.readVarLong(byteBuffer), ValueDataType.readTimestampTime(byteBuffer), DataUtils.readVarInt(byteBuffer) * 60);
            }
            case 135: {
                return ValueTimestampTimeZone.fromDateValueAndNanos(DataUtils.readVarLong(byteBuffer), ValueDataType.readTimestampTime(byteBuffer), ValueDataType.readTimeZone(byteBuffer));
            }
            case 12: {
                return ValueVarbinary.getNoCopy(ValueDataType.readVarBytes(byteBuffer));
            }
            case 137: {
                return ValueBinary.getNoCopy(ValueDataType.readVarBytes(byteBuffer));
            }
            case 19: {
                return ValueJavaObject.getNoCopy(ValueDataType.readVarBytes(byteBuffer));
            }
            case 20: {
                return ValueUuid.get(byteBuffer.getLong(), byteBuffer.getLong());
            }
            case 13: {
                return ValueVarchar.get(DataUtils.readString(byteBuffer));
            }
            case 14: {
                return ValueVarcharIgnoreCase.get(DataUtils.readString(byteBuffer));
            }
            case 21: {
                return ValueChar.get(DataUtils.readString(byteBuffer));
            }
            case 25: {
                int n6 = DataUtils.readVarInt(byteBuffer);
                if (typeInfo != null) {
                    return ((ExtTypeInfoEnum)typeInfo.getExtTypeInfo()).getValue(n6, this.provider);
                }
                return ValueInteger.get(n6);
            }
            case 26: {
                boolean bl;
                int n7 = byteBuffer.get();
                boolean bl2 = bl = n7 < 0;
                if (bl) {
                    n7 ^= 0xFFFFFFFF;
                }
                return ValueInterval.from(IntervalQualifier.valueOf(n7), bl, DataUtils.readVarLong(byteBuffer), n7 < 5 ? 0L : DataUtils.readVarLong(byteBuffer));
            }
            case 62: {
                return ValueReal.ZERO;
            }
            case 63: {
                return ValueReal.ONE;
            }
            case 60: {
                return ValueDouble.ZERO;
            }
            case 61: {
                return ValueDouble.ONE;
            }
            case 7: {
                return ValueDouble.get(Double.longBitsToDouble(Long.reverse(DataUtils.readVarLong(byteBuffer))));
            }
            case 8: {
                return ValueReal.get(Float.intBitsToFloat(Integer.reverse(DataUtils.readVarInt(byteBuffer))));
            }
            case 15: {
                int n8 = DataUtils.readVarInt(byteBuffer);
                if (n8 >= 0) {
                    byte[] byArray = Utils.newBytes(n8);
                    byteBuffer.get(byArray, 0, n8);
                    return ValueBlob.createSmall(byArray);
                }
                if (n8 == -3) {
                    return new ValueBlob(this.readLobDataDatabase(byteBuffer), DataUtils.readVarLong(byteBuffer));
                }
                throw DbException.get(90030, "lob type: " + n8);
            }
            case 16: {
                int n9 = DataUtils.readVarInt(byteBuffer);
                if (n9 >= 0) {
                    byte[] byArray = Utils.newBytes(n9);
                    byteBuffer.get(byArray, 0, n9);
                    return ValueClob.createSmall(byArray, DataUtils.readVarLong(byteBuffer));
                }
                if (n9 == -3) {
                    return new ValueClob(this.readLobDataDatabase(byteBuffer), DataUtils.readVarLong(byteBuffer), DataUtils.readVarLong(byteBuffer));
                }
                throw DbException.get(90030, "lob type: " + n9);
            }
            case 17: {
                if (typeInfo != null) {
                    TypeInfo typeInfo2 = (TypeInfo)typeInfo.getExtTypeInfo();
                    return ValueArray.get(typeInfo2, this.readArrayElements(byteBuffer, typeInfo2), this.provider);
                }
                return ValueArray.get(this.readArrayElements(byteBuffer, null), this.provider);
            }
            case 27: {
                int n10 = DataUtils.readVarInt(byteBuffer);
                Value[] valueArray = new Value[n10];
                if (typeInfo != null) {
                    ExtTypeInfoRow extTypeInfoRow = (ExtTypeInfoRow)typeInfo.getExtTypeInfo();
                    Iterator<Map.Entry<String, TypeInfo>> iterator2 = extTypeInfoRow.getFields().iterator();
                    for (int i = 0; i < n10; ++i) {
                        valueArray[i] = this.readValue(byteBuffer, iterator2.next().getValue());
                    }
                    return ValueRow.get(typeInfo, valueArray);
                }
                TypeInfo[] typeInfoArray = this.rowFactory.getColumnTypes();
                for (int i = 0; i < n10; ++i) {
                    valueArray[i] = this.readValue(byteBuffer, typeInfoArray[i]);
                }
                return ValueRow.get(valueArray);
            }
            case 22: {
                return ValueGeometry.get(ValueDataType.readVarBytes(byteBuffer));
            }
            case 134: {
                return ValueJson.getInternal(ValueDataType.readVarBytes(byteBuffer));
            }
        }
        if (n >= 32 && n < 48) {
            int n11 = n - 32;
            if (typeInfo != null && typeInfo.getValueType() == 36) {
                return ((ExtTypeInfoEnum)typeInfo.getExtTypeInfo()).getValue(n11, this.provider);
            }
            return ValueInteger.get(n11);
        }
        if (n >= 48 && n < 56) {
            return ValueBigint.get(n - 48);
        }
        if (n >= 100 && n < 132) {
            int n12 = n - 100;
            byte[] byArray = Utils.newBytes(n12);
            byteBuffer.get(byArray, 0, n12);
            return ValueVarbinary.getNoCopy(byArray);
        }
        if (n >= 68 && n < 100) {
            return ValueVarchar.get(DataUtils.readString(byteBuffer, n - 68));
        }
        throw DbException.get(90030, "type: " + n);
    }

    private LobDataDatabase readLobDataDatabase(ByteBuffer byteBuffer) {
        int n = DataUtils.readVarInt(byteBuffer);
        long l = DataUtils.readVarLong(byteBuffer);
        LobDataDatabase lobDataDatabase = new LobDataDatabase(this.handler, n, l);
        return lobDataDatabase;
    }

    private Value[] readArrayElements(ByteBuffer byteBuffer, TypeInfo typeInfo) {
        int n = DataUtils.readVarInt(byteBuffer);
        Value[] valueArray = new Value[n];
        for (int i = 0; i < n; ++i) {
            valueArray[i] = this.readValue(byteBuffer, typeInfo);
        }
        return valueArray;
    }

    private static byte[] readVarBytes(ByteBuffer byteBuffer) {
        int n = DataUtils.readVarInt(byteBuffer);
        byte[] byArray = Utils.newBytes(n);
        byteBuffer.get(byArray, 0, n);
        return byArray;
    }

    private static long readTimestampTime(ByteBuffer byteBuffer) {
        return DataUtils.readVarLong(byteBuffer) * 1000000L + (long)DataUtils.readVarInt(byteBuffer);
    }

    private static int readTimeZone(ByteBuffer byteBuffer) {
        byte by = byteBuffer.get();
        if (by == 127) {
            return DataUtils.readVarInt(byteBuffer);
        }
        if (by == -128) {
            return -DataUtils.readVarInt(byteBuffer);
        }
        return by * 900;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ValueDataType)) {
            return false;
        }
        ValueDataType valueDataType = (ValueDataType)object;
        if (!this.compareMode.equals(valueDataType.compareMode)) {
            return false;
        }
        int[] nArray = this.rowFactory == null ? null : this.rowFactory.getIndexes();
        int[] nArray2 = valueDataType.rowFactory == null ? null : valueDataType.rowFactory.getIndexes();
        return Arrays.equals(this.sortTypes, valueDataType.sortTypes) && Arrays.equals(nArray, nArray2);
    }

    @Override
    public int hashCode() {
        int[] nArray = this.rowFactory == null ? null : this.rowFactory.getIndexes();
        return super.hashCode() ^ Arrays.hashCode(nArray) ^ this.compareMode.hashCode() ^ Arrays.hashCode(this.sortTypes);
    }

    @Override
    public void save(WriteBuffer writeBuffer, MetaType<Database> metaType) {
        ValueDataType.writeIntArray(writeBuffer, this.sortTypes);
        int n = this.rowFactory == null ? 0 : this.rowFactory.getColumnCount();
        writeBuffer.putVarInt(n);
        int[] nArray = this.rowFactory == null ? null : this.rowFactory.getIndexes();
        ValueDataType.writeIntArray(writeBuffer, nArray);
        writeBuffer.put(this.rowFactory == null || this.rowFactory.getRowDataType().isStoreKeys() ? (byte)1 : 0);
    }

    private static void writeIntArray(WriteBuffer writeBuffer, int[] nArray) {
        if (nArray == null) {
            writeBuffer.putVarInt(0);
        } else {
            writeBuffer.putVarInt(nArray.length + 1);
            for (int n : nArray) {
                writeBuffer.putVarInt(n);
            }
        }
    }

    public Factory getFactory() {
        return FACTORY;
    }

    public static final class Factory
    implements StatefulDataType.Factory<Database> {
        @Override
        public DataType<?> create(ByteBuffer byteBuffer, MetaType<Database> metaType, Database database) {
            CompareMode compareMode;
            int[] nArray = Factory.readIntArray(byteBuffer);
            int n = DataUtils.readVarInt(byteBuffer);
            int[] nArray2 = Factory.readIntArray(byteBuffer);
            boolean bl = byteBuffer.get() != 0;
            CompareMode compareMode2 = compareMode = database == null ? CompareMode.getInstance(null, 0) : database.getCompareMode();
            if (database == null) {
                return new ValueDataType();
            }
            if (nArray == null) {
                return new ValueDataType(database, null);
            }
            RowFactory rowFactory = RowFactory.getDefaultRowFactory().createRowFactory(database, compareMode, database, nArray, nArray2, null, n, bl);
            return rowFactory.getRowDataType();
        }

        private static int[] readIntArray(ByteBuffer byteBuffer) {
            int n = DataUtils.readVarInt(byteBuffer) - 1;
            if (n < 0) {
                return null;
            }
            int[] nArray = new int[n];
            for (int i = 0; i < nArray.length; ++i) {
                nArray[i] = DataUtils.readVarInt(byteBuffer);
            }
            return nArray;
        }
    }
}

