/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxDelaySubscription;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;

final class MonoDelaySubscription<T, U>
extends InternalMonoOperator<T, T>
implements Consumer<FluxDelaySubscription.DelaySubscriptionOtherSubscriber<T, U>> {
    final Publisher<U> other;

    MonoDelaySubscription(Mono<? extends T> source2, Publisher<U> other) {
        super(source2);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        this.other.subscribe(new FluxDelaySubscription.DelaySubscriptionOtherSubscriber(actual, this));
        return null;
    }

    @Override
    public void accept(FluxDelaySubscription.DelaySubscriptionOtherSubscriber<T, U> s2) {
        this.source.subscribe(new FluxDelaySubscription.DelaySubscriptionMainSubscriber(s2.actual, s2));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

