/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.Serializable;
import org.omg.CORBA.Any;
import org.omg.CORBA.AnySeqHolder;
import org.omg.CORBA.BadFixedValue;
import org.omg.CORBA.BooleanSeqHolder;
import org.omg.CORBA.CharSeqHolder;
import org.omg.CORBA.DoubleSeqHolder;
import org.omg.CORBA.FloatSeqHolder;
import org.omg.CORBA.LongLongSeqHolder;
import org.omg.CORBA.LongSeqHolder;
import org.omg.CORBA.Object;
import org.omg.CORBA.OctetSeqHolder;
import org.omg.CORBA.ShortSeqHolder;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ULongLongSeqHolder;
import org.omg.CORBA.ULongSeqHolder;
import org.omg.CORBA.UShortSeqHolder;
import org.omg.CORBA.WCharSeqHolder;
import org.omg.CORBA.portable.ValueBase;

public interface DataInputStream
extends ValueBase {
    public Any read_any();

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

    public double read_longdouble();

    public String read_string();

    public String read_wstring();

    public Object read_Object();

    public Serializable read_Value();

    public TypeCode read_TypeCode();

    public void read_any_array(AnySeqHolder var1, int var2, int var3);

    public void read_boolean_array(BooleanSeqHolder var1, int var2, int var3);

    public void read_char_array(CharSeqHolder var1, int var2, int var3);

    public void read_wchar_array(WCharSeqHolder var1, int var2, int var3);

    public void read_octet_array(OctetSeqHolder var1, int var2, int var3);

    public void read_short_array(ShortSeqHolder var1, int var2, int var3);

    public void read_ushort_array(UShortSeqHolder var1, int var2, int var3);

    public void read_long_array(LongSeqHolder var1, int var2, int var3);

    public void read_ulong_array(ULongSeqHolder var1, int var2, int var3);

    public void read_ulonglong_array(ULongLongSeqHolder var1, int var2, int var3);

    public void read_longlong_array(LongLongSeqHolder var1, int var2, int var3);

    public void read_float_array(FloatSeqHolder var1, int var2, int var3);

    public void read_double_array(DoubleSeqHolder var1, int var2, int var3);

    public void read_long_double_array(DoubleSeqHolder var1, int var2, int var3);

    public Any read_fixed(short var1, short var2) throws BadFixedValue;

    public void read_fixed_array(AnySeqHolder var1, int var2, int var3, short var4, short var5) throws BadFixedValue;
}

