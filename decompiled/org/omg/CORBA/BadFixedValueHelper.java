/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BadFixedValue;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class BadFixedValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BadFixedValueHelper> clazz = BadFixedValueHelper.class;
        synchronized (BadFixedValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(BadFixedValueHelper.id(), "BadFixedValue", new StructMember[]{new StructMember("offset", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, BadFixedValue s2) {
        any.type(BadFixedValueHelper.type());
        BadFixedValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BadFixedValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            BadFixedValue badFixedValue = BadFixedValueHelper.read(in);
            return badFixedValue;
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
        return "IDL:omg.org/CORBA/BadFixedValue:1.0";
    }

    public static BadFixedValue read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(BadFixedValueHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int x0 = in.read_ulong();
        BadFixedValue result = new BadFixedValue(id, x0);
        return result;
    }

    public static void write(OutputStream out, BadFixedValue s2) {
        out.write_string(BadFixedValueHelper.id());
        out.write_ulong(s2.offset);
    }
}

