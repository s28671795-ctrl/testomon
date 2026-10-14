/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2Error;
import com.hierynomus.mssmb2.SMB2MessageCommandCode;
import com.hierynomus.mssmb2.SMB2MessageFlag;
import com.hierynomus.mssmb2.SMB2PacketData;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import com.hierynomus.smb.SMBPacket;

public class SMB2Packet
extends SMBPacket<SMB2PacketData, SMB2PacketHeader> {
    public static final int SINGLE_CREDIT_PAYLOAD_SIZE = 65536;
    protected int structureSize;
    private SMB2Error error;

    protected SMB2Packet() {
        super(new SMB2PacketHeader());
    }

    protected SMB2Packet(int structureSize, SMB2Dialect dialect, SMB2MessageCommandCode messageType) {
        this(structureSize, dialect, messageType, 0L, 0L);
    }

    protected SMB2Packet(int structureSize, SMB2Dialect dialect, SMB2MessageCommandCode messageType, long sessionId) {
        this(structureSize, dialect, messageType, sessionId, 0L);
    }

    protected SMB2Packet(int structureSize, SMB2Dialect dialect, SMB2MessageCommandCode messageType, long sessionId, long treeId) {
        super(new SMB2PacketHeader());
        this.structureSize = structureSize;
        ((SMB2PacketHeader)this.header).setDialect(dialect);
        ((SMB2PacketHeader)this.header).setMessageType(messageType);
        ((SMB2PacketHeader)this.header).setSessionId(sessionId);
        ((SMB2PacketHeader)this.header).setTreeId(treeId);
    }

    public long getSequenceNumber() {
        return ((SMB2PacketHeader)this.header).getMessageId();
    }

    public int getStructureSize() {
        return this.structureSize;
    }

    @Override
    public SMBBuffer getBuffer() {
        return this.buffer;
    }

    @Override
    public void write(SMBBuffer buffer) {
        this.buffer = buffer;
        ((SMB2PacketHeader)this.header).writeTo(buffer);
        this.writeTo(buffer);
        ((SMB2PacketHeader)this.header).setMessageEndPosition(buffer.wpos());
    }

    protected void writeTo(SMBBuffer buffer) {
        throw new UnsupportedOperationException("Should be implemented by specific message type");
    }

    @Override
    protected final void read(SMB2PacketData packetData) throws Buffer.BufferException {
        this.buffer = packetData.getDataBuffer();
        this.header = packetData.getHeader();
        this.readMessage(this.buffer);
        this.buffer.rpos(((SMB2PacketHeader)this.header).getMessageEndPosition());
    }

    final void readError(SMB2PacketData packetData) throws Buffer.BufferException {
        this.buffer = packetData.getDataBuffer();
        this.header = packetData.getHeader();
        this.error = new SMB2Error().read((SMB2PacketHeader)this.header, this.buffer);
        this.buffer.rpos(((SMB2PacketHeader)this.header).getMessageEndPosition());
    }

    protected void readMessage(SMBBuffer buffer) throws Buffer.BufferException {
        throw new UnsupportedOperationException("Should be implemented by specific message type");
    }

    public final boolean isSuccess() {
        return this.error == null;
    }

    public boolean isIntermediateAsyncResponse() {
        return EnumWithValue.EnumUtils.isSet(((SMB2PacketHeader)this.header).getFlags(), SMB2MessageFlag.SMB2_FLAGS_ASYNC_COMMAND) && ((SMB2PacketHeader)this.header).getStatusCode() == NtStatus.STATUS_PENDING.getValue();
    }

    public int getMaxPayloadSize() {
        return 65536;
    }

    public int getCreditsAssigned() {
        return ((SMB2PacketHeader)this.getHeader()).getCreditCharge();
    }

    public void setCreditsAssigned(int creditsAssigned) {
        ((SMB2PacketHeader)this.getHeader()).setCreditCharge(creditsAssigned);
    }

    public SMB2Error getError() {
        return this.error;
    }

    public SMB2Packet getPacket() {
        return this;
    }

    public String toString() {
        return (Object)((Object)((SMB2PacketHeader)this.header).getMessage()) + " with message id << " + ((SMB2PacketHeader)this.header).getMessageId() + " >>";
    }
}

