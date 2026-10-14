/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class BAD_INV_ORDERHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BAD_INV_ORDERHelper> clazz = BAD_INV_ORDERHelper.class;
        synchronized (BAD_INV_ORDERHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(BAD_INV_ORDERHelper.id(), "BAD_INV_ORDER", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, BAD_INV_ORDER s2) {
        any.type(BAD_INV_ORDERHelper.type());
        BAD_INV_ORDERHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BAD_INV_ORDER extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            BAD_INV_ORDER bAD_INV_ORDER = BAD_INV_ORDERHelper.read(in);
            return bAD_INV_ORDER;
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
        return "IDL:omg.org/CORBA/BAD_INV_ORDER:1.0";
    }

    public static BAD_INV_ORDER read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(BAD_INV_ORDERHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        CompletionStatus x1 = CompletionStatusHelper.read(in);
        BAD_INV_ORDER result = new BAD_INV_ORDER(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, BAD_INV_ORDER s2) {
        out.write_string(BAD_INV_ORDERHelper.id());
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }
}

