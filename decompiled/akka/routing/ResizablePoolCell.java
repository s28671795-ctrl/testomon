/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorSystemImpl;
import akka.actor.InternalActorRef;
import akka.actor.Props;
import akka.dispatch.Envelope;
import akka.dispatch.Envelope$;
import akka.dispatch.MessageDispatcher;
import akka.routing.OptimalSizeExploringResizer;
import akka.routing.Pool;
import akka.routing.ResizablePoolActor$Resize$;
import akka.routing.Resizer;
import akka.routing.RoutedActorCell;
import akka.routing.Routee;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import scala.Function0;
import scala.Predef$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Vector;
import scala.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-a!\u0002\u000b\u0016\u0005]I\u0002\"\u0003\u0010\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0011'\u0011%Q\u0003A!A!\u0002\u0013Yc\u0006\u0003\u00051\u0001\t\u0005\t\u0015!\u00032\u0011%!\u0004A!A!\u0002\u0013)4\bC\u0005>\u0001\t\u0005\t\u0015!\u00032}!I\u0001\t\u0001B\u0001B\u0003%1&\u0011\u0005\t\u0007\u0002\u0011)\u0019!C\u0001\t\"A\u0001\n\u0001B\u0001B\u0003%Q\tC\u0003J\u0001\u0011\u0005!\nC\u0004T\u0001\t\u0007I\u0011\u0001+\t\ra\u0003\u0001\u0015!\u0003V\u0011\u001dI\u0006A1A\u0005\niCaa\u001a\u0001!\u0002\u0013Y\u0006b\u00025\u0001\u0005\u0004%I!\u001b\u0005\u0007[\u0002\u0001\u000b\u0011\u00026\t\u000b9\u0004A\u0011K8\t\u000bY\u0004A\u0011I<\t\ru\u0004A\u0011A\f\u007f\u0011\u0019\tI\u0001\u0001C\u0005_\n\t\"+Z:ju\u0006\u0014G.\u001a)p_2\u001cU\r\u001c7\u000b\u0005Y9\u0012a\u0002:pkRLgn\u001a\u0006\u00021\u0005!\u0011m[6b'\t\u0001!\u0004\u0005\u0002\u001c95\tQ#\u0003\u0002\u001e+\ty!k\\;uK\u0012\f5\r^8s\u0007\u0016dG.A\u0004`gf\u001cH/Z7\u0004\u0001A\u0011\u0011\u0005J\u0007\u0002E)\u00111eF\u0001\u0006C\u000e$xN]\u0005\u0003K\t\u0012q\"Q2u_J\u001c\u0016p\u001d;f[&k\u0007\u000f\\\u0005\u0003O!\naa]=ti\u0016l\u0017BA\u0015#\u0005%\t5\r^8s\u0007\u0016dG.\u0001\u0003`e\u00164\u0007CA\u0011-\u0013\ti#E\u0001\tJ]R,'O\\1m\u0003\u000e$xN\u001d*fM&\u0011q\u0006K\u0001\u0005g\u0016dg-\u0001\u0007`e>,H/\u001a:Qe>\u00048\u000f\u0005\u0002\"e%\u00111G\t\u0002\u0006!J|\u0007o]\u0001\u0012?J|W\u000f^3s\t&\u001c\b/\u0019;dQ\u0016\u0014\bC\u0001\u001c:\u001b\u00059$B\u0001\u001d\u0018\u0003!!\u0017n\u001d9bi\u000eD\u0017B\u0001\u001e8\u0005EiUm]:bO\u0016$\u0015n\u001d9bi\u000eDWM]\u0005\u0003y!\n!\u0002Z5ta\u0006$8\r[3s\u00031y&o\\;uK\u0016\u0004&o\u001c9t\u0013\tyD$A\u0006s_V$X-\u001a)s_B\u001c\u0018aC0tkB,'O^5t_JL!A\u0011\u0015\u0002\rA\f'/\u001a8u\u0003\u0011\u0001xn\u001c7\u0016\u0003\u0015\u0003\"a\u0007$\n\u0005\u001d+\"\u0001\u0002)p_2\fQ\u0001]8pY\u0002\na\u0001P5oSRtD\u0003C&M\u001b:{\u0005+\u0015*\u0011\u0005m\u0001\u0001\"\u0002\u0010\n\u0001\u0004\u0001\u0003\"\u0002\u0016\n\u0001\u0004Y\u0003\"\u0002\u0019\n\u0001\u0004\t\u0004\"\u0002\u001b\n\u0001\u0004)\u0004\"B\u001f\n\u0001\u0004\t\u0004\"\u0002!\n\u0001\u0004Y\u0003\"B\"\n\u0001\u0004)\u0015a\u0002:fg&TXM]\u000b\u0002+B\u00111DV\u0005\u0003/V\u0011qAU3tSj,'/\u0001\u0005sKNL'0\u001a:!\u0003A\u0011Xm]5{K&s\u0007K]8he\u0016\u001c8/F\u0001\\!\taV-D\u0001^\u0015\tqv,\u0001\u0004bi>l\u0017n\u0019\u0006\u0003A\u0006\f!bY8oGV\u0014(/\u001a8u\u0015\t\u00117-\u0001\u0003vi&d'\"\u00013\u0002\t)\fg/Y\u0005\u0003Mv\u0013Q\"\u0011;p[&\u001c'i\\8mK\u0006t\u0017!\u0005:fg&TX-\u00138Qe><'/Z:tA\u0005i!/Z:ju\u0016\u001cu.\u001e8uKJ,\u0012A\u001b\t\u00039.L!\u0001\\/\u0003\u0015\u0005#x.\\5d\u0019>tw-\u0001\bsKNL'0Z\"pk:$XM\u001d\u0011\u0002\u001bA\u0014XmU;qKJ\u001cF/\u0019:u)\u0005\u0001\bCA9u\u001b\u0005\u0011(\"A:\u0002\u000bM\u001c\u0017\r\\1\n\u0005U\u0014(\u0001B+oSR\f1b]3oI6+7o]1hKR\u0011\u0001\u000f\u001f\u0005\u0006sF\u0001\rA_\u0001\tK:4X\r\\8qKB\u0011ag_\u0005\u0003y^\u0012\u0001\"\u00128wK2|\u0007/Z\u0001\u0007e\u0016\u001c\u0018N_3\u0015\u0005A|\bbBA\u0001%\u0001\u0007\u00111A\u0001\bS:LG/[1m!\r\t\u0018QA\u0005\u0004\u0003\u000f\u0011(a\u0002\"p_2,\u0017M\\\u0001\u0016iJL(+\u001a9peRlUm]:bO\u0016\u001cu.\u001e8u\u0001")
public final class ResizablePoolCell
extends RoutedActorCell {
    private final Pool pool;
    private final Resizer resizer;
    private final AtomicBoolean resizeInProgress;
    private final AtomicLong resizeCounter;

    public Pool pool() {
        return this.pool;
    }

    public Resizer resizer() {
        return this.resizer;
    }

    private AtomicBoolean resizeInProgress() {
        return this.resizeInProgress;
    }

    private AtomicLong resizeCounter() {
        return this.resizeCounter;
    }

    @Override
    public void preSuperStart() {
        if (this.resizer().isTimeForResize(this.resizeCounter().getAndIncrement())) {
            this.resize(true);
            return;
        }
    }

    @Override
    public void sendMessage(Envelope envelope) {
        if (!this.routerConfig().isManagementMessage(envelope.message()) && this.resizer().isTimeForResize(this.resizeCounter().getAndIncrement()) && this.resizeInProgress().compareAndSet(false, true)) {
            super.sendMessage(Envelope$.MODULE$.apply(ResizablePoolActor$Resize$.MODULE$, this.self(), this.system()));
        }
        super.sendMessage(envelope);
    }

    public void resize(boolean initial) {
        if (this.resizeInProgress().get() || initial) {
            try {
                this.tryReportMessageCount();
                int requestedCapacity = this.resizer().resize(this.router().routees());
                if (requestedCapacity > 0) {
                    Vector newRoutees = (Vector)package$.MODULE$.Vector().fill(requestedCapacity, (Function0<Routee> & Serializable)() -> this.pool().newRoutee(this.routeeProps(), this));
                    this.addRoutees(newRoutees);
                } else if (requestedCapacity < 0) {
                    IndexedSeq<Routee> currentRoutees = this.router().routees();
                    IndexedSeq abandon = (IndexedSeq)currentRoutees.drop(currentRoutees.length() + requestedCapacity);
                    this.removeRoutees(abandon, true);
                }
            }
            finally {
                this.resizeInProgress().set(false);
            }
            return;
        }
    }

    private void tryReportMessageCount() {
        Resizer resizer = this.resizer();
        if (resizer instanceof OptimalSizeExploringResizer) {
            OptimalSizeExploringResizer optimalSizeExploringResizer = (OptimalSizeExploringResizer)resizer;
            optimalSizeExploringResizer.reportMessageCount(this.router().routees(), this.resizeCounter().get());
            return;
        }
    }

    public ResizablePoolCell(ActorSystemImpl _system, InternalActorRef _ref, Props _routerProps, MessageDispatcher _routerDispatcher, Props _routeeProps, InternalActorRef _supervisor, Pool pool) {
        this.pool = pool;
        super(_system, _ref, _routerProps, _routerDispatcher, _routeeProps, _supervisor);
        Predef$.MODULE$.require(pool.resizer().isDefined(), (Function0<Object>)(Function0<String> & Serializable)() -> "RouterConfig must be a Pool with defined resizer");
        this.resizer = pool.resizer().get();
        this.resizeInProgress = new AtomicBoolean();
        this.resizeCounter = new AtomicLong();
    }
}

