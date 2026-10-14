/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.exception_type;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class exception_typeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<exception_typeHelper> clazz = exception_typeHelper.class;
        synchronized (exception_typeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(exception_typeHelper.id(), "exception_type", new String[]{"NO_EXCEPTION", "USER_EXCEPTION", "SYSTEM_EXCEPTION"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, exception_type s2) {
        any.type(exception_typeHelper.type());
        exception_typeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static exception_type extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            exception_type exception_type2 = exception_typeHelper.read(in);
            return exception_type2;
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
        return "IDL:omg.org/CORBA/exception_type:1.0";
    }

    public static exception_type read(InputStream in) {
        return exception_type.from_int(in.read_long());
    }

    public static void write(OutputStream out, exception_type s2) {
        out.write_long(s2.value());
    }
}

