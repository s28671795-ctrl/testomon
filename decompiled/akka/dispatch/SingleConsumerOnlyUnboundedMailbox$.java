/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.SingleConsumerOnlyUnboundedMailbox;
import java.io.Serializable;
import scala.runtime.AbstractFunction0;
import scala.runtime.ModuleSerializationProxy;

public final class SingleConsumerOnlyUnboundedMailbox$
extends AbstractFunction0<SingleConsumerOnlyUnboundedMailbox>
implements Serializable {
    public static final SingleConsumerOnlyUnboundedMailbox$ MODULE$ = new SingleConsumerOnlyUnboundedMailbox$();

    @Override
    public final String toString() {
        return "SingleConsumerOnlyUnboundedMailbox";
    }

    @Override
    public SingleConsumerOnlyUnboundedMailbox apply() {
        return new SingleConsumerOnlyUnboundedMailbox();
    }

    public boolean unapply(SingleConsumerOnlyUnboundedMailbox x$0) {
        return x$0 != null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SingleConsumerOnlyUnboundedMailbox$.class);
    }

    private SingleConsumerOnlyUnboundedMailbox$() {
    }
}

