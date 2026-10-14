/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IIOP.ProfileBody_1_0;
import org.omg.IIOP.VersionHelper;
import org.omg.IOP.ObjectKeyHelper;

public abstract class ProfileBody_1_0Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ProfileBody_1_0Helper> clazz = ProfileBody_1_0Helper.class;
        synchronized (ProfileBody_1_0Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ProfileBody_1_0Helper.id(), "ProfileBody_1_0", new StructMember[]{new StructMember("iiop_version", ORB.init().create_struct_tc(VersionHelper.id(), "Version", new StructMember[]{new StructMember("major", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(10)), null)}), null), new StructMember("host", ORB.init().create_string_tc(0), null), new StructMember("port", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("object_key", ORB.init().create_alias_tc(ObjectKeyHelper.id(), "ObjectKey", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ProfileBody_1_0 s2) {
        any.type(ProfileBody_1_0Helper.type());
        ProfileBody_1_0Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ProfileBody_1_0 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ProfileBody_1_0 profileBody_1_0 = ProfileBody_1_0Helper.read(in);
            return profileBody_1_0;
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
        return "IDL:omg.org/IIOP/ProfileBody_1_0:1.0";
    }

    public static ProfileBody_1_0 read(InputStream in) {
        ProfileBody_1_0 result = new ProfileBody_1_0();
        result.iiop_version = VersionHelper.read(in);
        result.host = in.read_string();
        result.port = in.read_ushort();
        result.object_key = OctetSeqHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, ProfileBody_1_0 s2) {
        VersionHelper.write(out, s2.iiop_version);
        String tmpResult0 = s2.host;
        out.write_string(tmpResult0);
        out.write_ushort(s2.port);
        OctetSeqHelper.write(out, s2.object_key);
    }
}

