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
import org.omg.IOP.ProfileIdHelper;
import org.omg.IOP.TaggedProfile;

public abstract class TaggedProfileHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TaggedProfileHelper> clazz = TaggedProfileHelper.class;
        synchronized (TaggedProfileHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(TaggedProfileHelper.id(), "TaggedProfile", new StructMember[]{new StructMember("tag", ORB.init().create_alias_tc(ProfileIdHelper.id(), "ProfileId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("profile_data", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TaggedProfile s2) {
        any.type(TaggedProfileHelper.type());
        TaggedProfileHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TaggedProfile extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TaggedProfile taggedProfile = TaggedProfileHelper.read(in);
            return taggedProfile;
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
        return "IDL:omg.org/IOP/TaggedProfile:1.0";
    }

    public static TaggedProfile read(InputStream in) {
        TaggedProfile result = new TaggedProfile();
        result.tag = in.read_ulong();
        int _lresult_profile_data0 = in.read_long();
        try {
            int x = in.available();
            if (x > 0 && _lresult_profile_data0 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _lresult_profile_data0);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        result.profile_data = new byte[_lresult_profile_data0];
        in.read_octet_array(result.profile_data, 0, _lresult_profile_data0);
        return result;
    }

    public static void write(OutputStream out, TaggedProfile s2) {
        out.write_ulong(s2.tag);
        out.write_long(s2.profile_data.length);
        out.write_octet_array(s2.profile_data, 0, s2.profile_data.length);
    }
}

