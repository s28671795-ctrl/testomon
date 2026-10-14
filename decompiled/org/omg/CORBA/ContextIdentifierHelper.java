/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ContextIdentifierHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, String s2) {
        any.type(ContextIdentifierHelper.type());
        ContextIdentifierHelper.write(any.create_output_stream(), s2);
    }

    public static String extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ContextIdentifierHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ContextIdentifierHelper> clazz = ContextIdentifierHelper.class;
        synchronized (ContextIdentifierHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ContextIdentifierHelper.id(), "ContextIdentifier", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ContextIdentifier:1.0";
    }

    public static String read(InputStream _in) {
        String _result = _in.read_string();
        return _result;
    }

    public static void write(OutputStream _out, String _s) {
        String tmpResult407 = _s;
        _out.write_string(tmpResult407);
    }
}

