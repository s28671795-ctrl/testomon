/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.OptimizableOperator;
import reactor.util.annotation.Nullable;

final class MonoSourceFuseable<I>
extends Mono<I>
implements Fuseable,
Scannable,
OptimizableOperator<I, I> {
    final Publisher<? extends I> source;
    @Nullable
    final OptimizableOperator<?, I> optimizableOperator;

    MonoSourceFuseable(Publisher<? extends I> source2) {
        OptimizableOperator optimSource;
        this.source = Objects.requireNonNull(source2);
        this.optimizableOperator = source2 instanceof OptimizableOperator ? (optimSource = (OptimizableOperator)source2) : null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super I> actual) {
        this.source.subscribe(actual);
    }

    @Override
    public final CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super I> actual) {
        return actual;
    }

    @Override
    public final CorePublisher<? extends I> source() {
        return this;
    }

    @Override
    public final OptimizableOperator<?, ? extends I> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.from(this.source).scanUnsafe(key);
        }
        return null;
    }
}

