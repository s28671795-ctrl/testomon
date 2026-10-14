/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class MonoSingleOptionalCallable<T>
extends Mono<Optional<T>>
implements Callable<Optional<T>>,
SourceProducer<Optional<T>> {
    final Callable<? extends T> callable;

    MonoSingleOptionalCallable(Callable<? extends T> source2) {
        this.callable = Objects.requireNonNull(source2, "source");
    }

    @Override
    public void subscribe(CoreSubscriber<? super Optional<T>> actual) {
        Operators.MonoInnerProducerBase<Optional<T>> sds = new Operators.MonoInnerProducerBase<Optional<T>>(actual);
        actual.onSubscribe(sds);
        if (sds.isCancelled()) {
            return;
        }
        try {
            T t2 = this.callable.call();
            sds.complete(Optional.ofNullable(t2));
        }
        catch (Throwable e) {
            actual.onError(Operators.onOperatorError(e, actual.currentContext()));
        }
    }

    @Override
    public Optional<T> block() {
        return this.block(Duration.ZERO);
    }

    @Override
    public Optional<T> block(Duration m4) {
        T v;
        try {
            v = this.callable.call();
        }
        catch (Throwable e) {
            throw Exceptions.propagate(e);
        }
        return Optional.ofNullable(v);
    }

    @Override
    public Optional<T> call() throws Exception {
        T v = this.callable.call();
        return Optional.ofNullable(v);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

