/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAManagerFactoryPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerHelper;

public abstract class POAManagerSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, POAManager[] s2) {
        any.type(POAManagerSeqHelper.type());
        POAManagerSeqHelper.write(any.create_output_stream(), s2);
    }

    public static POAManager[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return POAManagerSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<POAManagerSeqHelper> clazz = POAManagerSeqHelper.class;
        synchronized (POAManagerSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(POAManagerSeqHelper.id(), "POAManagerSeq", ORB.init().create_sequence_tc(0, ORB.init().create_local_interface_tc("IDL:omg.org/PortableServer/POAManager:1.0", "POAManager")));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableServer/POAManagerFactory/POAManagerSeq:1.0";
    }

    public static POAManager[] read(InputStream _in) {
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
        POAManager[] _result = new POAManager[_l_result1];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = POAManagerHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, POAManager[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            POAManagerHelper.write(_out, _s[i]);
        }
    }
}

