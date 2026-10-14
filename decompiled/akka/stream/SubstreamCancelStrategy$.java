/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.SubstreamCancelStrategies$Drain$;
import akka.stream.SubstreamCancelStrategies$Propagate$;
import akka.stream.SubstreamCancelStrategy;

public final class SubstreamCancelStrategy$ {
    public static final SubstreamCancelStrategy$ MODULE$ = new SubstreamCancelStrategy$();

    public SubstreamCancelStrategy propagate() {
        return SubstreamCancelStrategies$Propagate$.MODULE$;
    }

    public SubstreamCancelStrategy drain() {
        return SubstreamCancelStrategies$Drain$.MODULE$;
    }

    private SubstreamCancelStrategy$() {
    }
}

