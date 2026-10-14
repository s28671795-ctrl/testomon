/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.util.Map;
import java.util.function.Function;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.core.Cookie;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ParamException;
import org.glassfish.jersey.server.internal.inject.AbstractValueParamProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

@Singleton
final class CookieParamValueParamProvider
extends AbstractValueParamProvider {
    public CookieParamValueParamProvider(Provider<MultivaluedParameterExtractorProvider> mpep) {
        super(mpep, Parameter.Source.COOKIE);
    }

    @Override
    public Function<ContainerRequest, ?> createValueProvider(Parameter parameter) {
        String parameterName = parameter.getSourceName();
        if (parameterName == null || parameterName.length() == 0) {
            return null;
        }
        if (parameter.getRawType() == Cookie.class) {
            return new CookieTypeParamValueProvider(parameterName);
        }
        MultivaluedParameterExtractor<?> e = this.get(parameter);
        if (e == null) {
            return null;
        }
        return new CookieParamValueProvider(e);
    }

    private static final class CookieTypeParamValueProvider
    implements Function<ContainerRequest, Cookie> {
        private final String name;

        CookieTypeParamValueProvider(String name) {
            this.name = name;
        }

        @Override
        public Cookie apply(ContainerRequest containerRequest) {
            return containerRequest.getCookies().get(this.name);
        }
    }

    private static final class CookieParamValueProvider
    implements Function<ContainerRequest, Object> {
        private final MultivaluedParameterExtractor<?> extractor;

        CookieParamValueProvider(MultivaluedParameterExtractor<?> extractor) {
            this.extractor = extractor;
        }

        @Override
        public Object apply(ContainerRequest containerRequest) {
            MultivaluedStringMap cookies = new MultivaluedStringMap();
            for (Map.Entry<String, Cookie> e : containerRequest.getCookies().entrySet()) {
                cookies.putSingle(e.getKey(), e.getValue().getValue());
            }
            try {
                return this.extractor.extract(cookies);
            }
            catch (ExtractorException ex) {
                throw new ParamException.CookieParamException(ex.getCause(), this.extractor.getName(), this.extractor.getDefaultValueString());
            }
        }
    }
}

