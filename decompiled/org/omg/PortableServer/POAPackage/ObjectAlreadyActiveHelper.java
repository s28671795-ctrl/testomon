/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;

public abstract class ObjectAlreadyActiveHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ObjectAlreadyActiveHelper> clazz = ObjectAlreadyActiveHelper.class;
        synchronized (ObjectAlreadyActiveHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(ObjectAlreadyActiveHelper.id(), "ObjectAlreadyActive", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ObjectAlreadyActive s2) {
        any.type(ObjectAlreadyActiveHelper.type());
        ObjectAlreadyActiveHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ObjectAlreadyActive extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ObjectAlreadyActive objectAlreadyActive = ObjectAlreadyActiveHelper.read(in);
            return objectAlreadyActive;
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
        return "IDL:omg.org/PortableServer/POA/ObjectAlreadyActive:1.0";
    }

    public static ObjectAlreadyActive read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(ObjectAlreadyActiveHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        ObjectAlreadyActive result = new ObjectAlreadyActive(id);
        return result;
    }

    public static void write(OutputStream out, ObjectAlreadyActive s2) {
        out.write_string(ObjectAlreadyActiveHelper.id());
    }
}

