/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class Mailbox$
implements Serializable {
    public static final Mailbox$ MODULE$ = new Mailbox$();
    private static final int shouldNotProcessMask = ~2;
    private static final int suspendMask = ~3;

    public final int Open() {
        return 0;
    }

    public final int Closed() {
        return 1;
    }

    public final int Scheduled() {
        return 2;
    }

    public final int shouldScheduleMask() {
        return 3;
    }

    public final int shouldNotProcessMask() {
        return shouldNotProcessMask;
    }

    public final int suspendMask() {
        return suspendMask;
    }

    public final int suspendUnit() {
        return 4;
    }

    public final boolean debug() {
        return false;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Mailbox$.class);
    }

    private Mailbox$() {
    }
}

