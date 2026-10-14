/*
 * Decompiled with CFR 0.152.
 */
package org.omg.MIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
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
import org.omg.MIOP.AddressHelper;
import org.omg.MIOP.UIPMC_ProfileBody;
import org.omg.MIOP.VersionHelper;

public abstract class UIPMC_ProfileBodyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<UIPMC_ProfileBodyHelper> clazz = UIPMC_ProfileBodyHelper.class;
        synchronized (UIPMC_ProfileBodyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(UIPMC_ProfileBodyHelper.id(), "UIPMC_ProfileBody", new StructMember[]{new StructMember("miop_version", ORB.init().create_alias_tc(VersionHelper.id(), "Version", ORB.init().create_struct_tc(org.omg.GIOP.VersionHelper.id(), "Version", new StructMember[]{new StructMember("major", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(10)), null)})), null), new StructMember("the_address", ORB.init().create_alias_tc(AddressHelper.id(), "Address", ORB.init().create_string_tc(0)), null), new StructMember("the_port", ORB.init().get_primitive_tc(TCKind.from_int(2)), null), new StructMember("components", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(TaggedComponentHelper.id(), "TaggedComponent", new StructMember[]{new StructMember("tag", ORB.init().create_alias_tc(ComponentIdHelper.id(), "ComponentId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("component_data", ORB.init().create_alias_tc(ComponentDataHelper.id(), "ComponentData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)})), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, UIPMC_ProfileBody s2) {
        any.type(UIPMC_ProfileBodyHelper.type());
        UIPMC_ProfileBodyHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UIPMC_ProfileBody extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            UIPMC_ProfileBody uIPMC_ProfileBody = UIPMC_ProfileBodyHelper.read(in);
            return uIPMC_ProfileBody;
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
        return "IDL:omg.org/MIOP/UIPMC_ProfileBody:1.0";
    }

    public static UIPMC_ProfileBody read(InputStream in) {
        UIPMC_ProfileBody result = new UIPMC_ProfileBody();
        result.miop_version = org.omg.GIOP.VersionHelper.read(in);
        result.the_address = in.read_string();
        result.the_port = in.read_short();
        int _lresult_components1 = in.read_long();
        try {
            int x = in.available();
            if (x > 0 && _lresult_components1 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _lresult_components1);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        result.components = new TaggedComponent[_lresult_components1];
        for (int i = 0; i < result.components.length; ++i) {
            result.components[i] = TaggedComponentHelper.read(in);
        }
        return result;
    }

    public static void write(OutputStream out, UIPMC_ProfileBody s2) {
        org.omg.GIOP.VersionHelper.write(out, s2.miop_version);
        String tmpResult1 = s2.the_address;
        out.write_string(tmpResult1);
        out.write_short(s2.the_port);
        out.write_long(s2.components.length);
        for (int i = 0; i < s2.components.length; ++i) {
            TaggedComponentHelper.write(out, s2.components[i]);
        }
    }
}

