/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class MembershipStyleValueHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, int s2) {
        any.insert_long(s2);
    }

    public static int extract(Any any) {
        return any.extract_long();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MembershipStyleValueHelper> clazz = MembershipStyleValueHelper.class;
        synchronized (MembershipStyleValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(MembershipStyleValueHelper.id(), "MembershipStyleValue", ORB.init().get_primitive_tc(TCKind.from_int(3)));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/MembershipStyleValue:1.0";
    }

    public static int read(InputStream _in) {
        int _result = _in.read_long();
        return _result;
    }

    public static void write(OutputStream _out, int _s) {
        _out.write_long(_s);
    }
}

