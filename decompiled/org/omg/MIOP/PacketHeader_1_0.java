/*
 * Decompiled with CFR 0.152.
 */
package org.omg.MIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class PacketHeader_1_0
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public char[] magic;
    public byte hdr_version;
    public byte flags;
    public short packet_length;
    public int packet_number;
    public int number_of_packets;
    public byte[] Id;

    public PacketHeader_1_0() {
    }

    public PacketHeader_1_0(char[] magic, byte hdr_version, byte flags, short packet_length, int packet_number, int number_of_packets, byte[] Id2) {
        this.magic = magic;
        this.hdr_version = hdr_version;
        this.flags = flags;
        this.packet_length = packet_length;
        this.packet_number = packet_number;
        this.number_of_packets = number_of_packets;
        this.Id = Id2;
    }
}

