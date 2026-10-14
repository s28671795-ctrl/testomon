/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.Encoding;
import org.omg.IOP.EncodingFormatHelper;

public abstract class EncodingHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<EncodingHelper> clazz = EncodingHelper.class;
        synchronized (EncodingHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(EncodingHelper.id(), "Encoding", new StructMember[]{new StructMember("format", ORB.init().create_alias_tc(EncodingFormatHelper.id(), "EncodingFormat", ORB.init().get_primitive_tc(TCKind.from_int(2))), null), new StructMember("major_version", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("minor_version", ORB.init().get_primitive_tc(TCKind.from_int(10)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Encoding s2) {
        any.type(EncodingHelper.type());
        EncodingHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Encoding extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            Encoding encoding = EncodingHelper.read(in);
            return encoding;
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
        return "IDL:omg.org/IOP/Encoding:1.0";
    }

    public static Encoding read(InputStream in) {
        Encoding result = new Encoding();
        result.format = in.read_short();
        result.major_version = in.read_octet();
        result.minor_version = in.read_octet();
        return result;
    }

    public static void write(OutputStream out, Encoding s2) {
        out.write_short(s2.format);
        out.write_octet(s2.major_version);
        out.write_octet(s2.minor_version);
    }
}

