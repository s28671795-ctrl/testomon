/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.TransferPhase;
import akka.stream.impl.TransferState;
import akka.stream.impl.WaitingForUpstreamSubscription$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055d!B\u000e\u001d\u0001\u0002\u0012\u0003\u0002C\u001f\u0001\u0005+\u0007I\u0011\u0001 \t\u0011\t\u0003!\u0011#Q\u0001\n}B\u0001b\u0011\u0001\u0003\u0016\u0004%\t\u0001\u0012\u0005\t\u0011\u0002\u0011\t\u0012)A\u0005\u000b\")\u0011\n\u0001C\u0001\u0015\")a\n\u0001C\u0001\u001f\")1\u000b\u0001C\u0001\u001f\"9A\u000bAA\u0001\n\u0003)\u0006b\u0002-\u0001#\u0003%\t!\u0017\u0005\bI\u0002\t\n\u0011\"\u0001f\u0011\u001d9\u0007!!A\u0005B!Dq!\u001d\u0001\u0002\u0002\u0013\u0005a\bC\u0004s\u0001\u0005\u0005I\u0011A:\t\u000fe\u0004\u0011\u0011!C!u\"I\u00111\u0001\u0001\u0002\u0002\u0013\u0005\u0011Q\u0001\u0005\n\u0003\u0013\u0001\u0011\u0011!C!\u0003\u0017A\u0011\"a\u0004\u0001\u0003\u0003%\t%!\u0005\t\u0013\u0005M\u0001!!A\u0005B\u0005U\u0001\"CA\f\u0001\u0005\u0005I\u0011IA\r\u000f)\tI\u0003HA\u0001\u0012\u0003\u0001\u00131\u0006\u0004\n7q\t\t\u0011#\u0001!\u0003[Aa!S\u000b\u0005\u0002\u0005\u0015\u0003\"CA\n+\u0005\u0005IQIA\u000b\u0011%\t9%FA\u0001\n\u0003\u000bI\u0005C\u0005\u0002PU\t\t\u0011\"!\u0002R!I\u00111M\u000b\u0002\u0002\u0013%\u0011Q\r\u0002\u001f/\u0006LG/\u001b8h\r>\u0014X\u000b]:ue\u0016\fWnU;cg\u000e\u0014\u0018\u000e\u001d;j_:T!!\b\u0010\u0002\t%l\u0007\u000f\u001c\u0006\u0003?\u0001\naa\u001d;sK\u0006l'\"A\u0011\u0002\t\u0005\\7.Y\n\u0006\u0001\rJS\u0006\r\t\u0003I\u001dj\u0011!\n\u0006\u0002M\u0005)1oY1mC&\u0011\u0001&\n\u0002\u0007\u0003:L(+\u001a4\u0011\u0005)ZS\"\u0001\u000f\n\u00051b\"!\u0004+sC:\u001ch-\u001a:Ti\u0006$X\r\u0005\u0002%]%\u0011q&\n\u0002\b!J|G-^2u!\t\t$H\u0004\u00023q9\u00111gN\u0007\u0002i)\u0011QGN\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\ta%\u0003\u0002:K\u00059\u0001/Y2lC\u001e,\u0017BA\u001e=\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tIT%A\u0005sK6\f\u0017N\\5oOV\tq\b\u0005\u0002%\u0001&\u0011\u0011)\n\u0002\u0004\u0013:$\u0018A\u0003:f[\u0006Lg.\u001b8hA\u00059\u0011M\u001c3UQ\u0016tW#A#\u0011\u0005)2\u0015BA$\u001d\u00055!&/\u00198tM\u0016\u0014\b\u000b[1tK\u0006A\u0011M\u001c3UQ\u0016t\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004\u00172k\u0005C\u0001\u0016\u0001\u0011\u0015iT\u00011\u0001@\u0011\u0015\u0019U\u00011\u0001F\u0003\u001dI7OU3bIf,\u0012\u0001\u0015\t\u0003IEK!AU\u0013\u0003\u000f\t{w\u000e\\3b]\u0006Y\u0011n]\"p[BdW\r^3e\u0003\u0011\u0019w\u000e]=\u0015\u0007-3v\u000bC\u0004>\u0011A\u0005\t\u0019A \t\u000f\rC\u0001\u0013!a\u0001\u000b\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001.+\u0005}Z6&\u0001/\u0011\u0005u\u0013W\"\u00010\u000b\u0005}\u0003\u0017!C;oG\",7m[3e\u0015\t\tW%\u0001\u0006b]:|G/\u0019;j_:L!a\u00190\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003\u0019T#!R.\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005I\u0007C\u00016p\u001b\u0005Y'B\u00017n\u0003\u0011a\u0017M\\4\u000b\u00039\fAA[1wC&\u0011\u0001o\u001b\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011Ao\u001e\t\u0003IUL!A^\u0013\u0003\u0007\u0005s\u0017\u0010C\u0004y\u001b\u0005\u0005\t\u0019A \u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005Y\bc\u0001?\u0000i6\tQP\u0003\u0002\u007fK\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0007\u0005\u0005QP\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGc\u0001)\u0002\b!9\u0001pDA\u0001\u0002\u0004!\u0018A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2![A\u0007\u0011\u001dA\b#!AA\u0002}\n\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u007f\u0005AAo\\*ue&tw\rF\u0001j\u0003\u0019)\u0017/^1mgR\u0019\u0001+a\u0007\t\u000fa\u001c\u0012\u0011!a\u0001i\"\u001a\u0001!a\b\u0011\t\u0005\u0005\u0012QE\u0007\u0003\u0003GQ!!\u0019\u0011\n\t\u0005\u001d\u00121\u0005\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\u0010XC&$\u0018N\\4G_J,\u0006o\u001d;sK\u0006l7+\u001e2tGJL\u0007\u000f^5p]B\u0011!&F\n\u0006+\u0005=\u00121\b\t\b\u0003c\t9dP#L\u001b\t\t\u0019DC\u0002\u00026\u0015\nqA];oi&lW-\u0003\u0003\u0002:\u0005M\"!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA!\u0011QHA\"\u001b\t\tyDC\u0002\u0002B5\f!![8\n\u0007m\ny\u0004\u0006\u0002\u0002,\u0005)\u0011\r\u001d9msR)1*a\u0013\u0002N!)Q\b\u0007a\u0001\u007f!)1\t\u0007a\u0001\u000b\u00069QO\\1qa2LH\u0003BA*\u0003?\u0002R\u0001JA+\u00033J1!a\u0016&\u0005\u0019y\u0005\u000f^5p]B)A%a\u0017@\u000b&\u0019\u0011QL\u0013\u0003\rQ+\b\u000f\\33\u0011!\t\t'GA\u0001\u0002\u0004Y\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011q\r\t\u0004U\u0006%\u0014bAA6W\n1qJ\u00196fGR\u0004")
@InternalApi
public class WaitingForUpstreamSubscription
implements TransferState,
Product,
Serializable {
    private final int remaining;
    private final TransferPhase andThen;

    public static Option<Tuple2<Object, TransferPhase>> unapply(WaitingForUpstreamSubscription x$0) {
        return WaitingForUpstreamSubscription$.MODULE$.unapply(x$0);
    }

    public static WaitingForUpstreamSubscription apply(int remaining, TransferPhase andThen) {
        return WaitingForUpstreamSubscription$.MODULE$.apply(remaining, andThen);
    }

    public static Function1<Tuple2<Object, TransferPhase>, WaitingForUpstreamSubscription> tupled() {
        return WaitingForUpstreamSubscription$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<TransferPhase, WaitingForUpstreamSubscription>> curried() {
        return WaitingForUpstreamSubscription$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public boolean isExecutable() {
        return TransferState.isExecutable$(this);
    }

    @Override
    public TransferState $bar$bar(TransferState other) {
        return TransferState.$bar$bar$(this, other);
    }

    @Override
    public TransferState $amp$amp(TransferState other) {
        return TransferState.$amp$amp$(this, other);
    }

    public int remaining() {
        return this.remaining;
    }

    public TransferPhase andThen() {
        return this.andThen;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    public WaitingForUpstreamSubscription copy(int remaining, TransferPhase andThen) {
        return new WaitingForUpstreamSubscription(remaining, andThen);
    }

    public int copy$default$1() {
        return this.remaining();
    }

    public TransferPhase copy$default$2() {
        return this.andThen();
    }

    @Override
    public String productPrefix() {
        return "WaitingForUpstreamSubscription";
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
                return BoxesRunTime.boxToInteger(this.remaining());
            }
            case 1: {
                return this.andThen();
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
        return x$1 instanceof WaitingForUpstreamSubscription;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "remaining";
            }
            case 1: {
                return "andThen";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.remaining());
        n = Statics.mix(n, Statics.anyHash(this.andThen()));
        return Statics.finalizeHash(n, 2);
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
        if (!(object instanceof WaitingForUpstreamSubscription)) return false;
        boolean bl = true;
        if (!bl) return false;
        WaitingForUpstreamSubscription waitingForUpstreamSubscription = (WaitingForUpstreamSubscription)x$1;
        if (this.remaining() != waitingForUpstreamSubscription.remaining()) return false;
        TransferPhase transferPhase = this.andThen();
        TransferPhase transferPhase2 = waitingForUpstreamSubscription.andThen();
        if (transferPhase == null) {
            if (transferPhase2 != null) {
                return false;
            }
        } else if (!((Object)transferPhase).equals(transferPhase2)) return false;
        if (!waitingForUpstreamSubscription.canEqual(this)) return false;
        return true;
    }

    public WaitingForUpstreamSubscription(int remaining, TransferPhase andThen) {
        this.remaining = remaining;
        this.andThen = andThen;
        TransferState.$init$(this);
        Product.$init$(this);
    }
}

