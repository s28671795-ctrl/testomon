/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

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
import org.omg.CosNaming.IstringHelper;
import org.omg.CosNaming.NameComponentHelper;
import org.omg.PortableGroup.NameHelper;
import org.omg.PortableGroup.Property;
import org.omg.PortableGroup.PropertyHelper;
import org.omg.PortableGroup.ValueHelper;

public abstract class PropertiesHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, Property[] s2) {
        any.type(PropertiesHelper.type());
        PropertiesHelper.write(any.create_output_stream(), s2);
    }

    public static Property[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return PropertiesHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PropertiesHelper> clazz = PropertiesHelper.class;
        synchronized (PropertiesHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(PropertiesHelper.id(), "Properties", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(PropertyHelper.id(), "Property", new StructMember[]{new StructMember("nam", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)})))), null), new StructMember("val", ORB.init().create_alias_tc(ValueHelper.id(), "Value", ORB.init().get_primitive_tc(TCKind.from_int(11))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/Properties:1.0";
    }

    public static Property[] read(InputStream _in) {
        int _l_result1 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result1 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result1);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        Property[] _result = new Property[_l_result1];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = PropertyHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, Property[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            PropertyHelper.write(_out, _s[i]);
        }
    }
}

