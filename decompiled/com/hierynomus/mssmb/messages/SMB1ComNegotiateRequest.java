/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb.messages;

import com.hierynomus.mssmb.SMB1Header;
import com.hierynomus.mssmb.SMB1Packet;
import com.hierynomus.mssmb.SMB1PacketData;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.protocol.commons.Charsets;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import java.util.ArrayList;
import java.util.Set;

public class SMB1ComNegotiateRequest
extends SMB1Packet {
    private Set<SMB2Dialect> dialects;

    public SMB1ComNegotiateRequest(Set<SMB2Dialect> dialects) {
        this.dialects = dialects;
    }

    @Override
    public void writeTo(SMBBuffer buffer) {
        buffer.putByte((byte)0);
        ArrayList<String> dialectsToWrite = new ArrayList<String>();
        dialectsToWrite.add("SMB 2.002");
        if (this.dialects.size() > 1 || !this.dialects.contains((Object)SMB2Dialect.SMB_2_0_2)) {
            dialectsToWrite.add("SMB 2.???");
        }
        int byteCount = 0;
        for (String s2 : dialectsToWrite) {
            byteCount += 1 + (s2.length() + 1);
        }
        buffer.putUInt16(byteCount);
        for (String s2 : dialectsToWrite) {
            buffer.putByte((byte)2);
            buffer.putNullTerminatedString(s2, Charsets.UTF_8);
        }
        ((SMB1Header)this.header).setMessageEndPosition(buffer.wpos());
    }

    @Override
    public void read(SMB1PacketData packetData) throws Buffer.BufferException {
        throw new UnsupportedOperationException("SMBv1 not implemented in SMBJ");
    }

    public String toString() {
        return "SMB_COM_NEGOTIATE";
    }
}

