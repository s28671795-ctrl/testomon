/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.VersionHelper;
import org.omg.PortableGroup.GroupDomainIdHelper;
import org.omg.PortableGroup.ObjectGroupIdHelper;
import org.omg.PortableGroup.ObjectGroupRefVersionHelper;
import org.omg.PortableGroup.TagGroupTaggedComponent;

public abstract class TagGroupTaggedComponentHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TagGroupTaggedComponentHelper> clazz = TagGroupTaggedComponentHelper.class;
        synchronized (TagGroupTaggedComponentHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(TagGroupTaggedComponentHelper.id(), "TagGroupTaggedComponent", new StructMember[]{new StructMember("group_version", ORB.init().create_struct_tc(VersionHelper.id(), "Version", new StructMember[]{new StructMember("major", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(10)), null)}), null), new StructMember("group_domain_id", ORB.init().create_alias_tc(GroupDomainIdHelper.id(), "GroupDomainId", ORB.init().create_string_tc(0)), null), new StructMember("object_group_id", ORB.init().create_alias_tc(ObjectGroupIdHelper.id(), "ObjectGroupId", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("object_group_ref_version", ORB.init().create_alias_tc(ObjectGroupRefVersionHelper.id(), "ObjectGroupRefVersion", ORB.init().get_primitive_tc(TCKind.from_int(5))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TagGroupTaggedComponent s2) {
        any.type(TagGroupTaggedComponentHelper.type());
        TagGroupTaggedComponentHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TagGroupTaggedComponent extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TagGroupTaggedComponent tagGroupTaggedComponent = TagGroupTaggedComponentHelper.read(in);
            return tagGroupTaggedComponent;
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
        return "IDL:omg.org/PortableGroup/TagGroupTaggedComponent:1.0";
    }

    public static TagGroupTaggedComponent read(InputStream in) {
        TagGroupTaggedComponent result = new TagGroupTaggedComponent();
        result.group_version = VersionHelper.read(in);
        result.group_domain_id = in.read_string();
        result.object_group_id = in.read_ulonglong();
        result.object_group_ref_version = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, TagGroupTaggedComponent s2) {
        VersionHelper.write(out, s2.group_version);
        String tmpResult1 = s2.group_domain_id;
        out.write_string(tmpResult1);
        out.write_ulonglong(s2.object_group_id);
        out.write_ulong(s2.object_group_ref_version);
    }
}

