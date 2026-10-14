/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import com.hierynomus.smb.SMBHeader;
import com.hierynomus.smbj.common.Check;

public class SMB2TransformHeader
implements SMBHeader {
    public static final byte[] ENCRYPTED_PROTOCOL_ID = new byte[]{-3, 83, 77, 66};
    private int headerStartPosition;
    private byte[] signature;
    private byte[] nonce;
    private int originalMessageSize;
    private int flagsEncryptionAlgorithm;
    private long sessionId;
    private int messageEndPosition;

    public SMB2TransformHeader(byte[] nonce, int originalMessageSize, long sessionId) {
        this.signature = new byte[16];
        this.nonce = nonce;
        this.originalMessageSize = originalMessageSize;
        this.sessionId = sessionId;
    }

    public SMB2TransformHeader() {
    }

    @Override
    public void writeTo(SMBBuffer buffer) {
        this.headerStartPosition = buffer.rpos();
        buffer.putRawBytes(ENCRYPTED_PROTOCOL_ID);
        buffer.putRawBytes(this.signature);
        buffer.putRawBytes(this.nonce);
        buffer.putReserved(16 - this.nonce.length);
        buffer.putUInt32(this.originalMessageSize);
        buffer.putReserved2();
        buffer.putUInt16(1);
        buffer.putLong(this.sessionId);
    }

    @Override
    public void readFrom(Buffer<?> buffer) throws Buffer.BufferException {
        this.headerStartPosition = buffer.rpos();
        byte[] protocolId = buffer.readRawBytes(4);
        Check.ensureEquals(protocolId, ENCRYPTED_PROTOCOL_ID, "Could not find SMB2 Packet header");
        this.signature = buffer.readRawBytes(16);
        this.nonce = buffer.readRawBytes(16);
        this.originalMessageSize = buffer.readUInt32AsInt();
        buffer.skip(2);
        this.flagsEncryptionAlgorithm = buffer.readUInt16();
        this.sessionId = buffer.readLong();
        this.messageEndPosition = buffer.wpos();
    }

    @Override
    public int getHeaderStartPosition() {
        return this.headerStartPosition;
    }

    @Override
    public int getMessageEndPosition() {
        return this.messageEndPosition;
    }

    public void setMessageEndPosition(int messageEndPosition) {
        this.messageEndPosition = messageEndPosition;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public int getOriginalMessageSize() {
        return this.originalMessageSize;
    }

    public int getFlagsEncryptionAlgorithm() {
        return this.flagsEncryptionAlgorithm;
    }

    public long getSessionId() {
        return this.sessionId;
    }
}

