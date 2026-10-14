/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class DefinitionKindHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DefinitionKindHelper> clazz = DefinitionKindHelper.class;
        synchronized (DefinitionKindHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(DefinitionKindHelper.id(), "DefinitionKind", new String[]{"dk_none", "dk_all", "dk_Attribute", "dk_Constant", "dk_Exception", "dk_Interface", "dk_Module", "dk_Operation", "dk_Typedef", "dk_Alias", "dk_Struct", "dk_Union", "dk_Enum", "dk_Primitive", "dk_String", "dk_Sequence", "dk_Array", "dk_Repository", "dk_Wstring", "dk_Fixed", "dk_Value", "dk_ValueBox", "dk_ValueMember", "dk_Native", "dk_AbstractInterface", "dk_LocalInterface", "dk_Component", "dk_Home", "dk_Factory", "dk_Finder", "dk_Emits", "dk_Publishes", "dk_Consumes", "dk_Provides", "dk_Uses", "dk_Event"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, DefinitionKind s2) {
        any.type(DefinitionKindHelper.type());
        DefinitionKindHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DefinitionKind extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            DefinitionKind definitionKind = DefinitionKindHelper.read(in);
            return definitionKind;
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
        return "IDL:omg.org/CORBA/DefinitionKind:1.0";
    }

    public static DefinitionKind read(InputStream in) {
        return DefinitionKind.from_int(in.read_long());
    }

    public static void write(OutputStream out, DefinitionKind s2) {
        out.write_long(s2.value());
    }
}

