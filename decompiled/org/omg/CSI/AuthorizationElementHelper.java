/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.AuthorizationElement;
import org.omg.CSI.AuthorizationElementContentsHelper;
import org.omg.CSI.AuthorizationElementTypeHelper;

public abstract class AuthorizationElementHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuthorizationElementHelper> clazz = AuthorizationElementHelper.class;
        synchronized (AuthorizationElementHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(AuthorizationElementHelper.id(), "AuthorizationElement", new StructMember[]{new StructMember("the_type", ORB.init().create_alias_tc(AuthorizationElementTypeHelper.id(), "AuthorizationElementType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("the_element", ORB.init().create_alias_tc(AuthorizationElementContentsHelper.id(), "AuthorizationElementContents", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AuthorizationElement s2) {
        any.type(AuthorizationElementHelper.type());
        AuthorizationElementHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AuthorizationElement extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AuthorizationElement authorizationElement = AuthorizationElementHelper.read(in);
            return authorizationElement;
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
        return "IDL:omg.org/CSI/AuthorizationElement:1.0";
    }

    public static AuthorizationElement read(InputStream in) {
        AuthorizationElement result = new AuthorizationElement();
        result.the_type = in.read_ulong();
        result.the_element = AuthorizationElementContentsHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, AuthorizationElement s2) {
        out.write_ulong(s2.the_type);
        AuthorizationElementContentsHelper.write(out, s2.the_element);
    }
}

