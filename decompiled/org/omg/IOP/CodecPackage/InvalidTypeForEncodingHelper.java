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
import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;

public abstract class InvalidTypeForEncodingHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidTypeForEncodingHelper> clazz = InvalidTypeForEncodingHelper.class;
        synchronized (InvalidTypeForEncodingHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidTypeForEncodingHelper.id(), "InvalidTypeForEncoding", new StructMember[0]);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidTypeForEncoding s2) {
        any.type(InvalidTypeForEncodingHelper.type());
        InvalidTypeForEncodingHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidTypeForEncoding extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidTypeForEncoding invalidTypeForEncoding = InvalidTypeForEncodingHelper.read(in);
            return invalidTypeForEncoding;
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
        return "IDL:omg.org/IOP/Codec/InvalidTypeForEncoding:1.0";
    }

    public static InvalidTypeForEncoding read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidTypeForEncodingHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        InvalidTypeForEncoding result = new InvalidTypeForEncoding(id);
        return result;
    }

    public static void write(OutputStream out, InvalidTypeForEncoding s2) {
        out.write_string(InvalidTypeForEncodingHelper.id());
    }
}

