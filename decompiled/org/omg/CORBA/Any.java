/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.Serializable;
import java.math.BigDecimal;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.Object;
import org.omg.CORBA.Principal;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public abstract class Any
implements IDLEntity {
    public abstract boolean equal(Any var1);

    public abstract TypeCode type();

    public abstract void type(TypeCode var1);

    public abstract void read_value(InputStream var1, TypeCode var2);

    public abstract void write_value(OutputStream var1);

    public abstract OutputStream create_output_stream();

    public abstract InputStream create_input_stream();

    public abstract short extract_short();

    public abstract void insert_short(short var1);

    public abstract int extract_long();

    public abstract void insert_long(int var1);

    public abstract long extract_longlong();

    public abstract void insert_longlong(long var1);

    public abstract short extract_ushort();

    public abstract void insert_ushort(short var1);

    public abstract int extract_ulong();

    public abstract void insert_ulong(int var1);

    public abstract long extract_ulonglong();

    public abstract void insert_ulonglong(long var1);

    public abstract float extract_float();

    public abstract void insert_float(float var1);

    public abstract double extract_double();

    public abstract void insert_double(double var1);

    public abstract boolean extract_boolean();

    public abstract void insert_boolean(boolean var1);

    public abstract char extract_char();

    public abstract void insert_char(char var1);

    public abstract char extract_wchar();

    public abstract void insert_wchar(char var1);

    public abstract byte extract_octet();

    public abstract void insert_octet(byte var1);

    public abstract Any extract_any();

    public abstract void insert_any(Any var1);

    public abstract Object extract_Object();

    public abstract void insert_Object(Object var1);

    public abstract Serializable extract_Value();

    public abstract void insert_Value(Serializable var1);

    public abstract void insert_Value(Serializable var1, TypeCode var2);

    public abstract void insert_Object(Object var1, TypeCode var2);

    public abstract String extract_string();

    public abstract void insert_string(String var1);

    public abstract String extract_wstring();

    public abstract void insert_wstring(String var1);

    public abstract TypeCode extract_TypeCode();

    public abstract void insert_TypeCode(TypeCode var1);

    @Deprecated
    public Principal extract_Principal() {
        throw new NO_IMPLEMENT();
    }

    @Deprecated
    public void insert_Principal(Principal p) {
        throw new NO_IMPLEMENT();
    }

    public Streamable extract_Streamable() {
        throw new NO_IMPLEMENT();
    }

    public void insert_Streamable(Streamable s2) {
        throw new NO_IMPLEMENT();
    }

    public BigDecimal extract_fixed() {
        throw new NO_IMPLEMENT();
    }

    public void insert_fixed(BigDecimal f, TypeCode t2) {
        throw new NO_IMPLEMENT();
    }
}

