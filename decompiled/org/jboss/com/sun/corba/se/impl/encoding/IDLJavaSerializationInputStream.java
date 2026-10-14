/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedList;
import org.jboss.com.sun.corba.se.impl.corba.PrincipalImpl;
import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerRead;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStreamBase;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream_1_0;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationDefaults;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.omg.CORBA.Any;
import org.omg.CORBA.AnySeqHolder;
import org.omg.CORBA.BooleanSeqHolder;
import org.omg.CORBA.CharSeqHolder;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.DoubleSeqHolder;
import org.omg.CORBA.FloatSeqHolder;
import org.omg.CORBA.LongLongSeqHolder;
import org.omg.CORBA.LongSeqHolder;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.Object;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA.Principal;
import org.omg.CORBA.ShortSeqHolder;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ULongLongSeqHolder;
import org.omg.CORBA.ULongSeqHolder;
import org.omg.CORBA.UShortSeqHolder;
import org.omg.CORBA.WCharSeqHolder;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA.portable.IDLEntity;

public class IDLJavaSerializationInputStream
extends CDRInputStreamBase {
    private ORB orb;
    private int bufSize;
    private ByteBuffer buffer;
    private ObjectInputStream is;
    private _ByteArrayInputStream bis;
    private BufferManagerRead bufferManager;
    private final int directReadLength = 16;
    private boolean markOn;
    private int peekIndex;
    private int peekCount;
    private LinkedList<java.lang.Object> markedItemQ = new LinkedList();
    protected ORBUtilSystemException wrapper;

    public IDLJavaSerializationInputStream(byte encodingVersion) {
    }

    @Override
    public void init(org.omg.CORBA.ORB orb, ByteBuffer byteBuffer, int bufSize, boolean littleEndian, BufferManagerRead bufferManager) {
        byte[] buf;
        this.orb = (ORB)orb;
        this.bufSize = bufSize;
        this.bufferManager = bufferManager;
        this.buffer = byteBuffer;
        this.wrapper = ORBUtilSystemException.get((ORB)orb, "rpc.encoding");
        if (this.buffer.hasArray()) {
            buf = this.buffer.array();
        } else {
            buf = new byte[bufSize];
            this.buffer.get(buf);
        }
        this.bis = new _ByteArrayInputStream(buf);
    }

    private void initObjectInputStream() {
        if (this.is != null) {
            throw this.wrapper.javaStreamInitFailed();
        }
        try {
            this.is = new MarshalObjectInputStream(this.bis, this.orb);
        }
        catch (Exception e) {
            throw this.wrapper.javaStreamInitFailed(e);
        }
    }

    @Override
    public boolean read_boolean() {
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return (Boolean)this.markedItemQ.removeFirst();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return (Boolean)this.markedItemQ.get(this.peekIndex++);
        }
        try {
            boolean value = this.is.readBoolean();
            if (this.markOn) {
                this.markedItemQ.addLast(value);
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_boolean");
        }
    }

    @Override
    public char read_char() {
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return ((Character)this.markedItemQ.removeFirst()).charValue();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return ((Character)this.markedItemQ.get(this.peekIndex++)).charValue();
        }
        try {
            char value = this.is.readChar();
            if (this.markOn) {
                this.markedItemQ.addLast(new Character(value));
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_char");
        }
    }

    @Override
    public char read_wchar() {
        return this.read_char();
    }

    @Override
    public byte read_octet() {
        if (this.bis.getPosition() < 16) {
            byte b = (byte)this.bis.read();
            if (this.bis.getPosition() == 16) {
                this.initObjectInputStream();
            }
            return b;
        }
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return (Byte)this.markedItemQ.removeFirst();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return (Byte)this.markedItemQ.get(this.peekIndex++);
        }
        try {
            byte value = this.is.readByte();
            if (this.markOn) {
                this.markedItemQ.addLast(new Byte(value));
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_octet");
        }
    }

    @Override
    public short read_short() {
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return (Short)this.markedItemQ.removeFirst();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return (Short)this.markedItemQ.get(this.peekIndex++);
        }
        try {
            short value = this.is.readShort();
            if (this.markOn) {
                this.markedItemQ.addLast(new Short(value));
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_short");
        }
    }

    @Override
    public short read_ushort() {
        return this.read_short();
    }

    @Override
    public int read_long() {
        if (this.bis.getPosition() < 16) {
            int b1 = this.bis.read() << 24 & 0xFF000000;
            int b2 = this.bis.read() << 16 & 0xFF0000;
            int b3 = this.bis.read() << 8 & 0xFF00;
            int b4 = this.bis.read() << 0 & 0xFF;
            if (this.bis.getPosition() == 16) {
                this.initObjectInputStream();
            } else if (this.bis.getPosition() > 16) {
                this.wrapper.javaSerializationException("read_long");
            }
            return b1 | b2 | b3 | b4;
        }
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return (Integer)this.markedItemQ.removeFirst();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return (Integer)this.markedItemQ.get(this.peekIndex++);
        }
        try {
            int value = this.is.readInt();
            if (this.markOn) {
                this.markedItemQ.addLast(new Integer(value));
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_long");
        }
    }

    @Override
    public int read_ulong() {
        return this.read_long();
    }

    @Override
    public double read_longdouble() {
        throw this.wrapper.longDoubleNotImplemented(CompletionStatus.COMPLETED_MAYBE);
    }

    @Override
    public long read_longlong() {
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return (Long)this.markedItemQ.removeFirst();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return (Long)this.markedItemQ.get(this.peekIndex++);
        }
        try {
            long value = this.is.readLong();
            if (this.markOn) {
                this.markedItemQ.addLast(new Long(value));
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_longlong");
        }
    }

    @Override
    public long read_ulonglong() {
        return this.read_longlong();
    }

    @Override
    public float read_float() {
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return ((Float)this.markedItemQ.removeFirst()).floatValue();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return ((Float)this.markedItemQ.get(this.peekIndex++)).floatValue();
        }
        try {
            float value = this.is.readFloat();
            if (this.markOn) {
                this.markedItemQ.addLast(new Float(value));
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_float");
        }
    }

    @Override
    public double read_double() {
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return (Double)this.markedItemQ.removeFirst();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return (Double)this.markedItemQ.get(this.peekIndex++);
        }
        try {
            double value = this.is.readDouble();
            if (this.markOn) {
                this.markedItemQ.addLast(new Double(value));
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_double");
        }
    }

    @Override
    public String read_string() {
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return (String)this.markedItemQ.removeFirst();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return (String)this.markedItemQ.get(this.peekIndex++);
        }
        try {
            String value = this.is.readUTF();
            if (this.markOn) {
                this.markedItemQ.addLast(value);
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_string");
        }
    }

    @Override
    public String read_wstring() {
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return (String)this.markedItemQ.removeFirst();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return (String)this.markedItemQ.get(this.peekIndex++);
        }
        try {
            String value = (String)this.is.readObject();
            if (this.markOn) {
                this.markedItemQ.addLast(value);
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_wstring");
        }
    }

    @Override
    public void read_boolean_array(boolean[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_boolean();
        }
    }

    @Override
    public void read_char_array(char[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_char();
        }
    }

    @Override
    public void read_wchar_array(char[] value, int offset, int length) {
        this.read_char_array(value, offset, length);
    }

    @Override
    public void read_octet_array(byte[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_octet();
        }
    }

    @Override
    public void read_short_array(short[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_short();
        }
    }

    @Override
    public void read_ushort_array(short[] value, int offset, int length) {
        this.read_short_array(value, offset, length);
    }

    @Override
    public void read_long_array(int[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_long();
        }
    }

    @Override
    public void read_ulong_array(int[] value, int offset, int length) {
        this.read_long_array(value, offset, length);
    }

    @Override
    public void read_longlong_array(long[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_longlong();
        }
    }

    @Override
    public void read_ulonglong_array(long[] value, int offset, int length) {
        this.read_longlong_array(value, offset, length);
    }

    @Override
    public void read_float_array(float[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_float();
        }
    }

    @Override
    public void read_double_array(double[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_double();
        }
    }

    @Override
    public Object read_Object() {
        return this.read_Object(null);
    }

    @Override
    public TypeCode read_TypeCode() {
        TypeCodeImpl tc = new TypeCodeImpl(this.orb);
        tc.read_value(this.parent);
        return tc;
    }

    @Override
    public Any read_any() {
        Any any = this.orb.create_any();
        TypeCodeImpl tc = new TypeCodeImpl(this.orb);
        try {
            tc.read_value(this.parent);
        }
        catch (MARSHAL ex) {
            if (tc.kind().value() != 29) {
                throw ex;
            }
            ex.printStackTrace();
        }
        any.read_value(this.parent, tc);
        return any;
    }

    @Override
    public Principal read_Principal() {
        int len = this.read_long();
        byte[] pvalue = new byte[len];
        this.read_octet_array(pvalue, 0, len);
        PrincipalImpl p = new PrincipalImpl();
        ((Principal)p).name(pvalue);
        return p;
    }

    @Override
    public BigDecimal read_fixed() {
        return new BigDecimal(this.read_fixed_buffer().toString());
    }

    private StringBuffer read_fixed_buffer() {
        StringBuffer buffer = new StringBuffer(64);
        boolean wroteFirstDigit = false;
        boolean more = true;
        while (more) {
            byte doubleDigit = this.read_octet();
            int firstDigit = (doubleDigit & 0xF0) >> 4;
            int secondDigit = doubleDigit & 0xF;
            if (wroteFirstDigit || firstDigit != 0) {
                buffer.append(Character.forDigit(firstDigit, 10));
                wroteFirstDigit = true;
            }
            if (secondDigit == 12) {
                if (!wroteFirstDigit) {
                    return new StringBuffer("0.0");
                }
                more = false;
                continue;
            }
            if (secondDigit == 13) {
                buffer.insert(0, '-');
                more = false;
                continue;
            }
            buffer.append(Character.forDigit(secondDigit, 10));
            wroteFirstDigit = true;
        }
        return buffer;
    }

    @Override
    public Object read_Object(Class<?> clz) {
        IOR ior = IORFactories.makeIOR(this.parent);
        if (ior.isNil()) {
            return null;
        }
        PresentationManager.StubFactoryFactory sff = ORB.getStubFactoryFactory();
        String codeBase = ior.getProfile().getCodebase();
        PresentationManager.StubFactory stubFactory = null;
        if (clz == null) {
            RepositoryId rid = RepositoryId.cache.getId(ior.getTypeId());
            String className = rid.getClassName();
            boolean isIDLInterface = rid.isIDLType();
            if (className == null || className.equals("")) {
                stubFactory = null;
            } else {
                try {
                    stubFactory = sff.createStubFactory(className, isIDLInterface, codeBase, null, null);
                }
                catch (Exception exc) {
                    stubFactory = null;
                }
            }
        } else if (StubAdapter.isStubClass(clz)) {
            stubFactory = PresentationDefaults.makeStaticStubFactory(clz);
        } else {
            boolean isIDL = IDLEntity.class.isAssignableFrom(clz);
            stubFactory = sff.createStubFactory(clz.getName(), isIDL, codeBase, clz, clz.getClassLoader());
        }
        return CDRInputStream_1_0.internalIORToObject(ior, stubFactory, this.orb);
    }

    @Override
    public org.omg.CORBA.ORB orb() {
        return this.orb;
    }

    @Override
    public Serializable read_value() {
        if (!this.markOn && !this.markedItemQ.isEmpty()) {
            return (Serializable)this.markedItemQ.removeFirst();
        }
        if (this.markOn && !this.markedItemQ.isEmpty() && this.peekIndex < this.peekCount) {
            return (Serializable)this.markedItemQ.get(this.peekIndex++);
        }
        try {
            Serializable value = (Serializable)this.is.readObject();
            if (this.markOn) {
                this.markedItemQ.addLast(value);
            }
            return value;
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"read_value");
        }
    }

    @Override
    public Serializable read_value(Class<?> clz) {
        return this.read_value();
    }

    @Override
    public Serializable read_value(BoxedValueHelper factory2) {
        return this.read_value();
    }

    @Override
    public Serializable read_value(String rep_id) {
        return this.read_value();
    }

    @Override
    public Serializable read_value(Serializable value) {
        return this.read_value();
    }

    @Override
    public java.lang.Object read_abstract_interface() {
        return this.read_abstract_interface(null);
    }

    @Override
    public java.lang.Object read_abstract_interface(Class<?> clz) {
        boolean isObject = this.read_boolean();
        if (isObject) {
            return this.read_Object(clz);
        }
        return this.read_value();
    }

    @Override
    public void consumeEndian() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public int getPosition() {
        try {
            return this.bis.getPosition();
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"getPosition");
        }
    }

    @Override
    public java.lang.Object read_Abstract() {
        return this.read_abstract_interface();
    }

    @Override
    public Serializable read_Value() {
        return this.read_value();
    }

    @Override
    public void read_any_array(AnySeqHolder seq, int offset, int length) {
        this.read_any_array(seq.value, offset, length);
    }

    private final void read_any_array(Any[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            value[i + offset] = this.read_any();
        }
    }

    @Override
    public void read_boolean_array(BooleanSeqHolder seq, int offset, int length) {
        this.read_boolean_array(seq.value, offset, length);
    }

    @Override
    public void read_char_array(CharSeqHolder seq, int offset, int length) {
        this.read_char_array(seq.value, offset, length);
    }

    @Override
    public void read_wchar_array(WCharSeqHolder seq, int offset, int length) {
        this.read_wchar_array(seq.value, offset, length);
    }

    @Override
    public void read_octet_array(OctetSeqHolder seq, int offset, int length) {
        this.read_octet_array(seq.value, offset, length);
    }

    @Override
    public void read_short_array(ShortSeqHolder seq, int offset, int length) {
        this.read_short_array(seq.value, offset, length);
    }

    @Override
    public void read_ushort_array(UShortSeqHolder seq, int offset, int length) {
        this.read_ushort_array(seq.value, offset, length);
    }

    @Override
    public void read_long_array(LongSeqHolder seq, int offset, int length) {
        this.read_long_array(seq.value, offset, length);
    }

    @Override
    public void read_ulong_array(ULongSeqHolder seq, int offset, int length) {
        this.read_ulong_array(seq.value, offset, length);
    }

    @Override
    public void read_ulonglong_array(ULongLongSeqHolder seq, int offset, int length) {
        this.read_ulonglong_array(seq.value, offset, length);
    }

    @Override
    public void read_longlong_array(LongLongSeqHolder seq, int offset, int length) {
        this.read_longlong_array(seq.value, offset, length);
    }

    @Override
    public void read_float_array(FloatSeqHolder seq, int offset, int length) {
        this.read_float_array(seq.value, offset, length);
    }

    @Override
    public void read_double_array(DoubleSeqHolder seq, int offset, int length) {
        this.read_double_array(seq.value, offset, length);
    }

    @Override
    public String[] _truncatable_ids() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public void mark(int readLimit) {
        if (this.markOn || this.is == null) {
            throw this.wrapper.javaSerializationException("mark");
        }
        this.markOn = true;
        if (!this.markedItemQ.isEmpty()) {
            this.peekIndex = 0;
            this.peekCount = this.markedItemQ.size();
        }
    }

    @Override
    public void reset() {
        this.markOn = false;
        this.peekIndex = 0;
        this.peekCount = 0;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public CDRInputStreamBase dup() {
        CDRInputStreamBase result = null;
        try {
            result = (CDRInputStreamBase)this.getClass().newInstance();
        }
        catch (Exception e) {
            throw this.wrapper.couldNotDuplicateCdrInputStream(e);
        }
        result.init(this.orb, this.buffer, this.bufSize, false, null);
        ((IDLJavaSerializationInputStream)result).skipBytes(this.getPosition());
        ((IDLJavaSerializationInputStream)result).setMarkData(this.markOn, this.peekIndex, this.peekCount, (LinkedList)this.markedItemQ.clone());
        return result;
    }

    void skipBytes(int len) {
        try {
            this.is.skipBytes(len);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"skipBytes");
        }
    }

    void setMarkData(boolean markOn, int peekIndex, int peekCount, LinkedList<java.lang.Object> markedItemQ) {
        this.markOn = markOn;
        this.peekIndex = peekIndex;
        this.peekCount = peekCount;
        this.markedItemQ = markedItemQ;
    }

    @Override
    public BigDecimal read_fixed(short digits, short scale) {
        StringBuffer buffer = this.read_fixed_buffer();
        if (digits != buffer.length()) {
            throw this.wrapper.badFixed(new Integer(digits), new Integer(buffer.length()));
        }
        buffer.insert(digits - scale, '.');
        return new BigDecimal(buffer.toString());
    }

    @Override
    public boolean isLittleEndian() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    void setHeaderPadding(boolean headerPadding) {
    }

    @Override
    public ByteBuffer getByteBuffer() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public void setByteBuffer(ByteBuffer byteBuffer) {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public void setByteBufferWithInfo(ByteBufferWithInfo bbwi) {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public int getBufferLength() {
        return this.bufSize;
    }

    @Override
    public void setBufferLength(int value) {
    }

    @Override
    public int getIndex() {
        return this.bis.getPosition();
    }

    @Override
    public void setIndex(int value) {
        try {
            this.bis.setPosition(value);
        }
        catch (IndexOutOfBoundsException e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"setIndex");
        }
    }

    @Override
    public void orb(org.omg.CORBA.ORB orb) {
        this.orb = (ORB)orb;
    }

    @Override
    public BufferManagerRead getBufferManager() {
        return this.bufferManager;
    }

    @Override
    public GIOPVersion getGIOPVersion() {
        return GIOPVersion.V1_2;
    }

    @Override
    CodeBase getCodeBase() {
        return this.parent.getCodeBase();
    }

    @Override
    void printBuffer() {
        byte[] buf = this.buffer.array();
        System.out.println("+++++++ Input Buffer ++++++++");
        System.out.println();
        System.out.println("Current position: " + this.getPosition());
        System.out.println("Total length : " + this.bufSize);
        System.out.println();
        char[] charBuf = new char[16];
        try {
            for (int i = 0; i < buf.length; i += 16) {
                int x;
                int j;
                for (j = 0; j < 16 && j + i < buf.length; ++j) {
                    String hex;
                    int k = buf[i + j];
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
                for (x = 0; x < 16 && x + i < buf.length; ++x) {
                    charBuf[x] = ORBUtility.isPrintable((char)buf[i + x]) ? (int)buf[i + x] : 46;
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
    void alignOnBoundary(int octetBoundary) {
        throw this.wrapper.giopVersionError();
    }

    @Override
    void performORBVersionSpecificInit() {
    }

    @Override
    public void resetCodeSetConverters() {
    }

    @Override
    public void start_value() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public void end_value() {
        throw this.wrapper.giopVersionError();
    }

    class MarshalObjectInputStream
    extends ObjectInputStream {
        ORB orb;

        MarshalObjectInputStream(InputStream out, ORB orb) throws IOException {
            super(out);
            this.orb = orb;
            AccessController.doPrivileged(new PrivilegedAction<Void>(){

                @Override
                public Void run() {
                    MarshalObjectInputStream.this.enableResolveObject(true);
                    return null;
                }
            });
        }

        @Override
        protected final java.lang.Object resolveObject(java.lang.Object obj) throws IOException {
            try {
                if (StubAdapter.isStub(obj)) {
                    StubAdapter.connect(obj, this.orb);
                }
            }
            catch (RemoteException re) {
                IOException ie = new IOException("resolveObject failed");
                ie.initCause(re);
                throw ie;
            }
            return obj;
        }
    }

    class _ByteArrayInputStream
    extends ByteArrayInputStream {
        _ByteArrayInputStream(byte[] buf) {
            super(buf);
        }

        int getPosition() {
            return this.pos;
        }

        void setPosition(int value) {
            if (value < 0 || value > this.count) {
                throw new IndexOutOfBoundsException();
            }
            this.pos = value;
        }
    }
}

