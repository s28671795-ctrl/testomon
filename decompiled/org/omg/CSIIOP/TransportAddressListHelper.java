/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

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
import org.omg.CSIIOP.TransportAddress;
import org.omg.CSIIOP.TransportAddressHelper;

public abstract class TransportAddressListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, TransportAddress[] s2) {
        any.type(TransportAddressListHelper.type());
        TransportAddressListHelper.write(any.create_output_stream(), s2);
    }

    public static TransportAddress[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return TransportAddressListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TransportAddressListHelper> clazz = TransportAddressListHelper.class;
        synchronized (TransportAddressListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(TransportAddressListHelper.id(), "TransportAddressList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(TransportAddressHelper.id(), "TransportAddress", new StructMember[]{new StructMember("host_name", ORB.init().create_string_tc(0), null), new StructMember("port", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CSIIOP/TransportAddressList:1.0";
    }

    public static TransportAddress[] read(InputStream _in) {
        int _l_result3 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result3 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result3);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        TransportAddress[] _result = new TransportAddress[_l_result3];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = TransportAddressHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, TransportAddress[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            TransportAddressHelper.write(_out, _s[i]);
        }
    }
}

