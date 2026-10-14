/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.ThreadPoolConfig;
import akka.dispatch.ThreadPoolConfig$;
import akka.dispatch.ThreadPoolConfigBuilder$;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.TimeUnit;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\rb\u0001\u0002\u0014(\u00052B\u0001B\u0011\u0001\u0003\u0016\u0004%\ta\u0011\u0005\t\u0011\u0002\u0011\t\u0012)A\u0005\t\")\u0011\n\u0001C\u0001\u0015\")Q\n\u0001C\u0001\u001d\")Q\n\u0001C\u00011\")1\u000e\u0001C\u0001Y\")Q\u000e\u0001C\u0001]\")A\u000f\u0001C\u0001k\")1\u0010\u0001C\u0001y\"1q\u0010\u0001C\u0001\u0003\u0003Aq!a\u0002\u0001\t\u0003\tI\u0001C\u0004\u0002\u000e\u0001!\t!a\u0004\t\u000f\u0005M\u0001\u0001\"\u0001\u0002\u0016!9\u0011\u0011\u0006\u0001\u0005\u0002\u0005-\u0002bBA\u001a\u0001\u0011\u0005\u0011Q\u0007\u0005\b\u0003\u0003\u0002A\u0011AA\"\u0011\u001d\t)\u0006\u0001C\u0001\u0003/Bq!!\u0018\u0001\t\u0003\ty\u0006C\u0004\u0002d\u0001!\t!!\u001a\t\u0013\u0005\u001d\u0005!!A\u0005\u0002\u0005%\u0005\"CAG\u0001E\u0005I\u0011AAH\u0011%\t)\u000bAA\u0001\n\u0003\n9\u000bC\u0005\u00020\u0002\t\t\u0011\"\u0001\u00022\"I\u00111\u0017\u0001\u0002\u0002\u0013\u0005\u0011Q\u0017\u0005\n\u0003\u0003\u0004\u0011\u0011!C!\u0003\u0007D\u0011\"!5\u0001\u0003\u0003%\t!a5\t\u0013\u0005]\u0007!!A\u0005B\u0005e\u0007\"CAo\u0001\u0005\u0005I\u0011IAp\u0011%\t\t\u000fAA\u0001\n\u0003\n\u0019\u000fC\u0005\u0002f\u0002\t\t\u0011\"\u0011\u0002h\u001eI\u00111^\u0014\u0002\u0002#\u0005\u0011Q\u001e\u0004\tM\u001d\n\t\u0011#\u0001\u0002p\"1\u0011\n\tC\u0001\u0005\u000fA\u0011\"!9!\u0003\u0003%)%a9\t\u0013\t%\u0001%!A\u0005\u0002\n-\u0001\"\u0003B\bA\u0005\u0005I\u0011\u0011B\t\u0011%\u0011I\u0002IA\u0001\n\u0013\u0011YBA\fUQJ,\u0017\r\u001a)p_2\u001cuN\u001c4jO\n+\u0018\u000e\u001c3fe*\u0011\u0001&K\u0001\tI&\u001c\b/\u0019;dQ*\t!&\u0001\u0003bW.\f7\u0001A\n\u0005\u00015\u001ad\u0007\u0005\u0002/c5\tqFC\u00011\u0003\u0015\u00198-\u00197b\u0013\t\u0011tF\u0001\u0004B]f\u0014VM\u001a\t\u0003]QJ!!N\u0018\u0003\u000fA\u0013x\u000eZ;diB\u0011qg\u0010\b\u0003qur!!\u000f\u001f\u000e\u0003iR!aO\u0016\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0001\u0014B\u0001 0\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001Q!\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005yz\u0013AB2p]\u001aLw-F\u0001E!\t)e)D\u0001(\u0013\t9uE\u0001\tUQJ,\u0017\r\u001a)p_2\u001cuN\u001c4jO\u000691m\u001c8gS\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002L\u0019B\u0011Q\t\u0001\u0005\u0006\u0005\u000e\u0001\r\u0001R\u0001)o&$\bNT3x)\"\u0014X-\u00193Q_>dw+\u001b;i\u0007V\u001cHo\\7CY>\u001c7.\u001b8h#V,W/\u001a\u000b\u0003\u0017>CQ\u0001\u0015\u0003A\u0002E\u000bqB\\3x#V,W/\u001a$bGR|'/\u001f\t\u0003%Vs!!R*\n\u0005Q;\u0013\u0001\u0005+ie\u0016\fG\rU8pY\u000e{gNZ5h\u0013\t1vK\u0001\u0007Rk\u0016,XMR1di>\u0014\u0018P\u0003\u0002UOQ\u00111*\u0017\u0005\u00065\u0016\u0001\raW\u0001\u0006cV,W/\u001a\t\u00049\u000e,W\"A/\u000b\u0005y{\u0016AC2p]\u000e,(O]3oi*\u0011\u0001-Y\u0001\u0005kRLGNC\u0001c\u0003\u0011Q\u0017M^1\n\u0005\u0011l&!\u0004\"m_\u000e\\\u0017N\\4Rk\u0016,X\r\u0005\u0002gS6\tqM\u0003\u0002iC\u0006!A.\u00198h\u0013\tQwM\u0001\u0005Sk:t\u0017M\u00197f\u0003u:\u0018\u000e\u001e5OK^$\u0006N]3bIB{w\u000e\\,ji\"d\u0015N\\6fI\ncwnY6j]\u001e\fV/Z;f/&$\b.\u00168c_VtG-\u001a3DCB\f7-\u001b;z+\u0005Y\u0015\u0001N<ji\"tUm\u001e+ie\u0016\fG\rU8pY^KG\u000f\u001b'j].,GM\u00117pG.LgnZ)vKV,w+\u001b;i\u0007\u0006\u0004\u0018mY5usR\u00111j\u001c\u0005\u0006a\u001e\u0001\r!]\u0001\tG\u0006\u0004\u0018mY5usB\u0011aF]\u0005\u0003g>\u00121!\u00138u\u0003E:\u0018\u000e\u001e5OK^$\u0006N]3bIB{w\u000e\\,ji\"\u001c\u0016P\\2ie>tw.^:Rk\u0016,XmV5uQ\u001a\u000b\u0017N\u001d8fgN$\"a\u0013<\t\u000b]D\u0001\u0019\u0001=\u0002\t\u0019\f\u0017N\u001d\t\u0003]eL!A_\u0018\u0003\u000f\t{w\u000e\\3b]\u0006qt/\u001b;i\u001d\u0016<H\u000b\u001b:fC\u0012\u0004vn\u001c7XSRD\u0017I\u001d:bs\ncwnY6j]\u001e\fV/Z;f/&$\bnQ1qC\u000eLG/_!oI\u001a\u000b\u0017N\u001d8fgN$2aS?\u007f\u0011\u0015\u0001\u0018\u00021\u0001r\u0011\u00159\u0018\u00021\u0001y\u0003A\u0019X\r\u001e$jq\u0016$\u0007k\\8m'&TX\rF\u0002L\u0003\u0007Aa!!\u0002\u000b\u0001\u0004\t\u0018\u0001B:ju\u0016\fqb]3u\u0007>\u0014X\rU8pYNK'0\u001a\u000b\u0004\u0017\u0006-\u0001BBA\u0003\u0017\u0001\u0007\u0011/\u0001\btKRl\u0015\r\u001f)p_2\u001c\u0016N_3\u0015\u0007-\u000b\t\u0002\u0003\u0004\u0002\u00061\u0001\r!]\u0001\u001ag\u0016$8i\u001c:f!>|GnU5{K\u001a\u0013x.\u001c$bGR|'\u000fF\u0004L\u0003/\tY\"!\n\t\r\u0005eQ\u00021\u0001r\u0003\ri\u0017N\u001c\u0005\b\u0003;i\u0001\u0019AA\u0010\u0003)iW\u000f\u001c;ja2LWM\u001d\t\u0004]\u0005\u0005\u0012bAA\u0012_\t1Ai\\;cY\u0016Da!a\n\u000e\u0001\u0004\t\u0018aA7bq\u0006A2/\u001a;NCb\u0004vn\u001c7TSj,gI]8n\r\u0006\u001cGo\u001c:\u0015\u000f-\u000bi#a\f\u00022!1\u0011\u0011\u0004\bA\u0002EDq!!\b\u000f\u0001\u0004\ty\u0002\u0003\u0004\u0002(9\u0001\r!]\u0001\u0019g\u0016$8*Z3q\u00032Lg/\u001a+j[\u0016Le.T5mY&\u001cHcA&\u00028!9\u0011\u0011H\bA\u0002\u0005m\u0012\u0001\u0002;j[\u0016\u00042ALA\u001f\u0013\r\tyd\f\u0002\u0005\u0019>tw-\u0001\ttKR\\U-\u001a9BY&4X\rV5nKR\u00191*!\u0012\t\u000f\u0005e\u0002\u00031\u0001\u0002HA!\u0011\u0011JA)\u001b\t\tYE\u0003\u0003\u0002N\u0005=\u0013\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005y{\u0013\u0002BA*\u0003\u0017\u0012\u0001\u0002R;sCRLwN\\\u0001\u001ag\u0016$\u0018\t\u001c7po\u000e{'/\u001a+ie\u0016\fG\rV5nK>,H\u000fF\u0002L\u00033Ba!a\u0017\u0012\u0001\u0004A\u0018!B1mY><\u0018aD:fiF+X-^3GC\u000e$xN]=\u0015\u0007-\u000b\t\u0007C\u0003Q%\u0001\u0007\u0011+A\u0005d_:4\u0017nZ;sKR\u00191*a\u001a\t\u000f\u0005%4\u00031\u0001\u0002l\u0005\u0011am\u001d\t\u0006]\u00055\u0014\u0011O\u0005\u0004\u0003_z#A\u0003\u001fsKB,\u0017\r^3e}A)a&a\u001d\u0002x%\u0019\u0011QO\u0018\u0003\r=\u0003H/[8o!\u0019\tI(!!L\u0017:!\u00111PA?!\tIt&C\u0002\u0002\u0000=\na\u0001\u0015:fI\u00164\u0017\u0002BAB\u0003\u000b\u0013\u0001BR;oGRLwN\u001c\u0006\u0004\u0003\u007fz\u0013\u0001B2paf$2aSAF\u0011\u001d\u0011E\u0003%AA\u0002\u0011\u000babY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002\u0012*\u001aA)a%,\u0005\u0005U\u0005\u0003BAL\u0003Ck!!!'\u000b\t\u0005m\u0015QT\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a(0\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003G\u000bIJA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAAU!\r1\u00171V\u0005\u0004\u0003[;'AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001r\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!a.\u0002>B\u0019a&!/\n\u0007\u0005mvFA\u0002B]fD\u0001\"a0\u0019\u0003\u0003\u0005\r!]\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005\u0015\u0007CBAd\u0003\u001b\f9,\u0004\u0002\u0002J*\u0019\u00111Z\u0018\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002P\u0006%'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$2\u0001_Ak\u0011%\tyLGA\u0001\u0002\u0004\t9,\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BAU\u00037D\u0001\"a0\u001c\u0003\u0003\u0005\r!]\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0011/\u0001\u0005u_N#(/\u001b8h)\t\tI+\u0001\u0004fcV\fGn\u001d\u000b\u0004q\u0006%\b\"CA`=\u0005\u0005\t\u0019AA\\\u0003]!\u0006N]3bIB{w\u000e\\\"p]\u001aLwMQ;jY\u0012,'\u000f\u0005\u0002FAM)\u0001%!=\u0002~B1\u00111_A}\t.k!!!>\u000b\u0007\u0005]x&A\u0004sk:$\u0018.\\3\n\t\u0005m\u0018Q\u001f\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\u0000\u0005\u000bi!A!\u0001\u000b\u0007\t\r\u0011-\u0001\u0002j_&\u0019\u0001I!\u0001\u0015\u0005\u00055\u0018!B1qa2LHcA&\u0003\u000e!)!i\ta\u0001\t\u00069QO\\1qa2LH\u0003\u0002B\n\u0005+\u0001BALA:\t\"A!q\u0003\u0013\u0002\u0002\u0003\u00071*A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"A!\b\u0011\u0007\u0019\u0014y\"C\u0002\u0003\"\u001d\u0014aa\u00142kK\u000e$\b")
public final class ThreadPoolConfigBuilder
implements Product,
Serializable {
    private final ThreadPoolConfig config;

    public static Option<ThreadPoolConfig> unapply(ThreadPoolConfigBuilder x$0) {
        return ThreadPoolConfigBuilder$.MODULE$.unapply(x$0);
    }

    public static ThreadPoolConfigBuilder apply(ThreadPoolConfig config) {
        return ThreadPoolConfigBuilder$.MODULE$.apply(config);
    }

    public static <A> Function1<ThreadPoolConfig, A> andThen(Function1<ThreadPoolConfigBuilder, A> g2) {
        return ThreadPoolConfigBuilder$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, ThreadPoolConfigBuilder> compose(Function1<A, ThreadPoolConfig> g2) {
        return ThreadPoolConfigBuilder$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ThreadPoolConfig config() {
        return this.config;
    }

    public ThreadPoolConfigBuilder withNewThreadPoolWithCustomBlockingQueue(Function0<BlockingQueue<Runnable>> newQueueFactory) {
        Function0<BlockingQueue<Runnable>> x$1 = newQueueFactory;
        boolean x$2 = this.config().copy$default$1();
        int x$3 = this.config().copy$default$2();
        int x$4 = this.config().copy$default$3();
        Duration x$5 = this.config().copy$default$4();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$2, x$3, x$4, x$5, x$1, x$6));
    }

    public ThreadPoolConfigBuilder withNewThreadPoolWithCustomBlockingQueue(BlockingQueue<Runnable> queue) {
        return this.withNewThreadPoolWithCustomBlockingQueue(ThreadPoolConfig$.MODULE$.reusableQueue(queue));
    }

    public ThreadPoolConfigBuilder withNewThreadPoolWithLinkedBlockingQueueWithUnboundedCapacity() {
        Function0<BlockingQueue<Runnable>> x$1 = ThreadPoolConfig$.MODULE$.linkedBlockingQueue();
        boolean x$2 = this.config().copy$default$1();
        int x$3 = this.config().copy$default$2();
        int x$4 = this.config().copy$default$3();
        Duration x$5 = this.config().copy$default$4();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$2, x$3, x$4, x$5, x$1, x$6));
    }

    public ThreadPoolConfigBuilder withNewThreadPoolWithLinkedBlockingQueueWithCapacity(int capacity) {
        Function0<BlockingQueue<Runnable>> x$1 = ThreadPoolConfig$.MODULE$.linkedBlockingQueue(capacity);
        boolean x$2 = this.config().copy$default$1();
        int x$3 = this.config().copy$default$2();
        int x$4 = this.config().copy$default$3();
        Duration x$5 = this.config().copy$default$4();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$2, x$3, x$4, x$5, x$1, x$6));
    }

    public ThreadPoolConfigBuilder withNewThreadPoolWithSynchronousQueueWithFairness(boolean fair) {
        Function0<BlockingQueue<Runnable>> x$1 = ThreadPoolConfig$.MODULE$.synchronousQueue(fair);
        boolean x$2 = this.config().copy$default$1();
        int x$3 = this.config().copy$default$2();
        int x$4 = this.config().copy$default$3();
        Duration x$5 = this.config().copy$default$4();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$2, x$3, x$4, x$5, x$1, x$6));
    }

    public ThreadPoolConfigBuilder withNewThreadPoolWithArrayBlockingQueueWithCapacityAndFairness(int capacity, boolean fair) {
        Function0<BlockingQueue<Runnable>> x$1 = ThreadPoolConfig$.MODULE$.arrayBlockingQueue(capacity, fair);
        boolean x$2 = this.config().copy$default$1();
        int x$3 = this.config().copy$default$2();
        int x$4 = this.config().copy$default$3();
        Duration x$5 = this.config().copy$default$4();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$2, x$3, x$4, x$5, x$1, x$6));
    }

    public ThreadPoolConfigBuilder setFixedPoolSize(int size) {
        int x$1 = size;
        int x$2 = size;
        boolean x$3 = this.config().copy$default$1();
        Duration x$4 = this.config().copy$default$4();
        Function0<BlockingQueue<Runnable>> x$5 = this.config().copy$default$5();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$3, x$1, x$2, x$4, x$5, x$6));
    }

    public ThreadPoolConfigBuilder setCorePoolSize(int size) {
        int x$1 = size;
        int x$2 = package$.MODULE$.max(size, this.config().maxPoolSize());
        boolean x$3 = this.config().copy$default$1();
        Duration x$4 = this.config().copy$default$4();
        Function0<BlockingQueue<Runnable>> x$5 = this.config().copy$default$5();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$3, x$1, x$2, x$4, x$5, x$6));
    }

    public ThreadPoolConfigBuilder setMaxPoolSize(int size) {
        int x$1 = package$.MODULE$.max(size, this.config().corePoolSize());
        boolean x$2 = this.config().copy$default$1();
        int x$3 = this.config().copy$default$2();
        Duration x$4 = this.config().copy$default$4();
        Function0<BlockingQueue<Runnable>> x$5 = this.config().copy$default$5();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$2, x$3, x$1, x$4, x$5, x$6));
    }

    public ThreadPoolConfigBuilder setCorePoolSizeFromFactor(int min2, double multiplier, int max) {
        return this.setCorePoolSize(ThreadPoolConfig$.MODULE$.scaledPoolSize(min2, multiplier, max));
    }

    public ThreadPoolConfigBuilder setMaxPoolSizeFromFactor(int min2, double multiplier, int max) {
        return this.setMaxPoolSize(ThreadPoolConfig$.MODULE$.scaledPoolSize(min2, multiplier, max));
    }

    public ThreadPoolConfigBuilder setKeepAliveTimeInMillis(long time) {
        return this.setKeepAliveTime(Duration$.MODULE$.apply(time, TimeUnit.MILLISECONDS));
    }

    public ThreadPoolConfigBuilder setKeepAliveTime(Duration time) {
        Duration x$1 = time;
        boolean x$2 = this.config().copy$default$1();
        int x$3 = this.config().copy$default$2();
        int x$4 = this.config().copy$default$3();
        Function0<BlockingQueue<Runnable>> x$5 = this.config().copy$default$5();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$2, x$3, x$4, x$1, x$5, x$6));
    }

    public ThreadPoolConfigBuilder setAllowCoreThreadTimeout(boolean allow) {
        return this.copy(this.config().copy(allow, this.config().copy$default$2(), this.config().copy$default$3(), this.config().copy$default$4(), this.config().copy$default$5(), this.config().copy$default$6()));
    }

    public ThreadPoolConfigBuilder setQueueFactory(Function0<BlockingQueue<Runnable>> newQueueFactory) {
        Function0<BlockingQueue<Runnable>> x$1 = newQueueFactory;
        boolean x$2 = this.config().copy$default$1();
        int x$3 = this.config().copy$default$2();
        int x$4 = this.config().copy$default$3();
        Duration x$5 = this.config().copy$default$4();
        RejectedExecutionHandler x$6 = this.config().copy$default$6();
        return this.copy(this.config().copy(x$2, x$3, x$4, x$5, x$1, x$6));
    }

    public ThreadPoolConfigBuilder configure(Seq<Option<Function1<ThreadPoolConfigBuilder, ThreadPoolConfigBuilder>>> fs) {
        return fs.foldLeft(this, (Function2<ThreadPoolConfigBuilder, Option, ThreadPoolConfigBuilder> & Serializable)(c, f) -> (ThreadPoolConfigBuilder)f.map((Function1<Function1, ThreadPoolConfigBuilder> & Serializable)x$1 -> (ThreadPoolConfigBuilder)x$1.apply(c)).getOrElse((Function0<ThreadPoolConfigBuilder> & Serializable)() -> c));
    }

    public ThreadPoolConfigBuilder copy(ThreadPoolConfig config) {
        return new ThreadPoolConfigBuilder(config);
    }

    public ThreadPoolConfig copy$default$1() {
        return this.config();
    }

    @Override
    public String productPrefix() {
        return "ThreadPoolConfigBuilder";
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
                return this.config();
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
        return x$1 instanceof ThreadPoolConfigBuilder;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "config";
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
        if (!(object instanceof ThreadPoolConfigBuilder)) return false;
        boolean bl = true;
        if (!bl) return false;
        ThreadPoolConfigBuilder threadPoolConfigBuilder = (ThreadPoolConfigBuilder)x$1;
        ThreadPoolConfig threadPoolConfig = this.config();
        ThreadPoolConfig threadPoolConfig2 = threadPoolConfigBuilder.config();
        if (threadPoolConfig != null) {
            if (!((Object)threadPoolConfig).equals(threadPoolConfig2)) return false;
            return true;
        }
        if (threadPoolConfig2 == null) return true;
        return false;
    }

    public ThreadPoolConfigBuilder(ThreadPoolConfig config) {
        this.config = config;
        Product.$init$(this);
    }
}

