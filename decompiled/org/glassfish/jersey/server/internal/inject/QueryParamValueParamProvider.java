/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.util.function.Function;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ParamException;
import org.glassfish.jersey.server.internal.inject.AbstractValueParamProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

@Singleton
final class QueryParamValueParamProvider
extends AbstractValueParamProvider {
    public QueryParamValueParamProvider(Provider<MultivaluedParameterExtractorProvider> mpep) {
        super(mpep, Parameter.Source.QUERY);
    }

    @Override
    public Function<ContainerRequest, ?> createValueProvider(Parameter parameter) {
        String parameterName = parameter.getSourceName();
        if (parameterName == null || parameterName.length() == 0) {
            return null;
        }
        MultivaluedParameterExtractor<?> e = this.get(parameter);
        if (e == null) {
            return null;
        }
        return new QueryParamValueProvider(e, !parameter.isEncoded());
    }

    private static final class QueryParamValueProvider
    implements Function<ContainerRequest, Object> {
        private final MultivaluedParameterExtractor<?> extractor;
        private final boolean decode;

        QueryParamValueProvider(MultivaluedParameterExtractor<?> extractor, boolean decode) {
            this.extractor = extractor;
            this.decode = decode;
        }

        @Override
        public Object apply(ContainerRequest containerRequest) {
            try {
                return this.extractor.extract(containerRequest.getUriInfo().getQueryParameters(this.decode));
            }
            catch (ExtractorException e) {
                throw new ParamException.QueryParamException(e.getCause(), this.extractor.getName(), this.extractor.getDefaultValueString());
            }
        }
    }
}

