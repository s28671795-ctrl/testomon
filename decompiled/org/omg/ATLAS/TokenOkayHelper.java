/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.io.IOException;
import org.omg.ATLAS.TokenOkay;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class TokenOkayHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TokenOkayHelper> clazz = TokenOkayHelper.class;
        synchronized (TokenOkayHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(TokenOkayHelper.id(), "TokenOkay", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TokenOkay s2) {
        any.type(TokenOkayHelper.type());
        TokenOkayHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TokenOkay extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TokenOkay tokenOkay = TokenOkayHelper.read(in);
            return tokenOkay;
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
        return "IDL:omg.org/ATLAS/TokenOkay:1.0";
    }

    public static TokenOkay read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(TokenOkayHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        TokenOkay result = new TokenOkay(id);
        return result;
    }

    public static void write(OutputStream out, TokenOkay s2) {
        out.write_string(TokenOkayHelper.id());
    }
}

