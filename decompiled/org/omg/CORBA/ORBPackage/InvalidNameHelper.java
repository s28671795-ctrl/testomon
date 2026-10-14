/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ORBPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class InvalidNameHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidNameHelper> clazz = InvalidNameHelper.class;
        synchronized (InvalidNameHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidNameHelper.id(), "InvalidName", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidName s2) {
        any.type(InvalidNameHelper.type());
        InvalidNameHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidName extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidName invalidName = InvalidNameHelper.read(in);
            return invalidName;
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
        return "IDL:omg.org/CORBA/ORB/InvalidName:1.0";
    }

    public static InvalidName read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidNameHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        InvalidName result = new InvalidName(id);
        return result;
    }

    public static void write(OutputStream out, InvalidName s2) {
        out.write_string(InvalidNameHelper.id());
    }
}

