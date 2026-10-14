/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.CodecFactory;

public abstract class CodecFactoryHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CodecFactoryHelper> clazz = CodecFactoryHelper.class;
        synchronized (CodecFactoryHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_local_interface_tc("IDL:omg.org/IOP/CodecFactory:1.0", "CodecFactory");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CodecFactory s2) {
        any.insert_Object(s2);
    }

    public static CodecFactory extract(Any any) {
        return CodecFactoryHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/IOP/CodecFactory:1.0";
    }

    public static CodecFactory read(InputStream in) {
        throw new MARSHAL();
    }

    public static void write(OutputStream _out, CodecFactory s2) {
        throw new MARSHAL();
    }

    public static CodecFactory narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof CodecFactory) {
            return (CodecFactory)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static CodecFactory unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof CodecFactory) {
            return (CodecFactory)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

