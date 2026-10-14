/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SSLIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SSLIOP.SSL;
import org.omg.Security.AssociationOptionsHelper;

public abstract class SSLHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SSLHelper> clazz = SSLHelper.class;
        synchronized (SSLHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(SSLHelper.id(), "SSL", new StructMember[]{new StructMember("target_supports", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("target_requires", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("port", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SSL s2) {
        any.type(SSLHelper.type());
        SSLHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SSL extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SSL sSL = SSLHelper.read(in);
            return sSL;
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
        return "IDL:omg.org/SSLIOP/SSL:1.0";
    }

    public static SSL read(InputStream in) {
        SSL result = new SSL();
        result.target_supports = in.read_ushort();
        result.target_requires = in.read_ushort();
        result.port = in.read_ushort();
        return result;
    }

    public static void write(OutputStream out, SSL s2) {
        out.write_ushort(s2.target_supports);
        out.write_ushort(s2.target_requires);
        out.write_ushort(s2.port);
    }
}

