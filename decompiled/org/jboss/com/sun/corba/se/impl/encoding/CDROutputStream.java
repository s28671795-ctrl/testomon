/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.encoding.BufferManagerWrite;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStreamBase;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream_1_0;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream_1_1;
import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream_1_2;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.impl.encoding.IDLJavaSerializationOutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.MarshalOutputStream;
import org.jboss.com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.pept.protocol.MessageMediator;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaMessageMediator;
import org.omg.CORBA.Any;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DataOutputStream;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ValueOutputStream;

public abstract class CDROutputStream
extends org.omg.CORBA_2_3.portable.OutputStream
implements MarshalOutputStream,
DataOutputStream,
ValueOutputStream {
    private CDROutputStreamBase impl;
    protected ORB orb;
    protected ORBUtilSystemException wrapper;
    protected CorbaMessageMediator corbaMessageMediator;

    public CDROutputStream(ORB orb, GIOPVersion version, byte encodingVersion, boolean littleEndian, BufferManagerWrite bufferManager, byte streamFormatVersion, boolean usePooledByteBuffers) {
        this.impl = OutputStreamFactory.newOutputStream(orb, version, encodingVersion);
        this.impl.init(orb, littleEndian, bufferManager, streamFormatVersion, usePooledByteBuffers);
        this.impl.setParent(this);
        this.orb = orb;
        this.wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
    }

    public CDROutputStream(ORB orb, GIOPVersion version, byte encodingVersion, boolean littleEndian, BufferManagerWrite bufferManager, byte streamFormatVersion) {
        this(orb, version, encodingVersion, littleEndian, bufferManager, streamFormatVersion, true);
    }

    @Override
    public abstract InputStream create_input_stream();

    @Override
    public final void write_boolean(boolean value) {
        this.impl.write_boolean(value);
    }

    @Override
    public final void write_char(char value) {
        this.impl.write_char(value);
    }

    @Override
    public final void write_wchar(char value) {
        this.impl.write_wchar(value);
    }

    @Override
    public final void write_octet(byte value) {
        this.impl.write_octet(value);
    }

    @Override
    public final void write_short(short value) {
        this.impl.write_short(value);
    }

    @Override
    public final void write_ushort(short value) {
        this.impl.write_ushort(value);
    }

    @Override
    public final void write_long(int value) {
        this.impl.write_long(value);
    }

    @Override
    public final void write_ulong(int value) {
        this.impl.write_ulong(value);
    }

    @Override
    public void write_longdouble(double value) {
        this.impl.write_longdouble(value);
    }

    @Override
    public final void write_longlong(long value) {
        this.impl.write_longlong(value);
    }

    @Override
    public final void write_ulonglong(long value) {
        this.impl.write_ulonglong(value);
    }

    @Override
    public final void write_float(float value) {
        this.impl.write_float(value);
    }

    @Override
    public final void write_double(double value) {
        this.impl.write_double(value);
    }

    @Override
    public final void write_string(String value) {
        this.impl.write_string(value);
    }

    @Override
    public final void write_wstring(String value) {
        this.impl.write_wstring(value);
    }

    @Override
    public final void write_boolean_array(boolean[] value, int offset, int length) {
        this.impl.write_boolean_array(value, offset, length);
    }

    @Override
    public final void write_char_array(char[] value, int offset, int length) {
        this.impl.write_char_array(value, offset, length);
    }

    @Override
    public final void write_wchar_array(char[] value, int offset, int length) {
        this.impl.write_wchar_array(value, offset, length);
    }

    @Override
    public final void write_octet_array(byte[] value, int offset, int length) {
        this.impl.write_octet_array(value, offset, length);
    }

    @Override
    public final void write_short_array(short[] value, int offset, int length) {
        this.impl.write_short_array(value, offset, length);
    }

    @Override
    public final void write_ushort_array(short[] value, int offset, int length) {
        this.impl.write_ushort_array(value, offset, length);
    }

    @Override
    public final void write_long_array(int[] value, int offset, int length) {
        this.impl.write_long_array(value, offset, length);
    }

    @Override
    public final void write_ulong_array(int[] value, int offset, int length) {
        this.impl.write_ulong_array(value, offset, length);
    }

    @Override
    public final void write_longlong_array(long[] value, int offset, int length) {
        this.impl.write_longlong_array(value, offset, length);
    }

    @Override
    public final void write_ulonglong_array(long[] value, int offset, int length) {
        this.impl.write_ulonglong_array(value, offset, length);
    }

    @Override
    public final void write_float_array(float[] value, int offset, int length) {
        this.impl.write_float_array(value, offset, length);
    }

    @Override
    public final void write_double_array(double[] value, int offset, int length) {
        this.impl.write_double_array(value, offset, length);
    }

    @Override
    public final void write_Object(Object value) {
        this.impl.write_Object(value);
    }

    @Override
    public final void write_TypeCode(TypeCode value) {
        this.impl.write_TypeCode(value);
    }

    @Override
    public final void write_any(Any value) {
        this.impl.write_any(value);
    }

    @Override
    public final void write_Principal(Principal value) {
        this.impl.write_Principal(value);
    }

    @Override
    public final void write(int b) throws IOException {
        this.impl.write(b);
    }

    @Override
    public final void write_fixed(BigDecimal value) {
        this.impl.write_fixed(value);
    }

    @Override
    public final void write_Context(Context ctx, ContextList contexts) {
        this.impl.write_Context(ctx, contexts);
    }

    @Override
    public final org.omg.CORBA.ORB orb() {
        return this.impl.orb();
    }

    @Override
    public final void write_value(Serializable value) {
        this.impl.write_value(value);
    }

    @Override
    public final void write_value(Serializable value, Class clz) {
        this.impl.write_value(value, clz);
    }

    @Override
    public final void write_value(Serializable value, String repository_id) {
        this.impl.write_value(value, repository_id);
    }

    @Override
    public final void write_value(Serializable value, BoxedValueHelper factory2) {
        this.impl.write_value(value, factory2);
    }

    @Override
    public final void write_abstract_interface(java.lang.Object obj) {
        this.impl.write_abstract_interface(obj);
    }

    @Override
    public final void write(byte[] b) throws IOException {
        this.impl.write(b);
    }

    @Override
    public final void write(byte[] b, int off, int len) throws IOException {
        this.impl.write(b, off, len);
    }

    @Override
    public final void flush() throws IOException {
        this.impl.flush();
    }

    @Override
    public final void close() throws IOException {
        this.impl.close();
    }

    @Override
    public final void start_block() {
        this.impl.start_block();
    }

    @Override
    public final void end_block() {
        this.impl.end_block();
    }

    @Override
    public final void putEndian() {
        this.impl.putEndian();
    }

    @Override
    public void writeTo(OutputStream s2) throws IOException {
        this.impl.writeTo(s2);
    }

    @Override
    public final byte[] toByteArray() {
        return this.impl.toByteArray();
    }

    public final void write_Abstract(java.lang.Object value) {
        this.impl.write_Abstract(value);
    }

    @Override
    public final void write_Value(Serializable value) {
        this.impl.write_Value(value);
    }

    @Override
    public final void write_any_array(Any[] seq, int offset, int length) {
        this.impl.write_any_array(seq, offset, length);
    }

    public void setMessageMediator(MessageMediator messageMediator) {
        this.corbaMessageMediator = (CorbaMessageMediator)messageMediator;
    }

    public MessageMediator getMessageMediator() {
        return this.corbaMessageMediator;
    }

    @Override
    public final String[] _truncatable_ids() {
        return this.impl._truncatable_ids();
    }

    protected final int getSize() {
        return this.impl.getSize();
    }

    protected final int getIndex() {
        return this.impl.getIndex();
    }

    protected int getRealIndex(int index) {
        return index;
    }

    protected final void setIndex(int value) {
        this.impl.setIndex(value);
    }

    protected final ByteBuffer getByteBuffer() {
        return this.impl.getByteBuffer();
    }

    protected final void setByteBuffer(ByteBuffer byteBuffer) {
        this.impl.setByteBuffer(byteBuffer);
    }

    public final boolean isLittleEndian() {
        return this.impl.isLittleEndian();
    }

    public ByteBufferWithInfo getByteBufferWithInfo() {
        return this.impl.getByteBufferWithInfo();
    }

    protected void setByteBufferWithInfo(ByteBufferWithInfo bbwi) {
        this.impl.setByteBufferWithInfo(bbwi);
    }

    public final BufferManagerWrite getBufferManager() {
        return this.impl.getBufferManager();
    }

    @Override
    public final void write_fixed(BigDecimal bigDecimal, short digits, short scale) {
        this.impl.write_fixed(bigDecimal, digits, scale);
    }

    public final void writeOctetSequenceTo(org.omg.CORBA.portable.OutputStream s2) {
        this.impl.writeOctetSequenceTo(s2);
    }

    public final GIOPVersion getGIOPVersion() {
        return this.impl.getGIOPVersion();
    }

    public final void writeIndirection(int tag, int posIndirectedTo) {
        this.impl.writeIndirection(tag, posIndirectedTo);
    }

    protected CodeSetConversion.CTBConverter createCharCTBConverter() {
        return CodeSetConversion.impl().getCTBConverter(OSFCodeSetRegistry.ISO_8859_1);
    }

    protected abstract CodeSetConversion.CTBConverter createWCharCTBConverter();

    protected final void freeInternalCaches() {
        this.impl.freeInternalCaches();
    }

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
    public void start_value(String rep_id) {
        this.impl.start_value(rep_id);
    }

    @Override
    public void end_value() {
        this.impl.end_value();
    }

    private static class OutputStreamFactory {
        private OutputStreamFactory() {
        }

        public static CDROutputStreamBase newOutputStream(ORB orb, GIOPVersion version, byte encodingVersion) {
            switch (version.intValue()) {
                case 256: {
                    return new CDROutputStream_1_0();
                }
                case 257: {
                    return new CDROutputStream_1_1();
                }
                case 258: {
                    if (encodingVersion != 0) {
                        return new IDLJavaSerializationOutputStream(encodingVersion);
                    }
                    return new CDROutputStream_1_2();
                }
            }
            ORBUtilSystemException wrapper = ORBUtilSystemException.get(orb, "rpc.encoding");
            throw wrapper.unsupportedGiopVersion(version);
        }
    }
}

