/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
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
import org.omg.Messaging.PolicyValueHelper;

public abstract class PolicyValueSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, PolicyValue[] s2) {
        any.type(PolicyValueSeqHelper.type());
        PolicyValueSeqHelper.write(any.create_output_stream(), s2);
    }

    public static PolicyValue[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return PolicyValueSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PolicyValueSeqHelper> clazz = PolicyValueSeqHelper.class;
        synchronized (PolicyValueSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(PolicyValueSeqHelper.id(), "PolicyValueSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(PolicyValueHelper.id(), "PolicyValue", new StructMember[]{new StructMember("ptype", ORB.init().create_alias_tc(PolicyTypeHelper.id(), "PolicyType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("pvalue", ORB.init().create_alias_tc(PolicyDataHelper.id(), "PolicyData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Messaging/PolicyValueSeq:1.0";
    }

    public static PolicyValue[] read(InputStream _in) {
        int _l_result0 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result0 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        PolicyValue[] _result = new PolicyValue[_l_result0];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = PolicyValueHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, PolicyValue[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            PolicyValueHelper.write(_out, _s[i]);
        }
    }
}

