/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.CurrentPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.CurrentPackage.NoContext;

public abstract class NoContextHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NoContextHelper> clazz = NoContextHelper.class;
        synchronized (NoContextHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(NoContextHelper.id(), "NoContext", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NoContext s2) {
        any.type(NoContextHelper.type());
        NoContextHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NoContext extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NoContext noContext = NoContextHelper.read(in);
            return noContext;
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
        return "IDL:omg.org/PortableServer/Current/NoContext:1.0";
    }

    public static NoContext read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(NoContextHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        NoContext result = new NoContext(id);
        return result;
    }

    public static void write(OutputStream out, NoContext s2) {
        out.write_string(NoContextHelper.id());
    }
}

