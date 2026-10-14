/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class TCKindHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TCKindHelper> clazz = TCKindHelper.class;
        synchronized (TCKindHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(TCKindHelper.id(), "TCKind", new String[]{"tk_null", "tk_void", "tk_short", "tk_long", "tk_ushort", "tk_ulong", "tk_float", "tk_double", "tk_boolean", "tk_char", "tk_octet", "tk_any", "tk_TypeCode", "tk_Principal", "tk_objref", "tk_struct", "tk_union", "tk_enum", "tk_string", "tk_sequence", "tk_array", "tk_alias", "tk_except", "tk_longlong", "tk_ulonglong", "tk_longdouble", "tk_wchar", "tk_wstring", "tk_fixed", "tk_value", "tk_value_box", "tk_native", "tk_abstract_interface", "tk_local_interface", "tk_component", "tk_home", "tk_event"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TCKind s2) {
        any.type(TCKindHelper.type());
        TCKindHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TCKind extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TCKind tCKind = TCKindHelper.read(in);
            return tCKind;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Unexpected exception " + e.toString());
            }
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/TCKind:1.0";
    }

    public static TCKind read(InputStream in) {
        return TCKind.from_int(in.read_long());
    }

    public static void write(OutputStream out, TCKind s2) {
        out.write_long(s2.value());
    }
}

