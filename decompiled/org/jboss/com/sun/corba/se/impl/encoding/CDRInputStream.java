/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerRead;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStreamBase;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream_1_0;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream_1_1;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream_1_2;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.impl.encoding.IDLJavaSerializationInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.MarshalInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaMessageMediator;
import org.jboss.com.sun.org.omg.SendingContext.CodeBase;
import org.omg.CORBA.Any;
import org.omg.CORBA.AnySeqHolder;
import org.omg.CORBA.BooleanSeqHolder;
import org.omg.CORBA.CharSeqHolder;
import org.omg.CORBA.Context;
import org.omg.CORBA.DataInputStream;
import org.omg.CORBA.DoubleSeqHolder;
import org.omg.CORBA.FloatSeqHolder;
import org.omg.CORBA.LongLongSeqHolder;
import org.omg.CORBA.LongSeqHolder;
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
import org.omg.CORBA.portable.ValueInputStream;
import org.omg.CORBA_2_3.portable.InputStream;

public abstract class CDRInputStream
extends InputStream
implements MarshalInputStream,
DataInputStream,
ValueInputStream {
    protected CorbaMessageMediator messageMediator;
    private CDRInputStreamBase impl;

    public CDRInputStream() {
    }

    public CDRInputStream(CDRInputStream is) {
        this.impl = is.impl.dup();
        this.impl.setParent(this);
    }

    public CDRInputStream(org.omg.CORBA.ORB orb, ByteBuffer byteBuffer, int size, boolean littleEndian, GIOPVersion version, byte encodingVersion, BufferManagerRead bufMgr) {
        this.impl = InputStreamFactory.newInputStream((ORB)orb, version, encodingVersion);
        this.impl.init(orb, byteBuffer, size, littleEndian, bufMgr);
        this.impl.setParent(this);
    }

    @Override
    public final boolean read_boolean() {
        return this.impl.read_boolean();
    }

    @Override
    public final char read_char() {
        return this.impl.read_char();
    }

    @Override
    public final char read_wchar() {
        return this.impl.read_wchar();
    }

    @Override
    public final byte read_octet() {
        return this.impl.read_octet();
    }

    @Override
    public final short read_short() {
        return this.impl.read_short();
    }

    @Override
    public final short read_ushort() {
        return this.impl.read_ushort();
    }

    @Override
    public final int read_long() {
        return this.impl.read_long();
    }

    @Override
    public final int read_ulong() {
        return this.impl.read_ulong();
    }

    @Override
    public double read_longdouble() {
        return this.impl.read_longdouble();
    }

    @Override
    public final long read_longlong() {
        return this.impl.read_longlong();
    }

    @Override
    public final long read_ulonglong() {
        return this.impl.read_ulonglong();
    }

    @Override
    public final float read_float() {
        return this.impl.read_float();
    }

    @Override
    public final double read_double() {
        return this.impl.read_double();
    }

    @Override
    public final String read_string() {
        return this.impl.read_string();
    }

    @Override
    public final String read_wstring() {
        return this.impl.read_wstring();
    }

    @Override
    public final void read_boolean_array(boolean[] value, int offset, int length) {
        this.impl.read_boolean_array(value, offset, length);
    }

    @Override
    public final void read_char_array(char[] value, int offset, int length) {
        this.impl.read_char_array(value, offset, length);
    }

    @Override
    public final void read_wchar_array(char[] value, int offset, int length) {
        this.impl.read_wchar_array(value, offset, length);
    }

    @Override
    public final void read_octet_array(byte[] value, int offset, int length) {
        this.impl.read_octet_array(value, offset, length);
    }

    @Override
    public final void read_short_array(short[] value, int offset, int length) {
        this.impl.read_short_array(value, offset, length);
    }

    @Override
    public final void read_ushort_array(short[] value, int offset, int length) {
        this.impl.read_ushort_array(value, offset, length);
    }

    @Override
    public final void read_long_array(int[] value, int offset, int length) {
        this.impl.read_long_array(value, offset, length);
    }

    @Override
    public final void read_ulong_array(int[] value, int offset, int length) {
        this.impl.read_ulong_array(value, offset, length);
    }

    @Override
    public final void read_longlong_array(long[] value, int offset, int length) {
        this.impl.read_longlong_array(value, offset, length);
    }

    @Override
    public final void read_ulonglong_array(long[] value, int offset, int length) {
        this.impl.read_ulonglong_array(value, offset, length);
    }

    @Override
    public final void read_float_array(float[] value, int offset, int length) {
        this.impl.read_float_array(value, offset, length);
    }

    @Override
    public final void read_double_array(double[] value, int offset, int length) {
        this.impl.read_double_array(value, offset, length);
    }

    @Override
    public final Object read_Object() {
        return this.impl.read_Object();
    }

    @Override
    public final TypeCode read_TypeCode() {
        return this.impl.read_TypeCode();
    }

    @Override
    public final Any read_any() {
        return this.impl.read_any();
    }

    @Override
    public final Principal read_Principal() {
        return this.impl.read_Principal();
    }

    @Override
    public final int read() throws IOException {
        return this.impl.read();
    }

    @Override
    public final BigDecimal read_fixed() {
        return this.impl.read_fixed();
    }

    @Override
    public final Context read_Context() {
        return this.impl.read_Context();
    }

    @Override
    public final Object read_Object(Class clz) {
        return this.impl.read_Object(clz);
    }

    @Override
    public final org.omg.CORBA.ORB orb() {
        return this.impl.orb();
    }

    @Override
    public final Serializable read_value() {
        return this.impl.read_value();
    }

    @Override
    public final Serializable read_value(Class clz) {
        return this.impl.read_value(clz);
    }

    @Override
    public final Serializable read_value(BoxedValueHelper factory2) {
        return this.impl.read_value(factory2);
    }

    @Override
    public final Serializable read_value(String rep_id) {
        return this.impl.read_value(rep_id);
    }

    @Override
    public final Serializable read_value(Serializable value) {
        return this.impl.read_value(value);
    }

    @Override
    public final java.lang.Object read_abstract_interface() {
        return this.impl.read_abstract_interface();
    }

    @Override
    public final java.lang.Object read_abstract_interface(Class clz) {
        return this.impl.read_abstract_interface(clz);
    }

    @Override
    public final void consumeEndian() {
        this.impl.consumeEndian();
    }

    @Override
    public final int getPosition() {
        return this.impl.getPosition();
    }

    public final java.lang.Object read_Abstract() {
        return this.impl.read_Abstract();
    }

    @Override
    public final Serializable read_Value() {
        return this.impl.read_Value();
    }

    @Override
    public final void read_any_array(AnySeqHolder seq, int offset, int length) {
        this.impl.read_any_array(seq, offset, length);
    }

    @Override
    public final void read_boolean_array(BooleanSeqHolder seq, int offset, int length) {
        this.impl.read_boolean_array(seq, offset, length);
    }

    @Override
    public final void read_char_array(CharSeqHolder seq, int offset, int length) {
        this.impl.read_char_array(seq, offset, length);
    }

    @Override
    public final void read_wchar_array(WCharSeqHolder seq, int offset, int length) {
        this.impl.read_wchar_array(seq, offset, length);
    }

    @Override
    public final void read_octet_array(OctetSeqHolder seq, int offset, int length) {
        this.impl.read_octet_array(seq, offset, length);
    }

    @Override
    public final void read_short_array(ShortSeqHolder seq, int offset, int length) {
        this.impl.read_short_array(seq, offset, length);
    }

    @Override
    public final void read_ushort_array(UShortSeqHolder seq, int offset, int length) {
        this.impl.read_ushort_array(seq, offset, length);
    }

    @Override
    public final void read_long_array(LongSeqHolder seq, int offset, int length) {
        this.impl.read_long_array(seq, offset, length);
    }

    @Override
    public final void read_ulong_array(ULongSeqHolder seq, int offset, int length) {
        this.impl.read_ulong_array(seq, offset, length);
    }

    @Override
    public final void read_ulonglong_array(ULongLongSeqHolder seq, int offset, int length) {
        this.impl.read_ulonglong_array(seq, offset, length);
    }

    @Override
    public final void read_longlong_array(LongLongSeqHolder seq, int offset, int length) {
        this.impl.read_longlong_array(seq, offset, length);
    }

    @Override
    public final void read_float_array(FloatSeqHolder seq, int offset, int length) {
        this.impl.read_float_array(seq, offset, length);
    }

    @Override
    public final void read_double_array(DoubleSeqHolder seq, int offset, int length) {
        this.impl.read_double_array(seq, offset, length);
    }

    @Override
    public final String[] _truncatable_ids() {
        return this.impl._truncatable_ids();
    }

    @Override
    public final int read(byte[] b) throws IOException {
        return this.impl.read(b);
    }

    @Override
    public final int read(byte[] b, int off, int len) throws IOException {
        return this.impl.read(b, off, len);
    }

    @Override
    public final long skip(long n) throws IOException {
        return this.impl.skip(n);
    }

    @Override
    public final int available() throws IOException {
        return this.impl.available();
    }

    @Override
    public final void close() throws IOException {
        this.impl.close();
    }

    @Override
    public final void mark(int readlimit) {
        this.impl.mark(readlimit);
    }

    @Override
    public final void reset() {
        this.impl.reset();
    }

    @Override
    public final boolean markSupported() {
        return this.impl.markSupported();
    }

    public abstract CDRInputStream dup();

    @Override
    public final BigDecimal read_fixed(short digits, short scale) {
        return this.impl.read_fixed(digits, scale);
    }

    public final boolean isLittleEndian() {
        return this.impl.isLittleEndian();
    }

    protected final ByteBuffer getByteBuffer() {
        return this.impl.getByteBuffer();
    }

    protected final void setByteBuffer(ByteBuffer byteBuffer) {
        this.impl.setByteBuffer(byteBuffer);
    }

    protected final void setByteBufferWithInfo(ByteBufferWithInfo bbwi) {
        this.impl.setByteBufferWithInfo(bbwi);
    }

    public final int getBufferLength() {
        return this.impl.getBufferLength();
    }

    protected final void setBufferLength(int value) {
        this.impl.setBufferLength(value);
    }

    protected final int getIndex() {
        return this.impl.getIndex();
    }

    protected final void setIndex(int value) {
        this.impl.setIndex(value);
    }

    public final void orb(org.omg.CORBA.ORB orb) {
        this.impl.orb(orb);
    }

    public final GIOPVersion getGIOPVersion() {
        return this.impl.getGIOPVersion();
    }

    public final BufferManagerRead getBufferManager() {
        return this.impl.getBufferManager();
    }

    public CodeBase getCodeBase() {
        return null;
    }

    protected CodeSetConversion.BTCConverter createCharBTCConverter() {
        return CodeSetConversion.impl().getBTCConverter(OSFCodeSetRegistry.ISO_8859_1, this.impl.isLittleEndian());
    }

    protected abstract CodeSetConversion.BTCConverter createWCharBTCConverter();

    void printBuffer() {
        this.impl.printBuffer();
    }

    public void alignOnBoundary(int octetBoundary) {
        this.impl.alignOnBoundary(octetBoundary);
    }

    public void setHeaderPadding(boolean headerPadding) {
        this.impl.setHeaderPadding(headerPadding);
    }

    @Override
    public void performORBVersionSpecificInit() {
        if (this.impl != null) {
            this.impl.performORBVersionSpecificInit();
        }
    }

    @Override
    public void resetCodeSetConverters() {
        this.impl.resetCodeSetConverters();
    }

    public void setMessageMediator(MessageMediator messageMediator) {
        this.messageMediator = (CorbaMessageMediator)messageMediator;
    }

    public MessageMediator getMessageMediator() {
        return this.messageMediator;
    }

    @Override
    public void start_value() {
        this.impl.start_value();
    }

    @Override
    public void end_value() {
        this.impl.end_value();
    }

    private static class InputStreamFactory {
        private InputStreamFactory() {
        }

        public static CDRInputStreamBase newInputStream(ORB orb, GIOPVersion version, byte encodingVersion) {
            switch (version.intValue()) {
                case 256: {
                    return new CDRInputStream_1_0();
                }
                case 257: {
                    return new CDRInputStream_1_1();
                }
                case 258: {
                    if (encodingVersion != 0) {
                        return new IDLJavaSerializationInputStream(encodingVersion);
                    }
                    return new CDRInputStream_1_2();
                }
            }
            ORBUtilSystemException wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
            throw wrapper.unsupportedGiopVersion(version);
        }
    }
}

