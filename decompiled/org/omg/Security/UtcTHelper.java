/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.TimeBase.TdfTHelper;
import org.omg.TimeBase.TimeTHelper;
import org.omg.TimeBase.UtcT;

public abstract class UtcTHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, UtcT s2) {
        any.type(UtcTHelper.type());
        UtcTHelper.write(any.create_output_stream(), s2);
    }

    public static UtcT extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return UtcTHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<UtcTHelper> clazz = UtcTHelper.class;
        synchronized (UtcTHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(UtcTHelper.id(), "UtcT", ORB.init().create_struct_tc(org.omg.TimeBase.UtcTHelper.id(), "UtcT", new StructMember[]{new StructMember("time", ORB.init().create_alias_tc(TimeTHelper.id(), "TimeT", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("inacclo", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("inacchi", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("tdf", ORB.init().create_alias_tc(TdfTHelper.id(), "TdfT", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)}));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Security/UtcT:1.0";
    }

    public static UtcT read(InputStream _in) {
        UtcT _result = org.omg.TimeBase.UtcTHelper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, UtcT _s) {
        org.omg.TimeBase.UtcTHelper.write(_out, _s);
    }
}

