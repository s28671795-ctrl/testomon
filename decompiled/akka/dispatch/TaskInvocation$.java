/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.TaskInvocation;
import akka.event.EventStream;
import java.io.Serializable;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;

public final class TaskInvocation$
extends AbstractFunction3<EventStream, Runnable, Function0<BoxedUnit>, TaskInvocation>
implements Serializable {
    public static final TaskInvocation$ MODULE$ = new TaskInvocation$();

    @Override
    public final String toString() {
        return "TaskInvocation";
    }

    @Override
    public TaskInvocation apply(EventStream eventStream, Runnable runnable2, Function0<BoxedUnit> cleanup) {
        return new TaskInvocation(eventStream, runnable2, cleanup);
    }

    public Option<Tuple3<EventStream, Runnable, Function0<BoxedUnit>>> unapply(TaskInvocation x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<EventStream, Runnable, Function0<BoxedUnit>>>(new Tuple3<EventStream, Runnable, Function0<BoxedUnit>>(x$0.eventStream(), x$0.runnable(), x$0.cleanup()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TaskInvocation$.class);
    }

    private TaskInvocation$() {
    }
}

