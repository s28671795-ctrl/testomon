/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.util.Iterator;
import java.util.List;
import java.util.regex.MatchResult;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.server.internal.ServerTraceEvent;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.Route;
import org.glassfish.jersey.server.internal.routing.Router;
import org.glassfish.jersey.server.internal.routing.RoutingContext;
import org.glassfish.jersey.uri.PathPattern;

final class PathMatchingRouter
implements Router {
    private final List<Route> acceptedRoutes;

    PathMatchingRouter(List<Route> routes) {
        this.acceptedRoutes = routes;
    }

    @Override
    public Router.Continuation apply(RequestProcessingContext context) {
        RoutingContext rc = context.routingContext();
        String path = rc.getFinalMatchingGroup();
        TracingLogger tracingLogger = TracingLogger.getInstance(context.request());
        tracingLogger.log(ServerTraceEvent.MATCH_PATH_FIND, path);
        Router.Continuation result = null;
        MatchResult matchResultCandidate = null;
        Route acceptedRouteCandidate = null;
        Iterator<Route> iterator2 = this.acceptedRoutes.iterator();
        while (iterator2.hasNext()) {
            Route acceptedRoute = iterator2.next();
            PathPattern routePattern = acceptedRoute.routingPattern();
            MatchResult matchResult = routePattern.match(path);
            if (matchResult != null) {
                if (PathMatchingRouter.isLocator(acceptedRoute) && matchResultCandidate != null) {
                    result = this.matchPathSelected(context, acceptedRouteCandidate, matchResultCandidate, tracingLogger);
                    break;
                }
                if (PathMatchingRouter.isLocator(acceptedRoute) || PathMatchingRouter.designatorMatch(acceptedRoute, context)) {
                    result = this.matchPathSelected(context, acceptedRoute, matchResult, tracingLogger);
                    break;
                }
                if (matchResultCandidate != null) continue;
                matchResultCandidate = matchResult;
                acceptedRouteCandidate = acceptedRoute;
                continue;
            }
            tracingLogger.log(ServerTraceEvent.MATCH_PATH_NOT_MATCHED, routePattern.getRegex());
        }
        if (tracingLogger.isLogEnabled(ServerTraceEvent.MATCH_PATH_SKIPPED)) {
            while (iterator2.hasNext()) {
                tracingLogger.log(ServerTraceEvent.MATCH_PATH_SKIPPED, iterator2.next().routingPattern().getRegex());
            }
        }
        if (result == null && acceptedRouteCandidate != null) {
            result = this.matchPathSelected(context, acceptedRouteCandidate, matchResultCandidate, tracingLogger);
        }
        if (result == null) {
            return Router.Continuation.of(context);
        }
        return result;
    }

    private Router.Continuation matchPathSelected(RequestProcessingContext context, Route acceptedRoute, MatchResult matchResult, TracingLogger tracingLogger) {
        context.routingContext().pushMatchResult(matchResult);
        Router.Continuation result = Router.Continuation.of(context, acceptedRoute.next());
        tracingLogger.log(ServerTraceEvent.MATCH_PATH_SELECTED, acceptedRoute.routingPattern().getRegex());
        return result;
    }

    private static boolean designatorMatch(Route route, RequestProcessingContext context) {
        String httpMethod = context.request().getMethod();
        if (route.getHttpMethods().contains(httpMethod)) {
            return true;
        }
        return "HEAD".equals(httpMethod) && route.getHttpMethods().contains("GET");
    }

    private static boolean isLocator(Route route) {
        return route.getHttpMethods() == null;
    }
}

