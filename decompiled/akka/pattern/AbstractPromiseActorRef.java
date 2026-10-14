/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.pattern.PromiseActorRef;
import akka.util.Unsafe;

final class AbstractPromiseActorRef {
    static final long stateOffset;
    static final long watchedByOffset;

    AbstractPromiseActorRef() {
    }

    static {
        try {
            stateOffset = Unsafe.instance.objectFieldOffset(PromiseActorRef.class.getDeclaredField("_stateDoNotCallMeDirectly"));
            watchedByOffset = Unsafe.instance.objectFieldOffset(PromiseActorRef.class.getDeclaredField("_watchedByDoNotCallMeDirectly"));
        }
        catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }
}

