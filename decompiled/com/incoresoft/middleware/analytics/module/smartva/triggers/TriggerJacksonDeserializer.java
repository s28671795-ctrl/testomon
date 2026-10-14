/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.module.smartva.triggers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incoresoft.middleware.analytics.models.smartva.TriggerType;
import com.incoresoft.middleware.analytics.module.smartva.triggers.Trigger;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriggerJacksonDeserializer
extends JsonDeserializer<Trigger> {
    private static final Logger log = LoggerFactory.getLogger(TriggerJacksonDeserializer.class);

    @Override
    public Trigger deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper2 = (ObjectMapper)p.getCodec();
        JsonNode node = (JsonNode)mapper2.readTree(p);
        JsonNode typeNode = node.get("type");
        if (typeNode == null || typeNode.isNull()) {
            return null;
        }
        String typeString = typeNode.asText();
        try {
            TriggerType triggerType = TriggerType.valueOf(typeString.toUpperCase());
            return mapper2.treeToValue((TreeNode)node, triggerType.getTriggerClass());
        }
        catch (IllegalArgumentException e) {
            log.error("Unknown variant: {}", (Object)typeString, (Object)e);
            return null;
        }
    }
}

