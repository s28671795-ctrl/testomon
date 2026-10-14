/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.IstringHelper;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameComponentHelper;
import org.omg.PortableGroup.NameHelper;
import org.omg.PortableGroup.UnsupportedProperty;

public abstract class UnsupportedPropertyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<UnsupportedPropertyHelper> clazz = UnsupportedPropertyHelper.class;
        synchronized (UnsupportedPropertyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(UnsupportedPropertyHelper.id(), "UnsupportedProperty", new StructMember[]{new StructMember("nam", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)})))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, UnsupportedProperty s2) {
        any.type(UnsupportedPropertyHelper.type());
        UnsupportedPropertyHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UnsupportedProperty extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            UnsupportedProperty unsupportedProperty = UnsupportedPropertyHelper.read(in);
            return unsupportedProperty;
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
        return "IDL:omg.org/PortableGroup/UnsupportedProperty:1.0";
    }

    public static UnsupportedProperty read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(UnsupportedPropertyHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        NameComponent[] x0 = org.omg.CosNaming.NameHelper.read(in);
        UnsupportedProperty result = new UnsupportedProperty(id, x0);
        return result;
    }

    public static void write(OutputStream out, UnsupportedProperty s2) {
        out.write_string(UnsupportedPropertyHelper.id());
        org.omg.CosNaming.NameHelper.write(out, s2.nam);
    }
}

