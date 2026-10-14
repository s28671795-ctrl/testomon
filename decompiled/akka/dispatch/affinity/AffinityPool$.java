/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import akka.annotation.ApiMayChange;
import akka.annotation.InternalApi;

@InternalApi
@ApiMayChange
public final class AffinityPool$ {
    public static final AffinityPool$ MODULE$ = new AffinityPool$();

    public final int Uninitialized() {
        return 0;
    }

    public final int Initializing() {
        return 1;
    }

    public final int Running() {
        return 2;
    }

    public final int ShuttingDown() {
        return 3;
    }

    public final int ShutDown() {
        return 4;
    }

    public final int Terminated() {
        return 5;
    }

    public final int Initial() {
        return 0;
    }

    public final int Spinning() {
        return 1;
    }

    public final int Yielding() {
        return 2;
    }

    public final int Parking() {
        return 3;
    }

    private AffinityPool$() {
    }
}

