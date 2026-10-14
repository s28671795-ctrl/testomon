/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class INVALID_TRANSACTIONHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<INVALID_TRANSACTIONHelper> clazz = INVALID_TRANSACTIONHelper.class;
        synchronized (INVALID_TRANSACTIONHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(INVALID_TRANSACTIONHelper.id(), "INVALID_TRANSACTION", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, INVALID_TRANSACTION s2) {
        any.type(INVALID_TRANSACTIONHelper.type());
        INVALID_TRANSACTIONHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static INVALID_TRANSACTION extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            INVALID_TRANSACTION iNVALID_TRANSACTION = INVALID_TRANSACTIONHelper.read(in);
            return iNVALID_TRANSACTION;
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
        return "IDL:omg.org/CORBA/INVALID_TRANSACTION:1.0";
    }

    public static INVALID_TRANSACTION read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(INVALID_TRANSACTIONHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        CompletionStatus x1 = CompletionStatusHelper.read(in);
        INVALID_TRANSACTION result = new INVALID_TRANSACTION(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, INVALID_TRANSACTION s2) {
        out.write_string(INVALID_TRANSACTIONHelper.id());
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }
}

