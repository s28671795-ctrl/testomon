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
import org.omg.PortableGroup.PropertyManager;
import org.omg.PortableGroup._PropertyManagerStub;

public abstract class PropertyManagerHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PropertyManagerHelper> clazz = PropertyManagerHelper.class;
        synchronized (PropertyManagerHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/PortableGroup/PropertyManager:1.0", "PropertyManager");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PropertyManager s2) {
        any.insert_Object(s2);
    }

    public static PropertyManager extract(Any any) {
        return PropertyManagerHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/PropertyManager:1.0";
    }

    public static PropertyManager read(InputStream in) {
        return PropertyManagerHelper.narrow(in.read_Object(_PropertyManagerStub.class));
    }

    public static void write(OutputStream _out, PropertyManager s2) {
        _out.write_Object(s2);
    }

    public static PropertyManager narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PropertyManager) {
            return (PropertyManager)obj;
        }
        if (obj._is_a("IDL:omg.org/PortableGroup/PropertyManager:1.0")) {
            _PropertyManagerStub stub = new _PropertyManagerStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static PropertyManager unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PropertyManager) {
            return (PropertyManager)obj;
        }
        _PropertyManagerStub stub = new _PropertyManagerStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

