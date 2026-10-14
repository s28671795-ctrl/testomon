/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.AuthTokenDispenser;
import org.omg.ATLAS._AuthTokenDispenserStub;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class AuthTokenDispenserHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuthTokenDispenserHelper> clazz = AuthTokenDispenserHelper.class;
        synchronized (AuthTokenDispenserHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/ATLAS/AuthTokenDispenser:1.0", "AuthTokenDispenser");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AuthTokenDispenser s2) {
        any.insert_Object(s2);
    }

    public static AuthTokenDispenser extract(Any any) {
        return AuthTokenDispenserHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/ATLAS/AuthTokenDispenser:1.0";
    }

    public static AuthTokenDispenser read(InputStream in) {
        return AuthTokenDispenserHelper.narrow(in.read_Object(_AuthTokenDispenserStub.class));
    }

    public static void write(OutputStream _out, AuthTokenDispenser s2) {
        _out.write_Object(s2);
    }

    public static AuthTokenDispenser narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AuthTokenDispenser) {
            return (AuthTokenDispenser)obj;
        }
        if (obj._is_a("IDL:omg.org/ATLAS/AuthTokenDispenser:1.0")) {
            _AuthTokenDispenserStub stub = new _AuthTokenDispenserStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static AuthTokenDispenser unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof AuthTokenDispenser) {
            return (AuthTokenDispenser)obj;
        }
        _AuthTokenDispenserStub stub = new _AuthTokenDispenserStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

