/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Balance;

public final class Balance$ {
    public static final Balance$ MODULE$ = new Balance$();

    public <T> Balance<T> apply(int outputPorts, boolean waitForAllDownstreams) {
        return new Balance(outputPorts, waitForAllDownstreams, false);
    }

    public <T> boolean apply$default$2() {
        return false;
    }

    private Balance$() {
    }
}

