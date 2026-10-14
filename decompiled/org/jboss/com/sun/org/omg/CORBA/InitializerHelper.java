/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.IdentifierHelper;
import org.jboss.com.sun.org.omg.CORBA.Initializer;
import org.jboss.com.sun.org.omg.CORBA.StructMemberHelper;
import org.jboss.com.sun.org.omg.CORBA.StructMemberSeqHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class InitializerHelper {
    private static String _id = "IDL:omg.org/CORBA/Initializer:1.0";
    private static TypeCode __typeCode = null;
    private static boolean __active = false;

    public static void insert(Any a, Initializer that) {
        OutputStream out = a.create_output_stream();
        a.type(InitializerHelper.type());
        InitializerHelper.write(out, that);
        a.read_value(out.create_input_stream(), InitializerHelper.type());
    }

    public static Initializer extract(Any a) {
        return InitializerHelper.read(a.create_input_stream());
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
            StructMember[] _members0 = new StructMember[2];
            TypeCode _tcOf_members0 = null;
            _tcOf_members0 = StructMemberHelper.type();
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(StructMemberSeqHelper.id(), "StructMemberSeq", _tcOf_members0);
            _members0[0] = new StructMember("members", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", _tcOf_members0);
            _members0[1] = new StructMember("name", _tcOf_members0, null);
            __typeCode = ORB.init().create_struct_tc(InitializerHelper.id(), "Initializer", _members0);
            __active = false;
            // ** MonitorExit[var0] (shouldn't be in output)
            return __typeCode;
        }
    }

    public static String id() {
        return _id;
    }

    public static Initializer read(InputStream istream) {
        Initializer value = new Initializer();
        value.members = StructMemberSeqHelper.read(istream);
        value.name = istream.read_string();
        return value;
    }

    public static void write(OutputStream ostream, Initializer value) {
        StructMemberSeqHelper.write(ostream, value.members);
        ostream.write_string(value.name);
    }
}

