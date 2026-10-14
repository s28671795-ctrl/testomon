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
import org.omg.Security.DelegationDirective;

public abstract class DelegationDirectiveHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DelegationDirectiveHelper> clazz = DelegationDirectiveHelper.class;
        synchronized (DelegationDirectiveHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(DelegationDirectiveHelper.id(), "DelegationDirective", new String[]{"Delegate", "NoDelegate"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DelegationDirective s2) {
        any.type(DelegationDirectiveHelper.type());
        DelegationDirectiveHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DelegationDirective extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            DelegationDirective delegationDirective = DelegationDirectiveHelper.read(in);
            return delegationDirective;
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
        return "IDL:omg.org/Security/DelegationDirective:1.0";
    }

    public static DelegationDirective read(InputStream in) {
        return DelegationDirective.from_int(in.read_long());
    }

    public static void write(OutputStream out, DelegationDirective s2) {
        out.write_long(s2.value());
    }
}

