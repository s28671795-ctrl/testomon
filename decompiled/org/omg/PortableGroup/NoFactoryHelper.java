/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.IstringHelper;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameComponentHelper;
import org.omg.PortableGroup.LocationHelper;
import org.omg.PortableGroup.NameHelper;
import org.omg.PortableGroup.NoFactory;
import org.omg.PortableGroup.TypeIdHelper;

public abstract class NoFactoryHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NoFactoryHelper> clazz = NoFactoryHelper.class;
        synchronized (NoFactoryHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(NoFactoryHelper.id(), "NoFactory", new StructMember[]{new StructMember("the_location", ORB.init().create_alias_tc(LocationHelper.id(), "Location", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_alias_tc(org.omg.CosNaming.NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)}))))), null), new StructMember("type_id", ORB.init().create_alias_tc(TypeIdHelper.id(), "TypeId", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NoFactory s2) {
        any.type(NoFactoryHelper.type());
        NoFactoryHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NoFactory extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NoFactory noFactory = NoFactoryHelper.read(in);
            return noFactory;
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
        return "IDL:omg.org/PortableGroup/NoFactory:1.0";
    }

    public static NoFactory read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(NoFactoryHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        NameComponent[] x0 = org.omg.CosNaming.NameHelper.read(in);
        String x1 = in.read_string();
        NoFactory result = new NoFactory(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, NoFactory s2) {
        out.write_string(NoFactoryHelper.id());
        org.omg.CosNaming.NameHelper.write(out, s2.the_location);
        String tmpResult3 = s2.type_id;
        out.write_string(tmpResult3);
    }
}

