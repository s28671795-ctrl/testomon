/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.EstablishTrust;

public abstract class EstablishTrustHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<EstablishTrustHelper> clazz = EstablishTrustHelper.class;
        synchronized (EstablishTrustHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(EstablishTrustHelper.id(), "EstablishTrust", new StructMember[]{new StructMember("trust_in_client", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("trust_in_target", ORB.init().get_primitive_tc(TCKind.from_int(8)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, EstablishTrust s2) {
        any.type(EstablishTrustHelper.type());
        EstablishTrustHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static EstablishTrust extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            EstablishTrust establishTrust = EstablishTrustHelper.read(in);
            return establishTrust;
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
        return "IDL:omg.org/Security/EstablishTrust:1.0";
    }

    public static EstablishTrust read(InputStream in) {
        EstablishTrust result = new EstablishTrust();
        result.trust_in_client = in.read_boolean();
        result.trust_in_target = in.read_boolean();
        return result;
    }

    public static void write(OutputStream out, EstablishTrust s2) {
        out.write_boolean(s2.trust_in_client);
        out.write_boolean(s2.trust_in_target);
    }
}

