/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Messaging.ReplyHandler;

public abstract class ReplyHandlerHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ReplyHandlerHelper> clazz = ReplyHandlerHelper.class;
        synchronized (ReplyHandlerHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_interface_tc("IDL:omg.org/Messaging/ReplyHandler:1.0", "ReplyHandler");
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ReplyHandler s2) {
        any.insert_Object(s2);
    }

    public static ReplyHandler extract(Any any) {
        return ReplyHandlerHelper.narrow(any.extract_Object());
    }

    public static String id() {
        return "IDL:omg.org/Messaging/ReplyHandler:1.0";
    }

    public static ReplyHandler read(InputStream in) {
        return ReplyHandlerHelper.narrow(in.read_Object());
    }

    public static void write(OutputStream _out, ReplyHandler s2) {
        _out.write_Object(s2);
    }

    public static ReplyHandler narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ReplyHandler) {
            return (ReplyHandler)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }

    public static ReplyHandler unchecked_narrow(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ReplyHandler) {
            return (ReplyHandler)obj;
        }
        throw new BAD_PARAM("Narrow failed");
    }
}

