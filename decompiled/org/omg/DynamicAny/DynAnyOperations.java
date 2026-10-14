/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.io.Serializable;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

public interface DynAnyOperations {
    public TypeCode type();

    public void assign(DynAny var1) throws TypeMismatch;

    public void from_any(Any var1) throws InvalidValue, TypeMismatch;

    public Any to_any();

    public boolean equal(DynAny var1);

    public void destroy();

    public DynAny copy();

    public void insert_boolean(boolean var1) throws InvalidValue, TypeMismatch;

    public void insert_octet(byte var1) throws InvalidValue, TypeMismatch;

    public void insert_char(char var1) throws InvalidValue, TypeMismatch;

    public void insert_short(short var1) throws InvalidValue, TypeMismatch;

    public void insert_ushort(short var1) throws InvalidValue, TypeMismatch;

    public void insert_long(int var1) throws InvalidValue, TypeMismatch;

    public void insert_ulong(int var1) throws InvalidValue, TypeMismatch;

    public void insert_float(float var1) throws InvalidValue, TypeMismatch;

    public void insert_double(double var1) throws InvalidValue, TypeMismatch;

    public void insert_string(String var1) throws InvalidValue, TypeMismatch;

    public void insert_reference(Object var1) throws InvalidValue, TypeMismatch;

    public void insert_typecode(TypeCode var1) throws InvalidValue, TypeMismatch;

    public void insert_longlong(long var1) throws InvalidValue, TypeMismatch;

    public void insert_ulonglong(long var1) throws InvalidValue, TypeMismatch;

    public void insert_longdouble(double var1) throws InvalidValue, TypeMismatch;

    public void insert_wchar(char var1) throws InvalidValue, TypeMismatch;

    public void insert_wstring(String var1) throws InvalidValue, TypeMismatch;

    public void insert_any(Any var1) throws InvalidValue, TypeMismatch;

    public void insert_dyn_any(DynAny var1) throws InvalidValue, TypeMismatch;

    public void insert_val(Serializable var1) throws InvalidValue, TypeMismatch;

    public boolean get_boolean() throws InvalidValue, TypeMismatch;

    public byte get_octet() throws InvalidValue, TypeMismatch;

    public char get_char() throws InvalidValue, TypeMismatch;

    public short get_short() throws InvalidValue, TypeMismatch;

    public short get_ushort() throws InvalidValue, TypeMismatch;

    public int get_long() throws InvalidValue, TypeMismatch;

    public int get_ulong() throws InvalidValue, TypeMismatch;

    public float get_float() throws InvalidValue, TypeMismatch;

    public double get_double() throws InvalidValue, TypeMismatch;

    public String get_string() throws InvalidValue, TypeMismatch;

    public Object get_reference() throws InvalidValue, TypeMismatch;

    public TypeCode get_typecode() throws InvalidValue, TypeMismatch;

    public long get_longlong() throws InvalidValue, TypeMismatch;

    public long get_ulonglong() throws InvalidValue, TypeMismatch;

    public double get_longdouble() throws InvalidValue, TypeMismatch;

    public char get_wchar() throws InvalidValue, TypeMismatch;

    public String get_wstring() throws InvalidValue, TypeMismatch;

    public Any get_any() throws InvalidValue, TypeMismatch;

    public DynAny get_dyn_any() throws InvalidValue, TypeMismatch;

    public Serializable get_val() throws InvalidValue, TypeMismatch;

    public boolean seek(int var1);

    public void rewind();

    public boolean next();

    public int component_count();

    public DynAny current_component() throws TypeMismatch;

    public void insert_boolean_seq(boolean[] var1) throws InvalidValue, TypeMismatch;

    public void insert_octet_seq(byte[] var1) throws InvalidValue, TypeMismatch;

    public void insert_char_seq(char[] var1) throws InvalidValue, TypeMismatch;

    public void insert_short_seq(short[] var1) throws InvalidValue, TypeMismatch;

    public void insert_ushort_seq(short[] var1) throws InvalidValue, TypeMismatch;

    public void insert_long_seq(int[] var1) throws InvalidValue, TypeMismatch;

    public void insert_ulong_seq(int[] var1) throws InvalidValue, TypeMismatch;

    public void insert_float_seq(float[] var1) throws InvalidValue, TypeMismatch;

    public void insert_double_seq(double[] var1) throws InvalidValue, TypeMismatch;

    public void insert_longlong_seq(long[] var1) throws InvalidValue, TypeMismatch;

    public void insert_ulonglong_seq(long[] var1) throws InvalidValue, TypeMismatch;

    public void insert_longdouble_seq(double[] var1) throws InvalidValue, TypeMismatch;

    public void insert_wchar_seq(char[] var1) throws InvalidValue, TypeMismatch;

    public boolean[] get_boolean_seq() throws InvalidValue, TypeMismatch;

    public byte[] get_octet_seq() throws InvalidValue, TypeMismatch;

    public char[] get_char_seq() throws InvalidValue, TypeMismatch;

    public short[] get_short_seq() throws InvalidValue, TypeMismatch;

    public short[] get_ushort_seq() throws InvalidValue, TypeMismatch;

    public int[] get_long_seq() throws InvalidValue, TypeMismatch;

    public int[] get_ulong_seq() throws InvalidValue, TypeMismatch;

    public float[] get_float_seq() throws InvalidValue, TypeMismatch;

    public double[] get_double_seq() throws InvalidValue, TypeMismatch;

    public long[] get_longlong_seq() throws InvalidValue, TypeMismatch;

    public long[] get_ulonglong_seq() throws InvalidValue, TypeMismatch;

    public double[] get_longdouble_seq() throws InvalidValue, TypeMismatch;

    public char[] get_wchar_seq() throws InvalidValue, TypeMismatch;
}

