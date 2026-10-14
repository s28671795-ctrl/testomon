/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SchedulerException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class SchedulerException$
extends AbstractFunction1<String, SchedulerException>
implements Serializable {
    public static final SchedulerException$ MODULE$ = new SchedulerException$();

    @Override
    public final String toString() {
        return "SchedulerException";
    }

    @Override
    public SchedulerException apply(String msg) {
        return new SchedulerException(msg);
    }

    public Option<String> unapply(SchedulerException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SchedulerException$.class);
    }

    private SchedulerException$() {
    }
}

