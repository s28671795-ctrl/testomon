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
import org.omg.PortableGroup.CriteriaHelper;
import org.omg.PortableGroup.FactoryInfo;
import org.omg.PortableGroup.FactoryInfoHelper;
import org.omg.PortableGroup.LocationHelper;
import org.omg.PortableGroup.NameHelper;
import org.omg.PortableGroup.PropertiesHelper;
import org.omg.PortableGroup.PropertyHelper;
import org.omg.PortableGroup.ValueHelper;

public abstract class FactoryInfosHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, FactoryInfo[] s2) {
        any.type(FactoryInfosHelper.type());
        FactoryInfosHelper.write(any.create_output_stream(), s2);
    }

    public static FactoryInfo[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return FactoryInfosHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<FactoryInfosHelper> clazz = FactoryInfosHelper.class;
        synchronized (FactoryInfosHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(FactoryInfosHelper.id(), "FactoryInfos", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(FactoryInfoHelper.id(), "FactoryInfo", new StructMember[]{new StructMember("the_factory", ORB.init().create_interface_tc("IDL:omg.org/PortableGroup/GenericFactory:1.0", "GenericFactory"), null), new StructMember("the_location", ORB.init().create_alias_tc(LocationHelper.id(), "Location", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)}))))), null), new StructMember("the_criteria", ORB.init().create_alias_tc(CriteriaHelper.id(), "Criteria", ORB.init().create_alias_tc(PropertiesHelper.id(), "Properties", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(PropertyHelper.id(), "Property", new StructMember[]{new StructMember("nam", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)})))), null), new StructMember("val", ORB.init().create_alias_tc(ValueHelper.id(), "Value", ORB.init().get_primitive_tc(TCKind.from_int(11))), null)})))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/FactoryInfos:1.0";
    }

    public static FactoryInfo[] read(InputStream _in) {
        int _l_result3 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result3 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result3);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        FactoryInfo[] _result = new FactoryInfo[_l_result3];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = FactoryInfoHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, FactoryInfo[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            FactoryInfoHelper.write(_out, _s[i]);
        }
    }
}

