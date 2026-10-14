/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jboss.com.sun.corba.se.impl.corba.TypeCodeImpl;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream;
import org.jboss.com.sun.corba.se.impl.encoding.TypeCodeReader;
import org.omg.CORBA.Any;
import org.omg.CORBA.Context;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA_2_3.portable.InputStream;

public class WrapperInputStream
extends InputStream
implements TypeCodeReader {
    private CDRInputStream stream;
    private Map<Integer, TypeCodeImpl> typeMap = null;
    private int startPos = 0;

    public WrapperInputStream(CDRInputStream s2) {
        this.stream = s2;
        this.startPos = this.stream.getPosition();
    }

    @Override
    public int read() throws IOException {
        return this.stream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.stream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.stream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return this.stream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return this.stream.available();
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

    @Override
    public void mark(int readlimit) {
        this.stream.mark(readlimit);
    }

    @Override
    public void reset() {
        this.stream.reset();
    }

    @Override
    public boolean markSupported() {
        return this.stream.markSupported();
    }

    @Override
    public int getPosition() {
        return this.stream.getPosition();
    }

    @Override
    public void consumeEndian() {
        this.stream.consumeEndian();
    }

    @Override
    public boolean read_boolean() {
        return this.stream.read_boolean();
    }

    @Override
    public char read_char() {
        return this.stream.read_char();
    }

    @Override
    public char read_wchar() {
        return this.stream.read_wchar();
    }

    @Override
    public byte read_octet() {
        return this.stream.read_octet();
    }

    @Override
    public short read_short() {
        return this.stream.read_short();
    }

    @Override
    public short read_ushort() {
        return this.stream.read_ushort();
    }

    @Override
    public int read_long() {
        return this.stream.read_long();
    }

    @Override
    public int read_ulong() {
        return this.stream.read_ulong();
    }

    @Override
    public long read_longlong() {
        return this.stream.read_longlong();
    }

    @Override
    public long read_ulonglong() {
        return this.stream.read_ulonglong();
    }

    @Override
    public float read_float() {
        return this.stream.read_float();
    }

    @Override
    public double read_double() {
        return this.stream.read_double();
    }

    @Override
    public String read_string() {
        return this.stream.read_string();
    }

    @Override
    public String read_wstring() {
        return this.stream.read_wstring();
    }

    @Override
    public void read_boolean_array(boolean[] value, int offset, int length) {
        this.stream.read_boolean_array(value, offset, length);
    }

    @Override
    public void read_char_array(char[] value, int offset, int length) {
        this.stream.read_char_array(value, offset, length);
    }

    @Override
    public void read_wchar_array(char[] value, int offset, int length) {
        this.stream.read_wchar_array(value, offset, length);
    }

    @Override
    public void read_octet_array(byte[] value, int offset, int length) {
        this.stream.read_octet_array(value, offset, length);
    }

    @Override
    public void read_short_array(short[] value, int offset, int length) {
        this.stream.read_short_array(value, offset, length);
    }

    @Override
    public void read_ushort_array(short[] value, int offset, int length) {
        this.stream.read_ushort_array(value, offset, length);
    }

    @Override
    public void read_long_array(int[] value, int offset, int length) {
        this.stream.read_long_array(value, offset, length);
    }

    @Override
    public void read_ulong_array(int[] value, int offset, int length) {
        this.stream.read_ulong_array(value, offset, length);
    }

    @Override
    public void read_longlong_array(long[] value, int offset, int length) {
        this.stream.read_longlong_array(value, offset, length);
    }

    @Override
    public void read_ulonglong_array(long[] value, int offset, int length) {
        this.stream.read_ulonglong_array(value, offset, length);
    }

    @Override
    public void read_float_array(float[] value, int offset, int length) {
        this.stream.read_float_array(value, offset, length);
    }

    @Override
    public void read_double_array(double[] value, int offset, int length) {
        this.stream.read_double_array(value, offset, length);
    }

    @Override
    public Object read_Object() {
        return this.stream.read_Object();
    }

    @Override
    public Serializable read_value() {
        return this.stream.read_value();
    }

    @Override
    public TypeCode read_TypeCode() {
        return this.stream.read_TypeCode();
    }

    @Override
    public Any read_any() {
        return this.stream.read_any();
    }

    @Override
    public Principal read_Principal() {
        return this.stream.read_Principal();
    }

    @Override
    public BigDecimal read_fixed() {
        return this.stream.read_fixed();
    }

    @Override
    public Context read_Context() {
        return this.stream.read_Context();
    }

    @Override
    public ORB orb() {
        return this.stream.orb();
    }

    @Override
    public void addTypeCodeAtPosition(TypeCodeImpl tc, int position) {
        if (this.typeMap == null) {
            this.typeMap = new HashMap<Integer, TypeCodeImpl>(16);
        }
        this.typeMap.put(position, tc);
    }

    @Override
    public TypeCodeImpl getTypeCodeAtPosition(int position) {
        if (this.typeMap == null) {
            return null;
        }
        return this.typeMap.get(position);
    }

    @Override
    public void setEnclosingInputStream(InputStream enclosure) {
    }

    @Override
    public TypeCodeReader getTopLevelStream() {
        return this;
    }

    @Override
    public int getTopLevelPosition() {
        return this.getPosition() - this.startPos;
    }

    @Override
    public void performORBVersionSpecificInit() {
        this.stream.performORBVersionSpecificInit();
    }

    @Override
    public void resetCodeSetConverters() {
        this.stream.resetCodeSetConverters();
    }

    @Override
    public void printTypeMap() {
        System.out.println("typeMap = {");
        ArrayList<Integer> sortedKeys = new ArrayList<Integer>(this.typeMap.keySet());
        Collections.sort(sortedKeys);
        for (Integer pos : sortedKeys) {
            TypeCodeImpl tci = this.typeMap.get(pos);
            System.out.println("  key = " + pos + ", value = " + tci.description());
        }
        System.out.println("}");
    }
}

