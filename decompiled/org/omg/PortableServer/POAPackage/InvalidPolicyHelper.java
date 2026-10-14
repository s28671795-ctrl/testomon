/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POAPackage.InvalidPolicy;

public abstract class InvalidPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidPolicyHelper> clazz = InvalidPolicyHelper.class;
        synchronized (InvalidPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidPolicyHelper.id(), "InvalidPolicy", new StructMember[]{new StructMember("index", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidPolicy s2) {
        any.type(InvalidPolicyHelper.type());
        InvalidPolicyHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidPolicy extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidPolicy invalidPolicy = InvalidPolicyHelper.read(in);
            return invalidPolicy;
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
        return "IDL:omg.org/PortableServer/POA/InvalidPolicy:1.0";
    }

    public static InvalidPolicy read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidPolicyHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        short x0 = in.read_ushort();
        InvalidPolicy result = new InvalidPolicy(id, x0);
        return result;
    }

    public static void write(OutputStream out, InvalidPolicy s2) {
        out.write_string(InvalidPolicyHelper.id());
        out.write_ushort(s2.index);
    }
}

