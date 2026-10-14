/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.AutoReceivedMessage;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.routing.Router;
import akka.routing.RouterActor;
import akka.routing.RouterManagementMesssage;
import akka.routing.RoutingLogic;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e4qAC\u0006\u0011\u0002\u0007\u0005\u0001\u0003C\u0003$\u0001\u0011\u0005A\u0005C\u0003)\u0001\u0019\u0005\u0011\u0006C\u00037\u0001\u0019\u0005q\u0007C\u0003A\u0001\u0011\u0005\u0011\tC\u0003U\u0001\u0011\u0005Q\u000bC\u0003_\u0001\u0011\u0005q\fC\u0003a\u0001\u0011\u0005\u0011\rC\u0003g\u0001\u0011\u0005q\r\u0003\u0004o\u0001\u0019\u0005Qb\u001c\u0002\r%>,H/\u001a:D_:4\u0017n\u001a\u0006\u0003\u00195\tqA]8vi&twMC\u0001\u000f\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001!E\f\u0011\u0005I)R\"A\n\u000b\u0003Q\tQa]2bY\u0006L!AF\n\u0003\r\u0005s\u0017PU3g!\tA\u0002E\u0004\u0002\u001a=9\u0011!$H\u0007\u00027)\u0011AdD\u0001\u0007yI|w\u000e\u001e \n\u0003QI!aH\n\u0002\u000fA\f7m[1hK&\u0011\u0011E\t\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003?M\ta\u0001J5oSR$C#A\u0013\u0011\u0005I1\u0013BA\u0014\u0014\u0005\u0011)f.\u001b;\u0002\u0019\r\u0014X-\u0019;f%>,H/\u001a:\u0015\u0005)r\u0003CA\u0016-\u001b\u0005Y\u0011BA\u0017\f\u0005\u0019\u0011v.\u001e;fe\")qF\u0001a\u0001a\u000511/_:uK6\u0004\"!\r\u001b\u000e\u0003IR!aM\u0007\u0002\u000b\u0005\u001cGo\u001c:\n\u0005U\u0012$aC!di>\u00148+_:uK6\f\u0001C]8vi\u0016\u0014H)[:qCR\u001c\u0007.\u001a:\u0016\u0003a\u0002\"!O\u001f\u000f\u0005iZ\u0004C\u0001\u000e\u0014\u0013\ta4#\u0001\u0004Qe\u0016$WMZ\u0005\u0003}}\u0012aa\u0015;sS:<'B\u0001\u001f\u0014\u0003Y\u0011x.\u001e;j]\u001edunZ5d\u0007>tGO]8mY\u0016\u0014HC\u0001\"I!\r\u00112)R\u0005\u0003\tN\u0011aa\u00149uS>t\u0007CA\u0019G\u0013\t9%GA\u0003Qe>\u00048\u000fC\u0003J\t\u0001\u0007!*\u0001\u0007s_V$\u0018N\\4M_\u001eL7\r\u0005\u0002,\u0017&\u0011Aj\u0003\u0002\r%>,H/\u001b8h\u0019><\u0017n\u0019\u0015\u0003\u0011:\u0003\"a\u0014*\u000e\u0003AS!!U\u0007\u0002\tU$\u0018\u000e\\\u0005\u0003'B\u0013a!\u001e8vg\u0016$\u0017aE5t\u001b\u0006t\u0017mZ3nK:$X*Z:tC\u001e,GC\u0001,Z!\t\u0011r+\u0003\u0002Y'\t9!i\\8mK\u0006t\u0007\"\u0002.\u0006\u0001\u0004Y\u0016aA7tOB\u0011!\u0003X\u0005\u0003;N\u00111!\u00118z\u0003}\u0019Ho\u001c9S_V$XM],iK:\fE\u000e\u001c*pkR,Wm\u001d*f[>4X\rZ\u000b\u0002-\u0006aq/\u001b;i\r\u0006dGNY1dWR\u0011!m\u0019\t\u0003W\u0001AQ\u0001Z\u0004A\u0002\t\fQa\u001c;iKJD#a\u0019(\u0002\u0019Y,'/\u001b4z\u0007>tg-[4\u0015\u0005\u0015B\u0007\"B5\t\u0001\u0004Q\u0017\u0001\u00029bi\"\u0004\"!M6\n\u00051\u0014$!C!di>\u0014\b+\u0019;iQ\tAg*A\tde\u0016\fG/\u001a*pkR,'/Q2u_J$\u0012\u0001\u001d\t\u0003WEL!A]\u0006\u0003\u0017I{W\u000f^3s\u0003\u000e$xN\u001d\u0015\u0005\u0001Q<\b\u0010\u0005\u0002\u0013k&\u0011ao\u0005\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001")
public interface RouterConfig
extends Serializable {
    public Router createRouter(ActorSystem var1);

    public String routerDispatcher();

    public static /* synthetic */ Option routingLogicController$(RouterConfig $this, RoutingLogic routingLogic) {
        return $this.routingLogicController(routingLogic);
    }

    default public Option<Props> routingLogicController(RoutingLogic routingLogic) {
        return None$.MODULE$;
    }

    public static /* synthetic */ boolean isManagementMessage$(RouterConfig $this, Object msg) {
        return $this.isManagementMessage(msg);
    }

    default public boolean isManagementMessage(Object msg) {
        Object object = msg;
        return object instanceof AutoReceivedMessage ? true : (object instanceof Terminated ? true : object instanceof RouterManagementMesssage);
    }

    public static /* synthetic */ boolean stopRouterWhenAllRouteesRemoved$(RouterConfig $this) {
        return $this.stopRouterWhenAllRouteesRemoved();
    }

    default public boolean stopRouterWhenAllRouteesRemoved() {
        return true;
    }

    public static /* synthetic */ RouterConfig withFallback$(RouterConfig $this, RouterConfig other) {
        return $this.withFallback(other);
    }

    default public RouterConfig withFallback(RouterConfig other) {
        return this;
    }

    public static /* synthetic */ void verifyConfig$(RouterConfig $this, ActorPath path) {
        $this.verifyConfig(path);
    }

    default public void verifyConfig(ActorPath path) {
    }

    public RouterActor createRouterActor();

    public static void $init$(RouterConfig $this) {
    }
}

