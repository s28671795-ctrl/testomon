/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.UnboundedDequeBasedMailbox;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class UnboundedDequeBasedMailbox$
implements Serializable {
    public static final UnboundedDequeBasedMailbox$ MODULE$ = new UnboundedDequeBasedMailbox$();

    public UnboundedDequeBasedMailbox apply() {
        return new UnboundedDequeBasedMailbox();
    }

    public boolean unapply(UnboundedDequeBasedMailbox x$0) {
        return x$0 != null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UnboundedDequeBasedMailbox$.class);
    }

    private UnboundedDequeBasedMailbox$() {
    }
}

