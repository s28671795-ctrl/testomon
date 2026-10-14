/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
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
import org.omg.IOP.TaggedComponentHelper;

public abstract class TaggedComponentSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, TaggedComponent[] s2) {
        any.type(TaggedComponentSeqHelper.type());
        TaggedComponentSeqHelper.write(any.create_output_stream(), s2);
    }

    public static TaggedComponent[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return TaggedComponentSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TaggedComponentSeqHelper> clazz = TaggedComponentSeqHelper.class;
        synchronized (TaggedComponentSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(TaggedComponentSeqHelper.id(), "TaggedComponentSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(TaggedComponentHelper.id(), "TaggedComponent", new StructMember[]{new StructMember("tag", ORB.init().create_alias_tc(ComponentIdHelper.id(), "ComponentId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("component_data", ORB.init().create_alias_tc(ComponentDataHelper.id(), "ComponentData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/IOP/TaggedComponentSeq:1.0";
    }

    public static TaggedComponent[] read(InputStream _in) {
        int _l_result3 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result3 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result3);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        TaggedComponent[] _result = new TaggedComponent[_l_result3];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = TaggedComponentHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, TaggedComponent[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            TaggedComponentHelper.write(_out, _s[i]);
        }
    }
}

