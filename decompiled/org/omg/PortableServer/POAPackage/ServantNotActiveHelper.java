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
import org.omg.PortableServer.POAPackage.ServantNotActive;

public abstract class ServantNotActiveHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServantNotActiveHelper> clazz = ServantNotActiveHelper.class;
        synchronized (ServantNotActiveHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(ServantNotActiveHelper.id(), "ServantNotActive", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServantNotActive s2) {
        any.type(ServantNotActiveHelper.type());
        ServantNotActiveHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ServantNotActive extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ServantNotActive servantNotActive = ServantNotActiveHelper.read(in);
            return servantNotActive;
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
        return "IDL:omg.org/PortableServer/POA/ServantNotActive:1.0";
    }

    public static ServantNotActive read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(ServantNotActiveHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        ServantNotActive result = new ServantNotActive(id);
        return result;
    }

    public static void write(OutputStream out, ServantNotActive s2) {
        out.write_string(ServantNotActiveHelper.id());
    }
}

