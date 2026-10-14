/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Repository;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._RepositoryStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class RepositoryHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RepositoryHelper> clazz = RepositoryHelper.class;
        synchronized (RepositoryHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/Repository:1.0", "Repository");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Repository s2) {
        any.insert_Object(s2);
    }

    public static Repository extract(Any any) {
        return RepositoryHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/Repository:1.0";
    }

    public static Repository read(InputStream in) {
        return RepositoryHelper.narrow(in.read_Object(_RepositoryStub.class));
    }

    public static void write(OutputStream _out, Repository s2) {
        _out.write_Object(s2);
    }

    public static Repository narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Repository) {
            return (Repository)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/Repository:1.0")) {
            _RepositoryStub stub = new _RepositoryStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static Repository unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Repository) {
            return (Repository)obj;
        }
        _RepositoryStub stub = new _RepositoryStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

