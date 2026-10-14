/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.VisibilityHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ValueMemberHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ValueMemberHelper> clazz = ValueMemberHelper.class;
        synchronized (ValueMemberHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ValueMemberHelper.id(), "ValueMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null), new StructMember("access", ORB.init().create_alias_tc(VisibilityHelper.id(), "Visibility", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ValueMember s2) {
        any.type(ValueMemberHelper.type());
        ValueMemberHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ValueMember extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ValueMember valueMember = ValueMemberHelper.read(in);
            return valueMember;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Unexpected exception " + e.toString());
            }
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ValueMember:1.0";
    }

    public static ValueMember read(InputStream in) {
        ValueMember result = new ValueMember();
        result.name = in.read_string();
        result.id = in.read_string();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        result.type = in.read_TypeCode();
        result.type_def = IDLTypeHelper.read(in);
        result.access = in.read_short();
        return result;
    }

    public static void write(OutputStream out, ValueMember s2) {
        String tmpResult561 = s2.name;
        out.write_string(tmpResult561);
        String tmpResult562 = s2.id;
        out.write_string(tmpResult562);
        String tmpResult563 = s2.defined_in;
        out.write_string(tmpResult563);
        String tmpResult564 = s2.version;
        out.write_string(tmpResult564);
        out.write_TypeCode(s2.type);
        IDLTypeHelper.write(out, s2.type_def);
        out.write_short(s2.access);
    }
}

