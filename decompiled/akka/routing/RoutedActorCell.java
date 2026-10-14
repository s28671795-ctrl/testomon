/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.actor.ActorSystemImpl;
import akka.actor.IndirectActorProducer;
import akka.actor.InternalActorRef;
import akka.actor.PoisonPill$;
import akka.actor.Props;
import akka.actor.dungeon.Dispatch;
import akka.dispatch.Envelope;
import akka.dispatch.MessageDispatcher;
import akka.routing.ActorRefRoutee;
import akka.routing.Group;
import akka.routing.Pool;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Some;
import scala.collection.IterableFactory$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Nil$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\u0005-uAB\u0011#\u0011\u0003!cE\u0002\u0004)E!\u0005A%\u000b\u0005\u0006a\u0005!\tA\r\u0004\u0005g\u0005\u0001A\u0007\u0003\u0005<\u0007\t\u0005\t\u0015!\u0003=\u0011\u0015\u00014\u0001\"\u0001@\u0011\u0015\u00195\u0001\"\u0011E\u0011\u0015\u00016\u0001\"\u0011R\r\u0015A#\u0005\u0001\u0013S\u0011%1\u0006B!A!\u0002\u00139&\fC\u0005]\u0011\t\u0005\t\u0015!\u0003^A\"A!\r\u0003B\u0001B\u0003%1\rC\u0005g\u0011\t\u0005\t\u0015!\u0003h[\"Aq\u000e\u0003BC\u0002\u0013\u0005\u0001\u000f\u0003\u0005r\u0011\t\u0005\t\u0015!\u0003d\u0011%\u0011\bB!A!\u0002\u0013i6\u000fC\u00031\u0011\u0011\u0005Q\u000f\u0003\u0005<\u0011\t\u0007I\u0011\u0001\u0013~\u0011\u0019q\b\u0002)A\u0005y!Aq\u0010\u0003a\u0001\n\u0013\t\t\u0001C\u0005\u0002\n!\u0001\r\u0011\"\u0003\u0002\f!A\u0011q\u0003\u0005!B\u0013\t\u0019\u0001C\u0004\u0002\"!!\t!!\u0001\t\u000f\u0005\r\u0002\u0002\"\u0001\u0002&!9\u0011\u0011\u0007\u0005\u0005\u0002\u0005M\u0002bBA%\u0011\u0011\u0005\u00111\n\u0005\b\u00033BA\u0011AA.\u0011\u001d\t\t\u0007\u0003C\u0005\u0003GBq!a\u001a\t\t\u0013\tI\u0007C\u0004\u0002n!!I!a\u001c\t\u000f\u0005M\u0004\u0002\"\u0011\u0002v!9\u0011\u0011\u0010\u0005\u0005\u0012\u0005m\u0004bBA?\u0011\u0011\u0005\u0013qP\u0001\u0010%>,H/\u001a3BGR|'oQ3mY*\u00111\u0005J\u0001\be>,H/\u001b8h\u0015\u0005)\u0013\u0001B1lW\u0006\u0004\"aJ\u0001\u000e\u0003\t\u0012qBU8vi\u0016$\u0017i\u0019;pe\u000e+G\u000e\\\n\u0003\u0003)\u0002\"a\u000b\u0018\u000e\u00031R\u0011!L\u0001\u0006g\u000e\fG.Y\u0005\u0003_1\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u0003\u0019\u0012!CU8vi\u0016\u0014\u0018i\u0019;pe\u000e\u0013X-\u0019;peN\u00191AK\u001b\u0011\u0005YJT\"A\u001c\u000b\u0005a\"\u0013!B1di>\u0014\u0018B\u0001\u001e8\u0005UIe\u000eZ5sK\u000e$\u0018i\u0019;peB\u0013x\u000eZ;dKJ\fAB]8vi\u0016\u00148i\u001c8gS\u001e\u0004\"aJ\u001f\n\u0005y\u0012#\u0001\u0004*pkR,'oQ8oM&<GC\u0001!C!\t\t5!D\u0001\u0002\u0011\u0015YT\u00011\u0001=\u0003)\t7\r^8s\u00072\f7o]\u000b\u0002\u000bB\u0019aiS'\u000e\u0003\u001dS!\u0001S%\u0002\t1\fgn\u001a\u0006\u0002\u0015\u0006!!.\u0019<b\u0013\tauIA\u0003DY\u0006\u001c8\u000f\u0005\u0002(\u001d&\u0011qJ\t\u0002\f%>,H/\u001a:BGR|'/A\u0004qe>$WoY3\u0015\u00035\u001b\"\u0001C*\u0011\u0005Y\"\u0016BA+8\u0005%\t5\r^8s\u0007\u0016dG.A\u0004`gf\u001cH/Z7\u0011\u0005YB\u0016BA-8\u0005=\t5\r^8s'f\u001cH/Z7J[Bd\u0017BA.U\u0003\u0019\u0019\u0018p\u001d;f[\u0006!qL]3g!\t1d,\u0003\u0002`o\t\u0001\u0012J\u001c;fe:\fG.Q2u_J\u0014VMZ\u0005\u0003CR\u000bAa]3mM\u0006aqL]8vi\u0016\u0014\bK]8qgB\u0011a\u0007Z\u0005\u0003K^\u0012Q\u0001\u0015:paN\f\u0011c\u0018:pkR,'\u000fR5ta\u0006$8\r[3s!\tA7.D\u0001j\u0015\tQG%\u0001\u0005eSN\u0004\u0018\r^2i\u0013\ta\u0017NA\tNKN\u001c\u0018mZ3ESN\u0004\u0018\r^2iKJL!A\u001c+\u0002\u0015\u0011L7\u000f]1uG\",'/A\u0006s_V$X-\u001a)s_B\u001cX#A2\u0002\u0019I|W\u000f^3f!J|\u0007o\u001d\u0011\u0002\u0017}\u001bX\u000f]3sm&\u001cxN]\u0005\u0003iR\u000ba\u0001]1sK:$Hc\u0002<xqfT8\u0010 \t\u0003O!AQA\u0016\tA\u0002]CQ\u0001\u0018\tA\u0002uCQA\u0019\tA\u0002\rDQA\u001a\tA\u0002\u001dDQa\u001c\tA\u0002\rDQA\u001d\tA\u0002u+\u0012\u0001P\u0001\u000ee>,H/\u001a:D_:4\u0017n\u001a\u0011\u0002\u000f}\u0013x.\u001e;feV\u0011\u00111\u0001\t\u0004O\u0005\u0015\u0011bAA\u0004E\t1!k\\;uKJ\f1b\u0018:pkR,'o\u0018\u0013fcR!\u0011QBA\n!\rY\u0013qB\u0005\u0004\u0003#a#\u0001B+oSRD\u0011\"!\u0006\u0015\u0003\u0003\u0005\r!a\u0001\u0002\u0007a$\u0013'\u0001\u0005`e>,H/\u001a:!Q\r)\u00121\u0004\t\u0004W\u0005u\u0011bAA\u0010Y\tAao\u001c7bi&dW-\u0001\u0004s_V$XM]\u0001\nC\u0012$'k\\;uK\u0016$B!!\u0004\u0002(!9\u0011\u0011F\fA\u0002\u0005-\u0012A\u0002:pkR,W\rE\u0002(\u0003[I1!a\f#\u0005\u0019\u0011v.\u001e;fK\u0006Q\u0011\r\u001a3S_V$X-Z:\u0015\t\u00055\u0011Q\u0007\u0005\b\u0003oA\u0002\u0019AA\u001d\u0003\u001d\u0011x.\u001e;fKN\u0004b!a\u000f\u0002F\u0005-RBAA\u001f\u0015\u0011\ty$!\u0011\u0002\u0013%lW.\u001e;bE2,'bAA\"Y\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005\u001d\u0013Q\b\u0002\t\u0013R,'/\u00192mK\u0006a!/Z7pm\u0016\u0014v.\u001e;fKR1\u0011QBA'\u0003\u001fBq!!\u000b\u001a\u0001\u0004\tY\u0003C\u0004\u0002Re\u0001\r!a\u0015\u0002\u0013M$x\u000e]\"iS2$\u0007cA\u0016\u0002V%\u0019\u0011q\u000b\u0017\u0003\u000f\t{w\u000e\\3b]\u0006i!/Z7pm\u0016\u0014v.\u001e;fKN$b!!\u0004\u0002^\u0005}\u0003bBA\u001c5\u0001\u0007\u0011\u0011\b\u0005\b\u0003#R\u0002\u0019AA*\u0003\u00159\u0018\r^2i)\u0011\ti!!\u001a\t\u000f\u0005%2\u00041\u0001\u0002,\u00059QO\\<bi\u000eDG\u0003BA\u0007\u0003WBq!!\u000b\u001d\u0001\u0004\tY#A\u0006ti>\u0004\u0018JZ\"iS2$G\u0003BA\u0007\u0003cBq!!\u000b\u001e\u0001\u0004\tY#A\u0003ti\u0006\u0014H\u000f\u0006\u0002\u0002x5\t\u0001\"A\u0007qe\u0016\u001cV\u000f]3s'R\f'\u000f\u001e\u000b\u0003\u0003\u001b\t1b]3oI6+7o]1hKR!\u0011QBAA\u0011\u001d\t\u0019\t\ta\u0001\u0003\u000b\u000b\u0001\"\u001a8wK2|\u0007/\u001a\t\u0004Q\u0006\u001d\u0015bAAES\nAQI\u001c<fY>\u0004X\r")
public class RoutedActorCell
extends ActorCell {
    private final Props routeeProps;
    private final RouterConfig routerConfig;
    private volatile Router _router;

    public Props routeeProps() {
        return this.routeeProps;
    }

    public RouterConfig routerConfig() {
        return this.routerConfig;
    }

    private Router _router() {
        return this._router;
    }

    private void _router_$eq(Router x$1) {
        this._router = x$1;
    }

    public Router router() {
        return this._router();
    }

    public void addRoutee(Routee routee) {
        this.addRoutees(new $colon$colon<Nothing$>((Nothing$)((Object)routee), Nil$.MODULE$));
    }

    public void addRoutees(Iterable<Routee> routees) {
        routees.foreach((Function1<Routee, Object> & Serializable)routee -> {
            this.watch(routee);
            return BoxedUnit.UNIT;
        });
        Router r = this._router();
        this._router_$eq(r.withRoutees((IndexedSeq)r.routees().$plus$plus(routees)));
    }

    public void removeRoutee(Routee routee, boolean stopChild) {
        this.removeRoutees(new $colon$colon<Nothing$>((Nothing$)((Object)routee), Nil$.MODULE$), stopChild);
    }

    public void removeRoutees(Iterable<Routee> routees, boolean stopChild) {
        Router r = this._router();
        IndexedSeq<Routee> newRoutees = routees.foldLeft(r.routees(), (Function2<IndexedSeq, Routee, IndexedSeq> & Serializable)(xs, x) -> {
            this.unwatch((Routee)x);
            return (IndexedSeq)xs.filterNot((Function1<Routee, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(RoutedActorCell.$anonfun$removeRoutees$2(x, x$1)));
        });
        this._router_$eq(r.withRoutees(newRoutees));
        if (stopChild) {
            routees.foreach((Function1<Routee, Object> & Serializable)routee -> {
                this.stopIfChild(routee);
                return BoxedUnit.UNIT;
            });
            return;
        }
    }

    private void watch(Routee routee) {
        Routee routee2 = routee;
        if (routee2 instanceof ActorRefRoutee) {
            ActorRefRoutee actorRefRoutee = (ActorRefRoutee)routee2;
            ActorRef ref = actorRefRoutee.ref();
            this.watch(ref);
            return;
        }
    }

    private void unwatch(Routee routee) {
        Routee routee2 = routee;
        if (routee2 instanceof ActorRefRoutee) {
            ActorRefRoutee actorRefRoutee = (ActorRefRoutee)routee2;
            ActorRef ref = actorRefRoutee.ref();
            this.unwatch(ref);
            return;
        }
    }

    private void stopIfChild(Routee routee) {
        Routee routee2 = routee;
        if (routee2 instanceof ActorRefRoutee) {
            ActorRefRoutee actorRefRoutee = (ActorRefRoutee)routee2;
            ActorRef ref = actorRefRoutee.ref();
            Option<ActorRef> option = this.child(ref.path().name());
            if (option instanceof Some) {
                Some some = (Some)option;
                ActorRef actorRef2 = (ActorRef)some.value();
                ActorRef actorRef3 = ref;
                ActorRef actorRef4 = actorRef2;
                if (!(actorRef3 != null ? !((Object)actorRef3).equals(actorRef4) : actorRef4 != null)) {
                    FiniteDuration x$1 = new package.DurationInt(scala.concurrent.duration.package$.MODULE$.DurationInt(100)).milliseconds();
                    ActorRef x$2 = ref;
                    PoisonPill$ x$3 = PoisonPill$.MODULE$;
                    MessageDispatcher x$4 = this.dispatcher();
                    ActorRef x$5 = this.system().scheduler().scheduleOnce$default$5(x$1, x$2, x$3);
                    this.system().scheduler().scheduleOnce(x$1, x$2, (Object)x$3, (ExecutionContext)x$4, x$5);
                }
            }
            return;
        }
    }

    @Override
    public RoutedActorCell start() {
        this._router_$eq(this.routerConfig().createRouter(this.system()));
        RouterConfig routerConfig = this.routerConfig();
        if (routerConfig instanceof Pool) {
            Pool pool = (Pool)routerConfig;
            int nrOfRoutees = pool.nrOfInstances(this.system());
            if (nrOfRoutees > 0) {
                this.addRoutees((Iterable)((Object)package$.MODULE$.Vector().fill(nrOfRoutees, (Function0<Routee> & Serializable)() -> pool.newRoutee(this.routeeProps(), this))));
            }
        } else if (routerConfig instanceof Group) {
            Group group = (Group)routerConfig;
            Iterable<String> paths = group.paths(this.system());
            if (paths.nonEmpty()) {
                this.addRoutees(paths.iterator().map((Function1<String, Routee> & Serializable)p -> group.routeeFor((String)p, this)).to(IterableFactory$.MODULE$.toFactory(IndexedSeq$.MODULE$)));
            }
        }
        this.preSuperStart();
        return (RoutedActorCell)Dispatch.start$(this);
    }

    public void preSuperStart() {
    }

    @Override
    public void sendMessage(Envelope envelope) {
        if (this.routerConfig().isManagementMessage(envelope.message())) {
            Dispatch.sendMessage$(this, envelope);
            return;
        }
        this.router().route(envelope.message(), envelope.sender());
    }

    public static final /* synthetic */ boolean $anonfun$removeRoutees$2(Routee x$3, Routee x$1) {
        Routee routee = x$1;
        Routee routee2 = x$3;
        return !(routee != null ? !routee.equals(routee2) : routee2 != null);
    }

    public RoutedActorCell(ActorSystemImpl _system, InternalActorRef _ref, Props _routerProps, MessageDispatcher _routerDispatcher, Props routeeProps, InternalActorRef _supervisor) {
        this.routeeProps = routeeProps;
        super(_system, _ref, _routerProps, _routerDispatcher, _supervisor);
        this.routerConfig = _routerProps.routerConfig();
        this._router = null;
    }

    public static class RouterActorCreator
    implements IndirectActorProducer {
        private final RouterConfig routerConfig;

        public Class<RouterActor> actorClass() {
            return RouterActor.class;
        }

        @Override
        public RouterActor produce() {
            return this.routerConfig.createRouterActor();
        }

        public RouterActorCreator(RouterConfig routerConfig) {
            this.routerConfig = routerConfig;
        }
    }
}

