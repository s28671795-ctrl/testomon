/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.util.function.Function;
import javax.inject.Provider;
import javax.ws.rs.container.AsyncResponse;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

final class AsyncResponseValueParamProvider
implements ValueParamProvider {
    private final Provider<AsyncContext> asyncContextProvider;

    public AsyncResponseValueParamProvider(Provider<AsyncContext> asyncContextProvider) {
        this.asyncContextProvider = asyncContextProvider;
    }

    public Function<ContainerRequest, AsyncResponse> getValueProvider(Parameter parameter) {
        if (parameter.getSource() != Parameter.Source.SUSPENDED) {
            return null;
        }
        if (!AsyncResponse.class.isAssignableFrom(parameter.getRawType())) {
            return null;
        }
        return containerRequest -> this.asyncContextProvider.get();
    }

    @Override
    public ValueParamProvider.PriorityType getPriority() {
        return ValueParamProvider.Priority.NORMAL;
    }
}

