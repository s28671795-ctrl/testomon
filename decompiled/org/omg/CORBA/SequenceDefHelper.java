/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SequenceDef;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._SequenceDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class SequenceDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SequenceDefHelper> clazz = SequenceDefHelper.class;
        synchronized (SequenceDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/SequenceDef:1.0", "SequenceDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SequenceDef s2) {
        any.insert_Object(s2);
    }

    public static SequenceDef extract(Any any) {
        return SequenceDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/SequenceDef:1.0";
    }

    public static SequenceDef read(InputStream in) {
        return SequenceDefHelper.narrow(in.read_Object(_SequenceDefStub.class));
    }

    public static void write(OutputStream _out, SequenceDef s2) {
        _out.write_Object(s2);
    }

    public static SequenceDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof SequenceDef) {
            return (SequenceDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/SequenceDef:1.0")) {
            _SequenceDefStub stub = new _SequenceDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static SequenceDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof SequenceDef) {
            return (SequenceDef)obj;
        }
        _SequenceDefStub stub = new _SequenceDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

