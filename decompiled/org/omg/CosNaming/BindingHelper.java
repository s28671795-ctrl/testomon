/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingTypeHelper;
import org.omg.CosNaming.IstringHelper;
import org.omg.CosNaming.NameComponentHelper;
import org.omg.CosNaming.NameHelper;

public abstract class BindingHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BindingHelper> clazz = BindingHelper.class;
        synchronized (BindingHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(BindingHelper.id(), "Binding", new StructMember[]{new StructMember("binding_name", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)}))), null), new StructMember("binding_type", ORB.init().create_enum_tc(BindingTypeHelper.id(), "BindingType", new String[]{"nobject", "ncontext"}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Binding s2) {
        any.type(BindingHelper.type());
        BindingHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Binding extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            Binding binding = BindingHelper.read(in);
            return binding;
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
        return "IDL:omg.org/CosNaming/Binding:1.0";
    }

    public static Binding read(InputStream in) {
        Binding result = new Binding();
        result.binding_name = NameHelper.read(in);
        result.binding_type = BindingTypeHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, Binding s2) {
        NameHelper.write(out, s2.binding_name);
        BindingTypeHelper.write(out, s2.binding_type);
    }
}

