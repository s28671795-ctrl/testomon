/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.GIOP.Version;

public final class MessageHeader_1_1
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public char[] magic;
    public Version GIOP_version;
    public byte flags;
    public byte message_type;
    public int message_size;

    public MessageHeader_1_1() {
    }

    public MessageHeader_1_1(char[] magic, Version GIOP_version, byte flags, byte message_type, int message_size) {
        this.magic = magic;
        this.GIOP_version = GIOP_version;
        this.flags = flags;
        this.message_type = message_type;
        this.message_size = message_size;
    }
}

