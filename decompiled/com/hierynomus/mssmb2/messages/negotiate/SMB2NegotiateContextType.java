/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages.negotiate;

import com.hierynomus.protocol.commons.EnumWithValue;

public enum SMB2NegotiateContextType implements EnumWithValue<SMB2NegotiateContextType>
{
    SMB2_PREAUTH_INTEGRITY_CAPABILITIES(1L),
    SMB2_ENCRYPTION_CAPABILITIES(2L),
    SMB2_COMPRESSION_CAPABILITIES(4L),
    SMB2_NETNAME_NEGOTIATE_CONTEXT_ID(5L);

    private long value;

    private SMB2NegotiateContextType(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return this.value;
    }
}

