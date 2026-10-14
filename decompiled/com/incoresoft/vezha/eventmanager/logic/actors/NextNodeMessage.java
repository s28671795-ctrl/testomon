/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager.logic.actors;

import java.util.Map;

public class NextNodeMessage {
    private final String nextNodeId;
    private final Map<String, String> params;

    public String getNextNodeId() {
        return this.nextNodeId;
    }

    public NextNodeMessage(String nextNodeId, Map<String, String> params) {
        this.nextNodeId = nextNodeId;
        this.params = params;
    }

    public Map<String, String> getParams() {
        return this.params;
    }
}

