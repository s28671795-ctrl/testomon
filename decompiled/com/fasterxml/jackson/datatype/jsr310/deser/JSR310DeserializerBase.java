/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.Arrays;

abstract class JSR310DeserializerBase<T>
extends StdScalarDeserializer<T> {
    private static final long serialVersionUID = 1L;
    protected final boolean _isLenient;

    protected JSR310DeserializerBase(Class<T> supportedType) {
        super(supportedType);
        this._isLenient = true;
    }

    protected JSR310DeserializerBase(Class<T> supportedType, Boolean leniency) {
        super(supportedType);
        this._isLenient = !Boolean.FALSE.equals(leniency);
    }

    protected JSR310DeserializerBase(JSR310DeserializerBase<T> base) {
        super((StdScalarDeserializer<?>)base);
        this._isLenient = base._isLenient;
    }

    protected JSR310DeserializerBase(JSR310DeserializerBase<T> base, Boolean leniency) {
        super((StdScalarDeserializer<?>)base);
        this._isLenient = !Boolean.FALSE.equals(leniency);
    }

    protected abstract JSR310DeserializerBase<T> withLeniency(Boolean var1);

    protected boolean isLenient() {
        return this._isLenient;
    }

    protected T _fromEmptyString(JsonParser p, DeserializationContext ctxt, String str) throws IOException {
        CoercionAction act = this._checkFromStringCoercion(ctxt, str);
        switch (act) {
            case AsEmpty: {
                return (T)this.getEmptyValue(ctxt);
            }
        }
        if (!this._isLenient) {
            return this._failForNotLenient(p, ctxt, JsonToken.VALUE_STRING);
        }
        return null;
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.DateTime;
    }

    @Override
    public Object deserializeWithType(JsonParser parser, DeserializationContext context, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(parser, context);
    }

    protected boolean _isValidTimestampString(String str) {
        return this._isIntNumber(str) && NumberInput.inLongRange(str, str.charAt(0) == '-');
    }

    protected <BOGUS> BOGUS _reportWrongToken(DeserializationContext context, JsonToken exp, String unit) throws IOException {
        context.reportWrongTokenException(this, exp, "Expected %s for '%s' of %s value", exp.name(), unit, this.handledType().getName());
        return null;
    }

    protected <BOGUS> BOGUS _reportWrongToken(JsonParser parser, DeserializationContext context, JsonToken ... expTypes) throws IOException {
        return (BOGUS)context.reportInputMismatch(this.handledType(), "Unexpected token (%s), expected one of %s for %s value", new Object[]{parser.getCurrentToken(), Arrays.asList(expTypes).toString(), this.handledType().getName()});
    }

    protected <R> R _handleDateTimeException(DeserializationContext context, DateTimeException e0, String value) throws JsonMappingException {
        try {
            return (R)context.handleWeirdStringValue(this.handledType(), value, "Failed to deserialize %s: (%s) %s", this.handledType().getName(), e0.getClass().getName(), e0.getMessage());
        }
        catch (JsonMappingException e) {
            e.initCause(e0);
            throw e;
        }
        catch (IOException e) {
            if (null == e.getCause()) {
                e.initCause(e0);
            }
            throw JsonMappingException.fromUnexpectedIOE(e);
        }
    }

    protected <R> R _handleUnexpectedToken(DeserializationContext context, JsonParser parser, String message, Object ... args2) throws JsonMappingException {
        try {
            return (R)context.handleUnexpectedToken(this.handledType(), parser.getCurrentToken(), parser, message, args2);
        }
        catch (JsonMappingException e) {
            throw e;
        }
        catch (IOException e) {
            throw JsonMappingException.fromUnexpectedIOE(e);
        }
    }

    protected <R> R _handleUnexpectedToken(DeserializationContext context, JsonParser parser, JsonToken ... expTypes) throws JsonMappingException {
        return this._handleUnexpectedToken(context, parser, "Unexpected token (%s), expected one of %s for %s value", new Object[]{parser.currentToken(), Arrays.asList(expTypes), this.handledType().getName()});
    }

    protected T _failForNotLenient(JsonParser p, DeserializationContext ctxt, JsonToken expToken) throws IOException {
        return (T)ctxt.handleUnexpectedToken(this.handledType(), expToken, p, "Cannot deserialize instance of %s out of %s token: not allowed because 'strict' mode set for property or type (enable 'lenient' handling to allow)", new Object[]{ClassUtil.nameOf(this.handledType()), p.currentToken()});
    }

    protected DateTimeException _peelDTE(DateTimeException e) {
        Throwable t2;
        while ((t2 = e.getCause()) != null && t2 instanceof DateTimeException) {
            e = (DateTimeException)t2;
        }
        return e;
    }
}

