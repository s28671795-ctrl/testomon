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
import org.omg.CSI.GSS_NT_ExportedNameHelper;
import org.omg.CSI.OIDHelper;
import org.omg.CSIIOP.AS_ContextSec;
import org.omg.CSIIOP.AssociationOptionsHelper;

public abstract class AS_ContextSecHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AS_ContextSecHelper> clazz = AS_ContextSecHelper.class;
        synchronized (AS_ContextSecHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(AS_ContextSecHelper.id(), "AS_ContextSec", new StructMember[]{new StructMember("target_supports", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("target_requires", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("client_authentication_mech", ORB.init().create_alias_tc(OIDHelper.id(), "OID", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null), new StructMember("target_name", ORB.init().create_alias_tc(GSS_NT_ExportedNameHelper.id(), "GSS_NT_ExportedName", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AS_ContextSec s2) {
        any.type(AS_ContextSecHelper.type());
        AS_ContextSecHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AS_ContextSec extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AS_ContextSec aS_ContextSec = AS_ContextSecHelper.read(in);
            return aS_ContextSec;
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
        return "IDL:omg.org/CSIIOP/AS_ContextSec:1.0";
    }

    public static AS_ContextSec read(InputStream in) {
        AS_ContextSec result = new AS_ContextSec();
        result.target_supports = in.read_ushort();
        result.target_requires = in.read_ushort();
        result.client_authentication_mech = OIDHelper.read(in);
        result.target_name = GSS_NT_ExportedNameHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, AS_ContextSec s2) {
        out.write_ushort(s2.target_supports);
        out.write_ushort(s2.target_requires);
        OIDHelper.write(out, s2.client_authentication_mech);
        GSS_NT_ExportedNameHelper.write(out, s2.target_name);
    }
}

