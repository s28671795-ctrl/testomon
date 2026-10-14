/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import org.glassfish.jersey.process.internal.Stage;
import org.glassfish.jersey.process.internal.Stages;
import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;

final class MatchedEndpointExtractorStage
implements Stage<RequestProcessingContext> {
    MatchedEndpointExtractorStage() {
    }

    @Override
    public Stage.Continuation<RequestProcessingContext> apply(RequestProcessingContext processingContext) {
        Endpoint endpoint = processingContext.routingContext().getEndpoint();
        return endpoint != null ? Stage.Continuation.of(processingContext, Stages.asStage(endpoint)) : Stage.Continuation.of(processingContext);
    }
}

