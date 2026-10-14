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
import org.omg.Security.DelegationMode;

public abstract class DelegationModeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DelegationModeHelper> clazz = DelegationModeHelper.class;
        synchronized (DelegationModeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(DelegationModeHelper.id(), "DelegationMode", new String[]{"SecDelModeNoDelegation", "SecDelModeSimpleDelegation", "SecDelModeCompositeDelegation"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DelegationMode s2) {
        any.type(DelegationModeHelper.type());
        DelegationModeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DelegationMode extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            DelegationMode delegationMode = DelegationModeHelper.read(in);
            return delegationMode;
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
        return "IDL:omg.org/Security/DelegationMode:1.0";
    }

    public static DelegationMode read(InputStream in) {
        return DelegationMode.from_int(in.read_long());
    }

    public static void write(OutputStream out, DelegationMode s2) {
        out.write_long(s2.value());
    }
}

