/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.IDLTypeHelper;
import org.jboss.com.sun.org.omg.CORBA.IdentifierHelper;
import org.jboss.com.sun.org.omg.CORBA.ParameterDescription;
import org.jboss.com.sun.org.omg.CORBA.ParameterModeHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class ParameterDescriptionHelper {
    private static String _id = "IDL:omg.org/CORBA/ParameterDescription:1.0";
    private static TypeCode __typeCode = null;
    private static boolean __active = false;

    public static void insert(Any a, ParameterDescription that) {
        OutputStream out = a.create_output_stream();
        a.type(ParameterDescriptionHelper.type());
        ParameterDescriptionHelper.write(out, that);
        a.read_value(out.create_input_stream(), ParameterDescriptionHelper.type());
    }

    public static ParameterDescription extract(Any a) {
        return ParameterDescriptionHelper.read(a.create_input_stream());
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
            StructMember[] _members0 = new StructMember[4];
            TypeCode _tcOf_members0 = null;
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", _tcOf_members0);
            _members0[0] = new StructMember("name", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
            _members0[1] = new StructMember("type", _tcOf_members0, null);
            _tcOf_members0 = IDLTypeHelper.type();
            _members0[2] = new StructMember("type_def", _tcOf_members0, null);
            _tcOf_members0 = ParameterModeHelper.type();
            _members0[3] = new StructMember("mode", _tcOf_members0, null);
            __typeCode = ORB.init().create_struct_tc(ParameterDescriptionHelper.id(), "ParameterDescription", _members0);
            __active = false;
            // ** MonitorExit[var0] (shouldn't be in output)
            return __typeCode;
        }
    }

    public static String id() {
        return _id;
    }

    public static ParameterDescription read(InputStream istream) {
        ParameterDescription value = new ParameterDescription();
        value.name = istream.read_string();
        value.type = istream.read_TypeCode();
        value.type_def = IDLTypeHelper.read(istream);
        value.mode = ParameterModeHelper.read(istream);
        return value;
    }

    public static void write(OutputStream ostream, ParameterDescription value) {
        ostream.write_string(value.name);
        ostream.write_TypeCode(value.type);
        IDLTypeHelper.write(ostream, value.type_def);
        ParameterModeHelper.write(ostream, value.mode);
    }
}

