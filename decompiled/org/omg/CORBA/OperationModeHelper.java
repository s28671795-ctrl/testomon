/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OperationMode;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class OperationModeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<OperationModeHelper> clazz = OperationModeHelper.class;
        synchronized (OperationModeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(OperationModeHelper.id(), "OperationMode", new String[]{"OP_NORMAL", "OP_ONEWAY"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, OperationMode s2) {
        any.type(OperationModeHelper.type());
        OperationModeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static OperationMode extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            OperationMode operationMode = OperationModeHelper.read(in);
            return operationMode;
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
        return "IDL:omg.org/CORBA/OperationMode:1.0";
    }

    public static OperationMode read(InputStream in) {
        return OperationMode.from_int(in.read_long());
    }

    public static void write(OutputStream out, OperationMode s2) {
        out.write_long(s2.value());
    }
}

