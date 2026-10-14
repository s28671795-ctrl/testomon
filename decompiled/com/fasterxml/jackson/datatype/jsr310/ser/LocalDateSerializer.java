/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer
extends JSR310FormattedSerializerBase<LocalDate> {
    private static final long serialVersionUID = 1L;
    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();

    protected LocalDateSerializer() {
        super(LocalDate.class);
    }

    protected LocalDateSerializer(LocalDateSerializer base, Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
        super(base, useTimestamp, dtf, shape);
    }

    public LocalDateSerializer(DateTimeFormatter formatter) {
        super(LocalDate.class, formatter);
    }

    protected LocalDateSerializer withFormat(Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
        return new LocalDateSerializer(this, useTimestamp, dtf, shape);
    }

    @Override
    public void serialize(LocalDate date, JsonGenerator g2, SerializerProvider provider) throws IOException {
        if (this.useTimestamp(provider)) {
            if (this._shape == JsonFormat.Shape.NUMBER_INT) {
                g2.writeNumber(date.toEpochDay());
            } else {
                g2.writeStartArray();
                this._serializeAsArrayContents(date, g2, provider);
                g2.writeEndArray();
            }
        } else {
            g2.writeString(this._formatter == null ? date.toString() : date.format(this._formatter));
        }
    }

    @Override
    public void serializeWithType(LocalDate value, JsonGenerator g2, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g2, typeSer.typeId(value, this.serializationShape(provider)));
        switch (typeIdDef.valueShape) {
            case START_ARRAY: {
                this._serializeAsArrayContents(value, g2, provider);
                break;
            }
            case VALUE_NUMBER_INT: {
                g2.writeNumber(value.toEpochDay());
                break;
            }
            default: {
                g2.writeString(this._formatter == null ? value.toString() : value.format(this._formatter));
            }
        }
        typeSer.writeTypeSuffix(g2, typeIdDef);
    }

    protected void _serializeAsArrayContents(LocalDate value, JsonGenerator g2, SerializerProvider provider) throws IOException {
        g2.writeNumber(value.getYear());
        g2.writeNumber(value.getMonthValue());
        g2.writeNumber(value.getDayOfMonth());
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        boolean useTimestamp;
        SerializerProvider provider = visitor.getProvider();
        boolean bl = useTimestamp = provider != null && this.useTimestamp(provider);
        if (useTimestamp) {
            this._acceptTimestampVisitor(visitor, typeHint);
        } else {
            JsonStringFormatVisitor v2 = visitor.expectStringFormat(typeHint);
            if (v2 != null) {
                v2.format(JsonValueFormat.DATE);
            }
        }
    }

    @Override
    protected JsonToken serializationShape(SerializerProvider provider) {
        if (this.useTimestamp(provider)) {
            if (this._shape == JsonFormat.Shape.NUMBER_INT) {
                return JsonToken.VALUE_NUMBER_INT;
            }
            return JsonToken.START_ARRAY;
        }
        return JsonToken.VALUE_STRING;
    }
}

