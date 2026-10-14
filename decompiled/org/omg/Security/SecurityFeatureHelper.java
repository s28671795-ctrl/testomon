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
import org.omg.Security.SecurityFeature;

public abstract class SecurityFeatureHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SecurityFeatureHelper> clazz = SecurityFeatureHelper.class;
        synchronized (SecurityFeatureHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(SecurityFeatureHelper.id(), "SecurityFeature", new String[]{"SecNoDelegation", "SecSimpleDelegation", "SecCompositeDelegation", "SecNoProtection", "SecIntegrity", "SecConfidentiality", "SecIntegrityAndConfidentiality", "SecDetectReplay", "SecDetectMisordering", "SecEstablishTrustInTarget", "SecEstablishTrustInClient"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SecurityFeature s2) {
        any.type(SecurityFeatureHelper.type());
        SecurityFeatureHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SecurityFeature extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SecurityFeature securityFeature = SecurityFeatureHelper.read(in);
            return securityFeature;
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
        return "IDL:omg.org/Security/SecurityFeature:1.0";
    }

    public static SecurityFeature read(InputStream in) {
        return SecurityFeature.from_int(in.read_long());
    }

    public static void write(OutputStream out, SecurityFeature s2) {
        out.write_long(s2.value());
    }
}

