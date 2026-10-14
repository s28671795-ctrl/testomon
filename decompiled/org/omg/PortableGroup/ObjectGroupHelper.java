/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ObjectGroupHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Object s2) {
        any.type(ObjectGroupHelper.type());
        ObjectGroupHelper.write(any.create_output_stream(), s2);
    }

    public static Object extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ObjectGroupHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ObjectGroupHelper> clazz = ObjectGroupHelper.class;
        synchronized (ObjectGroupHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ObjectGroupHelper.id(), "ObjectGroup", ORB.init().get_primitive_tc(TCKind.tk_objref));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/ObjectGroup:1.0";
    }

    public static Object read(InputStream _in) {
        Object _result = _in.read_Object();
        return _result;
    }

    public static void write(OutputStream _out, Object _s) {
        _out.write_Object(_s);
    }
}

