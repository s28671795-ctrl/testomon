/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.mssmb2.SMB3CompressionAlgorithm;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import com.hierynomus.smb.SMBHeader;
import com.hierynomus.smbj.common.Check;

public class SMB2CompressionTransformHeader
implements SMBHeader {
    public static final byte[] COMPRESSED_PROTOCOL_ID = new byte[]{-4, 83, 77, 66};
    private int headerStartPosition;
    private int originalCompressedSegmentSize;
    private SMB3CompressionAlgorithm compressionAlgorithm;
    private int offset;
    private int messageEndPosition;

    @Override
    public void writeTo(SMBBuffer buffer) {
    }

    @Override
    public void readFrom(Buffer<?> buffer) throws Buffer.BufferException {
        this.headerStartPosition = buffer.rpos();
        byte[] protocolId = buffer.readRawBytes(4);
        Check.ensureEquals(protocolId, COMPRESSED_PROTOCOL_ID, "Could not find SMB2 Packet header");
        this.originalCompressedSegmentSize = buffer.readUInt32AsInt();
        this.compressionAlgorithm = EnumWithValue.EnumUtils.valueOf(buffer.readUInt16(), SMB3CompressionAlgorithm.class, null);
        Check.ensure(this.compressionAlgorithm != null && this.compressionAlgorithm != SMB3CompressionAlgorithm.NONE, "The CompressionAlgorithm field of the SMB2_COMPRESSION_TRANSFORM_HEADER should contain a valid value.");
        buffer.skip(2);
        this.offset = buffer.readUInt32AsInt();
        this.messageEndPosition = buffer.wpos();
    }

    @Override
    public int getHeaderStartPosition() {
        return this.headerStartPosition;
    }

    @Override
    public int getMessageEndPosition() {
        return this.messageEndPosition;
    }

    public int getOriginalCompressedSegmentSize() {
        return this.originalCompressedSegmentSize;
    }

    public SMB3CompressionAlgorithm getCompressionAlgorithm() {
        return this.compressionAlgorithm;
    }

    public int getOffset() {
        return this.offset;
    }
}

