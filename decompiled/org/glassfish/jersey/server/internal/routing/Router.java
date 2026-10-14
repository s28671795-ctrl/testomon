/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.util.Collections;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;

interface Router {
    public Continuation apply(RequestProcessingContext var1);

    public static final class Continuation {
        private final RequestProcessingContext requestProcessingContext;
        private final Iterable<Router> next;

        static Continuation of(RequestProcessingContext result) {
            return new Continuation(result, null);
        }

        static Continuation of(RequestProcessingContext result, Iterable<Router> next) {
            return new Continuation(result, next);
        }

        static Continuation of(RequestProcessingContext request, Router next) {
            return new Continuation(request, Collections.singletonList(next));
        }

        private Continuation(RequestProcessingContext request, Iterable<Router> next) {
            this.requestProcessingContext = request;
            this.next = next == null ? Collections.emptyList() : next;
        }

        RequestProcessingContext requestContext() {
            return this.requestProcessingContext;
        }

        Iterable<Router> next() {
            return this.next;
        }
    }
}

