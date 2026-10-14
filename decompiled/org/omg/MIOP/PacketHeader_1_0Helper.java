/*
 * Decompiled with CFR 0.152.
 */
package org.omg.MIOP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.MIOP.PacketHeader_1_0;
import org.omg.MIOP.UniqueIdHelper;

public abstract class PacketHeader_1_0Helper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<PacketHeader_1_0Helper> clazz = PacketHeader_1_0Helper.class;
        synchronized (PacketHeader_1_0Helper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(PacketHeader_1_0Helper.id(), "PacketHeader_1_0", new StructMember[]{new StructMember("magic", ORB.init().create_array_tc(4, ORB.init().get_primitive_tc(TCKind.from_int(9))), null), new StructMember("hdr_version", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("flags", ORB.init().get_primitive_tc(TCKind.from_int(10)), null), new StructMember("packet_length", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("packet_number", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("number_of_packets", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("Id", ORB.init().create_alias_tc(UniqueIdHelper.id(), "UniqueId", ORB.init().create_sequence_tc(252, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, PacketHeader_1_0 s2) {
        any.type(PacketHeader_1_0Helper.type());
        PacketHeader_1_0Helper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PacketHeader_1_0 extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            PacketHeader_1_0 packetHeader_1_0 = PacketHeader_1_0Helper.read(in);
            return packetHeader_1_0;
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
        return "IDL:omg.org/MIOP/PacketHeader_1_0:1.0";
    }

    public static PacketHeader_1_0 read(InputStream in) {
        PacketHeader_1_0 result = new PacketHeader_1_0();
        result.magic = new char[4];
        in.read_char_array(result.magic, 0, 4);
        result.hdr_version = in.read_octet();
        result.flags = in.read_octet();
        result.packet_length = in.read_ushort();
        result.packet_number = in.read_ulong();
        result.number_of_packets = in.read_ulong();
        result.Id = UniqueIdHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, PacketHeader_1_0 s2) {
        if (s2.magic.length < 4) {
            throw new MARSHAL("Incorrect array size " + s2.magic.length + ", expecting 4");
        }
        out.write_char_array(s2.magic, 0, 4);
        out.write_octet(s2.hdr_version);
        out.write_octet(s2.flags);
        out.write_ushort(s2.packet_length);
        out.write_ulong(s2.packet_number);
        out.write_ulong(s2.number_of_packets);
        UniqueIdHelper.write(out, s2.Id);
    }
}

