/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.ProfileIdHelper;
import org.omg.RTCORBA.Protocol;
import org.omg.RTCORBA.ProtocolHelper;

public abstract class ProtocolListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Protocol[] s2) {
        any.type(ProtocolListHelper.type());
        ProtocolListHelper.write(any.create_output_stream(), s2);
    }

    public static Protocol[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ProtocolListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ProtocolListHelper> clazz = ProtocolListHelper.class;
        synchronized (ProtocolListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ProtocolListHelper.id(), "ProtocolList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ProtocolHelper.id(), "Protocol", new StructMember[]{new StructMember("protocol_type", ORB.init().create_alias_tc(ProfileIdHelper.id(), "ProfileId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("orb_protocol_properties", ORB.init().create_local_interface_tc("IDL:omg.org/RTCORBA/ProtocolProperties:1.0", "ProtocolProperties"), null), new StructMember("transport_protocol_properties", ORB.init().create_local_interface_tc("IDL:omg.org/RTCORBA/ProtocolProperties:1.0", "ProtocolProperties"), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/RTCORBA/ProtocolList:1.0";
    }

    public static Protocol[] read(InputStream _in) {
        int _l_result1 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result1 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result1);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        Protocol[] _result = new Protocol[_l_result1];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = ProtocolHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, Protocol[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ProtocolHelper.write(_out, _s[i]);
        }
    }
}

