/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages;

import com.hierynomus.msdtyp.MsDataTypes;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2GlobalCapability;
import com.hierynomus.mssmb2.SMB2MessageCommandCode;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB3EncryptionCipher;
import com.hierynomus.mssmb2.SMB3HashAlgorithm;
import com.hierynomus.mssmb2.messages.negotiate.SMB2EncryptionCapabilities;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContext;
import com.hierynomus.mssmb2.messages.negotiate.SMB2PreauthIntegrityCapabilities;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.smb.SMBBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SMB2NegotiateRequest
extends SMB2Packet {
    private Set<SMB2Dialect> dialects;
    private UUID clientGuid;
    private boolean clientSigningRequired;
    private Set<SMB2GlobalCapability> capabilities;
    private List<SMB2NegotiateContext> negotiateContextList;

    public SMB2NegotiateRequest(Set<SMB2Dialect> dialects, UUID clientGuid, boolean clientSigningRequired, Set<SMB2GlobalCapability> capabilities, byte[] salt) {
        super(36, SMB2Dialect.UNKNOWN, SMB2MessageCommandCode.SMB2_NEGOTIATE, 0L, 0L);
        this.dialects = dialects;
        this.clientGuid = clientGuid;
        this.clientSigningRequired = clientSigningRequired;
        this.capabilities = capabilities;
        this.negotiateContextList = this.buildNegotiateContextList(salt);
    }

    private List<SMB2NegotiateContext> buildNegotiateContextList(byte[] salt) {
        if (this.dialects.contains((Object)SMB2Dialect.SMB_3_1_1)) {
            ArrayList<SMB2NegotiateContext> contexts = new ArrayList<SMB2NegotiateContext>();
            List<SMB3HashAlgorithm> hashAlgorithmList = Arrays.asList(SMB3HashAlgorithm.SHA_512);
            contexts.add(new SMB2PreauthIntegrityCapabilities(hashAlgorithmList, salt));
            List<SMB3EncryptionCipher> cipherList = Arrays.asList(SMB3EncryptionCipher.AES_128_GCM, SMB3EncryptionCipher.AES_128_CCM);
            contexts.add(new SMB2EncryptionCapabilities(cipherList));
            return contexts;
        }
        return Collections.emptyList();
    }

    @Override
    protected void writeTo(SMBBuffer buffer) {
        buffer.putUInt16(this.structureSize);
        buffer.putUInt16(this.dialects.size());
        buffer.putUInt16(this.securityMode());
        buffer.putReserved(2);
        this.putCapabilities(buffer);
        MsDataTypes.putGuid(this.clientGuid, buffer);
        this.putNegotiateContextOrStartTime(buffer);
        this.putDialects(buffer);
        int eightByteAlignment = (this.structureSize + this.dialects.size() * 2) % 8;
        if (eightByteAlignment > 0) {
            buffer.putReserved(8 - eightByteAlignment);
        }
        this.putNegotiateContextList(buffer);
    }

    private int securityMode() {
        if (this.clientSigningRequired) {
            return 2;
        }
        return 1;
    }

    private void putNegotiateContextList(SMBBuffer buffer) {
        if (this.dialects.contains((Object)SMB2Dialect.SMB_3_1_1)) {
            for (int i = 0; i < this.negotiateContextList.size(); ++i) {
                int bytesWritten = this.negotiateContextList.get(i).write(buffer);
                if (i >= this.negotiateContextList.size() - 1 || bytesWritten % 8 == 0) continue;
                buffer.putReserved(8 - bytesWritten % 8);
            }
        }
    }

    private void putDialects(SMBBuffer buffer) {
        for (SMB2Dialect dialect : this.dialects) {
            buffer.putUInt16(dialect.getValue());
        }
    }

    private void putNegotiateContextOrStartTime(SMBBuffer buffer) {
        if (this.dialects.contains((Object)SMB2Dialect.SMB_3_1_1)) {
            int trueEightByteAlignment = 8 - (this.structureSize + this.dialects.size() * 2) % 8;
            long negotiateContextOffset = 64 + this.structureSize + this.dialects.size() * 2 + trueEightByteAlignment;
            buffer.putUInt32(negotiateContextOffset);
            buffer.putUInt16(this.negotiateContextList.size());
            buffer.putReserved2();
        } else {
            buffer.putReserved(8);
        }
    }

    private void putCapabilities(SMBBuffer buffer) {
        if (SMB2Dialect.supportsSmb3x(this.dialects)) {
            buffer.putUInt32(EnumWithValue.EnumUtils.toLong(this.capabilities));
        } else {
            buffer.putReserved4();
        }
    }
}

