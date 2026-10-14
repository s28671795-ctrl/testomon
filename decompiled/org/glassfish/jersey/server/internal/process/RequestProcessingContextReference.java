/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.process;

import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;

@RequestScoped
public class RequestProcessingContextReference
implements Ref<RequestProcessingContext> {
    private RequestProcessingContext processingContext;

    @Override
    public void set(RequestProcessingContext processingContext) {
        this.processingContext = processingContext;
    }

    @Override
    public RequestProcessingContext get() {
        return this.processingContext;
    }
}

