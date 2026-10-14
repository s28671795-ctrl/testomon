/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.AssociationOptionsHelper;
import org.omg.Security.MechanismTypeHelper;
import org.omg.Security.SecurityMechanismData;
import org.omg.Security.SecurityNameHelper;

public abstract class SecurityMechanismDataHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SecurityMechanismDataHelper> clazz = SecurityMechanismDataHelper.class;
        synchronized (SecurityMechanismDataHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(SecurityMechanismDataHelper.id(), "SecurityMechanismData", new StructMember[]{new StructMember("mechanism", ORB.init().create_alias_tc(MechanismTypeHelper.id(), "MechanismType", ORB.init().create_string_tc(0)), null), new StructMember("security_name", ORB.init().create_alias_tc(SecurityNameHelper.id(), "SecurityName", ORB.init().create_string_tc(0)), null), new StructMember("options_supported", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("options_required", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SecurityMechanismData s2) {
        any.type(SecurityMechanismDataHelper.type());
        SecurityMechanismDataHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SecurityMechanismData extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SecurityMechanismData securityMechanismData = SecurityMechanismDataHelper.read(in);
            return securityMechanismData;
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
        return "IDL:omg.org/Security/SecurityMechanismData:1.0";
    }

    public static SecurityMechanismData read(InputStream in) {
        SecurityMechanismData result = new SecurityMechanismData();
        result.mechanism = in.read_string();
        result.security_name = in.read_string();
        result.options_supported = in.read_ushort();
        result.options_required = in.read_ushort();
        return result;
    }

    public static void write(OutputStream out, SecurityMechanismData s2) {
        String tmpResult4 = s2.mechanism;
        out.write_string(tmpResult4);
        String tmpResult5 = s2.security_name;
        out.write_string(tmpResult5);
        out.write_ushort(s2.options_supported);
        out.write_ushort(s2.options_required);
    }
}

