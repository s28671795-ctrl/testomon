/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.Initializer;
import org.omg.CORBA.InitializerHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.StructMemberHelper;
import org.omg.CORBA.StructMemberSeqHelper;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class InitializerSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Initializer[] s2) {
        any.type(InitializerSeqHelper.type());
        InitializerSeqHelper.write(any.create_output_stream(), s2);
    }

    public static Initializer[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return InitializerSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InitializerSeqHelper> clazz = InitializerSeqHelper.class;
        synchronized (InitializerSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(InitializerSeqHelper.id(), "InitializerSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(InitializerHelper.id(), "Initializer", new StructMember[]{new StructMember("members", ORB.init().create_alias_tc(StructMemberSeqHelper.id(), "StructMemberSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(StructMemberHelper.id(), "StructMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null)}))), null), new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/InitializerSeq:1.0";
    }

    public static Initializer[] read(InputStream _in) {
        int _l_result14 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result14 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result14);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        Initializer[] _result = new Initializer[_l_result14];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = InitializerHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, Initializer[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            InitializerHelper.write(_out, _s[i]);
        }
    }
}

