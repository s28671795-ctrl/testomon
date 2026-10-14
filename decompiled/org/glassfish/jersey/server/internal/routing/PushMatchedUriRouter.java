/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.Router;

final class PushMatchedUriRouter
implements Router {
    PushMatchedUriRouter() {
    }

    @Override
    public Router.Continuation apply(RequestProcessingContext context) {
        context.routingContext().pushLeftHandPath();
        return Router.Continuation.of(context);
    }
}

