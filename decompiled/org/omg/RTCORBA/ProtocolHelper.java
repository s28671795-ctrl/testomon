/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.ProfileIdHelper;
import org.omg.RTCORBA.Protocol;
import org.omg.RTCORBA.ProtocolPropertiesHelper;

public abstract class ProtocolHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ProtocolHelper> clazz = ProtocolHelper.class;
        synchronized (ProtocolHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ProtocolHelper.id(), "Protocol", new StructMember[]{new StructMember("protocol_type", ORB.init().create_alias_tc(ProfileIdHelper.id(), "ProfileId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("orb_protocol_properties", ORB.init().create_local_interface_tc("IDL:omg.org/RTCORBA/ProtocolProperties:1.0", "ProtocolProperties"), null), new StructMember("transport_protocol_properties", ORB.init().create_local_interface_tc("IDL:omg.org/RTCORBA/ProtocolProperties:1.0", "ProtocolProperties"), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Protocol s2) {
        any.type(ProtocolHelper.type());
        ProtocolHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Protocol extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            Protocol protocol = ProtocolHelper.read(in);
            return protocol;
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
        return "IDL:omg.org/RTCORBA/Protocol:1.0";
    }

    public static Protocol read(InputStream in) {
        Protocol result = new Protocol();
        result.protocol_type = in.read_ulong();
        result.orb_protocol_properties = ProtocolPropertiesHelper.read(in);
        result.transport_protocol_properties = ProtocolPropertiesHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, Protocol s2) {
        out.write_ulong(s2.protocol_type);
        ProtocolPropertiesHelper.write(out, s2.orb_protocol_properties);
        ProtocolPropertiesHelper.write(out, s2.transport_protocol_properties);
    }
}

