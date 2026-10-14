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
import org.omg.Security.RightsCombinator;

public abstract class RightsCombinatorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RightsCombinatorHelper> clazz = RightsCombinatorHelper.class;
        synchronized (RightsCombinatorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(RightsCombinatorHelper.id(), "RightsCombinator", new String[]{"SecAllRights", "SecAnyRight"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, RightsCombinator s2) {
        any.type(RightsCombinatorHelper.type());
        RightsCombinatorHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static RightsCombinator extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            RightsCombinator rightsCombinator = RightsCombinatorHelper.read(in);
            return rightsCombinator;
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
        return "IDL:omg.org/Security/RightsCombinator:1.0";
    }

    public static RightsCombinator read(InputStream in) {
        return RightsCombinator.from_int(in.read_long());
    }

    public static void write(OutputStream out, RightsCombinator s2) {
        out.write_long(s2.value());
    }
}

