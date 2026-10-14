/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.util.Hashtable;

public class DynAnyIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("insert_longlong", "(in:value )");
        irInfo.put("get_val", "()");
        irInfo.put("insert_octet", "(in:value )");
        irInfo.put("get_reference", "()");
        irInfo.put("destroy", "()");
        irInfo.put("get_wchar", "()");
        irInfo.put("get_octet_seq", "org.omg.CORBA.OctetSeq()");
        irInfo.put("insert_val", "(in:value )");
        irInfo.put("insert_typecode", "(in:value )");
        irInfo.put("get_ushort_seq", "org.omg.CORBA.UShortSeq()");
        irInfo.put("to_any", "()");
        irInfo.put("rewind", "()");
        irInfo.put("get_short_seq", "org.omg.CORBA.ShortSeq()");
        irInfo.put("get_long", "()");
        irInfo.put("insert_double_seq", "(in:value org.omg.CORBA.DoubleSeq)");
        irInfo.put("insert_longlong_seq", "(in:value org.omg.CORBA.LongLongSeq)");
        irInfo.put("get_char", "()");
        irInfo.put("insert_longdouble", "(in:value )");
        irInfo.put("get_ulonglong_seq", "org.omg.CORBA.ULongLongSeq()");
        irInfo.put("copy", "()");
        irInfo.put("get_float", "()");
        irInfo.put("get_float_seq", "org.omg.CORBA.FloatSeq()");
        irInfo.put("get_string", "()");
        irInfo.put("insert_wstring", "(in:value )");
        irInfo.put("get_ulong", "()");
        irInfo.put("insert_wchar_seq", "(in:value org.omg.CORBA.WCharSeq)");
        irInfo.put("insert_ulong_seq", "(in:value org.omg.CORBA.ULongSeq)");
        irInfo.put("insert_short", "(in:value )");
        irInfo.put("get_longdouble", "()");
        irInfo.put("get_longlong_seq", "org.omg.CORBA.LongLongSeq()");
        irInfo.put("insert_ulonglong", "(in:value )");
        irInfo.put("insert_long", "(in:value )");
        irInfo.put("get_any", "()");
        irInfo.put("get_boolean_seq", "org.omg.CORBA.BooleanSeq()");
        irInfo.put("type", "()");
        irInfo.put("insert_char", "(in:value )");
        irInfo.put("insert_dyn_any", "(in:value )");
        irInfo.put("insert_boolean", "(in:value )");
        irInfo.put("get_double_seq", "org.omg.CORBA.DoubleSeq()");
        irInfo.put("get_octet", "()");
        irInfo.put("get_long_seq", "org.omg.CORBA.LongSeq()");
        irInfo.put("insert_string", "(in:value )");
        irInfo.put("insert_wchar", "(in:value )");
        irInfo.put("get_double", "()");
        irInfo.put("insert_any", "(in:value )");
        irInfo.put("get_char_seq", "org.omg.CORBA.CharSeq()");
        irInfo.put("insert_reference", "(in:value )");
        irInfo.put("insert_ulonglong_seq", "(in:value org.omg.CORBA.ULongLongSeq)");
        irInfo.put("next", "()");
        irInfo.put("get_longlong", "()");
        irInfo.put("get_longdouble_seq", "org.omg.CORBA.LongDoubleSeq()");
        irInfo.put("insert_longdouble_seq", "(in:value org.omg.CORBA.LongDoubleSeq)");
        irInfo.put("get_ushort", "()");
        irInfo.put("component_count", "()");
        irInfo.put("current_component", "()");
        irInfo.put("get_typecode", "()");
        irInfo.put("insert_octet_seq", "(in:value org.omg.CORBA.OctetSeq)");
        irInfo.put("get_wchar_seq", "org.omg.CORBA.WCharSeq()");
        irInfo.put("insert_float", "(in:value )");
        irInfo.put("insert_double", "(in:value )");
        irInfo.put("insert_ushort_seq", "(in:value org.omg.CORBA.UShortSeq)");
        irInfo.put("assign", "(in:dyn_any )");
        irInfo.put("insert_short_seq", "(in:value org.omg.CORBA.ShortSeq)");
        irInfo.put("seek", "(in:index )");
        irInfo.put("get_ulong_seq", "org.omg.CORBA.ULongSeq()");
        irInfo.put("insert_boolean_seq", "(in:value org.omg.CORBA.BooleanSeq)");
        irInfo.put("insert_ushort", "(in:value )");
        irInfo.put("insert_ulong", "(in:value )");
        irInfo.put("equal", "(in:dyn_any )");
        irInfo.put("get_wstring", "()");
        irInfo.put("insert_long_seq", "(in:value org.omg.CORBA.LongSeq)");
        irInfo.put("get_ulonglong", "()");
        irInfo.put("get_boolean", "()");
        irInfo.put("from_any", "(in:value )");
        irInfo.put("get_dyn_any", "()");
        irInfo.put("insert_char_seq", "(in:value org.omg.CORBA.CharSeq)");
        irInfo.put("get_short", "()");
        irInfo.put("insert_float_seq", "(in:value org.omg.CORBA.FloatSeq)");
    }
}

