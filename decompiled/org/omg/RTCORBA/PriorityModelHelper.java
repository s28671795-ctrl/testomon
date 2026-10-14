/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.RTCORBA.PriorityModel;

public abstract class PriorityModelHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PriorityModelHelper> clazz = PriorityModelHelper.class;
        synchronized (PriorityModelHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(PriorityModelHelper.id(), "PriorityModel", new String[]{"CLIENT_PROPAGATED", "SERVER_DECLARED"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PriorityModel s2) {
        any.type(PriorityModelHelper.type());
        PriorityModelHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PriorityModel extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            PriorityModel priorityModel = PriorityModelHelper.read(in);
            return priorityModel;
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
        return "IDL:omg.org/RTCORBA/PriorityModel:1.0";
    }

    public static PriorityModel read(InputStream in) {
        return PriorityModel.from_int(in.read_long());
    }

    public static void write(OutputStream out, PriorityModel s2) {
        out.write_long(s2.value());
    }
}

