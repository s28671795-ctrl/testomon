/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.services.installations.events;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.services.installations.events.CoreServiceInstallationEvent;
import com.incoresoft.services.installations.events.CoreServiceInstallationEventType;
import java.io.IOException;

public class CoreServiceInstallationEventDeserializer
extends JsonDeserializer<CoreServiceInstallationEvent> {
    @Override
    public CoreServiceInstallationEvent deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        Object node = jsonParser.getCodec().readTree(jsonParser);
        try (JsonParser newParser = node.traverse(jsonParser.getCodec());){
            newParser.nextToken();
            String typeValue = ((TextNode)node.get("type")).textValue();
            CoreServiceInstallationEventType type = CoreServiceInstallationEventType.parse(typeValue);
            Class<? extends CoreServiceInstallationEvent> messageClass = type.getMessageClass();
            CoreServiceInstallationEvent coreServiceInstallationEvent = Serialization.objectMapper().treeToValue((TreeNode)node, messageClass);
            return coreServiceInstallationEvent;
        }
    }
}

