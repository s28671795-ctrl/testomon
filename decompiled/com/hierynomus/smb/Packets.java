/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smb;

import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import com.hierynomus.smb.SMBPacket;
import com.hierynomus.smbj.common.SMBRuntimeException;

public class Packets {
    public static byte[] getPacketBytes(SMBPacket<?, ?> packet) {
        SMBBuffer buffer = packet.getBuffer();
        int originalPos = buffer.rpos();
        buffer.rpos(packet.getHeader().getHeaderStartPosition());
        byte[] packetBytes = new byte[packet.getHeader().getMessageEndPosition() - packet.getHeader().getHeaderStartPosition()];
        try {
            buffer.readRawBytes(packetBytes);
        }
        catch (Buffer.BufferException be) {
            throw new SMBRuntimeException("Cannot read packet bytes from buffer", be);
        }
        buffer.rpos(originalPos);
        return packetBytes;
    }
}

