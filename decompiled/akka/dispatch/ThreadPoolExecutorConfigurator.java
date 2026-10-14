/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.ExecutorServiceConfigurator;
import akka.dispatch.ExecutorServiceFactory;
import akka.dispatch.ThreadPoolConfig;
import akka.dispatch.ThreadPoolConfig$;
import akka.dispatch.ThreadPoolConfigBuilder;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005e3A\u0001C\u0005\u0001\u001d!A1\u0003\u0001B\u0001B\u0003%A\u0003\u0003\u0005\u001e\u0001\t\u0005\t\u0015!\u0003\u001f\u0011\u0015\t\u0003\u0001\"\u0001#\u0011\u001d1\u0003A1A\u0005\u0002\u001dBaa\u000b\u0001!\u0002\u0013A\u0003\"\u0002\u0017\u0001\t#i\u0003\"\u0002\u001e\u0001\t\u0003Y$A\b+ie\u0016\fG\rU8pY\u0016CXmY;u_J\u001cuN\u001c4jOV\u0014\u0018\r^8s\u0015\tQ1\"\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005a\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001\u001fA\u0011\u0001#E\u0007\u0002\u0013%\u0011!#\u0003\u0002\u001c\u000bb,7-\u001e;peN+'O^5dK\u000e{gNZ5hkJ\fGo\u001c:\u0002\r\r|gNZ5h!\t)2$D\u0001\u0017\u0015\t\u0019rC\u0003\u0002\u00193\u0005AA/\u001f9fg\u00064WMC\u0001\u001b\u0003\r\u0019w.\\\u0005\u00039Y\u0011aaQ8oM&<\u0017!\u00049sKJ,\u0017/^5tSR,7\u000f\u0005\u0002\u0011?%\u0011\u0001%\u0003\u0002\u0018\t&\u001c\b/\u0019;dQ\u0016\u0014\bK]3sKF,\u0018n]5uKN\fa\u0001P5oSRtDcA\u0012%KA\u0011\u0001\u0003\u0001\u0005\u0006'\r\u0001\r\u0001\u0006\u0005\u0006;\r\u0001\rAH\u0001\u0011i\"\u0014X-\u00193Q_>d7i\u001c8gS\u001e,\u0012\u0001\u000b\t\u0003!%J!AK\u0005\u0003!QC'/Z1e!>|GnQ8oM&<\u0017!\u0005;ie\u0016\fG\rU8pY\u000e{gNZ5hA\u0005i2M]3bi\u0016$\u0006N]3bIB{w\u000e\\\"p]\u001aLwMQ;jY\u0012,'\u000fF\u0002/cI\u0002\"\u0001E\u0018\n\u0005AJ!a\u0006+ie\u0016\fG\rU8pY\u000e{gNZ5h\u0005VLG\u000eZ3s\u0011\u0015\u0019b\u00011\u0001\u0015\u0011\u0015ib\u00011\u0001\u001fQ\t\u0011D\u0007\u0005\u00026q5\taG\u0003\u00028\u0017\u0005!Q\u000f^5m\u0013\tIdG\u0001\u0004v]V\u001cX\rZ\u0001\u001dGJ,\u0017\r^3Fq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f\r\u0006\u001cGo\u001c:z)\ratH\u0014\t\u0003!uJ!AP\u0005\u0003-\u0015CXmY;u_J\u001cVM\u001d<jG\u00164\u0015m\u0019;pefDQ\u0001Q\u0004A\u0002\u0005\u000b!!\u001b3\u0011\u0005\t[eBA\"J!\t!u)D\u0001F\u0015\t1U\"\u0001\u0004=e>|GO\u0010\u0006\u0002\u0011\u0006)1oY1mC&\u0011!jR\u0001\u0007!J,G-\u001a4\n\u00051k%AB*ue&twM\u0003\u0002K\u000f\")qj\u0002a\u0001!\u0006iA\u000f\u001b:fC\u00124\u0015m\u0019;pef\u0004\"!U,\u000e\u0003IS!a\u0015+\u0002\u0015\r|gnY;se\u0016tGO\u0003\u00028+*\ta+\u0001\u0003kCZ\f\u0017B\u0001-S\u00055!\u0006N]3bI\u001a\u000b7\r^8ss\u0002")
public class ThreadPoolExecutorConfigurator
extends ExecutorServiceConfigurator {
    private final ThreadPoolConfig threadPoolConfig;

    public ThreadPoolConfig threadPoolConfig() {
        return this.threadPoolConfig;
    }

    public ThreadPoolConfigBuilder createThreadPoolConfigBuilder(Config config, DispatcherPrerequisites prerequisites) {
        ThreadPoolConfigBuilder builder = new ThreadPoolConfigBuilder(new ThreadPoolConfig(ThreadPoolConfig$.MODULE$.apply$default$1(), ThreadPoolConfig$.MODULE$.apply$default$2(), ThreadPoolConfig$.MODULE$.apply$default$3(), ThreadPoolConfig$.MODULE$.apply$default$4(), ThreadPoolConfig$.MODULE$.apply$default$5(), ThreadPoolConfig$.MODULE$.apply$default$6())).setKeepAliveTime(Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "keep-alive-time")).setAllowCoreThreadTimeout(config.getBoolean("allow-core-timeout")).configure(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Option[]{new Some<Integer>(BoxesRunTime.boxToInteger(config.getInt("task-queue-size"))).flatMap((Function1<Object, Option> & Serializable)x0$1 -> ThreadPoolExecutorConfigurator.$anonfun$createThreadPoolConfigBuilder$1(config, BoxesRunTime.unboxToInt(x0$1)))}));
        String string = config.getString("fixed-pool-size");
        String string2 = "off";
        if (!(string != null ? !string.equals(string2) : string2 != null)) {
            return builder.setCorePoolSizeFromFactor(config.getInt("core-pool-size-min"), config.getDouble("core-pool-size-factor"), config.getInt("core-pool-size-max")).setMaxPoolSizeFromFactor(config.getInt("max-pool-size-min"), config.getDouble("max-pool-size-factor"), config.getInt("max-pool-size-max"));
        }
        return builder.setFixedPoolSize(config.getInt("fixed-pool-size"));
    }

    @Override
    public ExecutorServiceFactory createExecutorServiceFactory(String id, ThreadFactory threadFactory) {
        return this.threadPoolConfig().createExecutorServiceFactory(id, threadFactory);
    }

    public static final /* synthetic */ Option $anonfun$createThreadPoolConfigBuilder$1(Config config$1, int x0$1) {
        int n = x0$1;
        switch (n) {
            default: 
        }
        if (n > 0) {
            return new Some<String>(config$1.getString("task-queue-type")).map((Function1<String, Function0> & Serializable)x0$2 -> {
                String string;
                block8: {
                    string = x0$2;
                    switch (string == null ? 0 : string.hashCode()) {
                        case -1102666215: {
                            if ("linked".equals(string)) {
                                break;
                            }
                            break block8;
                        }
                        case 0: {
                            if ("".equals(string)) {
                                break;
                            }
                            break block8;
                        }
                        case 93090393: {
                            if ("array".equals(string)) {
                                return ThreadPoolConfig$.MODULE$.arrayBlockingQueue(n, false);
                            }
                            break block8;
                        }
                        default: {
                            break block8;
                        }
                    }
                    return ThreadPoolConfig$.MODULE$.linkedBlockingQueue(n);
                }
                throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("[%s] is not a valid task-queue-type [array|linked]!"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{string})));
            }).map((Function1<Function0, Function1> & Serializable)qf -> (Function1<ThreadPoolConfigBuilder, ThreadPoolConfigBuilder> & Serializable)q -> q.setQueueFactory((Function0<BlockingQueue<Runnable>>)qf));
        }
        return None$.MODULE$;
    }

    public ThreadPoolExecutorConfigurator(Config config, DispatcherPrerequisites prerequisites) {
        super(config, prerequisites);
        this.threadPoolConfig = this.createThreadPoolConfigBuilder(config, prerequisites).config();
    }
}

