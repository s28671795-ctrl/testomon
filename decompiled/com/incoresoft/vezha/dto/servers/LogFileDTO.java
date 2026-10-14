/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.servers.mq.LogFileMqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

public final class LogFileDTO {
    @JSONField(name="file_name")
    @Schema(name="file_name", description="File name", implementation=String.class, example="analytics.log")
    private final String fileName;
    @JSONField(name="path")
    @Schema(name="path", description="Relative log file path.", implementation=String.class, example="log/analytics.log")
    private final String path;
    @JSONField(name="last_modified")
    @Schema(name="last_modified", description="Unix timestamp in milliseconds of last file modification", implementation=Long.class, example="1661242787000")
    private final long lastModified;
    @JSONField(name="file_size")
    @Schema(name="file_size", description="Log file size in bytes", implementation=Long.class, example="1045612")
    private final long fileSize;

    public LogFileDTO(@NotNull LogFileMqDTO mqDTO) {
        this.fileName = mqDTO.getFileName();
        this.path = mqDTO.getPath();
        this.lastModified = mqDTO.getLastModified();
        this.fileSize = mqDTO.getFileSize();
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
        if (!(o instanceof LogFileDTO)) {
            return false;
        }
        LogFileDTO other = (LogFileDTO)o;
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
        return "LogFileDTO(fileName=" + this.getFileName() + ", path=" + this.getPath() + ", lastModified=" + this.getLastModified() + ", fileSize=" + this.getFileSize() + ")";
    }
}

