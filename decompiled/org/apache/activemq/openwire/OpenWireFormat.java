/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.openwire;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.DataStreamMarshaller;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.ByteSequenceData;
import org.apache.activemq.util.DataByteArrayInputStream;
import org.apache.activemq.util.DataByteArrayOutputStream;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.wireformat.WireFormat;

public final class OpenWireFormat
implements WireFormat {
    public static final int DEFAULT_STORE_VERSION = 11;
    public static final int DEFAULT_WIRE_VERSION = 12;
    public static final int DEFAULT_LEGACY_VERSION = 6;
    public static final long DEFAULT_MAX_FRAME_SIZE = Long.MAX_VALUE;
    static final byte NULL_TYPE = 0;
    private static final int MARSHAL_CACHE_SIZE = 16383;
    private static final int MARSHAL_CACHE_FREE_SPACE = 100;
    private DataStreamMarshaller[] dataMarshallers;
    private int version;
    private boolean stackTraceEnabled;
    private boolean tcpNoDelayEnabled;
    private boolean cacheEnabled;
    private boolean tightEncodingEnabled;
    private boolean sizePrefixDisabled;
    private long maxFrameSize = Long.MAX_VALUE;
    private short nextMarshallCacheIndex;
    private short nextMarshallCacheEvictionIndex;
    private Map<DataStructure, Short> marshallCacheMap = new HashMap<DataStructure, Short>();
    private DataStructure[] marshallCache = null;
    private DataStructure[] unmarshallCache = null;
    private DataByteArrayOutputStream bytesOut = new DataByteArrayOutputStream();
    private DataByteArrayInputStream bytesIn = new DataByteArrayInputStream();
    private WireFormatInfo preferedWireFormatInfo;

    public OpenWireFormat() {
        this(11);
    }

    public OpenWireFormat(int i) {
        this.setVersion(i);
    }

    public int hashCode() {
        return this.version ^ (this.cacheEnabled ? 0x10000000 : 0x20000000) ^ (this.stackTraceEnabled ? 0x1000000 : 0x2000000) ^ (this.tightEncodingEnabled ? 0x100000 : 0x200000) ^ (this.sizePrefixDisabled ? 65536 : 131072);
    }

    public OpenWireFormat copy() {
        OpenWireFormat answer = new OpenWireFormat(this.version);
        answer.stackTraceEnabled = this.stackTraceEnabled;
        answer.tcpNoDelayEnabled = this.tcpNoDelayEnabled;
        answer.cacheEnabled = this.cacheEnabled;
        answer.tightEncodingEnabled = this.tightEncodingEnabled;
        answer.sizePrefixDisabled = this.sizePrefixDisabled;
        answer.preferedWireFormatInfo = this.preferedWireFormatInfo;
        return answer;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        OpenWireFormat o = (OpenWireFormat)object;
        return o.stackTraceEnabled == this.stackTraceEnabled && o.cacheEnabled == this.cacheEnabled && o.version == this.version && o.tightEncodingEnabled == this.tightEncodingEnabled && o.sizePrefixDisabled == this.sizePrefixDisabled;
    }

    public String toString() {
        return "OpenWireFormat{version=" + this.version + ", cacheEnabled=" + this.cacheEnabled + ", stackTraceEnabled=" + this.stackTraceEnabled + ", tightEncodingEnabled=" + this.tightEncodingEnabled + ", sizePrefixDisabled=" + this.sizePrefixDisabled + ", maxFrameSize=" + this.maxFrameSize + "}";
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public synchronized ByteSequence marshal(Object command) throws IOException {
        if (this.cacheEnabled) {
            this.runMarshallCacheEvictionSweep();
        }
        ByteSequence sequence2 = null;
        int size = 1;
        if (command != null) {
            DataStructure c = (DataStructure)command;
            byte type = c.getDataStructureType();
            DataStreamMarshaller dsm = this.dataMarshallers[type & 0xFF];
            if (dsm == null) {
                throw new IOException("Unknown data type: " + type);
            }
            if (this.tightEncodingEnabled) {
                BooleanStream bs = new BooleanStream();
                size += dsm.tightMarshal1(this, c, bs);
                this.bytesOut.restart(size += bs.marshalledSize());
                if (!this.sizePrefixDisabled) {
                    this.bytesOut.writeInt(size);
                }
                this.bytesOut.writeByte(type);
                bs.marshal(this.bytesOut);
                dsm.tightMarshal2(this, c, this.bytesOut, bs);
                sequence2 = this.bytesOut.toByteSequence();
            } else {
                this.bytesOut.restart();
                if (!this.sizePrefixDisabled) {
                    this.bytesOut.writeInt(0);
                }
                this.bytesOut.writeByte(type);
                dsm.looseMarshal(this, c, this.bytesOut);
                sequence2 = this.bytesOut.toByteSequence();
                if (!this.sizePrefixDisabled) {
                    size = sequence2.getLength() - 4;
                    int pos = sequence2.offset;
                    ByteSequenceData.writeIntBig(sequence2, size);
                    sequence2.offset = pos;
                }
            }
        } else {
            this.bytesOut.restart(5);
            this.bytesOut.writeInt(size);
            this.bytesOut.writeByte(0);
            sequence2 = this.bytesOut.toByteSequence();
        }
        return sequence2;
    }

    @Override
    public synchronized Object unmarshal(ByteSequence sequence2) throws IOException {
        this.bytesIn.restart(sequence2);
        if (!this.sizePrefixDisabled) {
            int size = this.bytesIn.readInt();
            if (sequence2.getLength() - 4 != size) {
                // empty if block
            }
            if ((long)size > this.maxFrameSize) {
                throw IOExceptionSupport.createFrameSizeException(size, this.maxFrameSize);
            }
        }
        Object command = this.doUnmarshal(this.bytesIn);
        return command;
    }

    @Override
    public synchronized void marshal(Object o, DataOutput dataOut) throws IOException {
        if (this.cacheEnabled) {
            this.runMarshallCacheEvictionSweep();
        }
        int size = 1;
        if (o != null) {
            DataStructure c = (DataStructure)o;
            byte type = c.getDataStructureType();
            DataStreamMarshaller dsm = this.dataMarshallers[type & 0xFF];
            if (dsm == null) {
                throw new IOException("Unknown data type: " + type);
            }
            if (this.tightEncodingEnabled) {
                BooleanStream bs = new BooleanStream();
                size += dsm.tightMarshal1(this, c, bs);
                size += bs.marshalledSize();
                if (!this.sizePrefixDisabled) {
                    dataOut.writeInt(size);
                }
                dataOut.writeByte(type);
                bs.marshal(dataOut);
                dsm.tightMarshal2(this, c, dataOut, bs);
            } else {
                DataOutput looseOut = dataOut;
                if (!this.sizePrefixDisabled) {
                    this.bytesOut.restart();
                    looseOut = this.bytesOut;
                }
                looseOut.writeByte(type);
                dsm.looseMarshal(this, c, looseOut);
                if (!this.sizePrefixDisabled) {
                    ByteSequence sequence2 = this.bytesOut.toByteSequence();
                    dataOut.writeInt(sequence2.getLength());
                    dataOut.write(sequence2.getData(), sequence2.getOffset(), sequence2.getLength());
                }
            }
        } else {
            if (!this.sizePrefixDisabled) {
                dataOut.writeInt(size);
            }
            dataOut.writeByte(0);
        }
    }

    @Override
    public Object unmarshal(DataInput dis) throws IOException {
        int size;
        DataInput dataIn = dis;
        if (!this.sizePrefixDisabled && (long)(size = dis.readInt()) > this.maxFrameSize) {
            throw IOExceptionSupport.createFrameSizeException(size, this.maxFrameSize);
        }
        return this.doUnmarshal(dataIn);
    }

    public int tightMarshal1(Object o, BooleanStream bs) throws IOException {
        int size = 1;
        if (o != null) {
            DataStructure c = (DataStructure)o;
            byte type = c.getDataStructureType();
            DataStreamMarshaller dsm = this.dataMarshallers[type & 0xFF];
            if (dsm == null) {
                throw new IOException("Unknown data type: " + type);
            }
            size += dsm.tightMarshal1(this, c, bs);
            size += bs.marshalledSize();
        }
        return size;
    }

    public void tightMarshal2(Object o, DataOutput ds, BooleanStream bs) throws IOException {
        if (this.cacheEnabled) {
            this.runMarshallCacheEvictionSweep();
        }
        if (o != null) {
            DataStructure c = (DataStructure)o;
            byte type = c.getDataStructureType();
            DataStreamMarshaller dsm = this.dataMarshallers[type & 0xFF];
            if (dsm == null) {
                throw new IOException("Unknown data type: " + type);
            }
            ds.writeByte(type);
            bs.marshal(ds);
            dsm.tightMarshal2(this, c, ds, bs);
        }
    }

    @Override
    public void setVersion(int version) {
        Class<?> mfClass;
        String mfName = "org.apache.activemq.openwire.v" + version + ".MarshallerFactory";
        try {
            mfClass = Class.forName(mfName, false, this.getClass().getClassLoader());
        }
        catch (ClassNotFoundException e) {
            throw (IllegalArgumentException)new IllegalArgumentException("Invalid version: " + version + ", could not load " + mfName).initCause(e);
        }
        try {
            Method method2 = mfClass.getMethod("createMarshallerMap", OpenWireFormat.class);
            this.dataMarshallers = (DataStreamMarshaller[])method2.invoke(null, this);
        }
        catch (Throwable e) {
            throw (IllegalArgumentException)new IllegalArgumentException("Invalid version: " + version + ", " + mfName + " does not properly implement the createMarshallerMap method.").initCause(e);
        }
        this.version = version;
    }

    public Object doUnmarshal(DataInput dis) throws IOException {
        byte dataType = dis.readByte();
        if (dataType != 0) {
            DataStreamMarshaller dsm = this.dataMarshallers[dataType & 0xFF];
            if (dsm == null) {
                throw new IOException("Unknown data type: " + dataType);
            }
            DataStructure data = dsm.createObject();
            if (this.tightEncodingEnabled) {
                BooleanStream bs = new BooleanStream();
                bs.unmarshal(dis);
                dsm.tightUnmarshal(this, data, dis, bs);
            } else {
                dsm.looseUnmarshal(this, data, dis);
            }
            return data;
        }
        return null;
    }

    public int tightMarshalNestedObject1(DataStructure o, BooleanStream bs) throws IOException {
        byte type;
        DataStreamMarshaller dsm;
        bs.writeBoolean(o != null);
        if (o == null) {
            return 0;
        }
        if (o.isMarshallAware()) {
            ByteSequence sequence2 = null;
            bs.writeBoolean(sequence2 != null);
            if (sequence2 != null) {
                return 1 + sequence2.getLength();
            }
        }
        if ((dsm = this.dataMarshallers[(type = o.getDataStructureType()) & 0xFF]) == null) {
            throw new IOException("Unknown data type: " + type);
        }
        return 1 + dsm.tightMarshal1(this, o, bs);
    }

    public void tightMarshalNestedObject2(DataStructure o, DataOutput ds, BooleanStream bs) throws IOException {
        if (!bs.readBoolean()) {
            return;
        }
        byte type = o.getDataStructureType();
        ds.writeByte(type);
        if (o.isMarshallAware() && bs.readBoolean()) {
            throw new IOException("Corrupted stream");
        }
        DataStreamMarshaller dsm = this.dataMarshallers[type & 0xFF];
        if (dsm == null) {
            throw new IOException("Unknown data type: " + type);
        }
        dsm.tightMarshal2(this, o, ds, bs);
    }

    public DataStructure tightUnmarshalNestedObject(DataInput dis, BooleanStream bs) throws IOException {
        if (bs.readBoolean()) {
            byte dataType = dis.readByte();
            DataStreamMarshaller dsm = this.dataMarshallers[dataType & 0xFF];
            if (dsm == null) {
                throw new IOException("Unknown data type: " + dataType);
            }
            DataStructure data = dsm.createObject();
            if (data.isMarshallAware() && bs.readBoolean()) {
                dis.readInt();
                dis.readByte();
                BooleanStream bs2 = new BooleanStream();
                bs2.unmarshal(dis);
                dsm.tightUnmarshal(this, data, dis, bs2);
            } else {
                dsm.tightUnmarshal(this, data, dis, bs);
            }
            return data;
        }
        return null;
    }

    public DataStructure looseUnmarshalNestedObject(DataInput dis) throws IOException {
        if (dis.readBoolean()) {
            byte dataType = dis.readByte();
            DataStreamMarshaller dsm = this.dataMarshallers[dataType & 0xFF];
            if (dsm == null) {
                throw new IOException("Unknown data type: " + dataType);
            }
            DataStructure data = dsm.createObject();
            dsm.looseUnmarshal(this, data, dis);
            return data;
        }
        return null;
    }

    public void looseMarshalNestedObject(DataStructure o, DataOutput dataOut) throws IOException {
        dataOut.writeBoolean(o != null);
        if (o != null) {
            byte type = o.getDataStructureType();
            dataOut.writeByte(type);
            DataStreamMarshaller dsm = this.dataMarshallers[type & 0xFF];
            if (dsm == null) {
                throw new IOException("Unknown data type: " + type);
            }
            dsm.looseMarshal(this, o, dataOut);
        }
    }

    public void runMarshallCacheEvictionSweep() {
        while (this.marshallCacheMap.size() > this.marshallCache.length - 100) {
            this.marshallCacheMap.remove(this.marshallCache[this.nextMarshallCacheEvictionIndex]);
            this.marshallCache[this.nextMarshallCacheEvictionIndex] = null;
            this.nextMarshallCacheEvictionIndex = (short)(this.nextMarshallCacheEvictionIndex + 1);
            if (this.nextMarshallCacheEvictionIndex < this.marshallCache.length) continue;
            this.nextMarshallCacheEvictionIndex = 0;
        }
    }

    public Short getMarshallCacheIndex(DataStructure o) {
        return this.marshallCacheMap.get(o);
    }

    public Short addToMarshallCache(DataStructure o) {
        short s2 = this.nextMarshallCacheIndex;
        this.nextMarshallCacheIndex = (short)(s2 + 1);
        short i = s2;
        if (this.nextMarshallCacheIndex >= this.marshallCache.length) {
            this.nextMarshallCacheIndex = 0;
        }
        if (this.marshallCacheMap.size() < this.marshallCache.length) {
            this.marshallCache[i] = o;
            Short index = new Short(i);
            this.marshallCacheMap.put(o, index);
            return index;
        }
        return new Short(-1);
    }

    public void setInUnmarshallCache(short index, DataStructure o) {
        if (index == -1) {
            return;
        }
        this.unmarshallCache[index] = o;
    }

    public DataStructure getFromUnmarshallCache(short index) {
        return this.unmarshallCache[index];
    }

    public void setStackTraceEnabled(boolean b) {
        this.stackTraceEnabled = b;
    }

    public boolean isStackTraceEnabled() {
        return this.stackTraceEnabled;
    }

    public boolean isTcpNoDelayEnabled() {
        return this.tcpNoDelayEnabled;
    }

    public void setTcpNoDelayEnabled(boolean tcpNoDelayEnabled) {
        this.tcpNoDelayEnabled = tcpNoDelayEnabled;
    }

    public boolean isCacheEnabled() {
        return this.cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        if (cacheEnabled) {
            this.marshallCache = new DataStructure[16383];
            this.unmarshallCache = new DataStructure[16383];
        }
        this.cacheEnabled = cacheEnabled;
    }

    public boolean isTightEncodingEnabled() {
        return this.tightEncodingEnabled;
    }

    public void setTightEncodingEnabled(boolean tightEncodingEnabled) {
        this.tightEncodingEnabled = tightEncodingEnabled;
    }

    public boolean isSizePrefixDisabled() {
        return this.sizePrefixDisabled;
    }

    public void setSizePrefixDisabled(boolean prefixPacketSize) {
        this.sizePrefixDisabled = prefixPacketSize;
    }

    public void setPreferedWireFormatInfo(WireFormatInfo info) {
        this.preferedWireFormatInfo = info;
    }

    public WireFormatInfo getPreferedWireFormatInfo() {
        return this.preferedWireFormatInfo;
    }

    public long getMaxFrameSize() {
        return this.maxFrameSize;
    }

    public void setMaxFrameSize(long maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    public void renegotiateWireFormat(WireFormatInfo info) throws IOException {
        if (this.preferedWireFormatInfo == null) {
            throw new IllegalStateException("Wireformat cannot not be renegotiated.");
        }
        this.setVersion(this.min(this.preferedWireFormatInfo.getVersion(), info.getVersion()));
        info.setVersion(this.getVersion());
        this.setMaxFrameSize(this.min(this.preferedWireFormatInfo.getMaxFrameSize(), info.getMaxFrameSize()));
        info.setMaxFrameSize(this.getMaxFrameSize());
        this.stackTraceEnabled = info.isStackTraceEnabled() && this.preferedWireFormatInfo.isStackTraceEnabled();
        info.setStackTraceEnabled(this.stackTraceEnabled);
        this.tcpNoDelayEnabled = info.isTcpNoDelayEnabled() && this.preferedWireFormatInfo.isTcpNoDelayEnabled();
        info.setTcpNoDelayEnabled(this.tcpNoDelayEnabled);
        this.cacheEnabled = info.isCacheEnabled() && this.preferedWireFormatInfo.isCacheEnabled();
        info.setCacheEnabled(this.cacheEnabled);
        this.tightEncodingEnabled = info.isTightEncodingEnabled() && this.preferedWireFormatInfo.isTightEncodingEnabled();
        info.setTightEncodingEnabled(this.tightEncodingEnabled);
        this.sizePrefixDisabled = info.isSizePrefixDisabled() && this.preferedWireFormatInfo.isSizePrefixDisabled();
        info.setSizePrefixDisabled(this.sizePrefixDisabled);
        if (this.cacheEnabled) {
            int size = Math.min(this.preferedWireFormatInfo.getCacheSize(), info.getCacheSize());
            info.setCacheSize(size);
            if (size == 0) {
                size = 16383;
            }
            this.marshallCache = new DataStructure[size];
            this.unmarshallCache = new DataStructure[size];
            this.nextMarshallCacheIndex = 0;
            this.nextMarshallCacheEvictionIndex = 0;
            this.marshallCacheMap = new HashMap<DataStructure, Short>();
        } else {
            this.marshallCache = null;
            this.unmarshallCache = null;
            this.nextMarshallCacheIndex = 0;
            this.nextMarshallCacheEvictionIndex = 0;
            this.marshallCacheMap = null;
        }
    }

    protected int min(int version1, int version2) {
        if (version1 < version2 && version1 > 0 || version2 <= 0) {
            return version1;
        }
        return version2;
    }

    protected long min(long version1, long version2) {
        if (version1 < version2 && version1 > 0L || version2 <= 0L) {
            return version1;
        }
        return version2;
    }
}

