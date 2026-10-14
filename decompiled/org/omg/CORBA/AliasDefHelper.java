/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AliasDef;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._AliasDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class AliasDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AliasDefHelper> clazz = AliasDefHelper.class;
        synchronized (AliasDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/AliasDef:1.0", "AliasDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AliasDef s2) {
        any.insert_Object(s2);
    }

    public static AliasDef extract(Any any) {
        return AliasDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/AliasDef:1.0";
    }

    public static AliasDef read(InputStream in) {
        return AliasDefHelper.narrow(in.read_Object(_AliasDefStub.class));
    }

    public static void write(OutputStream _out, AliasDef s2) {
        _out.write_Object(s2);
    }

    public static AliasDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AliasDef) {
            return (AliasDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/AliasDef:1.0")) {
            _AliasDefStub stub = new _AliasDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static AliasDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AliasDef) {
            return (AliasDef)obj;
        }
        _AliasDefStub stub = new _AliasDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

