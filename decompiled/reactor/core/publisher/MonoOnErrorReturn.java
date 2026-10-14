/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.function.Predicate;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxOnErrorReturn;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

final class MonoOnErrorReturn<T>
extends InternalMonoOperator<T, T> {
    @Nullable
    final Predicate<? super Throwable> resumableErrorPredicate;
    @Nullable
    final T fallbackValue;

    MonoOnErrorReturn(Mono<? extends T> source2, @Nullable Predicate<? super Throwable> predicate, @Nullable T value) {
        super(source2);
        this.resumableErrorPredicate = predicate;
        this.fallbackValue = value;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new FluxOnErrorReturn.ReturnSubscriber<T>(actual, (Predicate<Throwable>)this.resumableErrorPredicate, this.fallbackValue, false);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

