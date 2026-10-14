/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP.CodecPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.CodecPackage.TypeMismatch;

public abstract class TypeMismatchHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<TypeMismatchHelper> clazz = TypeMismatchHelper.class;
        synchronized (TypeMismatchHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(TypeMismatchHelper.id(), "TypeMismatch", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, TypeMismatch s2) {
        any.type(TypeMismatchHelper.type());
        TypeMismatchHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TypeMismatch extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            TypeMismatch typeMismatch = TypeMismatchHelper.read(in);
            return typeMismatch;
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
        return "IDL:omg.org/IOP/Codec/TypeMismatch:1.0";
    }

    public static TypeMismatch read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(TypeMismatchHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        TypeMismatch result = new TypeMismatch(id);
        return result;
    }

    public static void write(OutputStream out, TypeMismatch s2) {
        out.write_string(TypeMismatchHelper.id());
    }
}

