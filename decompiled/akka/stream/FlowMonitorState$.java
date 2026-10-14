/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FlowMonitorState;
import akka.stream.FlowMonitorState$Finished$;
import akka.stream.FlowMonitorState$Initialized$;

public final class FlowMonitorState$ {
    public static final FlowMonitorState$ MODULE$ = new FlowMonitorState$();

    public <U> FlowMonitorState.StreamState<U> initialized() {
        return FlowMonitorState$Initialized$.MODULE$;
    }

    public <U> FlowMonitorState.StreamState<U> received(U msg) {
        return new FlowMonitorState.Received<U>(msg);
    }

    public <U> FlowMonitorState.StreamState<U> failed(Throwable cause) {
        return new FlowMonitorState.Failed(cause);
    }

    public <U> FlowMonitorState.StreamState<U> finished() {
        return FlowMonitorState$Finished$.MODULE$;
    }

    private FlowMonitorState$() {
    }
}

