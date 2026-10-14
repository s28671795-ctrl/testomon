/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.ntlm.messages;

import com.hierynomus.ntlm.messages.NtlmNegotiateFlag;
import com.hierynomus.ntlm.messages.NtlmPacket;
import com.hierynomus.ntlm.messages.TargetInfo;
import com.hierynomus.ntlm.messages.WindowsVersion;
import com.hierynomus.protocol.commons.Charsets;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import java.util.EnumSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NtlmChallenge
extends NtlmPacket {
    private static final Logger logger = LoggerFactory.getLogger(NtlmChallenge.class);
    private int targetNameLen;
    private int targetNameBufferOffset;
    private EnumSet<NtlmNegotiateFlag> negotiateFlags;
    private byte[] serverChallenge;
    private WindowsVersion version;
    private int targetInfoLen;
    private int targetInfoBufferOffset;
    private String targetName;
    private TargetInfo targetInfo;

    @Override
    public void read(Buffer.PlainBuffer buffer) throws Buffer.BufferException {
        buffer.readString(Charsets.UTF_8, 8);
        buffer.readUInt32();
        this.readTargetNameFields(buffer);
        this.negotiateFlags = EnumWithValue.EnumUtils.toEnumSet(buffer.readUInt32(), NtlmNegotiateFlag.class);
        this.serverChallenge = buffer.readRawBytes(8);
        buffer.skip(8);
        this.readTargetInfoFields(buffer);
        this.readVersion(buffer);
        this.readTargetName(buffer);
        this.readTargetInfo(buffer);
    }

    private void readTargetInfo(Buffer.PlainBuffer buffer) throws Buffer.BufferException {
        if (this.targetInfoLen > 0) {
            buffer.rpos(this.targetInfoBufferOffset);
            this.targetInfo = new TargetInfo().readFrom(buffer);
        }
    }

    private void readTargetName(Buffer.PlainBuffer buffer) throws Buffer.BufferException {
        if (this.targetNameLen > 0) {
            buffer.rpos(this.targetNameBufferOffset);
            this.targetName = buffer.readString(Charsets.UTF_16LE, this.targetNameLen / 2);
        }
    }

    private void readVersion(Buffer.PlainBuffer buffer) throws Buffer.BufferException {
        if (this.negotiateFlags.contains(NtlmNegotiateFlag.NTLMSSP_NEGOTIATE_VERSION)) {
            this.version = new WindowsVersion().readFrom(buffer);
            logger.debug("Windows version = {}", (Object)this.version);
        } else {
            buffer.skip(8);
        }
    }

    private void readTargetNameFields(Buffer.PlainBuffer buffer) throws Buffer.BufferException {
        this.targetNameLen = buffer.readUInt16();
        buffer.skip(2);
        this.targetNameBufferOffset = buffer.readUInt32AsInt();
    }

    private void readTargetInfoFields(Buffer.PlainBuffer buffer) throws Buffer.BufferException {
        if (this.negotiateFlags.contains(NtlmNegotiateFlag.NTLMSSP_NEGOTIATE_TARGET_INFO)) {
            this.targetInfoLen = buffer.readUInt16();
            buffer.skip(2);
            this.targetInfoBufferOffset = buffer.readUInt32AsInt();
        } else {
            buffer.skip(8);
        }
    }

    public String getTargetName() {
        return this.targetName;
    }

    public byte[] getServerChallenge() {
        return this.serverChallenge;
    }

    public EnumSet<NtlmNegotiateFlag> getNegotiateFlags() {
        return this.negotiateFlags;
    }

    public TargetInfo getTargetInfo() {
        return this.targetInfo;
    }

    public WindowsVersion getVersion() {
        return this.version;
    }
}

