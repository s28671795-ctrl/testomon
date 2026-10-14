/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.UnboundedMailbox;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class UnboundedMailbox$
implements Serializable {
    public static final UnboundedMailbox$ MODULE$ = new UnboundedMailbox$();

    public UnboundedMailbox apply() {
        return new UnboundedMailbox();
    }

    public boolean unapply(UnboundedMailbox x$0) {
        return x$0 != null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UnboundedMailbox$.class);
    }

    private UnboundedMailbox$() {
    }
}

