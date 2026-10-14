/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.UnionMemberHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class UnionMemberSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, UnionMember[] s2) {
        any.type(UnionMemberSeqHelper.type());
        UnionMemberSeqHelper.write(any.create_output_stream(), s2);
    }

    public static UnionMember[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return UnionMemberSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<UnionMemberSeqHelper> clazz = UnionMemberSeqHelper.class;
        synchronized (UnionMemberSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(UnionMemberSeqHelper.id(), "UnionMemberSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(UnionMemberHelper.id(), "UnionMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("label", ORB.init().get_primitive_tc(TCKind.from_int(11)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/UnionMemberSeq:1.0";
    }

    public static UnionMember[] read(InputStream _in) {
        int _l_result17 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result17 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result17);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        UnionMember[] _result = new UnionMember[_l_result17];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = UnionMemberHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, UnionMember[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            UnionMemberHelper.write(_out, _s[i]);
        }
    }
}

