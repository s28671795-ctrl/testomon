/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.ParameterModeHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ParameterDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ParameterDescriptionHelper> clazz = ParameterDescriptionHelper.class;
        synchronized (ParameterDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ParameterDescriptionHelper.id(), "ParameterDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null), new StructMember("mode", ORB.init().create_enum_tc(ParameterModeHelper.id(), "ParameterMode", new String[]{"PARAM_IN", "PARAM_OUT", "PARAM_INOUT"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ParameterDescription s2) {
        any.type(ParameterDescriptionHelper.type());
        ParameterDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ParameterDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ParameterDescription parameterDescription = ParameterDescriptionHelper.read(in);
            return parameterDescription;
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
        return "IDL:omg.org/CORBA/ParameterDescription:1.0";
    }

    public static ParameterDescription read(InputStream in) {
        ParameterDescription result = new ParameterDescription();
        result.name = in.read_string();
        result.type = in.read_TypeCode();
        result.type_def = IDLTypeHelper.read(in);
        result.mode = ParameterModeHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, ParameterDescription s2) {
        String tmpResult406 = s2.name;
        out.write_string(tmpResult406);
        out.write_TypeCode(s2.type);
        IDLTypeHelper.write(out, s2.type_def);
        ParameterModeHelper.write(out, s2.mode);
    }
}

