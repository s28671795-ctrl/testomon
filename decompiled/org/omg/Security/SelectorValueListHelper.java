/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

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
import org.omg.Security.SelectorTypeHelper;
import org.omg.Security.SelectorValue;
import org.omg.Security.SelectorValueHelper;

public abstract class SelectorValueListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, SelectorValue[] s2) {
        any.type(SelectorValueListHelper.type());
        SelectorValueListHelper.write(any.create_output_stream(), s2);
    }

    public static SelectorValue[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return SelectorValueListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SelectorValueListHelper> clazz = SelectorValueListHelper.class;
        synchronized (SelectorValueListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(SelectorValueListHelper.id(), "SelectorValueList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(SelectorValueHelper.id(), "SelectorValue", new StructMember[]{new StructMember("selector", ORB.init().create_alias_tc(SelectorTypeHelper.id(), "SelectorType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("value", ORB.init().get_primitive_tc(TCKind.from_int(11)), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Security/SelectorValueList:1.0";
    }

    public static SelectorValue[] read(InputStream _in) {
        int _l_result10 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result10 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result10);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        SelectorValue[] _result = new SelectorValue[_l_result10];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = SelectorValueHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, SelectorValue[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            SelectorValueHelper.write(_out, _s[i]);
        }
    }
}

