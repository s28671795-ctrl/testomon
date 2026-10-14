/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ExtAttributeDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ExtAttributeDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExtAttributeDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExtAttributeDefHelper> clazz = ExtAttributeDefHelper.class;
        synchronized (ExtAttributeDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ExtAttributeDef:1.0", "ExtAttributeDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ExtAttributeDef s2) {
        any.insert_Object(s2);
    }

    public static ExtAttributeDef extract(Any any) {
        return ExtAttributeDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ExtAttributeDef:1.0";
    }

    public static ExtAttributeDef read(InputStream in) {
        return ExtAttributeDefHelper.narrow(in.read_Object(_ExtAttributeDefStub.class));
    }

    public static void write(OutputStream _out, ExtAttributeDef s2) {
        _out.write_Object(s2);
    }

    public static ExtAttributeDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtAttributeDef) {
            return (ExtAttributeDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ExtAttributeDef:1.0")) {
            _ExtAttributeDefStub stub = new _ExtAttributeDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ExtAttributeDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ExtAttributeDef) {
            return (ExtAttributeDef)obj;
        }
        _ExtAttributeDefStub stub = new _ExtAttributeDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

