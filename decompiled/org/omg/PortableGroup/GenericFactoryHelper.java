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
import org.omg.PortableGroup.GenericFactory;
import org.omg.PortableGroup._GenericFactoryStub;

public abstract class GenericFactoryHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<GenericFactoryHelper> clazz = GenericFactoryHelper.class;
        synchronized (GenericFactoryHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/PortableGroup/GenericFactory:1.0", "GenericFactory");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, GenericFactory s2) {
        any.insert_Object(s2);
    }

    public static GenericFactory extract(Any any) {
        return GenericFactoryHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/GenericFactory:1.0";
    }

    public static GenericFactory read(InputStream in) {
        return GenericFactoryHelper.narrow(in.read_Object(_GenericFactoryStub.class));
    }

    public static void write(OutputStream _out, GenericFactory s2) {
        _out.write_Object(s2);
    }

    public static GenericFactory narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof GenericFactory) {
            return (GenericFactory)obj;
        }
        if (obj._is_a("IDL:omg.org/PortableGroup/GenericFactory:1.0")) {
            _GenericFactoryStub stub = new _GenericFactoryStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static GenericFactory unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof GenericFactory) {
            return (GenericFactory)obj;
        }
        _GenericFactoryStub stub = new _GenericFactoryStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

