/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableInterceptor.InvalidSlot;

public abstract class InvalidSlotHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidSlotHelper> clazz = InvalidSlotHelper.class;
        synchronized (InvalidSlotHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidSlotHelper.id(), "InvalidSlot", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidSlot s2) {
        any.type(InvalidSlotHelper.type());
        InvalidSlotHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidSlot extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidSlot invalidSlot = InvalidSlotHelper.read(in);
            return invalidSlot;
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
        return "IDL:omg.org/PortableInterceptor/InvalidSlot:1.0";
    }

    public static InvalidSlot read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidSlotHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        InvalidSlot result = new InvalidSlot(id);
        return result;
    }

    public static void write(OutputStream out, InvalidSlot s2) {
        out.write_string(InvalidSlotHelper.id());
    }
}

