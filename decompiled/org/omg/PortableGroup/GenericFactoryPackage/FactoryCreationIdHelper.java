/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup.GenericFactoryPackage;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class FactoryCreationIdHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Any s2) {
        any.type(FactoryCreationIdHelper.type());
        FactoryCreationIdHelper.write(any.create_output_stream(), s2);
    }

    public static Any extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return FactoryCreationIdHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<FactoryCreationIdHelper> clazz = FactoryCreationIdHelper.class;
        synchronized (FactoryCreationIdHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(FactoryCreationIdHelper.id(), "FactoryCreationId", ORB.init().get_primitive_tc(TCKind.from_int(11)));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/GenericFactory/FactoryCreationId:1.0";
    }

    public static Any read(InputStream _in) {
        Any _result = _in.read_any();
        return _result;
    }

    public static void write(OutputStream _out, Any _s) {
        _out.write_any(_s);
    }
}

