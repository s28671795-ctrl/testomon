/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class CODESET_INCOMPATIBLEHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CODESET_INCOMPATIBLEHelper> clazz = CODESET_INCOMPATIBLEHelper.class;
        synchronized (CODESET_INCOMPATIBLEHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(CODESET_INCOMPATIBLEHelper.id(), "CODESET_INCOMPATIBLE", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CODESET_INCOMPATIBLE s2) {
        any.type(CODESET_INCOMPATIBLEHelper.type());
        CODESET_INCOMPATIBLEHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CODESET_INCOMPATIBLE extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CODESET_INCOMPATIBLE cODESET_INCOMPATIBLE = CODESET_INCOMPATIBLEHelper.read(in);
            return cODESET_INCOMPATIBLE;
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
        return "IDL:omg.org/CORBA/CODESET_INCOMPATIBLE:1.0";
    }

    public static CODESET_INCOMPATIBLE read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(CODESET_INCOMPATIBLEHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        CompletionStatus x1 = CompletionStatusHelper.read(in);
        CODESET_INCOMPATIBLE result = new CODESET_INCOMPATIBLE(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, CODESET_INCOMPATIBLE s2) {
        out.write_string(CODESET_INCOMPATIBLEHelper.id());
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }
}

