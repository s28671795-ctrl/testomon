/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.mssmb2.SMB3CompressedPacketData;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.protocol.transport.PacketFactory;
import java.io.IOException;

public class SMB3CompressedPacketFactory
implements PacketFactory<SMB3CompressedPacketData> {
    @Override
    public SMB3CompressedPacketData read(byte[] data) throws Buffer.BufferException, IOException {
        return new SMB3CompressedPacketData(data);
    }

    @Override
    public boolean canHandle(byte[] data) {
        return data[0] == -4 && data[1] == 83 && data[2] == 77 && data[3] == 66;
    }
}

