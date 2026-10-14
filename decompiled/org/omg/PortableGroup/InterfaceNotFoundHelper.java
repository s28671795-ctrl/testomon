/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableGroup.InterfaceNotFound;

public abstract class InterfaceNotFoundHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InterfaceNotFoundHelper> clazz = InterfaceNotFoundHelper.class;
        synchronized (InterfaceNotFoundHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InterfaceNotFoundHelper.id(), "InterfaceNotFound", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InterfaceNotFound s2) {
        any.type(InterfaceNotFoundHelper.type());
        InterfaceNotFoundHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InterfaceNotFound extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InterfaceNotFound interfaceNotFound = InterfaceNotFoundHelper.read(in);
            return interfaceNotFound;
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
        return "IDL:omg.org/PortableGroup/InterfaceNotFound:1.0";
    }

    public static InterfaceNotFound read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InterfaceNotFoundHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        InterfaceNotFound result = new InterfaceNotFound(id);
        return result;
    }

    public static void write(OutputStream out, InterfaceNotFound s2) {
        out.write_string(InterfaceNotFoundHelper.id());
    }
}

