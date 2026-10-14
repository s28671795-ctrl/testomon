/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase;
import java.io.IOException;
import java.time.ZoneId;

public class ZoneIdSerializer
extends ToStringSerializerBase {
    private static final long serialVersionUID = 1L;

    public ZoneIdSerializer() {
        super(ZoneId.class);
    }

    @Override
    public void serializeWithType(Object value, JsonGenerator g2, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g2, typeSer.typeId(value, ZoneId.class, JsonToken.VALUE_STRING));
        this.serialize(value, g2, provider);
        typeSer.writeTypeSuffix(g2, typeIdDef);
    }

    @Override
    public String valueToString(Object value) {
        return value.toString();
    }
}

