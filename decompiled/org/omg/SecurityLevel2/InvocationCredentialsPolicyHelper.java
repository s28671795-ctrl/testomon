/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.SecurityLevel2.InvocationCredentialsPolicy;

public abstract class InvocationCredentialsPolicyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvocationCredentialsPolicyHelper> clazz = InvocationCredentialsPolicyHelper.class;
        synchronized (InvocationCredentialsPolicyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/SecurityLevel2/InvocationCredentialsPolicy:1.0", "InvocationCredentialsPolicy");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvocationCredentialsPolicy s2) {
        any.insert_Object(s2);
    }

    public static InvocationCredentialsPolicy extract(Any any) {
        return InvocationCredentialsPolicyHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/SecurityLevel2/InvocationCredentialsPolicy:1.0";
    }

    public static InvocationCredentialsPolicy read(InputStream in) {
        return InvocationCredentialsPolicyHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, InvocationCredentialsPolicy s2) {
        _out.write_Object(s2);
    }

    public static InvocationCredentialsPolicy narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof InvocationCredentialsPolicy) {
            return (InvocationCredentialsPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static InvocationCredentialsPolicy unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof InvocationCredentialsPolicy) {
            return (InvocationCredentialsPolicy)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

