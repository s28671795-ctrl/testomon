/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public abstract class POAListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, POA[] s2) {
        any.type(POAListHelper.type());
        POAListHelper.write(any.create_output_stream(), s2);
    }

    public static POA[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return POAListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<POAListHelper> clazz = POAListHelper.class;
        synchronized (POAListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(POAListHelper.id(), "POAList", ORB.init().create_sequence_tc(0, ORB.init().create_local_interface_tc("IDL:omg.org/PortableServer/POA:1.0", "POA")));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableServer/POAList:1.0";
    }

    public static POA[] read(InputStream _in) {
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
        POA[] _result = new POA[_l_result0];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = POAHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, POA[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            POAHelper.write(_out, _s[i]);
        }
    }
}

