/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Util$;
import akka.routing.Group;
import akka.routing.Routee;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import scala.Option;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}2Q\u0001B\u0003\u0002\u0002)AQ!\u0006\u0001\u0005\u0002YAQ\u0001\u0007\u0001\u0007\u0002eAQ!\u000e\u0001\u0005FY\u0012\u0011b\u0012:pkB\u0014\u0015m]3\u000b\u0005\u00199\u0011a\u0002:pkRLgn\u001a\u0006\u0002\u0011\u0005!\u0011m[6b\u0007\u0001\u00192\u0001A\u0006\u0012!\taq\"D\u0001\u000e\u0015\u0005q\u0011!B:dC2\f\u0017B\u0001\t\u000e\u0005\u0019\te.\u001f*fMB\u0011!cE\u0007\u0002\u000b%\u0011A#\u0002\u0002\u0006\u000fJ|W\u000f]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003]\u0001\"A\u0005\u0001\u0002\u0011\u001d,G\u000fU1uQN$\"AG\u0017\u0011\u0007m\u0001#%D\u0001\u001d\u0015\tib$\u0001\u0003mC:<'\"A\u0010\u0002\t)\fg/Y\u0005\u0003Cq\u0011\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0003G)r!\u0001\n\u0015\u0011\u0005\u0015jQ\"\u0001\u0014\u000b\u0005\u001dJ\u0011A\u0002\u001fs_>$h(\u0003\u0002*\u001b\u00051\u0001K]3eK\u001aL!a\u000b\u0017\u0003\rM#(/\u001b8h\u0015\tIS\u0002C\u0003/\u0005\u0001\u0007q&\u0001\u0004tsN$X-\u001c\t\u0003aMj\u0011!\r\u0006\u0003e\u001d\tQ!Y2u_JL!\u0001N\u0019\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0001\u0006a\u0006$\bn\u001d\u000b\u0003oy\u00022\u0001O\u001f#\u001b\u0005I$B\u0001\u001e<\u0003%IW.\\;uC\ndWM\u0003\u0002=\u001b\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005\u0005J\u0004\"\u0002\u0018\u0004\u0001\u0004y\u0003")
public abstract class GroupBase
implements Group {
    @Override
    public Props props() {
        return Group.props$(this);
    }

    @Override
    public Routee routeeFor(String path, ActorContext context) {
        return Group.routeeFor$(this, path, context);
    }

    @Override
    public RouterActor createRouterActor() {
        return Group.createRouterActor$(this);
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

    public abstract java.lang.Iterable<String> getPaths(ActorSystem var1);

    @Override
    public final Iterable<String> paths(ActorSystem system) {
        return Util$.MODULE$.immutableSeq(this.getPaths(system));
    }

    public GroupBase() {
        RouterConfig.$init$(this);
        Group.$init$(this);
    }
}

