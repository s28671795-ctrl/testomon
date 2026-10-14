/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;

public class SMB2DecryptedPacketData
extends SMB2PacketData {
    public SMB2DecryptedPacketData(byte[] data) throws Buffer.BufferException {
        super(data);
    }

    public SMB2DecryptedPacketData(SMBBuffer dataBuffer) throws Buffer.BufferException {
        super(dataBuffer);
    }

    @Override
    public SMB2PacketData next() throws Buffer.BufferException {
        if (this.isCompounded()) {
            return new SMB2DecryptedPacketData(this.dataBuffer);
        }
        return null;
    }

    @Override
    public boolean isDecrypted() {
        return true;
    }
}

