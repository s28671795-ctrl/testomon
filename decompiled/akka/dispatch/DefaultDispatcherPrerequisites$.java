/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorSystem;
import akka.actor.DynamicAccess;
import akka.actor.Scheduler;
import akka.dispatch.DefaultDispatcherPrerequisites;
import akka.dispatch.Mailboxes;
import akka.event.EventStream;
import java.io.Serializable;
import java.util.concurrent.ThreadFactory;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple7;
import scala.concurrent.ExecutionContext;
import scala.runtime.AbstractFunction7;
import scala.runtime.ModuleSerializationProxy;

public final class DefaultDispatcherPrerequisites$
extends AbstractFunction7<ThreadFactory, EventStream, Scheduler, DynamicAccess, ActorSystem.Settings, Mailboxes, Option<ExecutionContext>, DefaultDispatcherPrerequisites>
implements Serializable {
    public static final DefaultDispatcherPrerequisites$ MODULE$ = new DefaultDispatcherPrerequisites$();

    @Override
    public final String toString() {
        return "DefaultDispatcherPrerequisites";
    }

    @Override
    public DefaultDispatcherPrerequisites apply(ThreadFactory threadFactory, EventStream eventStream, Scheduler scheduler, DynamicAccess dynamicAccess, ActorSystem.Settings settings, Mailboxes mailboxes, Option<ExecutionContext> defaultExecutionContext) {
        return new DefaultDispatcherPrerequisites(threadFactory, eventStream, scheduler, dynamicAccess, settings, mailboxes, defaultExecutionContext);
    }

    public Option<Tuple7<ThreadFactory, EventStream, Scheduler, DynamicAccess, ActorSystem.Settings, Mailboxes, Option<ExecutionContext>>> unapply(DefaultDispatcherPrerequisites x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple7<ThreadFactory, EventStream, Scheduler, DynamicAccess, ActorSystem.Settings, Mailboxes, Option<ExecutionContext>>>(new Tuple7<ThreadFactory, EventStream, Scheduler, DynamicAccess, ActorSystem.Settings, Mailboxes, Option<ExecutionContext>>(x$0.threadFactory(), x$0.eventStream(), x$0.scheduler(), x$0.dynamicAccess(), x$0.settings(), x$0.mailboxes(), x$0.defaultExecutionContext()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DefaultDispatcherPrerequisites$.class);
    }

    private DefaultDispatcherPrerequisites$() {
    }
}

