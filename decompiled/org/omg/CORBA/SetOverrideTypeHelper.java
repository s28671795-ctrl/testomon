/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class SetOverrideTypeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SetOverrideTypeHelper> clazz = SetOverrideTypeHelper.class;
        synchronized (SetOverrideTypeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(SetOverrideTypeHelper.id(), "SetOverrideType", new String[]{"SET_OVERRIDE", "ADD_OVERRIDE"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SetOverrideType s2) {
        any.type(SetOverrideTypeHelper.type());
        SetOverrideTypeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SetOverrideType extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SetOverrideType setOverrideType = SetOverrideTypeHelper.read(in);
            return setOverrideType;
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
        return "IDL:omg.org/CORBA/SetOverrideType:1.0";
    }

    public static SetOverrideType read(InputStream in) {
        return SetOverrideType.from_int(in.read_long());
    }

    public static void write(OutputStream out, SetOverrideType s2) {
        out.write_long(s2.value());
    }
}

