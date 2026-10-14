/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.mssmb2.SMB2TransformHeader;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBPacketData;

public class SMB3EncryptedPacketData
extends SMBPacketData<SMB2TransformHeader> {
    public SMB3EncryptedPacketData(byte[] data) throws Buffer.BufferException {
        super(new SMB2TransformHeader(), data);
    }

    public byte[] getCipherText() throws Buffer.BufferException {
        return this.getDataBuffer().readRawBytes(((SMB2TransformHeader)this.getHeader()).getOriginalMessageSize());
    }

    public String toString() {
        return "Encrypted for session id << " + ((SMB2TransformHeader)this.getHeader()).getSessionId() + " >>";
    }
}

