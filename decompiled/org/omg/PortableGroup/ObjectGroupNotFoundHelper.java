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
import org.omg.PortableGroup.ObjectGroupNotFound;

public abstract class ObjectGroupNotFoundHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ObjectGroupNotFoundHelper> clazz = ObjectGroupNotFoundHelper.class;
        synchronized (ObjectGroupNotFoundHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(ObjectGroupNotFoundHelper.id(), "ObjectGroupNotFound", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ObjectGroupNotFound s2) {
        any.type(ObjectGroupNotFoundHelper.type());
        ObjectGroupNotFoundHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ObjectGroupNotFound extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ObjectGroupNotFound objectGroupNotFound = ObjectGroupNotFoundHelper.read(in);
            return objectGroupNotFound;
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
        return "IDL:omg.org/PortableGroup/ObjectGroupNotFound:1.0";
    }

    public static ObjectGroupNotFound read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(ObjectGroupNotFoundHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        ObjectGroupNotFound result = new ObjectGroupNotFound(id);
        return result;
    }

    public static void write(OutputStream out, ObjectGroupNotFound s2) {
        out.write_string(ObjectGroupNotFoundHelper.id());
    }
}

