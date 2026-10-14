/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers.mq;

import com.alibaba.fastjson.annotation.JSONField;

public final class LogFileContentRequestParamsDTO {
    @JSONField(name="full_path")
    private final String path;
    @JSONField(name="lines_from_file_end")
    private final int lineFromFileEnd;

    public LogFileContentRequestParamsDTO(String path, int lineFromFileEnd) {
        this.path = path;
        this.lineFromFileEnd = lineFromFileEnd;
    }

    public String getPath() {
        return this.path;
    }

    public int getLineFromFileEnd() {
        return this.lineFromFileEnd;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LogFileContentRequestParamsDTO)) {
            return false;
        }
        LogFileContentRequestParamsDTO other = (LogFileContentRequestParamsDTO)o;
        if (this.getLineFromFileEnd() != other.getLineFromFileEnd()) {
            return false;
        }
        String this$path = this.getPath();
        String other$path = other.getPath();
        return !(this$path == null ? other$path != null : !this$path.equals(other$path));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getLineFromFileEnd();
        String $path = this.getPath();
        result = result * 59 + ($path == null ? 43 : $path.hashCode());
        return result;
    }

    public String toString() {
        return "LogFileContentRequestParamsDTO(path=" + this.getPath() + ", lineFromFileEnd=" + this.getLineFromFileEnd() + ")";
    }
}

