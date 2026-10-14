/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.mssmb2.SMB2CompressionTransformHeader;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBPacketData;

public class SMB3CompressedPacketData
extends SMBPacketData<SMB2CompressionTransformHeader> {
    private boolean decrypted;

    public SMB3CompressedPacketData(byte[] data) throws Buffer.BufferException {
        this(data, false);
    }

    public SMB3CompressedPacketData(byte[] data, boolean decrypted) throws Buffer.BufferException {
        super(new SMB2CompressionTransformHeader(), data);
        this.decrypted = decrypted;
    }

    public boolean isDecrypted() {
        return this.decrypted;
    }
}

