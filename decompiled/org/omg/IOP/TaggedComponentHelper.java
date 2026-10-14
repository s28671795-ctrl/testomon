/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.ComponentDataHelper;
import org.omg.IOP.ComponentIdHelper;
import org.omg.IOP.TaggedComponent;

public abstract class TaggedComponentHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TaggedComponentHelper> clazz = TaggedComponentHelper.class;
        synchronized (TaggedComponentHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(TaggedComponentHelper.id(), "TaggedComponent", new StructMember[]{new StructMember("tag", ORB.init().create_alias_tc(ComponentIdHelper.id(), "ComponentId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("component_data", ORB.init().create_alias_tc(ComponentDataHelper.id(), "ComponentData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TaggedComponent s2) {
        any.type(TaggedComponentHelper.type());
        TaggedComponentHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TaggedComponent extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TaggedComponent taggedComponent = TaggedComponentHelper.read(in);
            return taggedComponent;
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
        return "IDL:omg.org/IOP/TaggedComponent:1.0";
    }

    public static TaggedComponent read(InputStream in) {
        TaggedComponent result = new TaggedComponent();
        result.tag = in.read_ulong();
        result.component_data = OctetSeqHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, TaggedComponent s2) {
        out.write_ulong(s2.tag);
        OctetSeqHelper.write(out, s2.component_data);
    }
}

