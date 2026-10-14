/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.Mailbox;
import akka.util.Unsafe;

final class AbstractMailbox {
    static final long mailboxStatusOffset;
    static final long systemMessageOffset;

    AbstractMailbox() {
    }

    static {
        try {
            mailboxStatusOffset = Unsafe.instance.objectFieldOffset(Mailbox.class.getDeclaredField("_statusDoNotCallMeDirectly"));
            systemMessageOffset = Unsafe.instance.objectFieldOffset(Mailbox.class.getDeclaredField("_systemQueueDoNotCallMeDirectly"));
        }
        catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }
}

