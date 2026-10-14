/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages.negotiate;

import com.hierynomus.mssmb2.SMB3HashAlgorithm;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContext;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContextType;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import java.util.ArrayList;
import java.util.List;

public class SMB2PreauthIntegrityCapabilities
extends SMB2NegotiateContext {
    public static final int DEFAULT_SALT_LENGTH = 32;
    private List<SMB3HashAlgorithm> hashAlgorithms;
    private byte[] salt;

    public SMB2PreauthIntegrityCapabilities() {
        super(SMB2NegotiateContextType.SMB2_PREAUTH_INTEGRITY_CAPABILITIES);
        this.hashAlgorithms = new ArrayList<SMB3HashAlgorithm>();
    }

    public SMB2PreauthIntegrityCapabilities(List<SMB3HashAlgorithm> hashAlgorithms, byte[] salt) {
        super(SMB2NegotiateContextType.SMB2_PREAUTH_INTEGRITY_CAPABILITIES);
        this.hashAlgorithms = hashAlgorithms;
        this.salt = (byte[])salt.clone();
    }

    @Override
    protected int writeContext(SMBBuffer buffer) {
        if (this.hashAlgorithms == null) {
            throw new IllegalStateException("There should be at least 1 hash algorithm provided");
        }
        if (this.salt == null) {
            throw new IllegalStateException("A salt should be provided");
        }
        buffer.putUInt16(this.hashAlgorithms.size());
        buffer.putUInt16(this.salt.length);
        for (SMB3HashAlgorithm hashAlgorithm : this.hashAlgorithms) {
            buffer.putUInt16((int)hashAlgorithm.getValue());
        }
        buffer.putRawBytes(this.salt);
        return 4 + 2 * this.hashAlgorithms.size() + this.salt.length;
    }

    @Override
    protected void readContext(SMBBuffer buffer, int dataSize) throws Buffer.BufferException {
        int hashAlgorithmCount = buffer.readUInt16();
        int saltLength = buffer.readUInt16();
        for (int i = 0; i < hashAlgorithmCount; ++i) {
            int l = buffer.readUInt16();
            SMB3HashAlgorithm alg = EnumWithValue.EnumUtils.valueOf(l, SMB3HashAlgorithm.class, null);
            if (alg == null) {
                throw new IllegalStateException(String.format("Unknown SMB3HashAlgorithm with value '%d'", l));
            }
            this.hashAlgorithms.add(alg);
        }
        this.salt = buffer.readRawBytes(saltLength);
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public List<SMB3HashAlgorithm> getHashAlgorithms() {
        return this.hashAlgorithms;
    }
}

