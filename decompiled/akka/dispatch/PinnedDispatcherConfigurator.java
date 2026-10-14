/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.ExecutorServiceConfigurator;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.MessageDispatcherConfigurator;
import akka.dispatch.PinnedDispatcher;
import akka.dispatch.ThreadPoolConfig;
import akka.dispatch.ThreadPoolConfig$;
import akka.dispatch.ThreadPoolExecutorConfigurator;
import akka.event.Logging;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import com.typesafe.config.Config;
import scala.Predef$;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005E2Aa\u0002\u0005\u0001\u001b!A!\u0003\u0001B\u0001B\u0003%1\u0003C\u0005\u001d\u0001\t\u0005\t\u0015!\u0003\u001eA!)\u0011\u0005\u0001C\u0001E!9a\u0005\u0001b\u0001\n\u00139\u0003BB\u0016\u0001A\u0003%\u0001\u0006C\u0003-\u0001\u0011\u0005SF\u0001\u000fQS:tW\r\u001a#jgB\fGo\u00195fe\u000e{gNZ5hkJ\fGo\u001c:\u000b\u0005%Q\u0011\u0001\u00033jgB\fGo\u00195\u000b\u0003-\tA!Y6lC\u000e\u00011C\u0001\u0001\u000f!\ty\u0001#D\u0001\t\u0013\t\t\u0002BA\u000fNKN\u001c\u0018mZ3ESN\u0004\u0018\r^2iKJ\u001cuN\u001c4jOV\u0014\u0018\r^8s\u0003\u0019\u0019wN\u001c4jOB\u0011ACG\u0007\u0002+)\u0011!C\u0006\u0006\u0003/a\t\u0001\u0002^=qKN\fg-\u001a\u0006\u00023\u0005\u00191m\\7\n\u0005m)\"AB\"p]\u001aLw-A\u0007qe\u0016\u0014X-];jg&$Xm\u001d\t\u0003\u001fyI!a\b\u0005\u0003/\u0011K7\u000f]1uG\",'\u000f\u0015:fe\u0016\fX/[:ji\u0016\u001c\u0018B\u0001\u000f\u0011\u0003\u0019a\u0014N\\5u}Q\u00191\u0005J\u0013\u0011\u0005=\u0001\u0001\"\u0002\n\u0004\u0001\u0004\u0019\u0002\"\u0002\u000f\u0004\u0001\u0004i\u0012\u0001\u0005;ie\u0016\fG\rU8pY\u000e{gNZ5h+\u0005A\u0003CA\b*\u0013\tQ\u0003B\u0001\tUQJ,\u0017\r\u001a)p_2\u001cuN\u001c4jO\u0006\tB\u000f\u001b:fC\u0012\u0004vn\u001c7D_:4\u0017n\u001a\u0011\u0002\u0015\u0011L7\u000f]1uG\",'\u000fF\u0001/!\tyq&\u0003\u00021\u0011\t\tR*Z:tC\u001e,G)[:qCR\u001c\u0007.\u001a:")
public class PinnedDispatcherConfigurator
extends MessageDispatcherConfigurator {
    private final Config config;
    private final ThreadPoolConfig threadPoolConfig;

    private ThreadPoolConfig threadPoolConfig() {
        return this.threadPoolConfig;
    }

    @Override
    public MessageDispatcher dispatcher() {
        return new PinnedDispatcher(this, null, this.config.getString("id"), Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(this.config), "shutdown-timeout"), this.threadPoolConfig());
    }

    public PinnedDispatcherConfigurator(Config config, DispatcherPrerequisites prerequisites) {
        ThreadPoolConfig threadPoolConfig;
        this.config = config;
        super(config, prerequisites);
        ExecutorServiceConfigurator executorServiceConfigurator = this.configureExecutor();
        if (executorServiceConfigurator instanceof ThreadPoolExecutorConfigurator) {
            ThreadPoolExecutorConfigurator threadPoolExecutorConfigurator = (ThreadPoolExecutorConfigurator)executorServiceConfigurator;
            threadPoolConfig = threadPoolExecutorConfigurator.threadPoolConfig();
        } else {
            super.prerequisites().eventStream().publish(new Logging.Warning("PinnedDispatcherConfigurator", this.getClass(), StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("PinnedDispatcher [%s] not configured to use ThreadPoolExecutor, falling back to default config."), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{config.getString("id")}))));
            threadPoolConfig = new ThreadPoolConfig(ThreadPoolConfig$.MODULE$.apply$default$1(), ThreadPoolConfig$.MODULE$.apply$default$2(), ThreadPoolConfig$.MODULE$.apply$default$3(), ThreadPoolConfig$.MODULE$.apply$default$4(), ThreadPoolConfig$.MODULE$.apply$default$5(), ThreadPoolConfig$.MODULE$.apply$default$6());
        }
        this.threadPoolConfig = threadPoolConfig;
    }
}

