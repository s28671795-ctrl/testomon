/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.PolicyTypeHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Messaging.PolicyDataHelper;
import org.omg.Messaging.PolicyValue;

public abstract class PolicyValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PolicyValueHelper> clazz = PolicyValueHelper.class;
        synchronized (PolicyValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(PolicyValueHelper.id(), "PolicyValue", new StructMember[]{new StructMember("ptype", ORB.init().create_alias_tc(PolicyTypeHelper.id(), "PolicyType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("pvalue", ORB.init().create_alias_tc(PolicyDataHelper.id(), "PolicyData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PolicyValue s2) {
        any.type(PolicyValueHelper.type());
        PolicyValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PolicyValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            PolicyValue policyValue = PolicyValueHelper.read(in);
            return policyValue;
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
        return "IDL:omg.org/Messaging/PolicyValue:1.0";
    }

    public static PolicyValue read(InputStream in) {
        PolicyValue result = new PolicyValue();
        result.ptype = in.read_ulong();
        result.pvalue = OctetSeqHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, PolicyValue s2) {
        out.write_ulong(s2.ptype);
        OctetSeqHelper.write(out, s2.pvalue);
    }
}

