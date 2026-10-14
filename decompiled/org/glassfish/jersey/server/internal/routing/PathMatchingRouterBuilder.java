/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.util.LinkedList;
import java.util.List;
import org.glassfish.jersey.server.internal.routing.MethodSelectingRouter;
import org.glassfish.jersey.server.internal.routing.PathMatchingRouter;
import org.glassfish.jersey.server.internal.routing.PathToRouterBuilder;
import org.glassfish.jersey.server.internal.routing.Route;
import org.glassfish.jersey.server.internal.routing.Router;
import org.glassfish.jersey.uri.PathPattern;

final class PathMatchingRouterBuilder
implements PathToRouterBuilder {
    private final List<Route> acceptedRoutes = new LinkedList<Route>();
    private List<Router> currentRouters;

    static PathToRouterBuilder newRoute(PathPattern pattern) {
        PathMatchingRouterBuilder builder = new PathMatchingRouterBuilder();
        builder.startNewRoute(pattern);
        return builder;
    }

    private PathMatchingRouterBuilder() {
    }

    private void startNewRoute(PathPattern pattern) {
        this.currentRouters = new LinkedList<Router>();
        this.acceptedRoutes.add(Route.of(pattern, this.currentRouters));
    }

    protected List<Route> acceptedRoutes() {
        return this.acceptedRoutes;
    }

    @Override
    public PathMatchingRouterBuilder to(Router router) {
        if (MethodSelectingRouter.class.isInstance(router)) {
            this.acceptedRoutes.get(this.acceptedRoutes.size() - 1).setHttpMethods(((MethodSelectingRouter)router).getHttpMethods());
        }
        this.currentRouters.add(router);
        return this;
    }

    public PathToRouterBuilder route(PathPattern pattern) {
        this.startNewRoute(pattern);
        return this;
    }

    public PathMatchingRouter build() {
        return new PathMatchingRouter(this.acceptedRoutes());
    }
}

