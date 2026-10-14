/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiConsumer;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.ContextPropagation;
import reactor.core.publisher.FluxHandle;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

final class MonoHandle<T, R>
extends InternalMonoOperator<T, R> {
    final BiConsumer<? super T, SynchronousSink<R>> handler;

    MonoHandle(Mono<? extends T> source2, BiConsumer<? super T, SynchronousSink<R>> handler) {
        super(source2);
        this.handler = Objects.requireNonNull(handler, "handler");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        BiConsumer<? super T, SynchronousSink<R>> handler2 = ContextPropagation.contextRestoreForHandle(this.handler, actual::currentContext);
        return new FluxHandle.HandleSubscriber<T, R>(actual, handler2);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

