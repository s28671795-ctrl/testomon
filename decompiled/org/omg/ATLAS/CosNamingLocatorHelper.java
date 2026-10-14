/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.io.IOException;
import org.omg.ATLAS.CosNamingLocator;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.IstringHelper;
import org.omg.CosNaming.NameComponentHelper;
import org.omg.CosNaming.NameHelper;
import org.omg.CosNaming.NamingContextHelper;

public abstract class CosNamingLocatorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CosNamingLocatorHelper> clazz = CosNamingLocatorHelper.class;
        synchronized (CosNamingLocatorHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(CosNamingLocatorHelper.id(), "CosNamingLocator", new StructMember[]{new StructMember("name_service", ORB.init().create_interface_tc("IDL:omg.org/CosNaming/NamingContext:1.0", "NamingContext"), null), new StructMember("the_name", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CosNamingLocator s2) {
        any.type(CosNamingLocatorHelper.type());
        CosNamingLocatorHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CosNamingLocator extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CosNamingLocator cosNamingLocator = CosNamingLocatorHelper.read(in);
            return cosNamingLocator;
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
        return "IDL:omg.org/ATLAS/CosNamingLocator:1.0";
    }

    public static CosNamingLocator read(InputStream in) {
        CosNamingLocator result = new CosNamingLocator();
        result.name_service = NamingContextHelper.read(in);
        result.the_name = NameHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, CosNamingLocator s2) {
        NamingContextHelper.write(out, s2.name_service);
        NameHelper.write(out, s2.the_name);
    }
}

