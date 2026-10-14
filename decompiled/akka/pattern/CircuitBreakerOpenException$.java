/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class CircuitBreakerOpenException$
implements Serializable {
    public static final CircuitBreakerOpenException$ MODULE$ = new CircuitBreakerOpenException$();

    public String $lessinit$greater$default$2() {
        return "Circuit Breaker is open; calls are failing fast";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(CircuitBreakerOpenException$.class);
    }

    private CircuitBreakerOpenException$() {
    }
}

