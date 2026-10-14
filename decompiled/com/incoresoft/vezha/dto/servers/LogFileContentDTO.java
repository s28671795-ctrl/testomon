/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.servers;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.dto.servers.mq.LogFileContentMqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

public final class LogFileContentDTO {
    @JSONField(name="content")
    @Schema(name="content", description="Log file contents.", implementation=String.class, example="2022-08-18 10:17:24,160 DEBUG [Stream_nats_notify.cpp:98] [10] Send status 1")
    private final String content;

    public LogFileContentDTO(@NotNull LogFileContentMqDTO mqDTO) {
        this.content = mqDTO.getContent();
    }

    public String getContent() {
        return this.content;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LogFileContentDTO)) {
            return false;
        }
        LogFileContentDTO other = (LogFileContentDTO)o;
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
        return "LogFileContentDTO(content=" + this.getContent() + ")";
    }
}

