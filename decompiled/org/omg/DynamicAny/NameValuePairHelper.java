/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.DynamicAny.FieldNameHelper;
import org.omg.DynamicAny.NameValuePair;

public abstract class NameValuePairHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NameValuePairHelper> clazz = NameValuePairHelper.class;
        synchronized (NameValuePairHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(NameValuePairHelper.id(), "NameValuePair", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(FieldNameHelper.id(), "FieldName", ORB.init().create_string_tc(0)), null), new StructMember("value", ORB.init().get_primitive_tc(TCKind.from_int(11)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NameValuePair s2) {
        any.type(NameValuePairHelper.type());
        NameValuePairHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NameValuePair extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NameValuePair nameValuePair = NameValuePairHelper.read(in);
            return nameValuePair;
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
        return "IDL:omg.org/DynamicAny/NameValuePair:1.0";
    }

    public static NameValuePair read(InputStream in) {
        NameValuePair result = new NameValuePair();
        result.id = in.read_string();
        result.value = in.read_any();
        return result;
    }

    public static void write(OutputStream out, NameValuePair s2) {
        String tmpResult1 = s2.id;
        out.write_string(tmpResult1);
        out.write_any(s2.value);
    }
}

