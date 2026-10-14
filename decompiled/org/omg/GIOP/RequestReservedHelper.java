/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class RequestReservedHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, byte[] s2) {
        any.type(RequestReservedHelper.type());
        RequestReservedHelper.write(any.create_output_stream(), s2);
    }

    public static byte[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return RequestReservedHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RequestReservedHelper> clazz = RequestReservedHelper.class;
        synchronized (RequestReservedHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(RequestReservedHelper.id(), "RequestReserved", ORB.init().create_array_tc(3, ORB.init().get_primitive_tc(TCKind.from_int(10))));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GIOP/RequestReserved:1.0";
    }

    public static byte[] read(InputStream _in) {
        byte[] _result = new byte[3];
        _in.read_octet_array(_result, 0, 3);
        return _result;
    }

    public static void write(OutputStream _out, byte[] _s) {
        if (_s.length < 3) {
            throw new MARSHAL("Incorrect array size " + _s.length + ", expecting 3");
        }
        _out.write_octet_array(_s, 0, 3);
    }
}

