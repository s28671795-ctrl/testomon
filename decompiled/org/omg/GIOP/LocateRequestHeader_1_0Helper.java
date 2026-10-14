/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.LocateRequestHeader_1_0;
import org.omg.IOP.ObjectKeyHelper;

public abstract class LocateRequestHeader_1_0Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<LocateRequestHeader_1_0Helper> clazz = LocateRequestHeader_1_0Helper.class;
        synchronized (LocateRequestHeader_1_0Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(LocateRequestHeader_1_0Helper.id(), "LocateRequestHeader_1_0", new StructMember[]{new StructMember("request_id", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("object_key", ORB.init().create_alias_tc(ObjectKeyHelper.id(), "ObjectKey", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, LocateRequestHeader_1_0 s2) {
        any.type(LocateRequestHeader_1_0Helper.type());
        LocateRequestHeader_1_0Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static LocateRequestHeader_1_0 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            LocateRequestHeader_1_0 locateRequestHeader_1_0 = LocateRequestHeader_1_0Helper.read(in);
            return locateRequestHeader_1_0;
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
        return "IDL:omg.org/GIOP/LocateRequestHeader_1_0:1.0";
    }

    public static LocateRequestHeader_1_0 read(InputStream in) {
        LocateRequestHeader_1_0 result = new LocateRequestHeader_1_0();
        result.request_id = in.read_ulong();
        result.object_key = OctetSeqHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, LocateRequestHeader_1_0 s2) {
        out.write_ulong(s2.request_id);
        OctetSeqHelper.write(out, s2.object_key);
    }
}

