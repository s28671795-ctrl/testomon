/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import java.io.Serializable;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.Messaging.ExceptionHolder;

public abstract class ExceptionHolderHelper {
    private static volatile TypeCode _type = null;

    public static void insert(Any a, ExceptionHolder v) {
        a.insert_Value(v, v._type());
    }

    public static ExceptionHolder extract(Any a) {
        return (ExceptionHolder)a.extract_Value();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExceptionHolderHelper> clazz = ExceptionHolderHelper.class;
        synchronized (ExceptionHolderHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_value_tc("IDL:omg.org/Messaging/ExceptionHolder:1.0", "ExceptionHolder", (short)0, null, new ValueMember[]{new ValueMember("is_system_exception", "IDL:*primitive*:1.0", "ExceptionHolder", "1.0", ORB.init().get_primitive_tc(TCKind.from_int(8)), null, 0), new ValueMember("byte_order", "IDL:*primitive*:1.0", "ExceptionHolder", "1.0", ORB.init().get_primitive_tc(TCKind.from_int(8)), null, 0), new ValueMember("marshaled_exception", "IDL:marshaled_exception:1.0", "ExceptionHolder", "1.0", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))), null, 0)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Messaging/ExceptionHolder:1.0";
    }

    public static ExceptionHolder read(org.omg.CORBA.portable.InputStream is) {
        return (ExceptionHolder)((InputStream)is).read_value("IDL:omg.org/Messaging/ExceptionHolder:1.0");
    }

    public static void write(org.omg.CORBA.portable.OutputStream os, ExceptionHolder val) {
        ((OutputStream)os).write_value((Serializable)val, "IDL:omg.org/Messaging/ExceptionHolder:1.0");
    }
}

