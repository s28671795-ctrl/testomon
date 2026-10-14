/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnknownUserException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class UnknownUserExceptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<UnknownUserExceptionHelper> clazz = UnknownUserExceptionHelper.class;
        synchronized (UnknownUserExceptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(UnknownUserExceptionHelper.id(), "UnknownUserException", new StructMember[]{new StructMember("except", ORB.init().get_primitive_tc(TCKind.from_int(11)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, UnknownUserException s2) {
        any.type(UnknownUserExceptionHelper.type());
        UnknownUserExceptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UnknownUserException extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            UnknownUserException unknownUserException = UnknownUserExceptionHelper.read(in);
            return unknownUserException;
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
        return "IDL:omg.org/CORBA/UnknownUserException:1.0";
    }

    public static UnknownUserException read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(UnknownUserExceptionHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        Any x0 = in.read_any();
        UnknownUserException result = new UnknownUserException(id, x0);
        return result;
    }

    public static void write(OutputStream out, UnknownUserException s2) {
        out.write_string(UnknownUserExceptionHelper.id());
        out.write_any(s2.except);
    }
}

