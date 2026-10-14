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
import org.omg.PortableGroup.ObjectNotAdded;

public abstract class ObjectNotAddedHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ObjectNotAddedHelper> clazz = ObjectNotAddedHelper.class;
        synchronized (ObjectNotAddedHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(ObjectNotAddedHelper.id(), "ObjectNotAdded", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ObjectNotAdded s2) {
        any.type(ObjectNotAddedHelper.type());
        ObjectNotAddedHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ObjectNotAdded extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ObjectNotAdded objectNotAdded = ObjectNotAddedHelper.read(in);
            return objectNotAdded;
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
        return "IDL:omg.org/PortableGroup/ObjectNotAdded:1.0";
    }

    public static ObjectNotAdded read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(ObjectNotAddedHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        ObjectNotAdded result = new ObjectNotAdded(id);
        return result;
    }

    public static void write(OutputStream out, ObjectNotAdded s2) {
        out.write_string(ObjectNotAddedHelper.id());
    }
}

