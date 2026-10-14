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
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class BadKindHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BadKindHelper> clazz = BadKindHelper.class;
        synchronized (BadKindHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(BadKindHelper.id(), "BadKind", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, BadKind s2) {
        any.type(BadKindHelper.type());
        BadKindHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BadKind extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            BadKind badKind = BadKindHelper.read(in);
            return badKind;
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
        return "IDL:omg.org/CORBA/TypeCode/BadKind:1.0";
    }

    public static BadKind read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(BadKindHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        BadKind result = new BadKind(id);
        return result;
    }

    public static void write(OutputStream out, BadKind s2) {
        out.write_string(BadKindHelper.id());
    }
}

