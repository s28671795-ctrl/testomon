/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAManagerPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POAManagerPackage.State;

public abstract class StateHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<StateHelper> clazz = StateHelper.class;
        synchronized (StateHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(StateHelper.id(), "State", new String[]{"HOLDING", "ACTIVE", "DISCARDING", "INACTIVE"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, State s2) {
        any.type(StateHelper.type());
        StateHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static State extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            State state = StateHelper.read(in);
            return state;
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
        return "IDL:omg.org/PortableServer/POAManager/State:1.0";
    }

    public static State read(InputStream in) {
        return State.from_int(in.read_long());
    }

    public static void write(OutputStream out, State s2) {
        out.write_long(s2.value());
    }
}

