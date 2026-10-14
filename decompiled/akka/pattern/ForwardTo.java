/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.pattern.ForwardTo$;
import akka.pattern.HandlingWhileStopped;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005ub!\u0002\f\u0018\u0001fY\u0002\u0002\u0003\u001c\u0001\u0005+\u0007I\u0011A\u001c\t\u0011y\u0002!\u0011#Q\u0001\naBQa\u0010\u0001\u0005\u0002\u0001Cqa\u0011\u0001\u0002\u0002\u0013\u0005A\tC\u0004G\u0001E\u0005I\u0011A$\t\u000fI\u0003\u0011\u0011!C!'\"9A\fAA\u0001\n\u0003i\u0006bB1\u0001\u0003\u0003%\tA\u0019\u0005\bQ\u0002\t\t\u0011\"\u0011j\u0011\u001d\u0001\b!!A\u0005\u0002EDqA\u001e\u0001\u0002\u0002\u0013\u0005s\u000fC\u0004z\u0001\u0005\u0005I\u0011\t>\t\u000fm\u0004\u0011\u0011!C!y\"9Q\u0010AA\u0001\n\u0003rxACA\u0001/\u0005\u0005\t\u0012A\r\u0002\u0004\u0019IacFA\u0001\u0012\u0003I\u0012Q\u0001\u0005\u0007\u007fA!\t!!\b\t\u000fm\u0004\u0012\u0011!C#y\"I\u0011q\u0004\t\u0002\u0002\u0013\u0005\u0015\u0011\u0005\u0005\n\u0003K\u0001\u0012\u0011!CA\u0003OA\u0011\"a\r\u0011\u0003\u0003%I!!\u000e\u0003\u0013\u0019{'o^1sIR{'B\u0001\r\u001a\u0003\u001d\u0001\u0018\r\u001e;fe:T\u0011AG\u0001\u0005C.\\\u0017mE\u0003\u00019\t2\u0013\u0006\u0005\u0002\u001eA5\taDC\u0001 \u0003\u0015\u00198-\u00197b\u0013\t\tcD\u0001\u0004B]f\u0014VM\u001a\t\u0003G\u0011j\u0011aF\u0005\u0003K]\u0011A\u0003S1oI2LgnZ,iS2,7\u000b^8qa\u0016$\u0007CA\u000f(\u0013\tAcDA\u0004Qe>$Wo\u0019;\u0011\u0005)\u001adBA\u00162\u001d\ta\u0003'D\u0001.\u0015\tqs&\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005y\u0012B\u0001\u001a\u001f\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001N\u001b\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005Ir\u0012a\u00025b]\u0012dWM]\u000b\u0002qA\u0011\u0011\bP\u0007\u0002u)\u00111(G\u0001\u0006C\u000e$xN]\u0005\u0003{i\u0012\u0001\"Q2u_J\u0014VMZ\u0001\tQ\u0006tG\r\\3sA\u00051A(\u001b8jiz\"\"!\u0011\"\u0011\u0005\r\u0002\u0001\"\u0002\u001c\u0004\u0001\u0004A\u0014\u0001B2paf$\"!Q#\t\u000fY\"\u0001\u0013!a\u0001q\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001%+\u0005aJ5&\u0001&\u0011\u0005-\u0003V\"\u0001'\u000b\u00055s\u0015!C;oG\",7m[3e\u0015\tye$\u0001\u0006b]:|G/\u0019;j_:L!!\u0015'\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002)B\u0011QKW\u0007\u0002-*\u0011q\u000bW\u0001\u0005Y\u0006twMC\u0001Z\u0003\u0011Q\u0017M^1\n\u0005m3&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001_!\tir,\u0003\u0002a=\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u00111M\u001a\t\u0003;\u0011L!!\u001a\u0010\u0003\u0007\u0005s\u0017\u0010C\u0004h\u0011\u0005\u0005\t\u0019\u00010\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005Q\u0007cA6oG6\tAN\u0003\u0002n=\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005=d'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"A];\u0011\u0005u\u0019\u0018B\u0001;\u001f\u0005\u001d\u0011un\u001c7fC:Dqa\u001a\u0006\u0002\u0002\u0003\u00071-\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GC\u0001+y\u0011\u001d97\"!AA\u0002y\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002=\u0006AAo\\*ue&tw\rF\u0001U\u0003\u0019)\u0017/^1mgR\u0011!o \u0005\bO:\t\t\u00111\u0001d\u0003%1uN]<be\u0012$v\u000e\u0005\u0002$!M)\u0001#a\u0002\u0002\u0014A1\u0011\u0011BA\bq\u0005k!!a\u0003\u000b\u0007\u00055a$A\u0004sk:$\u0018.\\3\n\t\u0005E\u00111\u0002\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\u000b\u00037i!!a\u0006\u000b\u0007\u0005e\u0001,\u0001\u0002j_&\u0019A'a\u0006\u0015\u0005\u0005\r\u0011!B1qa2LHcA!\u0002$!)ag\u0005a\u0001q\u00059QO\\1qa2LH\u0003BA\u0015\u0003_\u0001B!HA\u0016q%\u0019\u0011Q\u0006\u0010\u0003\r=\u0003H/[8o\u0011!\t\t\u0004FA\u0001\u0002\u0004\t\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011q\u0007\t\u0004+\u0006e\u0012bAA\u001e-\n1qJ\u00196fGR\u0004")
public class ForwardTo
implements HandlingWhileStopped,
Product,
Serializable {
    private final ActorRef handler;

    public static Option<ActorRef> unapply(ForwardTo x$0) {
        return ForwardTo$.MODULE$.unapply(x$0);
    }

    public static ForwardTo apply(ActorRef handler) {
        return ForwardTo$.MODULE$.apply(handler);
    }

    public static <A> Function1<ActorRef, A> andThen(Function1<ForwardTo, A> g2) {
        return ForwardTo$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, ForwardTo> compose(Function1<A, ActorRef> g2) {
        return ForwardTo$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef handler() {
        return this.handler;
    }

    public ForwardTo copy(ActorRef handler) {
        return new ForwardTo(handler);
    }

    public ActorRef copy$default$1() {
        return this.handler();
    }

    @Override
    public String productPrefix() {
        return "ForwardTo";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.handler();
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
        return x$1 instanceof ForwardTo;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "handler";
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
        if (!(object instanceof ForwardTo)) return false;
        boolean bl = true;
        if (!bl) return false;
        ForwardTo forwardTo = (ForwardTo)x$1;
        ActorRef actorRef2 = this.handler();
        ActorRef actorRef3 = forwardTo.handler();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        if (!forwardTo.canEqual(this)) return false;
        return true;
    }

    public ForwardTo(ActorRef handler) {
        this.handler = handler;
        Product.$init$(this);
    }
}

