/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.util.List;
import java.util.function.Function;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.core.PathSegment;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ParamException;
import org.glassfish.jersey.server.internal.inject.AbstractValueParamProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

@Singleton
final class MatrixParamValueParamProvider
extends AbstractValueParamProvider {
    public MatrixParamValueParamProvider(Provider<MultivaluedParameterExtractorProvider> mpep) {
        super(mpep, Parameter.Source.MATRIX);
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
        return new MatrixParamValueProvider(e, !parameter.isEncoded());
    }

    private static final class MatrixParamValueProvider
    implements Function<ContainerRequest, Object> {
        private final MultivaluedParameterExtractor<?> extractor;
        private final boolean decode;

        MatrixParamValueProvider(MultivaluedParameterExtractor<?> extractor, boolean decode) {
            this.extractor = extractor;
            this.decode = decode;
        }

        @Override
        public Object apply(ContainerRequest containerRequest) {
            List<PathSegment> l = containerRequest.getUriInfo().getPathSegments(this.decode);
            PathSegment p = l.get(l.size() - 1);
            try {
                return this.extractor.extract(p.getMatrixParameters());
            }
            catch (ExtractorException e) {
                throw new ParamException.MatrixParamException(e.getCause(), this.extractor.getName(), this.extractor.getDefaultValueString());
            }
        }
    }
}

