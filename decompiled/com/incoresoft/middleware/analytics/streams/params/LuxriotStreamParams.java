/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.streams.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.streams.params.AbstractStreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class LuxriotStreamParams
extends AbstractStreamParams {
    @JSONField(name="luxriot_channel_id")
    @NotNull
    @Min(value=0L)
    private final @NotNull @Min(value=0L) Integer channelId;
    @JSONField(name="luxriot_server_id")
    @NotNull
    @Min(value=0L)
    private final @NotNull @Min(value=0L) Integer serverId;

    public LuxriotStreamParams(Integer channelId, Integer serverId) {
        super(StreamType.luxriot);
        this.channelId = channelId;
        this.serverId = serverId;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public Integer getServerId() {
        return this.serverId;
    }
}

