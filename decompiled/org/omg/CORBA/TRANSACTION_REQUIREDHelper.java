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
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TRANSACTION_REQUIRED;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class TRANSACTION_REQUIREDHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TRANSACTION_REQUIREDHelper> clazz = TRANSACTION_REQUIREDHelper.class;
        synchronized (TRANSACTION_REQUIREDHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(TRANSACTION_REQUIREDHelper.id(), "TRANSACTION_REQUIRED", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TRANSACTION_REQUIRED s2) {
        any.type(TRANSACTION_REQUIREDHelper.type());
        TRANSACTION_REQUIREDHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TRANSACTION_REQUIRED extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TRANSACTION_REQUIRED tRANSACTION_REQUIRED = TRANSACTION_REQUIREDHelper.read(in);
            return tRANSACTION_REQUIRED;
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
        return "IDL:omg.org/CORBA/TRANSACTION_REQUIRED:1.0";
    }

    public static TRANSACTION_REQUIRED read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(TRANSACTION_REQUIREDHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        CompletionStatus x1 = CompletionStatusHelper.read(in);
        TRANSACTION_REQUIRED result = new TRANSACTION_REQUIRED(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, TRANSACTION_REQUIRED s2) {
        out.write_string(TRANSACTION_REQUIREDHelper.id());
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }
}

