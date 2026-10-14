/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern.internal;

import akka.annotation.InternalApi;
import akka.pattern.internal.CircuitBreakerTelemetry;

@InternalApi
public final class CircuitBreakerNoopTelemetry$
implements CircuitBreakerTelemetry {
    public static final CircuitBreakerNoopTelemetry$ MODULE$ = new CircuitBreakerNoopTelemetry$();

    @Override
    public void onOpen() {
    }

    @Override
    public void onClose() {
    }

    @Override
    public void onHalfOpen() {
    }

    @Override
    public void onCallSuccess(long elapsedNanos) {
    }

    @Override
    public void onCallFailure(long elapsedNanos) {
    }

    @Override
    public void onCallTimeoutFailure(long elapsedNanos) {
    }

    @Override
    public void onCallBreakerOpenFailure() {
    }

    @Override
    public void stopped() {
    }

    private CircuitBreakerNoopTelemetry$() {
    }
}

