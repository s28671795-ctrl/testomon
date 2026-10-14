/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.SystemExceptionReplyBody;

public abstract class SystemExceptionReplyBodyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SystemExceptionReplyBodyHelper> clazz = SystemExceptionReplyBodyHelper.class;
        synchronized (SystemExceptionReplyBodyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(SystemExceptionReplyBodyHelper.id(), "SystemExceptionReplyBody", new StructMember[]{new StructMember("exception_id", ORB.init().create_string_tc(0), null), new StructMember("minor_code_value", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completion_status", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SystemExceptionReplyBody s2) {
        any.type(SystemExceptionReplyBodyHelper.type());
        SystemExceptionReplyBodyHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SystemExceptionReplyBody extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SystemExceptionReplyBody systemExceptionReplyBody = SystemExceptionReplyBodyHelper.read(in);
            return systemExceptionReplyBody;
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
        return "IDL:omg.org/GIOP/SystemExceptionReplyBody:1.0";
    }

    public static SystemExceptionReplyBody read(InputStream in) {
        SystemExceptionReplyBody result = new SystemExceptionReplyBody();
        result.exception_id = in.read_string();
        result.minor_code_value = in.read_ulong();
        result.completion_status = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, SystemExceptionReplyBody s2) {
        String tmpResult3 = s2.exception_id;
        out.write_string(tmpResult3);
        out.write_ulong(s2.minor_code_value);
        out.write_ulong(s2.completion_status);
    }
}

