/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.io.IOException;
import org.omg.ATLAS.IllegalTokenRequest;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class IllegalTokenRequestHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IllegalTokenRequestHelper> clazz = IllegalTokenRequestHelper.class;
        synchronized (IllegalTokenRequestHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(IllegalTokenRequestHelper.id(), "IllegalTokenRequest", new StructMember[]{new StructMember("the_errnum", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("the_reason", ORB.init().create_string_tc(0), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IllegalTokenRequest s2) {
        any.type(IllegalTokenRequestHelper.type());
        IllegalTokenRequestHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IllegalTokenRequest extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            IllegalTokenRequest illegalTokenRequest = IllegalTokenRequestHelper.read(in);
            return illegalTokenRequest;
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
        return "IDL:omg.org/ATLAS/IllegalTokenRequest:1.0";
    }

    public static IllegalTokenRequest read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(IllegalTokenRequestHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        String x1 = in.read_string();
        IllegalTokenRequest result = new IllegalTokenRequest(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, IllegalTokenRequest s2) {
        out.write_string(IllegalTokenRequestHelper.id());
        out.write_ulong(s2.the_errnum);
        String tmpResult0 = s2.the_reason;
        out.write_string(tmpResult0);
    }
}

