/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.TypeCodePackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class BoundsHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BoundsHelper> clazz = BoundsHelper.class;
        synchronized (BoundsHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(BoundsHelper.id(), "Bounds", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Bounds s2) {
        any.type(BoundsHelper.type());
        BoundsHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Bounds extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            Bounds bounds = BoundsHelper.read(in);
            return bounds;
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
        return "IDL:omg.org/CORBA/TypeCode/Bounds:1.0";
    }

    public static Bounds read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(BoundsHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        Bounds result = new Bounds(id);
        return result;
    }

    public static void write(OutputStream out, Bounds s2) {
        out.write_string(BoundsHelper.id());
    }
}

