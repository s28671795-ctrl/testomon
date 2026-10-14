/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._IDLTypeStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class IDLTypeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IDLTypeHelper> clazz = IDLTypeHelper.class;
        synchronized (IDLTypeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IDLType s2) {
        any.insert_Object(s2);
    }

    public static IDLType extract(Any any) {
        return IDLTypeHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/IDLType:1.0";
    }

    public static IDLType read(InputStream in) {
        return IDLTypeHelper.narrow(in.read_Object(_IDLTypeStub.class));
    }

    public static void write(OutputStream _out, IDLType s2) {
        _out.write_Object(s2);
    }

    public static IDLType narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IDLType) {
            return (IDLType)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/IDLType:1.0")) {
            _IDLTypeStub stub = new _IDLTypeStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static IDLType unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof IDLType) {
            return (IDLType)obj;
        }
        _IDLTypeStub stub = new _IDLTypeStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

