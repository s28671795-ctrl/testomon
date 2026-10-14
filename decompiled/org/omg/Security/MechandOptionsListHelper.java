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
import org.omg.Security.AssociationOptionsHelper;
import org.omg.Security.MechandOptions;
import org.omg.Security.MechandOptionsHelper;
import org.omg.Security.MechanismTypeHelper;

public abstract class MechandOptionsListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, MechandOptions[] s2) {
        any.type(MechandOptionsListHelper.type());
        MechandOptionsListHelper.write(any.create_output_stream(), s2);
    }

    public static MechandOptions[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return MechandOptionsListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MechandOptionsListHelper> clazz = MechandOptionsListHelper.class;
        synchronized (MechandOptionsListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(MechandOptionsListHelper.id(), "MechandOptionsList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(MechandOptionsHelper.id(), "MechandOptions", new StructMember[]{new StructMember("mechanism_type", ORB.init().create_alias_tc(MechanismTypeHelper.id(), "MechanismType", ORB.init().create_string_tc(0)), null), new StructMember("options_supported", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Security/MechandOptionsList:1.0";
    }

    public static MechandOptions[] read(InputStream _in) {
        int _l_result8 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result8 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result8);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        MechandOptions[] _result = new MechandOptions[_l_result8];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = MechandOptionsHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, MechandOptions[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            MechandOptionsHelper.write(_out, _s[i]);
        }
    }
}

