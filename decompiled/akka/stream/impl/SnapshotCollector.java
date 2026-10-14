/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.actor.TimerScheduler;
import akka.actor.TimerSchedulerImpl;
import akka.actor.Timers;
import akka.annotation.InternalApi;
import akka.pattern.StatusReply$Error$;
import akka.pattern.StatusReply$Success$;
import akka.stream.impl.SnapshotCollector$;
import akka.stream.impl.SnapshotCollector$SnapshotTimeout$;
import akka.stream.impl.StreamSupervisor;
import akka.stream.impl.fusing.ActorGraphInterpreter$Snapshot$;
import akka.stream.snapshot.StreamSnapshot;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Set;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005EuA\u0002\u000f\u001e\u0011\u0003\t3E\u0002\u0004&;!\u0005\u0011E\n\u0005\u0006[\u0005!\taL\u0004\u0006a\u0005A\t)\r\u0004\u0006g\u0005A\t\t\u000e\u0005\u0006[\u0011!\t\u0001\u0012\u0005\b\u000b\u0012\t\t\u0011\"\u0011G\u0011\u001dyE!!A\u0005\u0002ACq\u0001\u0016\u0003\u0002\u0002\u0013\u0005Q\u000bC\u0004\\\t\u0005\u0005I\u0011\t/\t\u000f\r$\u0011\u0011!C\u0001I\"9\u0011\u000eBA\u0001\n\u0003R\u0007bB6\u0005\u0003\u0003%\t\u0005\u001c\u0005\b[\u0012\t\t\u0011\"\u0003o\u0011\u0015\u0011\u0018\u0001\"\u0001t\r\u0019)SDA\u0011\u00028!A1p\u0004B\u0001B\u0003%A\u0010\u0003\u0006\u0002\u0012=\u0011\t\u0011)A\u0005\u0003'A!\"!\n\u0010\u0005\u0003\u0005\u000b\u0011BA\u0005\u0011\u0019is\u0002\"\u0001\u0002F!I\u0011qJ\bA\u0002\u0013\u0005\u0011\u0011\u000b\u0005\n\u0003'z\u0001\u0019!C\u0001\u0003+Bq!a\u0018\u0010A\u0003&A\u0010C\u0005\u0002b=\u0001\r\u0011\"\u0001\u0002d!I\u0011qO\bA\u0002\u0013\u0005\u0011\u0011\u0010\u0005\t\u0003{z\u0001\u0015)\u0003\u0002f!9\u0011qP\b\u0005B\u0005\u0005\u0005bBAF\u001f\u0011\u0005\u0011QR\u0001\u0012':\f\u0007o\u001d5pi\u000e{G\u000e\\3di>\u0014(B\u0001\u0010 \u0003\u0011IW\u000e\u001d7\u000b\u0005\u0001\n\u0013AB:ue\u0016\fWNC\u0001#\u0003\u0011\t7n[1\u0011\u0005\u0011\nQ\"A\u000f\u0003#Ms\u0017\r]:i_R\u001cu\u000e\u001c7fGR|'o\u0005\u0002\u0002OA\u0011\u0001fK\u0007\u0002S)\t!&A\u0003tG\u0006d\u0017-\u0003\u0002-S\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002G\u0005y1K\\1qg\"|G\u000fV5nK>,H\u000f\u0005\u00023\t5\t\u0011AA\bT]\u0006\u00048\u000f[8u)&lWm\\;u'\u0011!q%\u000e\u001d\u0011\u0005!2\u0014BA\u001c*\u0005\u001d\u0001&o\u001c3vGR\u0004\"!O!\u000f\u0005izdBA\u001e?\u001b\u0005a$BA\u001f/\u0003\u0019a$o\\8u}%\t!&\u0003\u0002AS\u00059\u0001/Y2lC\u001e,\u0017B\u0001\"D\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u0001\u0015\u0006F\u00012\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tq\t\u0005\u0002I\u001b6\t\u0011J\u0003\u0002K\u0017\u0006!A.\u00198h\u0015\u0005a\u0015\u0001\u00026bm\u0006L!AT%\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005\t\u0006C\u0001\u0015S\u0013\t\u0019\u0016FA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002W3B\u0011\u0001fV\u0005\u00031&\u00121!\u00118z\u0011\u001dQ\u0006\"!AA\u0002E\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A/\u0011\u0007y\u000bg+D\u0001`\u0015\t\u0001\u0017&\u0001\u0006d_2dWm\u0019;j_:L!AY0\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003K\"\u0004\"\u0001\u000b4\n\u0005\u001dL#a\u0002\"p_2,\u0017M\u001c\u0005\b5*\t\t\u00111\u0001W\u0003!A\u0017m\u001d5D_\u0012,G#A)\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012aR\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0002_B\u0011\u0001\n]\u0005\u0003c&\u0013aa\u00142kK\u000e$\u0018!\u00029s_B\u001cHC\u0002;{\u0003\u001f\t\u0019\u0003\u0005\u0002vq6\taO\u0003\u0002xC\u0005)\u0011m\u0019;pe&\u0011\u0011P\u001e\u0002\u0006!J|\u0007o\u001d\u0005\u0006w:\u0001\r\u0001`\u0001\rgR\u0014X-Y7BGR|'o\u001d\t\u0006{\u0006\r\u0011\u0011\u0002\b\u0003}~\u0004\"aO\u0015\n\u0007\u0005\u0005\u0011&\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003\u000b\t9AA\u0002TKRT1!!\u0001*!\r)\u00181B\u0005\u0004\u0003\u001b1(\u0001C!di>\u0014(+\u001a4\t\u000f\u0005Ea\u00021\u0001\u0002\u0014\u00059A/[7f_V$\b\u0003BA\u000b\u0003?i!!a\u0006\u000b\t\u0005e\u00111D\u0001\tIV\u0014\u0018\r^5p]*\u0019\u0011QD\u0015\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0003\u0002\"\u0005]!A\u0004$j]&$X\rR;sCRLwN\u001c\u0005\b\u0003Kq\u0001\u0019AA\u0005\u0003\u001d\u0011X\r\u001d7z)>D3!AA\u0015!\u0011\tY#!\r\u000e\u0005\u00055\"bAA\u0018C\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005M\u0012Q\u0006\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000eK\u0002\u0001\u0003S\u0019baD\u0014\u0002:\u0005}\u0002cA;\u0002<%\u0019\u0011Q\b<\u0003\u000b\u0005\u001bGo\u001c:\u0011\u0007U\f\t%C\u0002\u0002DY\u0014a\u0001V5nKJ\u001cH\u0003CA$\u0003\u0013\nY%!\u0014\u0011\u0005\u0011z\u0001\"B>\u0014\u0001\u0004a\bbBA\t'\u0001\u0007\u00111\u0003\u0005\b\u0003K\u0019\u0002\u0019AA\u0005\u00035aWM\u001a;U_J+7\u000f]8oIV\tA0A\tmK\u001a$Hk\u001c*fgB|g\u000eZ0%KF$B!a\u0016\u0002^A\u0019\u0001&!\u0017\n\u0007\u0005m\u0013F\u0001\u0003V]&$\bb\u0002.\u0016\u0003\u0003\u0005\r\u0001`\u0001\u000fY\u00164G\u000fV8SKN\u0004xN\u001c3!\u0003%\u0019w\u000e\u001c7fGR,G-\u0006\u0002\u0002fA)\u0011(a\u001a\u0002l%\u0019\u0011\u0011N\"\u0003\t1K7\u000f\u001e\t\u0005\u0003[\n\u0019(\u0004\u0002\u0002p)\u0019\u0011\u0011O\u0010\u0002\u0011Mt\u0017\r]:i_RLA!!\u001e\u0002p\tq1\u000b\u001e:fC6\u001cf.\u00199tQ>$\u0018!D2pY2,7\r^3e?\u0012*\u0017\u000f\u0006\u0003\u0002X\u0005m\u0004\u0002\u0003.\u0019\u0003\u0003\u0005\r!!\u001a\u0002\u0015\r|G\u000e\\3di\u0016$\u0007%A\u0004sK\u000e,\u0017N^3\u0016\u0005\u0005\r\u0005\u0003BAC\u0003\u000fk\u0011aD\u0005\u0005\u0003\u0013\u000bYDA\u0004SK\u000e,\u0017N^3\u0002\u001d\r|W\u000e\u001d7fi\u0016Le\rR8oKR\u0011\u0011q\u000b\u0015\u0004\u001f\u0005%\u0002")
@InternalApi
public final class SnapshotCollector
implements Timers {
    public final FiniteDuration akka$stream$impl$SnapshotCollector$$timeout;
    public final ActorRef akka$stream$impl$SnapshotCollector$$replyTo;
    private Set<ActorRef> leftToRespond;
    private List<StreamSnapshot> collected;
    private TimerSchedulerImpl akka$actor$Timers$$_timers;
    private ActorContext context;
    private ActorRef self;

    public static Props props(Set<ActorRef> streamActors, FiniteDuration timeout2, ActorRef replyTo) {
        return SnapshotCollector$.MODULE$.props(streamActors, timeout2, replyTo);
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundPreRestart(Throwable reason, Option message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundReceive(PartialFunction receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    public final TimerScheduler timers() {
        return Timers.timers$(this);
    }

    @Override
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Timers.aroundPreRestart$(this, reason, message);
    }

    @Override
    public void aroundPostStop() {
        Timers.aroundPostStop$(this);
    }

    @Override
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Timers.aroundReceive$(this, receive2, msg);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
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
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
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
    public TimerSchedulerImpl akka$actor$Timers$$_timers() {
        return this.akka$actor$Timers$$_timers;
    }

    @Override
    public final void akka$actor$Timers$_setter_$akka$actor$Timers$$_timers_$eq(TimerSchedulerImpl x$1) {
        this.akka$actor$Timers$$_timers = x$1;
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

    public Set<ActorRef> leftToRespond() {
        return this.leftToRespond;
    }

    public void leftToRespond_$eq(Set<ActorRef> x$1) {
        this.leftToRespond = x$1;
    }

    public List<StreamSnapshot> collected() {
        return this.collected;
    }

    public void collected_$eq(List<StreamSnapshot> x$1) {
        this.collected = x$1;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ SnapshotCollector $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof StreamSnapshot) {
                    StreamSnapshot streamSnapshot;
                    StreamSnapshot streamSnapshot2 = streamSnapshot = (StreamSnapshot)A1;
                    this.$outer.collected_$eq(this.$outer.collected().$colon$colon(streamSnapshot2));
                    this.$outer.leftToRespond_$eq((Set)this.$outer.leftToRespond().$minus(this.$outer.sender()));
                    this.$outer.completeIfDone();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Terminated) {
                    Terminated terminated = (Terminated)A1;
                    ActorRef streamActor = terminated.actor();
                    this.$outer.leftToRespond_$eq((Set)this.$outer.leftToRespond().$minus(streamActor));
                    this.$outer.completeIfDone();
                    return (B1)BoxedUnit.UNIT;
                }
                if (SnapshotCollector$SnapshotTimeout$.MODULE$.equals(A1)) {
                    this.$outer.akka$stream$impl$SnapshotCollector$$replyTo.$bang(StatusReply$Error$.MODULE$.apply(new StringBuilder(67).append("Didn't get replies from all stream actors within the timeout of ").append(this.$outer.akka$stream$impl$SnapshotCollector$$timeout.toMillis()).append(" ms").toString()), this.$outer.self());
                    this.$outer.context().stop(this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof StreamSnapshot) {
                    return true;
                }
                if (object instanceof Terminated) {
                    return true;
                }
                return SnapshotCollector$SnapshotTimeout$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public void completeIfDone() {
        if (this.leftToRespond().isEmpty()) {
            this.akka$stream$impl$SnapshotCollector$$replyTo.$bang(StatusReply$Success$.MODULE$.apply(new StreamSupervisor.ChildrenSnapshots(this.collected())), this.self());
            this.context().stop(this.self());
            return;
        }
    }

    public static final /* synthetic */ void $anonfun$new$1(SnapshotCollector $this, ActorRef ref) {
        $this.context().watch(ref);
        ref.$bang(ActorGraphInterpreter$Snapshot$.MODULE$, $this.self());
    }

    public SnapshotCollector(Set<ActorRef> streamActors, FiniteDuration timeout2, ActorRef replyTo) {
        this.akka$stream$impl$SnapshotCollector$$timeout = timeout2;
        this.akka$stream$impl$SnapshotCollector$$replyTo = replyTo;
        Actor.$init$(this);
        Timers.$init$(this);
        this.leftToRespond = streamActors;
        this.collected = Nil$.MODULE$;
        if (streamActors.isEmpty()) {
            replyTo.$bang(StatusReply$Success$.MODULE$.apply(new StreamSupervisor.ChildrenSnapshots(Nil$.MODULE$)), this.self());
            this.context().stop(this.self());
        } else {
            streamActors.foreach((Function1<ActorRef, Object> & Serializable)ref -> {
                SnapshotCollector.$anonfun$new$1(this, ref);
                return BoxedUnit.UNIT;
            });
        }
        this.timers().startSingleTimer((Object)SnapshotCollector$SnapshotTimeout$.MODULE$, (Object)SnapshotCollector$SnapshotTimeout$.MODULE$, timeout2);
        Statics.releaseFence();
    }
}

