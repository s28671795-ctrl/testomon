/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.Serializable;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class StringValueHelper
implements BoxedValueHelper {
    private static TypeCode _type = ORB.init().create_value_box_tc(StringValueHelper.id(), "StringValue", ORB.init().create_string_tc(0));

    public static TypeCode type() {
        return _type;
    }

    public static void insert(Any any, String s2) {
        any.insert_Value((Serializable)((Object)s2), StringValueHelper.type());
    }

    public static String extract(Any any) {
        return (String)((Object)any.extract_Value());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/StringValue:1.0";
    }

    public static String read(InputStream in) {
        String result = in.read_string();
        return result;
    }

    public static void write(OutputStream out, String s2) {
        String tmpResult981 = s2;
        out.write_string(tmpResult981);
    }

    @Override
    public Serializable read_value(InputStream is) {
        return StringValueHelper.read(is);
    }

    @Override
    public void write_value(OutputStream os, Serializable value) {
        StringValueHelper.write(os, (String)((Object)value));
    }

    @Override
    public String get_id() {
        return StringValueHelper.id();
    }
}

