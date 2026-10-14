/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

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
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingHelper;
import org.omg.CosNaming.BindingTypeHelper;
import org.omg.CosNaming.IstringHelper;
import org.omg.CosNaming.NameComponentHelper;
import org.omg.CosNaming.NameHelper;

public abstract class BindingListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Binding[] s2) {
        any.type(BindingListHelper.type());
        BindingListHelper.write(any.create_output_stream(), s2);
    }

    public static Binding[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return BindingListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BindingListHelper> clazz = BindingListHelper.class;
        synchronized (BindingListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(BindingListHelper.id(), "BindingList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(BindingHelper.id(), "Binding", new StructMember[]{new StructMember("binding_name", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)}))), null), new StructMember("binding_type", ORB.init().create_enum_tc(BindingTypeHelper.id(), "BindingType", new String[]{"nobject", "ncontext"}), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CosNaming/BindingList:1.0";
    }

    public static Binding[] read(InputStream _in) {
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
        Binding[] _result = new Binding[_l_result1];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = BindingHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, Binding[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            BindingHelper.write(_out, _s[i]);
        }
    }
}

