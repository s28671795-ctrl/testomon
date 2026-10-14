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
import org.omg.PortableServer.RequestProcessingPolicyValue;

public abstract class RequestProcessingPolicyValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RequestProcessingPolicyValueHelper> clazz = RequestProcessingPolicyValueHelper.class;
        synchronized (RequestProcessingPolicyValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(RequestProcessingPolicyValueHelper.id(), "RequestProcessingPolicyValue", new String[]{"USE_ACTIVE_OBJECT_MAP_ONLY", "USE_DEFAULT_SERVANT", "USE_SERVANT_MANAGER"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RequestProcessingPolicyValue s2) {
        any.type(RequestProcessingPolicyValueHelper.type());
        RequestProcessingPolicyValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static RequestProcessingPolicyValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            RequestProcessingPolicyValue requestProcessingPolicyValue = RequestProcessingPolicyValueHelper.read(in);
            return requestProcessingPolicyValue;
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
        return "IDL:omg.org/PortableServer/RequestProcessingPolicyValue:1.0";
    }

    public static RequestProcessingPolicyValue read(InputStream in) {
        return RequestProcessingPolicyValue.from_int(in.read_long());
    }

    public static void write(OutputStream out, RequestProcessingPolicyValue s2) {
        out.write_long(s2.value());
    }
}

