/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.util.function.Function;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.inject.AbstractValueParamProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

@Singleton
class EntityParamValueParamProvider
extends AbstractValueParamProvider {
    EntityParamValueParamProvider(Provider<MultivaluedParameterExtractorProvider> mpep) {
        super(mpep, Parameter.Source.ENTITY);
    }

    @Override
    protected Function<ContainerRequest, ?> createValueProvider(Parameter parameter) {
        return new EntityValueSupplier(parameter);
    }

    private static class EntityValueSupplier
    implements Function<ContainerRequest, Object> {
        private final Parameter parameter;

        public EntityValueSupplier(Parameter parameter) {
            this.parameter = parameter;
        }

        @Override
        public Object apply(ContainerRequest containerRequest) {
            ContainerRequest value;
            Class<?> rawType = this.parameter.getRawType();
            if ((Request.class.isAssignableFrom(rawType) || ContainerRequestContext.class.isAssignableFrom(rawType)) && rawType.isInstance(containerRequest)) {
                value = containerRequest;
            } else {
                value = containerRequest.readEntity(rawType, this.parameter.getType(), this.parameter.getAnnotations());
                if (rawType.isPrimitive() && value == null) {
                    throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST).entity(LocalizationMessages.ERROR_PRIMITIVE_TYPE_NULL()).build());
                }
            }
            return value;
        }
    }
}

