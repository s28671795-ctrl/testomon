/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorPath;
import akka.actor.Props;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)2Q\u0001B\u0003\u0002\u0002)AQ!\u0006\u0001\u0005\u0002YAa\u0001\u0007\u0001\u0005B\u001dI\u0002\"B\u000f\u0001\t\u0003r\"AE\"vgR|WNU8vi\u0016\u00148i\u001c8gS\u001eT!AB\u0004\u0002\u000fI|W\u000f^5oO*\t\u0001\"\u0001\u0003bW.\f7\u0001A\n\u0004\u0001-\t\u0002C\u0001\u0007\u0010\u001b\u0005i!\"\u0001\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Ai!AB!osJ+g\r\u0005\u0002\u0013'5\tQ!\u0003\u0002\u0015\u000b\ta!k\\;uKJ\u001cuN\u001c4jO\u00061A(\u001b8jiz\"\u0012a\u0006\t\u0003%\u0001\t\u0011c\u0019:fCR,'k\\;uKJ\f5\r^8s)\u0005Q\u0002C\u0001\n\u001c\u0013\taRAA\u0006S_V$XM]!di>\u0014\u0018\u0001\u0005:pkR,'\u000fR5ta\u0006$8\r[3s+\u0005y\u0002C\u0001\u0011(\u001d\t\tS\u0005\u0005\u0002#\u001b5\t1E\u0003\u0002%\u0013\u00051AH]8pizJ!AJ\u0007\u0002\rA\u0013X\rZ3g\u0013\tA\u0013F\u0001\u0004TiJLgn\u001a\u0006\u0003M5\u0001")
public abstract class CustomRouterConfig
implements RouterConfig {
    @Override
    public Option<Props> routingLogicController(RoutingLogic routingLogic) {
        return RouterConfig.routingLogicController$(this, routingLogic);
    }

    @Override
    public boolean isManagementMessage(Object msg) {
        return RouterConfig.isManagementMessage$(this, msg);
    }

    @Override
    public boolean stopRouterWhenAllRouteesRemoved() {
        return RouterConfig.stopRouterWhenAllRouteesRemoved$(this);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        return RouterConfig.withFallback$(this, other);
    }

    @Override
    public void verifyConfig(ActorPath path) {
        RouterConfig.verifyConfig$(this, path);
    }

    @Override
    public RouterActor createRouterActor() {
        return new RouterActor();
    }

    @Override
    public String routerDispatcher() {
        return "akka.actor.default-dispatcher";
    }

    public CustomRouterConfig() {
        RouterConfig.$init$(this);
    }
}

