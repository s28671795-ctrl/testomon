/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.DynamicAny.DynAnyHelper;
import org.omg.DynamicAny.FieldNameHelper;
import org.omg.DynamicAny.NameDynAnyPair;

public abstract class NameDynAnyPairHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NameDynAnyPairHelper> clazz = NameDynAnyPairHelper.class;
        synchronized (NameDynAnyPairHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(NameDynAnyPairHelper.id(), "NameDynAnyPair", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(FieldNameHelper.id(), "FieldName", ORB.init().create_string_tc(0)), null), new StructMember("value", ORB.init().create_local_interface_tc("IDL:omg.org/DynamicAny/DynAny:1.0", "DynAny"), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NameDynAnyPair s2) {
        any.type(NameDynAnyPairHelper.type());
        NameDynAnyPairHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NameDynAnyPair extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NameDynAnyPair nameDynAnyPair = NameDynAnyPairHelper.read(in);
            return nameDynAnyPair;
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
        return "IDL:omg.org/DynamicAny/NameDynAnyPair:1.0";
    }

    public static NameDynAnyPair read(InputStream in) {
        NameDynAnyPair result = new NameDynAnyPair();
        result.id = in.read_string();
        result.value = DynAnyHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, NameDynAnyPair s2) {
        String tmpResult2 = s2.id;
        out.write_string(tmpResult2);
        DynAnyHelper.write(out, s2.value);
    }
}

