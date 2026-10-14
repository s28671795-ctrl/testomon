/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.dispatch.ExecutionContexts$;
import akka.pattern.AskableActorRef$;
import akka.pattern.AskableActorSelection$;
import akka.pattern.PipeToSupport;
import akka.pattern.package$;
import akka.routing.ActorRefRoutee;
import akka.routing.ActorSelectionRoutee;
import akka.routing.Routee;
import akka.routing.ScatterGatherFirstCompletedRoutees$;
import akka.util.Timeout;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005]e!\u0002\u000e\u001c\u0005vy\u0002\u0002\u0003\u001e\u0001\u0005+\u0007I\u0011A\u001e\t\u0011\u0011\u0003!\u0011#Q\u0001\nqB\u0001\"\u0012\u0001\u0003\u0016\u0004%\tA\u0012\u0005\t\u001f\u0002\u0011\t\u0012)A\u0005\u000f\")\u0001\u000b\u0001C\u0001#\")Q\u000b\u0001C!-\"9q\rAA\u0001\n\u0003A\u0007bB6\u0001#\u0003%\t\u0001\u001c\u0005\bo\u0002\t\n\u0011\"\u0001y\u0011\u001dQ\b!!A\u0005BmD\u0011\"!\u0003\u0001\u0003\u0003%\t!a\u0003\t\u0013\u0005M\u0001!!A\u0005\u0002\u0005U\u0001\"CA\u000e\u0001\u0005\u0005I\u0011IA\u000f\u0011%\t9\u0003AA\u0001\n\u0003\tI\u0003C\u0005\u00024\u0001\t\t\u0011\"\u0011\u00026!I\u0011\u0011\b\u0001\u0002\u0002\u0013\u0005\u00131\b\u0005\n\u0003{\u0001\u0011\u0011!C!\u0003\u007fA\u0011\"!\u0011\u0001\u0003\u0003%\t%a\u0011\b\u0015\u0005M3$!A\t\u0002u\t)FB\u0005\u001b7\u0005\u0005\t\u0012A\u000f\u0002X!1\u0001\u000b\u0006C\u0001\u0003_B\u0011\"!\u0010\u0015\u0003\u0003%)%a\u0010\t\u0013\u0005ED#!A\u0005\u0002\u0006M\u0004\"CA=)\u0005\u0005I\u0011QA>\u0011%\ti\tFA\u0001\n\u0013\tyI\u0001\u0012TG\u0006$H/\u001a:HCRDWM\u001d$jeN$8i\\7qY\u0016$X\r\u001a*pkR,Wm\u001d\u0006\u00039u\tqA]8vi&twMC\u0001\u001f\u0003\u0011\t7n[1\u0014\u000b\u0001\u0001cEK\u0017\u0011\u0005\u0005\"S\"\u0001\u0012\u000b\u0003\r\nQa]2bY\u0006L!!\n\u0012\u0003\r\u0005s\u0017PU3g!\t9\u0003&D\u0001\u001c\u0013\tI3D\u0001\u0004S_V$X-\u001a\t\u0003C-J!\u0001\f\u0012\u0003\u000fA\u0013x\u000eZ;diB\u0011af\u000e\b\u0003_Ur!\u0001\r\u001b\u000e\u0003ER!AM\u001a\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011aI\u0005\u0003m\t\nq\u0001]1dW\u0006<W-\u0003\u00029s\ta1+\u001a:jC2L'0\u00192mK*\u0011aGI\u0001\be>,H/Z3t+\u0005a\u0004cA\u001fCM5\taH\u0003\u0002@\u0001\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u0003\n\n!bY8mY\u0016\u001cG/[8o\u0013\t\u0019eH\u0001\u0006J]\u0012,\u00070\u001a3TKF\f\u0001B]8vi\u0016,7\u000fI\u0001\u0007o&$\b.\u001b8\u0016\u0003\u001d\u0003\"\u0001S'\u000e\u0003%S!AS&\u0002\u0011\u0011,(/\u0019;j_:T!\u0001\u0014\u0012\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0002O\u0013\nqa)\u001b8ji\u0016$UO]1uS>t\u0017aB<ji\"Lg\u000eI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007I\u001bF\u000b\u0005\u0002(\u0001!)!(\u0002a\u0001y!)Q)\u0002a\u0001\u000f\u0006!1/\u001a8e)\r9&l\u0018\t\u0003CaK!!\u0017\u0012\u0003\tUs\u0017\u000e\u001e\u0005\u00067\u001a\u0001\r\u0001X\u0001\b[\u0016\u001c8/Y4f!\t\tS,\u0003\u0002_E\t\u0019\u0011I\\=\t\u000b\u00014\u0001\u0019A1\u0002\rM,g\u000eZ3s!\t\u0011W-D\u0001d\u0015\t!W$A\u0003bGR|'/\u0003\u0002gG\nA\u0011i\u0019;peJ+g-\u0001\u0003d_BLHc\u0001*jU\"9!h\u0002I\u0001\u0002\u0004a\u0004bB#\b!\u0003\u0005\raR\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005i'F\u0001\u001foW\u0005y\u0007C\u00019v\u001b\u0005\t(B\u0001:t\u0003%)hn\u00195fG.,GM\u0003\u0002uE\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Y\f(!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T#A=+\u0005\u001ds\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001}!\ri\u0018QA\u0007\u0002}*\u0019q0!\u0001\u0002\t1\fgn\u001a\u0006\u0003\u0003\u0007\tAA[1wC&\u0019\u0011q\u0001@\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\ti\u0001E\u0002\"\u0003\u001fI1!!\u0005#\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\ra\u0016q\u0003\u0005\n\u00033a\u0011\u0011!a\u0001\u0003\u001b\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u0010!\u0015\t\t#a\t]\u001b\u0005\u0001\u0015bAA\u0013\u0001\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\tY#!\r\u0011\u0007\u0005\ni#C\u0002\u00020\t\u0012qAQ8pY\u0016\fg\u000e\u0003\u0005\u0002\u001a9\t\t\u00111\u0001]\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007q\f9\u0004C\u0005\u0002\u001a=\t\t\u00111\u0001\u0002\u000e\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\u000e\u0005AAo\\*ue&tw\rF\u0001}\u0003\u0019)\u0017/^1mgR!\u00111FA#\u0011!\tIBEA\u0001\u0002\u0004a\u0006f\u0002\u0001\u0002J\u0005=\u0013\u0011\u000b\t\u0004C\u0005-\u0013bAA'E\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005\u00113kY1ui\u0016\u0014x)\u0019;iKJ4\u0015N]:u\u0007>l\u0007\u000f\\3uK\u0012\u0014v.\u001e;fKN\u0004\"a\n\u000b\u0014\u000bQ\tI&!\u001a\u0011\u000f\u0005m\u0013\u0011\r\u001fH%6\u0011\u0011Q\f\u0006\u0004\u0003?\u0012\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003G\niFA\tBEN$(/Y2u\rVt7\r^5p]J\u0002B!a\u001a\u0002n5\u0011\u0011\u0011\u000e\u0006\u0005\u0003W\n\t!\u0001\u0002j_&\u0019\u0001(!\u001b\u0015\u0005\u0005U\u0013!B1qa2LH#\u0002*\u0002v\u0005]\u0004\"\u0002\u001e\u0018\u0001\u0004a\u0004\"B#\u0018\u0001\u00049\u0015aB;oCB\u0004H.\u001f\u000b\u0005\u0003{\nI\tE\u0003\"\u0003\u007f\n\u0019)C\u0002\u0002\u0002\n\u0012aa\u00149uS>t\u0007#B\u0011\u0002\u0006r:\u0015bAADE\t1A+\u001e9mKJB\u0001\"a#\u0019\u0003\u0003\u0005\rAU\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAI!\ri\u00181S\u0005\u0004\u0003+s(AB(cU\u0016\u001cG\u000f")
public final class ScatterGatherFirstCompletedRoutees
implements Routee,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final IndexedSeq<Routee> routees;
    private final FiniteDuration within;

    public static Option<Tuple2<IndexedSeq<Routee>, FiniteDuration>> unapply(ScatterGatherFirstCompletedRoutees x$0) {
        return ScatterGatherFirstCompletedRoutees$.MODULE$.unapply(x$0);
    }

    public static ScatterGatherFirstCompletedRoutees apply(IndexedSeq<Routee> routees, FiniteDuration within) {
        return ScatterGatherFirstCompletedRoutees$.MODULE$.apply(routees, within);
    }

    public static Function1<Tuple2<IndexedSeq<Routee>, FiniteDuration>, ScatterGatherFirstCompletedRoutees> tupled() {
        return ScatterGatherFirstCompletedRoutees$.MODULE$.tupled();
    }

    public static Function1<IndexedSeq<Routee>, Function1<FiniteDuration, ScatterGatherFirstCompletedRoutees>> curried() {
        return ScatterGatherFirstCompletedRoutees$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public IndexedSeq<Routee> routees() {
        return this.routees;
    }

    public FiniteDuration within() {
        return this.within;
    }

    @Override
    public void send(Object message, ActorRef sender) {
        ExecutionContext ec = ExecutionContexts$.MODULE$.parasitic();
        if (this.routees().isEmpty()) {
            Future reply = Future$.MODULE$.failed(new TimeoutException("Timeout due to no routees"));
            PipeToSupport.PipeableFuture qual$1 = package$.MODULE$.pipe(reply, ec);
            ActorRef x$1 = sender;
            ActorRef x$2 = qual$1.pipeTo$default$2(x$1);
            qual$1.pipeTo(x$1, x$2);
            return;
        }
        Timeout timeout2 = new Timeout(this.within());
        Promise promise = Promise$.MODULE$.apply();
        this.routees().foreach((Function1<Routee, Object> & Serializable)x0$1 -> {
            Routee routee = x0$1;
            if (routee instanceof ActorRefRoutee) {
                ActorRefRoutee actorRefRoutee = (ActorRefRoutee)routee;
                ActorRef ref = actorRefRoutee.ref();
                ActorRef qual$2 = package$.MODULE$.ask(ref);
                Object x$3 = message;
                Timeout x$4 = timeout2;
                ActorRef x$5 = AskableActorRef$.MODULE$.ask$default$3$extension(qual$2, x$3);
                return promise.completeWith(AskableActorRef$.MODULE$.ask$extension(qual$2, x$3, x$4, x$5));
            }
            if (routee instanceof ActorSelectionRoutee) {
                ActorSelectionRoutee actorSelectionRoutee = (ActorSelectionRoutee)routee;
                ActorSelection sel = actorSelectionRoutee.selection();
                ActorSelection qual$3 = package$.MODULE$.ask(sel);
                Object x$6 = message;
                Timeout x$7 = timeout2;
                ActorRef x$8 = AskableActorSelection$.MODULE$.ask$default$3$extension(qual$3, x$6);
                return promise.completeWith(AskableActorSelection$.MODULE$.ask$extension(qual$3, x$6, x$7, x$8));
            }
            return BoxedUnit.UNIT;
        });
        PipeToSupport.PipeableFuture qual$4 = package$.MODULE$.pipe(promise.future(), ec);
        ActorRef x$9 = sender;
        ActorRef x$10 = qual$4.pipeTo$default$2(x$9);
        qual$4.pipeTo(x$9, x$10);
    }

    public ScatterGatherFirstCompletedRoutees copy(IndexedSeq<Routee> routees, FiniteDuration within) {
        return new ScatterGatherFirstCompletedRoutees(routees, within);
    }

    public IndexedSeq<Routee> copy$default$1() {
        return this.routees();
    }

    public FiniteDuration copy$default$2() {
        return this.within();
    }

    @Override
    public String productPrefix() {
        return "ScatterGatherFirstCompletedRoutees";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.routees();
            }
            case 1: {
                return this.within();
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
        return x$1 instanceof ScatterGatherFirstCompletedRoutees;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "routees";
            }
            case 1: {
                return "within";
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
        if (!(object instanceof ScatterGatherFirstCompletedRoutees)) return false;
        boolean bl = true;
        if (!bl) return false;
        ScatterGatherFirstCompletedRoutees scatterGatherFirstCompletedRoutees = (ScatterGatherFirstCompletedRoutees)x$1;
        IndexedSeq<Routee> indexedSeq = this.routees();
        IndexedSeq<Routee> indexedSeq2 = scatterGatherFirstCompletedRoutees.routees();
        if (indexedSeq == null) {
            if (indexedSeq2 != null) {
                return false;
            }
        } else if (!indexedSeq.equals(indexedSeq2)) return false;
        FiniteDuration finiteDuration = this.within();
        FiniteDuration finiteDuration2 = scatterGatherFirstCompletedRoutees.within();
        if (finiteDuration == null) {
            if (finiteDuration2 == null) return true;
            return false;
        } else {
            if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            return true;
        }
    }

    public ScatterGatherFirstCompletedRoutees(IndexedSeq<Routee> routees, FiniteDuration within) {
        this.routees = routees;
        this.within = within;
        Product.$init$(this);
    }
}

