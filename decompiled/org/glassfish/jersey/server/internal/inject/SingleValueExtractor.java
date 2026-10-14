/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ParamConverter;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.server.internal.inject.AbstractParamValueExtractor;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;

final class SingleValueExtractor<T>
extends AbstractParamValueExtractor<T>
implements MultivaluedParameterExtractor<T> {
    public SingleValueExtractor(ParamConverter<T> converter, String parameterName, String defaultStringValue) {
        super(converter, parameterName, defaultStringValue);
    }

    @Override
    public T extract(MultivaluedMap<String, String> parameters) {
        String value = parameters.getFirst(this.getName());
        try {
            return this.fromString(value == null && this.isDefaultValueRegistered() ? this.getDefaultValueString() : value);
        }
        catch (ProcessingException | WebApplicationException ex) {
            throw ex;
        }
        catch (IllegalArgumentException ex) {
            return this.defaultValue();
        }
        catch (Exception ex) {
            throw new ExtractorException(ex);
        }
    }
}

