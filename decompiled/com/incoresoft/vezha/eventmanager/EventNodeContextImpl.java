/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.eventmanager.EventNode;
import com.incoresoft.middleware.eventmanager.EventNodeContext;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

class EventNodeContextImpl
implements EventNodeContext {
    private final EventNode currentNode;
    private final JSONObject inputParams;

    public EventNodeContextImpl(@NotNull EventNode currentNode, @NotNull JSONObject inputParams) {
        this.currentNode = Objects.requireNonNull(currentNode);
        this.inputParams = Objects.requireNonNull(inputParams);
    }

    @Override
    public EventNode getCurrentNode() {
        return this.currentNode;
    }

    @Override
    public JSONObject getInputParams() {
        return this.inputParams;
    }
}

