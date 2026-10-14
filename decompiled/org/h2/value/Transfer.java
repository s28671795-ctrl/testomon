/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import org.h2.api.IntervalQualifier;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Session;
import org.h2.message.DbException;
import org.h2.security.SHA256;
import org.h2.store.Data;
import org.h2.store.DataReader;
import org.h2.util.Bits;
import org.h2.util.DateTimeUtils;
import org.h2.util.IOUtils;
import org.h2.util.MathUtils;
import org.h2.util.NetUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.ExtTypeInfo;
import org.h2.value.ExtTypeInfoEnum;
import org.h2.value.ExtTypeInfoGeometry;
import org.h2.value.ExtTypeInfoNumeric;
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
import org.h2.value.ValueDate;
import org.h2.value.ValueDecfloat;
import org.h2.value.ValueDouble;
import org.h2.value.ValueEnumBase;
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
import org.h2.value.lob.LobDataFetchOnDemand;

public final class Transfer {
    private static final int BUFFER_SIZE = 65536;
    private static final int LOB_MAGIC = 4660;
    private static final int LOB_MAC_SALT_LENGTH = 16;
    private static final int NULL = 0;
    private static final int BOOLEAN = 1;
    private static final int TINYINT = 2;
    private static final int SMALLINT = 3;
    private static final int INTEGER = 4;
    private static final int BIGINT = 5;
    private static final int NUMERIC = 6;
    private static final int DOUBLE = 7;
    private static final int REAL = 8;
    private static final int TIME = 9;
    private static final int DATE = 10;
    private static final int TIMESTAMP = 11;
    private static final int VARBINARY = 12;
    private static final int VARCHAR = 13;
    private static final int VARCHAR_IGNORECASE = 14;
    private static final int BLOB = 15;
    private static final int CLOB = 16;
    private static final int ARRAY = 17;
    private static final int JAVA_OBJECT = 19;
    private static final int UUID = 20;
    private static final int CHAR = 21;
    private static final int GEOMETRY = 22;
    private static final int TIMESTAMP_TZ = 24;
    private static final int ENUM = 25;
    private static final int INTERVAL = 26;
    private static final int ROW = 27;
    private static final int JSON = 28;
    private static final int TIME_TZ = 29;
    private static final int BINARY = 30;
    private static final int DECFLOAT = 31;
    private static final int[] VALUE_TO_TI = new int[43];
    private static final int[] TI_TO_VALUE = new int[45];
    private final ReentrantLock lock = new ReentrantLock();
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Session session;
    private boolean ssl;
    private int version;
    private byte[] lobMacSalt;

    private static void addType(int n, int n2) {
        Transfer.VALUE_TO_TI[n2 + 1] = n;
        Transfer.TI_TO_VALUE[n + 1] = n2;
    }

    public Transfer(Session session, Socket socket) {
        this.session = session;
        this.socket = socket;
    }

    private void lock() {
        this.lock.lock();
    }

    private void unlock() {
        this.lock.unlock();
    }

    public void init() throws IOException {
        this.lock();
        try {
            if (this.socket != null) {
                this.in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream(), 65536));
                this.out = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream(), 65536));
            }
        }
        finally {
            this.unlock();
        }
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public Transfer writeBoolean(boolean bl) throws IOException {
        this.out.writeByte((byte)(bl ? 1 : 0));
        return this;
    }

    public boolean readBoolean() throws IOException {
        return this.in.readByte() != 0;
    }

    public Transfer writeByte(byte by) throws IOException {
        this.out.writeByte(by);
        return this;
    }

    public byte readByte() throws IOException {
        return this.in.readByte();
    }

    private Transfer writeShort(short s2) throws IOException {
        this.out.writeShort(s2);
        return this;
    }

    private short readShort() throws IOException {
        return this.in.readShort();
    }

    public Transfer writeInt(int n) throws IOException {
        this.out.writeInt(n);
        return this;
    }

    public int readInt() throws IOException {
        return this.in.readInt();
    }

    public Transfer writeLong(long l) throws IOException {
        this.out.writeLong(l);
        return this;
    }

    public long readLong() throws IOException {
        return this.in.readLong();
    }

    private Transfer writeDouble(double d) throws IOException {
        this.out.writeDouble(d);
        return this;
    }

    private Transfer writeFloat(float f) throws IOException {
        this.out.writeFloat(f);
        return this;
    }

    private double readDouble() throws IOException {
        return this.in.readDouble();
    }

    private float readFloat() throws IOException {
        return this.in.readFloat();
    }

    public Transfer writeString(String string) throws IOException {
        if (string == null) {
            this.out.writeInt(-1);
        } else {
            this.out.writeInt(string.length());
            this.out.writeChars(string);
        }
        return this;
    }

    public String readString() throws IOException {
        int n = this.in.readInt();
        if (n == -1) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(this.in.readChar());
        }
        String string = stringBuilder.toString();
        string = StringUtils.cache(string);
        return string;
    }

    public Transfer writeBytes(byte[] byArray) throws IOException {
        if (byArray == null) {
            this.writeInt(-1);
        } else {
            this.writeInt(byArray.length);
            this.out.write(byArray);
        }
        return this;
    }

    public Transfer writeBytes(byte[] byArray, int n, int n2) throws IOException {
        this.out.write(byArray, n, n2);
        return this;
    }

    public byte[] readBytes() throws IOException {
        int n = this.readInt();
        if (n == -1) {
            return null;
        }
        byte[] byArray = Utils.newBytes(n);
        this.in.readFully(byArray);
        return byArray;
    }

    public void readBytes(byte[] byArray, int n, int n2) throws IOException {
        this.in.readFully(byArray, n, n2);
    }

    public void close() {
        block9: {
            this.lock();
            try {
                if (this.socket == null) break block9;
                try {
                    if (this.out != null) {
                        this.out.flush();
                    }
                    this.socket.close();
                }
                catch (IOException iOException) {
                    DbException.traceThrowable(iOException);
                }
                finally {
                    this.socket = null;
                }
            }
            finally {
                this.unlock();
            }
        }
    }

    public Transfer writeTypeInfo(TypeInfo typeInfo) throws IOException {
        if (this.version >= 20) {
            this.writeTypeInfo20(typeInfo);
        } else {
            this.writeTypeInfo19(typeInfo);
        }
        return this;
    }

    private void writeTypeInfo20(TypeInfo typeInfo) throws IOException {
        int n = typeInfo.getValueType();
        this.writeInt(VALUE_TO_TI[n + 1]);
        switch (n) {
            case -1: 
            case 0: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 17: 
            case 39: {
                break;
            }
            case 1: 
            case 2: 
            case 4: 
            case 5: 
            case 6: 
            case 16: 
            case 35: 
            case 38: {
                this.writeInt((int)typeInfo.getDeclaredPrecision());
                break;
            }
            case 3: 
            case 7: {
                this.writeLong(typeInfo.getDeclaredPrecision());
                break;
            }
            case 13: {
                this.writeInt((int)typeInfo.getDeclaredPrecision());
                this.writeInt(typeInfo.getDeclaredScale());
                this.writeBoolean(typeInfo.getExtTypeInfo() != null);
                break;
            }
            case 14: 
            case 15: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 28: 
            case 29: 
            case 30: 
            case 32: {
                this.writeBytePrecisionWithDefault(typeInfo.getDeclaredPrecision());
                break;
            }
            case 18: 
            case 19: 
            case 20: 
            case 21: {
                this.writeByteScaleWithDefault(typeInfo.getDeclaredScale());
                break;
            }
            case 27: 
            case 31: 
            case 33: 
            case 34: {
                this.writeBytePrecisionWithDefault(typeInfo.getDeclaredPrecision());
                this.writeByteScaleWithDefault(typeInfo.getDeclaredScale());
                break;
            }
            case 36: {
                this.writeTypeInfoEnum(typeInfo);
                break;
            }
            case 37: {
                this.writeTypeInfoGeometry(typeInfo);
                break;
            }
            case 40: {
                this.writeInt((int)typeInfo.getDeclaredPrecision());
                this.writeTypeInfo((TypeInfo)typeInfo.getExtTypeInfo());
                break;
            }
            case 41: {
                this.writeTypeInfoRow(typeInfo);
                break;
            }
            default: {
                throw DbException.getUnsupportedException("value type " + n);
            }
        }
    }

    private void writeBytePrecisionWithDefault(long l) throws IOException {
        this.writeByte(l >= 0L ? (byte)l : (byte)-1);
    }

    private void writeByteScaleWithDefault(int n) throws IOException {
        this.writeByte((byte)(n >= 0 ? (int)n : -1));
    }

    private void writeTypeInfoEnum(TypeInfo typeInfo) throws IOException {
        ExtTypeInfoEnum extTypeInfoEnum = (ExtTypeInfoEnum)typeInfo.getExtTypeInfo();
        if (extTypeInfoEnum != null) {
            int n = extTypeInfoEnum.getCount();
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeString(extTypeInfoEnum.getEnumerator(i));
            }
        } else {
            this.writeInt(0);
        }
    }

    private void writeTypeInfoGeometry(TypeInfo typeInfo) throws IOException {
        ExtTypeInfoGeometry extTypeInfoGeometry = (ExtTypeInfoGeometry)typeInfo.getExtTypeInfo();
        if (extTypeInfoGeometry == null) {
            this.writeByte((byte)0);
        } else {
            int n = extTypeInfoGeometry.getType();
            Integer n2 = extTypeInfoGeometry.getSrid();
            if (n == 0) {
                if (n2 == null) {
                    this.writeByte((byte)0);
                } else {
                    this.writeByte((byte)2);
                    this.writeInt(n2);
                }
            } else if (n2 == null) {
                this.writeByte((byte)1);
                this.writeShort((short)n);
            } else {
                this.writeByte((byte)3);
                this.writeShort((short)n);
                this.writeInt(n2);
            }
        }
    }

    private void writeTypeInfoRow(TypeInfo typeInfo) throws IOException {
        Set<Map.Entry<String, TypeInfo>> set = ((ExtTypeInfoRow)typeInfo.getExtTypeInfo()).getFields();
        this.writeInt(set.size());
        for (Map.Entry<String, TypeInfo> entry : set) {
            this.writeString(entry.getKey()).writeTypeInfo(entry.getValue());
        }
    }

    private void writeTypeInfo19(TypeInfo typeInfo) throws IOException {
        int n = typeInfo.getValueType();
        switch (n) {
            case 5: {
                n = 6;
                break;
            }
            case 16: {
                n = 13;
            }
        }
        this.writeInt(VALUE_TO_TI[n + 1]).writeLong(typeInfo.getPrecision()).writeInt(typeInfo.getScale());
    }

    public TypeInfo readTypeInfo() throws IOException {
        if (this.version >= 20) {
            return this.readTypeInfo20();
        }
        return this.readTypeInfo19();
    }

    private TypeInfo readTypeInfo20() throws IOException {
        int n = TI_TO_VALUE[this.readInt() + 1];
        long l = -1L;
        int n2 = -1;
        ExtTypeInfo extTypeInfo = null;
        switch (n) {
            case -1: 
            case 0: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 17: 
            case 39: {
                break;
            }
            case 1: 
            case 2: 
            case 4: 
            case 5: 
            case 6: 
            case 16: 
            case 35: 
            case 38: {
                l = this.readInt();
                break;
            }
            case 3: 
            case 7: {
                l = this.readLong();
                break;
            }
            case 13: {
                l = this.readInt();
                n2 = this.readInt();
                if (!this.readBoolean()) break;
                extTypeInfo = ExtTypeInfoNumeric.DECIMAL;
                break;
            }
            case 14: 
            case 15: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 28: 
            case 29: 
            case 30: 
            case 32: {
                l = this.readByte();
                break;
            }
            case 18: 
            case 19: 
            case 20: 
            case 21: {
                n2 = this.readByte();
                break;
            }
            case 27: 
            case 31: 
            case 33: 
            case 34: {
                l = this.readByte();
                n2 = this.readByte();
                break;
            }
            case 36: {
                extTypeInfo = this.readTypeInfoEnum();
                break;
            }
            case 37: {
                extTypeInfo = this.readTypeInfoGeometry();
                break;
            }
            case 40: {
                l = this.readInt();
                extTypeInfo = this.readTypeInfo();
                break;
            }
            case 41: {
                extTypeInfo = this.readTypeInfoRow();
                break;
            }
            default: {
                throw DbException.getUnsupportedException("value type " + n);
            }
        }
        return TypeInfo.getTypeInfo(n, l, n2, extTypeInfo);
    }

    private ExtTypeInfo readTypeInfoEnum() throws IOException {
        ExtTypeInfoEnum extTypeInfoEnum;
        int n = this.readInt();
        if (n > 0) {
            String[] stringArray = new String[n];
            for (int i = 0; i < n; ++i) {
                stringArray[i] = this.readString();
            }
            extTypeInfoEnum = new ExtTypeInfoEnum(stringArray);
        } else {
            extTypeInfoEnum = null;
        }
        return extTypeInfoEnum;
    }

    private ExtTypeInfo readTypeInfoGeometry() throws IOException {
        ExtTypeInfoGeometry extTypeInfoGeometry;
        byte by = this.readByte();
        switch (by) {
            case 0: {
                extTypeInfoGeometry = null;
                break;
            }
            case 1: {
                extTypeInfoGeometry = new ExtTypeInfoGeometry(this.readShort(), null);
                break;
            }
            case 2: {
                extTypeInfoGeometry = new ExtTypeInfoGeometry(0, this.readInt());
                break;
            }
            case 3: {
                extTypeInfoGeometry = new ExtTypeInfoGeometry(this.readShort(), this.readInt());
                break;
            }
            default: {
                throw DbException.getUnsupportedException("GEOMETRY type encoding " + by);
            }
        }
        return extTypeInfoGeometry;
    }

    private ExtTypeInfo readTypeInfoRow() throws IOException {
        LinkedHashMap<String, TypeInfo> linkedHashMap = new LinkedHashMap<String, TypeInfo>();
        int n = this.readInt();
        for (int i = 0; i < n; ++i) {
            String string = this.readString();
            if (linkedHashMap.putIfAbsent(string, this.readTypeInfo()) == null) continue;
            throw DbException.get(42121, string);
        }
        return new ExtTypeInfoRow(linkedHashMap);
    }

    private TypeInfo readTypeInfo19() throws IOException {
        return TypeInfo.getTypeInfo(TI_TO_VALUE[this.readInt() + 1], this.readLong(), this.readInt(), null);
    }

    public void writeValue(Value value) throws IOException {
        int n = value.getValueType();
        switch (n) {
            case 0: {
                this.writeInt(0);
                break;
            }
            case 5: {
                if (this.version >= 20) {
                    this.writeInt(30);
                    this.writeBytes(value.getBytesNoCopy());
                    break;
                }
            }
            case 6: {
                this.writeInt(12);
                this.writeBytes(value.getBytesNoCopy());
                break;
            }
            case 35: {
                this.writeInt(19);
                this.writeBytes(value.getBytesNoCopy());
                break;
            }
            case 39: {
                this.writeInt(20);
                ValueUuid valueUuid = (ValueUuid)value;
                this.writeLong(valueUuid.getHigh());
                this.writeLong(valueUuid.getLow());
                break;
            }
            case 8: {
                this.writeInt(1);
                this.writeBoolean(value.getBoolean());
                break;
            }
            case 9: {
                this.writeInt(2);
                this.writeByte(value.getByte());
                break;
            }
            case 18: {
                this.writeInt(9);
                this.writeLong(((ValueTime)value).getNanos());
                break;
            }
            case 19: {
                ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value;
                if (this.version >= 19) {
                    this.writeInt(29);
                    this.writeLong(valueTimeTimeZone.getNanos());
                    this.writeInt(valueTimeTimeZone.getTimeZoneOffsetSeconds());
                    break;
                }
                this.writeInt(9);
                ValueTimestampTimeZone valueTimestampTimeZone = this.session.isRemote() ? DateTimeUtils.currentTimestamp(DateTimeUtils.getTimeZone()) : this.session.currentTimestamp();
                this.writeLong(DateTimeUtils.normalizeNanosOfDay(valueTimeTimeZone.getNanos() + (long)(valueTimeTimeZone.getTimeZoneOffsetSeconds() - valueTimestampTimeZone.getTimeZoneOffsetSeconds()) * 86400000000000L));
                break;
            }
            case 17: {
                this.writeInt(10);
                this.writeLong(((ValueDate)value).getDateValue());
                break;
            }
            case 20: {
                this.writeInt(11);
                ValueTimestamp valueTimestamp = (ValueTimestamp)value;
                this.writeLong(valueTimestamp.getDateValue());
                this.writeLong(valueTimestamp.getTimeNanos());
                break;
            }
            case 21: {
                this.writeInt(24);
                ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value;
                this.writeLong(valueTimestampTimeZone.getDateValue());
                this.writeLong(valueTimestampTimeZone.getTimeNanos());
                int n2 = valueTimestampTimeZone.getTimeZoneOffsetSeconds();
                this.writeInt(this.version >= 19 ? n2 : n2 / 60);
                break;
            }
            case 16: {
                if (this.version >= 20) {
                    this.writeInt(31);
                    this.writeString(value.getString());
                    break;
                }
            }
            case 13: {
                this.writeInt(6);
                this.writeString(value.getString());
                break;
            }
            case 15: {
                this.writeInt(7);
                this.writeDouble(value.getDouble());
                break;
            }
            case 14: {
                this.writeInt(8);
                this.writeFloat(value.getFloat());
                break;
            }
            case 11: {
                this.writeInt(4);
                this.writeInt(value.getInt());
                break;
            }
            case 12: {
                this.writeInt(5);
                this.writeLong(value.getLong());
                break;
            }
            case 10: {
                this.writeInt(3);
                if (this.version >= 20) {
                    this.writeShort(value.getShort());
                    break;
                }
                this.writeInt(value.getShort());
                break;
            }
            case 2: {
                this.writeInt(13);
                this.writeString(value.getString());
                break;
            }
            case 4: {
                this.writeInt(14);
                this.writeString(value.getString());
                break;
            }
            case 1: {
                this.writeInt(21);
                this.writeString(value.getString());
                break;
            }
            case 7: {
                this.writeInt(15);
                ValueBlob valueBlob = (ValueBlob)value;
                LobData lobData = valueBlob.getLobData();
                long l = valueBlob.octetLength();
                if (lobData instanceof LobDataDatabase) {
                    LobDataDatabase lobDataDatabase = (LobDataDatabase)lobData;
                    this.writeLong(-1L);
                    this.writeInt(lobDataDatabase.getTableId());
                    this.writeLong(lobDataDatabase.getLobId());
                    this.writeBytes(this.calculateLobMac(lobDataDatabase.getLobId()));
                    this.writeLong(l);
                    break;
                }
                if (l < 0L) {
                    throw DbException.get(90067, "length=" + l);
                }
                this.writeLong(l);
                long l2 = IOUtils.copyAndCloseInput(valueBlob.getInputStream(), this.out);
                if (l2 != l) {
                    throw DbException.get(90067, "length:" + l + " written:" + l2);
                }
                this.writeInt(4660);
                break;
            }
            case 3: {
                this.writeInt(16);
                ValueClob valueClob = (ValueClob)value;
                LobData lobData = valueClob.getLobData();
                long l = valueClob.charLength();
                if (lobData instanceof LobDataDatabase) {
                    LobDataDatabase lobDataDatabase = (LobDataDatabase)lobData;
                    this.writeLong(-1L);
                    this.writeInt(lobDataDatabase.getTableId());
                    this.writeLong(lobDataDatabase.getLobId());
                    this.writeBytes(this.calculateLobMac(lobDataDatabase.getLobId()));
                    if (this.version >= 20) {
                        this.writeLong(valueClob.octetLength());
                    }
                    this.writeLong(l);
                    break;
                }
                if (l < 0L) {
                    throw DbException.get(90067, "length=" + l);
                }
                this.writeLong(l);
                Reader reader = valueClob.getReader();
                Data.copyString(reader, this.out);
                this.writeInt(4660);
                break;
            }
            case 40: {
                this.writeInt(17);
                ValueArray valueArray = (ValueArray)value;
                Value[] valueArray2 = valueArray.getList();
                int n3 = valueArray2.length;
                this.writeInt(n3);
                for (Value value2 : valueArray2) {
                    this.writeValue(value2);
                }
                break;
            }
            case 41: {
                this.writeInt(this.version >= 18 ? 27 : 17);
                ValueRow valueRow = (ValueRow)value;
                Value[] valueArray = valueRow.getList();
                int n4 = valueArray.length;
                this.writeInt(n4);
                for (Value value3 : valueArray) {
                    this.writeValue(value3);
                }
                break;
            }
            case 36: {
                this.writeInt(25);
                this.writeInt(value.getInt());
                if (this.version >= 20) break;
                this.writeString(value.getString());
                break;
            }
            case 37: {
                this.writeInt(22);
                this.writeBytes(value.getBytesNoCopy());
                break;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: {
                if (this.version >= 18) {
                    ValueInterval valueInterval = (ValueInterval)value;
                    int n5 = n - 22;
                    if (valueInterval.isNegative()) {
                        n5 ^= 0xFFFFFFFF;
                    }
                    this.writeInt(26);
                    this.writeByte((byte)n5);
                    this.writeLong(valueInterval.getLeading());
                    break;
                }
                this.writeInt(13);
                this.writeString(value.getString());
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
                if (this.version >= 18) {
                    ValueInterval valueInterval = (ValueInterval)value;
                    int n6 = n - 22;
                    if (valueInterval.isNegative()) {
                        n6 ^= 0xFFFFFFFF;
                    }
                    this.writeInt(26);
                    this.writeByte((byte)n6);
                    this.writeLong(valueInterval.getLeading());
                    this.writeLong(valueInterval.getRemaining());
                    break;
                }
                this.writeInt(13);
                this.writeString(value.getString());
                break;
            }
            case 38: {
                this.writeInt(28);
                this.writeBytes(value.getBytesNoCopy());
                break;
            }
            default: {
                throw DbException.get(90067, "type=" + n);
            }
        }
    }

    public Value readValue(TypeInfo typeInfo) throws IOException {
        int n = this.readInt();
        switch (n) {
            case 0: {
                return ValueNull.INSTANCE;
            }
            case 12: {
                return ValueVarbinary.getNoCopy(this.readBytes());
            }
            case 30: {
                return ValueBinary.getNoCopy(this.readBytes());
            }
            case 20: {
                return ValueUuid.get(this.readLong(), this.readLong());
            }
            case 19: {
                return ValueJavaObject.getNoCopy(this.readBytes());
            }
            case 1: {
                return ValueBoolean.get(this.readBoolean());
            }
            case 2: {
                return ValueTinyint.get(this.readByte());
            }
            case 10: {
                return ValueDate.fromDateValue(this.readLong());
            }
            case 9: {
                return ValueTime.fromNanos(this.readLong());
            }
            case 29: {
                return ValueTimeTimeZone.fromNanos(this.readLong(), this.readInt());
            }
            case 11: {
                return ValueTimestamp.fromDateValueAndNanos(this.readLong(), this.readLong());
            }
            case 24: {
                long l = this.readLong();
                long l2 = this.readLong();
                int n2 = this.readInt();
                return ValueTimestampTimeZone.fromDateValueAndNanos(l, l2, this.version >= 19 ? n2 : n2 * 60);
            }
            case 6: {
                return ValueNumeric.get(new BigDecimal(this.readString()));
            }
            case 7: {
                return ValueDouble.get(this.readDouble());
            }
            case 8: {
                return ValueReal.get(this.readFloat());
            }
            case 25: {
                int n3 = this.readInt();
                if (this.version >= 20) {
                    return ((ExtTypeInfoEnum)typeInfo.getExtTypeInfo()).getValue(n3, (CastDataProvider)this.session);
                }
                return ValueEnumBase.get(this.readString(), n3);
            }
            case 4: {
                return ValueInteger.get(this.readInt());
            }
            case 5: {
                return ValueBigint.get(this.readLong());
            }
            case 3: {
                if (this.version >= 20) {
                    return ValueSmallint.get(this.readShort());
                }
                return ValueSmallint.get((short)this.readInt());
            }
            case 13: {
                return ValueVarchar.get(this.readString());
            }
            case 14: {
                return ValueVarcharIgnoreCase.get(this.readString());
            }
            case 21: {
                return ValueChar.get(this.readString());
            }
            case 15: {
                long l = this.readLong();
                if (l == -1L) {
                    int n4 = this.readInt();
                    long l3 = this.readLong();
                    byte[] byArray = this.readBytes();
                    long l4 = this.readLong();
                    return new ValueBlob(new LobDataFetchOnDemand(this.session.getDataHandler(), n4, l3, byArray), l4);
                }
                ValueBlob valueBlob = this.session.getDataHandler().getLobStorage().createBlob(this.in, l);
                int n5 = this.readInt();
                if (n5 != 4660) {
                    throw DbException.get(90067, "magic=" + n5);
                }
                return valueBlob;
            }
            case 16: {
                long l = this.readLong();
                if (l == -1L) {
                    int n6 = this.readInt();
                    long l5 = this.readLong();
                    byte[] byArray = this.readBytes();
                    long l6 = this.version >= 20 ? this.readLong() : -1L;
                    l = this.readLong();
                    return new ValueClob(new LobDataFetchOnDemand(this.session.getDataHandler(), n6, l5, byArray), l6, l);
                }
                if (l < 0L) {
                    throw DbException.get(90067, "length=" + l);
                }
                ValueClob valueClob = this.session.getDataHandler().getLobStorage().createClob(new DataReader(this.in), l);
                int n7 = this.readInt();
                if (n7 != 4660) {
                    throw DbException.get(90067, "magic=" + n7);
                }
                return valueClob;
            }
            case 17: {
                int n8 = this.readInt();
                if (n8 < 0) {
                    n8 ^= 0xFFFFFFFF;
                    this.readString();
                }
                if (typeInfo != null) {
                    TypeInfo typeInfo2 = (TypeInfo)typeInfo.getExtTypeInfo();
                    return ValueArray.get(typeInfo2, this.readArrayElements(n8, typeInfo2), this.session);
                }
                return ValueArray.get(this.readArrayElements(n8, null), this.session);
            }
            case 27: {
                int n9 = this.readInt();
                Value[] valueArray = new Value[n9];
                if (typeInfo != null) {
                    ExtTypeInfoRow extTypeInfoRow = (ExtTypeInfoRow)typeInfo.getExtTypeInfo();
                    Iterator<Map.Entry<String, TypeInfo>> iterator2 = extTypeInfoRow.getFields().iterator();
                    for (int i = 0; i < n9; ++i) {
                        valueArray[i] = this.readValue(iterator2.next().getValue());
                    }
                    return ValueRow.get(typeInfo, valueArray);
                }
                for (int i = 0; i < n9; ++i) {
                    valueArray[i] = this.readValue(null);
                }
                return ValueRow.get(valueArray);
            }
            case 22: {
                return ValueGeometry.get(this.readBytes());
            }
            case 26: {
                boolean bl;
                int n10 = this.readByte();
                boolean bl2 = bl = n10 < 0;
                if (bl) {
                    n10 ^= 0xFFFFFFFF;
                }
                return ValueInterval.from(IntervalQualifier.valueOf(n10), bl, this.readLong(), n10 < 5 ? 0L : this.readLong());
            }
            case 28: {
                return ValueJson.fromJson(this.readBytes());
            }
            case 31: {
                String string;
                switch (string = this.readString()) {
                    case "-Infinity": {
                        return ValueDecfloat.NEGATIVE_INFINITY;
                    }
                    case "Infinity": {
                        return ValueDecfloat.POSITIVE_INFINITY;
                    }
                    case "NaN": {
                        return ValueDecfloat.NAN;
                    }
                }
                return ValueDecfloat.get(new BigDecimal(string));
            }
        }
        throw DbException.get(90067, "type=" + n);
    }

    private Value[] readArrayElements(int n, TypeInfo typeInfo) throws IOException {
        Value[] valueArray = new Value[n];
        for (int i = 0; i < n; ++i) {
            valueArray[i] = this.readValue(typeInfo);
        }
        return valueArray;
    }

    public long readRowCount() throws IOException {
        return this.version >= 20 ? this.readLong() : (long)this.readInt();
    }

    public Transfer writeRowCount(long l) throws IOException {
        return this.version >= 20 ? this.writeLong(l) : this.writeInt(l < Integer.MAX_VALUE ? (int)l : Integer.MAX_VALUE);
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSSL(boolean bl) {
        this.ssl = bl;
    }

    public Transfer openNewConnection() throws IOException {
        InetAddress inetAddress = this.socket.getInetAddress();
        int n = this.socket.getPort();
        Socket socket = NetUtils.createSocket(inetAddress, n, this.ssl);
        Transfer transfer = new Transfer(null, socket);
        transfer.setSSL(this.ssl);
        return transfer;
    }

    public void setVersion(int n) {
        this.version = n;
    }

    public int getVersion() {
        return this.version;
    }

    public boolean isClosed() {
        this.lock();
        try {
            boolean bl = this.socket == null || this.socket.isClosed();
            return bl;
        }
        finally {
            this.unlock();
        }
    }

    public void verifyLobMac(byte[] byArray, long l) {
        byte[] byArray2 = this.calculateLobMac(l);
        if (!Utils.compareSecure(byArray, byArray2)) {
            throw DbException.get(90067, "Invalid lob hmac; possibly the connection was re-opened internally");
        }
    }

    private byte[] calculateLobMac(long l) {
        if (this.lobMacSalt == null) {
            this.lobMacSalt = MathUtils.secureRandomBytes(16);
        }
        byte[] byArray = new byte[8];
        Bits.writeLong(byArray, 0, l);
        return SHA256.getHashWithSalt(byArray, this.lobMacSalt);
    }

    static {
        Transfer.addType(-1, -1);
        Transfer.addType(0, 0);
        Transfer.addType(1, 8);
        Transfer.addType(2, 9);
        Transfer.addType(3, 10);
        Transfer.addType(4, 11);
        Transfer.addType(5, 12);
        Transfer.addType(6, 13);
        Transfer.addType(7, 15);
        Transfer.addType(8, 14);
        Transfer.addType(9, 18);
        Transfer.addType(10, 17);
        Transfer.addType(11, 20);
        Transfer.addType(12, 6);
        Transfer.addType(13, 2);
        Transfer.addType(14, 4);
        Transfer.addType(15, 7);
        Transfer.addType(16, 3);
        Transfer.addType(17, 40);
        Transfer.addType(19, 35);
        Transfer.addType(20, 39);
        Transfer.addType(21, 1);
        Transfer.addType(22, 37);
        Transfer.addType(24, 21);
        Transfer.addType(25, 36);
        Transfer.addType(26, 22);
        Transfer.addType(27, 23);
        Transfer.addType(28, 24);
        Transfer.addType(29, 25);
        Transfer.addType(30, 26);
        Transfer.addType(31, 27);
        Transfer.addType(32, 28);
        Transfer.addType(33, 29);
        Transfer.addType(34, 30);
        Transfer.addType(35, 31);
        Transfer.addType(36, 32);
        Transfer.addType(37, 33);
        Transfer.addType(38, 34);
        Transfer.addType(39, 41);
        Transfer.addType(40, 38);
        Transfer.addType(41, 19);
        Transfer.addType(42, 5);
        Transfer.addType(43, 16);
    }
}

