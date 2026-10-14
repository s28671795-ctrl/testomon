/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.IORAddressingInfo;
import org.omg.IOP.IORHelper;
import org.omg.IOP.ProfileIdHelper;
import org.omg.IOP.TaggedProfileHelper;

public abstract class IORAddressingInfoHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IORAddressingInfoHelper> clazz = IORAddressingInfoHelper.class;
        synchronized (IORAddressingInfoHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(IORAddressingInfoHelper.id(), "IORAddressingInfo", new StructMember[]{new StructMember("selected_profile_index", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("ior", ORB.init().create_struct_tc(IORHelper.id(), "IOR", new StructMember[]{new StructMember("type_id", ORB.init().create_string_tc(0), null), new StructMember("profiles", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(TaggedProfileHelper.id(), "TaggedProfile", new StructMember[]{new StructMember("tag", ORB.init().create_alias_tc(ProfileIdHelper.id(), "ProfileId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("profile_data", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null)})), null)}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IORAddressingInfo s2) {
        any.type(IORAddressingInfoHelper.type());
        IORAddressingInfoHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IORAddressingInfo extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            IORAddressingInfo iORAddressingInfo = IORAddressingInfoHelper.read(in);
            return iORAddressingInfo;
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
        return "IDL:omg.org/GIOP/IORAddressingInfo:1.0";
    }

    public static IORAddressingInfo read(InputStream in) {
        IORAddressingInfo result = new IORAddressingInfo();
        result.selected_profile_index = in.read_ulong();
        result.ior = IORHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, IORAddressingInfo s2) {
        out.write_ulong(s2.selected_profile_index);
        IORHelper.write(out, s2.ior);
    }
}

