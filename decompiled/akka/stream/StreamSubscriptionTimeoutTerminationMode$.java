/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$CancelTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$NoopTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$WarnTermination$;

public final class StreamSubscriptionTimeoutTerminationMode$ {
    public static final StreamSubscriptionTimeoutTerminationMode$ MODULE$ = new StreamSubscriptionTimeoutTerminationMode$();

    public StreamSubscriptionTimeoutTerminationMode noop() {
        return StreamSubscriptionTimeoutTerminationMode$NoopTermination$.MODULE$;
    }

    public StreamSubscriptionTimeoutTerminationMode warn() {
        return StreamSubscriptionTimeoutTerminationMode$WarnTermination$.MODULE$;
    }

    public StreamSubscriptionTimeoutTerminationMode cancel() {
        return StreamSubscriptionTimeoutTerminationMode$CancelTermination$.MODULE$;
    }

    private StreamSubscriptionTimeoutTerminationMode$() {
    }
}

