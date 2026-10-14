/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.ServantRetentionPolicyValue;

public abstract class ServantRetentionPolicyValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServantRetentionPolicyValueHelper> clazz = ServantRetentionPolicyValueHelper.class;
        synchronized (ServantRetentionPolicyValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(ServantRetentionPolicyValueHelper.id(), "ServantRetentionPolicyValue", new String[]{"RETAIN", "NON_RETAIN"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ServantRetentionPolicyValue s2) {
        any.type(ServantRetentionPolicyValueHelper.type());
        ServantRetentionPolicyValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ServantRetentionPolicyValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ServantRetentionPolicyValue servantRetentionPolicyValue = ServantRetentionPolicyValueHelper.read(in);
            return servantRetentionPolicyValue;
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
        return "IDL:omg.org/PortableServer/ServantRetentionPolicyValue:1.0";
    }

    public static ServantRetentionPolicyValue read(InputStream in) {
        return ServantRetentionPolicyValue.from_int(in.read_long());
    }

    public static void write(OutputStream out, ServantRetentionPolicyValue s2) {
        out.write_long(s2.value());
    }
}

