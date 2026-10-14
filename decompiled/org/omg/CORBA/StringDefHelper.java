/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StringDef;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._StringDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class StringDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<StringDefHelper> clazz = StringDefHelper.class;
        synchronized (StringDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/StringDef:1.0", "StringDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, StringDef s2) {
        any.insert_Object(s2);
    }

    public static StringDef extract(Any any) {
        return StringDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/StringDef:1.0";
    }

    public static StringDef read(InputStream in) {
        return StringDefHelper.narrow(in.read_Object(_StringDefStub.class));
    }

    public static void write(OutputStream _out, StringDef s2) {
        _out.write_Object(s2);
    }

    public static StringDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof StringDef) {
            return (StringDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/StringDef:1.0")) {
            _StringDefStub stub = new _StringDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static StringDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof StringDef) {
            return (StringDef)obj;
        }
        _StringDefStub stub = new _StringDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

