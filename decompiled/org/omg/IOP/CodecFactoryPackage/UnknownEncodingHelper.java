/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP.CodecFactoryPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.CodecFactoryPackage.UnknownEncoding;

public abstract class UnknownEncodingHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<UnknownEncodingHelper> clazz = UnknownEncodingHelper.class;
        synchronized (UnknownEncodingHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(UnknownEncodingHelper.id(), "UnknownEncoding", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, UnknownEncoding s2) {
        any.type(UnknownEncodingHelper.type());
        UnknownEncodingHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UnknownEncoding extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            UnknownEncoding unknownEncoding = UnknownEncodingHelper.read(in);
            return unknownEncoding;
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
        return "IDL:omg.org/IOP/CodecFactory/UnknownEncoding:1.0";
    }

    public static UnknownEncoding read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(UnknownEncodingHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        UnknownEncoding result = new UnknownEncoding(id);
        return result;
    }

    public static void write(OutputStream out, UnknownEncoding s2) {
        out.write_string(UnknownEncodingHelper.id());
    }
}

