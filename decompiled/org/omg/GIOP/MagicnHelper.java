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

public abstract class MagicnHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, char[] s2) {
        any.type(MagicnHelper.type());
        MagicnHelper.write(any.create_output_stream(), s2);
    }

    public static char[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return MagicnHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MagicnHelper> clazz = MagicnHelper.class;
        synchronized (MagicnHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(MagicnHelper.id(), "Magicn", ORB.init().create_array_tc(4, ORB.init().get_primitive_tc(TCKind.from_int(9))));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GIOP/Magicn:1.0";
    }

    public static char[] read(InputStream _in) {
        char[] _result = new char[4];
        _in.read_char_array(_result, 0, 4);
        return _result;
    }

    public static void write(OutputStream _out, char[] _s) {
        if (_s.length < 4) {
            throw new MARSHAL("Incorrect array size " + _s.length + ", expecting 4");
        }
        _out.write_char_array(_s, 0, 4);
    }
}

