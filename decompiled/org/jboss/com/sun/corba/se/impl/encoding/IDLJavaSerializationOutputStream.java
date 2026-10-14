/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStreamBase;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.BoxedValueHelper;

public class IDLJavaSerializationOutputStream
extends CDROutputStreamBase {
    private ORB orb;
    private ObjectOutputStream os;
    private _ByteArrayOutputStream bos;
    private BufferManagerWrite bufferManager;
    private final int directWriteLength = 16;
    protected ORBUtilSystemException wrapper;

    public IDLJavaSerializationOutputStream(byte encodingVersion) {
    }

    @Override
    public void init(org.omg.CORBA.ORB orb, boolean littleEndian, BufferManagerWrite bufferManager, byte streamFormatVersion, boolean usePooledByteBuffers) {
        this.orb = (ORB)orb;
        this.bufferManager = bufferManager;
        this.wrapper = ORBUtilSystemException.get((ORB)orb, "rpc.encoding");
        this.bos = new _ByteArrayOutputStream(1024);
    }

    private void initObjectOutputStream() {
        if (this.os != null) {
            throw this.wrapper.javaStreamInitFailed();
        }
        try {
            this.os = new MarshalObjectOutputStream(this.bos, this.orb);
        }
        catch (Exception e) {
            throw this.wrapper.javaStreamInitFailed(e);
        }
    }

    @Override
    public final void write_boolean(boolean value) {
        try {
            this.os.writeBoolean(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_boolean");
        }
    }

    @Override
    public final void write_char(char value) {
        try {
            this.os.writeChar(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_char");
        }
    }

    @Override
    public final void write_wchar(char value) {
        this.write_char(value);
    }

    @Override
    public final void write_octet(byte value) {
        if (this.bos.size() < 16) {
            this.bos.write(value);
            if (this.bos.size() == 16) {
                this.initObjectOutputStream();
            }
            return;
        }
        try {
            this.os.writeByte(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_octet");
        }
    }

    @Override
    public final void write_short(short value) {
        try {
            this.os.writeShort(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_short");
        }
    }

    @Override
    public final void write_ushort(short value) {
        this.write_short(value);
    }

    @Override
    public final void write_long(int value) {
        if (this.bos.size() < 16) {
            this.bos.write((byte)(value >>> 24 & 0xFF));
            this.bos.write((byte)(value >>> 16 & 0xFF));
            this.bos.write((byte)(value >>> 8 & 0xFF));
            this.bos.write((byte)(value >>> 0 & 0xFF));
            if (this.bos.size() == 16) {
                this.initObjectOutputStream();
            } else if (this.bos.size() > 16) {
                this.wrapper.javaSerializationException("write_long");
            }
            return;
        }
        try {
            this.os.writeInt(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_long");
        }
    }

    @Override
    public final void write_ulong(int value) {
        this.write_long(value);
    }

    @Override
    public void write_longdouble(double value) {
        throw this.wrapper.longDoubleNotImplemented(CompletionStatus.COMPLETED_MAYBE);
    }

    @Override
    public final void write_longlong(long value) {
        try {
            this.os.writeLong(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_longlong");
        }
    }

    @Override
    public final void write_ulonglong(long value) {
        this.write_longlong(value);
    }

    @Override
    public final void write_float(float value) {
        try {
            this.os.writeFloat(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_float");
        }
    }

    @Override
    public final void write_double(double value) {
        try {
            this.os.writeDouble(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_double");
        }
    }

    @Override
    public final void write_string(String value) {
        try {
            this.os.writeUTF(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_string");
        }
    }

    @Override
    public final void write_wstring(String value) {
        try {
            this.os.writeObject(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_wstring");
        }
    }

    @Override
    public final void write_boolean_array(boolean[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            this.write_boolean(value[offset + i]);
        }
    }

    @Override
    public final void write_char_array(char[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            this.write_char(value[offset + i]);
        }
    }

    @Override
    public final void write_wchar_array(char[] value, int offset, int length) {
        this.write_char_array(value, offset, length);
    }

    @Override
    public final void write_octet_array(byte[] value, int offset, int length) {
        try {
            this.os.write(value, offset, length);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_octet_array");
        }
    }

    @Override
    public final void write_short_array(short[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            this.write_short(value[offset + i]);
        }
    }

    @Override
    public final void write_ushort_array(short[] value, int offset, int length) {
        this.write_short_array(value, offset, length);
    }

    @Override
    public final void write_long_array(int[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            this.write_long(value[offset + i]);
        }
    }

    @Override
    public final void write_ulong_array(int[] value, int offset, int length) {
        this.write_long_array(value, offset, length);
    }

    @Override
    public final void write_longlong_array(long[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            this.write_longlong(value[offset + i]);
        }
    }

    @Override
    public final void write_ulonglong_array(long[] value, int offset, int length) {
        this.write_longlong_array(value, offset, length);
    }

    @Override
    public final void write_float_array(float[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            this.write_float(value[offset + i]);
        }
    }

    @Override
    public final void write_double_array(double[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            this.write_double(value[offset + i]);
        }
    }

    @Override
    public final void write_Object(Object value) {
        if (value == null) {
            IOR nullIOR = IORFactories.makeIOR(this.orb);
            nullIOR.write(this.parent);
            return;
        }
        if (value instanceof LocalObject) {
            throw this.wrapper.writeLocalObject(CompletionStatus.COMPLETED_MAYBE);
        }
        IOR ior = ORBUtility.connectAndGetIOR(this.orb, value);
        ior.write(this.parent);
    }

    @Override
    public final void write_TypeCode(TypeCode tc) {
        if (tc == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        TypeCodeImpl tci = tc instanceof TypeCodeImpl ? (TypeCodeImpl)tc : new TypeCodeImpl(this.orb, tc);
        tci.write_value(this.parent);
    }

    @Override
    public final void write_any(Any any) {
        if (any == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        this.write_TypeCode(any.type());
        any.write_value(this.parent);
    }

    @Override
    public final void write_Principal(Principal p) {
        this.write_long(p.name().length);
        this.write_octet_array(p.name(), 0, p.name().length);
    }

    @Override
    public final void write_fixed(BigDecimal bigDecimal) {
        this.write_fixed(bigDecimal.toString(), bigDecimal.signum());
    }

    private void write_fixed(String string, int signum) {
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
    public final org.omg.CORBA.ORB orb() {
        return this.orb;
    }

    @Override
    public final void write_value(Serializable value) {
        this.write_value(value, (String)null);
    }

    @Override
    public final void write_value(Serializable value, Class<?> clz) {
        this.write_value(value);
    }

    @Override
    public final void write_value(Serializable value, String repository_id) {
        try {
            this.os.writeObject(value);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_value");
        }
    }

    @Override
    public final void write_value(Serializable value, BoxedValueHelper factory2) {
        this.write_value(value, (String)null);
    }

    @Override
    public final void write_abstract_interface(java.lang.Object obj) {
        boolean isCorbaObject = false;
        Object theCorbaObject = null;
        if (obj != null && obj instanceof Object) {
            theCorbaObject = (Object)obj;
            isCorbaObject = true;
        }
        this.write_boolean(isCorbaObject);
        if (isCorbaObject) {
            this.write_Object(theCorbaObject);
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
    public final void start_block() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public final void end_block() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public final void putEndian() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public void writeTo(OutputStream s2) throws IOException {
        try {
            this.os.flush();
            this.bos.writeTo(s2);
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"writeTo");
        }
    }

    @Override
    public final byte[] toByteArray() {
        try {
            this.os.flush();
            return this.bos.toByteArray();
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"toByteArray");
        }
    }

    @Override
    public final void write_Abstract(java.lang.Object value) {
        this.write_abstract_interface(value);
    }

    @Override
    public final void write_Value(Serializable value) {
        this.write_value(value);
    }

    @Override
    public final void write_any_array(Any[] value, int offset, int length) {
        for (int i = 0; i < length; ++i) {
            this.write_any(value[offset + i]);
        }
    }

    @Override
    public final String[] _truncatable_ids() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public final int getSize() {
        try {
            this.os.flush();
            return this.bos.size();
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"write_boolean");
        }
    }

    @Override
    public final int getIndex() {
        return this.getSize();
    }

    protected int getRealIndex(int index) {
        return this.getSize();
    }

    @Override
    public final void setIndex(int value) {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public final ByteBuffer getByteBuffer() {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public final void setByteBuffer(ByteBuffer byteBuffer) {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public final boolean isLittleEndian() {
        return false;
    }

    @Override
    public ByteBufferWithInfo getByteBufferWithInfo() {
        try {
            this.os.flush();
        }
        catch (Exception e) {
            throw this.wrapper.javaSerializationException(e, (java.lang.Object)"getByteBufferWithInfo");
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(this.bos.getByteArray());
        byteBuffer.limit(this.bos.size());
        return new ByteBufferWithInfo((org.omg.CORBA.ORB)this.orb, byteBuffer, this.bos.size());
    }

    @Override
    public void setByteBufferWithInfo(ByteBufferWithInfo bbwi) {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public final BufferManagerWrite getBufferManager() {
        return this.bufferManager;
    }

    @Override
    public final void write_fixed(BigDecimal bigDecimal, short digits, short scale) {
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
    public final void writeOctetSequenceTo(org.omg.CORBA.portable.OutputStream s2) {
        byte[] buf = this.toByteArray();
        s2.write_long(buf.length);
        s2.write_octet_array(buf, 0, buf.length);
    }

    @Override
    public final GIOPVersion getGIOPVersion() {
        return GIOPVersion.V1_2;
    }

    @Override
    public final void writeIndirection(int tag, int posIndirectedTo) {
        throw this.wrapper.giopVersionError();
    }

    @Override
    void freeInternalCaches() {
    }

    @Override
    void printBuffer() {
        byte[] buf = this.toByteArray();
        System.out.println("+++++++ Output Buffer ++++++++");
        System.out.println();
        System.out.println("Current position: " + buf.length);
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
    public void alignOnBoundary(int octetBoundary) {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public void setHeaderPadding(boolean headerPadding) {
    }

    @Override
    public void start_value(String rep_id) {
        throw this.wrapper.giopVersionError();
    }

    @Override
    public void end_value() {
        throw this.wrapper.giopVersionError();
    }

    class MarshalObjectOutputStream
    extends ObjectOutputStream {
        ORB orb;

        MarshalObjectOutputStream(OutputStream out, ORB orb) throws IOException {
            super(out);
            this.orb = orb;
            AccessController.doPrivileged(new PrivilegedAction<Void>(){

                @Override
                public Void run() {
                    MarshalObjectOutputStream.this.enableReplaceObject(true);
                    return null;
                }
            });
        }

        @Override
        protected final java.lang.Object replaceObject(java.lang.Object obj) throws IOException {
            try {
                if (obj instanceof Remote && !StubAdapter.isStub(obj)) {
                    return Utility.autoConnect(obj, this.orb, true);
                }
            }
            catch (Exception e) {
                IOException ie = new IOException("replaceObject failed");
                ie.initCause(e);
                throw ie;
            }
            return obj;
        }
    }

    class _ByteArrayOutputStream
    extends ByteArrayOutputStream {
        _ByteArrayOutputStream(int initialSize) {
            super(initialSize);
        }

        byte[] getByteArray() {
            return this.buf;
        }
    }
}

