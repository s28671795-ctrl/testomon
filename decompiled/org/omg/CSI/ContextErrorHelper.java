/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.ContextError;
import org.omg.CSI.ContextIdHelper;
import org.omg.CSI.GSSTokenHelper;

public abstract class ContextErrorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ContextErrorHelper> clazz = ContextErrorHelper.class;
        synchronized (ContextErrorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ContextErrorHelper.id(), "ContextError", new StructMember[]{new StructMember("client_context_id", ORB.init().create_alias_tc(ContextIdHelper.id(), "ContextId", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("major_status", ORB.init().get_primitive_tc(TCKind.from_int(3)), null), new StructMember("minor_status", ORB.init().get_primitive_tc(TCKind.from_int(3)), null), new StructMember("error_token", ORB.init().create_alias_tc(GSSTokenHelper.id(), "GSSToken", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ContextError s2) {
        any.type(ContextErrorHelper.type());
        ContextErrorHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ContextError extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ContextError contextError = ContextErrorHelper.read(in);
            return contextError;
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
        return "IDL:omg.org/CSI/ContextError:1.0";
    }

    public static ContextError read(InputStream in) {
        ContextError result = new ContextError();
        result.client_context_id = in.read_ulonglong();
        result.major_status = in.read_long();
        result.minor_status = in.read_long();
        result.error_token = GSSTokenHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, ContextError s2) {
        out.write_ulonglong(s2.client_context_id);
        out.write_long(s2.major_status);
        out.write_long(s2.minor_status);
        GSSTokenHelper.write(out, s2.error_token);
    }
}

