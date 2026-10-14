/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.PollableSetPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.PollableSetPackage.UnknownPollable;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class UnknownPollableHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<UnknownPollableHelper> clazz = UnknownPollableHelper.class;
        synchronized (UnknownPollableHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(UnknownPollableHelper.id(), "UnknownPollable", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, UnknownPollable s2) {
        any.type(UnknownPollableHelper.type());
        UnknownPollableHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UnknownPollable extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            UnknownPollable unknownPollable = UnknownPollableHelper.read(in);
            return unknownPollable;
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
        return "IDL:omg.org/CORBA/PollableSet/UnknownPollable:1.0";
    }

    public static UnknownPollable read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(UnknownPollableHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        UnknownPollable result = new UnknownPollable(id);
        return result;
    }

    public static void write(OutputStream out, UnknownPollable s2) {
        out.write_string(UnknownPollableHelper.id());
    }
}

