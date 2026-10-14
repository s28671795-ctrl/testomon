/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages.negotiate;

import com.hierynomus.mssmb2.messages.negotiate.SMB2CompressionCapabilities;
import com.hierynomus.mssmb2.messages.negotiate.SMB2EncryptionCapabilities;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContextType;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NetNameNegotiateContextId;
import com.hierynomus.mssmb2.messages.negotiate.SMB2PreauthIntegrityCapabilities;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import com.hierynomus.smbj.common.SMBRuntimeException;

public abstract class SMB2NegotiateContext {
    private SMB2NegotiateContextType negotiateContextType;

    protected SMB2NegotiateContext(SMB2NegotiateContextType negotiateContextType) {
        this.negotiateContextType = negotiateContextType;
    }

    public final int write(SMBBuffer buffer) {
        SMBBuffer tempBuffer = new SMBBuffer();
        int bytesWritten = this.writeContext(tempBuffer);
        this.writeContextHeader(buffer, bytesWritten);
        buffer.putBuffer(tempBuffer);
        return 8 + bytesWritten;
    }

    protected int writeContext(SMBBuffer buffer) {
        throw new UnsupportedOperationException("Should be implemented by specific SMB2NegotiateContext");
    }

    private void writeContextHeader(SMBBuffer buffer, int dataLength) {
        buffer.putUInt16((int)this.negotiateContextType.getValue());
        buffer.putUInt16(dataLength);
        buffer.putReserved4();
    }

    public static SMB2NegotiateContext factory(SMBBuffer buffer) throws Buffer.BufferException {
        int negotiateContextTypeId = buffer.readUInt16();
        SMB2NegotiateContextType negotiateContextType = EnumWithValue.EnumUtils.valueOf(negotiateContextTypeId, SMB2NegotiateContextType.class, null);
        switch (negotiateContextType) {
            case SMB2_PREAUTH_INTEGRITY_CAPABILITIES: {
                return new SMB2PreauthIntegrityCapabilities().read(buffer);
            }
            case SMB2_ENCRYPTION_CAPABILITIES: {
                return new SMB2EncryptionCapabilities().read(buffer);
            }
            case SMB2_COMPRESSION_CAPABILITIES: {
                return new SMB2CompressionCapabilities().read(buffer);
            }
            case SMB2_NETNAME_NEGOTIATE_CONTEXT_ID: {
                return new SMB2NetNameNegotiateContextId().read(buffer);
            }
        }
        throw new SMBRuntimeException("Unknown SMB2NegotiateContextType encountered: " + negotiateContextTypeId + " / " + negotiateContextType);
    }

    public final SMB2NegotiateContext read(SMBBuffer buffer) throws Buffer.BufferException {
        int dataAlignment;
        int dataSize = this.readContextHeader(buffer);
        this.readContext(buffer, dataSize);
        int n = dataAlignment = dataSize % 8 == 0 ? 0 : 8 - dataSize % 8;
        if (dataAlignment > 0 && buffer.available() >= dataAlignment) {
            buffer.skip(dataAlignment);
        }
        return this;
    }

    protected void readContext(SMBBuffer buffer, int dataSize) throws Buffer.BufferException {
        throw new UnsupportedOperationException("Should be implemented by specific message type");
    }

    private int readContextHeader(SMBBuffer buffer) throws Buffer.BufferException {
        int size = buffer.readUInt16();
        buffer.skip(4);
        return size;
    }

    public SMB2NegotiateContextType getNegotiateContextType() {
        return this.negotiateContextType;
    }
}

