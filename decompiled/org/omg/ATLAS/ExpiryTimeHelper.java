/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

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
import org.omg.TimeBase.TdfTHelper;
import org.omg.TimeBase.TimeTHelper;
import org.omg.TimeBase.UtcT;
import org.omg.TimeBase.UtcTHelper;

public abstract class ExpiryTimeHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, UtcT[] s2) {
        any.type(ExpiryTimeHelper.type());
        ExpiryTimeHelper.write(any.create_output_stream(), s2);
    }

    public static UtcT[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ExpiryTimeHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExpiryTimeHelper> clazz = ExpiryTimeHelper.class;
        synchronized (ExpiryTimeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ExpiryTimeHelper.id(), "ExpiryTime", ORB.init().create_sequence_tc(1, ORB.init().create_struct_tc(UtcTHelper.id(), "UtcT", new StructMember[]{new StructMember("time", ORB.init().create_alias_tc(TimeTHelper.id(), "TimeT", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("inacclo", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("inacchi", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("tdf", ORB.init().create_alias_tc(TdfTHelper.id(), "TdfT", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/ATLAS/ExpiryTime:1.0";
    }

    public static UtcT[] read(InputStream _in) {
        int _l_result0 = _in.read_long();
        if (_l_result0 > 1) {
            throw new MARSHAL("Sequence length incorrect!");
        }
        try {
            int x = _in.available();
            if (x > 0 && _l_result0 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        UtcT[] _result = new UtcT[_l_result0];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = UtcTHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, UtcT[] _s) {
        if (_s.length > 1) {
            throw new MARSHAL("Incorrect sequence length");
        }
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            UtcTHelper.write(_out, _s[i]);
        }
    }
}

