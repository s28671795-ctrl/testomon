/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model.internal;

import java.lang.reflect.InvocationHandler;
import javax.inject.Singleton;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.inject.ConfiguredValidator;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.model.internal.AbstractJavaResourceMethodDispatcher;
import org.glassfish.jersey.server.spi.internal.ResourceMethodDispatcher;

@Singleton
final class VoidVoidDispatcherProvider
implements ResourceMethodDispatcher.Provider {
    private final ResourceContext resourceContext;

    VoidVoidDispatcherProvider(ResourceContext resourceContext) {
        this.resourceContext = resourceContext;
    }

    @Override
    public ResourceMethodDispatcher create(Invocable resourceMethod, InvocationHandler handler, ConfiguredValidator validator) {
        if (resourceMethod.getHandlingMethod().getReturnType() != Void.TYPE || !resourceMethod.getParameters().isEmpty()) {
            return null;
        }
        return this.resourceContext.initResource(new VoidToVoidDispatcher(resourceMethod, handler, validator));
    }

    private static class VoidToVoidDispatcher
    extends AbstractJavaResourceMethodDispatcher {
        private VoidToVoidDispatcher(Invocable resourceMethod, InvocationHandler handler, ConfiguredValidator validator) {
            super(resourceMethod, handler, validator);
        }

        @Override
        public Response doDispatch(Object resource, ContainerRequest containerRequest) throws ProcessingException {
            this.invoke(containerRequest, resource, new Object[0]);
            return Response.noContent().build();
        }
    }
}

