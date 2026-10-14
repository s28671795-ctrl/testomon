/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.GSS_NT_ExportedNameHelper;
import org.omg.CSI.IdentityTokenTypeHelper;
import org.omg.CSI.OIDHelper;
import org.omg.CSI.OIDListHelper;
import org.omg.CSIIOP.AS_ContextSecHelper;
import org.omg.CSIIOP.AssociationOptionsHelper;
import org.omg.CSIIOP.CompoundSecMech;
import org.omg.CSIIOP.SAS_ContextSecHelper;
import org.omg.CSIIOP.ServiceConfigurationHelper;
import org.omg.CSIIOP.ServiceConfigurationListHelper;
import org.omg.CSIIOP.ServiceConfigurationSyntaxHelper;
import org.omg.CSIIOP.ServiceSpecificNameHelper;
import org.omg.IOP.ComponentDataHelper;
import org.omg.IOP.ComponentIdHelper;
import org.omg.IOP.TaggedComponentHelper;

public abstract class CompoundSecMechHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CompoundSecMechHelper> clazz = CompoundSecMechHelper.class;
        synchronized (CompoundSecMechHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(CompoundSecMechHelper.id(), "CompoundSecMech", new StructMember[]{new StructMember("target_requires", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("transport_mech", ORB.init().create_struct_tc(TaggedComponentHelper.id(), "TaggedComponent", new StructMember[]{new StructMember("tag", ORB.init().create_alias_tc(ComponentIdHelper.id(), "ComponentId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("component_data", ORB.init().create_alias_tc(ComponentDataHelper.id(), "ComponentData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)}), null), new StructMember("as_context_mech", ORB.init().create_struct_tc(AS_ContextSecHelper.id(), "AS_ContextSec", new StructMember[]{new StructMember("target_supports", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("target_requires", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("client_authentication_mech", ORB.init().create_alias_tc(OIDHelper.id(), "OID", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null), new StructMember("target_name", ORB.init().create_alias_tc(GSS_NT_ExportedNameHelper.id(), "GSS_NT_ExportedName", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)}), null), new StructMember("sas_context_mech", ORB.init().create_struct_tc(SAS_ContextSecHelper.id(), "SAS_ContextSec", new StructMember[]{new StructMember("target_supports", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("target_requires", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("privilege_authorities", ORB.init().create_alias_tc(ServiceConfigurationListHelper.id(), "ServiceConfigurationList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceConfigurationHelper.id(), "ServiceConfiguration", new StructMember[]{new StructMember("syntax", ORB.init().create_alias_tc(ServiceConfigurationSyntaxHelper.id(), "ServiceConfigurationSyntax", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("name", ORB.init().create_alias_tc(ServiceSpecificNameHelper.id(), "ServiceSpecificName", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)}))), null), new StructMember("supported_naming_mechanisms", ORB.init().create_alias_tc(OIDListHelper.id(), "OIDList", ORB.init().create_sequence_tc(0, OIDHelper.type())), null), new StructMember("supported_identity_types", ORB.init().create_alias_tc(IdentityTokenTypeHelper.id(), "IdentityTokenType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null)}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CompoundSecMech s2) {
        any.type(CompoundSecMechHelper.type());
        CompoundSecMechHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CompoundSecMech extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CompoundSecMech compoundSecMech = CompoundSecMechHelper.read(in);
            return compoundSecMech;
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
        return "IDL:omg.org/CSIIOP/CompoundSecMech:1.0";
    }

    public static CompoundSecMech read(InputStream in) {
        CompoundSecMech result = new CompoundSecMech();
        result.target_requires = in.read_ushort();
        result.transport_mech = TaggedComponentHelper.read(in);
        result.as_context_mech = AS_ContextSecHelper.read(in);
        result.sas_context_mech = SAS_ContextSecHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, CompoundSecMech s2) {
        out.write_ushort(s2.target_requires);
        TaggedComponentHelper.write(out, s2.transport_mech);
        AS_ContextSecHelper.write(out, s2.as_context_mech);
        SAS_ContextSecHelper.write(out, s2.sas_context_mech);
    }
}

