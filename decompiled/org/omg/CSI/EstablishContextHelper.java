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
import org.omg.CSI.AuthorizationElementContentsHelper;
import org.omg.CSI.AuthorizationElementHelper;
import org.omg.CSI.AuthorizationElementTypeHelper;
import org.omg.CSI.AuthorizationTokenHelper;
import org.omg.CSI.ContextIdHelper;
import org.omg.CSI.EstablishContext;
import org.omg.CSI.GSSTokenHelper;
import org.omg.CSI.IdentityTokenHelper;

public abstract class EstablishContextHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<EstablishContextHelper> clazz = EstablishContextHelper.class;
        synchronized (EstablishContextHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(EstablishContextHelper.id(), "EstablishContext", new StructMember[]{new StructMember("client_context_id", ORB.init().create_alias_tc(ContextIdHelper.id(), "ContextId", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("authorization_token", ORB.init().create_alias_tc(AuthorizationTokenHelper.id(), "AuthorizationToken", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(AuthorizationElementHelper.id(), "AuthorizationElement", new StructMember[]{new StructMember("the_type", ORB.init().create_alias_tc(AuthorizationElementTypeHelper.id(), "AuthorizationElementType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("the_element", ORB.init().create_alias_tc(AuthorizationElementContentsHelper.id(), "AuthorizationElementContents", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)}))), null), new StructMember("identity_token", IdentityTokenHelper.type(), null), new StructMember("client_authentication_token", ORB.init().create_alias_tc(GSSTokenHelper.id(), "GSSToken", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, EstablishContext s2) {
        any.type(EstablishContextHelper.type());
        EstablishContextHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static EstablishContext extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            EstablishContext establishContext = EstablishContextHelper.read(in);
            return establishContext;
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
        return "IDL:omg.org/CSI/EstablishContext:1.0";
    }

    public static EstablishContext read(InputStream in) {
        EstablishContext result = new EstablishContext();
        result.client_context_id = in.read_ulonglong();
        result.authorization_token = AuthorizationTokenHelper.read(in);
        result.identity_token = IdentityTokenHelper.read(in);
        result.client_authentication_token = GSSTokenHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, EstablishContext s2) {
        out.write_ulonglong(s2.client_context_id);
        AuthorizationTokenHelper.write(out, s2.authorization_token);
        IdentityTokenHelper.write(out, s2.identity_token);
        GSSTokenHelper.write(out, s2.client_authentication_token);
    }
}

