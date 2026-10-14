/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages;

import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2FileId;
import com.hierynomus.mssmb2.SMB2MessageCommandCode;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.messages.submodule.SMB2LockElement;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.smb.SMBBuffer;
import java.util.List;

public class SMB2LockRequest
extends SMB2Packet {
    private final short lockSequenceNumber;
    private final int lockSequenceIndex;
    private final SMB2FileId fileId;
    private final List<SMB2LockElement> lockElements;

    public SMB2LockRequest(SMB2Dialect dialect, long sessionId, long treeId, short lockSequenceNumber, int lockSequenceIndex, SMB2FileId fileId, List<SMB2LockElement> lockElements) {
        super(48, dialect, SMB2MessageCommandCode.SMB2_LOCK, sessionId, treeId);
        if (lockSequenceNumber < 0 || lockSequenceNumber > 15) {
            throw new IllegalArgumentException("Only 4-bit integer value is allowed for lockSequenceNumber.");
        }
        this.lockSequenceNumber = lockSequenceNumber;
        if (lockSequenceIndex < 0 || lockSequenceIndex > 64) {
            throw new IllegalArgumentException("Only value between 0 to 64 (inclusive) is allowed for lockSequenceIndex.");
        }
        this.lockSequenceIndex = lockSequenceIndex;
        this.fileId = fileId;
        this.lockElements = lockElements;
    }

    @Override
    protected void writeTo(SMBBuffer buffer) {
        buffer.putUInt16(this.structureSize);
        buffer.putUInt16(this.lockElements.size());
        buffer.putUInt32(this.getLsnAndLsi());
        this.fileId.write(buffer);
        for (SMB2LockElement lockElement : this.lockElements) {
            buffer.putUInt64(lockElement.getOffset());
            buffer.putUInt64(lockElement.getLength());
            buffer.putUInt32(EnumWithValue.EnumUtils.toLong(lockElement.getLockFlags()));
            buffer.putReserved4();
        }
    }

    private int getLsnAndLsi() {
        return (this.lockSequenceIndex << 4) + this.lockSequenceNumber;
    }
}

