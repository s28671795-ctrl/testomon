/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.AttributeDef;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._AttributeDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class AttributeDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AttributeDefHelper> clazz = AttributeDefHelper.class;
        synchronized (AttributeDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/AttributeDef:1.0", "AttributeDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AttributeDef s2) {
        any.insert_Object(s2);
    }

    public static AttributeDef extract(Any any) {
        return AttributeDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/AttributeDef:1.0";
    }

    public static AttributeDef read(InputStream in) {
        return AttributeDefHelper.narrow(in.read_Object(_AttributeDefStub.class));
    }

    public static void write(OutputStream _out, AttributeDef s2) {
        _out.write_Object(s2);
    }

    public static AttributeDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AttributeDef) {
            return (AttributeDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/AttributeDef:1.0")) {
            _AttributeDefStub stub = new _AttributeDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static AttributeDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AttributeDef) {
            return (AttributeDef)obj;
        }
        _AttributeDefStub stub = new _AttributeDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

