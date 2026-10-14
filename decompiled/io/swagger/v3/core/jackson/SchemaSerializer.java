/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import io.swagger.v3.oas.models.media.Schema;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

public class SchemaSerializer
extends JsonSerializer<Schema>
implements ResolvableSerializer {
    private JsonSerializer<Object> defaultSerializer;

    public SchemaSerializer(JsonSerializer<Object> serializer) {
        this.defaultSerializer = serializer;
    }

    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        if (this.defaultSerializer instanceof ResolvableSerializer) {
            ((ResolvableSerializer)((Object)this.defaultSerializer)).resolve(serializerProvider);
        }
    }

    @Override
    public void serialize(Schema value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (StringUtils.isBlank(value.get$ref())) {
            if (value.getExampleSetFlag() && value.getExample() == null) {
                jgen.writeStartObject();
                this.defaultSerializer.unwrappingSerializer(null).serialize(value, jgen, provider);
                jgen.writeNullField("example");
                jgen.writeEndObject();
            } else {
                this.defaultSerializer.serialize(value, jgen, provider);
            }
        } else {
            jgen.writeStartObject();
            jgen.writeStringField("$ref", value.get$ref());
            jgen.writeEndObject();
        }
    }
}

