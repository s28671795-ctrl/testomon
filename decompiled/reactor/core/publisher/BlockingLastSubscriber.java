/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.publisher.BlockingSingleSubscriber;

final class BlockingLastSubscriber<T>
extends BlockingSingleSubscriber<T> {
    BlockingLastSubscriber() {
    }

    @Override
    public void onNext(T t2) {
        this.value = t2;
    }

    @Override
    public void onError(Throwable t2) {
        this.value = null;
        this.error = t2;
        this.countDown();
    }

    @Override
    public String stepName() {
        return "blockLast";
    }
}

