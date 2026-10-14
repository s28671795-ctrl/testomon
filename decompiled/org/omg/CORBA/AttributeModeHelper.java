/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class AttributeModeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AttributeModeHelper> clazz = AttributeModeHelper.class;
        synchronized (AttributeModeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(AttributeModeHelper.id(), "AttributeMode", new String[]{"ATTR_NORMAL", "ATTR_READONLY"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AttributeMode s2) {
        any.type(AttributeModeHelper.type());
        AttributeModeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AttributeMode extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AttributeMode attributeMode = AttributeModeHelper.read(in);
            return attributeMode;
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
        return "IDL:omg.org/CORBA/AttributeMode:1.0";
    }

    public static AttributeMode read(InputStream in) {
        return AttributeMode.from_int(in.read_long());
    }

    public static void write(OutputStream out, AttributeMode s2) {
        out.write_long(s2.value());
    }
}

