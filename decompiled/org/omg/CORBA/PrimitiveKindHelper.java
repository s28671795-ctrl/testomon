/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.PrimitiveKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class PrimitiveKindHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PrimitiveKindHelper> clazz = PrimitiveKindHelper.class;
        synchronized (PrimitiveKindHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(PrimitiveKindHelper.id(), "PrimitiveKind", new String[]{"pk_null", "pk_void", "pk_short", "pk_long", "pk_ushort", "pk_ulong", "pk_float", "pk_double", "pk_boolean", "pk_char", "pk_octet", "pk_any", "pk_TypeCode", "pk_Principal", "pk_string", "pk_objref", "pk_longlong", "pk_ulonglong", "pk_longdouble", "pk_wchar", "pk_wstring", "pk_value_base"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PrimitiveKind s2) {
        any.type(PrimitiveKindHelper.type());
        PrimitiveKindHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PrimitiveKind extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            PrimitiveKind primitiveKind = PrimitiveKindHelper.read(in);
            return primitiveKind;
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
        return "IDL:omg.org/CORBA/PrimitiveKind:1.0";
    }

    public static PrimitiveKind read(InputStream in) {
        return PrimitiveKind.from_int(in.read_long());
    }

    public static void write(OutputStream out, PrimitiveKind s2) {
        out.write_long(s2.value());
    }
}

