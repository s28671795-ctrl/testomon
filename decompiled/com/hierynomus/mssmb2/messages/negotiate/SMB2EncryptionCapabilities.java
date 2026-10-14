/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages.negotiate;

import com.hierynomus.mssmb2.SMB3EncryptionCipher;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContext;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContextType;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import java.util.ArrayList;
import java.util.List;

public class SMB2EncryptionCapabilities
extends SMB2NegotiateContext {
    private List<SMB3EncryptionCipher> cipherList;

    public SMB2EncryptionCapabilities() {
        super(SMB2NegotiateContextType.SMB2_ENCRYPTION_CAPABILITIES);
        this.cipherList = new ArrayList<SMB3EncryptionCipher>();
    }

    public SMB2EncryptionCapabilities(List<SMB3EncryptionCipher> cipherList) {
        super(SMB2NegotiateContextType.SMB2_ENCRYPTION_CAPABILITIES);
        this.cipherList = cipherList;
    }

    @Override
    protected int writeContext(SMBBuffer buffer) {
        if (this.cipherList == null || this.cipherList.isEmpty()) {
            throw new IllegalStateException("Cannot serialize an empty or null cipherList");
        }
        buffer.putUInt16(this.cipherList.size());
        for (SMB3EncryptionCipher encryptionCipher : this.cipherList) {
            buffer.putUInt16((int)encryptionCipher.getValue());
        }
        return 2 + 2 * this.cipherList.size();
    }

    @Override
    protected void readContext(SMBBuffer buffer, int dataSize) throws Buffer.BufferException {
        int cipherCount = buffer.readUInt16();
        for (int i = 0; i < cipherCount; ++i) {
            this.cipherList.add(EnumWithValue.EnumUtils.valueOf(buffer.readUInt16(), SMB3EncryptionCipher.class, null));
        }
    }

    public List<SMB3EncryptionCipher> getCipherList() {
        return this.cipherList;
    }
}

