/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers.mq;

import com.alibaba.fastjson.annotation.JSONField;

public final class LogFileMqDTO {
    @JSONField(name="file_name")
    private final String fileName;
    @JSONField(name="path")
    private final String path;
    @JSONField(name="last_modified")
    private final long lastModified;
    @JSONField(name="file_size")
    private final long fileSize;

    public LogFileMqDTO(String fileName, String path, long lastModified, long fileSize) {
        this.fileName = fileName;
        this.path = path;
        this.lastModified = lastModified;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getPath() {
        return this.path;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LogFileMqDTO)) {
            return false;
        }
        LogFileMqDTO other = (LogFileMqDTO)o;
        if (this.getLastModified() != other.getLastModified()) {
            return false;
        }
        if (this.getFileSize() != other.getFileSize()) {
            return false;
        }
        String this$fileName = this.getFileName();
        String other$fileName = other.getFileName();
        if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) {
            return false;
        }
        String this$path = this.getPath();
        String other$path = other.getPath();
        return !(this$path == null ? other$path != null : !this$path.equals(other$path));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $lastModified = this.getLastModified();
        result = result * 59 + (int)($lastModified >>> 32 ^ $lastModified);
        long $fileSize = this.getFileSize();
        result = result * 59 + (int)($fileSize >>> 32 ^ $fileSize);
        String $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        String $path = this.getPath();
        result = result * 59 + ($path == null ? 43 : $path.hashCode());
        return result;
    }

    public String toString() {
        return "LogFileMqDTO(fileName=" + this.getFileName() + ", path=" + this.getPath() + ", lastModified=" + this.getLastModified() + ", fileSize=" + this.getFileSize() + ")";
    }
}

