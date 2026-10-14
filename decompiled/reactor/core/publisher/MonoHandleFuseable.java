/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiConsumer;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.ContextPropagation;
import reactor.core.publisher.FluxHandleFuseable;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

final class MonoHandleFuseable<T, R>
extends InternalMonoOperator<T, R>
implements Fuseable {
    final BiConsumer<? super T, SynchronousSink<R>> handler;

    MonoHandleFuseable(Mono<? extends T> source2, BiConsumer<? super T, SynchronousSink<R>> handler) {
        super(source2);
        this.handler = Objects.requireNonNull(handler, "handler");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        BiConsumer<? super T, SynchronousSink<R>> handler2 = ContextPropagation.contextRestoreForHandle(this.handler, actual::currentContext);
        return new FluxHandleFuseable.HandleFuseableSubscriber<T, R>(actual, handler2);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

