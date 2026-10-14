/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorInitializationException$;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.routing.ActorRefRoutee;
import akka.routing.AddRoutee;
import akka.routing.GetRoutees$;
import akka.routing.RemoveRoutee;
import akka.routing.RoutedActorCell;
import akka.routing.Routee;
import akka.routing.Routees;
import akka.routing.RouterActor$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005=3Q!\u0003\u0006\u0001\u00199AQa\u0007\u0001\u0005\u0002uAq\u0001\t\u0001C\u0002\u0013\u0005\u0011\u0005\u0003\u0004&\u0001\u0001\u0006IA\t\u0005\bM\u0001\u0011\r\u0011\"\u0001(\u0011\u0019q\u0003\u0001)A\u0005Q!)q\u0006\u0001C\u0001a!)!\b\u0001C\u0001w!)A\b\u0001C!{\tY!k\\;uKJ\f5\r^8s\u0015\tYA\"A\u0004s_V$\u0018N\\4\u000b\u00035\tA!Y6lCN\u0019\u0001aD\u000b\u0011\u0005A\u0019R\"A\t\u000b\u0003I\tQa]2bY\u0006L!\u0001F\t\u0003\r\u0005s\u0017PU3g!\t1\u0012$D\u0001\u0018\u0015\tAB\"A\u0003bGR|'/\u0003\u0002\u001b/\t)\u0011i\u0019;pe\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\u001f!\ty\u0002!D\u0001\u000b\u0003\u0011\u0019W\r\u001c7\u0016\u0003\t\u0002\"aH\u0012\n\u0005\u0011R!a\u0004*pkR,G-Q2u_J\u001cU\r\u001c7\u0002\u000b\r,G\u000e\u001c\u0011\u0002-I|W\u000f^5oO2{w-[2D_:$(o\u001c7mKJ,\u0012\u0001\u000b\t\u0004!%Z\u0013B\u0001\u0016\u0012\u0005\u0019y\u0005\u000f^5p]B\u0011a\u0003L\u0005\u0003[]\u0011\u0001\"Q2u_J\u0014VMZ\u0001\u0018e>,H/\u001b8h\u0019><\u0017nY\"p]R\u0014x\u000e\u001c7fe\u0002\nqA]3dK&4X-F\u00012!\u0011\u0001\"\u0007N\u001c\n\u0005M\n\"a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8\u0011\u0005A)\u0014B\u0001\u001c\u0012\u0005\r\te.\u001f\t\u0003!aJ!!O\t\u0003\tUs\u0017\u000e^\u0001\u0018gR|\u0007/\u00134BY2\u0014v.\u001e;fKN\u0014V-\\8wK\u0012$\u0012aN\u0001\u000baJ,'+Z:uCJ$HcA\u001c?\u0019\")q\b\u0003a\u0001\u0001\u0006)1-Y;tKB\u0011\u0011)\u0013\b\u0003\u0005\u001es!a\u0011$\u000e\u0003\u0011S!!\u0012\u000f\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0011\u0012B\u0001%\u0012\u0003\u001d\u0001\u0018mY6bO\u0016L!AS&\u0003\u0013QC'o\\<bE2,'B\u0001%\u0012\u0011\u0015i\u0005\u00021\u0001O\u0003\ri7o\u001a\t\u0004!%\"\u0004")
public class RouterActor
implements Actor {
    private final RoutedActorCell cell;
    private final Option<ActorRef> routingLogicController;
    private ActorContext context;
    private ActorRef self;

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void postStop() throws Exception {
        Actor.postStop$(this);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    public RoutedActorCell cell() {
        return this.cell;
    }

    public Option<ActorRef> routingLogicController() {
        return this.routingLogicController;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ RouterActor $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (GetRoutees$.MODULE$.equals(A1)) {
                    this.$outer.sender().$bang(new Routees(this.$outer.cell().router().routees()), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof AddRoutee) {
                    AddRoutee addRoutee = (AddRoutee)A1;
                    Routee routee = addRoutee.routee();
                    this.$outer.cell().addRoutee(routee);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof RemoveRoutee) {
                    RemoveRoutee removeRoutee = (RemoveRoutee)A1;
                    Routee routee = removeRoutee.routee();
                    this.$outer.cell().removeRoutee(routee, true);
                    this.$outer.stopIfAllRouteesRemoved();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Terminated) {
                    Terminated terminated = (Terminated)A1;
                    ActorRef child = terminated.actor();
                    this.$outer.cell().removeRoutee(new ActorRefRoutee(child), false);
                    this.$outer.stopIfAllRouteesRemoved();
                    return (B1)BoxedUnit.UNIT;
                }
                if (this.$outer.routingLogicController().isDefined()) {
                    this.$outer.routingLogicController().foreach((Function1<ActorRef, Object> & Serializable)x$2 -> {
                        $anonfun$receive$1.$anonfun$applyOrElse$1(this, A1, x$2);
                        return BoxedUnit.UNIT;
                    });
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (GetRoutees$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof AddRoutee) {
                    return true;
                }
                if (object instanceof RemoveRoutee) {
                    return true;
                }
                if (object instanceof Terminated) {
                    return true;
                }
                return this.$outer.routingLogicController().isDefined();
            }

            public static final /* synthetic */ void $anonfun$applyOrElse$1($anonfun$receive$1 $this, Object x1$1, ActorRef x$2) {
                x$2.forward(x1$1, $this.$outer.context());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1$adapted(akka.routing.RouterActor$$anonfun$receive$1 java.lang.Object akka.actor.ActorRef )}, serializedLambda);
            }
        };
    }

    public void stopIfAllRouteesRemoved() {
        if (this.cell().router().routees().isEmpty() && this.cell().routerConfig().stopRouterWhenAllRouteesRemoved()) {
            this.context().stop(this.self());
            return;
        }
    }

    @Override
    public void preRestart(Throwable cause, Option<Object> msg) {
    }

    public RouterActor() {
        Actor.$init$(this);
        ActorContext actorContext = this.context();
        if (!(actorContext instanceof RoutedActorCell)) {
            throw ActorInitializationException$.MODULE$.apply(new StringBuilder(56).append("Router actor can only be used in RoutedActorRef, not in ").append(this.context().getClass()).toString());
        }
        RoutedActorCell routedActorCell = (RoutedActorCell)actorContext;
        this.cell = routedActorCell;
        this.routingLogicController = this.cell().routerConfig().routingLogicController(this.cell().router().logic()).map((Function1<Props, ActorRef> & Serializable)props -> this.context().actorOf(props.withDispatcher(this.context().props().dispatcher()), "routingLogicController"));
        Statics.releaseFence();
    }
}

