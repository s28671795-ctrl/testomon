/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class DefinitionKindHelper {
    private static String _id = "IDL:omg.org/CORBA/DefinitionKind:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, DefinitionKind that) {
        OutputStream out = a.create_output_stream();
        a.type(DefinitionKindHelper.type());
        DefinitionKindHelper.write(out, that);
        a.read_value(out.create_input_stream(), DefinitionKindHelper.type());
    }

    public static DefinitionKind extract(Any a) {
        return DefinitionKindHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            __typeCode = ORB.init().create_enum_tc(DefinitionKindHelper.id(), "DefinitionKind", new String[]{"dk_none", "dk_all", "dk_Attribute", "dk_Constant", "dk_Exception", "dk_Interface", "dk_Module", "dk_Operation", "dk_Typedef", "dk_Alias", "dk_Struct", "dk_Union", "dk_Enum", "dk_Primitive", "dk_String", "dk_Sequence", "dk_Array", "dk_Repository", "dk_Wstring", "dk_Fixed", "dk_Value", "dk_ValueBox", "dk_ValueMember", "dk_Native"});
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static DefinitionKind read(InputStream istream) {
        return DefinitionKind.from_int(istream.read_long());
    }

    public static void write(OutputStream ostream, DefinitionKind value) {
        ostream.write_long(value.value());
    }
}

