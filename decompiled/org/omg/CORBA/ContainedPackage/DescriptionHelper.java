/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ContainedPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ContainedPackage.Description;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class DescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DescriptionHelper> clazz = DescriptionHelper.class;
        synchronized (DescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(DescriptionHelper.id(), "Description", new StructMember[]{new StructMember("kind", ORB.init().create_enum_tc(DefinitionKindHelper.id(), "DefinitionKind", new String[]{"dk_none", "dk_all", "dk_Attribute", "dk_Constant", "dk_Exception", "dk_Interface", "dk_Module", "dk_Operation", "dk_Typedef", "dk_Alias", "dk_Struct", "dk_Union", "dk_Enum", "dk_Primitive", "dk_String", "dk_Sequence", "dk_Array", "dk_Repository", "dk_Wstring", "dk_Fixed", "dk_Value", "dk_ValueBox", "dk_ValueMember", "dk_Native", "dk_AbstractInterface", "dk_LocalInterface", "dk_Component", "dk_Home", "dk_Factory", "dk_Finder", "dk_Emits", "dk_Publishes", "dk_Consumes", "dk_Provides", "dk_Uses", "dk_Event"}), null), new StructMember("value", ORB.init().get_primitive_tc(TCKind.from_int(11)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Description s2) {
        any.type(DescriptionHelper.type());
        DescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Description extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            Description description = DescriptionHelper.read(in);
            return description;
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
        return "IDL:omg.org/CORBA/Contained/Description:1.0";
    }

    public static Description read(InputStream in) {
        Description result = new Description();
        result.kind = DefinitionKindHelper.read(in);
        result.value = in.read_any();
        return result;
    }

    public static void write(OutputStream out, Description s2) {
        DefinitionKindHelper.write(out, s2.kind);
        out.write_any(s2.value);
    }
}

