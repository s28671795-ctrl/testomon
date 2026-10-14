/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.EnumDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._EnumDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class EnumDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<EnumDefHelper> clazz = EnumDefHelper.class;
        synchronized (EnumDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/EnumDef:1.0", "EnumDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, EnumDef s2) {
        any.insert_Object(s2);
    }

    public static EnumDef extract(Any any) {
        return EnumDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/EnumDef:1.0";
    }

    public static EnumDef read(InputStream in) {
        return EnumDefHelper.narrow(in.read_Object(_EnumDefStub.class));
    }

    public static void write(OutputStream _out, EnumDef s2) {
        _out.write_Object(s2);
    }

    public static EnumDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof EnumDef) {
            return (EnumDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/EnumDef:1.0")) {
            _EnumDefStub stub = new _EnumDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static EnumDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof EnumDef) {
            return (EnumDef)obj;
        }
        _EnumDefStub stub = new _EnumDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

