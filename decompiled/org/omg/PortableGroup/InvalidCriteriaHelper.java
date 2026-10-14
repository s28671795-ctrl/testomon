/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.Any;
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
import org.omg.PortableGroup.InvalidCriteria;
import org.omg.PortableGroup.NameHelper;
import org.omg.PortableGroup.PropertiesHelper;
import org.omg.PortableGroup.Property;
import org.omg.PortableGroup.PropertyHelper;
import org.omg.PortableGroup.ValueHelper;

public abstract class InvalidCriteriaHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidCriteriaHelper> clazz = InvalidCriteriaHelper.class;
        synchronized (InvalidCriteriaHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidCriteriaHelper.id(), "InvalidCriteria", new StructMember[]{new StructMember("invalid_criteria", ORB.init().create_alias_tc(CriteriaHelper.id(), "Criteria", ORB.init().create_alias_tc(PropertiesHelper.id(), "Properties", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(PropertyHelper.id(), "Property", new StructMember[]{new StructMember("nam", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)})))), null), new StructMember("val", ORB.init().create_alias_tc(ValueHelper.id(), "Value", ORB.init().get_primitive_tc(TCKind.from_int(11))), null)})))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidCriteria s2) {
        any.type(InvalidCriteriaHelper.type());
        InvalidCriteriaHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidCriteria extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidCriteria invalidCriteria = InvalidCriteriaHelper.read(in);
            return invalidCriteria;
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
        return "IDL:omg.org/PortableGroup/InvalidCriteria:1.0";
    }

    public static InvalidCriteria read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidCriteriaHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        Property[] x0 = PropertiesHelper.read(in);
        InvalidCriteria result = new InvalidCriteria(id, x0);
        return result;
    }

    public static void write(OutputStream out, InvalidCriteria s2) {
        out.write_string(InvalidCriteriaHelper.id());
        PropertiesHelper.write(out, s2.invalid_criteria);
    }
}

