/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.Router;

final class Routers {
    private static final Router IDENTITY_ROUTER = new Router(){

        @Override
        public Router.Continuation apply(RequestProcessingContext data) {
            return Router.Continuation.of(data);
        }
    };

    private Routers() {
        throw new AssertionError((Object)"No instances of this class.");
    }

    public static Router noop() {
        return IDENTITY_ROUTER;
    }

    public static Router endpoint(Endpoint endpoint) {
        return new EndpointRouter(endpoint);
    }

    public static Endpoint extractEndpoint(Router router) {
        if (router instanceof EndpointRouter) {
            return ((EndpointRouter)router).endpoint;
        }
        return null;
    }

    private static class EndpointRouter
    implements Router {
        private final Endpoint endpoint;

        public EndpointRouter(Endpoint endpoint) {
            this.endpoint = endpoint;
        }

        @Override
        public Router.Continuation apply(RequestProcessingContext context) {
            return Router.Continuation.of(context);
        }
    }
}

