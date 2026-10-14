/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA.RTORBPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.RTCORBA.RTORBPackage.InvalidThreadpool;

public abstract class InvalidThreadpoolHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidThreadpoolHelper> clazz = InvalidThreadpoolHelper.class;
        synchronized (InvalidThreadpoolHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidThreadpoolHelper.id(), "InvalidThreadpool", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidThreadpool s2) {
        any.type(InvalidThreadpoolHelper.type());
        InvalidThreadpoolHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidThreadpool extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidThreadpool invalidThreadpool = InvalidThreadpoolHelper.read(in);
            return invalidThreadpool;
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
        return "IDL:omg.org/RTCORBA/RTORB/InvalidThreadpool:1.0";
    }

    public static InvalidThreadpool read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidThreadpoolHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        InvalidThreadpool result = new InvalidThreadpool(id);
        return result;
    }

    public static void write(OutputStream out, InvalidThreadpool s2) {
        out.write_string(InvalidThreadpoolHelper.id());
    }
}

