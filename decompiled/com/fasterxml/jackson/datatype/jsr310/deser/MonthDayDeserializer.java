/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

public class MonthDayDeserializer
extends JSR310DateTimeDeserializerBase<MonthDay> {
    private static final long serialVersionUID = 1L;
    public static final MonthDayDeserializer INSTANCE = new MonthDayDeserializer();

    public MonthDayDeserializer() {
        this((DateTimeFormatter)null);
    }

    public MonthDayDeserializer(DateTimeFormatter formatter) {
        super(MonthDay.class, formatter);
    }

    protected MonthDayDeserializer withDateFormat(DateTimeFormatter dtf) {
        return new MonthDayDeserializer(dtf);
    }

    protected MonthDayDeserializer(MonthDayDeserializer base, Boolean leniency) {
        super(base, leniency);
    }

    @Override
    protected MonthDayDeserializer withLeniency(Boolean leniency) {
        return new MonthDayDeserializer(this, leniency);
    }

    protected MonthDayDeserializer withShape(JsonFormat.Shape shape) {
        return this;
    }

    @Override
    public MonthDay deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.hasToken(JsonToken.VALUE_STRING)) {
            return this._fromString(parser, context, parser.getText());
        }
        if (parser.isExpectedStartObjectToken()) {
            return this._fromString(parser, context, context.extractScalarFromObject(parser, this, this.handledType()));
        }
        if (parser.isExpectedStartArrayToken()) {
            JsonToken t2 = parser.nextToken();
            if (t2 == JsonToken.END_ARRAY) {
                return null;
            }
            if ((t2 == JsonToken.VALUE_STRING || t2 == JsonToken.VALUE_EMBEDDED_OBJECT) && context.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                MonthDay parsed = this.deserialize(parser, context);
                if (parser.nextToken() != JsonToken.END_ARRAY) {
                    this.handleMissingEndArrayForSingle(parser, context);
                }
                return parsed;
            }
            if (t2 != JsonToken.VALUE_NUMBER_INT) {
                this._reportWrongToken(context, JsonToken.VALUE_NUMBER_INT, "month");
            }
            int month = parser.getIntValue();
            int day = parser.nextIntValue(-1);
            if (day == -1) {
                if (!parser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                    this._reportWrongToken(context, JsonToken.VALUE_NUMBER_INT, "day");
                }
                day = parser.getIntValue();
            }
            if (parser.nextToken() != JsonToken.END_ARRAY) {
                throw context.wrongTokenException(parser, this.handledType(), JsonToken.END_ARRAY, "Expected array to end");
            }
            return MonthDay.of(month, day);
        }
        if (parser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return (MonthDay)parser.getEmbeddedObject();
        }
        return (MonthDay)this._handleUnexpectedToken(context, parser, JsonToken.VALUE_STRING, JsonToken.START_ARRAY);
    }

    protected MonthDay _fromString(JsonParser p, DeserializationContext ctxt, String string0) throws IOException {
        String string = string0.trim();
        if (string.length() == 0) {
            return (MonthDay)this._fromEmptyString(p, ctxt, string);
        }
        try {
            if (this._formatter == null) {
                return MonthDay.parse(string);
            }
            return MonthDay.parse(string, this._formatter);
        }
        catch (DateTimeException e) {
            return (MonthDay)this._handleDateTimeException(ctxt, e, string);
        }
    }
}

