/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.AssemblyOp;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelFluxOnAssembly<T>
extends ParallelFlux<T>
implements Fuseable,
AssemblyOp,
Scannable {
    final ParallelFlux<T> source;
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    ParallelFluxOnAssembly(ParallelFlux<T> source2, FluxOnAssembly.AssemblySnapshot stacktrace) {
        this.source = source2;
        this.stacktrace = stacktrace;
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override
    public void subscribe(CoreSubscriber<? super T>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        CoreSubscriber[] parents = new CoreSubscriber[n];
        for (int i = 0; i < n; ++i) {
            CoreSubscriber<? super T> s2 = subscribers[i];
            if (s2 instanceof Fuseable.ConditionalSubscriber) {
                Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)s2;
                s2 = new FluxOnAssembly.OnAssemblyConditionalSubscriber<T>(cs, this.stacktrace, (Publisher<?>)this.source, (Publisher<?>)this);
            } else {
                s2 = new FluxOnAssembly.OnAssemblySubscriber<T>(s2, this.stacktrace, this.source, this);
            }
            parents[i] = s2;
        }
        this.source.subscribe(parents);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.ACTUAL_METADATA) {
            return !this.stacktrace.isCheckpoint;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public String stepName() {
        return this.stacktrace.operatorAssemblyInformation();
    }

    @Override
    public String toString() {
        return this.stacktrace.operatorAssemblyInformation();
    }
}

