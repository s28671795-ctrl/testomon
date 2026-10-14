/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.Serializable;
import org.omg.CORBA.Any;
import org.omg.CORBA.BadFixedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.ValueBase;

public interface DataOutputStream
extends ValueBase {
    public void write_any(Any var1);

    public void write_boolean(boolean var1);

    public void write_char(char var1);

    public void write_wchar(char var1);

    public void write_octet(byte var1);

    public void write_short(short var1);

    public void write_ushort(short var1);

    public void write_long(int var1);

    public void write_ulong(int var1);

    public void write_longlong(long var1);

    public void write_ulonglong(long var1);

    public void write_float(float var1);

    public void write_double(double var1);

    public void write_longdouble(double var1);

    public void write_string(String var1);

    public void write_wstring(String var1);

    public void write_Object(Object var1);

    public void write_Value(Serializable var1);

    public void write_TypeCode(TypeCode var1);

    public void write_any_array(Any[] var1, int var2, int var3);

    public void write_boolean_array(boolean[] var1, int var2, int var3);

    public void write_char_array(char[] var1, int var2, int var3);

    public void write_wchar_array(char[] var1, int var2, int var3);

    public void write_octet_array(byte[] var1, int var2, int var3);

    public void write_short_array(short[] var1, int var2, int var3);

    public void write_ushort_array(short[] var1, int var2, int var3);

    public void write_long_array(int[] var1, int var2, int var3);

    public void write_ulong_array(int[] var1, int var2, int var3);

    public void write_ulonglong_array(long[] var1, int var2, int var3);

    public void write_longlong_array(long[] var1, int var2, int var3);

    public void write_float_array(float[] var1, int var2, int var3);

    public void write_double_array(double[] var1, int var2, int var3);

    public void write_long_double_array(double[] var1, int var2, int var3);

    public void write_fixed(Any var1) throws BadFixedValue;

    public void write_fixed_array(Any[] var1, int var2, int var3) throws BadFixedValue;
}

