/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringSeqHelper;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class AdapterNameHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, String[] s2) {
        any.type(AdapterNameHelper.type());
        AdapterNameHelper.write(any.create_output_stream(), s2);
    }

    public static String[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return AdapterNameHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AdapterNameHelper> clazz = AdapterNameHelper.class;
        synchronized (AdapterNameHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(AdapterNameHelper.id(), "AdapterName", ORB.init().create_alias_tc(StringSeqHelper.id(), "StringSeq", ORB.init().create_sequence_tc(0, ORB.init().create_string_tc(0))));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableInterceptor/AdapterName:1.0";
    }

    public static String[] read(InputStream _in) {
        String[] _result = StringSeqHelper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, String[] _s) {
        StringSeqHelper.write(_out, _s);
    }
}

