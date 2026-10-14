/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class INV_OBJREFHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<INV_OBJREFHelper> clazz = INV_OBJREFHelper.class;
        synchronized (INV_OBJREFHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(INV_OBJREFHelper.id(), "INV_OBJREF", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, INV_OBJREF s2) {
        any.type(INV_OBJREFHelper.type());
        INV_OBJREFHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static INV_OBJREF extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            INV_OBJREF iNV_OBJREF = INV_OBJREFHelper.read(in);
            return iNV_OBJREF;
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
        return "IDL:omg.org/CORBA/INV_OBJREF:1.0";
    }

    public static INV_OBJREF read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(INV_OBJREFHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        CompletionStatus x1 = CompletionStatusHelper.read(in);
        INV_OBJREF result = new INV_OBJREF(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, INV_OBJREF s2) {
        out.write_string(INV_OBJREFHelper.id());
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }
}

