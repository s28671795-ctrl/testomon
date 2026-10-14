/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages.negotiate;

import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContext;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContextType;
import com.hierynomus.protocol.commons.Charsets;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;

public class SMB2NetNameNegotiateContextId
extends SMB2NegotiateContext {
    private String netName;

    SMB2NetNameNegotiateContextId() {
        super(SMB2NegotiateContextType.SMB2_NETNAME_NEGOTIATE_CONTEXT_ID);
    }

    SMB2NetNameNegotiateContextId(String netName) {
        super(SMB2NegotiateContextType.SMB2_NETNAME_NEGOTIATE_CONTEXT_ID);
        this.netName = netName;
    }

    @Override
    protected int writeContext(SMBBuffer buffer) {
        buffer.putNullTerminatedString(this.netName, Charsets.UTF_16);
        return this.netName.length() * 2 + 2;
    }

    @Override
    protected void readContext(SMBBuffer buffer, int dataSize) throws Buffer.BufferException {
        this.netName = buffer.readNullTerminatedString(Charsets.UTF_16);
    }

    public String getNetName() {
        return this.netName;
    }
}

