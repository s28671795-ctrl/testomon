/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.annotation.InternalApi;

@InternalApi
public final class SourceRefStageImpl$ {
    public static final SourceRefStageImpl$ MODULE$ = new SourceRefStageImpl$();
    private static final String SubscriptionTimeoutTimerKey = "SubscriptionTimeoutKey";
    private static final String DemandRedeliveryTimerKey = "DemandRedeliveryTimerKey";
    private static final String TerminationDeadlineTimerKey = "TerminationDeadlineTimerKey";
    private static final String CancellationDeadlineTimerKey = "CancellationDeadlineTimerKey";

    public String SubscriptionTimeoutTimerKey() {
        return SubscriptionTimeoutTimerKey;
    }

    public String DemandRedeliveryTimerKey() {
        return DemandRedeliveryTimerKey;
    }

    public String TerminationDeadlineTimerKey() {
        return TerminationDeadlineTimerKey;
    }

    public String CancellationDeadlineTimerKey() {
        return CancellationDeadlineTimerKey;
    }

    private SourceRefStageImpl$() {
    }
}

