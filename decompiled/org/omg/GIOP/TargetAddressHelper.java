/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.AddressingDispositionHelper;
import org.omg.GIOP.IORAddressingInfo;
import org.omg.GIOP.IORAddressingInfoHelper;
import org.omg.GIOP.TargetAddress;
import org.omg.IOP.ObjectKeyHelper;
import org.omg.IOP.TaggedProfile;
import org.omg.IOP.TaggedProfileHelper;

public abstract class TargetAddressHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TargetAddressHelper> clazz = TargetAddressHelper.class;
        synchronized (TargetAddressHelper.class) {
            if (_type != null) return _type;
            UnionMember[] members = new UnionMember[3];
            Any label_any = ORB.init().create_any();
            label_any.insert_short((short)0);
            members[0] = new UnionMember("object_key", label_any, ORB.init().create_alias_tc(ObjectKeyHelper.id(), "ObjectKey", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null);
            label_any = ORB.init().create_any();
            label_any.insert_short((short)1);
            members[1] = new UnionMember("profile", label_any, TaggedProfileHelper.type(), null);
            label_any = ORB.init().create_any();
            label_any.insert_short((short)2);
            members[2] = new UnionMember("ior", label_any, IORAddressingInfoHelper.type(), null);
            _type = ORB.init().create_union_tc(TargetAddressHelper.id(), "TargetAddress", ORB.init().create_alias_tc(AddressingDispositionHelper.id(), "AddressingDisposition", ORB.init().get_primitive_tc(TCKind.from_int(2))), members);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TargetAddress s2) {
        any.type(TargetAddressHelper.type());
        TargetAddressHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TargetAddress extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TargetAddress targetAddress = TargetAddressHelper.read(in);
            return targetAddress;
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
        return "IDL:omg.org/GIOP/TargetAddress:1.0";
    }

    public static TargetAddress read(InputStream in) {
        TargetAddress result = new TargetAddress();
        short disc = in.read_short();
        switch (disc) {
            case 0: {
                byte[] _var = OctetSeqHelper.read(in);
                result.object_key(_var);
                break;
            }
            case 1: {
                TaggedProfile _var = TaggedProfileHelper.read(in);
                result.profile(_var);
                break;
            }
            case 2: {
                IORAddressingInfo _var = IORAddressingInfoHelper.read(in);
                result.ior(_var);
                break;
            }
        }
        return result;
    }

    public static void write(OutputStream out, TargetAddress s2) {
        out.write_short(s2.discriminator());
        switch (s2.discriminator()) {
            case 0: {
                OctetSeqHelper.write(out, s2.object_key());
                break;
            }
            case 1: {
                TaggedProfileHelper.write(out, s2.profile());
                break;
            }
            case 2: {
                IORAddressingInfoHelper.write(out, s2.ior());
            }
        }
    }
}

