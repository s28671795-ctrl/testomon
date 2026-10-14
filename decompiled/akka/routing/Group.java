/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Props$;
import akka.routing.ActorSelectionRoutee;
import akka.routing.Routee;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=3qAB\u0004\u0011\u0002\u0007\u0005A\u0002C\u0003\u0018\u0001\u0011\u0005\u0001\u0004C\u0003\u001d\u0001\u0019\u0005Q\u0004C\u0003:\u0001\u0011\u0005!\b\u0003\u0004?\u0001\u0011\u0005\u0011b\u0010\u0005\u0007\u0015\u0002!\t%C&\u0003\u000b\u001d\u0013x.\u001e9\u000b\u0005!I\u0011a\u0002:pkRLgn\u001a\u0006\u0002\u0015\u0005!\u0011m[6b\u0007\u0001\u00192\u0001A\u0007\u0014!\tq\u0011#D\u0001\u0010\u0015\u0005\u0001\u0012!B:dC2\f\u0017B\u0001\n\u0010\u0005\u0019\te.\u001f*fMB\u0011A#F\u0007\u0002\u000f%\u0011ac\u0002\u0002\r%>,H/\u001a:D_:4\u0017nZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003e\u0001\"A\u0004\u000e\n\u0005my!\u0001B+oSR\fQ\u0001]1uQN$\"AH\u0019\u0011\u0007}!c%D\u0001!\u0015\t\t#%A\u0005j[6,H/\u00192mK*\u00111eD\u0001\u000bG>dG.Z2uS>t\u0017BA\u0013!\u0005!IE/\u001a:bE2,\u0007CA\u0014/\u001d\tAC\u0006\u0005\u0002*\u001f5\t!F\u0003\u0002,\u0017\u00051AH]8pizJ!!L\b\u0002\rA\u0013X\rZ3g\u0013\ty\u0003G\u0001\u0004TiJLgn\u001a\u0006\u0003[=AQA\r\u0002A\u0002M\naa]=ti\u0016l\u0007C\u0001\u001b8\u001b\u0005)$B\u0001\u001c\n\u0003\u0015\t7\r^8s\u0013\tATGA\u0006BGR|'oU=ti\u0016l\u0017!\u00029s_B\u001cH#A\u001e\u0011\u0005Qb\u0014BA\u001f6\u0005\u0015\u0001&o\u001c9t\u0003%\u0011x.\u001e;fK\u001a{'\u000fF\u0002A\u0007\u0016\u0003\"\u0001F!\n\u0005\t;!A\u0002*pkR,W\rC\u0003E\t\u0001\u0007a%\u0001\u0003qCRD\u0007\"\u0002$\u0005\u0001\u00049\u0015aB2p]R,\u0007\u0010\u001e\t\u0003i!K!!S\u001b\u0003\u0019\u0005\u001bGo\u001c:D_:$X\r\u001f;\u0002#\r\u0014X-\u0019;f%>,H/\u001a:BGR|'\u000fF\u0001M!\t!R*\u0003\u0002O\u000f\tY!k\\;uKJ\f5\r^8s\u0001")
public interface Group
extends RouterConfig {
    public Iterable<String> paths(ActorSystem var1);

    public static /* synthetic */ Props props$(Group $this) {
        return $this.props();
    }

    default public Props props() {
        return Props$.MODULE$.empty().withRouter(this);
    }

    public static /* synthetic */ Routee routeeFor$(Group $this, String path, ActorContext context) {
        return $this.routeeFor(path, context);
    }

    default public Routee routeeFor(String path, ActorContext context) {
        return new ActorSelectionRoutee(context.actorSelection(path));
    }

    public static /* synthetic */ RouterActor createRouterActor$(Group $this) {
        return $this.createRouterActor();
    }

    @Override
    default public RouterActor createRouterActor() {
        return new RouterActor();
    }

    public static void $init$(Group $this) {
    }
}

