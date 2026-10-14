/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.DelegationState;

public abstract class DelegationStateHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DelegationStateHelper> clazz = DelegationStateHelper.class;
        synchronized (DelegationStateHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(DelegationStateHelper.id(), "DelegationState", new String[]{"SecInitiator", "SecDelegate"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DelegationState s2) {
        any.type(DelegationStateHelper.type());
        DelegationStateHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DelegationState extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            DelegationState delegationState = DelegationStateHelper.read(in);
            return delegationState;
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
        return "IDL:omg.org/Security/DelegationState:1.0";
    }

    public static DelegationState read(InputStream in) {
        return DelegationState.from_int(in.read_long());
    }

    public static void write(OutputStream out, DelegationState s2) {
        out.write_long(s2.value());
    }
}

