/*
 * Decompiled with CFR 0.152.
 */
package reactor.core;

import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;

public interface CorePublisher<T>
extends Publisher<T> {
    @Override
    public void subscribe(CoreSubscriber<? super T> var1);
}

