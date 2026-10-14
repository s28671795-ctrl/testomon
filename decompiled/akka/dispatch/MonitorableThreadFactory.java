/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.MonitorableThreadFactory$;
import java.io.Serializable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.concurrent.BlockContext;
import scala.concurrent.CanAwait;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t}t!\u0002\u00192\u0011\u00031d!\u0002\u001d2\u0011\u0003I\u0004\"\u0002%\u0002\t\u0003I\u0005b\u0002&\u0002\u0005\u0004%\ta\u0013\u0005\u0007-\u0006\u0001\u000b\u0011\u0002'\u0007\u000b]\u000b\u0001a\r-\t\u0011\u0019,!\u0011!Q\u0001\n\u001dDQ\u0001S\u0003\u0005\u0002)DQA\\\u0003\u0005B=D\u0011\"a\u0004\u0002\u0003\u0003%\t)!\u0005\t\u0013\t\r\u0014!%A\u0005\u0002\t%\u0001\"\u0003B3\u0003E\u0005I\u0011\u0001B\b\u0011%\u00119'AA\u0001\n\u0003\u0013I\u0007C\u0005\u0003x\u0005\t\n\u0011\"\u0001\u0003\n!I!\u0011P\u0001\u0012\u0002\u0013\u0005!q\u0002\u0005\n\u0005w\n\u0011\u0011!C\u0005\u0005{2Q\u0001O\u0019C\u0003+A!\"!\u0014\u0011\u0005+\u0007I\u0011AA(\u0011)\t\t\u0007\u0005B\tB\u0003%\u0011\u0011\u000b\u0005\u000b\u0003G\u0002\"Q3A\u0005\u0002\u0005\u0015\u0004BCA7!\tE\t\u0015!\u0003\u0002h!Q\u0011q\u000e\t\u0003\u0016\u0004%\t!!\u001d\t\u0015\u0005}\u0004C!E!\u0002\u0013\t\u0019\bC\u0005\u0002\u0002B\u0011)\u001a!C\u0001\u0017\"I\u00111\u0011\t\u0003\u0012\u0003\u0006I\u0001\u0014\u0005\u000b\u0003\u000b\u0003\"Q1A\u0005\u0012\u0005\u001d\u0005BCAK!\tE\t\u0015!\u0003\u0002\n\"1\u0001\n\u0005C\u0001\u0003/Cq!a)\u0011\t\u0003\t)\u000bC\u0004\u0002$B!\t!a+\t\u000f\u0005u\u0006\u0003\"\u0001\u0002@\"9\u0011Q\u0019\t\u0005\u0012\u0005\u001d\u0007\"CAk!\u0005\u0005I\u0011AAl\u0011%\t\u0019\u000fEI\u0001\n\u0003\t)\u000fC\u0005\u0002|B\t\n\u0011\"\u0001\u0002~\"I!\u0011\u0001\t\u0012\u0002\u0013\u0005!1\u0001\u0005\n\u0005\u000f\u0001\u0012\u0013!C\u0001\u0005\u0013A\u0011B!\u0004\u0011#\u0003%\tAa\u0004\t\u0013\tM\u0001c#A\u0005\u0002\u0005\u001d\u0005\"\u0003B\u000b!\u0005\u0005I\u0011\tB\f\u0011%\u0011i\u0002EA\u0001\n\u0003\u0011y\u0002C\u0005\u0003(A\t\t\u0011\"\u0001\u0003*!I!q\u0006\t\u0002\u0002\u0013\u0005#\u0011\u0007\u0005\n\u0005\u007f\u0001\u0012\u0011!C\u0001\u0005\u0003B\u0011B!\u0012\u0011\u0003\u0003%\tEa\u0012\t\u0013\t-\u0003#!A\u0005B\t5\u0003\"\u0003B(!\u0005\u0005I\u0011\tB)\u0011%\u0011\u0019\u0006EA\u0001\n\u0003\u0012)&\u0001\rN_:LGo\u001c:bE2,G\u000b\u001b:fC\u00124\u0015m\u0019;pefT!AM\u001a\u0002\u0011\u0011L7\u000f]1uG\"T\u0011\u0001N\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005]\nQ\"A\u0019\u000315{g.\u001b;pe\u0006\u0014G.\u001a+ie\u0016\fGMR1di>\u0014\u0018pE\u0002\u0002u\u0001\u0003\"a\u000f \u000e\u0003qR\u0011!P\u0001\u0006g\u000e\fG.Y\u0005\u0003\u007fq\u0012a!\u00118z%\u00164\u0007CA!G\u001b\u0005\u0011%BA\"E\u0003\tIwNC\u0001F\u0003\u0011Q\u0017M^1\n\u0005\u001d\u0013%\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\bF\u00017\u0003%!wNT8uQ&tw-F\u0001M!\ti5K\u0004\u0002O#6\tqJ\u0003\u0002Q\t\u0006!A.\u00198h\u0013\t\u0011v*\u0001\u0004UQJ,\u0017\rZ\u0005\u0003)V\u0013\u0001$\u00168dCV<\u0007\u000e^#yG\u0016\u0004H/[8o\u0011\u0006tG\r\\3s\u0015\t\u0011v*\u0001\u0006e_:{G\u000f[5oO\u0002\u0012\u0001$Q6lC\u001a{'o\u001b&pS:<vN]6feRC'/Z1e'\r)\u0011,\u0019\t\u00035~k\u0011a\u0017\u0006\u00039v\u000b!bY8oGV\u0014(/\u001a8u\u0015\tqF)\u0001\u0003vi&d\u0017B\u00011\\\u0005Q1uN]6K_&twk\u001c:lKJ$\u0006N]3bIB\u0011!\rZ\u0007\u0002G*\u0011A\fP\u0005\u0003K\u000e\u0014AB\u00117pG.\u001cuN\u001c;fqR\fQa\u00189p_2\u0004\"A\u00175\n\u0005%\\&\u0001\u0004$pe.Tu.\u001b8Q_>dGCA6n!\taW!D\u0001\u0002\u0011\u00151w\u00011\u0001h\u0003\u001d\u0011Gn\\2l\u001f:,\"\u0001\u001d;\u0015\u0007E\f)\u0001\u0006\u0002s{B\u00111\u000f\u001e\u0007\u0001\t\u0015)\bB1\u0001w\u0005\u0005!\u0016CA<{!\tY\u00040\u0003\u0002zy\t9aj\u001c;iS:<\u0007CA\u001e|\u0013\taHHA\u0002B]fDQA \u0005A\u0004}\f!\u0002]3s[&\u001c8/[8o!\r\u0011\u0017\u0011A\u0005\u0004\u0003\u0007\u0019'\u0001C\"b]\u0006;\u0018-\u001b;\t\u0011\u0005\u001d\u0001\u0002\"a\u0001\u0003\u0013\tQ\u0001\u001e5v].\u0004BaOA\u0006e&\u0019\u0011Q\u0002\u001f\u0003\u0011q\u0012\u0017P\\1nKz\nQ!\u00199qYf$B\"a\u0005\u0003Z\tm#Q\fB0\u0005C\u0002\"a\u000e\t\u0014\u0017A\t9\"!\b\u0002$\u0005E\u0012q\u0007\t\u0004\u001d\u0006e\u0011bAA\u000e\u001f\n1qJ\u00196fGR\u00042AWA\u0010\u0013\r\t\tc\u0017\u0002\u000e)\"\u0014X-\u00193GC\u000e$xN]=\u0011\t\u0005\u0015\u00121\u0006\b\u00045\u0006\u001d\u0012bAA\u00157\u0006aai\u001c:l\u0015>Lg\u000eU8pY&!\u0011QFA\u0018\u0005m1uN]6K_&twk\u001c:lKJ$\u0006N]3bI\u001a\u000b7\r^8ss*\u0019\u0011\u0011F.\u0011\u0007m\n\u0019$C\u0002\u00026q\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0003\u0002:\u0005%c\u0002BA\u001e\u0003\u000brA!!\u0010\u0002D5\u0011\u0011q\b\u0006\u0004\u0003\u0003*\u0014A\u0002\u001fs_>$h(C\u0001>\u0013\r\t9\u0005P\u0001\ba\u0006\u001c7.Y4f\u0013\r9\u00151\n\u0006\u0004\u0003\u000fb\u0014\u0001\u00028b[\u0016,\"!!\u0015\u0011\t\u0005M\u00131\f\b\u0005\u0003+\n9\u0006E\u0002\u0002>qJ1!!\u0017=\u0003\u0019\u0001&/\u001a3fM&!\u0011QLA0\u0005\u0019\u0019FO]5oO*\u0019\u0011\u0011\f\u001f\u0002\u000b9\fW.\u001a\u0011\u0002\u0011\u0011\fW-\\8oS\u000e,\"!a\u001a\u0011\u0007m\nI'C\u0002\u0002lq\u0012qAQ8pY\u0016\fg.A\u0005eC\u0016lwN\\5dA\u0005\u00112m\u001c8uKb$8\t\\1tg2{\u0017\rZ3s+\t\t\u0019\bE\u0003<\u0003k\nI(C\u0002\u0002xq\u0012aa\u00149uS>t\u0007c\u0001(\u0002|%\u0019\u0011QP(\u0003\u0017\rc\u0017m]:M_\u0006$WM]\u0001\u0014G>tG/\u001a=u\u00072\f7o\u001d'pC\u0012,'\u000fI\u0001\u0011Kb\u001cW\r\u001d;j_:D\u0015M\u001c3mKJ\f\u0011#\u001a=dKB$\u0018n\u001c8IC:$G.\u001a:!\u0003\u001d\u0019w.\u001e8uKJ,\"!!#\u0011\t\u0005-\u0015\u0011S\u0007\u0003\u0003\u001bS1!a$\\\u0003\u0019\tGo\\7jG&!\u00111SAG\u0005)\tEo\\7jG2{gnZ\u0001\tG>,h\u000e^3sAQa\u00111CAM\u00037\u000bi*a(\u0002\"\"9\u0011QJ\u000eA\u0002\u0005E\u0003bBA27\u0001\u0007\u0011q\r\u0005\b\u0003_Z\u0002\u0019AA:\u0011!\t\ti\u0007I\u0001\u0002\u0004a\u0005\"CAC7A\u0005\t\u0019AAE\u0003%qWm\u001e+ie\u0016\fG\rF\u0002Z\u0003OCa!!+\u001d\u0001\u00049\u0017\u0001\u00029p_2$B!!,\u00024B\u0019a*a,\n\u0007\u0005EvJ\u0001\u0004UQJ,\u0017\r\u001a\u0005\b\u0003kk\u0002\u0019AA\\\u0003!\u0011XO\u001c8bE2,\u0007c\u0001(\u0002:&\u0019\u00111X(\u0003\u0011I+hN\\1cY\u0016\f\u0001b^5uQ:\u000bW.\u001a\u000b\u0005\u0003'\t\t\rC\u0004\u0002Dz\u0001\r!!\u0015\u0002\u000f9,wOT1nK\u0006!q/\u001b:f+\u0011\tI-!4\u0015\t\u0005-\u0017\u0011\u001b\t\u0004g\u00065GAB; \u0005\u0004\ty-E\u0002x\u0003[Cq!a5 \u0001\u0004\tY-A\u0001u\u0003\u0011\u0019w\u000e]=\u0015\u0019\u0005M\u0011\u0011\\An\u0003;\fy.!9\t\u0013\u00055\u0003\u0005%AA\u0002\u0005E\u0003\"CA2AA\u0005\t\u0019AA4\u0011%\ty\u0007\tI\u0001\u0002\u0004\t\u0019\b\u0003\u0005\u0002\u0002\u0002\u0002\n\u00111\u0001M\u0011%\t)\t\tI\u0001\u0002\u0004\tI)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005\u001d(\u0006BA)\u0003S\\#!a;\u0011\t\u00055\u0018q_\u0007\u0003\u0003_TA!!=\u0002t\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003kd\u0014AC1o]>$\u0018\r^5p]&!\u0011\u0011`Ax\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\tyP\u000b\u0003\u0002h\u0005%\u0018AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0005\u000bQC!a\u001d\u0002j\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\"TC\u0001B\u0006U\ra\u0015\u0011^\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136+\t\u0011\tB\u000b\u0003\u0002\n\u0006%\u0018\u0001E2pk:$XM\u001d\u0013bG\u000e,7o\u001d\u00135\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011!\u0011\u0004\t\u0004\u001d\nm\u0011bAA/\u001f\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011!\u0011\u0005\t\u0004w\t\r\u0012b\u0001B\u0013y\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019!Pa\u000b\t\u0013\t5\u0012&!AA\u0002\t\u0005\u0012a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u00034A)!Q\u0007B\u001eu6\u0011!q\u0007\u0006\u0004\u0005sa\u0014AC2pY2,7\r^5p]&!!Q\bB\u001c\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\u001d$1\t\u0005\t\u0005[Y\u0013\u0011!a\u0001u\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\u0011IB!\u0013\t\u0013\t5B&!AA\u0002\t\u0005\u0012\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\t\u0005\u0012\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\te\u0011AB3rk\u0006d7\u000f\u0006\u0003\u0002h\t]\u0003\u0002\u0003B\u0017_\u0005\u0005\t\u0019\u0001>\t\u000f\u00055\u0013\u00021\u0001\u0002R!9\u00111M\u0005A\u0002\u0005\u001d\u0004bBA8\u0013\u0001\u0007\u00111\u000f\u0005\t\u0003\u0003K\u0001\u0013!a\u0001\u0019\"I\u0011QQ\u0005\u0011\u0002\u0003\u0007\u0011\u0011R\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%i\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$S'A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\t-$1\u000f\t\u0006w\u0005U$Q\u000e\t\rw\t=\u0014\u0011KA4\u0003gb\u0015\u0011R\u0005\u0004\u0005cb$A\u0002+va2,W\u0007C\u0005\u0003v1\t\t\u00111\u0001\u0002\u0014\u0005\u0019\u0001\u0010\n\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00135\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%k\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011q\u0003")
public final class MonitorableThreadFactory
implements ThreadFactory,
ForkJoinPool.ForkJoinWorkerThreadFactory,
Product,
Serializable {
    private final String name;
    private final boolean daemonic;
    private final Option<ClassLoader> contextClassLoader;
    private final Thread.UncaughtExceptionHandler exceptionHandler;
    private final AtomicLong counter;

    public static AtomicLong $lessinit$greater$default$5() {
        return MonitorableThreadFactory$.MODULE$.$lessinit$greater$default$5();
    }

    public static Thread.UncaughtExceptionHandler $lessinit$greater$default$4() {
        return MonitorableThreadFactory$.MODULE$.$lessinit$greater$default$4();
    }

    public static Option<Tuple5<String, Object, Option<ClassLoader>, Thread.UncaughtExceptionHandler, AtomicLong>> unapply(MonitorableThreadFactory x$0) {
        return MonitorableThreadFactory$.MODULE$.unapply(x$0);
    }

    public static AtomicLong apply$default$5() {
        return MonitorableThreadFactory$.MODULE$.apply$default$5();
    }

    public static Thread.UncaughtExceptionHandler apply$default$4() {
        return MonitorableThreadFactory$.MODULE$.apply$default$4();
    }

    public static MonitorableThreadFactory apply(String name, boolean daemonic, Option<ClassLoader> contextClassLoader, Thread.UncaughtExceptionHandler exceptionHandler, AtomicLong counter) {
        return MonitorableThreadFactory$.MODULE$.apply(name, daemonic, contextClassLoader, exceptionHandler, counter);
    }

    public static Thread.UncaughtExceptionHandler doNothing() {
        return MonitorableThreadFactory$.MODULE$.doNothing();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public AtomicLong counter$access$4() {
        return this.counter;
    }

    public String name() {
        return this.name;
    }

    public boolean daemonic() {
        return this.daemonic;
    }

    public Option<ClassLoader> contextClassLoader() {
        return this.contextClassLoader;
    }

    public Thread.UncaughtExceptionHandler exceptionHandler() {
        return this.exceptionHandler;
    }

    public AtomicLong counter() {
        return this.counter;
    }

    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        AkkaForkJoinWorkerThread t2 = this.wire(new AkkaForkJoinWorkerThread(pool));
        t2.setName(new StringBuilder(1).append(this.name()).append("-").append(this.counter().incrementAndGet()).toString());
        return t2;
    }

    @Override
    public Thread newThread(Runnable runnable2) {
        return this.wire(new Thread(runnable2, new StringBuilder(1).append(this.name()).append("-").append(this.counter().incrementAndGet()).toString()));
    }

    public MonitorableThreadFactory withName(String newName) {
        return this.copy(newName, this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5());
    }

    public <T extends Thread> T wire(T t2) {
        t2.setUncaughtExceptionHandler(this.exceptionHandler());
        t2.setDaemon(this.daemonic());
        this.contextClassLoader().foreach((Function1<ClassLoader, Object> & Serializable)x$1 -> {
            t2.setContextClassLoader(x$1);
            return BoxedUnit.UNIT;
        });
        return t2;
    }

    public MonitorableThreadFactory copy(String name, boolean daemonic, Option<ClassLoader> contextClassLoader, Thread.UncaughtExceptionHandler exceptionHandler, AtomicLong counter) {
        return new MonitorableThreadFactory(name, daemonic, contextClassLoader, exceptionHandler, counter);
    }

    public String copy$default$1() {
        return this.name();
    }

    public boolean copy$default$2() {
        return this.daemonic();
    }

    public Option<ClassLoader> copy$default$3() {
        return this.contextClassLoader();
    }

    public Thread.UncaughtExceptionHandler copy$default$4() {
        return this.exceptionHandler();
    }

    public AtomicLong copy$default$5() {
        return this.counter();
    }

    @Override
    public String productPrefix() {
        return "MonitorableThreadFactory";
    }

    @Override
    public int productArity() {
        return 5;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.name();
            }
            case 1: {
                return BoxesRunTime.boxToBoolean(this.daemonic());
            }
            case 2: {
                return this.contextClassLoader();
            }
            case 3: {
                return this.exceptionHandler();
            }
            case 4: {
                return this.counter$access$4();
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
        return x$1 instanceof MonitorableThreadFactory;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "name";
            }
            case 1: {
                return "daemonic";
            }
            case 2: {
                return "contextClassLoader";
            }
            case 3: {
                return "exceptionHandler";
            }
            case 4: {
                return "counter";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.name()));
        n = Statics.mix(n, this.daemonic() ? 1231 : 1237);
        n = Statics.mix(n, Statics.anyHash(this.contextClassLoader()));
        n = Statics.mix(n, Statics.anyHash(this.exceptionHandler()));
        n = Statics.mix(n, Statics.anyHash(this.counter$access$4()));
        return Statics.finalizeHash(n, 5);
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
        if (!(object instanceof MonitorableThreadFactory)) return false;
        boolean bl = true;
        if (!bl) return false;
        MonitorableThreadFactory monitorableThreadFactory = (MonitorableThreadFactory)x$1;
        if (this.daemonic() != monitorableThreadFactory.daemonic()) return false;
        String string = this.name();
        String string2 = monitorableThreadFactory.name();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        Option<ClassLoader> option = this.contextClassLoader();
        Option<ClassLoader> option2 = monitorableThreadFactory.contextClassLoader();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.exceptionHandler();
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = monitorableThreadFactory.exceptionHandler();
        if (uncaughtExceptionHandler == null) {
            if (uncaughtExceptionHandler2 != null) {
                return false;
            }
        } else if (!uncaughtExceptionHandler.equals(uncaughtExceptionHandler2)) return false;
        if (!BoxesRunTime.equalsNumNum(this.counter$access$4(), monitorableThreadFactory.counter$access$4())) return false;
        return true;
    }

    public MonitorableThreadFactory(String name, boolean daemonic, Option<ClassLoader> contextClassLoader, Thread.UncaughtExceptionHandler exceptionHandler, AtomicLong counter) {
        this.name = name;
        this.daemonic = daemonic;
        this.contextClassLoader = contextClassLoader;
        this.exceptionHandler = exceptionHandler;
        this.counter = counter;
        Product.$init$(this);
    }

    public static class AkkaForkJoinWorkerThread
    extends ForkJoinWorkerThread
    implements BlockContext {
        @Override
        public <T> T blockOn(Function0<T> thunk, CanAwait permission) {
            AtomicReference<None$> result = new AtomicReference<None$>(None$.MODULE$);
            ForkJoinPool.managedBlock(new ForkJoinPool.ManagedBlocker(null, result, thunk){
                private final AtomicReference result$1;
                private final Function0 thunk$1;

                public boolean block() {
                    this.result$1.set(new Some<R>(this.thunk$1.apply()));
                    return true;
                }

                public boolean isReleasable() {
                    return ((Option)this.result$1.get()).isDefined();
                }
                {
                    this.result$1 = result$1;
                    this.thunk$1 = thunk$1;
                }
            });
            return (T)((Option)result.get()).get();
        }

        public AkkaForkJoinWorkerThread(ForkJoinPool _pool) {
            super(_pool);
        }
    }
}

