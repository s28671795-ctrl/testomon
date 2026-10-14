/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.PERSIST_STORE;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class PERSIST_STOREHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PERSIST_STOREHelper> clazz = PERSIST_STOREHelper.class;
        synchronized (PERSIST_STOREHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(PERSIST_STOREHelper.id(), "PERSIST_STORE", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PERSIST_STORE s2) {
        any.type(PERSIST_STOREHelper.type());
        PERSIST_STOREHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PERSIST_STORE extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            PERSIST_STORE pERSIST_STORE = PERSIST_STOREHelper.read(in);
            return pERSIST_STORE;
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
        return "IDL:omg.org/CORBA/PERSIST_STORE:1.0";
    }

    public static PERSIST_STORE read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(PERSIST_STOREHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        CompletionStatus x1 = CompletionStatusHelper.read(in);
        PERSIST_STORE result = new PERSIST_STORE(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, PERSIST_STORE s2) {
        out.write_string(PERSIST_STOREHelper.id());
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }
}

