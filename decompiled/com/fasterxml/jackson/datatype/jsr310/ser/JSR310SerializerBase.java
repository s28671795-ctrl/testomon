/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

abstract class JSR310SerializerBase<T>
extends StdSerializer<T> {
    private static final long serialVersionUID = 1L;

    protected JSR310SerializerBase(Class<?> supportedType) {
        super(supportedType, false);
    }

    @Override
    public void serializeWithType(T value, JsonGenerator g2, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g2, typeSer.typeId(value, this.serializationShape(provider)));
        this.serialize(value, g2, provider);
        typeSer.writeTypeSuffix(g2, typeIdDef);
    }

    protected abstract JsonToken serializationShape(SerializerProvider var1);
}

