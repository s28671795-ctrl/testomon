/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.pattern.CircuitBreaker;
import akka.util.Unsafe;

class AbstractCircuitBreaker {
    protected static final long stateOffset;
    protected static final long resetTimeoutOffset;

    AbstractCircuitBreaker() {
    }

    static {
        try {
            stateOffset = Unsafe.instance.objectFieldOffset(CircuitBreaker.class.getDeclaredField("_currentStateDoNotCallMeDirectly"));
            resetTimeoutOffset = Unsafe.instance.objectFieldOffset(CircuitBreaker.class.getDeclaredField("_currentResetTimeoutDoNotCallMeDirectly"));
        }
        catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }
}

