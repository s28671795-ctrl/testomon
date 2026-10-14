/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast.events;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugin.commons.http.sse.events.SSEEvent;
import com.incoresoft.plugins.broadcast.events.PluginEventType;

public interface PluginEvent
extends SSEEvent {
    @Override
    @JsonProperty(value="type")
    @JSONField(name="type")
    default public String getType() {
        return PluginEventType.getByMessageClass(this.getClass()).toString();
    }
}

