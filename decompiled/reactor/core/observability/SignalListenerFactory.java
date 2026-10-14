/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.observability;

import org.reactivestreams.Publisher;
import reactor.core.observability.SignalListener;
import reactor.util.context.ContextView;

public interface SignalListenerFactory<T, STATE> {
    public STATE initializePublisherState(Publisher<? extends T> var1);

    public SignalListener<T> createListener(Publisher<? extends T> var1, ContextView var2, STATE var3);
}

