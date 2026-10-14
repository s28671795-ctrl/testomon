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
import org.omg.Security.AssociationStatus;

public abstract class AssociationStatusHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AssociationStatusHelper> clazz = AssociationStatusHelper.class;
        synchronized (AssociationStatusHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(AssociationStatusHelper.id(), "AssociationStatus", new String[]{"SecAssocSuccess", "SecAssocFailure", "SecAssocContinue"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AssociationStatus s2) {
        any.type(AssociationStatusHelper.type());
        AssociationStatusHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AssociationStatus extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AssociationStatus associationStatus = AssociationStatusHelper.read(in);
            return associationStatus;
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
        return "IDL:omg.org/Security/AssociationStatus:1.0";
    }

    public static AssociationStatus read(InputStream in) {
        return AssociationStatus.from_int(in.read_long());
    }

    public static void write(OutputStream out, AssociationStatus s2) {
        out.write_long(s2.value());
    }
}

