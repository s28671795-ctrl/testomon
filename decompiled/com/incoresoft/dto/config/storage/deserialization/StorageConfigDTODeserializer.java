/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.config.storage.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.incoresoft.dto.config.storage.StorageConfigDTO;
import com.incoresoft.dto.config.storage.StorageType;
import com.incoresoft.middleware.http.serialization.Serialization;
import java.io.IOException;

public class StorageConfigDTODeserializer
extends JsonDeserializer<StorageConfigDTO> {
    @Override
    public StorageConfigDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object node = jsonParser.getCodec().readTree(jsonParser);
        try (JsonParser newParser = node.traverse(jsonParser.getCodec());){
            newParser.nextToken();
            String typeValue = ((TextNode)node.get("type")).textValue();
            StorageType type = StorageType.parse(typeValue);
            Class<? extends StorageConfigDTO> messageClass = type.getConfigDtoClass();
            StorageConfigDTO storageConfigDTO = Serialization.objectMapper().treeToValue((TreeNode)node, messageClass);
            return storageConfigDTO;
        }
    }
}

