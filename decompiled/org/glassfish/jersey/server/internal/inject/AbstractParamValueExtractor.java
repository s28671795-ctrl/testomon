/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.internal.util.collection.UnsafeValue;
import org.glassfish.jersey.internal.util.collection.Values;

abstract class AbstractParamValueExtractor<T> {
    private final ParamConverter<T> paramConverter;
    private final String parameterName;
    private final String defaultValueString;
    private final UnsafeValue<T, RuntimeException> convertedDefaultValue;

    protected AbstractParamValueExtractor(ParamConverter<T> converter, String parameterName, final String defaultValueString) {
        this.paramConverter = converter;
        this.parameterName = parameterName;
        this.defaultValueString = defaultValueString;
        if (defaultValueString != null) {
            this.convertedDefaultValue = Values.lazy(new UnsafeValue<T, RuntimeException>(){

                @Override
                public T get() throws RuntimeException {
                    return AbstractParamValueExtractor.this.convert(defaultValueString);
                }
            });
            if (!converter.getClass().isAnnotationPresent(ParamConverter.Lazy.class)) {
                this.convertedDefaultValue.get();
            }
        } else {
            this.convertedDefaultValue = null;
        }
    }

    public String getName() {
        return this.parameterName;
    }

    public String getDefaultValueString() {
        return this.defaultValueString;
    }

    protected final T fromString(String value) {
        T result = this.convert(value);
        if (result == null) {
            return this.defaultValue();
        }
        return result;
    }

    private T convert(String value) {
        try {
            return this.paramConverter.fromString(value);
        }
        catch (WebApplicationException wae) {
            throw wae;
        }
        catch (IllegalArgumentException iae) {
            throw iae;
        }
        catch (Exception ex) {
            throw new ExtractorException(ex);
        }
    }

    protected final boolean isDefaultValueRegistered() {
        return this.defaultValueString != null;
    }

    protected final T defaultValue() {
        if (!this.isDefaultValueRegistered()) {
            return null;
        }
        return this.convertedDefaultValue.get();
    }
}

