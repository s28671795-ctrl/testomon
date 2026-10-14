/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.publisher.BlockingSingleSubscriber;

final class BlockingFirstSubscriber<T>
extends BlockingSingleSubscriber<T> {
    BlockingFirstSubscriber() {
    }

    @Override
    public void onNext(T t2) {
        if (this.value == null) {
            this.value = t2;
            this.dispose();
            this.countDown();
        }
    }

    @Override
    public void onError(Throwable t2) {
        if (this.value == null) {
            this.error = t2;
        }
        this.countDown();
    }

    @Override
    public String stepName() {
        return "blockFirst";
    }
}

