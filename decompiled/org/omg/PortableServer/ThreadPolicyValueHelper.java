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
import org.omg.PortableServer.ThreadPolicyValue;

public abstract class ThreadPolicyValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ThreadPolicyValueHelper> clazz = ThreadPolicyValueHelper.class;
        synchronized (ThreadPolicyValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(ThreadPolicyValueHelper.id(), "ThreadPolicyValue", new String[]{"ORB_CTRL_MODEL", "SINGLE_THREAD_MODEL", "MAIN_THREAD_MODEL"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ThreadPolicyValue s2) {
        any.type(ThreadPolicyValueHelper.type());
        ThreadPolicyValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ThreadPolicyValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ThreadPolicyValue threadPolicyValue = ThreadPolicyValueHelper.read(in);
            return threadPolicyValue;
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
        return "IDL:omg.org/PortableServer/ThreadPolicyValue:1.0";
    }

    public static ThreadPolicyValue read(InputStream in) {
        return ThreadPolicyValue.from_int(in.read_long());
    }

    public static void write(OutputStream out, ThreadPolicyValue s2) {
        out.write_long(s2.value());
    }
}

