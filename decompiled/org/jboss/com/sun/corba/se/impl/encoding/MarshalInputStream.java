/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import java.io.Serializable;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;

public interface MarshalInputStream {
    public boolean read_boolean();

    public char read_char();

    public char read_wchar();

    public byte read_octet();

    public short read_short();

    public short read_ushort();

    public int read_long();

    public int read_ulong();

    public long read_longlong();

    public long read_ulonglong();

    public float read_float();

    public double read_double();

    public String read_string();

    public String read_wstring();

    public void read_boolean_array(boolean[] var1, int var2, int var3);

    public void read_char_array(char[] var1, int var2, int var3);

    public void read_wchar_array(char[] var1, int var2, int var3);

    public void read_octet_array(byte[] var1, int var2, int var3);

    public void read_short_array(short[] var1, int var2, int var3);

    public void read_ushort_array(short[] var1, int var2, int var3);

    public void read_long_array(int[] var1, int var2, int var3);

    public void read_ulong_array(int[] var1, int var2, int var3);

    public void read_longlong_array(long[] var1, int var2, int var3);

    public void read_ulonglong_array(long[] var1, int var2, int var3);

    public void read_float_array(float[] var1, int var2, int var3);

    public void read_double_array(double[] var1, int var2, int var3);

    public Object read_Object();

    public TypeCode read_TypeCode();

    public Any read_any();

    public Principal read_Principal();

    public Object read_Object(Class<?> var1);

    public Serializable read_value() throws Exception;

    public void consumeEndian();

    public int getPosition();

    public void mark(int var1);

    public void reset();

    public void performORBVersionSpecificInit();

    public void resetCodeSetConverters();
}

