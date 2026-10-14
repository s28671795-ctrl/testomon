/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.AttributeDescription;
import org.jboss.com.sun.org.omg.CORBA.AttributeModeHelper;
import org.jboss.com.sun.org.omg.CORBA.IdentifierHelper;
import org.jboss.com.sun.org.omg.CORBA.RepositoryIdHelper;
import org.jboss.com.sun.org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class AttributeDescriptionHelper {
    private static String _id = "IDL:omg.org/CORBA/AttributeDescription:1.0";
    private static TypeCode __typeCode = null;
    private static boolean __active = false;

    public static void insert(Any a, AttributeDescription that) {
        OutputStream out = a.create_output_stream();
        a.type(AttributeDescriptionHelper.type());
        AttributeDescriptionHelper.write(out, that);
        a.read_value(out.create_input_stream(), AttributeDescriptionHelper.type());
    }

    public static AttributeDescription extract(Any a) {
        return AttributeDescriptionHelper.read(a.create_input_stream());
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
            StructMember[] _members0 = new StructMember[6];
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
            _tcOf_members0 = AttributeModeHelper.type();
            _members0[5] = new StructMember("mode", _tcOf_members0, null);
            __typeCode = ORB.init().create_struct_tc(AttributeDescriptionHelper.id(), "AttributeDescription", _members0);
            __active = false;
            // ** MonitorExit[var0] (shouldn't be in output)
            return __typeCode;
        }
    }

    public static String id() {
        return _id;
    }

    public static AttributeDescription read(InputStream istream) {
        AttributeDescription value = new AttributeDescription();
        value.name = istream.read_string();
        value.id = istream.read_string();
        value.defined_in = istream.read_string();
        value.version = istream.read_string();
        value.type = istream.read_TypeCode();
        value.mode = AttributeModeHelper.read(istream);
        return value;
    }

    public static void write(OutputStream ostream, AttributeDescription value) {
        ostream.write_string(value.name);
        ostream.write_string(value.id);
        ostream.write_string(value.defined_in);
        ostream.write_string(value.version);
        ostream.write_TypeCode(value.type);
        AttributeModeHelper.write(ostream, value.mode);
    }
}

