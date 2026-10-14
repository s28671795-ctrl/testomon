/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages;

import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2MessageCommandCode;
import com.hierynomus.mssmb2.SMB2MessageFlag;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;

public class SMB2Cancel
extends SMB2Packet {
    public SMB2Cancel() {
    }

    public SMB2Cancel(SMB2Dialect dialect, long sessionId, long messageId, long asyncId) {
        super(4, dialect, SMB2MessageCommandCode.SMB2_CANCEL, sessionId);
        ((SMB2PacketHeader)this.header).setMessageId(messageId);
        if (asyncId != 0L) {
            ((SMB2PacketHeader)this.header).setFlag(SMB2MessageFlag.SMB2_FLAGS_ASYNC_COMMAND);
            ((SMB2PacketHeader)this.header).setAsyncId(asyncId);
        }
    }

    @Override
    protected void writeTo(SMBBuffer buffer) {
        buffer.putUInt16(this.structureSize);
        buffer.putReserved2();
    }

    @Override
    protected void readMessage(SMBBuffer buffer) throws Buffer.BufferException {
        buffer.readUInt16();
        buffer.skip(2);
    }
}

