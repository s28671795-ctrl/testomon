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
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.ValueMemberHelper;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.VisibilityHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ValueMemberSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ValueMember[] s2) {
        any.type(ValueMemberSeqHelper.type());
        ValueMemberSeqHelper.write(any.create_output_stream(), s2);
    }

    public static ValueMember[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ValueMemberSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ValueMemberSeqHelper> clazz = ValueMemberSeqHelper.class;
        synchronized (ValueMemberSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ValueMemberSeqHelper.id(), "ValueMemberSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ValueMemberHelper.id(), "ValueMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null), new StructMember("access", ORB.init().create_alias_tc(VisibilityHelper.id(), "Visibility", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ValueMemberSeq:1.0";
    }

    public static ValueMember[] read(InputStream _in) {
        int _l_result27 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result27 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result27);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        ValueMember[] _result = new ValueMember[_l_result27];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = ValueMemberHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, ValueMember[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ValueMemberHelper.write(_out, _s[i]);
        }
    }
}

