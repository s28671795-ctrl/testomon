/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Cancellable;
import akka.actor.Scheduler;
import akka.pattern.AskTimeoutException;
import akka.pattern.AskableActorRef$;
import akka.pattern.AskableActorSelection$;
import akka.pattern.PipeToSupport;
import akka.pattern.package$;
import akka.routing.ActorRefRoutee;
import akka.routing.ActorSelectionRoutee;
import akka.routing.Routee;
import akka.routing.TailChoppingRoutees$;
import akka.util.Timeout;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.BuildFrom$;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Random$;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005Mg!B\u0011#\u0005\u00122\u0003\u0002C!\u0001\u0005+\u0007I\u0011\u0001\"\t\u0011%\u0003!\u0011#Q\u0001\n\rC\u0001B\u0013\u0001\u0003\u0016\u0004%\ta\u0013\u0005\t)\u0002\u0011\t\u0012)A\u0005\u0019\"AQ\u000b\u0001BK\u0002\u0013\u0005a\u000b\u0003\u0005`\u0001\tE\t\u0015!\u0003X\u0011!\u0001\u0007A!f\u0001\n\u00031\u0006\u0002C1\u0001\u0005#\u0005\u000b\u0011B,\t\u0011\t\u0004!\u0011!Q\u0001\f\rDQa\u001a\u0001\u0005\u0002!DQ\u0001\u001d\u0001\u0005BED\u0001b \u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0001\u0005\n\u0003\u001f\u0001\u0011\u0013!C\u0001\u0003#A\u0011\"a\n\u0001#\u0003%\t!!\u000b\t\u0013\u00055\u0002!%A\u0005\u0002\u0005=\u0002\"CA\u001a\u0001E\u0005I\u0011AA\u0018\u0011%\t)\u0004AA\u0001\n\u0003\n9\u0004C\u0005\u0002J\u0001\t\t\u0011\"\u0001\u0002L!I\u00111\u000b\u0001\u0002\u0002\u0013\u0005\u0011Q\u000b\u0005\n\u00037\u0002\u0011\u0011!C!\u0003;B\u0011\"a\u001a\u0001\u0003\u0003%\t!!\u001b\t\u0013\u0005M\u0004!!A\u0005B\u0005U\u0004\"CA=\u0001\u0005\u0005I\u0011IA>\u0011%\ti\bAA\u0001\n\u0003\ny\bC\u0005\u0002\u0002\u0002\t\t\u0011\"\u0011\u0002\u0004\u001eQ\u00111\u0013\u0012\u0002\u0002#\u0005A%!&\u0007\u0013\u0005\u0012\u0013\u0011!E\u0001I\u0005]\u0005BB4\u001c\t\u0003\t\u0019\u000bC\u0005\u0002~m\t\t\u0011\"\u0012\u0002\u0000!I\u0011QU\u000e\u0002\u0002\u0013\u0005\u0015q\u0015\u0005\n\u0003k[\u0012\u0011!CA\u0003oC\u0011\"!3\u001c\u0003\u0003%I!a3\u0003'Q\u000b\u0017\u000e\\\"i_B\u0004\u0018N\\4S_V$X-Z:\u000b\u0005\r\"\u0013a\u0002:pkRLgn\u001a\u0006\u0002K\u0005!\u0011m[6b'\u0015\u0001q%L\u00195!\tA3&D\u0001*\u0015\u0005Q\u0013!B:dC2\f\u0017B\u0001\u0017*\u0005\u0019\te.\u001f*fMB\u0011afL\u0007\u0002E%\u0011\u0001G\t\u0002\u0007%>,H/Z3\u0011\u0005!\u0012\u0014BA\u001a*\u0005\u001d\u0001&o\u001c3vGR\u0004\"!\u000e \u000f\u0005YbdBA\u001c<\u001b\u0005A$BA\u001d;\u0003\u0019a$o\\8u}\r\u0001\u0011\"\u0001\u0016\n\u0005uJ\u0013a\u00029bG.\fw-Z\u0005\u0003\u007f\u0001\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!!P\u0015\u0002\u0013M\u001c\u0007.\u001a3vY\u0016\u0014X#A\"\u0011\u0005\u0011;U\"A#\u000b\u0005\u0019#\u0013!B1di>\u0014\u0018B\u0001%F\u0005%\u00196\r[3ek2,'/\u0001\u0006tG\",G-\u001e7fe\u0002\nqA]8vi\u0016,7/F\u0001M!\ri%+L\u0007\u0002\u001d*\u0011q\nU\u0001\nS6lW\u000f^1cY\u0016T!!U\u0015\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002T\u001d\nQ\u0011J\u001c3fq\u0016$7+Z9\u0002\u0011I|W\u000f^3fg\u0002\naa^5uQ&tW#A,\u0011\u0005akV\"A-\u000b\u0005i[\u0016\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005qK\u0013AC2p]\u000e,(O]3oi&\u0011a,\u0017\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0003\u001d9\u0018\u000e\u001e5j]\u0002\n\u0001\"\u001b8uKJ4\u0018\r\\\u0001\nS:$XM\u001d<bY\u0002\n!!Z2\u0011\u0005\u0011,W\"A.\n\u0005\u0019\\&\u0001E#yK\u000e,H/[8o\u0007>tG/\u001a=u\u0003\u0019a\u0014N\\5u}Q)\u0011\u000e\\7o_R\u0011!n\u001b\t\u0003]\u0001AQA\u0019\u0006A\u0004\rDQ!\u0011\u0006A\u0002\rCQA\u0013\u0006A\u00021CQ!\u0016\u0006A\u0002]CQ\u0001\u0019\u0006A\u0002]\u000bAa]3oIR\u0019!/\u001e>\u0011\u0005!\u001a\u0018B\u0001;*\u0005\u0011)f.\u001b;\t\u000bY\\\u0001\u0019A<\u0002\u000f5,7o]1hKB\u0011\u0001\u0006_\u0005\u0003s&\u00121!\u00118z\u0011\u0015Y8\u00021\u0001}\u0003\u0019\u0019XM\u001c3feB\u0011A)`\u0005\u0003}\u0016\u0013\u0001\"Q2u_J\u0014VMZ\u0001\u0005G>\u0004\u0018\u0010\u0006\u0006\u0002\u0004\u0005\u001d\u0011\u0011BA\u0006\u0003\u001b!2A[A\u0003\u0011\u0015\u0011G\u0002q\u0001d\u0011\u001d\tE\u0002%AA\u0002\rCqA\u0013\u0007\u0011\u0002\u0003\u0007A\nC\u0004V\u0019A\u0005\t\u0019A,\t\u000f\u0001d\u0001\u0013!a\u0001/\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAA\nU\r\u0019\u0015QC\u0016\u0003\u0003/\u0001B!!\u0007\u0002$5\u0011\u00111\u0004\u0006\u0005\u0003;\ty\"A\u0005v]\u000eDWmY6fI*\u0019\u0011\u0011E\u0015\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002&\u0005m!!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCAA\u0016U\ra\u0015QC\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\t\t\tDK\u0002X\u0003+\tabY8qs\u0012\"WMZ1vYR$C'A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003s\u0001B!a\u000f\u0002F5\u0011\u0011Q\b\u0006\u0005\u0003\u007f\t\t%\u0001\u0003mC:<'BAA\"\u0003\u0011Q\u0017M^1\n\t\u0005\u001d\u0013Q\b\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u00055\u0003c\u0001\u0015\u0002P%\u0019\u0011\u0011K\u0015\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0007]\f9\u0006C\u0005\u0002ZM\t\t\u00111\u0001\u0002N\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\u0018\u0011\u000b\u0005\u0005\u00141M<\u000e\u0003AK1!!\u001aQ\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005-\u0014\u0011\u000f\t\u0004Q\u00055\u0014bAA8S\t9!i\\8mK\u0006t\u0007\u0002CA-+\u0005\u0005\t\u0019A<\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003s\t9\bC\u0005\u0002ZY\t\t\u00111\u0001\u0002N\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002N\u0005AAo\\*ue&tw\r\u0006\u0002\u0002:\u00051Q-];bYN$B!a\u001b\u0002\u0006\"A\u0011\u0011L\r\u0002\u0002\u0003\u0007q\u000fK\u0004\u0001\u0003\u0013\u000by)!%\u0011\u0007!\nY)C\u0002\u0002\u000e&\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005\t1\u0003V1jY\u000eCw\u000e\u001d9j]\u001e\u0014v.\u001e;fKN\u0004\"AL\u000e\u0014\tm9\u0013\u0011\u0014\t\u0005\u00037\u000b\t+\u0004\u0002\u0002\u001e*!\u0011qTA!\u0003\tIw.C\u0002@\u0003;#\"!!&\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0015\u0005%\u0016QVAX\u0003c\u000b\u0019\fF\u0002k\u0003WCQA\u0019\u0010A\u0004\rDQ!\u0011\u0010A\u0002\rCQA\u0013\u0010A\u00021CQ!\u0016\u0010A\u0002]CQ\u0001\u0019\u0010A\u0002]\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002:\u0006\u0015\u0007#\u0002\u0015\u0002<\u0006}\u0016bAA_S\t1q\n\u001d;j_:\u0004r\u0001KAa\u00072;v+C\u0002\u0002D&\u0012a\u0001V;qY\u0016$\u0004\u0002CAd?\u0005\u0005\t\u0019\u00016\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002NB!\u00111HAh\u0013\u0011\t\t.!\u0010\u0003\r=\u0013'.Z2u\u0001")
public final class TailChoppingRoutees
implements Routee,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final Scheduler scheduler;
    private final IndexedSeq<Routee> routees;
    private final FiniteDuration within;
    private final FiniteDuration interval;
    private final ExecutionContext ec;

    public static Option<Tuple4<Scheduler, IndexedSeq<Routee>, FiniteDuration, FiniteDuration>> unapply(TailChoppingRoutees x$0) {
        return TailChoppingRoutees$.MODULE$.unapply(x$0);
    }

    public static TailChoppingRoutees apply(Scheduler scheduler, IndexedSeq<Routee> routees, FiniteDuration within, FiniteDuration interval, ExecutionContext ec) {
        return TailChoppingRoutees$.MODULE$.apply(scheduler, routees, within, interval, ec);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Scheduler scheduler() {
        return this.scheduler;
    }

    public IndexedSeq<Routee> routees() {
        return this.routees;
    }

    public FiniteDuration within() {
        return this.within;
    }

    public FiniteDuration interval() {
        return this.interval;
    }

    @Override
    public void send(Object message, ActorRef sender) {
        Timeout timeout2 = new Timeout(this.within());
        Promise promise = Promise$.MODULE$.apply();
        IndexedSeq shuffled2 = (IndexedSeq)Random$.MODULE$.shuffle(this.routees(), BuildFrom$.MODULE$.buildFromIterableOps());
        AtomicInteger aIdx = new AtomicInteger();
        int size = shuffled2.length();
        Cancellable tryWithNext = this.scheduler().scheduleWithFixedDelay(Duration$.MODULE$.Zero(), this.interval(), () -> {
            int idx = aIdx.getAndIncrement();
            if (idx < size) {
                Routee routee = (Routee)shuffled2.apply(idx);
                if (routee instanceof ActorRefRoutee) {
                    ActorRefRoutee actorRefRoutee = (ActorRefRoutee)routee;
                    ActorRef ref = actorRefRoutee.ref();
                    ActorRef qual$1 = package$.MODULE$.ask(ref);
                    Object x$1 = message;
                    Timeout x$2 = timeout2;
                    ActorRef x$3 = AskableActorRef$.MODULE$.ask$default$3$extension(qual$1, x$1);
                    promise.completeWith(AskableActorRef$.MODULE$.ask$extension(qual$1, x$1, x$2, x$3));
                    return;
                }
                if (routee instanceof ActorSelectionRoutee) {
                    ActorSelectionRoutee actorSelectionRoutee = (ActorSelectionRoutee)routee;
                    ActorSelection sel = actorSelectionRoutee.selection();
                    ActorSelection qual$2 = package$.MODULE$.ask(sel);
                    Object x$4 = message;
                    Timeout x$5 = timeout2;
                    ActorRef x$6 = AskableActorSelection$.MODULE$.ask$default$3$extension(qual$2, x$4);
                    promise.completeWith(AskableActorSelection$.MODULE$.ask$extension(qual$2, x$4, x$5, x$6));
                    return;
                }
                return;
            }
        }, this.ec);
        Cancellable sendTimeout = this.scheduler().scheduleOnce(this.within(), () -> promise.tryFailure(new AskTimeoutException(new StringBuilder(41).append("Ask timed out on [").append(sender).append("] after [").append(this.within()).append(".toMillis} ms]").toString())), this.ec);
        Future f = promise.future();
        f.onComplete((Function1<Try, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(TailChoppingRoutees.$anonfun$send$3(tryWithNext, sendTimeout, x0$1)), this.ec);
        PipeToSupport.PipeableFuture qual$3 = package$.MODULE$.pipe(f, this.ec);
        ActorRef x$7 = sender;
        ActorRef x$8 = qual$3.pipeTo$default$2(x$7);
        qual$3.pipeTo(x$7, x$8);
    }

    public TailChoppingRoutees copy(Scheduler scheduler, IndexedSeq<Routee> routees, FiniteDuration within, FiniteDuration interval, ExecutionContext ec) {
        return new TailChoppingRoutees(scheduler, routees, within, interval, ec);
    }

    public Scheduler copy$default$1() {
        return this.scheduler();
    }

    public IndexedSeq<Routee> copy$default$2() {
        return this.routees();
    }

    public FiniteDuration copy$default$3() {
        return this.within();
    }

    public FiniteDuration copy$default$4() {
        return this.interval();
    }

    @Override
    public String productPrefix() {
        return "TailChoppingRoutees";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.scheduler();
            }
            case 1: {
                return this.routees();
            }
            case 2: {
                return this.within();
            }
            case 3: {
                return this.interval();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof TailChoppingRoutees;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "scheduler";
            }
            case 1: {
                return "routees";
            }
            case 2: {
                return "within";
            }
            case 3: {
                return "interval";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof TailChoppingRoutees)) return false;
        boolean bl = true;
        if (!bl) return false;
        TailChoppingRoutees tailChoppingRoutees = (TailChoppingRoutees)x$1;
        Scheduler scheduler = this.scheduler();
        Scheduler scheduler2 = tailChoppingRoutees.scheduler();
        if (scheduler == null) {
            if (scheduler2 != null) {
                return false;
            }
        } else if (!scheduler.equals(scheduler2)) return false;
        IndexedSeq<Routee> indexedSeq = this.routees();
        IndexedSeq<Routee> indexedSeq2 = tailChoppingRoutees.routees();
        if (indexedSeq == null) {
            if (indexedSeq2 != null) {
                return false;
            }
        } else if (!indexedSeq.equals(indexedSeq2)) return false;
        FiniteDuration finiteDuration = this.within();
        FiniteDuration finiteDuration2 = tailChoppingRoutees.within();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        FiniteDuration finiteDuration3 = this.interval();
        FiniteDuration finiteDuration4 = tailChoppingRoutees.interval();
        if (finiteDuration3 == null) {
            if (finiteDuration4 == null) return true;
            return false;
        } else {
            if (!((Object)finiteDuration3).equals(finiteDuration4)) return false;
            return true;
        }
    }

    public static final /* synthetic */ boolean $anonfun$send$3(Cancellable tryWithNext$1, Cancellable sendTimeout$1, Try x0$1) {
        Try try_ = x0$1;
        tryWithNext$1.cancel();
        return sendTimeout$1.cancel();
    }

    public TailChoppingRoutees(Scheduler scheduler, IndexedSeq<Routee> routees, FiniteDuration within, FiniteDuration interval, ExecutionContext ec) {
        this.scheduler = scheduler;
        this.routees = routees;
        this.within = within;
        this.interval = interval;
        this.ec = ec;
        Product.$init$(this);
    }
}

