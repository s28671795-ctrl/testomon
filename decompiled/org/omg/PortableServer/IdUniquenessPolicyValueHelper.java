/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.IdUniquenessPolicyValue;

public abstract class IdUniquenessPolicyValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IdUniquenessPolicyValueHelper> clazz = IdUniquenessPolicyValueHelper.class;
        synchronized (IdUniquenessPolicyValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(IdUniquenessPolicyValueHelper.id(), "IdUniquenessPolicyValue", new String[]{"UNIQUE_ID", "MULTIPLE_ID"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IdUniquenessPolicyValue s2) {
        any.type(IdUniquenessPolicyValueHelper.type());
        IdUniquenessPolicyValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IdUniquenessPolicyValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            IdUniquenessPolicyValue idUniquenessPolicyValue = IdUniquenessPolicyValueHelper.read(in);
            return idUniquenessPolicyValue;
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
        return "IDL:omg.org/PortableServer/IdUniquenessPolicyValue:1.0";
    }

    public static IdUniquenessPolicyValue read(InputStream in) {
        return IdUniquenessPolicyValue.from_int(in.read_long());
    }

    public static void write(OutputStream out, IdUniquenessPolicyValue s2) {
        out.write_long(s2.value());
    }
}

