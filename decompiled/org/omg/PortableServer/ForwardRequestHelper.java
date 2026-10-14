/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.ForwardRequest;

public abstract class ForwardRequestHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ForwardRequestHelper> clazz = ForwardRequestHelper.class;
        synchronized (ForwardRequestHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(ForwardRequestHelper.id(), "ForwardRequest", new StructMember[]{new StructMember("forward_reference", ORB.init().get_primitive_tc(TCKind.tk_objref), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ForwardRequest s2) {
        any.type(ForwardRequestHelper.type());
        ForwardRequestHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ForwardRequest extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ForwardRequest forwardRequest = ForwardRequestHelper.read(in);
            return forwardRequest;
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
        return "IDL:omg.org/PortableServer/ForwardRequest:1.0";
    }

    public static ForwardRequest read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(ForwardRequestHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        Object x0 = in.read_Object();
        ForwardRequest result = new ForwardRequest(id, x0);
        return result;
    }

    public static void write(OutputStream out, ForwardRequest s2) {
        out.write_string(ForwardRequestHelper.id());
        out.write_Object(s2.forward_reference);
    }
}

