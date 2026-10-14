/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextExtPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;

public abstract class InvalidAddressHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidAddressHelper> clazz = InvalidAddressHelper.class;
        synchronized (InvalidAddressHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidAddressHelper.id(), "InvalidAddress", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidAddress s2) {
        any.type(InvalidAddressHelper.type());
        InvalidAddressHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidAddress extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidAddress invalidAddress = InvalidAddressHelper.read(in);
            return invalidAddress;
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
        return "IDL:omg.org/CosNaming/NamingContextExt/InvalidAddress:1.0";
    }

    public static InvalidAddress read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidAddressHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        InvalidAddress result = new InvalidAddress(id);
        return result;
    }

    public static void write(OutputStream out, InvalidAddress s2) {
        out.write_string(InvalidAddressHelper.id());
    }
}

