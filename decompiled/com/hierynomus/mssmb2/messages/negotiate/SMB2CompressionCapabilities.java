/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages.negotiate;

import com.hierynomus.mssmb2.SMB3CompressionAlgorithm;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContext;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContextType;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import java.util.ArrayList;
import java.util.List;

public class SMB2CompressionCapabilities
extends SMB2NegotiateContext {
    private List<SMB3CompressionAlgorithm> compressionAlgorithms;

    SMB2CompressionCapabilities() {
        super(SMB2NegotiateContextType.SMB2_COMPRESSION_CAPABILITIES);
        this.compressionAlgorithms = new ArrayList<SMB3CompressionAlgorithm>();
    }

    SMB2CompressionCapabilities(List<SMB3CompressionAlgorithm> compressionAlgorithms) {
        super(SMB2NegotiateContextType.SMB2_COMPRESSION_CAPABILITIES);
        this.compressionAlgorithms = compressionAlgorithms;
    }

    @Override
    protected int writeContext(SMBBuffer buffer) {
        if (this.compressionAlgorithms == null) {
            throw new IllegalStateException("Cannot write a null compressionAlgorithms array");
        }
        buffer.putUInt16(this.compressionAlgorithms.size());
        buffer.putReserved2();
        buffer.putReserved4();
        for (SMB3CompressionAlgorithm compressionAlgorithm : this.compressionAlgorithms) {
            buffer.putUInt16((int)compressionAlgorithm.getValue());
        }
        return 8 + 2 * this.compressionAlgorithms.size();
    }

    @Override
    protected void readContext(SMBBuffer buffer, int dataSize) throws Buffer.BufferException {
        int compressionAlgorithmCount = buffer.readUInt16();
        buffer.skip(2);
        buffer.skip(4);
        for (int i = 0; i < compressionAlgorithmCount; ++i) {
            int l = buffer.readUInt16();
            SMB3CompressionAlgorithm alg = EnumWithValue.EnumUtils.valueOf(l, SMB3CompressionAlgorithm.class, null);
            if (alg == null) {
                throw new IllegalStateException(String.format("Unknown SMB3CompressionAlgorithm with value '%d'", l));
            }
            this.compressionAlgorithms.add(alg);
        }
    }

    public List<SMB3CompressionAlgorithm> getCompressionAlgorithms() {
        return this.compressionAlgorithms;
    }
}

