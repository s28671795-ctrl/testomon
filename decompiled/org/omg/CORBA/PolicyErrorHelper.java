/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.PolicyError;
import org.omg.CORBA.PolicyErrorCodeHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class PolicyErrorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PolicyErrorHelper> clazz = PolicyErrorHelper.class;
        synchronized (PolicyErrorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(PolicyErrorHelper.id(), "PolicyError", new StructMember[]{new StructMember("reason", ORB.init().create_alias_tc(PolicyErrorCodeHelper.id(), "PolicyErrorCode", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PolicyError s2) {
        any.type(PolicyErrorHelper.type());
        PolicyErrorHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PolicyError extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            PolicyError policyError = PolicyErrorHelper.read(in);
            return policyError;
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
        return "IDL:omg.org/CORBA/PolicyError:1.0";
    }

    public static PolicyError read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(PolicyErrorHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        short x0 = in.read_short();
        PolicyError result = new PolicyError(id, x0);
        return result;
    }

    public static void write(OutputStream out, PolicyError s2) {
        out.write_string(PolicyErrorHelper.id());
        out.write_short(s2.reason);
    }
}

