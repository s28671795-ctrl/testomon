/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages;

import com.hierynomus.msfscc.directory.FileNotifyInformation;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import java.util.ArrayList;
import java.util.List;

public class SMB2ChangeNotifyResponse
extends SMB2Packet {
    List<FileNotifyInformation> fileNotifyInfoList = new ArrayList<FileNotifyInformation>();

    @Override
    protected void readMessage(SMBBuffer buffer) throws Buffer.BufferException {
        buffer.skip(2);
        int outputBufferOffset = buffer.readUInt16();
        int length = buffer.readUInt32AsInt();
        if (outputBufferOffset > 0 && length > 0) {
            this.fileNotifyInfoList = this.readFileNotifyInfo(buffer, outputBufferOffset);
        }
        buffer.rpos(((SMB2PacketHeader)this.header).getHeaderStartPosition() + outputBufferOffset + length);
    }

    private List<FileNotifyInformation> readFileNotifyInfo(SMBBuffer buffer, int outputBufferOffset) throws Buffer.BufferException {
        ArrayList<FileNotifyInformation> notifyInfoList = new ArrayList<FileNotifyInformation>();
        buffer.rpos(((SMB2PacketHeader)this.header).getHeaderStartPosition() + outputBufferOffset);
        while (true) {
            int entryStartPos = buffer.rpos();
            FileNotifyInformation info = new FileNotifyInformation();
            info.read(buffer);
            notifyInfoList.add(info);
            if (info.getNextEntryOffset() == 0) break;
            buffer.rpos(entryStartPos += info.getNextEntryOffset());
        }
        return notifyInfoList;
    }

    public List<FileNotifyInformation> getFileNotifyInfoList() {
        return this.fileNotifyInfoList;
    }
}

