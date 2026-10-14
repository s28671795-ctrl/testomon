/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SendingContext.RunTime;
import org.omg.SendingContext._RunTimeStub;

public abstract class RunTimeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RunTimeHelper> clazz = RunTimeHelper.class;
        synchronized (RunTimeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SendingContext/RunTime:1.0", "RunTime");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RunTime s2) {
        any.insert_Object(s2);
    }

    public static RunTime extract(Any any) {
        return RunTimeHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SendingContext/RunTime:1.0";
    }

    public static RunTime read(InputStream in) {
        return RunTimeHelper.narrow(in.read_Object(_RunTimeStub.class));
    }

    public static void write(OutputStream _out, RunTime s2) {
        _out.write_Object(s2);
    }

    public static RunTime narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RunTime) {
            return (RunTime)obj;
        }
        if (obj._is_a("IDL:omg.org/SendingContext/RunTime:1.0")) {
            _RunTimeStub stub = new _RunTimeStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static RunTime unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof RunTime) {
            return (RunTime)obj;
        }
        _RunTimeStub stub = new _RunTimeStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

