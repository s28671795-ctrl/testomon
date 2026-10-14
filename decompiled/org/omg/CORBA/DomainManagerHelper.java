/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._DomainManagerStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class DomainManagerHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DomainManagerHelper> clazz = DomainManagerHelper.class;
        synchronized (DomainManagerHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/DomainManager:1.0", "DomainManager");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DomainManager s2) {
        any.insert_Object(s2);
    }

    public static DomainManager extract(Any any) {
        return DomainManagerHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/DomainManager:1.0";
    }

    public static DomainManager read(InputStream in) {
        return DomainManagerHelper.narrow(in.read_Object(_DomainManagerStub.class));
    }

    public static void write(OutputStream _out, DomainManager s2) {
        _out.write_Object(s2);
    }

    public static DomainManager narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DomainManager) {
            return (DomainManager)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/DomainManager:1.0")) {
            _DomainManagerStub stub = new _DomainManagerStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static DomainManager unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DomainManager) {
            return (DomainManager)obj;
        }
        _DomainManagerStub stub = new _DomainManagerStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

