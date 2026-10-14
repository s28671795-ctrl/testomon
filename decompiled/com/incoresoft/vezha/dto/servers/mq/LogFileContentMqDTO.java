/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers.mq;

import com.alibaba.fastjson.annotation.JSONField;

public final class LogFileContentMqDTO {
    @JSONField(name="content")
    private final String content;

    public LogFileContentMqDTO(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LogFileContentMqDTO)) {
            return false;
        }
        LogFileContentMqDTO other = (LogFileContentMqDTO)o;
        String this$content = this.getContent();
        String other$content = other.getContent();
        return !(this$content == null ? other$content != null : !this$content.equals(other$content));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        return result;
    }

    public String toString() {
        return "LogFileContentMqDTO(content=" + this.getContent() + ")";
    }
}

