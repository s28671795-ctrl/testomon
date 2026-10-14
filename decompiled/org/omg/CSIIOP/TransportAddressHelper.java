/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSIIOP.TransportAddress;

public abstract class TransportAddressHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TransportAddressHelper> clazz = TransportAddressHelper.class;
        synchronized (TransportAddressHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(TransportAddressHelper.id(), "TransportAddress", new StructMember[]{new StructMember("host_name", ORB.init().create_string_tc(0), null), new StructMember("port", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TransportAddress s2) {
        any.type(TransportAddressHelper.type());
        TransportAddressHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TransportAddress extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TransportAddress transportAddress = TransportAddressHelper.read(in);
            return transportAddress;
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
        return "IDL:omg.org/CSIIOP/TransportAddress:1.0";
    }

    public static TransportAddress read(InputStream in) {
        TransportAddress result = new TransportAddress();
        result.host_name = in.read_string();
        result.port = in.read_ushort();
        return result;
    }

    public static void write(OutputStream out, TransportAddress s2) {
        String tmpResult0 = s2.host_name;
        out.write_string(tmpResult0);
        out.write_ushort(s2.port);
    }
}

