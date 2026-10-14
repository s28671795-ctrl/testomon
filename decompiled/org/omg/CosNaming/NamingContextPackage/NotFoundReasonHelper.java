/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;

public abstract class NotFoundReasonHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NotFoundReasonHelper> clazz = NotFoundReasonHelper.class;
        synchronized (NotFoundReasonHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(NotFoundReasonHelper.id(), "NotFoundReason", new String[]{"missing_node", "not_context", "not_object"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NotFoundReason s2) {
        any.type(NotFoundReasonHelper.type());
        NotFoundReasonHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NotFoundReason extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NotFoundReason notFoundReason = NotFoundReasonHelper.read(in);
            return notFoundReason;
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
        return "IDL:omg.org/CosNaming/NamingContext/NotFoundReason:1.0";
    }

    public static NotFoundReason read(InputStream in) {
        return NotFoundReason.from_int(in.read_long());
    }

    public static void write(OutputStream out, NotFoundReason s2) {
        out.write_long(s2.value());
    }
}

