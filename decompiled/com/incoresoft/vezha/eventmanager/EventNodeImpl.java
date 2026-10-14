/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.eventmanager.EventNode;
import com.incoresoft.vezha.dto.eventmanager.NodeDTO;
import com.incoresoft.vezha.eventmanager.node.Node;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class EventNodeImpl
implements EventNode {
    private final Node node;

    public EventNodeImpl(@NotNull Node node) {
        this.node = node;
    }

    public EventNodeImpl(@NotNull NodeDTO dto) {
        this.node = new Node(dto);
    }

    @Override
    public String getId() {
        return this.node.getId();
    }

    @Override
    public String getExtensionId() {
        return this.node.getType();
    }

    @Override
    public Map<String, String> getParams() {
        return ((JSONObject)JSON.toJSON(this.node.getParams())).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, it -> String.valueOf(it.getValue())));
    }

    public Map<Integer, String> getOutputs() {
        return this.node.getOutputs();
    }
}

