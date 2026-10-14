/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.MagicnHelper;
import org.omg.GIOP.MessageHeader_1_1;
import org.omg.GIOP.MessageHeader_1_1Helper;
import org.omg.GIOP.VersionHelper;

public abstract class MessageHeader_1_3Helper {
    private static volatile TypeCode _type;

    public static void insert(Any any, MessageHeader_1_1 s2) {
        any.type(MessageHeader_1_3Helper.type());
        MessageHeader_1_3Helper.write(any.create_output_stream(), s2);
    }

    public static MessageHeader_1_1 extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return MessageHeader_1_3Helper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MessageHeader_1_3Helper> clazz = MessageHeader_1_3Helper.class;
        synchronized (MessageHeader_1_3Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(MessageHeader_1_3Helper.id(), "MessageHeader_1_3", ORB.init().create_struct_tc(MessageHeader_1_1Helper.id(), "MessageHeader_1_1", new StructMember[]{new StructMember("magic", ORB.init().create_alias_tc(MagicnHelper.id(), "Magicn", ORB.init().create_array_tc(4, ORB.init().get_primitive_tc(TCKind.from_int(9)))), null), new StructMember("GIOP_version", ORB.init().create_struct_tc(VersionHelper.id(), "Version", new StructMember[]{new StructMember("major", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(10)), null)}), null), new StructMember("flags", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("message_type", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("message_size", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)}));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/GIOP/MessageHeader_1_3:1.0";
    }

    public static MessageHeader_1_1 read(InputStream _in) {
        MessageHeader_1_1 _result = MessageHeader_1_1Helper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, MessageHeader_1_1 _s) {
        MessageHeader_1_1Helper.write(_out, _s);
    }
}

