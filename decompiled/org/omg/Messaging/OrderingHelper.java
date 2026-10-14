/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class OrderingHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, short s2) {
        any.insert_ushort(s2);
    }

    public static short extract(Any any) {
        return any.extract_ushort();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<OrderingHelper> clazz = OrderingHelper.class;
        synchronized (OrderingHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(OrderingHelper.id(), "Ordering", ORB.init().get_primitive_tc(TCKind.from_int(4)));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Messaging/Ordering:1.0";
    }

    public static short read(InputStream _in) {
        short _result = _in.read_ushort();
        return _result;
    }

    public static void write(OutputStream _out, short _s) {
        _out.write_ushort(_s);
    }
}

