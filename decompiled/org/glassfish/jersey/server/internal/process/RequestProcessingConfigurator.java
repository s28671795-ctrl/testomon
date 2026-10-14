/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.process;

import java.lang.reflect.Type;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.SupplierClassBinding;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.CloseableService;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.internal.process.RequestProcessingContextReference;
import org.glassfish.jersey.server.internal.process.SecurityContextInjectee;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;

public class RequestProcessingConfigurator
implements BootstrapConfigurator {
    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        injectionManager.register(new ServerProcessingBinder());
    }

    private class ServerProcessingBinder
    extends AbstractBinder {
        private ServerProcessingBinder() {
        }

        @Override
        protected void configure() {
            this.bindAsContract(RequestProcessingContextReference.class).in(RequestScoped.class);
            ((SupplierClassBinding)((SupplierClassBinding)((SupplierClassBinding)this.bindFactory(ContainerRequestFactory.class).to((Type)((Object)ContainerRequest.class))).to((Type)((Object)ContainerRequestContext.class))).proxy(false)).in(RequestScoped.class);
            ((SupplierClassBinding)((SupplierClassBinding)((SupplierClassBinding)((SupplierClassBinding)this.bindFactory(ContainerRequestFactory.class).to((Type)((Object)HttpHeaders.class))).to((Type)((Object)Request.class))).proxy(true)).proxyForSameScope(false)).in(RequestScoped.class);
            ((SupplierClassBinding)((SupplierClassBinding)((SupplierClassBinding)((SupplierClassBinding)((SupplierClassBinding)this.bindFactory(UriRoutingContextFactory.class).to((Type)((Object)UriInfo.class))).to((Type)((Object)ExtendedUriInfo.class))).to((Type)((Object)ResourceInfo.class))).proxy(true)).proxyForSameScope(false)).in(RequestScoped.class);
            ((ClassBinding)((ClassBinding)((ClassBinding)this.bind(SecurityContextInjectee.class).to(SecurityContext.class)).proxy(true)).proxyForSameScope(false)).in(RequestScoped.class);
            ((SupplierClassBinding)((SupplierClassBinding)((SupplierClassBinding)this.bindFactory(CloseableServiceFactory.class).to((Type)((Object)CloseableService.class))).proxy(true)).proxyForSameScope(false)).in(RequestScoped.class);
            ((SupplierClassBinding)((SupplierClassBinding)this.bindFactory(AsyncContextFactory.class).to((Type)((Object)AsyncContext.class))).to((Type)((Object)AsyncResponse.class))).in(RequestScoped.class);
        }
    }

    private static class AsyncContextFactory
    implements Supplier<AsyncContext> {
        private final RequestProcessingContextReference reference;

        @Inject
        private AsyncContextFactory(RequestProcessingContextReference reference) {
            this.reference = reference;
        }

        @Override
        public AsyncContext get() {
            return this.reference.get().asyncContext();
        }
    }

    private static class CloseableServiceFactory
    implements Supplier<CloseableService> {
        private final RequestProcessingContextReference reference;

        @Inject
        private CloseableServiceFactory(RequestProcessingContextReference reference) {
            this.reference = reference;
        }

        @Override
        public CloseableService get() {
            return this.reference.get().closeableService();
        }
    }

    private static class UriRoutingContextFactory
    implements Supplier<UriRoutingContext> {
        private final RequestProcessingContextReference reference;

        @Inject
        private UriRoutingContextFactory(RequestProcessingContextReference reference) {
            this.reference = reference;
        }

        @Override
        public UriRoutingContext get() {
            return this.reference.get().uriRoutingContext();
        }
    }

    private static class ContainerRequestFactory
    implements Supplier<ContainerRequest> {
        private final RequestProcessingContextReference reference;

        @Inject
        private ContainerRequestFactory(RequestProcessingContextReference reference) {
            this.reference = reference;
        }

        @Override
        public ContainerRequest get() {
            return this.reference.get().request();
        }
    }
}

