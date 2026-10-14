/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.share;

import com.hierynomus.msfscc.fsctl.FsCtlPipePeekResponse;
import com.hierynomus.mssmb2.SMB2FileId;
import com.hierynomus.mssmb2.messages.SMB2ReadResponse;
import com.hierynomus.mssmb2.messages.SMB2WriteResponse;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.common.SmbPath;
import com.hierynomus.smbj.io.ArrayByteChunkProvider;
import com.hierynomus.smbj.share.Open;
import com.hierynomus.smbj.share.PipeShare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamedPipe
extends Open<PipeShare> {
    private static final long FSCTL_PIPE_PEEK = 1130508L;
    private static final long FSCTL_PIPE_TRANSCEIVE = 1163287L;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    NamedPipe(SMB2FileId fileId, PipeShare share, SmbPath name) {
        super(fileId, name, share);
    }

    public String getName() {
        return this.name.getPath();
    }

    public int write(byte[] buffer) {
        return this.write(buffer, 0, buffer.length);
    }

    public int write(byte[] buffer, int offset, int length) {
        ArrayByteChunkProvider provider = new ArrayByteChunkProvider(buffer, offset, length, 0L);
        this.logger.debug("Writing to {} from offset {}", (Object)this.name, (Object)provider.getOffset());
        SMB2WriteResponse wresp = ((PipeShare)this.share).write(this.fileId, provider);
        return wresp.getBytesWritten();
    }

    public int read(byte[] buffer) {
        return this.read(buffer, 0, buffer.length);
    }

    public int read(byte[] buffer, int offset, int length) {
        SMB2ReadResponse response = ((PipeShare)this.share).read(this.fileId, 0L, length);
        byte[] data = response.getData();
        int bytesRead = Math.min(length, data.length);
        System.arraycopy(data, 0, buffer, offset, bytesRead);
        return bytesRead;
    }

    public byte[] transact(byte[] inBuffer) {
        return this.ioctl(1163287L, true, inBuffer, 0, inBuffer.length);
    }

    public int transact(byte[] inBuffer, byte[] outBuffer) {
        return this.transact(inBuffer, 0, inBuffer.length, outBuffer, 0, outBuffer.length);
    }

    public int transact(byte[] inBuffer, int inOffset, int inLength, byte[] outBuffer, int outOffset, int outLength) {
        return this.ioctl(1163287L, true, inBuffer, inOffset, inLength, outBuffer, outOffset, outLength);
    }

    public FsCtlPipePeekResponse peek() {
        return this.peek(0);
    }

    public FsCtlPipePeekResponse peek(int maxDataSize) {
        byte[] output = ((PipeShare)this.share).ioctl(this.fileId, 1130508L, true, null, 0, 0, 24 + maxDataSize);
        try {
            SMBBuffer buffer = new SMBBuffer(output);
            FsCtlPipePeekResponse peekResponse = new FsCtlPipePeekResponse();
            peekResponse.read(buffer);
            return peekResponse;
        }
        catch (Buffer.BufferException e) {
            throw new SMBRuntimeException(e);
        }
    }

    public byte[] ioctl(long ctlCode, boolean isFsCtl, byte[] inData, int inOffset, int inLength) {
        return ((PipeShare)this.share).ioctl(this.fileId, ctlCode, isFsCtl, inData, inOffset, inLength);
    }

    public int ioctl(long ctlCode, boolean isFsCtl, byte[] inData, int inOffset, int inLength, byte[] outData, int outOffset, int outLength) {
        return ((PipeShare)this.share).ioctl(this.fileId, ctlCode, isFsCtl, inData, inOffset, inLength, outData, outOffset, outLength);
    }
}

