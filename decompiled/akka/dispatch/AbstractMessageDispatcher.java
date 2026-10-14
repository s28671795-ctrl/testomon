/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.MessageDispatcher;
import akka.util.Unsafe;

abstract class AbstractMessageDispatcher {
    static final long shutdownScheduleOffset;
    static final long inhabitantsOffset;

    AbstractMessageDispatcher() {
    }

    static {
        try {
            shutdownScheduleOffset = Unsafe.instance.objectFieldOffset(MessageDispatcher.class.getDeclaredField("_shutdownScheduleDoNotCallMeDirectly"));
            inhabitantsOffset = Unsafe.instance.objectFieldOffset(MessageDispatcher.class.getDeclaredField("_inhabitantsDoNotCallMeDirectly"));
        }
        catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }
}

