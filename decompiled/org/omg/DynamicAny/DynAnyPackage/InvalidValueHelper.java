/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny.DynAnyPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;

public abstract class InvalidValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidValueHelper> clazz = InvalidValueHelper.class;
        synchronized (InvalidValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidValueHelper.id(), "InvalidValue", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidValue s2) {
        any.type(InvalidValueHelper.type());
        InvalidValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidValue invalidValue = InvalidValueHelper.read(in);
            return invalidValue;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Unexpected exception " + e.toString());
            }
        }
    }

    public static String id() {
        return "IDL:omg.org/DynamicAny/DynAny/InvalidValue:1.0";
    }

    public static InvalidValue read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidValueHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        InvalidValue result = new InvalidValue(id);
        return result;
    }

    public static void write(OutputStream out, InvalidValue s2) {
        out.write_string(InvalidValueHelper.id());
    }
}

