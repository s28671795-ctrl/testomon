/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.msfscc.directory;

import com.hierynomus.msfscc.FileNotifyAction;
import com.hierynomus.protocol.commons.Charsets;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;

public class FileNotifyInformation {
    private int nextEntryOffset;
    private FileNotifyAction action;
    private String fileName;

    public void read(Buffer<?> buffer) throws Buffer.BufferException {
        this.nextEntryOffset = buffer.readUInt32AsInt();
        this.action = EnumWithValue.EnumUtils.valueOf(buffer.readUInt32(), FileNotifyAction.class, null);
        long fileNameLength = buffer.readUInt32();
        this.fileName = buffer.readString(Charsets.UTF_16LE, (int)fileNameLength / 2);
    }

    public int getNextEntryOffset() {
        return this.nextEntryOffset;
    }

    public FileNotifyAction getAction() {
        return this.action;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String toString() {
        return "FileNotifyInformation{action=" + this.action + ", fileName='" + this.fileName + '\'' + '}';
    }
}

