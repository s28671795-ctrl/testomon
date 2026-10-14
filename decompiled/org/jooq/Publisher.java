/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.concurrent.Flow;
import org.reactivestreams.FlowAdapters;

@FunctionalInterface
public interface Publisher<T>
extends org.reactivestreams.Publisher<T>,
Flow.Publisher<T> {
    @Override
    default public void subscribe(Flow.Subscriber<? super T> subscriber) {
        this.subscribe(FlowAdapters.toSubscriber(subscriber));
    }
}

