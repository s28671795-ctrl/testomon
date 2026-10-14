/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.DynamicAny.MustTruncate;

public abstract class MustTruncateHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MustTruncateHelper> clazz = MustTruncateHelper.class;
        synchronized (MustTruncateHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(MustTruncateHelper.id(), "MustTruncate", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, MustTruncate s2) {
        any.type(MustTruncateHelper.type());
        MustTruncateHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static MustTruncate extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            MustTruncate mustTruncate = MustTruncateHelper.read(in);
            return mustTruncate;
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
        return "IDL:omg.org/DynamicAny/MustTruncate:1.0";
    }

    public static MustTruncate read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(MustTruncateHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        MustTruncate result = new MustTruncate(id);
        return result;
    }

    public static void write(OutputStream out, MustTruncate s2) {
        out.write_string(MustTruncateHelper.id());
    }
}

