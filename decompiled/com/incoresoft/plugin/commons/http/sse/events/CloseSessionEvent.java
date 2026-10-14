/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.sse.events;

import com.incoresoft.plugin.commons.http.sse.events.SSEEvent;

public record CloseSessionEvent() implements SSEEvent
{
    @Override
    public String getType() {
        return "close_session_event";
    }
}

