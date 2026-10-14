/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages;

import com.hierynomus.msdtyp.FileTime;
import com.hierynomus.msdtyp.MsDataTypes;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2GlobalCapability;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.messages.negotiate.SMB2NegotiateContext;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SMB2NegotiateResponse
extends SMB2Packet {
    private int securityMode;
    private SMB2Dialect dialect;
    private UUID serverGuid;
    private Set<SMB2GlobalCapability> capabilities = EnumSet.noneOf(SMB2GlobalCapability.class);
    private int maxTransactSize;
    private int maxReadSize;
    private int maxWriteSize;
    private FileTime systemTime;
    private FileTime serverStartTime;
    private byte[] gssToken;
    private List<SMB2NegotiateContext> negotiateContextList;

    @Override
    protected void readMessage(SMBBuffer buffer) throws Buffer.BufferException {
        buffer.skip(2);
        this.securityMode = buffer.readUInt16();
        this.dialect = SMB2Dialect.lookup(buffer.readUInt16());
        int negotiateContextCount = this.readNegotiateContextCount(buffer);
        this.serverGuid = MsDataTypes.readGuid(buffer);
        this.capabilities = EnumWithValue.EnumUtils.toEnumSet(buffer.readUInt32(), SMB2GlobalCapability.class);
        this.maxTransactSize = buffer.readUInt32AsInt();
        this.maxReadSize = buffer.readUInt32AsInt();
        this.maxWriteSize = buffer.readUInt32AsInt();
        this.systemTime = MsDataTypes.readFileTime(buffer);
        this.serverStartTime = MsDataTypes.readFileTime(buffer);
        int securityBufferOffset = buffer.readUInt16();
        int securityBufferLength = buffer.readUInt16();
        int negotiateContextOffset = this.readNegotiateContextOffset(buffer);
        this.gssToken = this.readSecurityBuffer(buffer, securityBufferOffset, securityBufferLength);
        this.negotiateContextList = this.readNegotiateContextList(buffer, negotiateContextOffset, negotiateContextCount);
    }

    private List<SMB2NegotiateContext> readNegotiateContextList(SMBBuffer buffer, int negotiateContextOffset, int negotiateContextCount) {
        if (this.dialect == SMB2Dialect.SMB_3_1_1) {
            buffer.rpos(negotiateContextOffset);
            try {
                ArrayList<SMB2NegotiateContext> negotiateContextList = new ArrayList<SMB2NegotiateContext>();
                for (int i = 0; i < negotiateContextCount; ++i) {
                    SMB2NegotiateContext negotiateContext = SMB2NegotiateContext.factory(buffer);
                    negotiateContextList.add(negotiateContext);
                }
                return negotiateContextList;
            }
            catch (Buffer.BufferException e) {
                throw new IllegalArgumentException("unknown error when parse negotiateContext", e);
            }
        }
        return Collections.emptyList();
    }

    private byte[] readSecurityBuffer(SMBBuffer buffer, int securityBufferOffset, int securityBufferLength) throws Buffer.BufferException {
        if (securityBufferLength > 0) {
            buffer.rpos(securityBufferOffset);
            return buffer.readRawBytes(securityBufferLength);
        }
        return new byte[0];
    }

    private int readNegotiateContextOffset(SMBBuffer buffer) throws Buffer.BufferException {
        if (this.dialect == SMB2Dialect.SMB_3_1_1) {
            return buffer.readUInt16();
        }
        buffer.skip(2);
        return 0;
    }

    private int readNegotiateContextCount(Buffer<?> buffer) throws Buffer.BufferException {
        if (this.dialect == SMB2Dialect.SMB_3_1_1) {
            return buffer.readUInt16();
        }
        buffer.skip(2);
        return 0;
    }

    public byte[] getGssToken() {
        return this.gssToken;
    }

    public int getSecurityMode() {
        return this.securityMode;
    }

    public SMB2Dialect getDialect() {
        return this.dialect;
    }

    public UUID getServerGuid() {
        return this.serverGuid;
    }

    public Set<SMB2GlobalCapability> getCapabilities() {
        return this.capabilities;
    }

    public int getMaxTransactSize() {
        return this.maxTransactSize;
    }

    public int getMaxReadSize() {
        return this.maxReadSize;
    }

    public int getMaxWriteSize() {
        return this.maxWriteSize;
    }

    public FileTime getSystemTime() {
        return this.systemTime;
    }

    public FileTime getServerStartTime() {
        return this.serverStartTime;
    }

    public List<SMB2NegotiateContext> getNegotiateContextList() {
        return this.negotiateContextList;
    }
}

