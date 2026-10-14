/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.IOR;
import org.omg.IOP.ProfileIdHelper;
import org.omg.IOP.TaggedProfile;
import org.omg.IOP.TaggedProfileHelper;

public abstract class IORHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IORHelper> clazz = IORHelper.class;
        synchronized (IORHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(IORHelper.id(), "IOR", new StructMember[]{new StructMember("type_id", ORB.init().create_string_tc(0), null), new StructMember("profiles", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(TaggedProfileHelper.id(), "TaggedProfile", new StructMember[]{new StructMember("tag", ORB.init().create_alias_tc(ProfileIdHelper.id(), "ProfileId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("profile_data", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null)})), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IOR s2) {
        any.type(IORHelper.type());
        IORHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IOR extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            IOR iOR = IORHelper.read(in);
            return iOR;
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
        return "IDL:omg.org/IOP/IOR:1.0";
    }

    public static IOR read(InputStream in) {
        IOR result = new IOR();
        result.type_id = in.read_string();
        int _lresult_profiles2 = in.read_long();
        try {
            int x = in.available();
            if (x > 0 && _lresult_profiles2 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _lresult_profiles2);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        result.profiles = new TaggedProfile[_lresult_profiles2];
        for (int i = 0; i < result.profiles.length; ++i) {
            result.profiles[i] = TaggedProfileHelper.read(in);
        }
        return result;
    }

    public static void write(OutputStream out, IOR s2) {
        String tmpResult0 = s2.type_id;
        out.write_string(tmpResult0);
        out.write_long(s2.profiles.length);
        for (int i = 0; i < s2.profiles.length; ++i) {
            TaggedProfileHelper.write(out, s2.profiles[i]);
        }
    }
}

