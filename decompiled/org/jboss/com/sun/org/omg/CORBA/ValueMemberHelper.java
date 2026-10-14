/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.IDLTypeHelper;
import org.jboss.com.sun.org.omg.CORBA.IdentifierHelper;
import org.jboss.com.sun.org.omg.CORBA.RepositoryIdHelper;
import org.jboss.com.sun.org.omg.CORBA.VersionSpecHelper;
import org.jboss.com.sun.org.omg.CORBA.VisibilityHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class ValueMemberHelper {
    private static String _id = "IDL:omg.org/CORBA/ValueMember:1.0";
    private static TypeCode __typeCode = null;
    private static boolean __active = false;

    public static void insert(Any a, ValueMember that) {
        OutputStream out = a.create_output_stream();
        a.type(ValueMemberHelper.type());
        ValueMemberHelper.write(out, that);
        a.read_value(out.create_input_stream(), ValueMemberHelper.type());
    }

    public static ValueMember extract(Any a) {
        return ValueMemberHelper.read(a.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static synchronized TypeCode type() {
        if (__typeCode != null) return __typeCode;
        Class<TypeCode> clazz = TypeCode.class;
        synchronized (TypeCode.class) {
            if (__typeCode != null) return __typeCode;
            if (__active) {
                // ** MonitorExit[var0] (shouldn't be in output)
                return ORB.init().create_recursive_tc(_id);
            }
            __active = true;
            StructMember[] _members0 = new StructMember[7];
            TypeCode _tcOf_members0 = null;
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", _tcOf_members0);
            _members0[0] = new StructMember("name", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", _tcOf_members0);
            _members0[1] = new StructMember("id", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", _tcOf_members0);
            _members0[2] = new StructMember("defined_in", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", _tcOf_members0);
            _members0[3] = new StructMember("version", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
            _members0[4] = new StructMember("type", _tcOf_members0, null);
            _tcOf_members0 = IDLTypeHelper.type();
            _members0[5] = new StructMember("type_def", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_short);
            _tcOf_members0 = ORB.init().create_alias_tc(VisibilityHelper.id(), "Visibility", _tcOf_members0);
            _members0[6] = new StructMember("access", _tcOf_members0, null);
            __typeCode = ORB.init().create_struct_tc(ValueMemberHelper.id(), "ValueMember", _members0);
            __active = false;
            // ** MonitorExit[var0] (shouldn't be in output)
            return __typeCode;
        }
    }

    public static String id() {
        return _id;
    }

    public static ValueMember read(InputStream istream) {
        ValueMember value = new ValueMember();
        value.name = istream.read_string();
        value.id = istream.read_string();
        value.defined_in = istream.read_string();
        value.version = istream.read_string();
        value.type = istream.read_TypeCode();
        value.type_def = IDLTypeHelper.read(istream);
        value.access = istream.read_short();
        return value;
    }

    public static void write(OutputStream ostream, ValueMember value) {
        ostream.write_string(value.name);
        ostream.write_string(value.id);
        ostream.write_string(value.defined_in);
        ostream.write_string(value.version);
        ostream.write_TypeCode(value.type);
        IDLTypeHelper.write(ostream, value.type_def);
        ostream.write_short(value.access);
    }
}

