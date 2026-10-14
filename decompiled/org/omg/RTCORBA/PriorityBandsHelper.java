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
import org.omg.RTCORBA.PriorityBand;
import org.omg.RTCORBA.PriorityBandHelper;
import org.omg.RTCORBA.PriorityHelper;

public abstract class PriorityBandsHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, PriorityBand[] s2) {
        any.type(PriorityBandsHelper.type());
        PriorityBandsHelper.write(any.create_output_stream(), s2);
    }

    public static PriorityBand[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return PriorityBandsHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PriorityBandsHelper> clazz = PriorityBandsHelper.class;
        synchronized (PriorityBandsHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(PriorityBandsHelper.id(), "PriorityBands", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(PriorityBandHelper.id(), "PriorityBand", new StructMember[]{new StructMember("low", ORB.init().create_alias_tc(PriorityHelper.id(), "Priority", ORB.init().get_primitive_tc(TCKind.from_int(2))), null), new StructMember("high", ORB.init().create_alias_tc(PriorityHelper.id(), "Priority", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/RTCORBA/PriorityBands:1.0";
    }

    public static PriorityBand[] read(InputStream _in) {
        int _l_result2 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result2 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result2);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        PriorityBand[] _result = new PriorityBand[_l_result2];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = PriorityBandHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, PriorityBand[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            PriorityBandHelper.write(_out, _s[i]);
        }
    }
}

