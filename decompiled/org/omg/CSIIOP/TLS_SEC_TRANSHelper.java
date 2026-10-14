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
import org.omg.CSIIOP.AssociationOptionsHelper;
import org.omg.CSIIOP.TLS_SEC_TRANS;
import org.omg.CSIIOP.TransportAddressHelper;
import org.omg.CSIIOP.TransportAddressListHelper;

public abstract class TLS_SEC_TRANSHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TLS_SEC_TRANSHelper> clazz = TLS_SEC_TRANSHelper.class;
        synchronized (TLS_SEC_TRANSHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(TLS_SEC_TRANSHelper.id(), "TLS_SEC_TRANS", new StructMember[]{new StructMember("target_supports", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("target_requires", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("addresses", ORB.init().create_alias_tc(TransportAddressListHelper.id(), "TransportAddressList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(TransportAddressHelper.id(), "TransportAddress", new StructMember[]{new StructMember("host_name", ORB.init().create_string_tc(0), null), new StructMember("port", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TLS_SEC_TRANS s2) {
        any.type(TLS_SEC_TRANSHelper.type());
        TLS_SEC_TRANSHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TLS_SEC_TRANS extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TLS_SEC_TRANS tLS_SEC_TRANS = TLS_SEC_TRANSHelper.read(in);
            return tLS_SEC_TRANS;
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
        return "IDL:omg.org/CSIIOP/TLS_SEC_TRANS:1.0";
    }

    public static TLS_SEC_TRANS read(InputStream in) {
        TLS_SEC_TRANS result = new TLS_SEC_TRANS();
        result.target_supports = in.read_ushort();
        result.target_requires = in.read_ushort();
        result.addresses = TransportAddressListHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, TLS_SEC_TRANS s2) {
        out.write_ushort(s2.target_supports);
        out.write_ushort(s2.target_requires);
        TransportAddressListHelper.write(out, s2.addresses);
    }
}

