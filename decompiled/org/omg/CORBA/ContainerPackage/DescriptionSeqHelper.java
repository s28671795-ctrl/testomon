/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ContainerPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ContainerPackage.Description;
import org.omg.CORBA.ContainerPackage.DescriptionHelper;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class DescriptionSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Description[] s2) {
        any.type(DescriptionSeqHelper.type());
        DescriptionSeqHelper.write(any.create_output_stream(), s2);
    }

    public static Description[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return DescriptionSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<DescriptionSeqHelper> clazz = DescriptionSeqHelper.class;
        synchronized (DescriptionSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(DescriptionSeqHelper.id(), "DescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(DescriptionHelper.id(), "Description", new StructMember[]{new StructMember("contained_object", ORB.init().create_interface_tc("IDL:omg.org/CORBA/Contained:1.0", "Contained"), null), new StructMember("kind", ORB.init().create_enum_tc(DefinitionKindHelper.id(), "DefinitionKind", new String[]{"dk_none", "dk_all", "dk_Attribute", "dk_Constant", "dk_Exception", "dk_Interface", "dk_Module", "dk_Operation", "dk_Typedef", "dk_Alias", "dk_Struct", "dk_Union", "dk_Enum", "dk_Primitive", "dk_String", "dk_Sequence", "dk_Array", "dk_Repository", "dk_Wstring", "dk_Fixed", "dk_Value", "dk_ValueBox", "dk_ValueMember", "dk_Native", "dk_AbstractInterface", "dk_LocalInterface", "dk_Component", "dk_Home", "dk_Factory", "dk_Finder", "dk_Emits", "dk_Publishes", "dk_Consumes", "dk_Provides", "dk_Uses", "dk_Event"}), null), new StructMember("value", ORB.init().get_primitive_tc(TCKind.from_int(11)), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/Container/DescriptionSeq:1.0";
    }

    public static Description[] read(InputStream _in) {
        int _l_result19 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result19 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result19);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        Description[] _result = new Description[_l_result19];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = DescriptionHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, Description[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            DescriptionHelper.write(_out, _s[i]);
        }
    }
}

