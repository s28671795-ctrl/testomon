/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.swagger.v3.oas.models.callbacks.Callback;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class CallbackSerializer
extends JsonSerializer<Callback> {
    @Override
    public void serialize(Callback value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value != null && value.getExtensions() != null && !value.getExtensions().isEmpty()) {
            jgen.writeStartObject();
            if (StringUtils.isBlank(value.get$ref())) {
                if (!value.isEmpty()) {
                    for (Map.Entry entry : value.entrySet()) {
                        jgen.writeObjectField((String)entry.getKey(), entry.getValue());
                    }
                }
            } else {
                jgen.writeStringField("$ref", value.get$ref());
            }
            for (String string : value.getExtensions().keySet()) {
                jgen.writeObjectField(string, value.getExtensions().get(string));
            }
            jgen.writeEndObject();
        } else if (value == null || StringUtils.isBlank(value.get$ref())) {
            provider.defaultSerializeValue(value, jgen);
        } else {
            jgen.writeStartObject();
            jgen.writeStringField("$ref", value.get$ref());
            jgen.writeEndObject();
        }
    }
}

