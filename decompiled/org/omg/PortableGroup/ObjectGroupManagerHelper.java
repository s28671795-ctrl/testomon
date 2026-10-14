/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableGroup.ObjectGroupManager;
import org.omg.PortableGroup._ObjectGroupManagerStub;

public abstract class ObjectGroupManagerHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ObjectGroupManagerHelper> clazz = ObjectGroupManagerHelper.class;
        synchronized (ObjectGroupManagerHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/PortableGroup/ObjectGroupManager:1.0", "ObjectGroupManager");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ObjectGroupManager s2) {
        any.insert_Object(s2);
    }

    public static ObjectGroupManager extract(Any any) {
        return ObjectGroupManagerHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/ObjectGroupManager:1.0";
    }

    public static ObjectGroupManager read(InputStream in) {
        return ObjectGroupManagerHelper.narrow(in.read_Object(_ObjectGroupManagerStub.class));
    }

    public static void write(OutputStream _out, ObjectGroupManager s2) {
        _out.write_Object(s2);
    }

    public static ObjectGroupManager narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ObjectGroupManager) {
            return (ObjectGroupManager)obj;
        }
        if (obj._is_a("IDL:omg.org/PortableGroup/ObjectGroupManager:1.0")) {
            _ObjectGroupManagerStub stub = new _ObjectGroupManagerStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ObjectGroupManager unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ObjectGroupManager) {
            return (ObjectGroupManager)obj;
        }
        _ObjectGroupManagerStub stub = new _ObjectGroupManagerStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

