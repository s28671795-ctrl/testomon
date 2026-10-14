/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services.installations.events;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.plugin.commons.http.sse.events.SSEEvent;
import com.incoresoft.services.installations.events.CoreServiceInstallationEventType;

public interface CoreServiceInstallationEvent
extends SSEEvent {
    @Override
    @JsonProperty(value="type")
    @JSONField(name="type")
    default public String getType() {
        return CoreServiceInstallationEventType.getByMessageClass(this.getClass()).toString();
    }
}

