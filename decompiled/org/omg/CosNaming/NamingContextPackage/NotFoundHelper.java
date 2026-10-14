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
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;
import org.omg.CosNaming.NamingContextPackage.NotFoundReasonHelper;

public abstract class NotFoundHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NotFoundHelper> clazz = NotFoundHelper.class;
        synchronized (NotFoundHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(NotFoundHelper.id(), "NotFound", new StructMember[]{new StructMember("why", ORB.init().create_enum_tc(NotFoundReasonHelper.id(), "NotFoundReason", new String[]{"missing_node", "not_context", "not_object"}), null), new StructMember("rest_of_name", ORB.init().create_alias_tc(NameHelper.id(), "Name", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NotFound s2) {
        any.type(NotFoundHelper.type());
        NotFoundHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NotFound extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NotFound notFound = NotFoundHelper.read(in);
            return notFound;
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
        return "IDL:omg.org/CosNaming/NamingContext/NotFound:1.0";
    }

    public static NotFound read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(NotFoundHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        NotFoundReason x0 = NotFoundReasonHelper.read(in);
        NameComponent[] x1 = NameHelper.read(in);
        NotFound result = new NotFound(id, x0, x1);
        return result;
    }

    public static void write(OutputStream out, NotFound s2) {
        out.write_string(NotFoundHelper.id());
        NotFoundReasonHelper.write(out, s2.why);
        NameHelper.write(out, s2.rest_of_name);
    }
}

