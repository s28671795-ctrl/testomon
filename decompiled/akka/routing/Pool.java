/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.routing.ActorRefRoutee;
import akka.routing.Pool$;
import akka.routing.ResizablePoolActor;
import akka.routing.Resizer;
import akka.routing.Routee;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RouterPoolActor;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.IterableOnceOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e<Q!\u0005\n\t\u0002]1Q!\u0007\n\t\u0002iAQ!K\u0001\u0005\u0002)BqaK\u0001C\u0002\u0013\u0005A\u0006\u0003\u00044\u0003\u0001\u0006I!\f\u0005\bi\u0005\t\t\u0011\"\u00036\r\u001dI\"\u0003%A\u0002\u0002qBQ\u0001\u0011\u0004\u0005\u0002\u0005CQ!\u0012\u0004\u0007\u0002\u0019CQa\u0014\u0004\u0005\u0002ACa\u0001\u0016\u0004\u0005\u0002Q)\u0006BB2\u0007\t\u0003!B\rC\u0003h\r\u0019\u0005\u0001\u000eC\u0003p\r\u0019\u0005A\u0006C\u0003q\r\u0011\u0005\u0011\u000fC\u0003t\r\u0011\u0005\u0003\u000b\u0003\u0004u\r\u0011\u0005C#^\u0001\u0005!>|GN\u0003\u0002\u0014)\u00059!o\\;uS:<'\"A\u000b\u0002\t\u0005\\7.Y\u0002\u0001!\tA\u0012!D\u0001\u0013\u0005\u0011\u0001vn\u001c7\u0014\u0007\u0005Y\u0012\u0005\u0005\u0002\u001d?5\tQDC\u0001\u001f\u0003\u0015\u00198-\u00197b\u0013\t\u0001SD\u0001\u0004B]f\u0014VM\u001a\t\u0003E\u001dj\u0011a\t\u0006\u0003I\u0015\n!![8\u000b\u0003\u0019\nAA[1wC&\u0011\u0001f\t\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003]\t\u0011\u0004Z3gCVdGoU;qKJ4\u0018n]8s'R\u0014\u0018\r^3hsV\tQ\u0006\u0005\u0002/c5\tqF\u0003\u00021)\u0005)\u0011m\u0019;pe&\u0011!g\f\u0002\u0013'V\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u00170\u0001\u000eeK\u001a\fW\u000f\u001c;TkB,'O^5t_J\u001cFO]1uK\u001eL\b%\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u00017!\t9$(D\u00019\u0015\tIT%\u0001\u0003mC:<\u0017BA\u001e9\u0005\u0019y%M[3diN\u0019aaG\u001f\u0011\u0005aq\u0014BA \u0013\u00051\u0011v.\u001e;fe\u000e{gNZ5h\u0003\u0019!\u0013N\\5uIQ\t!\t\u0005\u0002\u001d\u0007&\u0011A)\b\u0002\u0005+:LG/A\u0007oe>3\u0017J\\:uC:\u001cWm\u001d\u000b\u0003\u000f*\u0003\"\u0001\b%\n\u0005%k\"aA%oi\")1\n\u0003a\u0001\u0019\u0006\u00191/_:\u0011\u00059j\u0015B\u0001(0\u0005-\t5\r^8s'f\u001cH/Z7\u0002#U\u001cX\rU8pY\u0012K7\u000f]1uG\",'/F\u0001R!\ta\"+\u0003\u0002T;\t9!i\\8mK\u0006t\u0017!\u00038foJ{W\u000f^3f)\r1\u0016L\u0018\t\u00031]K!\u0001\u0017\n\u0003\rI{W\u000f^3f\u0011\u0015Q&\u00021\u0001\\\u0003-\u0011x.\u001e;fKB\u0013x\u000e]:\u0011\u00059b\u0016BA/0\u0005\u0015\u0001&o\u001c9t\u0011\u0015y&\u00021\u0001a\u0003\u001d\u0019wN\u001c;fqR\u0004\"AL1\n\u0005\t|#\u0001D!di>\u00148i\u001c8uKb$\u0018\u0001G3oe&\u001c\u0007nV5uQB{w\u000e\u001c#jgB\fGo\u00195feR\u00191,\u001a4\t\u000bi[\u0001\u0019A.\t\u000b}[\u0001\u0019\u00011\u0002\u000fI,7/\u001b>feV\t\u0011\u000eE\u0002\u001dU2L!a[\u000f\u0003\r=\u0003H/[8o!\tAR.\u0003\u0002o%\t9!+Z:ju\u0016\u0014\u0018AE:va\u0016\u0014h/[:peN#(/\u0019;fOf\fQ\u0001\u001d:paN$\"a\u0017:\t\u000bis\u0001\u0019A.\u0002?M$x\u000e\u001d*pkR,'o\u00165f]\u0006cGNU8vi\u0016,7OU3n_Z,G-A\tde\u0016\fG/\u001a*pkR,'/Q2u_J$\u0012A\u001e\t\u00031]L!\u0001\u001f\n\u0003\u0017I{W\u000f^3s\u0003\u000e$xN\u001d")
public interface Pool
extends RouterConfig {
    public static SupervisorStrategy defaultSupervisorStrategy() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    public int nrOfInstances(ActorSystem var1);

    public static /* synthetic */ boolean usePoolDispatcher$(Pool $this) {
        return $this.usePoolDispatcher();
    }

    default public boolean usePoolDispatcher() {
        return false;
    }

    public static /* synthetic */ Routee newRoutee$(Pool $this, Props routeeProps, ActorContext context) {
        return $this.newRoutee(routeeProps, context);
    }

    default public Routee newRoutee(Props routeeProps, ActorContext context) {
        return new ActorRefRoutee(context.actorOf(this.enrichWithPoolDispatcher(routeeProps, context)));
    }

    public static /* synthetic */ Props enrichWithPoolDispatcher$(Pool $this, Props routeeProps, ActorContext context) {
        return $this.enrichWithPoolDispatcher(routeeProps, context);
    }

    default public Props enrichWithPoolDispatcher(Props routeeProps, ActorContext context) {
        if (this.usePoolDispatcher()) {
            String string = routeeProps.dispatcher();
            String string2 = "akka.actor.default-dispatcher";
            if (!(string != null ? !string.equals(string2) : string2 != null)) {
                return routeeProps.withDispatcher(new StringBuilder(38).append("akka.actor.deployment.").append(((IterableOnceOps)context.self().path().elements().drop(1)).mkString("/", "/", "")).append(".pool-dispatcher").toString());
            }
        }
        return routeeProps;
    }

    public Option<Resizer> resizer();

    public SupervisorStrategy supervisorStrategy();

    public static /* synthetic */ Props props$(Pool $this, Props routeeProps) {
        return $this.props(routeeProps);
    }

    default public Props props(Props routeeProps) {
        return routeeProps.withRouter(this);
    }

    public static /* synthetic */ boolean stopRouterWhenAllRouteesRemoved$(Pool $this) {
        return $this.stopRouterWhenAllRouteesRemoved();
    }

    @Override
    default public boolean stopRouterWhenAllRouteesRemoved() {
        return this.resizer().isEmpty();
    }

    public static /* synthetic */ RouterActor createRouterActor$(Pool $this) {
        return $this.createRouterActor();
    }

    @Override
    default public RouterActor createRouterActor() {
        Option<Resizer> option = this.resizer();
        if (None$.MODULE$.equals(option)) {
            return new RouterPoolActor(this.supervisorStrategy());
        }
        if (option instanceof Some) {
            return new ResizablePoolActor(this.supervisorStrategy());
        }
        throw new MatchError(option);
    }

    public static void $init$(Pool $this) {
    }
}

