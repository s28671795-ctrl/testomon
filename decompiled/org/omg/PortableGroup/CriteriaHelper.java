/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.IstringHelper;
import org.omg.CosNaming.NameComponentHelper;
import org.omg.PortableGroup.NameHelper;
import org.omg.PortableGroup.PropertiesHelper;
import org.omg.PortableGroup.Property;
import org.omg.PortableGroup.PropertyHelper;
import org.omg.PortableGroup.ValueHelper;

public abstract class CriteriaHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Property[] s2) {
        any.type(CriteriaHelper.type());
        CriteriaHelper.write(any.create_output_stream(), s2);
    }

    public static Property[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return CriteriaHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CriteriaHelper> clazz = CriteriaHelper.class;
        synchronized (CriteriaHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(CriteriaHelper.id(), "Criteria", ORB.init().create_alias_tc(PropertiesHelper.id(), "Properties", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(PropertyHelper.id(), "Property", new StructMember[]{new StructMember("nam", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)})))), null), new StructMember("val", ORB.init().create_alias_tc(ValueHelper.id(), "Value", ORB.init().get_primitive_tc(TCKind.from_int(11))), null)}))));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/Criteria:1.0";
    }

    public static Property[] read(InputStream _in) {
        Property[] _result = PropertiesHelper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, Property[] _s) {
        PropertiesHelper.write(_out, _s);
    }
}

