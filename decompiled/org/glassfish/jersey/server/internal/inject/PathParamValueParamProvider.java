/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
final class PathParamValueParamProvider
extends AbstractValueParamProvider {
    public PathParamValueParamProvider(Provider<MultivaluedParameterExtractorProvider> mpep) {
        super(mpep, Parameter.Source.PATH);
    }

    @Override
    public Function<ContainerRequest, ?> createValueProvider(Parameter parameter) {
        ParameterizedType pt;
        Type[] targs;
        String parameterName = parameter.getSourceName();
        if (parameterName == null || parameterName.length() == 0) {
            return null;
        }
        Class<?> rawParameterType = parameter.getRawType();
        if (rawParameterType == PathSegment.class) {
            return new PathParamPathSegmentValueSupplier(parameterName, !parameter.isEncoded());
        }
        if (rawParameterType == List.class && parameter.getType() instanceof ParameterizedType && (targs = (pt = (ParameterizedType)parameter.getType()).getActualTypeArguments()).length == 1 && targs[0] == PathSegment.class) {
            return new PathParamListPathSegmentValueSupplier(parameterName, !parameter.isEncoded());
        }
        MultivaluedParameterExtractor<?> e = this.get(parameter);
        if (e == null) {
            return null;
        }
        return new PathParamValueProvider(e, !parameter.isEncoded());
    }

    private static final class PathParamListPathSegmentValueSupplier
    implements Function<ContainerRequest, List<PathSegment>> {
        private final String name;
        private final boolean decode;

        PathParamListPathSegmentValueSupplier(String name, boolean decode) {
            this.name = name;
            this.decode = decode;
        }

        @Override
        public List<PathSegment> apply(ContainerRequest request) {
            return request.getUriInfo().getPathSegments(this.name, this.decode);
        }
    }

    private static final class PathParamPathSegmentValueSupplier
    implements Function<ContainerRequest, PathSegment> {
        private final String name;
        private final boolean decode;

        PathParamPathSegmentValueSupplier(String name, boolean decode) {
            this.name = name;
            this.decode = decode;
        }

        @Override
        public PathSegment apply(ContainerRequest request) {
            List<PathSegment> ps = request.getUriInfo().getPathSegments(this.name, this.decode);
            if (ps.isEmpty()) {
                return null;
            }
            return ps.get(ps.size() - 1);
        }
    }

    private static final class PathParamValueProvider
    implements Function<ContainerRequest, Object> {
        private final MultivaluedParameterExtractor<?> extractor;
        private final boolean decode;

        PathParamValueProvider(MultivaluedParameterExtractor<?> extractor, boolean decode) {
            this.extractor = extractor;
            this.decode = decode;
        }

        @Override
        public Object apply(ContainerRequest request) {
            try {
                return this.extractor.extract(request.getUriInfo().getPathParameters(this.decode));
            }
            catch (ExtractorException e) {
                throw new ParamException.PathParamException(e.getCause(), this.extractor.getName(), this.extractor.getDefaultValueString());
            }
        }
    }
}

