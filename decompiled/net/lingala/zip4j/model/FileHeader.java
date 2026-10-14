/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import java.util.Objects;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.model.AbstractFileHeader;

public class FileHeader
extends AbstractFileHeader {
    private int versionMadeBy;
    private int fileCommentLength = 0;
    private int diskNumberStart;
    private byte[] internalFileAttributes;
    private byte[] externalFileAttributes;
    private long offsetLocalHeader;
    private String fileComment;

    public FileHeader() {
        this.setSignature(HeaderSignature.CENTRAL_DIRECTORY);
    }

    public int getVersionMadeBy() {
        return this.versionMadeBy;
    }

    public void setVersionMadeBy(int versionMadeBy) {
        this.versionMadeBy = versionMadeBy;
    }

    public int getFileCommentLength() {
        return this.fileCommentLength;
    }

    public void setFileCommentLength(int fileCommentLength) {
        this.fileCommentLength = fileCommentLength;
    }

    public int getDiskNumberStart() {
        return this.diskNumberStart;
    }

    public void setDiskNumberStart(int diskNumberStart) {
        this.diskNumberStart = diskNumberStart;
    }

    public byte[] getInternalFileAttributes() {
        return this.internalFileAttributes;
    }

    public void setInternalFileAttributes(byte[] internalFileAttributes) {
        this.internalFileAttributes = internalFileAttributes;
    }

    public byte[] getExternalFileAttributes() {
        return this.externalFileAttributes;
    }

    public void setExternalFileAttributes(byte[] externalFileAttributes) {
        this.externalFileAttributes = externalFileAttributes;
    }

    public long getOffsetLocalHeader() {
        return this.offsetLocalHeader;
    }

    public void setOffsetLocalHeader(long offsetLocalHeader) {
        this.offsetLocalHeader = offsetLocalHeader;
    }

    public String getFileComment() {
        return this.fileComment;
    }

    public void setFileComment(String fileComment) {
        this.fileComment = fileComment;
    }

    public String toString() {
        return this.getFileName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        FileHeader that = (FileHeader)o;
        return this.determineOffsetOfLocalFileHeader(this) == this.determineOffsetOfLocalFileHeader(that);
    }

    public int hashCode() {
        return Objects.hash(this.getFileName(), this.determineOffsetOfLocalFileHeader(this));
    }

    private long determineOffsetOfLocalFileHeader(FileHeader fileHeader) {
        if (fileHeader.getZip64ExtendedInfo() != null) {
            return fileHeader.getZip64ExtendedInfo().getOffsetLocalHeader();
        }
        return fileHeader.getOffsetLocalHeader();
    }
}

