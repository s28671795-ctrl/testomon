/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.SMB2MessageCommandCode;
import com.hierynomus.mssmb2.SMB2MessageFlag;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import com.hierynomus.smb.SMBPacketData;

public class SMB2PacketData
extends SMBPacketData<SMB2PacketHeader> {
    public SMB2PacketData(byte[] data) throws Buffer.BufferException {
        super(new SMB2PacketHeader(), data);
    }

    SMB2PacketData(SMBBuffer buffer) throws Buffer.BufferException {
        super(new SMB2PacketHeader(), buffer);
    }

    public long getSequenceNumber() {
        return ((SMB2PacketHeader)this.getHeader()).getMessageId();
    }

    protected boolean isSuccess() {
        long statusCode = ((SMB2PacketHeader)this.getHeader()).getStatusCode();
        return NtStatus.isSuccess(statusCode) && statusCode != NtStatus.STATUS_PENDING.getValue();
    }

    public boolean isIntermediateAsyncResponse() {
        return EnumWithValue.EnumUtils.isSet(((SMB2PacketHeader)this.getHeader()).getFlags(), SMB2MessageFlag.SMB2_FLAGS_ASYNC_COMMAND) && ((SMB2PacketHeader)this.getHeader()).getStatusCode() == NtStatus.STATUS_PENDING.getValue();
    }

    public boolean isOplockBreakNotification() {
        return ((SMB2PacketHeader)this.getHeader()).getMessageId() == -1L && ((SMB2PacketHeader)this.getHeader()).getMessage() == SMB2MessageCommandCode.SMB2_OPLOCK_BREAK;
    }

    public boolean isCompounded() {
        return ((SMB2PacketHeader)this.getHeader()).getNextCommandOffset() != 0;
    }

    public SMB2PacketData next() throws Buffer.BufferException {
        if (this.isCompounded()) {
            return new SMB2PacketData(this.dataBuffer);
        }
        return null;
    }

    public boolean isDecrypted() {
        return false;
    }

    public String toString() {
        return (Object)((Object)((SMB2PacketHeader)this.getHeader()).getMessage()) + " with message id << " + ((SMB2PacketHeader)this.getHeader()).getMessageId() + " >>";
    }
}

