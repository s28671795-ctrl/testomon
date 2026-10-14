/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase;
import java.io.IOException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class OffsetTimeSerializer
extends JSR310FormattedSerializerBase<OffsetTime> {
    private static final long serialVersionUID = 1L;
    public static final OffsetTimeSerializer INSTANCE = new OffsetTimeSerializer();

    protected OffsetTimeSerializer() {
        super(OffsetTime.class);
    }

    protected OffsetTimeSerializer(OffsetTimeSerializer base, Boolean useTimestamp, DateTimeFormatter dtf) {
        this(base, useTimestamp, null, dtf);
    }

    protected OffsetTimeSerializer(OffsetTimeSerializer base, Boolean useTimestamp, Boolean useNanoseconds, DateTimeFormatter dtf) {
        super(base, useTimestamp, useNanoseconds, dtf, null);
    }

    protected OffsetTimeSerializer withFormat(Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
        return new OffsetTimeSerializer(this, useTimestamp, dtf);
    }

    @Override
    public void serialize(OffsetTime time, JsonGenerator g2, SerializerProvider provider) throws IOException {
        if (this.useTimestamp(provider)) {
            g2.writeStartArray();
            this._serializeAsArrayContents(time, g2, provider);
            g2.writeEndArray();
        } else {
            String str = this._formatter == null ? time.toString() : time.format(this._formatter);
            g2.writeString(str);
        }
    }

    @Override
    public void serializeWithType(OffsetTime value, JsonGenerator g2, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g2, typeSer.typeId(value, this.serializationShape(provider)));
        if (typeIdDef.valueShape == JsonToken.START_ARRAY) {
            this._serializeAsArrayContents(value, g2, provider);
        } else {
            String str = this._formatter == null ? value.toString() : value.format(this._formatter);
            g2.writeString(str);
        }
        typeSer.writeTypeSuffix(g2, typeIdDef);
    }

    private final void _serializeAsArrayContents(OffsetTime value, JsonGenerator g2, SerializerProvider provider) throws IOException {
        g2.writeNumber(value.getHour());
        g2.writeNumber(value.getMinute());
        int secs = value.getSecond();
        int nanos = value.getNano();
        if (secs > 0 || nanos > 0) {
            g2.writeNumber(secs);
            if (nanos > 0) {
                if (this.useNanoseconds(provider)) {
                    g2.writeNumber(nanos);
                } else {
                    g2.writeNumber(value.get(ChronoField.MILLI_OF_SECOND));
                }
            }
        }
        g2.writeString(value.getOffset().toString());
    }

    @Override
    protected JsonToken serializationShape(SerializerProvider provider) {
        return this.useTimestamp(provider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
    }

    @Override
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean writeZoneId, Boolean writeNanoseconds) {
        return new OffsetTimeSerializer(this, this._useTimestamp, writeNanoseconds, this._formatter);
    }
}

