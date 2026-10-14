/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.process.internal.AbstractChainableStage;
import org.glassfish.jersey.process.internal.Stage;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.ServerTraceEvent;
import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.Router;
import org.glassfish.jersey.server.internal.routing.Routers;
import org.glassfish.jersey.server.monitoring.RequestEvent;

final class RoutingStage
extends AbstractChainableStage<RequestProcessingContext> {
    private final Router routingRoot;

    RoutingStage(Router routingRoot) {
        this.routingRoot = routingRoot;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Stage.Continuation<RequestProcessingContext> apply(RequestProcessingContext context) {
        ContainerRequest request = context.request();
        context.triggerEvent(RequestEvent.Type.MATCHING_START);
        TracingLogger tracingLogger = TracingLogger.getInstance(request);
        long timestamp = tracingLogger.timestamp(ServerTraceEvent.MATCH_SUMMARY);
        try {
            RoutingResult result = this._apply(context, this.routingRoot);
            Stage nextStage = null;
            if (result.endpoint != null) {
                context.routingContext().setEndpoint(result.endpoint);
                nextStage = this.getDefaultNext();
            }
            Stage.Continuation<RequestProcessingContext> continuation = Stage.Continuation.of(result.context, nextStage);
            return continuation;
        }
        finally {
            tracingLogger.logDuration(ServerTraceEvent.MATCH_SUMMARY, timestamp, new Object[0]);
        }
    }

    private RoutingResult _apply(RequestProcessingContext request, Router router) {
        Router.Continuation continuation = router.apply(request);
        for (Router child : continuation.next()) {
            RoutingResult result = this._apply(continuation.requestContext(), child);
            if (result.endpoint == null) continue;
            return result;
        }
        Endpoint endpoint = Routers.extractEndpoint(router);
        if (endpoint != null) {
            return RoutingResult.from(continuation.requestContext(), endpoint);
        }
        return RoutingResult.from(continuation.requestContext());
    }

    private static final class RoutingResult {
        private final RequestProcessingContext context;
        private final Endpoint endpoint;

        private static RoutingResult from(RequestProcessingContext requestProcessingContext, Endpoint endpoint) {
            return new RoutingResult(requestProcessingContext, endpoint);
        }

        private static RoutingResult from(RequestProcessingContext requestProcessingContext) {
            return new RoutingResult(requestProcessingContext, null);
        }

        private RoutingResult(RequestProcessingContext context, Endpoint endpoint) {
            this.context = context;
            this.endpoint = endpoint;
        }
    }
}

