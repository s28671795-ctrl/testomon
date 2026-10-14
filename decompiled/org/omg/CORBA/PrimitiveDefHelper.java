/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.PrimitiveDef;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA._PrimitiveDefStub;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

public abstract class PrimitiveDefHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PrimitiveDefHelper> clazz = PrimitiveDefHelper.class;
        synchronized (PrimitiveDefHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/CORBA/PrimitiveDef:1.0", "PrimitiveDef");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PrimitiveDef s2) {
        any.insert_Object(s2);
    }

    public static PrimitiveDef extract(Any any) {
        return PrimitiveDefHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/PrimitiveDef:1.0";
    }

    public static PrimitiveDef read(InputStream in) {
        return PrimitiveDefHelper.narrow(in.read_Object(_PrimitiveDefStub.class));
    }

    public static void write(OutputStream _out, PrimitiveDef s2) {
        _out.write_Object(s2);
    }

    public static PrimitiveDef narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PrimitiveDef) {
            return (PrimitiveDef)obj;
        }
        if (obj._is_a("IDL:omg.org/CORBA/PrimitiveDef:1.0")) {
            _PrimitiveDefStub stub = new _PrimitiveDefStub();
            stub._set_delegate(((ObjectImpl)obj)._get_delegate());
            return stub;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static PrimitiveDef unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof PrimitiveDef) {
            return (PrimitiveDef)obj;
        }
        _PrimitiveDefStub stub = new _PrimitiveDefStub();
        stub._set_delegate(((ObjectImpl)obj)._get_delegate());
        return stub;
    }
}

