/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.MonitorableThreadFactory;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class MonitorableThreadFactory$
implements Serializable {
    public static final MonitorableThreadFactory$ MODULE$ = new MonitorableThreadFactory$();
    private static final Thread.UncaughtExceptionHandler doNothing = new Thread.UncaughtExceptionHandler(){

        public void uncaughtException(Thread thread2, Throwable cause) {
        }
    };

    public Thread.UncaughtExceptionHandler $lessinit$greater$default$4() {
        return this.doNothing();
    }

    public AtomicLong $lessinit$greater$default$5() {
        return new AtomicLong();
    }

    public Thread.UncaughtExceptionHandler doNothing() {
        return doNothing;
    }

    public MonitorableThreadFactory apply(String name, boolean daemonic, Option<ClassLoader> contextClassLoader, Thread.UncaughtExceptionHandler exceptionHandler, AtomicLong counter) {
        return new MonitorableThreadFactory(name, daemonic, contextClassLoader, exceptionHandler, counter);
    }

    public Thread.UncaughtExceptionHandler apply$default$4() {
        return this.doNothing();
    }

    public AtomicLong apply$default$5() {
        return new AtomicLong();
    }

    public Option<Tuple5<String, Object, Option<ClassLoader>, Thread.UncaughtExceptionHandler, AtomicLong>> unapply(MonitorableThreadFactory x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple5<String, Object, Option<ClassLoader>, Thread.UncaughtExceptionHandler, AtomicLong>>(new Tuple5<String, Boolean, Option<ClassLoader>, Thread.UncaughtExceptionHandler, AtomicLong>(x$0.name(), BoxesRunTime.boxToBoolean(x$0.daemonic()), x$0.contextClassLoader(), x$0.exceptionHandler(), x$0.counter()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MonitorableThreadFactory$.class);
    }

    private MonitorableThreadFactory$() {
    }
}

