/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

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
import org.omg.CosNaming.NameHelper;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;

public abstract class CannotProceedHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CannotProceedHelper> clazz = CannotProceedHelper.class;
        synchronized (CannotProceedHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(CannotProceedHelper.id(), "CannotProceed", new StructMember[]{new StructMember("cxt", ORB.init().create_interface_tc("IDL:omg.org/CosNaming/NamingContext:1.0", "NamingContext"), null), new StructMember("rest_of_name", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CannotProceed s2) {
        any.type(CannotProceedHelper.type());
        CannotProceedHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CannotProceed extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CannotProceed cannotProceed = CannotProceedHelper.read(in);
            return cannotProceed;
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
        return "IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0";
    }

    public static CannotProceed read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(CannotProceedHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        NamingContext x0 = NamingContextHelper.read(in);
        NameComponent[] x1 = NameHelper.read(in);
        CannotProceed result = new CannotProceed(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, CannotProceed s2) {
        out.write_string(CannotProceedHelper.id());
        NamingContextHelper.write(out, s2.cxt);
        NameHelper.write(out, s2.rest_of_name);
    }
}

