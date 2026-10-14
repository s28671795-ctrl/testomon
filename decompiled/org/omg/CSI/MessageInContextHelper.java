/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.ContextIdHelper;
import org.omg.CSI.MessageInContext;

public abstract class MessageInContextHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MessageInContextHelper> clazz = MessageInContextHelper.class;
        synchronized (MessageInContextHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(MessageInContextHelper.id(), "MessageInContext", new StructMember[]{new StructMember("client_context_id", ORB.init().create_alias_tc(ContextIdHelper.id(), "ContextId", ORB.init().get_primitive_tc(TCKind.from_int(24))), null), new StructMember("discard_context", ORB.init().get_primitive_tc(TCKind.from_int(8)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, MessageInContext s2) {
        any.type(MessageInContextHelper.type());
        MessageInContextHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static MessageInContext extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            MessageInContext messageInContext = MessageInContextHelper.read(in);
            return messageInContext;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Unexpected exception " + e.toString());
            }
        }
    }

    public static String id() {
        return "IDL:omg.org/CSI/MessageInContext:1.0";
    }

    public static MessageInContext read(InputStream in) {
        MessageInContext result = new MessageInContext();
        result.client_context_id = in.read_ulonglong();
        result.discard_context = in.read_boolean();
        return result;
    }

    public static void write(OutputStream out, MessageInContext s2) {
        out.write_ulonglong(s2.client_context_id);
        out.write_boolean(s2.discard_context);
    }
}

