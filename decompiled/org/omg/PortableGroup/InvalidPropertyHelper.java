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
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameComponentHelper;
import org.omg.PortableGroup.InvalidProperty;
import org.omg.PortableGroup.NameHelper;
import org.omg.PortableGroup.ValueHelper;

public abstract class InvalidPropertyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidPropertyHelper> clazz = InvalidPropertyHelper.class;
        synchronized (InvalidPropertyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidPropertyHelper.id(), "InvalidProperty", new StructMember[]{new StructMember("nam", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)})))), null), new StructMember("val", ORB.init().create_alias_tc(ValueHelper.id(), "Value", ORB.init().get_primitive_tc(TCKind.from_int(11))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidProperty s2) {
        any.type(InvalidPropertyHelper.type());
        InvalidPropertyHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidProperty extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidProperty invalidProperty = InvalidPropertyHelper.read(in);
            return invalidProperty;
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
        return "IDL:omg.org/PortableGroup/InvalidProperty:1.0";
    }

    public static InvalidProperty read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidPropertyHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        NameComponent[] x0 = org.omg.CosNaming.NameHelper.read(in);
        Any x1 = in.read_any();
        InvalidProperty result = new InvalidProperty(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, InvalidProperty s2) {
        out.write_string(InvalidPropertyHelper.id());
        org.omg.CosNaming.NameHelper.write(out, s2.nam);
        out.write_any(s2.val);
    }
}

