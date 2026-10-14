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
import org.omg.Security.CommunicationDirection;

public abstract class CommunicationDirectionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CommunicationDirectionHelper> clazz = CommunicationDirectionHelper.class;
        synchronized (CommunicationDirectionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(CommunicationDirectionHelper.id(), "CommunicationDirection", new String[]{"SecDirectionBoth", "SecDirectionRequest", "SecDirectionReply"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CommunicationDirection s2) {
        any.type(CommunicationDirectionHelper.type());
        CommunicationDirectionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CommunicationDirection extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CommunicationDirection communicationDirection = CommunicationDirectionHelper.read(in);
            return communicationDirection;
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
        return "IDL:omg.org/Security/CommunicationDirection:1.0";
    }

    public static CommunicationDirection read(InputStream in) {
        return CommunicationDirection.from_int(in.read_long());
    }

    public static void write(OutputStream out, CommunicationDirection s2) {
        out.write_long(s2.value());
    }
}

