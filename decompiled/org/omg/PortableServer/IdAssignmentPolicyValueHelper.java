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
import org.omg.PortableServer.IdAssignmentPolicyValue;

public abstract class IdAssignmentPolicyValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IdAssignmentPolicyValueHelper> clazz = IdAssignmentPolicyValueHelper.class;
        synchronized (IdAssignmentPolicyValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(IdAssignmentPolicyValueHelper.id(), "IdAssignmentPolicyValue", new String[]{"USER_ID", "SYSTEM_ID"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IdAssignmentPolicyValue s2) {
        any.type(IdAssignmentPolicyValueHelper.type());
        IdAssignmentPolicyValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IdAssignmentPolicyValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            IdAssignmentPolicyValue idAssignmentPolicyValue = IdAssignmentPolicyValueHelper.read(in);
            return idAssignmentPolicyValue;
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
        return "IDL:omg.org/PortableServer/IdAssignmentPolicyValue:1.0";
    }

    public static IdAssignmentPolicyValue read(InputStream in) {
        return IdAssignmentPolicyValue.from_int(in.read_long());
    }

    public static void write(OutputStream out, IdAssignmentPolicyValue s2) {
        out.write_long(s2.value());
    }
}

