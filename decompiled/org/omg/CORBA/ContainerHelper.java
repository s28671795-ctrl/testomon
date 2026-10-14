/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.Container;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._ContainerStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class ContainerHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ContainerHelper> clazz = ContainerHelper.class;
        synchronized (ContainerHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/Container:1.0", "Container");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Container s2) {
        any.insert_Object(s2);
    }

    public static Container extract(Any any) {
        return ContainerHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/Container:1.0";
    }

    public static Container read(InputStream in) {
        return ContainerHelper.narrow(in.read_Object(_ContainerStub.class));
    }

    public static void write(OutputStream _out, Container s2) {
        _out.write_Object(s2);
    }

    public static Container narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Container) {
            return (Container)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/Container:1.0")) {
            _ContainerStub stub = new _ContainerStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static Container unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Container) {
            return (Container)obj;
        }
        _ContainerStub stub = new _ContainerStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

