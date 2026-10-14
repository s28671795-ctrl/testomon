/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.Props;
import akka.routing.Pool;
import akka.routing.Routee;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y1QAA\u0002\u0002\u0002!AQa\u0005\u0001\u0005\u0002Q\u0011\u0001\u0002U8pY\n\u000b7/\u001a\u0006\u0003\t\u0015\tqA]8vi&twMC\u0001\u0007\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001!C\b\u0011\u0005)iQ\"A\u0006\u000b\u00031\tQa]2bY\u0006L!AD\u0006\u0003\r\u0005s\u0017PU3g!\t\u0001\u0012#D\u0001\u0004\u0013\t\u00112A\u0001\u0003Q_>d\u0017A\u0002\u001fj]&$h\bF\u0001\u0016!\t\u0001\u0002\u0001")
public abstract class PoolBase
implements Pool {
    @Override
    public boolean usePoolDispatcher() {
        return Pool.usePoolDispatcher$(this);
    }

    @Override
    public Routee newRoutee(Props routeeProps, ActorContext context) {
        return Pool.newRoutee$(this, routeeProps, context);
    }

    @Override
    public Props enrichWithPoolDispatcher(Props routeeProps, ActorContext context) {
        return Pool.enrichWithPoolDispatcher$(this, routeeProps, context);
    }

    @Override
    public Props props(Props routeeProps) {
        return Pool.props$(this, routeeProps);
    }

    @Override
    public boolean stopRouterWhenAllRouteesRemoved() {
        return Pool.stopRouterWhenAllRouteesRemoved$(this);
    }

    @Override
    public RouterActor createRouterActor() {
        return Pool.createRouterActor$(this);
    }

    @Override
    public Option<Props> routingLogicController(RoutingLogic routingLogic) {
        return RouterConfig.routingLogicController$(this, routingLogic);
    }

    @Override
    public boolean isManagementMessage(Object msg) {
        return RouterConfig.isManagementMessage$(this, msg);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        return RouterConfig.withFallback$(this, other);
    }

    @Override
    public void verifyConfig(ActorPath path) {
        RouterConfig.verifyConfig$(this, path);
    }

    public PoolBase() {
        RouterConfig.$init$(this);
        Pool.$init$(this);
    }
}

