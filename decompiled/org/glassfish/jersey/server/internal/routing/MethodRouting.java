/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.util.Arrays;
import java.util.List;
import org.glassfish.jersey.server.internal.routing.Router;
import org.glassfish.jersey.server.model.ResourceMethod;

final class MethodRouting {
    final ResourceMethod method;
    final List<Router> routers;

    MethodRouting(ResourceMethod method2, Router ... routers) {
        this.method = method2;
        this.routers = Arrays.asList(routers);
    }

    public String toString() {
        return "{" + this.method + " -> " + this.routers + '}';
    }
}

