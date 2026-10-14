/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.IdentityTokenTypeHelper;
import org.omg.CSI.OIDHelper;
import org.omg.CSI.OIDListHelper;
import org.omg.CSIIOP.AssociationOptionsHelper;
import org.omg.CSIIOP.SAS_ContextSec;
import org.omg.CSIIOP.ServiceConfigurationHelper;
import org.omg.CSIIOP.ServiceConfigurationListHelper;
import org.omg.CSIIOP.ServiceConfigurationSyntaxHelper;
import org.omg.CSIIOP.ServiceSpecificNameHelper;

public abstract class SAS_ContextSecHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SAS_ContextSecHelper> clazz = SAS_ContextSecHelper.class;
        synchronized (SAS_ContextSecHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(SAS_ContextSecHelper.id(), "SAS_ContextSec", new StructMember[]{new StructMember("target_supports", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("target_requires", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("privilege_authorities", ORB.init().create_alias_tc(ServiceConfigurationListHelper.id(), "ServiceConfigurationList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceConfigurationHelper.id(), "ServiceConfiguration", new StructMember[]{new StructMember("syntax", ORB.init().create_alias_tc(ServiceConfigurationSyntaxHelper.id(), "ServiceConfigurationSyntax", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("name", ORB.init().create_alias_tc(ServiceSpecificNameHelper.id(), "ServiceSpecificName", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)}))), null), new StructMember("supported_naming_mechanisms", ORB.init().create_alias_tc(OIDListHelper.id(), "OIDList", ORB.init().create_sequence_tc(0, OIDHelper.type())), null), new StructMember("supported_identity_types", ORB.init().create_alias_tc(IdentityTokenTypeHelper.id(), "IdentityTokenType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SAS_ContextSec s2) {
        any.type(SAS_ContextSecHelper.type());
        SAS_ContextSecHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SAS_ContextSec extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SAS_ContextSec sAS_ContextSec = SAS_ContextSecHelper.read(in);
            return sAS_ContextSec;
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
        return "IDL:omg.org/CSIIOP/SAS_ContextSec:1.0";
    }

    public static SAS_ContextSec read(InputStream in) {
        SAS_ContextSec result = new SAS_ContextSec();
        result.target_supports = in.read_ushort();
        result.target_requires = in.read_ushort();
        result.privilege_authorities = ServiceConfigurationListHelper.read(in);
        result.supported_naming_mechanisms = OIDListHelper.read(in);
        result.supported_identity_types = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, SAS_ContextSec s2) {
        out.write_ushort(s2.target_supports);
        out.write_ushort(s2.target_requires);
        ServiceConfigurationListHelper.write(out, s2.privilege_authorities);
        OIDListHelper.write(out, s2.supported_naming_mechanisms);
        out.write_ulong(s2.supported_identity_types);
    }
}

