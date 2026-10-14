/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.io.IOException;
import org.omg.ATLAS.AuthTokenData;
import org.omg.ATLAS.ExpiryTimeHelper;
import org.omg.ATLAS.IdTokenOptionHelper;
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
import org.omg.CSI.IdentityTokenHelper;
import org.omg.TimeBase.TdfTHelper;
import org.omg.TimeBase.TimeTHelper;
import org.omg.TimeBase.UtcTHelper;

public abstract class AuthTokenDataHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuthTokenDataHelper> clazz = AuthTokenDataHelper.class;
        synchronized (AuthTokenDataHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(AuthTokenDataHelper.id(), "AuthTokenData", new StructMember[]{new StructMember("ident_token", ORB.init().create_alias_tc(IdTokenOptionHelper.id(), "IdTokenOption", ORB.init().create_sequence_tc(1, IdentityTokenHelper.type())), null), new StructMember("auth_token", ORB.init().create_alias_tc(AuthorizationTokenHelper.id(), "AuthorizationToken", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(AuthorizationElementHelper.id(), "AuthorizationElement", new StructMember[]{new StructMember("the_type", ORB.init().create_alias_tc(AuthorizationElementTypeHelper.id(), "AuthorizationElementType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("the_element", ORB.init().create_alias_tc(AuthorizationElementContentsHelper.id(), "AuthorizationElementContents", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)}))), null), new StructMember("expiry_time", ORB.init().create_alias_tc(ExpiryTimeHelper.id(), "ExpiryTime", ORB.init().create_sequence_tc(1, ORB.init().create_struct_tc(UtcTHelper.id(), "UtcT", new StructMember[]{new StructMember("time", ORB.init().create_alias_tc(TimeTHelper.id(), "TimeT", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("inacclo", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("inacchi", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("tdf", ORB.init().create_alias_tc(TdfTHelper.id(), "TdfT", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AuthTokenData s2) {
        any.type(AuthTokenDataHelper.type());
        AuthTokenDataHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AuthTokenData extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AuthTokenData authTokenData = AuthTokenDataHelper.read(in);
            return authTokenData;
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
        return "IDL:omg.org/ATLAS/AuthTokenData:1.0";
    }

    public static AuthTokenData read(InputStream in) {
        AuthTokenData result = new AuthTokenData();
        result.ident_token = IdTokenOptionHelper.read(in);
        result.auth_token = AuthorizationTokenHelper.read(in);
        result.expiry_time = ExpiryTimeHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, AuthTokenData s2) {
        IdTokenOptionHelper.write(out, s2.ident_token);
        AuthorizationTokenHelper.write(out, s2.auth_token);
        ExpiryTimeHelper.write(out, s2.expiry_time);
    }
}

