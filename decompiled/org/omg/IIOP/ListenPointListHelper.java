/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

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
import org.omg.IIOP.ListenPoint;
import org.omg.IIOP.ListenPointHelper;

public abstract class ListenPointListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ListenPoint[] s2) {
        any.type(ListenPointListHelper.type());
        ListenPointListHelper.write(any.create_output_stream(), s2);
    }

    public static ListenPoint[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ListenPointListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ListenPointListHelper> clazz = ListenPointListHelper.class;
        synchronized (ListenPointListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ListenPointListHelper.id(), "ListenPointList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ListenPointHelper.id(), "ListenPoint", new StructMember[]{new StructMember("host", ORB.init().create_string_tc(0), null), new StructMember("port", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/IIOP/ListenPointList:1.0";
    }

    public static ListenPoint[] read(InputStream _in) {
        int _l_result0 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result0 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        ListenPoint[] _result = new ListenPoint[_l_result0];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = ListenPointHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, ListenPoint[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ListenPointHelper.write(_out, _s[i]);
        }
    }
}

