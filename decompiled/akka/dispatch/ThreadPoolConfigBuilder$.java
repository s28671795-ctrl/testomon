/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.ThreadPoolConfig;
import akka.dispatch.ThreadPoolConfigBuilder;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ThreadPoolConfigBuilder$
extends AbstractFunction1<ThreadPoolConfig, ThreadPoolConfigBuilder>
implements Serializable {
    public static final ThreadPoolConfigBuilder$ MODULE$ = new ThreadPoolConfigBuilder$();

    @Override
    public final String toString() {
        return "ThreadPoolConfigBuilder";
    }

    @Override
    public ThreadPoolConfigBuilder apply(ThreadPoolConfig config) {
        return new ThreadPoolConfigBuilder(config);
    }

    public Option<ThreadPoolConfig> unapply(ThreadPoolConfigBuilder x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ThreadPoolConfig>(x$0.config());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ThreadPoolConfigBuilder$.class);
    }

    private ThreadPoolConfigBuilder$() {
    }
}

