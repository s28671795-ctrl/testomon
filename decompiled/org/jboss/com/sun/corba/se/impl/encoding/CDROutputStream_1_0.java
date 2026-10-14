/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.rmi.CORBA.Util;
import javax.rmi.CORBA.ValueHandler;
import javax.rmi.CORBA.ValueHandlerMultiFormat;
import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputObject;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStreamBase;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.CacheTable;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdFactory;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdStrings;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdUtility;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.pept.transport.ByteBufferPool;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.orb.ORBVersionFactory;
import org.jboss.com.sun.org.omg.CORBA.portable.ValueHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CustomMarshal;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA.portable.CustomValue;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.StreamableValue;
import org.omg.CORBA.portable.ValueBase;

public class CDROutputStream_1_0
extends CDROutputStreamBase {
    private static final int INDIRECTION_TAG = -1;
    protected boolean littleEndian;
    protected BufferManagerWrite bufferManagerWrite;
    ByteBufferWithInfo bbwi;
    protected ORB orb;
    protected ORBUtilSystemException wrapper;
    protected boolean debug = false;
    protected int blockSizeIndex = -1;
    protected int blockSizePosition = 0;
    protected byte streamFormatVersion;
    private static final String kWriteMethod = "write";
    private CacheTable codebaseCache = null;
    private CacheTable valueCache = null;
    private CacheTable repositoryIdCache = null;
    private int end_flag = 0;
    private int chunkedValueNestingLevel = 0;
    private boolean mustChunk = false;
    protected boolean inBlock = false;
    private int end_flag_position = 0;
    private int end_flag_index = 0;
    private ValueHandler valueHandler = null;
    private RepositoryIdUtility repIdUtil;
    private RepositoryIdStrings repIdStrs;
    private CodeSetConversion.CTBConverter charConverter;
    private CodeSetConversion.CTBConverter wcharConverter;
    private static final String _id = "IDL:omg.org/CORBA/DataOutputStream:1.0";
    private static final String[] _ids = new String[]{"IDL:omg.org/CORBA/DataOutputStream:1.0"};

    @Override
    public void init(org.omg.CORBA.ORB orb, boolean littleEndian, BufferManagerWrite bufferManager, byte streamFormatVersion, boolean usePooledByteBuffers) {
        this.orb = (ORB)orb;
        this.wrapper = ORBUtilSystemException.get(this.orb, "rpc.encoding");
        this.debug = this.orb.transportDebugFlag;
        this.littleEndian = littleEndian;
        this.bufferManagerWrite = bufferManager;
        this.bbwi = new ByteBufferWithInfo(orb, bufferManager, usePooledByteBuffers);
        this.streamFormatVersion = streamFormatVersion;
        this.createRepositoryIdHandlers();
    }

    public void init(org.omg.CORBA.ORB orb, boolean littleEndian, BufferManagerWrite bufferManager, byte streamFormatVersion) {
        this.init(orb, littleEndian, bufferManager, streamFormatVersion, true);
    }

    private final void createRepositoryIdHandlers() {
        if (this.orb != null) {
            this.repIdUtil = RepositoryIdFactory.getRepIdUtility(this.orb);
            this.repIdStrs = RepositoryIdFactory.getRepIdStringsFactory(this.orb);
        } else {
            this.repIdUtil = RepositoryIdFactory.getRepIdUtility();
            this.repIdStrs = RepositoryIdFactory.getRepIdStringsFactory();
        }
    }

    @Override
    public BufferManagerWrite getBufferManager() {
        return this.bufferManagerWrite;
    }

    @Override
    public byte[] toByteArray() {
        byte[] it = new byte[this.bbwi.position()];
        for (int i = 0; i < this.bbwi.position(); ++i) {
            it[i] = this.bbwi.byteBuffer.get(i);
        }
        return it;
    }

    @Override
    public GIOPVersion getGIOPVersion() {
        return GIOPVersion.V1_0;
    }

    @Override
    void setHeaderPadding(boolean headerPadding) {
        throw this.wrapper.giopVersionError();
    }

    protected void handleSpecialChunkBegin(int requiredSize) {
    }

    protected void handleSpecialChunkEnd() {
    }

    protected final int computeAlignment(int align) {
        int incr;
        if (align > 1 && (incr = this.bbwi.position() & align - 1) != 0) {
            return align - incr;
        }
        return 0;
    }

    protected void alignAndReserve(int align, int n) {
        this.bbwi.position(this.bbwi.position() + this.computeAlignment(align));
        if (this.bbwi.position() + n > this.bbwi.buflen) {
            this.grow(align, n);
        }
    }

    protected void grow(int align, int n) {
        this.bbwi.needed = n;
        this.bufferManagerWrite.overflow(this.bbwi);
    }

    @Override
    public final void putEndian() throws SystemException {
        this.write_boolean(this.littleEndian);
    }

    public final boolean littleEndian() {
        return this.littleEndian;
    }

    @Override
    void freeInternalCaches() {
        if (this.codebaseCache != null) {
            this.codebaseCache.done();
        }
        if (this.valueCache != null) {
            this.valueCache.done();
        }
        if (this.repositoryIdCache != null) {
            this.repositoryIdCache.done();
        }
    }

    @Override
    public final void write_longdouble(double x) {
        throw this.wrapper.longDoubleNotImplemented(CompletionStatus.COMPLETED_MAYBE);
    }

    @Override
    public void write_octet(byte x) {
        this.alignAndReserve(1, 1);
        this.bbwi.byteBuffer.put(this.bbwi.position(), x);
        this.bbwi.position(this.bbwi.position() + 1);
    }

    @Override
    public final void write_boolean(boolean x) {
        this.write_octet(x ? (byte)1 : 0);
    }

    @Override
    public void write_char(char x) {
        CodeSetConversion.CTBConverter converter = this.getCharConverter();
        converter.convert(x);
        if (converter.getNumBytes() > 1) {
            throw this.wrapper.invalidSingleCharCtb(CompletionStatus.COMPLETED_MAYBE);
        }
        this.write_octet(converter.getBytes()[0]);
    }

    private final void writeLittleEndianWchar(char x) {
        this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(x & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(x >>> 8 & 0xFF));
        this.bbwi.position(this.bbwi.position() + 2);
    }

    private final void writeBigEndianWchar(char x) {
        this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(x >>> 8 & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(x & 0xFF));
        this.bbwi.position(this.bbwi.position() + 2);
    }

    private final void writeLittleEndianShort(short x) {
        this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(x & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(x >>> 8 & 0xFF));
        this.bbwi.position(this.bbwi.position() + 2);
    }

    private final void writeBigEndianShort(short x) {
        this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(x >>> 8 & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(x & 0xFF));
        this.bbwi.position(this.bbwi.position() + 2);
    }

    private final void writeLittleEndianLong(int x) {
        this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(x & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(x >>> 8 & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 2, (byte)(x >>> 16 & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 3, (byte)(x >>> 24 & 0xFF));
        this.bbwi.position(this.bbwi.position() + 4);
    }

    private final void writeBigEndianLong(int x) {
        this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(x >>> 24 & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(x >>> 16 & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 2, (byte)(x >>> 8 & 0xFF));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 3, (byte)(x & 0xFF));
        this.bbwi.position(this.bbwi.position() + 4);
    }

    private final void writeLittleEndianLongLong(long x) {
        this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(x & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(x >>> 8 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 2, (byte)(x >>> 16 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 3, (byte)(x >>> 24 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 4, (byte)(x >>> 32 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 5, (byte)(x >>> 40 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 6, (byte)(x >>> 48 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 7, (byte)(x >>> 56 & 0xFFL));
        this.bbwi.position(this.bbwi.position() + 8);
    }

    private final void writeBigEndianLongLong(long x) {
        this.bbwi.byteBuffer.put(this.bbwi.position(), (byte)(x >>> 56 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 1, (byte)(x >>> 48 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 2, (byte)(x >>> 40 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 3, (byte)(x >>> 32 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 4, (byte)(x >>> 24 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 5, (byte)(x >>> 16 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 6, (byte)(x >>> 8 & 0xFFL));
        this.bbwi.byteBuffer.put(this.bbwi.position() + 7, (byte)(x & 0xFFL));
        this.bbwi.position(this.bbwi.position() + 8);
    }

    @Override
    public void write_wchar(char x) {
        if (ORBUtility.isForeignORB(this.orb)) {
            throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
        }
        this.alignAndReserve(2, 2);
        if (this.littleEndian) {
            this.writeLittleEndianWchar(x);
        } else {
            this.writeBigEndianWchar(x);
        }
    }

    @Override
    public void write_short(short x) {
        this.alignAndReserve(2, 2);
        if (this.littleEndian) {
            this.writeLittleEndianShort(x);
        } else {
            this.writeBigEndianShort(x);
        }
    }

    @Override
    public final void write_ushort(short x) {
        this.write_short(x);
    }

    @Override
    public void write_long(int x) {
        this.alignAndReserve(4, 4);
        if (this.littleEndian) {
            this.writeLittleEndianLong(x);
        } else {
            this.writeBigEndianLong(x);
        }
    }

    @Override
    public final void write_ulong(int x) {
        this.write_long(x);
    }

    @Override
    public void write_longlong(long x) {
        this.alignAndReserve(8, 8);
        if (this.littleEndian) {
            this.writeLittleEndianLongLong(x);
        } else {
            this.writeBigEndianLongLong(x);
        }
    }

    @Override
    public final void write_ulonglong(long x) {
        this.write_longlong(x);
    }

    @Override
    public final void write_float(float x) {
        this.write_long(Float.floatToIntBits(x));
    }

    @Override
    public final void write_double(double x) {
        this.write_longlong(Double.doubleToLongBits(x));
    }

    @Override
    public void write_string(String value) {
        this.writeString(value);
    }

    protected int writeString(String value) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        CodeSetConversion.CTBConverter converter = this.getCharConverter();
        converter.convert(value);
        int len = converter.getNumBytes() + 1;
        this.handleSpecialChunkBegin(this.computeAlignment(4) + 4 + len);
        this.write_long(len);
        int indirection = this.get_offset() - 4;
        this.internalWriteOctetArray(converter.getBytes(), 0, converter.getNumBytes());
        this.write_octet((byte)0);
        this.handleSpecialChunkEnd();
        return indirection;
    }

    @Override
    public void write_wstring(String value) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        if (ORBUtility.isForeignORB(this.orb)) {
            throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
        }
        int len = value.length() + 1;
        this.handleSpecialChunkBegin(4 + len * 2 + this.computeAlignment(4));
        this.write_long(len);
        for (int i = 0; i < len - 1; ++i) {
            this.write_wchar(value.charAt(i));
        }
        this.write_short((short)0);
        this.handleSpecialChunkEnd();
    }

    void internalWriteOctetArray(byte[] value, int offset, int length) {
        int bytes;
        boolean align = true;
        for (int n = offset; n < length + offset; n += bytes) {
            int avail;
            int wanted;
            if (this.bbwi.position() + 1 > this.bbwi.buflen || align) {
                align = false;
                this.alignAndReserve(1, 1);
            }
            bytes = (wanted = length + offset - n) < (avail = this.bbwi.buflen - this.bbwi.position()) ? wanted : avail;
            for (int i = 0; i < bytes; ++i) {
                this.bbwi.byteBuffer.put(this.bbwi.position() + i, value[n + i]);
            }
            this.bbwi.position(this.bbwi.position() + bytes);
        }
    }

    @Override
    public final void write_octet_array(byte[] b, int offset, int length) {
        if (b == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.handleSpecialChunkBegin(length);
        this.internalWriteOctetArray(b, offset, length);
        this.handleSpecialChunkEnd();
    }

    @Override
    public void write_Principal(Principal p) {
        this.write_long(p.name().length);
        this.write_octet_array(p.name(), 0, p.name().length);
    }

    @Override
    public void write_any(Any any) {
        if (any == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.write_TypeCode(any.type());
        any.write_value(this.parent);
    }

    @Override
    public void write_TypeCode(TypeCode tc) {
        if (tc == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        TypeCodeImpl tci = tc instanceof TypeCodeImpl ? (TypeCodeImpl)tc : new TypeCodeImpl(this.orb, tc);
        tci.write_value(this.parent);
    }

    @Override
    public void write_Object(Object ref) {
        if (ref == null) {
            IOR nullIOR = IORFactories.makeIOR(this.orb);
            nullIOR.write(this.parent);
            return;
        }
        if (ref instanceof LocalObject) {
            throw this.wrapper.writeLocalObject(CompletionStatus.COMPLETED_MAYBE);
        }
        IOR ior = ORBUtility.connectAndGetIOR(this.orb, ref);
        ior.write(this.parent);
    }

    @Override
    public void write_abstract_interface(java.lang.Object obj) {
        boolean corbaObject = false;
        Object theObject = null;
        if (obj != null && obj instanceof Object) {
            theObject = (Object)obj;
            corbaObject = true;
        }
        this.write_boolean(corbaObject);
        if (corbaObject) {
            this.write_Object(theObject);
        } else {
            try {
                this.write_value((Serializable)obj);
            }
            catch (ClassCastException cce) {
                if (obj instanceof Serializable) {
                    throw cce;
                }
                ORBUtility.throwNotSerializableForCorba(obj.getClass().getName());
            }
        }
    }

    @Override
    public void write_value(Serializable object, Class<?> clz) {
        this.write_value(object);
    }

    private void writeWStringValue(String string) {
        int indirection = this.writeValueTag(this.mustChunk, true, null);
        this.write_repositoryId(this.repIdStrs.getWStringValueRepId());
        this.updateIndirectionTable(indirection, string, string);
        if (this.mustChunk) {
            this.start_block();
            --this.end_flag;
            --this.chunkedValueNestingLevel;
        } else {
            --this.end_flag;
        }
        this.write_wstring(string);
        if (this.mustChunk) {
            this.end_block();
        }
        this.writeEndTag(this.mustChunk);
    }

    private void writeArray(Serializable array, Class<?> clazz) {
        if (this.valueHandler == null) {
            this.valueHandler = ORBUtility.createValueHandler(this.orb);
        }
        int indirection = this.writeValueTag(this.mustChunk, true, Util.getCodebase(clazz));
        this.write_repositoryId(this.repIdStrs.createSequenceRepID(clazz));
        this.updateIndirectionTable(indirection, array, array);
        if (this.mustChunk) {
            this.start_block();
            --this.end_flag;
            --this.chunkedValueNestingLevel;
        } else {
            --this.end_flag;
        }
        if (this.valueHandler instanceof ValueHandlerMultiFormat) {
            ValueHandlerMultiFormat vh = (ValueHandlerMultiFormat)this.valueHandler;
            vh.writeValue(this.parent, array, this.streamFormatVersion);
        } else {
            this.valueHandler.writeValue(this.parent, array);
        }
        if (this.mustChunk) {
            this.end_block();
        }
        this.writeEndTag(this.mustChunk);
    }

    private void writeValueBase(ValueBase object, Class<?> clazz) {
        this.mustChunk = true;
        int indirection = this.writeValueTag(true, true, Util.getCodebase(clazz));
        String repId = object._truncatable_ids()[0];
        this.write_repositoryId(repId);
        this.updateIndirectionTable(indirection, object, object);
        this.start_block();
        --this.end_flag;
        --this.chunkedValueNestingLevel;
        this.writeIDLValue(object, repId);
        this.end_block();
        this.writeEndTag(true);
    }

    private void writeRMIIIOPValueType(Serializable object, Class<?> clazz) {
        Serializable key;
        if (this.valueHandler == null) {
            this.valueHandler = ORBUtility.createValueHandler(this.orb);
        }
        if ((object = this.valueHandler.writeReplace(key = object)) == null) {
            this.write_long(0);
            return;
        }
        if (object != key) {
            if (this.valueCache != null && this.valueCache.containsKey(object)) {
                this.writeIndirection(-1, this.valueCache.getVal(object));
                return;
            }
            clazz = object.getClass();
        }
        if (this.mustChunk || this.valueHandler.isCustomMarshaled(clazz)) {
            this.mustChunk = true;
        }
        int indirection = this.writeValueTag(this.mustChunk, true, Util.getCodebase(clazz));
        this.write_repositoryId(this.repIdStrs.createForJavaType(clazz));
        this.updateIndirectionTable(indirection, object, key);
        if (this.mustChunk) {
            --this.end_flag;
            --this.chunkedValueNestingLevel;
            this.start_block();
        } else {
            --this.end_flag;
        }
        if (this.valueHandler instanceof ValueHandlerMultiFormat) {
            ValueHandlerMultiFormat vh = (ValueHandlerMultiFormat)this.valueHandler;
            vh.writeValue(this.parent, object, this.streamFormatVersion);
        } else {
            this.valueHandler.writeValue(this.parent, object);
        }
        if (this.mustChunk) {
            this.end_block();
        }
        this.writeEndTag(this.mustChunk);
    }

    @Override
    public void write_value(Serializable object, String repository_id) {
        if (object == null) {
            this.write_long(0);
            return;
        }
        if (this.valueCache != null && this.valueCache.containsKey(object)) {
            this.writeIndirection(-1, this.valueCache.getVal(object));
            return;
        }
        Class<?> clazz = object.getClass();
        boolean oldMustChunk = this.mustChunk;
        if (this.mustChunk) {
            this.mustChunk = true;
        }
        if (this.inBlock) {
            this.end_block();
        }
        if (clazz.isArray()) {
            this.writeArray(object, clazz);
        } else if (object instanceof ValueBase) {
            this.writeValueBase((ValueBase)object, clazz);
        } else if (this.shouldWriteAsIDLEntity(object)) {
            this.writeIDLEntity((IDLEntity)object);
        } else if (object instanceof String) {
            this.writeWStringValue((String)((java.lang.Object)object));
        } else if (object instanceof Class) {
            this.writeClass(repository_id, (Class)object);
        } else {
            this.writeRMIIIOPValueType(object, clazz);
        }
        this.mustChunk = oldMustChunk;
        if (this.mustChunk) {
            this.start_block();
        }
    }

    @Override
    public void write_value(Serializable object) {
        this.write_value(object, (String)null);
    }

    @Override
    public void write_value(Serializable object, BoxedValueHelper factory2) {
        int indirection;
        if (object == null) {
            this.write_long(0);
            return;
        }
        if (this.valueCache != null && this.valueCache.containsKey(object)) {
            this.writeIndirection(-1, this.valueCache.getVal(object));
            return;
        }
        boolean oldMustChunk = this.mustChunk;
        boolean isCustom = false;
        if (factory2 instanceof ValueHelper) {
            short modifier;
            try {
                modifier = ((ValueHelper)factory2).get_type().type_modifier();
            }
            catch (BadKind ex) {
                modifier = 0;
            }
            if (object instanceof CustomMarshal && modifier == 1) {
                isCustom = true;
                this.mustChunk = true;
            }
            if (modifier == 3) {
                this.mustChunk = true;
            }
        }
        if (this.mustChunk) {
            if (this.inBlock) {
                this.end_block();
            }
            indirection = this.writeValueTag(true, this.orb.getORBData().useRepId(), Util.getCodebase(object.getClass()));
            if (this.orb.getORBData().useRepId()) {
                this.write_repositoryId(factory2.get_id());
            }
            this.updateIndirectionTable(indirection, object, object);
            this.start_block();
            --this.end_flag;
            --this.chunkedValueNestingLevel;
            if (isCustom) {
                ((CustomMarshal)object).marshal(this.parent);
            } else {
                factory2.write_value(this.parent, object);
            }
            this.end_block();
            this.writeEndTag(true);
        } else {
            indirection = this.writeValueTag(false, this.orb.getORBData().useRepId(), Util.getCodebase(object.getClass()));
            if (this.orb.getORBData().useRepId()) {
                this.write_repositoryId(factory2.get_id());
            }
            this.updateIndirectionTable(indirection, object, object);
            --this.end_flag;
            factory2.write_value(this.parent, object);
            this.writeEndTag(false);
        }
        this.mustChunk = oldMustChunk;
        if (this.mustChunk) {
            this.start_block();
        }
    }

    public int get_offset() {
        return this.bbwi.position();
    }

    @Override
    public void start_block() {
        if (this.debug) {
            this.dprint("CDROutputStream_1_0 start_block, position" + this.bbwi.position());
        }
        this.write_long(0);
        this.inBlock = true;
        this.blockSizePosition = this.get_offset();
        this.blockSizeIndex = this.bbwi.position();
        if (this.debug) {
            this.dprint("CDROutputStream_1_0 start_block, blockSizeIndex " + this.blockSizeIndex);
        }
    }

    protected void writeLongWithoutAlign(int x) {
        if (this.littleEndian) {
            this.writeLittleEndianLong(x);
        } else {
            this.writeBigEndianLong(x);
        }
    }

    @Override
    public void end_block() {
        if (this.debug) {
            this.dprint("CDROutputStream_1_0.java end_block");
        }
        if (!this.inBlock) {
            return;
        }
        if (this.debug) {
            this.dprint("CDROutputStream_1_0.java end_block, in a block");
        }
        this.inBlock = false;
        if (this.get_offset() == this.blockSizePosition) {
            this.bbwi.position(this.bbwi.position() - 4);
            this.blockSizeIndex = -1;
            this.blockSizePosition = -1;
            return;
        }
        int oldSize = this.bbwi.position();
        this.bbwi.position(this.blockSizeIndex - 4);
        this.writeLongWithoutAlign(oldSize - this.blockSizeIndex);
        this.bbwi.position(oldSize);
        this.blockSizeIndex = -1;
        this.blockSizePosition = -1;
    }

    @Override
    public org.omg.CORBA.ORB orb() {
        return this.orb;
    }

    @Override
    public final void write_boolean_array(boolean[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.handleSpecialChunkBegin(length);
        for (int i = 0; i < length; ++i) {
            this.write_boolean(value[offset + i]);
        }
        this.handleSpecialChunkEnd();
    }

    @Override
    public final void write_char_array(char[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.handleSpecialChunkBegin(length);
        for (int i = 0; i < length; ++i) {
            this.write_char(value[offset + i]);
        }
        this.handleSpecialChunkEnd();
    }

    @Override
    public void write_wchar_array(char[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.handleSpecialChunkBegin(this.computeAlignment(2) + length * 2);
        for (int i = 0; i < length; ++i) {
            this.write_wchar(value[offset + i]);
        }
        this.handleSpecialChunkEnd();
    }

    @Override
    public final void write_short_array(short[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.handleSpecialChunkBegin(this.computeAlignment(2) + length * 2);
        for (int i = 0; i < length; ++i) {
            this.write_short(value[offset + i]);
        }
        this.handleSpecialChunkEnd();
    }

    @Override
    public final void write_ushort_array(short[] value, int offset, int length) {
        this.write_short_array(value, offset, length);
    }

    @Override
    public final void write_long_array(int[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.handleSpecialChunkBegin(this.computeAlignment(4) + length * 4);
        for (int i = 0; i < length; ++i) {
            this.write_long(value[offset + i]);
        }
        this.handleSpecialChunkEnd();
    }

    @Override
    public final void write_ulong_array(int[] value, int offset, int length) {
        this.write_long_array(value, offset, length);
    }

    @Override
    public final void write_longlong_array(long[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.handleSpecialChunkBegin(this.computeAlignment(8) + length * 8);
        for (int i = 0; i < length; ++i) {
            this.write_longlong(value[offset + i]);
        }
        this.handleSpecialChunkEnd();
    }

    @Override
    public final void write_ulonglong_array(long[] value, int offset, int length) {
        this.write_longlong_array(value, offset, length);
    }

    @Override
    public final void write_float_array(float[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.handleSpecialChunkBegin(this.computeAlignment(4) + length * 4);
        for (int i = 0; i < length; ++i) {
            this.write_float(value[offset + i]);
        }
        this.handleSpecialChunkEnd();
    }

    @Override
    public final void write_double_array(double[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.handleSpecialChunkBegin(this.computeAlignment(8) + length * 8);
        for (int i = 0; i < length; ++i) {
            this.write_double(value[offset + i]);
        }
        this.handleSpecialChunkEnd();
    }

    public void write_string_array(String[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        for (int i = 0; i < length; ++i) {
            this.write_string(value[offset + i]);
        }
    }

    public void write_wstring_array(String[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        for (int i = 0; i < length; ++i) {
            this.write_wstring(value[offset + i]);
        }
    }

    @Override
    public final void write_any_array(Any[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            this.write_any(value[offset + i]);
        }
    }

    @Override
    public void writeTo(OutputStream s2) throws IOException {
        byte[] tmpBuf = null;
        if (this.bbwi.byteBuffer.hasArray()) {
            tmpBuf = this.bbwi.byteBuffer.array();
        } else {
            int size = this.bbwi.position();
            tmpBuf = new byte[size];
            for (int i = 0; i < size; ++i) {
                tmpBuf[i] = this.bbwi.byteBuffer.get(i);
            }
        }
        s2.write(tmpBuf, 0, this.bbwi.position());
    }

    @Override
    public void writeOctetSequenceTo(org.omg.CORBA.portable.OutputStream s2) {
        byte[] buf = null;
        if (this.bbwi.byteBuffer.hasArray()) {
            buf = this.bbwi.byteBuffer.array();
        } else {
            int size = this.bbwi.position();
            buf = new byte[size];
            for (int i = 0; i < size; ++i) {
                buf[i] = this.bbwi.byteBuffer.get(i);
            }
        }
        s2.write_long(this.bbwi.position());
        s2.write_octet_array(buf, 0, this.bbwi.position());
    }

    @Override
    public final int getSize() {
        return this.bbwi.position();
    }

    @Override
    public int getIndex() {
        return this.bbwi.position();
    }

    @Override
    public boolean isLittleEndian() {
        return this.littleEndian;
    }

    @Override
    public void setIndex(int value) {
        this.bbwi.position(value);
    }

    @Override
    public ByteBufferWithInfo getByteBufferWithInfo() {
        return this.bbwi;
    }

    @Override
    public void setByteBufferWithInfo(ByteBufferWithInfo bbwi) {
        this.bbwi = bbwi;
    }

    @Override
    public ByteBuffer getByteBuffer() {
        ByteBuffer result = null;
        if (this.bbwi != null) {
            result = this.bbwi.byteBuffer;
        }
        return result;
    }

    @Override
    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.bbwi.byteBuffer = byteBuffer;
    }

    private final void updateIndirectionTable(int indirection, java.lang.Object object, java.lang.Object key) {
        if (this.valueCache == null) {
            this.valueCache = new CacheTable(this.orb, true);
        }
        this.valueCache.put(object, indirection);
        if (key != object) {
            this.valueCache.put(key, indirection);
        }
    }

    private final void write_repositoryId(String id) {
        if (this.repositoryIdCache != null && this.repositoryIdCache.containsKey(id)) {
            this.writeIndirection(-1, this.repositoryIdCache.getVal(id));
            return;
        }
        int indirection = this.writeString(id);
        if (this.repositoryIdCache == null) {
            this.repositoryIdCache = new CacheTable(this.orb, true);
        }
        this.repositoryIdCache.put(id, indirection);
    }

    private void write_codebase(String str, int pos) {
        if (this.codebaseCache != null && this.codebaseCache.containsKey(str)) {
            this.writeIndirection(-1, this.codebaseCache.getVal(str));
        } else {
            this.write_string(str);
            if (this.codebaseCache == null) {
                this.codebaseCache = new CacheTable(this.orb, true);
            }
            this.codebaseCache.put(str, pos);
        }
    }

    private final int writeValueTag(boolean chunkIt, boolean useRepId, String codebase) {
        int indirection = 0;
        if (chunkIt && !useRepId) {
            if (codebase == null) {
                this.write_long(this.repIdUtil.getStandardRMIChunkedNoRepStrId());
                indirection = this.get_offset() - 4;
            } else {
                this.write_long(this.repIdUtil.getCodeBaseRMIChunkedNoRepStrId());
                indirection = this.get_offset() - 4;
                this.write_codebase(codebase, this.get_offset());
            }
        } else if (chunkIt && useRepId) {
            if (codebase == null) {
                this.write_long(this.repIdUtil.getStandardRMIChunkedId());
                indirection = this.get_offset() - 4;
            } else {
                this.write_long(this.repIdUtil.getCodeBaseRMIChunkedId());
                indirection = this.get_offset() - 4;
                this.write_codebase(codebase, this.get_offset());
            }
        } else if (!chunkIt && !useRepId) {
            if (codebase == null) {
                this.write_long(this.repIdUtil.getStandardRMIUnchunkedNoRepStrId());
                indirection = this.get_offset() - 4;
            } else {
                this.write_long(this.repIdUtil.getCodeBaseRMIUnchunkedNoRepStrId());
                indirection = this.get_offset() - 4;
                this.write_codebase(codebase, this.get_offset());
            }
        } else if (!chunkIt && useRepId) {
            if (codebase == null) {
                this.write_long(this.repIdUtil.getStandardRMIUnchunkedId());
                indirection = this.get_offset() - 4;
            } else {
                this.write_long(this.repIdUtil.getCodeBaseRMIUnchunkedId());
                indirection = this.get_offset() - 4;
                this.write_codebase(codebase, this.get_offset());
            }
        }
        return indirection;
    }

    private void writeIDLValue(Serializable object, String repID) {
        if (object instanceof StreamableValue) {
            ((StreamableValue)object)._write(this.parent);
        } else if (object instanceof CustomValue) {
            ((CustomValue)object).marshal(this.parent);
        } else {
            BoxedValueHelper helper = Utility.getHelper(object.getClass(), null, repID);
            boolean isCustom = false;
            if (helper instanceof ValueHelper && object instanceof CustomMarshal) {
                try {
                    if (((ValueHelper)helper).get_type().type_modifier() == 1) {
                        isCustom = true;
                    }
                }
                catch (BadKind ex) {
                    throw this.wrapper.badTypecodeForCustomValue(CompletionStatus.COMPLETED_MAYBE, ex);
                }
            }
            if (isCustom) {
                ((CustomMarshal)object).marshal(this.parent);
            } else {
                helper.write_value(this.parent, object);
            }
        }
    }

    private void writeEndTag(boolean chunked) {
        if (chunked) {
            if (this.get_offset() == this.end_flag_position && this.bbwi.position() == this.end_flag_index) {
                this.bbwi.position(this.bbwi.position() - 4);
            }
            this.writeNestingLevel();
            this.end_flag_index = this.bbwi.position();
            this.end_flag_position = this.get_offset();
            ++this.chunkedValueNestingLevel;
        }
        ++this.end_flag;
    }

    private void writeNestingLevel() {
        if (this.orb == null || ORBVersionFactory.getFOREIGN().equals(this.orb.getORBVersion()) || ORBVersionFactory.getNEWER().compareTo(this.orb.getORBVersion()) <= 0) {
            this.write_long(this.chunkedValueNestingLevel);
        } else {
            this.write_long(this.end_flag);
        }
    }

    private void writeClass(String repository_id, Class<?> clz) {
        if (repository_id == null) {
            repository_id = this.repIdStrs.getClassDescValueRepId();
        }
        int indirection = this.writeValueTag(this.mustChunk, true, null);
        this.updateIndirectionTable(indirection, clz, clz);
        this.write_repositoryId(repository_id);
        if (this.mustChunk) {
            this.start_block();
            --this.end_flag;
            --this.chunkedValueNestingLevel;
        } else {
            --this.end_flag;
        }
        this.writeClassBody(clz);
        if (this.mustChunk) {
            this.end_block();
        }
        this.writeEndTag(this.mustChunk);
    }

    private void writeClassBody(Class<?> clz) {
        if (this.orb == null || ORBVersionFactory.getFOREIGN().equals(this.orb.getORBVersion()) || ORBVersionFactory.getNEWER().compareTo(this.orb.getORBVersion()) <= 0) {
            this.write_value((Serializable)((java.lang.Object)Util.getCodebase(clz)));
            this.write_value((Serializable)((java.lang.Object)this.repIdStrs.createForAnyType(clz)));
        } else {
            this.write_value((Serializable)((java.lang.Object)this.repIdStrs.createForAnyType(clz)));
            this.write_value((Serializable)((java.lang.Object)Util.getCodebase(clz)));
        }
    }

    private boolean shouldWriteAsIDLEntity(Serializable object) {
        return object instanceof IDLEntity && !(object instanceof ValueBase) && !(object instanceof Object);
    }

    private void writeIDLEntity(IDLEntity object) {
        this.mustChunk = true;
        String repository_id = this.repIdStrs.createForJavaType(object);
        Class<?> clazz = object.getClass();
        String codebase = Util.getCodebase(clazz);
        int indirection = this.writeValueTag(true, true, codebase);
        this.updateIndirectionTable(indirection, object, object);
        this.write_repositoryId(repository_id);
        --this.end_flag;
        --this.chunkedValueNestingLevel;
        this.start_block();
        try {
            ClassLoader clazzLoader = clazz == null ? null : clazz.getClassLoader();
            final Class<?> helperClass = Utility.loadClassForClass(clazz.getName() + "Helper", codebase, clazzLoader, clazz, clazzLoader);
            final Class[] argTypes = new Class[]{org.omg.CORBA.portable.OutputStream.class, clazz};
            Method writeMethod = null;
            try {
                writeMethod = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                    @Override
                    public Method run() throws NoSuchMethodException {
                        return helperClass.getDeclaredMethod(CDROutputStream_1_0.kWriteMethod, argTypes);
                    }
                });
            }
            catch (PrivilegedActionException pae) {
                throw (NoSuchMethodException)pae.getException();
            }
            java.lang.Object[] args2 = new java.lang.Object[]{this.parent, object};
            writeMethod.invoke(null, args2);
        }
        catch (ClassNotFoundException cnfe) {
            throw this.wrapper.errorInvokingHelperWrite(CompletionStatus.COMPLETED_MAYBE, cnfe);
        }
        catch (NoSuchMethodException nsme) {
            throw this.wrapper.errorInvokingHelperWrite(CompletionStatus.COMPLETED_MAYBE, nsme);
        }
        catch (IllegalAccessException iae) {
            throw this.wrapper.errorInvokingHelperWrite(CompletionStatus.COMPLETED_MAYBE, iae);
        }
        catch (InvocationTargetException ite) {
            throw this.wrapper.errorInvokingHelperWrite(CompletionStatus.COMPLETED_MAYBE, ite);
        }
        this.end_block();
        this.writeEndTag(true);
    }

    @Override
    public void write_Abstract(java.lang.Object value) {
        this.write_abstract_interface(value);
    }

    @Override
    public void write_Value(Serializable value) {
        this.write_value(value);
    }

    @Override
    public void write_fixed(BigDecimal bigDecimal, short digits, short scale) {
        String fractionPart;
        String integerPart;
        int dotIndex;
        String string = bigDecimal.toString();
        if (string.charAt(0) == '-' || string.charAt(0) == '+') {
            string = string.substring(1);
        }
        if ((dotIndex = string.indexOf(46)) == -1) {
            integerPart = string;
            fractionPart = null;
        } else if (dotIndex == 0) {
            integerPart = null;
            fractionPart = string;
        } else {
            integerPart = string.substring(0, dotIndex);
            fractionPart = string.substring(dotIndex + 1);
        }
        StringBuffer stringBuffer = new StringBuffer(digits);
        if (fractionPart != null) {
            stringBuffer.append(fractionPart);
        }
        while (stringBuffer.length() < scale) {
            stringBuffer.append('0');
        }
        if (integerPart != null) {
            stringBuffer.insert(0, integerPart);
        }
        while (stringBuffer.length() < digits) {
            stringBuffer.insert(0, '0');
        }
        this.write_fixed(stringBuffer.toString(), bigDecimal.signum());
    }

    @Override
    public void write_fixed(BigDecimal bigDecimal) {
        this.write_fixed(bigDecimal.toString(), bigDecimal.signum());
    }

    public void write_fixed(String string, int signum) {
        char ch;
        int i;
        int stringLength = string.length();
        byte doubleDigit = 0;
        int numDigits = 0;
        for (i = 0; i < stringLength; ++i) {
            ch = string.charAt(i);
            if (ch == '-' || ch == '+' || ch == '.') continue;
            ++numDigits;
        }
        for (i = 0; i < stringLength; ++i) {
            ch = string.charAt(i);
            if (ch == '-' || ch == '+' || ch == '.') continue;
            byte digit = (byte)Character.digit(ch, 10);
            if (digit == -1) {
                throw this.wrapper.badDigitInFixed(CompletionStatus.COMPLETED_MAYBE);
            }
            if (numDigits % 2 == 0) {
                doubleDigit = (byte)(doubleDigit | digit);
                this.write_octet(doubleDigit);
                doubleDigit = 0;
            } else {
                doubleDigit = (byte)(doubleDigit | digit << 4);
            }
            --numDigits;
        }
        doubleDigit = signum == -1 ? (byte)((byte)(doubleDigit | 0xD)) : (byte)((byte)(doubleDigit | 0xC));
        this.write_octet(doubleDigit);
    }

    @Override
    public String[] _truncatable_ids() {
        if (_ids == null) {
            return null;
        }
        return (String[])_ids.clone();
    }

    @Override
    public void printBuffer() {
        CDROutputStream_1_0.printBuffer(this.bbwi);
    }

    public static void printBuffer(ByteBufferWithInfo bbwi) {
        System.out.println("+++++++ Output Buffer ++++++++");
        System.out.println();
        System.out.println("Current position: " + bbwi.position());
        System.out.println("Total length : " + bbwi.buflen);
        System.out.println();
        char[] charBuf = new char[16];
        try {
            for (int i = 0; i < bbwi.position(); i += 16) {
                int x;
                int j;
                for (j = 0; j < 16 && j + i < bbwi.position(); ++j) {
                    String hex;
                    int k = bbwi.byteBuffer.get(i + j);
                    if (k < 0) {
                        k = 256 + k;
                    }
                    if ((hex = Integer.toHexString(k)).length() == 1) {
                        hex = "0" + hex;
                    }
                    System.out.print(hex + " ");
                }
                while (j < 16) {
                    System.out.print("   ");
                    ++j;
                }
                for (x = 0; x < 16 && x + i < bbwi.position(); ++x) {
                    charBuf[x] = ORBUtility.isPrintable((char)bbwi.byteBuffer.get(i + x)) ? (int)bbwi.byteBuffer.get(i + x) : 46;
                }
                System.out.println(new String(charBuf, 0, x));
            }
        }
        catch (Throwable t2) {
            t2.printStackTrace();
        }
        System.out.println("++++++++++++++++++++++++++++++");
    }

    @Override
    public void writeIndirection(int tag, int posIndirectedTo) {
        this.handleSpecialChunkBegin(this.computeAlignment(4) + 8);
        this.write_long(tag);
        this.write_long(posIndirectedTo - this.parent.getRealIndex(this.get_offset()));
        this.handleSpecialChunkEnd();
    }

    protected CodeSetConversion.CTBConverter getCharConverter() {
        if (this.charConverter == null) {
            this.charConverter = this.parent.createCharCTBConverter();
        }
        return this.charConverter;
    }

    protected CodeSetConversion.CTBConverter getWCharConverter() {
        if (this.wcharConverter == null) {
            this.wcharConverter = this.parent.createWCharCTBConverter();
        }
        return this.wcharConverter;
    }

    protected void dprint(String msg) {
        if (this.debug) {
            ORBUtility.dprint(this, msg);
        }
    }

    @Override
    void alignOnBoundary(int octetBoundary) {
        this.alignAndReserve(octetBoundary, 0);
    }

    @Override
    public void start_value(String rep_id) {
        if (this.debug) {
            this.dprint("start_value w/ rep id " + rep_id + " called at pos " + this.get_offset() + " position " + this.bbwi.position());
        }
        if (this.inBlock) {
            this.end_block();
        }
        this.writeValueTag(true, true, null);
        this.write_repositoryId(rep_id);
        --this.end_flag;
        --this.chunkedValueNestingLevel;
        this.start_block();
    }

    @Override
    public void end_value() {
        if (this.debug) {
            this.dprint("end_value called at pos " + this.get_offset() + " position " + this.bbwi.position());
        }
        this.end_block();
        this.writeEndTag(true);
        if (this.debug) {
            this.dprint("mustChunk is " + this.mustChunk);
        }
        if (this.mustChunk) {
            this.start_block();
        }
    }

    @Override
    public void close() throws IOException {
        this.getBufferManager().close();
        if (this.getByteBufferWithInfo() != null && this.getByteBuffer() != null) {
            CDRInputObject inputObj;
            int bbHash = System.identityHashCode(this.bbwi.byteBuffer);
            MessageMediator messageMediator = this.parent.getMessageMediator();
            if (messageMediator != null && (inputObj = (CDRInputObject)messageMediator.getInputObject()) != null) {
                ByteBuffer inputBb = inputObj.getByteBuffer();
                int iBbHash = 0;
                if (inputBb != null && bbHash == (iBbHash = System.identityHashCode(inputBb))) {
                    inputObj.setByteBuffer(null);
                    inputObj.setByteBufferWithInfo(null);
                }
            }
            ByteBufferPool byteBufferPool = this.orb.getByteBufferPool();
            if (this.debug) {
                int bbAddress = System.identityHashCode(this.bbwi.byteBuffer);
                StringBuffer sb = new StringBuffer(80);
                sb.append(".close - releasing ByteBuffer id (");
                sb.append(bbAddress).append(") to ByteBufferPool.");
                String msg = sb.toString();
                this.dprint(msg);
            }
            byteBufferPool.releaseByteBuffer(this.getByteBuffer());
            this.bbwi.byteBuffer = null;
            this.bbwi = null;
        }
    }
}

