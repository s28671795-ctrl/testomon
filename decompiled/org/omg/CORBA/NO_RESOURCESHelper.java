/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_RESOURCES;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class NO_RESOURCESHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NO_RESOURCESHelper> clazz = NO_RESOURCESHelper.class;
        synchronized (NO_RESOURCESHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(NO_RESOURCESHelper.id(), "NO_RESOURCES", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NO_RESOURCES s2) {
        any.type(NO_RESOURCESHelper.type());
        NO_RESOURCESHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NO_RESOURCES extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NO_RESOURCES nO_RESOURCES = NO_RESOURCESHelper.read(in);
            return nO_RESOURCES;
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
        return "IDL:omg.org/CORBA/NO_RESOURCES:1.0";
    }

    public static NO_RESOURCES read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(NO_RESOURCESHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        CompletionStatus x1 = CompletionStatusHelper.read(in);
        NO_RESOURCES result = new NO_RESOURCES(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, NO_RESOURCES s2) {
        out.write_string(NO_RESOURCESHelper.id());
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }
}

