/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.PollableSetPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.PollableSetPackage.NoPossiblePollable;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class NoPossiblePollableHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NoPossiblePollableHelper> clazz = NoPossiblePollableHelper.class;
        synchronized (NoPossiblePollableHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(NoPossiblePollableHelper.id(), "NoPossiblePollable", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NoPossiblePollable s2) {
        any.type(NoPossiblePollableHelper.type());
        NoPossiblePollableHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NoPossiblePollable extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NoPossiblePollable noPossiblePollable = NoPossiblePollableHelper.read(in);
            return noPossiblePollable;
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
        return "IDL:omg.org/CORBA/PollableSet/NoPossiblePollable:1.0";
    }

    public static NoPossiblePollable read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(NoPossiblePollableHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        NoPossiblePollable result = new NoPossiblePollable(id);
        return result;
    }

    public static void write(OutputStream out, NoPossiblePollable s2) {
        out.write_string(NoPossiblePollableHelper.id());
    }
}

