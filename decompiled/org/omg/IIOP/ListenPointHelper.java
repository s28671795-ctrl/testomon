/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IIOP.ListenPoint;

public abstract class ListenPointHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ListenPointHelper> clazz = ListenPointHelper.class;
        synchronized (ListenPointHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ListenPointHelper.id(), "ListenPoint", new StructMember[]{new StructMember("host", ORB.init().create_string_tc(0), null), new StructMember("port", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ListenPoint s2) {
        any.type(ListenPointHelper.type());
        ListenPointHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ListenPoint extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ListenPoint listenPoint = ListenPointHelper.read(in);
            return listenPoint;
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
        return "IDL:omg.org/IIOP/ListenPoint:1.0";
    }

    public static ListenPoint read(InputStream in) {
        ListenPoint result = new ListenPoint();
        result.host = in.read_string();
        result.port = in.read_ushort();
        return result;
    }

    public static void write(OutputStream out, ListenPoint s2) {
        String tmpResult2 = s2.host;
        out.write_string(tmpResult2);
        out.write_ushort(s2.port);
    }
}

