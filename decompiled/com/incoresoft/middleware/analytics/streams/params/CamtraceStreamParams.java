/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.streams.params.AbstractStreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CamtraceStreamParams
extends AbstractStreamParams {
    @JSONField(name="camtrace_channel_id")
    @NotNull
    @Min(value=0L)
    private final @NotNull @Min(value=0L) Integer channelId;
    @JSONField(name="camtrace_host")
    @NotBlank
    private final String host;
    @JSONField(name="camtrace_port")
    @NotNull
    @Min(value=0L)
    private final @NotNull @Min(value=0L) Integer port;

    public CamtraceStreamParams(Integer channelId, String host2, Integer port) {
        super(StreamType.camtrace);
        this.channelId = channelId;
        this.host = host2;
        this.port = port;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public String getHost() {
        return this.host;
    }

    public Integer getPort() {
        return this.port;
    }
}

