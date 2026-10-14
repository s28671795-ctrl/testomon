/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.broadcast.events;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugins.broadcast.events.PluginEvent;
import com.incoresoft.plugins.broadcast.events.PluginEventType;
import java.io.IOException;

public class PluginEventDeserializer
extends JsonDeserializer<PluginEvent> {
    @Override
    public PluginEvent deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        Object node = jsonParser.getCodec().readTree(jsonParser);
        try (JsonParser newParser = node.traverse(jsonParser.getCodec());){
            newParser.nextToken();
            String typeValue = ((TextNode)node.get("type")).textValue();
            PluginEventType type = PluginEventType.parse(typeValue);
            Class<? extends PluginEvent> messageClass = type.getMessageClass();
            PluginEvent pluginEvent = Serialization.objectMapper().treeToValue((TreeNode)node, messageClass);
            return pluginEvent;
        }
    }
}

