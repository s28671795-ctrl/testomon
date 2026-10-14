/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.Any;
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
import org.omg.PortableGroup.GenericFactoryHelper;
import org.omg.PortableGroup.LocationHelper;
import org.omg.PortableGroup.NameHelper;
import org.omg.PortableGroup.PropertiesHelper;
import org.omg.PortableGroup.PropertyHelper;
import org.omg.PortableGroup.ValueHelper;

public abstract class FactoryInfoHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<FactoryInfoHelper> clazz = FactoryInfoHelper.class;
        synchronized (FactoryInfoHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(FactoryInfoHelper.id(), "FactoryInfo", new StructMember[]{new StructMember("the_factory", ORB.init().create_interface_tc("IDL:omg.org/PortableGroup/GenericFactory:1.0", "GenericFactory"), null), new StructMember("the_location", ORB.init().create_alias_tc(LocationHelper.id(), "Location", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)}))))), null), new StructMember("the_criteria", ORB.init().create_alias_tc(CriteriaHelper.id(), "Criteria", ORB.init().create_alias_tc(PropertiesHelper.id(), "Properties", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(PropertyHelper.id(), "Property", new StructMember[]{new StructMember("nam", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)})))), null), new StructMember("val", ORB.init().create_alias_tc(ValueHelper.id(), "Value", ORB.init().get_primitive_tc(TCKind.from_int(11))), null)})))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, FactoryInfo s2) {
        any.type(FactoryInfoHelper.type());
        FactoryInfoHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static FactoryInfo extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            FactoryInfo factoryInfo = FactoryInfoHelper.read(in);
            return factoryInfo;
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
        return "IDL:omg.org/PortableGroup/FactoryInfo:1.0";
    }

    public static FactoryInfo read(InputStream in) {
        FactoryInfo result = new FactoryInfo();
        result.the_factory = GenericFactoryHelper.read(in);
        result.the_location = org.omg.CosNaming.NameHelper.read(in);
        result.the_criteria = PropertiesHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, FactoryInfo s2) {
        GenericFactoryHelper.write(out, s2.the_factory);
        org.omg.CosNaming.NameHelper.write(out, s2.the_location);
        PropertiesHelper.write(out, s2.the_criteria);
    }
}

