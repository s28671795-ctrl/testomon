/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoCallable;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class FluxCallable<T>
extends Flux<T>
implements Callable<T>,
Fuseable,
SourceProducer<T> {
    final Callable<T> callable;

    FluxCallable(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        actual.onSubscribe(new MonoCallable.MonoCallableSubscription<T>(actual, this.callable));
    }

    @Override
    @Nullable
    public T call() throws Exception {
        return this.callable.call();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

