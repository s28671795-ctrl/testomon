/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ValueModifierHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, short s2) {
        any.insert_short(s2);
    }

    public static short extract(Any any) {
        return any.extract_short();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ValueModifierHelper> clazz = ValueModifierHelper.class;
        synchronized (ValueModifierHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ValueModifierHelper.id(), "ValueModifier", ORB.init().get_primitive_tc(TCKind.from_int(2)));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ValueModifier:1.0";
    }

    public static short read(InputStream _in) {
        short _result = _in.read_short();
        return _result;
    }

    public static void write(OutputStream _out, short _s) {
        _out.write_short(_s);
    }
}

