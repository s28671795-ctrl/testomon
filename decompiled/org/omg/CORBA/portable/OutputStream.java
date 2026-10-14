/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import java.io.IOException;
import java.math.BigDecimal;
import org.omg.CORBA.Any;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;

public abstract class OutputStream
extends java.io.OutputStream {
    @Override
    public void write(int b) throws IOException {
        throw new NO_IMPLEMENT();
    }

    public ORB orb() {
        throw new NO_IMPLEMENT();
    }

    public abstract InputStream create_input_stream();

    public abstract void write_boolean(boolean var1);

    public abstract void write_char(char var1);

    public abstract void write_wchar(char var1);

    public abstract void write_octet(byte var1);

    public abstract void write_short(short var1);

    public abstract void write_ushort(short var1);

    public abstract void write_long(int var1);

    public abstract void write_ulong(int var1);

    public abstract void write_longlong(long var1);

    public abstract void write_ulonglong(long var1);

    public abstract void write_float(float var1);

    public abstract void write_double(double var1);

    public abstract void write_string(String var1);

    public abstract void write_wstring(String var1);

    public abstract void write_boolean_array(boolean[] var1, int var2, int var3);

    public abstract void write_char_array(char[] var1, int var2, int var3);

    public abstract void write_wchar_array(char[] var1, int var2, int var3);

    public abstract void write_octet_array(byte[] var1, int var2, int var3);

    public abstract void write_short_array(short[] var1, int var2, int var3);

    public abstract void write_ushort_array(short[] var1, int var2, int var3);

    public abstract void write_long_array(int[] var1, int var2, int var3);

    public abstract void write_ulong_array(int[] var1, int var2, int var3);

    public abstract void write_longlong_array(long[] var1, int var2, int var3);

    public abstract void write_ulonglong_array(long[] var1, int var2, int var3);

    public abstract void write_float_array(float[] var1, int var2, int var3);

    public abstract void write_double_array(double[] var1, int var2, int var3);

    public abstract void write_Object(Object var1);

    public abstract void write_TypeCode(TypeCode var1);

    public abstract void write_any(Any var1);

    public void write_Context(Context ctx, ContextList contexts) {
        throw new NO_IMPLEMENT();
    }

    public void write_Principal(Principal value) {
        throw new NO_IMPLEMENT();
    }

    public void write_fixed(BigDecimal value) {
        throw new NO_IMPLEMENT();
    }

    public void write_fixed(BigDecimal value, short digits, short scale) {
        throw new NO_IMPLEMENT();
    }
}

