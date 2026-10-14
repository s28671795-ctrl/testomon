/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.Scheduler;
import akka.routing.NoRoutee$;
import akka.routing.Routee;
import akka.routing.RoutingLogic;
import akka.routing.TailChoppingRoutees;
import akka.routing.TailChoppingRoutingLogic$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005%g\u0001\u0002\u0011\"\u0005\u001aB\u0001\u0002\u0011\u0001\u0003\u0016\u0004%\t!\u0011\u0005\t\u0011\u0002\u0011\t\u0012)A\u0005\u0005\"A\u0011\n\u0001BK\u0002\u0013\u0005!\n\u0003\u0005T\u0001\tE\t\u0015!\u0003L\u0011!!\u0006A!f\u0001\n\u0003Q\u0005\u0002C+\u0001\u0005#\u0005\u000b\u0011B&\t\u0011Y\u0003!Q3A\u0005\u0002]C\u0001\u0002\u0018\u0001\u0003\u0012\u0003\u0006I\u0001\u0017\u0005\u0006;\u0002!\tA\u0018\u0005\u0006I\u0002!\t%\u001a\u0005\bq\u0002\t\t\u0011\"\u0001z\u0011\u001dq\b!%A\u0005\u0002}D\u0011\"!\u0006\u0001#\u0003%\t!a\u0006\t\u0013\u0005m\u0001!%A\u0005\u0002\u0005]\u0001\"CA\u000f\u0001E\u0005I\u0011AA\u0010\u0011%\t\u0019\u0003AA\u0001\n\u0003\n)\u0003C\u0005\u00028\u0001\t\t\u0011\"\u0001\u0002:!I\u0011\u0011\t\u0001\u0002\u0002\u0013\u0005\u00111\t\u0005\n\u0003\u0013\u0002\u0011\u0011!C!\u0003\u0017B\u0011\"!\u0016\u0001\u0003\u0003%\t!a\u0016\t\u0013\u0005\u0005\u0004!!A\u0005B\u0005\r\u0004\"CA4\u0001\u0005\u0005I\u0011IA5\u0011%\tY\u0007AA\u0001\n\u0003\ni\u0007C\u0005\u0002p\u0001\t\t\u0011\"\u0011\u0002r\u001dI\u0011\u0011Q\u0011\u0002\u0002#\u0005\u00111\u0011\u0004\tA\u0005\n\t\u0011#\u0001\u0002\u0006\"1QL\u0007C\u0001\u0003;C\u0011\"a\u001b\u001b\u0003\u0003%)%!\u001c\t\u0013\u0005}%$!A\u0005\u0002\u0006\u0005\u0006\"CAV5\u0005\u0005I\u0011QAW\u0011%\tyLGA\u0001\n\u0013\t\tM\u0001\rUC&d7\t[8qa&twMU8vi&tw\rT8hS\u000eT!AI\u0012\u0002\u000fI|W\u000f^5oO*\tA%\u0001\u0003bW.\f7\u0001A\n\u0006\u0001\u001dj\u0013\u0007\u000e\t\u0003Q-j\u0011!\u000b\u0006\u0002U\u0005)1oY1mC&\u0011A&\u000b\u0002\u0007\u0003:L(+\u001a4\u0011\u00059zS\"A\u0011\n\u0005A\n#\u0001\u0004*pkRLgn\u001a'pO&\u001c\u0007C\u0001\u00153\u0013\t\u0019\u0014FA\u0004Qe>$Wo\u0019;\u0011\u0005UjdB\u0001\u001c<\u001d\t9$(D\u00019\u0015\tIT%\u0001\u0004=e>|GOP\u0005\u0002U%\u0011A(K\u0001\ba\u0006\u001c7.Y4f\u0013\tqtH\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002=S\u0005I1o\u00195fIVdWM]\u000b\u0002\u0005B\u00111IR\u0007\u0002\t*\u0011QiI\u0001\u0006C\u000e$xN]\u0005\u0003\u000f\u0012\u0013\u0011bU2iK\u0012,H.\u001a:\u0002\u0015M\u001c\u0007.\u001a3vY\u0016\u0014\b%\u0001\u0004xSRD\u0017N\\\u000b\u0002\u0017B\u0011A*U\u0007\u0002\u001b*\u0011ajT\u0001\tIV\u0014\u0018\r^5p]*\u0011\u0001+K\u0001\u000bG>t7-\u001e:sK:$\u0018B\u0001*N\u000591\u0015N\\5uK\u0012+(/\u0019;j_:\fqa^5uQ&t\u0007%\u0001\u0005j]R,'O^1m\u0003%Ig\u000e^3sm\u0006d\u0007%A\u0004d_:$X\r\u001f;\u0016\u0003a\u0003\"!\u0017.\u000e\u0003=K!aW(\u0003!\u0015CXmY;uS>t7i\u001c8uKb$\u0018\u0001C2p]R,\u0007\u0010\u001e\u0011\u0002\rqJg.\u001b;?)\u0015y\u0006-\u00192d!\tq\u0003\u0001C\u0003A\u0013\u0001\u0007!\tC\u0003J\u0013\u0001\u00071\nC\u0003U\u0013\u0001\u00071\nC\u0003W\u0013\u0001\u0007\u0001,\u0001\u0004tK2,7\r\u001e\u000b\u0004M&t\u0007C\u0001\u0018h\u0013\tA\u0017E\u0001\u0004S_V$X-\u001a\u0005\u0006U*\u0001\ra[\u0001\b[\u0016\u001c8/Y4f!\tAC.\u0003\u0002nS\t\u0019\u0011I\\=\t\u000b=T\u0001\u0019\u00019\u0002\u000fI|W\u000f^3fgB\u0019\u0011O\u001e4\u000e\u0003IT!a\u001d;\u0002\u0013%lW.\u001e;bE2,'BA;*\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003oJ\u0014!\"\u00138eKb,GmU3r\u0003\u0011\u0019w\u000e]=\u0015\u000b}S8\u0010`?\t\u000f\u0001[\u0001\u0013!a\u0001\u0005\"9\u0011j\u0003I\u0001\u0002\u0004Y\u0005b\u0002+\f!\u0003\u0005\ra\u0013\u0005\b-.\u0001\n\u00111\u0001Y\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!!\u0001+\u0007\t\u000b\u0019a\u000b\u0002\u0002\u0006A!\u0011qAA\t\u001b\t\tIA\u0003\u0003\u0002\f\u00055\u0011!C;oG\",7m[3e\u0015\r\ty!K\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\n\u0003\u0013\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"!!\u0007+\u0007-\u000b\u0019!\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u0011\u0011\u0005\u0016\u00041\u0006\r\u0011!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002(A!\u0011\u0011FA\u001a\u001b\t\tYC\u0003\u0003\u0002.\u0005=\u0012\u0001\u00027b]\u001eT!!!\r\u0002\t)\fg/Y\u0005\u0005\u0003k\tYC\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003w\u00012\u0001KA\u001f\u0013\r\ty$\u000b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004W\u0006\u0015\u0003\"CA$%\u0005\u0005\t\u0019AA\u001e\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\n\t\u0006\u0003\u001f\n\tf[\u0007\u0002i&\u0019\u00111\u000b;\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u00033\ny\u0006E\u0002)\u00037J1!!\u0018*\u0005\u001d\u0011un\u001c7fC:D\u0001\"a\u0012\u0015\u0003\u0003\u0005\ra[\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002(\u0005\u0015\u0004\"CA$+\u0005\u0005\t\u0019AA\u001e\u0003!A\u0017m\u001d5D_\u0012,GCAA\u001e\u0003!!xn\u0015;sS:<GCAA\u0014\u0003\u0019)\u0017/^1mgR!\u0011\u0011LA:\u0011!\t9\u0005GA\u0001\u0002\u0004Y\u0007f\u0002\u0001\u0002x\u0005u\u0014q\u0010\t\u0004Q\u0005e\u0014bAA>S\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005AB+Y5m\u0007\"|\u0007\u000f]5oOJ{W\u000f^5oO2{w-[2\u0011\u00059R2#\u0002\u000e\u0002\b\u0006M\u0005#CAE\u0003\u001f\u00135j\u0013-`\u001b\t\tYIC\u0002\u0002\u000e&\nqA];oi&lW-\u0003\u0003\u0002\u0012\u0006-%!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oiA!\u0011QSAN\u001b\t\t9J\u0003\u0003\u0002\u001a\u0006=\u0012AA5p\u0013\rq\u0014q\u0013\u000b\u0003\u0003\u0007\u000bQ!\u00199qYf$\u0012bXAR\u0003K\u000b9+!+\t\u000b\u0001k\u0002\u0019\u0001\"\t\u000b%k\u0002\u0019A&\t\u000bQk\u0002\u0019A&\t\u000bYk\u0002\u0019\u0001-\u0002\u000fUt\u0017\r\u001d9msR!\u0011qVA^!\u0015A\u0013\u0011WA[\u0013\r\t\u0019,\u000b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000f!\n9LQ&L1&\u0019\u0011\u0011X\u0015\u0003\rQ+\b\u000f\\35\u0011!\tiLHA\u0001\u0002\u0004y\u0016a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\u0019\t\u0005\u0003S\t)-\u0003\u0003\u0002H\u0006-\"AB(cU\u0016\u001cG\u000f")
public final class TailChoppingRoutingLogic
implements RoutingLogic,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final Scheduler scheduler;
    private final FiniteDuration within;
    private final FiniteDuration interval;
    private final ExecutionContext context;

    public static Option<Tuple4<Scheduler, FiniteDuration, FiniteDuration, ExecutionContext>> unapply(TailChoppingRoutingLogic x$0) {
        return TailChoppingRoutingLogic$.MODULE$.unapply(x$0);
    }

    public static TailChoppingRoutingLogic apply(Scheduler scheduler, FiniteDuration within, FiniteDuration interval, ExecutionContext context) {
        return TailChoppingRoutingLogic$.MODULE$.apply(scheduler, within, interval, context);
    }

    public static Function1<Tuple4<Scheduler, FiniteDuration, FiniteDuration, ExecutionContext>, TailChoppingRoutingLogic> tupled() {
        return TailChoppingRoutingLogic$.MODULE$.tupled();
    }

    public static Function1<Scheduler, Function1<FiniteDuration, Function1<FiniteDuration, Function1<ExecutionContext, TailChoppingRoutingLogic>>>> curried() {
        return TailChoppingRoutingLogic$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Scheduler scheduler() {
        return this.scheduler;
    }

    public FiniteDuration within() {
        return this.within;
    }

    public FiniteDuration interval() {
        return this.interval;
    }

    public ExecutionContext context() {
        return this.context;
    }

    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {
        if (routees.isEmpty()) {
            return NoRoutee$.MODULE$;
        }
        return new TailChoppingRoutees(this.scheduler(), routees, this.within(), this.interval(), this.context());
    }

    public TailChoppingRoutingLogic copy(Scheduler scheduler, FiniteDuration within, FiniteDuration interval, ExecutionContext context) {
        return new TailChoppingRoutingLogic(scheduler, within, interval, context);
    }

    public Scheduler copy$default$1() {
        return this.scheduler();
    }

    public FiniteDuration copy$default$2() {
        return this.within();
    }

    public FiniteDuration copy$default$3() {
        return this.interval();
    }

    public ExecutionContext copy$default$4() {
        return this.context();
    }

    @Override
    public String productPrefix() {
        return "TailChoppingRoutingLogic";
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
                return this.within();
            }
            case 2: {
                return this.interval();
            }
            case 3: {
                return this.context();
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
        return x$1 instanceof TailChoppingRoutingLogic;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "scheduler";
            }
            case 1: {
                return "within";
            }
            case 2: {
                return "interval";
            }
            case 3: {
                return "context";
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
        if (!(object instanceof TailChoppingRoutingLogic)) return false;
        boolean bl = true;
        if (!bl) return false;
        TailChoppingRoutingLogic tailChoppingRoutingLogic = (TailChoppingRoutingLogic)x$1;
        Scheduler scheduler = this.scheduler();
        Scheduler scheduler2 = tailChoppingRoutingLogic.scheduler();
        if (scheduler == null) {
            if (scheduler2 != null) {
                return false;
            }
        } else if (!scheduler.equals(scheduler2)) return false;
        FiniteDuration finiteDuration = this.within();
        FiniteDuration finiteDuration2 = tailChoppingRoutingLogic.within();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        FiniteDuration finiteDuration3 = this.interval();
        FiniteDuration finiteDuration4 = tailChoppingRoutingLogic.interval();
        if (finiteDuration3 == null) {
            if (finiteDuration4 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration3).equals(finiteDuration4)) return false;
        ExecutionContext executionContext = this.context();
        ExecutionContext executionContext2 = tailChoppingRoutingLogic.context();
        if (executionContext == null) {
            if (executionContext2 == null) return true;
            return false;
        } else {
            if (!executionContext.equals(executionContext2)) return false;
            return true;
        }
    }

    public TailChoppingRoutingLogic(Scheduler scheduler, FiniteDuration within, FiniteDuration interval, ExecutionContext context) {
        this.scheduler = scheduler;
        this.within = within;
        this.interval = interval;
        this.context = context;
        Product.$init$(this);
    }
}

