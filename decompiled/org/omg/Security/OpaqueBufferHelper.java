/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.OpaqueBuffer;
import org.omg.Security.OpaqueHelper;

public abstract class OpaqueBufferHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<OpaqueBufferHelper> clazz = OpaqueBufferHelper.class;
        synchronized (OpaqueBufferHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(OpaqueBufferHelper.id(), "OpaqueBuffer", new StructMember[]{new StructMember("buffer", ORB.init().create_alias_tc(OpaqueHelper.id(), "Opaque", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null), new StructMember("startpos", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("endpos", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, OpaqueBuffer s2) {
        any.type(OpaqueBufferHelper.type());
        OpaqueBufferHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static OpaqueBuffer extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            OpaqueBuffer opaqueBuffer = OpaqueBufferHelper.read(in);
            return opaqueBuffer;
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
        return "IDL:omg.org/Security/OpaqueBuffer:1.0";
    }

    public static OpaqueBuffer read(InputStream in) {
        OpaqueBuffer result = new OpaqueBuffer();
        result.buffer = OpaqueHelper.read(in);
        result.startpos = in.read_ulong();
        result.endpos = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, OpaqueBuffer s2) {
        OpaqueHelper.write(out, s2.buffer);
        out.write_ulong(s2.startpos);
        out.write_ulong(s2.endpos);
    }
}

