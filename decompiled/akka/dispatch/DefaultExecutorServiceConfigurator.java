/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.ExecutorServiceConfigurator;
import akka.dispatch.ExecutorServiceFactory;
import akka.dispatch.ExecutorServiceFactoryProvider;
import akka.event.Logging;
import com.typesafe.config.Config;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.concurrent.ExecutionContext;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000593A\u0001C\u0005\u0001\u001d!A1\u0003\u0001B\u0001B\u0003%A\u0003\u0003\u0005\u001e\u0001\t\u0005\t\u0015!\u0003\u001f\u0011!\t\u0003A!A!\u0002\u0013y\u0001\"\u0002\u0012\u0001\t\u0003\u0019\u0003b\u0002\u0015\u0001\u0005\u0004%\t!\u000b\u0005\u0007[\u0001\u0001\u000b\u0011\u0002\u0016\t\u000b9\u0002A\u0011A\u0018\u0003E\u0011+g-Y;mi\u0016CXmY;u_J\u001cVM\u001d<jG\u0016\u001cuN\u001c4jOV\u0014\u0018\r^8s\u0015\tQ1\"\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005a\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001\u001fA\u0011\u0001#E\u0007\u0002\u0013%\u0011!#\u0003\u0002\u001c\u000bb,7-\u001e;peN+'O^5dK\u000e{gNZ5hkJ\fGo\u001c:\u0002\r\r|gNZ5h!\t)2$D\u0001\u0017\u0015\t\u0019rC\u0003\u0002\u00193\u0005AA/\u001f9fg\u00064WMC\u0001\u001b\u0003\r\u0019w.\\\u0005\u00039Y\u0011aaQ8oM&<\u0017!\u00049sKJ,\u0017/^5tSR,7\u000f\u0005\u0002\u0011?%\u0011\u0001%\u0003\u0002\u0018\t&\u001c\b/\u0019;dQ\u0016\u0014\bK]3sKF,\u0018n]5uKN\f\u0001BZ1mY\n\f7m[\u0001\u0007y%t\u0017\u000e\u001e \u0015\t\u0011*ce\n\t\u0003!\u0001AQa\u0005\u0003A\u0002QAQ!\b\u0003A\u0002yAQ!\t\u0003A\u0002=\t\u0001\u0002\u001d:pm&$WM]\u000b\u0002UA\u0011\u0001cK\u0005\u0003Y%\u0011a$\u0012=fGV$xN]*feZL7-\u001a$bGR|'/\u001f)s_ZLG-\u001a:\u0002\u0013A\u0014xN^5eKJ\u0004\u0013\u0001H2sK\u0006$X-\u0012=fGV$xN]*feZL7-\u001a$bGR|'/\u001f\u000b\u0004aM\u0012\u0005C\u0001\t2\u0013\t\u0011\u0014B\u0001\fFq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f\r\u0006\u001cGo\u001c:z\u0011\u0015!t\u00011\u00016\u0003\tIG\r\u0005\u00027\u007f9\u0011q'\u0010\t\u0003qmj\u0011!\u000f\u0006\u0003u5\ta\u0001\u0010:p_Rt$\"\u0001\u001f\u0002\u000bM\u001c\u0017\r\\1\n\u0005yZ\u0014A\u0002)sK\u0012,g-\u0003\u0002A\u0003\n11\u000b\u001e:j]\u001eT!AP\u001e\t\u000b\r;\u0001\u0019\u0001#\u0002\u001bQD'/Z1e\r\u0006\u001cGo\u001c:z!\t)E*D\u0001G\u0015\t9\u0005*\u0001\u0006d_:\u001cWO\u001d:f]RT!!\u0013&\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u0017\u0006!!.\u0019<b\u0013\tieIA\u0007UQJ,\u0017\r\u001a$bGR|'/\u001f")
public class DefaultExecutorServiceConfigurator
extends ExecutorServiceConfigurator {
    private final ExecutorServiceFactoryProvider provider;

    public ExecutorServiceFactoryProvider provider() {
        return this.provider;
    }

    @Override
    public ExecutorServiceFactory createExecutorServiceFactory(String id, ThreadFactory threadFactory) {
        return this.provider().createExecutorServiceFactory(id, threadFactory);
    }

    public DefaultExecutorServiceConfigurator(Config config, DispatcherPrerequisites prerequisites, ExecutorServiceConfigurator fallback) {
        super(config, prerequisites);
        Object object;
        Option<ExecutionContext> option = prerequisites.defaultExecutionContext();
        if (option instanceof Some) {
            Some some = (Some)option;
            ExecutionContext ec = (ExecutionContext)some.value();
            prerequisites.eventStream().publish(new Logging.Debug("DefaultExecutorServiceConfigurator", this.getClass(), "Using passed in ExecutionContext as default executor for this ActorSystem. If you want to use a different executor, please specify one in akka.actor.default-dispatcher.default-executor."));
            object = new ExecutorServiceFactory(null, ec){
                private final ExecutionContext ec$1;

                public ExecutorServiceFactory createExecutorServiceFactory(String id, ThreadFactory threadFactory) {
                    return this;
                }

                public ExecutorService createExecutorService() {
                    return this;
                }

                public void shutdown() {
                }

                public boolean isTerminated() {
                    return false;
                }

                public boolean awaitTermination(long timeout2, TimeUnit unit) {
                    return false;
                }

                public List<Runnable> shutdownNow() {
                    return Collections.emptyList();
                }

                public void execute(Runnable command) {
                    this.ec$1.execute(command);
                }

                public boolean isShutdown() {
                    return false;
                }
                {
                    this.ec$1 = ec$1;
                }
            };
        } else if (None$.MODULE$.equals(option)) {
            object = fallback;
        } else {
            throw new MatchError(option);
        }
        this.provider = object;
    }
}

