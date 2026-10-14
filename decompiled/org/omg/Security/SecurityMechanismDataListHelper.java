/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.AssociationOptionsHelper;
import org.omg.Security.MechanismTypeHelper;
import org.omg.Security.SecurityMechanismData;
import org.omg.Security.SecurityMechanismDataHelper;
import org.omg.Security.SecurityNameHelper;

public abstract class SecurityMechanismDataListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, SecurityMechanismData[] s2) {
        any.type(SecurityMechanismDataListHelper.type());
        SecurityMechanismDataListHelper.write(any.create_output_stream(), s2);
    }

    public static SecurityMechanismData[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return SecurityMechanismDataListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SecurityMechanismDataListHelper> clazz = SecurityMechanismDataListHelper.class;
        synchronized (SecurityMechanismDataListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(SecurityMechanismDataListHelper.id(), "SecurityMechanismDataList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(SecurityMechanismDataHelper.id(), "SecurityMechanismData", new StructMember[]{new StructMember("mechanism", ORB.init().create_alias_tc(MechanismTypeHelper.id(), "MechanismType", ORB.init().create_string_tc(0)), null), new StructMember("security_name", ORB.init().create_alias_tc(SecurityNameHelper.id(), "SecurityName", ORB.init().create_string_tc(0)), null), new StructMember("options_supported", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null), new StructMember("options_required", ORB.init().create_alias_tc(AssociationOptionsHelper.id(), "AssociationOptions", ORB.init().get_primitive_tc(TCKind.from_int(4))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Security/SecurityMechanismDataList:1.0";
    }

    public static SecurityMechanismData[] read(InputStream _in) {
        int _l_result2 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result2 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result2);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        SecurityMechanismData[] _result = new SecurityMechanismData[_l_result2];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = SecurityMechanismDataHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, SecurityMechanismData[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            SecurityMechanismDataHelper.write(_out, _s[i]);
        }
    }
}

