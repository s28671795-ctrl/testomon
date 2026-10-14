/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Broadcast;

public final class Broadcast$ {
    public static final Broadcast$ MODULE$ = new Broadcast$();

    public <T> Broadcast<T> apply(int outputPorts, boolean eagerCancel) {
        return new Broadcast(outputPorts, eagerCancel);
    }

    public <T> boolean apply$default$2() {
        return false;
    }

    private Broadcast$() {
    }
}

