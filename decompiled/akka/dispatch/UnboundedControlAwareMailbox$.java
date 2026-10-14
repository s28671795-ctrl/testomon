/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.UnboundedControlAwareMailbox;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class UnboundedControlAwareMailbox$
implements Serializable {
    public static final UnboundedControlAwareMailbox$ MODULE$ = new UnboundedControlAwareMailbox$();

    public UnboundedControlAwareMailbox apply() {
        return new UnboundedControlAwareMailbox();
    }

    public boolean unapply(UnboundedControlAwareMailbox x$0) {
        return x$0 != null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UnboundedControlAwareMailbox$.class);
    }

    private UnboundedControlAwareMailbox$() {
    }
}

