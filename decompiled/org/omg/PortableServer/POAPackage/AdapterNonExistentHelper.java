/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;

public abstract class AdapterNonExistentHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AdapterNonExistentHelper> clazz = AdapterNonExistentHelper.class;
        synchronized (AdapterNonExistentHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(AdapterNonExistentHelper.id(), "AdapterNonExistent", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AdapterNonExistent s2) {
        any.type(AdapterNonExistentHelper.type());
        AdapterNonExistentHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AdapterNonExistent extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AdapterNonExistent adapterNonExistent = AdapterNonExistentHelper.read(in);
            return adapterNonExistent;
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
        return "IDL:omg.org/PortableServer/POA/AdapterNonExistent:1.0";
    }

    public static AdapterNonExistent read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(AdapterNonExistentHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        AdapterNonExistent result = new AdapterNonExistent(id);
        return result;
    }

    public static void write(OutputStream out, AdapterNonExistent s2) {
        out.write_string(AdapterNonExistentHelper.id());
    }
}

