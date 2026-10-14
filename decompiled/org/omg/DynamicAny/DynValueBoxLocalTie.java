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
import org.omg.DynamicAny.DynValueBoxOperations;
import org.omg.DynamicAny._DynValueBoxLocalBase;

public class DynValueBoxLocalTie
extends _DynValueBoxLocalBase {
    private static final long serialVersionUID = 1L;
    private DynValueBoxOperations _delegate;

    public DynValueBoxLocalTie(DynValueBoxOperations delegate) {
        this._delegate = delegate;
    }

    public DynValueBoxOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(DynValueBoxOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void insert_long(int value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_long(value);
    }

    @Override
    public void rewind() {
        this._delegate.rewind();
    }

    @Override
    public void insert_short_seq(short[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_short_seq(value);
    }

    @Override
    public int component_count() {
        return this._delegate.component_count();
    }

    @Override
    public void assign(DynAny dyn_any) throws TypeMismatch {
        this._delegate.assign(dyn_any);
    }

    @Override
    public char get_char() throws InvalidValue, TypeMismatch {
        return this._delegate.get_char();
    }

    @Override
    public void insert_dyn_any(DynAny value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_dyn_any(value);
    }

    @Override
    public long[] get_ulonglong_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_ulonglong_seq();
    }

    @Override
    public void from_any(Any value) throws InvalidValue, TypeMismatch {
        this._delegate.from_any(value);
    }

    @Override
    public boolean[] get_boolean_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_boolean_seq();
    }

    @Override
    public void insert_ushort_seq(short[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_ushort_seq(value);
    }

    @Override
    public void insert_float_seq(float[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_float_seq(value);
    }

    @Override
    public void insert_boolean(boolean value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_boolean(value);
    }

    @Override
    public double[] get_longdouble_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_longdouble_seq();
    }

    @Override
    public void insert_char(char value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_char(value);
    }

    @Override
    public boolean equal(DynAny dyn_any) {
        return this._delegate.equal(dyn_any);
    }

    @Override
    public Object get_reference() throws InvalidValue, TypeMismatch {
        return this._delegate.get_reference();
    }

    @Override
    public void set_boxed_value(Any boxed) throws InvalidValue, TypeMismatch {
        this._delegate.set_boxed_value(boxed);
    }

    @Override
    public Any to_any() {
        return this._delegate.to_any();
    }

    @Override
    public int[] get_ulong_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_ulong_seq();
    }

    @Override
    public DynAny get_boxed_value_as_dyn_any() throws InvalidValue {
        return this._delegate.get_boxed_value_as_dyn_any();
    }

    @Override
    public void insert_long_seq(int[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_long_seq(value);
    }

    @Override
    public void insert_double(double value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_double(value);
    }

    @Override
    public void insert_short(short value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_short(value);
    }

    @Override
    public short get_short() throws InvalidValue, TypeMismatch {
        return this._delegate.get_short();
    }

    @Override
    public void insert_longlong_seq(long[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_longlong_seq(value);
    }

    @Override
    public void set_to_value() {
        this._delegate.set_to_value();
    }

    @Override
    public void insert_reference(Object value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_reference(value);
    }

    @Override
    public String get_string() throws InvalidValue, TypeMismatch {
        return this._delegate.get_string();
    }

    @Override
    public void insert_wchar_seq(char[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_wchar_seq(value);
    }

    @Override
    public short get_ushort() throws InvalidValue, TypeMismatch {
        return this._delegate.get_ushort();
    }

    @Override
    public TypeCode type() {
        return this._delegate.type();
    }

    @Override
    public DynAny get_dyn_any() throws InvalidValue, TypeMismatch {
        return this._delegate.get_dyn_any();
    }

    @Override
    public DynAny current_component() throws TypeMismatch {
        return this._delegate.current_component();
    }

    @Override
    public int[] get_long_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_long_seq();
    }

    @Override
    public void insert_wchar(char value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_wchar(value);
    }

    @Override
    public boolean seek(int index) {
        return this._delegate.seek(index);
    }

    @Override
    public char[] get_char_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_char_seq();
    }

    @Override
    public TypeCode get_typecode() throws InvalidValue, TypeMismatch {
        return this._delegate.get_typecode();
    }

    @Override
    public void insert_wstring(String value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_wstring(value);
    }

    @Override
    public void insert_string(String value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_string(value);
    }

    @Override
    public void insert_longlong(long value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_longlong(value);
    }

    @Override
    public void insert_octet(byte value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_octet(value);
    }

    @Override
    public void insert_ushort(short value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_ushort(value);
    }

    @Override
    public String get_wstring() throws InvalidValue, TypeMismatch {
        return this._delegate.get_wstring();
    }

    @Override
    public void insert_float(float value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_float(value);
    }

    @Override
    public Any get_boxed_value() throws InvalidValue {
        return this._delegate.get_boxed_value();
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public void insert_boolean_seq(boolean[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_boolean_seq(value);
    }

    @Override
    public long get_ulonglong() throws InvalidValue, TypeMismatch {
        return this._delegate.get_ulonglong();
    }

    @Override
    public long[] get_longlong_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_longlong_seq();
    }

    @Override
    public Any get_any() throws InvalidValue, TypeMismatch {
        return this._delegate.get_any();
    }

    @Override
    public char get_wchar() throws InvalidValue, TypeMismatch {
        return this._delegate.get_wchar();
    }

    @Override
    public void set_boxed_value_as_dyn_any(DynAny boxed) throws TypeMismatch {
        this._delegate.set_boxed_value_as_dyn_any(boxed);
    }

    @Override
    public short[] get_ushort_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_ushort_seq();
    }

    @Override
    public double[] get_double_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_double_seq();
    }

    @Override
    public float get_float() throws InvalidValue, TypeMismatch {
        return this._delegate.get_float();
    }

    @Override
    public void insert_ulonglong_seq(long[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_ulonglong_seq(value);
    }

    @Override
    public Serializable get_val() throws InvalidValue, TypeMismatch {
        return this._delegate.get_val();
    }

    @Override
    public void insert_octet_seq(byte[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_octet_seq(value);
    }

    @Override
    public void insert_typecode(TypeCode value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_typecode(value);
    }

    @Override
    public long get_longlong() throws InvalidValue, TypeMismatch {
        return this._delegate.get_longlong();
    }

    @Override
    public void insert_any(Any value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_any(value);
    }

    @Override
    public boolean is_null() {
        return this._delegate.is_null();
    }

    @Override
    public int get_ulong() throws InvalidValue, TypeMismatch {
        return this._delegate.get_ulong();
    }

    @Override
    public byte get_octet() throws InvalidValue, TypeMismatch {
        return this._delegate.get_octet();
    }

    @Override
    public void insert_ulonglong(long value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_ulonglong(value);
    }

    @Override
    public void set_to_null() {
        this._delegate.set_to_null();
    }

    @Override
    public int get_long() throws InvalidValue, TypeMismatch {
        return this._delegate.get_long();
    }

    @Override
    public void insert_char_seq(char[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_char_seq(value);
    }

    @Override
    public void insert_val(Serializable value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_val(value);
    }

    @Override
    public boolean get_boolean() throws InvalidValue, TypeMismatch {
        return this._delegate.get_boolean();
    }

    @Override
    public void insert_ulong_seq(int[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_ulong_seq(value);
    }

    @Override
    public byte[] get_octet_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_octet_seq();
    }

    @Override
    public double get_longdouble() throws InvalidValue, TypeMismatch {
        return this._delegate.get_longdouble();
    }

    @Override
    public boolean next() {
        return this._delegate.next();
    }

    @Override
    public void insert_double_seq(double[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_double_seq(value);
    }

    @Override
    public DynAny copy() {
        return this._delegate.copy();
    }

    @Override
    public void insert_longdouble(double value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_longdouble(value);
    }

    @Override
    public void insert_longdouble_seq(double[] value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_longdouble_seq(value);
    }

    @Override
    public short[] get_short_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_short_seq();
    }

    @Override
    public char[] get_wchar_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_wchar_seq();
    }

    @Override
    public void insert_ulong(int value) throws InvalidValue, TypeMismatch {
        this._delegate.insert_ulong(value);
    }

    @Override
    public float[] get_float_seq() throws InvalidValue, TypeMismatch {
        return this._delegate.get_float_seq();
    }

    @Override
    public double get_double() throws InvalidValue, TypeMismatch {
        return this._delegate.get_double();
    }
}

