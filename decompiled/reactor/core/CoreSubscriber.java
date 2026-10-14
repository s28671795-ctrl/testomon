/*
 * Decompiled with CFR 0.152.
 */
package reactor.core;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.util.context.Context;

public interface CoreSubscriber<T>
extends Subscriber<T> {
    default public Context currentContext() {
        return Context.empty();
    }

    @Override
    public void onSubscribe(Subscription var1);
}

