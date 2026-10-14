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
import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;

public abstract class AdapterAlreadyExistsHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AdapterAlreadyExistsHelper> clazz = AdapterAlreadyExistsHelper.class;
        synchronized (AdapterAlreadyExistsHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(AdapterAlreadyExistsHelper.id(), "AdapterAlreadyExists", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AdapterAlreadyExists s2) {
        any.type(AdapterAlreadyExistsHelper.type());
        AdapterAlreadyExistsHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AdapterAlreadyExists extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AdapterAlreadyExists adapterAlreadyExists = AdapterAlreadyExistsHelper.read(in);
            return adapterAlreadyExists;
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
        return "IDL:omg.org/PortableServer/POA/AdapterAlreadyExists:1.0";
    }

    public static AdapterAlreadyExists read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(AdapterAlreadyExistsHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        AdapterAlreadyExists result = new AdapterAlreadyExists(id);
        return result;
    }

    public static void write(OutputStream out, AdapterAlreadyExists s2) {
        out.write_string(AdapterAlreadyExistsHelper.id());
    }
}

