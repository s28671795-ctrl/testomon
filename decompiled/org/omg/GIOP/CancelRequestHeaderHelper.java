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
import org.omg.GIOP.CancelRequestHeader;

public abstract class CancelRequestHeaderHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CancelRequestHeaderHelper> clazz = CancelRequestHeaderHelper.class;
        synchronized (CancelRequestHeaderHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(CancelRequestHeaderHelper.id(), "CancelRequestHeader", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CancelRequestHeader s2) {
        any.type(CancelRequestHeaderHelper.type());
        CancelRequestHeaderHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CancelRequestHeader extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CancelRequestHeader cancelRequestHeader = CancelRequestHeaderHelper.read(in);
            return cancelRequestHeader;
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
        return "IDL:omg.org/GIOP/CancelRequestHeader:1.0";
    }

    public static CancelRequestHeader read(InputStream in) {
        CancelRequestHeader result = new CancelRequestHeader();
        result.request_id = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, CancelRequestHeader s2) {
        out.write_ulong(s2.request_id);
    }
}

