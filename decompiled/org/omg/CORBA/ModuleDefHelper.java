/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ModuleDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ModuleDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ModuleDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ModuleDefHelper> clazz = ModuleDefHelper.class;
        synchronized (ModuleDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/ModuleDef:1.0", "ModuleDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ModuleDef s2) {
        any.insert_Object(s2);
    }

    public static ModuleDef extract(Any any) {
        return ModuleDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ModuleDef:1.0";
    }

    public static ModuleDef read(InputStream in) {
        return ModuleDefHelper.narrow(in.read_Object(_ModuleDefStub.class));
    }

    public static void write(OutputStream _out, ModuleDef s2) {
        _out.write_Object(s2);
    }

    public static ModuleDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ModuleDef) {
            return (ModuleDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/ModuleDef:1.0")) {
            _ModuleDefStub stub = new _ModuleDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ModuleDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ModuleDef) {
            return (ModuleDef)obj;
        }
        _ModuleDefStub stub = new _ModuleDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

