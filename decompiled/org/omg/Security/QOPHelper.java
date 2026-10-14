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
import org.omg.Security.QOP;

public abstract class QOPHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<QOPHelper> clazz = QOPHelper.class;
        synchronized (QOPHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(QOPHelper.id(), "QOP", new String[]{"SecQOPNoProtection", "SecQOPIntegrity", "SecQOPConfidentiality", "SecQOPIntegrityAndConfidentiality"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, QOP s2) {
        any.type(QOPHelper.type());
        QOPHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static QOP extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            QOP qOP = QOPHelper.read(in);
            return qOP;
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
        return "IDL:omg.org/Security/QOP:1.0";
    }

    public static QOP read(InputStream in) {
        return QOP.from_int(in.read_long());
    }

    public static void write(OutputStream out, QOP s2) {
        out.write_long(s2.value());
    }
}

