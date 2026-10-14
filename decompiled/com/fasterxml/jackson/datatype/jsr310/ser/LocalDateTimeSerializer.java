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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class LocalDateTimeSerializer
extends JSR310FormattedSerializerBase<LocalDateTime> {
    private static final long serialVersionUID = 1L;
    public static final LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();

    protected LocalDateTimeSerializer() {
        this((DateTimeFormatter)null);
    }

    public LocalDateTimeSerializer(DateTimeFormatter f) {
        super(LocalDateTime.class, f);
    }

    protected LocalDateTimeSerializer(LocalDateTimeSerializer base, Boolean useTimestamp, Boolean useNanoseconds, DateTimeFormatter f) {
        super(base, useTimestamp, useNanoseconds, f, null);
    }

    @Override
    protected JSR310FormattedSerializerBase<LocalDateTime> withFormat(Boolean useTimestamp, DateTimeFormatter f, JsonFormat.Shape shape) {
        return new LocalDateTimeSerializer(this, useTimestamp, this._useNanoseconds, f);
    }

    protected DateTimeFormatter _defaultFormatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator g2, SerializerProvider provider) throws IOException {
        if (this.useTimestamp(provider)) {
            g2.writeStartArray();
            this._serializeAsArrayContents(value, g2, provider);
            g2.writeEndArray();
        } else {
            DateTimeFormatter dtf = this._formatter;
            if (dtf == null) {
                dtf = this._defaultFormatter();
            }
            g2.writeString(value.format(dtf));
        }
    }

    @Override
    public void serializeWithType(LocalDateTime value, JsonGenerator g2, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g2, typeSer.typeId(value, this.serializationShape(provider)));
        if (typeIdDef.valueShape == JsonToken.START_ARRAY) {
            this._serializeAsArrayContents(value, g2, provider);
        } else {
            DateTimeFormatter dtf = this._formatter;
            if (dtf == null) {
                dtf = this._defaultFormatter();
            }
            g2.writeString(value.format(dtf));
        }
        typeSer.writeTypeSuffix(g2, typeIdDef);
    }

    private final void _serializeAsArrayContents(LocalDateTime value, JsonGenerator g2, SerializerProvider provider) throws IOException {
        g2.writeNumber(value.getYear());
        g2.writeNumber(value.getMonthValue());
        g2.writeNumber(value.getDayOfMonth());
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
    }

    @Override
    protected JsonToken serializationShape(SerializerProvider provider) {
        return this.useTimestamp(provider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
    }

    @Override
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean writeZoneId, Boolean writeNanoseconds) {
        return new LocalDateTimeSerializer(this, this._useTimestamp, writeNanoseconds, this._formatter);
    }
}

