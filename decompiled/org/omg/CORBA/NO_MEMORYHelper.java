/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_MEMORY;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class NO_MEMORYHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NO_MEMORYHelper> clazz = NO_MEMORYHelper.class;
        synchronized (NO_MEMORYHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(NO_MEMORYHelper.id(), "NO_MEMORY", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NO_MEMORY s2) {
        any.type(NO_MEMORYHelper.type());
        NO_MEMORYHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NO_MEMORY extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NO_MEMORY nO_MEMORY = NO_MEMORYHelper.read(in);
            return nO_MEMORY;
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
        return "IDL:omg.org/CORBA/NO_MEMORY:1.0";
    }

    public static NO_MEMORY read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(NO_MEMORYHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        CompletionStatus x1 = CompletionStatusHelper.read(in);
        NO_MEMORY result = new NO_MEMORY(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, NO_MEMORY s2) {
        out.write_string(NO_MEMORYHelper.id());
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }
}

