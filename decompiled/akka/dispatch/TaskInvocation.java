/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.Batchable;
import akka.dispatch.TaskInvocation$;
import akka.dispatch.internal.ScalaBatchable$;
import akka.event.EventStream;
import akka.event.Logging;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005-e\u0001\u0002\u0010 \u0005\u0012B\u0001b\u0011\u0001\u0003\u0016\u0004%\t\u0001\u0012\u0005\t\u0017\u0002\u0011\t\u0012)A\u0005\u000b\"AA\n\u0001BK\u0002\u0013\u0005Q\n\u0003\u0005R\u0001\tE\t\u0015!\u0003O\u0011!\u0011\u0006A!f\u0001\n\u0003\u0019\u0006\u0002\u0003.\u0001\u0005#\u0005\u000b\u0011\u0002+\t\u000bm\u0003A\u0011\u0001/\t\u000b\u0005\u0004AQ\t2\t\u000b\u0019\u0004A\u0011A4\t\u000f!\u0004\u0011\u0011!C\u0001S\"9Q\u000eAI\u0001\n\u0003q\u0007bB=\u0001#\u0003%\tA\u001f\u0005\by\u0002\t\n\u0011\"\u0001~\u0011!y\b!!A\u0005B\u0005\u0005\u0001\"CA\u0005\u0001\u0005\u0005I\u0011AA\u0006\u0011%\t\u0019\u0002AA\u0001\n\u0003\t)\u0002C\u0005\u0002\"\u0001\t\t\u0011\"\u0011\u0002$!I\u0011\u0011\u0007\u0001\u0002\u0002\u0013\u0005\u00111\u0007\u0005\n\u0003o\u0001\u0011\u0011!C!\u0003sA\u0011\"!\u0010\u0001\u0003\u0003%\t%a\u0010\t\u0013\u0005\u0005\u0003!!A\u0005B\u0005\r\u0003\"CA#\u0001\u0005\u0005I\u0011IA$\u000f%\tYeHA\u0001\u0012\u0003\tiE\u0002\u0005\u001f?\u0005\u0005\t\u0012AA(\u0011\u0019Y\u0006\u0004\"\u0001\u0002h!I\u0011\u0011\t\r\u0002\u0002\u0013\u0015\u00131\t\u0005\n\u0003SB\u0012\u0011!CA\u0003WB\u0011\"a\u001d\u0019\u0003\u0003%\t)!\u001e\t\u0013\u0005\u001d\u0005$!A\u0005\n\u0005%%A\u0004+bg.LeN^8dCRLwN\u001c\u0006\u0003A\u0005\n\u0001\u0002Z5ta\u0006$8\r\u001b\u0006\u0002E\u0005!\u0011m[6b\u0007\u0001\u0019R\u0001A\u0013.c]\u0002\"AJ\u0016\u000e\u0003\u001dR!\u0001K\u0015\u0002\t1\fgn\u001a\u0006\u0002U\u0005!!.\u0019<b\u0013\tasE\u0001\u0004PE*,7\r\u001e\t\u0003]=j\u0011aH\u0005\u0003a}\u0011\u0011BQ1uG\"\f'\r\\3\u0011\u0005I*T\"A\u001a\u000b\u0003Q\nQa]2bY\u0006L!AN\u001a\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001\b\u0011\b\u0003syr!AO\u001f\u000e\u0003mR!\u0001P\u0012\u0002\rq\u0012xn\u001c;?\u0013\u0005!\u0014BA 4\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u0011\"\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005}\u001a\u0014aC3wK:$8\u000b\u001e:fC6,\u0012!\u0012\t\u0003\r&k\u0011a\u0012\u0006\u0003\u0011\u0006\nQ!\u001a<f]RL!AS$\u0003\u0017\u00153XM\u001c;TiJ,\u0017-\\\u0001\rKZ,g\u000e^*ue\u0016\fW\u000eI\u0001\teVtg.\u00192mKV\ta\n\u0005\u0002'\u001f&\u0011\u0001k\n\u0002\t%Vtg.\u00192mK\u0006I!/\u001e8oC\ndW\rI\u0001\bG2,\u0017M\\;q+\u0005!\u0006c\u0001\u001aV/&\u0011ak\r\u0002\n\rVt7\r^5p]B\u0002\"A\r-\n\u0005e\u001b$\u0001B+oSR\f\u0001b\u00197fC:,\b\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\tusv\f\u0019\t\u0003]\u0001AQaQ\u0004A\u0002\u0015CQ\u0001T\u0004A\u00029CQAU\u0004A\u0002Q\u000b1\"[:CCR\u001c\u0007.\u00192mKV\t1\r\u0005\u00023I&\u0011Qm\r\u0002\b\u0005>|G.Z1o\u0003\r\u0011XO\u001c\u000b\u0002/\u0006!1m\u001c9z)\u0011i&n\u001b7\t\u000f\rS\u0001\u0013!a\u0001\u000b\"9AJ\u0003I\u0001\u0002\u0004q\u0005b\u0002*\u000b!\u0003\u0005\r\u0001V\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005y'FA#qW\u0005\t\bC\u0001:x\u001b\u0005\u0019(B\u0001;v\u0003%)hn\u00195fG.,GM\u0003\u0002wg\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005a\u001c(!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T#A>+\u00059\u0003\u0018AD2paf$C-\u001a4bk2$HeM\u000b\u0002}*\u0012A\u000b]\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\r\u0001c\u0001\u0014\u0002\u0006%\u0019\u0011qA\u0014\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\ti\u0001E\u00023\u0003\u001fI1!!\u00054\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\t9\"!\b\u0011\u0007I\nI\"C\u0002\u0002\u001cM\u00121!\u00118z\u0011%\ty\u0002EA\u0001\u0002\u0004\ti!A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003K\u0001b!a\n\u0002.\u0005]QBAA\u0015\u0015\r\tYcM\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA\u0018\u0003S\u0011\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u00191-!\u000e\t\u0013\u0005}!#!AA\u0002\u0005]\u0011A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\u0001\u0002<!I\u0011qD\n\u0002\u0002\u0003\u0007\u0011QB\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011QB\u0001\ti>\u001cFO]5oOR\u0011\u00111A\u0001\u0007KF,\u0018\r\\:\u0015\u0007\r\fI\u0005C\u0005\u0002 Y\t\t\u00111\u0001\u0002\u0018\u0005qA+Y:l\u0013:4xnY1uS>t\u0007C\u0001\u0018\u0019'\u0015A\u0012\u0011KA/!!\t\u0019&!\u0017F\u001dRkVBAA+\u0015\r\t9fM\u0001\beVtG/[7f\u0013\u0011\tY&!\u0016\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t7\u0007\u0005\u0003\u0002`\u0005\u0015TBAA1\u0015\r\t\u0019'K\u0001\u0003S>L1!QA1)\t\ti%A\u0003baBd\u0017\u0010F\u0004^\u0003[\ny'!\u001d\t\u000b\r[\u0002\u0019A#\t\u000b1[\u0002\u0019\u0001(\t\u000bI[\u0002\u0019\u0001+\u0002\u000fUt\u0017\r\u001d9msR!\u0011qOAB!\u0015\u0011\u0014\u0011PA?\u0013\r\tYh\r\u0002\u0007\u001fB$\u0018n\u001c8\u0011\rI\ny(\u0012(U\u0013\r\t\ti\r\u0002\u0007)V\u0004H.Z\u001a\t\u0011\u0005\u0015E$!AA\u0002u\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\u0005)\u0003")
public final class TaskInvocation
implements Batchable,
Product,
Serializable {
    private final EventStream eventStream;
    private final Runnable runnable;
    private final Function0<BoxedUnit> cleanup;

    public static Option<Tuple3<EventStream, Runnable, Function0<BoxedUnit>>> unapply(TaskInvocation x$0) {
        return TaskInvocation$.MODULE$.unapply(x$0);
    }

    public static TaskInvocation apply(EventStream eventStream, Runnable runnable2, Function0<BoxedUnit> cleanup) {
        return TaskInvocation$.MODULE$.apply(eventStream, runnable2, cleanup);
    }

    public static Function1<Tuple3<EventStream, Runnable, Function0<BoxedUnit>>, TaskInvocation> tupled() {
        return TaskInvocation$.MODULE$.tupled();
    }

    public static Function1<EventStream, Function1<Runnable, Function1<Function0<BoxedUnit>, TaskInvocation>>> curried() {
        return TaskInvocation$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public EventStream eventStream() {
        return this.eventStream;
    }

    public Runnable runnable() {
        return this.runnable;
    }

    public Function0<BoxedUnit> cleanup() {
        return this.cleanup;
    }

    @Override
    public final boolean isBatchable() {
        return ScalaBatchable$.MODULE$.isBatchable(this.runnable());
    }

    @Override
    public void run() {
        block6: {
            try {
                try {
                    this.runnable().run();
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable e = option.get();
                        this.eventStream().publish(new Logging.Error(e, "TaskInvocation", this.getClass(), e.getMessage()));
                        break block6;
                    }
                    throw throwable;
                }
            }
            finally {
                this.cleanup().apply$mcV$sp();
            }
        }
    }

    public TaskInvocation copy(EventStream eventStream, Runnable runnable2, Function0<BoxedUnit> cleanup) {
        return new TaskInvocation(eventStream, runnable2, cleanup);
    }

    public EventStream copy$default$1() {
        return this.eventStream();
    }

    public Runnable copy$default$2() {
        return this.runnable();
    }

    public Function0<BoxedUnit> copy$default$3() {
        return this.cleanup();
    }

    @Override
    public String productPrefix() {
        return "TaskInvocation";
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
                return this.eventStream();
            }
            case 1: {
                return this.runnable();
            }
            case 2: {
                return this.cleanup();
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
        return x$1 instanceof TaskInvocation;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "eventStream";
            }
            case 1: {
                return "runnable";
            }
            case 2: {
                return "cleanup";
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
        if (!(object instanceof TaskInvocation)) return false;
        boolean bl = true;
        if (!bl) return false;
        TaskInvocation taskInvocation = (TaskInvocation)x$1;
        EventStream eventStream = this.eventStream();
        EventStream eventStream2 = taskInvocation.eventStream();
        if (eventStream == null) {
            if (eventStream2 != null) {
                return false;
            }
        } else if (!eventStream.equals(eventStream2)) return false;
        Runnable runnable2 = this.runnable();
        Runnable runnable3 = taskInvocation.runnable();
        if (runnable2 == null) {
            if (runnable3 != null) {
                return false;
            }
        } else if (!runnable2.equals(runnable3)) return false;
        Function0<BoxedUnit> function0 = this.cleanup();
        Function0<BoxedUnit> function02 = taskInvocation.cleanup();
        if (function0 == null) {
            if (function02 == null) return true;
            return false;
        } else {
            if (!function0.equals(function02)) return false;
            return true;
        }
    }

    public TaskInvocation(EventStream eventStream, Runnable runnable2, Function0<BoxedUnit> cleanup) {
        this.eventStream = eventStream;
        this.runnable = runnable2;
        this.cleanup = cleanup;
        Product.$init$(this);
    }
}

