/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;

class FactoryBasedEnumDeserializer
extends StdDeserializer<Object>
implements ContextualDeserializer {
    private static final long serialVersionUID = 1L;
    protected final JavaType _inputType;
    protected final AnnotatedMethod _factory;
    protected final JsonDeserializer<?> _deser;
    protected final ValueInstantiator _valueInstantiator;
    protected final SettableBeanProperty[] _creatorProps;
    protected final boolean _hasArgs;
    private transient PropertyBasedCreator _propCreator;

    public FactoryBasedEnumDeserializer(Class<?> cls, AnnotatedMethod f, JavaType paramType, ValueInstantiator valueInstantiator, SettableBeanProperty[] creatorProps) {
        super(cls);
        this._factory = f;
        this._hasArgs = true;
        this._inputType = paramType.hasRawClass(String.class) || paramType.hasRawClass(CharSequence.class) ? null : paramType;
        this._deser = null;
        this._valueInstantiator = valueInstantiator;
        this._creatorProps = creatorProps;
    }

    public FactoryBasedEnumDeserializer(Class<?> cls, AnnotatedMethod f) {
        super(cls);
        this._factory = f;
        this._hasArgs = false;
        this._inputType = null;
        this._deser = null;
        this._valueInstantiator = null;
        this._creatorProps = null;
    }

    protected FactoryBasedEnumDeserializer(FactoryBasedEnumDeserializer base, JsonDeserializer<?> deser) {
        super(base._valueClass);
        this._inputType = base._inputType;
        this._factory = base._factory;
        this._hasArgs = base._hasArgs;
        this._valueInstantiator = base._valueInstantiator;
        this._creatorProps = base._creatorProps;
        this._deser = deser;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        if (this._deser == null && this._inputType != null && this._creatorProps == null) {
            return new FactoryBasedEnumDeserializer(this, ctxt.findContextualValueDeserializer(this._inputType, property));
        }
        return this;
    }

    @Override
    public Boolean supportsUpdate(DeserializationConfig config) {
        return Boolean.FALSE;
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Enum;
    }

    @Override
    public boolean isCachable() {
        return true;
    }

    @Override
    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Object value;
        if (this._deser != null) {
            value = this._deser.deserialize(p, ctxt);
        } else if (this._hasArgs) {
            boolean unwrapping;
            if (this._creatorProps != null) {
                if (!p.isExpectedStartObjectToken()) {
                    JavaType targetType = this.getValueType(ctxt);
                    ctxt.reportInputMismatch(targetType, "Input mismatch reading Enum %s: properties-based `@JsonCreator` (%s) expects JSON Object (JsonToken.START_OBJECT), got JsonToken.%s", new Object[]{ClassUtil.getTypeDescription(targetType), this._factory, p.currentToken()});
                }
                if (this._propCreator == null) {
                    this._propCreator = PropertyBasedCreator.construct(ctxt, this._valueInstantiator, this._creatorProps, ctxt.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES));
                }
                p.nextToken();
                return this.deserializeEnumUsingPropertyBased(p, ctxt, this._propCreator);
            }
            JsonToken t2 = p.currentToken();
            boolean bl = unwrapping = t2 == JsonToken.START_ARRAY && ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
            if (unwrapping) {
                t2 = p.nextToken();
            }
            if (t2 == null || !t2.isScalarValue()) {
                value = "";
                p.skipChildren();
            } else {
                value = p.getValueAsString();
            }
            if (unwrapping && p.nextToken() != JsonToken.END_ARRAY) {
                this.handleMissingEndArrayForSingle(p, ctxt);
            }
        } else {
            p.skipChildren();
            try {
                return this._factory.call();
            }
            catch (Exception e) {
                Throwable t3 = ClassUtil.throwRootCauseIfIOE(e);
                return ctxt.handleInstantiationProblem(this._valueClass, null, t3);
            }
        }
        try {
            return this._factory.callOnWith(this._valueClass, value);
        }
        catch (Exception e) {
            Throwable t4 = ClassUtil.throwRootCauseIfIOE(e);
            if (t4 instanceof IllegalArgumentException && ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
                return null;
            }
            return ctxt.handleInstantiationProblem(this._valueClass, value, t4);
        }
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        if (this._deser == null) {
            return this.deserialize(p, ctxt);
        }
        return typeDeserializer.deserializeTypedFromAny(p, ctxt);
    }

    protected Object deserializeEnumUsingPropertyBased(JsonParser p, DeserializationContext ctxt, PropertyBasedCreator creator) throws IOException {
        PropertyValueBuffer buffer = creator.startBuilding(p, ctxt, null);
        JsonToken t2 = p.currentToken();
        while (t2 == JsonToken.FIELD_NAME) {
            String propName = p.currentName();
            p.nextToken();
            SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
            if (!buffer.readIdProperty(propName) || creatorProp != null) {
                if (creatorProp != null) {
                    buffer.assignParameter(creatorProp, this._deserializeWithErrorWrapping(p, ctxt, creatorProp));
                } else {
                    p.skipChildren();
                }
            }
            t2 = p.nextToken();
        }
        return creator.build(ctxt, buffer);
    }

    protected final Object _deserializeWithErrorWrapping(JsonParser p, DeserializationContext ctxt, SettableBeanProperty prop) throws IOException {
        try {
            return prop.deserialize(p, ctxt);
        }
        catch (Exception e) {
            return this.wrapAndThrow(e, this.handledType(), prop.getName(), ctxt);
        }
    }

    protected Object wrapAndThrow(Throwable t2, Object bean, String fieldName, DeserializationContext ctxt) throws IOException {
        throw JsonMappingException.wrapWithPath(this.throwOrReturnThrowable(t2, ctxt), bean, fieldName);
    }

    private Throwable throwOrReturnThrowable(Throwable t2, DeserializationContext ctxt) throws IOException {
        boolean wrap;
        t2 = ClassUtil.getRootCause(t2);
        ClassUtil.throwIfError(t2);
        boolean bl = wrap = ctxt == null || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
        if (t2 instanceof IOException) {
            if (!wrap || !(t2 instanceof JacksonException)) {
                throw (IOException)t2;
            }
        } else if (!wrap) {
            ClassUtil.throwIfRTE(t2);
        }
        return t2;
    }
}

