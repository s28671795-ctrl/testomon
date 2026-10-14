/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.NonBlockingBoundedMailbox;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class NonBlockingBoundedMailbox$
extends AbstractFunction1<Object, NonBlockingBoundedMailbox>
implements Serializable {
    public static final NonBlockingBoundedMailbox$ MODULE$ = new NonBlockingBoundedMailbox$();

    @Override
    public final String toString() {
        return "NonBlockingBoundedMailbox";
    }

    @Override
    public NonBlockingBoundedMailbox apply(int capacity) {
        return new NonBlockingBoundedMailbox(capacity);
    }

    public Option<Object> unapply(NonBlockingBoundedMailbox x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(BoxesRunTime.boxToInteger(x$0.capacity()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(NonBlockingBoundedMailbox$.class);
    }

    private NonBlockingBoundedMailbox$() {
    }
}

