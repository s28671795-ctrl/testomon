/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ChildRestartStats$;
import akka.actor.ChildStats;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005mf\u0001B\u0013'\u0005.B\u0001\"\u0012\u0001\u0003\u0016\u0004%\tA\u0012\u0005\t\u0015\u0002\u0011\t\u0012)A\u0005\u000f\"A1\n\u0001BI\u0002\u0013\u0005A\n\u0003\u0005Q\u0001\t\u0005\r\u0011\"\u0001R\u0011!9\u0006A!E!B\u0013i\u0005\u0002\u0003-\u0001\u0005#\u0007I\u0011A-\t\u0011u\u0003!\u00111A\u0005\u0002yC\u0001\u0002\u0019\u0001\u0003\u0012\u0003\u0006KA\u0017\u0005\u0006C\u0002!\tA\u0019\u0005\u0006O\u0002!\t\u0001\u0014\u0005\u0006Q\u0002!\t!\u001b\u0005\u0006k\u0002!IA\u001e\u0005\bw\u0002\t\t\u0011\"\u0001}\u0011%\t\t\u0001AI\u0001\n\u0003\t\u0019\u0001C\u0005\u0002\u001a\u0001\t\n\u0011\"\u0001\u0002\u001c!I\u0011q\u0004\u0001\u0012\u0002\u0013\u0005\u0011\u0011\u0005\u0005\n\u0003K\u0001\u0011\u0011!C!\u0003OA\u0001\"!\u000f\u0001\u0003\u0003%\t\u0001\u0014\u0005\n\u0003w\u0001\u0011\u0011!C\u0001\u0003{A\u0011\"a\u0012\u0001\u0003\u0003%\t%!\u0013\t\u0013\u0005]\u0003!!A\u0005\u0002\u0005e\u0003\"CA/\u0001\u0005\u0005I\u0011IA0\u0011%\t\u0019\u0007AA\u0001\n\u0003\n)\u0007C\u0005\u0002h\u0001\t\t\u0011\"\u0011\u0002j!I\u00111\u000e\u0001\u0002\u0002\u0013\u0005\u0013QN\u0004\n\u0003c2\u0013\u0011!E\u0001\u0003g2\u0001\"\n\u0014\u0002\u0002#\u0005\u0011Q\u000f\u0005\u0007Cn!\t!!$\t\u0013\u0005\u001d4$!A\u0005F\u0005%\u0004\"CAH7\u0005\u0005I\u0011QAI\u0011%\tIjGI\u0001\n\u0003\tY\u0002C\u0005\u0002\u001cn\t\n\u0011\"\u0001\u0002\"!I\u0011QT\u000e\u0002\u0002\u0013\u0005\u0015q\u0014\u0005\n\u0003[[\u0012\u0013!C\u0001\u00037A\u0011\"a,\u001c#\u0003%\t!!\t\t\u0013\u0005E6$!A\u0005\n\u0005M&!E\"iS2$'+Z:uCJ$8\u000b^1ug*\u0011q\u0005K\u0001\u0006C\u000e$xN\u001d\u0006\u0002S\u0005!\u0011m[6b\u0007\u0001\u0019R\u0001\u0001\u00173me\u0002\"!\f\u0019\u000e\u00039R\u0011aL\u0001\u0006g\u000e\fG.Y\u0005\u0003c9\u0012a!\u00118z%\u00164\u0007CA\u001a5\u001b\u00051\u0013BA\u001b'\u0005)\u0019\u0005.\u001b7e'R\fGo\u001d\t\u0003[]J!\u0001\u000f\u0018\u0003\u000fA\u0013x\u000eZ;diB\u0011!H\u0011\b\u0003w\u0001s!\u0001P \u000e\u0003uR!A\u0010\u0016\u0002\rq\u0012xn\u001c;?\u0013\u0005y\u0013BA!/\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0011#\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005\u0005s\u0013!B2iS2$W#A$\u0011\u0005MB\u0015BA%'\u0005!\t5\r^8s%\u00164\u0017AB2iS2$\u0007%A\nnCbt%o\u00144SKR\u0014\u0018.Z:D_VtG/F\u0001N!\tic*\u0003\u0002P]\t\u0019\u0011J\u001c;\u0002/5\f\u0007P\u0014:PMJ+GO]5fg\u000e{WO\u001c;`I\u0015\fHC\u0001*V!\ti3+\u0003\u0002U]\t!QK\\5u\u0011\u001d1F!!AA\u00025\u000b1\u0001\u001f\u00132\u0003Qi\u0017\r\u001f(s\u001f\u001a\u0014V\r\u001e:jKN\u001cu.\u001e8uA\u0005Y\"/Z:uCJ$H+[7f/&tGm\\<Ti\u0006\u0014HOT1o_N,\u0012A\u0017\t\u0003[mK!\u0001\u0018\u0018\u0003\t1{gnZ\u0001 e\u0016\u001cH/\u0019:u)&lWmV5oI><8\u000b^1si:\u000bgn\\:`I\u0015\fHC\u0001*`\u0011\u001d1v!!AA\u0002i\u000bAD]3ti\u0006\u0014H\u000fV5nK^Kg\u000eZ8x'R\f'\u000f\u001e(b]>\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0005G\u0012,g\r\u0005\u00024\u0001!)Q)\u0003a\u0001\u000f\"91*\u0003I\u0001\u0002\u0004i\u0005b\u0002-\n!\u0003\u0005\rAW\u0001\u0004k&$\u0017\u0001\u0007:fcV,7\u000f\u001e*fgR\f'\u000f\u001e)fe6L7o]5p]R\u0011!.\u001c\t\u0003[-L!\u0001\u001c\u0018\u0003\u000f\t{w\u000e\\3b]\")an\u0003a\u0001_\u0006i!/\u001a;sS\u0016\u001cx+\u001b8e_^\u0004B!\f9se&\u0011\u0011O\f\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u00075\u001aX*\u0003\u0002u]\t1q\n\u001d;j_:\f1C]3ue&,7/\u00138XS:$wn^(lCf$2A[<z\u0011\u0015AH\u00021\u0001N\u0003\u001d\u0011X\r\u001e:jKNDQA\u001f\u0007A\u00025\u000baa^5oI><\u0018\u0001B2paf$BaY?\u007f\u007f\"9Q)\u0004I\u0001\u0002\u00049\u0005bB&\u000e!\u0003\u0005\r!\u0014\u0005\b16\u0001\n\u00111\u0001[\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!!\u0002+\u0007\u001d\u000b9a\u000b\u0002\u0002\nA!\u00111BA\u000b\u001b\t\tiA\u0003\u0003\u0002\u0010\u0005E\u0011!C;oG\",7m[3e\u0015\r\t\u0019BL\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\f\u0003\u001b\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"!!\b+\u00075\u000b9!\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005\r\"f\u0001.\u0002\b\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"!!\u000b\u0011\t\u0005-\u0012QG\u0007\u0003\u0003[QA!a\f\u00022\u0005!A.\u00198h\u0015\t\t\u0019$\u0001\u0003kCZ\f\u0017\u0002BA\u001c\u0003[\u0011aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003\u007f\t)\u0005E\u0002.\u0003\u0003J1!a\u0011/\u0005\r\te.\u001f\u0005\b-N\t\t\u00111\u0001N\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA&!\u0019\ti%a\u0015\u0002@5\u0011\u0011q\n\u0006\u0004\u0003#r\u0013AC2pY2,7\r^5p]&!\u0011QKA(\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007)\fY\u0006\u0003\u0005W+\u0005\u0005\t\u0019AA \u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005%\u0012\u0011\r\u0005\b-Z\t\t\u00111\u0001N\u0003!A\u0017m\u001d5D_\u0012,G#A'\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u000b\u0002\r\u0015\fX/\u00197t)\rQ\u0017q\u000e\u0005\t-f\t\t\u00111\u0001\u0002@\u0005\t2\t[5mIJ+7\u000f^1siN#\u0018\r^:\u0011\u0005MZ2#B\u000e\u0002x\u0005\r\u0005\u0003CA=\u0003\u007f:UJW2\u000e\u0005\u0005m$bAA?]\u00059!/\u001e8uS6,\u0017\u0002BAA\u0003w\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c84!\u0011\t))a#\u000e\u0005\u0005\u001d%\u0002BAE\u0003c\t!![8\n\u0007\r\u000b9\t\u0006\u0002\u0002t\u0005)\u0011\r\u001d9msR91-a%\u0002\u0016\u0006]\u0005\"B#\u001f\u0001\u00049\u0005bB&\u001f!\u0003\u0005\r!\u0014\u0005\b1z\u0001\n\u00111\u0001[\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u0012\u0014aD1qa2LH\u0005Z3gCVdG\u000fJ\u001a\u0002\u000fUt\u0017\r\u001d9msR!\u0011\u0011UAU!\u0011i3/a)\u0011\r5\n)kR'[\u0013\r\t9K\f\u0002\u0007)V\u0004H.Z\u001a\t\u0011\u0005-\u0016%!AA\u0002\r\f1\u0001\u001f\u00131\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%e\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIM\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!.\u0011\t\u0005-\u0012qW\u0005\u0005\u0003s\u000biC\u0001\u0004PE*,7\r\u001e")
public final class ChildRestartStats
implements ChildStats,
Product,
Serializable {
    private final ActorRef child;
    private int maxNrOfRetriesCount;
    private long restartTimeWindowStartNanos;

    public static long $lessinit$greater$default$3() {
        return ChildRestartStats$.MODULE$.$lessinit$greater$default$3();
    }

    public static int $lessinit$greater$default$2() {
        return ChildRestartStats$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple3<ActorRef, Object, Object>> unapply(ChildRestartStats x$0) {
        return ChildRestartStats$.MODULE$.unapply(x$0);
    }

    public static long apply$default$3() {
        return ChildRestartStats$.MODULE$.apply$default$3();
    }

    public static int apply$default$2() {
        return ChildRestartStats$.MODULE$.apply$default$2();
    }

    public static ChildRestartStats apply(ActorRef child, int maxNrOfRetriesCount, long restartTimeWindowStartNanos) {
        return ChildRestartStats$.MODULE$.apply(child, maxNrOfRetriesCount, restartTimeWindowStartNanos);
    }

    public static Function1<Tuple3<ActorRef, Object, Object>, ChildRestartStats> tupled() {
        return ChildRestartStats$.MODULE$.tupled();
    }

    public static Function1<ActorRef, Function1<Object, Function1<Object, ChildRestartStats>>> curried() {
        return ChildRestartStats$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef child() {
        return this.child;
    }

    public int maxNrOfRetriesCount() {
        return this.maxNrOfRetriesCount;
    }

    public void maxNrOfRetriesCount_$eq(int x$1) {
        this.maxNrOfRetriesCount = x$1;
    }

    public long restartTimeWindowStartNanos() {
        return this.restartTimeWindowStartNanos;
    }

    public void restartTimeWindowStartNanos_$eq(long x$1) {
        this.restartTimeWindowStartNanos = x$1;
    }

    public int uid() {
        return this.child().path().uid();
    }

    public boolean requestRestartPermission(Tuple2<Option<Object>, Option<Object>> retriesWindow) {
        Option<Object> option;
        Some some;
        int retries;
        Option<Object> option2;
        Tuple2<Option<Object>, Option<Object>> tuple2 = retriesWindow;
        if (tuple2 != null && (option2 = tuple2._1()) instanceof Some && (retries = BoxesRunTime.unboxToInt((some = (Some)option2).value())) < 1) {
            return false;
        }
        if (tuple2 != null) {
            Option<Object> option3 = tuple2._1();
            Option<Object> option4 = tuple2._2();
            if (option3 instanceof Some) {
                Some some2 = (Some)option3;
                int retries2 = BoxesRunTime.unboxToInt(some2.value());
                if (None$.MODULE$.equals(option4)) {
                    this.maxNrOfRetriesCount_$eq(this.maxNrOfRetriesCount() + 1);
                    return this.maxNrOfRetriesCount() <= retries2;
                }
            }
        }
        if (tuple2 != null) {
            Option<Object> x = tuple2._1();
            Option<Object> option5 = tuple2._2();
            if (option5 instanceof Some) {
                Some some3 = (Some)option5;
                int window = BoxesRunTime.unboxToInt(some3.value());
                return this.retriesInWindowOkay(x.isDefined() ? BoxesRunTime.unboxToInt(x.get()) : 1, window);
            }
        }
        if (tuple2 != null && None$.MODULE$.equals(option = tuple2._1())) {
            return true;
        }
        throw new MatchError(tuple2);
    }

    private boolean retriesInWindowOkay(int retries, int window) {
        boolean insideWindow;
        long l;
        int retriesDone = this.maxNrOfRetriesCount() + 1;
        long now = System.nanoTime();
        if (this.restartTimeWindowStartNanos() == 0L) {
            this.restartTimeWindowStartNanos_$eq(now);
            l = now;
        } else {
            l = this.restartTimeWindowStartNanos();
        }
        long windowStart = l;
        boolean bl = insideWindow = now - windowStart <= TimeUnit.MILLISECONDS.toNanos(window);
        if (insideWindow) {
            this.maxNrOfRetriesCount_$eq(retriesDone);
            return retriesDone <= retries;
        }
        this.maxNrOfRetriesCount_$eq(1);
        this.restartTimeWindowStartNanos_$eq(now);
        return true;
    }

    public ChildRestartStats copy(ActorRef child, int maxNrOfRetriesCount, long restartTimeWindowStartNanos) {
        return new ChildRestartStats(child, maxNrOfRetriesCount, restartTimeWindowStartNanos);
    }

    public ActorRef copy$default$1() {
        return this.child();
    }

    public int copy$default$2() {
        return this.maxNrOfRetriesCount();
    }

    public long copy$default$3() {
        return this.restartTimeWindowStartNanos();
    }

    @Override
    public String productPrefix() {
        return "ChildRestartStats";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.child();
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.maxNrOfRetriesCount());
            }
            case 2: {
                return BoxesRunTime.boxToLong(this.restartTimeWindowStartNanos());
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
        return x$1 instanceof ChildRestartStats;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "child";
            }
            case 1: {
                return "maxNrOfRetriesCount";
            }
            case 2: {
                return "restartTimeWindowStartNanos";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.child()));
        n = Statics.mix(n, this.maxNrOfRetriesCount());
        n = Statics.mix(n, Statics.longHash(this.restartTimeWindowStartNanos()));
        return Statics.finalizeHash(n, 3);
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
        if (!(object instanceof ChildRestartStats)) return false;
        boolean bl = true;
        if (!bl) return false;
        ChildRestartStats childRestartStats = (ChildRestartStats)x$1;
        if (this.maxNrOfRetriesCount() != childRestartStats.maxNrOfRetriesCount()) return false;
        if (this.restartTimeWindowStartNanos() != childRestartStats.restartTimeWindowStartNanos()) return false;
        ActorRef actorRef2 = this.child();
        ActorRef actorRef3 = childRestartStats.child();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public ChildRestartStats(ActorRef child, int maxNrOfRetriesCount, long restartTimeWindowStartNanos) {
        this.child = child;
        this.maxNrOfRetriesCount = maxNrOfRetriesCount;
        this.restartTimeWindowStartNanos = restartTimeWindowStartNanos;
        Product.$init$(this);
    }
}

