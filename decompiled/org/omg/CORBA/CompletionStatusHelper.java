/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class CompletionStatusHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CompletionStatusHelper> clazz = CompletionStatusHelper.class;
        synchronized (CompletionStatusHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CompletionStatus s2) {
        any.type(CompletionStatusHelper.type());
        CompletionStatusHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CompletionStatus extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CompletionStatus completionStatus = CompletionStatusHelper.read(in);
            return completionStatus;
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
        return "IDL:omg.org/CORBA/CompletionStatus:1.0";
    }

    public static CompletionStatus read(InputStream in) {
        return CompletionStatus.from_int(in.read_long());
    }

    public static void write(OutputStream out, CompletionStatus s2) {
        out.write_long(s2.value());
    }
}

