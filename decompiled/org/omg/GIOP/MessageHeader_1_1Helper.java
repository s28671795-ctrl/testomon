/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.GIOP.MagicnHelper;
import org.omg.GIOP.MessageHeader_1_1;
import org.omg.GIOP.VersionHelper;

public abstract class MessageHeader_1_1Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<MessageHeader_1_1Helper> clazz = MessageHeader_1_1Helper.class;
        synchronized (MessageHeader_1_1Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(MessageHeader_1_1Helper.id(), "MessageHeader_1_1", new StructMember[]{new StructMember("magic", ORB.init().create_alias_tc(MagicnHelper.id(), "Magicn", ORB.init().create_array_tc(4, ORB.init().get_primitive_tc(TCKind.from_int(9)))), null), new StructMember("GIOP_version", ORB.init().create_struct_tc(VersionHelper.id(), "Version", new StructMember[]{new StructMember("major", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(10)), null)}), null), new StructMember("flags", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("message_type", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("message_size", ORB.init().get_primitive_tc(TCKind.from_int(5)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, MessageHeader_1_1 s2) {
        any.type(MessageHeader_1_1Helper.type());
        MessageHeader_1_1Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static MessageHeader_1_1 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            MessageHeader_1_1 messageHeader_1_1 = MessageHeader_1_1Helper.read(in);
            return messageHeader_1_1;
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
        return "IDL:omg.org/GIOP/MessageHeader_1_1:1.0";
    }

    public static MessageHeader_1_1 read(InputStream in) {
        MessageHeader_1_1 result = new MessageHeader_1_1();
        result.magic = MagicnHelper.read(in);
        result.GIOP_version = VersionHelper.read(in);
        result.flags = in.read_octet();
        result.message_type = in.read_octet();
        result.message_size = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, MessageHeader_1_1 s2) {
        MagicnHelper.write(out, s2.magic);
        VersionHelper.write(out, s2.GIOP_version);
        out.write_octet(s2.flags);
        out.write_octet(s2.message_type);
        out.write_ulong(s2.message_size);
    }
}

