/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.Version;

public abstract class VersionHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Version s2) {
        any.type(VersionHelper.type());
        VersionHelper.write(any.create_output_stream(), s2);
    }

    public static Version extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return VersionHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<VersionHelper> clazz = VersionHelper.class;
        synchronized (VersionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(VersionHelper.id(), "Version", ORB.init().create_struct_tc(org.omg.GIOP.VersionHelper.id(), "Version", new StructMember[]{new StructMember("major", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(10)), null)}));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/Version:1.0";
    }

    public static Version read(InputStream _in) {
        Version _result = org.omg.GIOP.VersionHelper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, Version _s) {
        org.omg.GIOP.VersionHelper.write(_out, _s);
    }
}

