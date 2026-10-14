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

public final class WStringValueHelper
implements BoxedValueHelper {
    private static TypeCode _type = ORB.init().create_value_box_tc(WStringValueHelper.id(), "WStringValue", ORB.init().create_wstring_tc(0));

    public static TypeCode type() {
        return _type;
    }

    public static void insert(Any any, String s2) {
        any.insert_Value((Serializable)((Object)s2), WStringValueHelper.type());
    }

    public static String extract(Any any) {
        return (String)((Object)any.extract_Value());
    }

    public static String id() {
        return "IDL:omg.org/CORBA/WStringValue:1.0";
    }

    public static String read(InputStream in) {
        String result = in.read_wstring();
        return result;
    }

    public static void write(OutputStream out, String s2) {
        String tmpResult982 = s2;
        out.write_wstring(tmpResult982);
    }

    @Override
    public Serializable read_value(InputStream is) {
        return WStringValueHelper.read(is);
    }

    @Override
    public void write_value(OutputStream os, Serializable value) {
        WStringValueHelper.write(os, (String)((Object)value));
    }

    @Override
    public String get_id() {
        return WStringValueHelper.id();
    }
}

