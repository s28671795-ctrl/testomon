/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class SerializedSubscriber<T>
implements InnerOperator<T, T> {
    final CoreSubscriber<? super T> actual;
    boolean drainLoopInProgress;
    boolean concurrentlyAddedContent;
    volatile boolean done;
    volatile boolean cancelled;
    LinkedArrayNode<T> head;
    LinkedArrayNode<T> tail;
    Throwable error;
    Subscription s;

    SerializedSubscriber(CoreSubscriber<? super T> actual) {
        this.actual = actual;
    }

    @Override
    public void onSubscribe(Subscription s2) {
        if (Operators.validate(this.s, s2)) {
            this.s = s2;
            this.actual.onSubscribe(this);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onNext(T t2) {
        if (this.cancelled) {
            Operators.onDiscard(t2, this.actual.currentContext());
            return;
        }
        if (this.done) {
            Operators.onNextDropped(t2, this.actual.currentContext());
            return;
        }
        SerializedSubscriber serializedSubscriber = this;
        synchronized (serializedSubscriber) {
            if (this.cancelled) {
                Operators.onDiscard(t2, this.actual.currentContext());
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            if (this.drainLoopInProgress) {
                this.serAdd(t2);
                this.concurrentlyAddedContent = true;
                return;
            }
            this.drainLoopInProgress = true;
        }
        this.actual.onNext(t2);
        this.serDrainLoop(this.actual);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onError(Throwable t2) {
        if (this.cancelled || this.done) {
            return;
        }
        SerializedSubscriber serializedSubscriber = this;
        synchronized (serializedSubscriber) {
            if (this.cancelled || this.done) {
                return;
            }
            this.done = true;
            this.error = t2;
            if (this.drainLoopInProgress) {
                this.concurrentlyAddedContent = true;
                return;
            }
        }
        this.actual.onError(t2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onComplete() {
        if (this.cancelled || this.done) {
            return;
        }
        SerializedSubscriber serializedSubscriber = this;
        synchronized (serializedSubscriber) {
            if (this.cancelled || this.done) {
                return;
            }
            this.done = true;
            if (this.drainLoopInProgress) {
                this.concurrentlyAddedContent = true;
                return;
            }
        }
        this.actual.onComplete();
    }

    @Override
    public void request(long n) {
        this.s.request(n);
    }

    @Override
    public void cancel() {
        this.cancelled = true;
        this.s.cancel();
    }

    void serAdd(T value) {
        if (this.cancelled) {
            Operators.onDiscard(value, this.actual.currentContext());
            return;
        }
        LinkedArrayNode<T> t2 = this.tail;
        if (t2 == null) {
            t2 = new LinkedArrayNode<T>(value);
            this.head = t2;
            this.tail = t2;
        } else if (t2.count == 16) {
            LinkedArrayNode<T> n = new LinkedArrayNode<T>(value);
            t2.next = n;
            this.tail = n;
        } else {
            t2.array[t2.count++] = value;
        }
        if (this.cancelled) {
            Operators.onDiscard(value, this.actual.currentContext());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void serDrainLoop(CoreSubscriber<? super T> actual) {
        boolean d;
        do {
            LinkedArrayNode<T> n;
            Throwable e;
            if (this.cancelled) {
                SerializedSubscriber serializedSubscriber = this;
                synchronized (serializedSubscriber) {
                    this.discardMultiple(this.head);
                }
                return;
            }
            SerializedSubscriber serializedSubscriber = this;
            synchronized (serializedSubscriber) {
                if (this.cancelled) {
                    this.discardMultiple(this.head);
                    return;
                }
                if (!this.concurrentlyAddedContent) {
                    this.drainLoopInProgress = false;
                    return;
                }
                this.concurrentlyAddedContent = false;
                d = this.done;
                e = this.error;
                n = this.head;
                this.head = null;
                this.tail = null;
            }
            while (n != null) {
                T[] arr = n.array;
                int c = n.count;
                for (int i = 0; i < c; ++i) {
                    if (this.cancelled) {
                        SerializedSubscriber serializedSubscriber2 = this;
                        synchronized (serializedSubscriber2) {
                            this.discardMultiple(n);
                        }
                        return;
                    }
                    actual.onNext(arr[i]);
                }
                n = n.next;
            }
            if (this.cancelled) {
                serializedSubscriber = this;
                synchronized (serializedSubscriber) {
                    this.discardMultiple(this.head);
                }
                return;
            }
            if (e == null) continue;
            actual.onError(e);
            return;
        } while (!d);
        actual.onComplete();
    }

    private void discardMultiple(LinkedArrayNode<T> head) {
        LinkedArrayNode<T> originalHead = head;
        LinkedArrayNode<T> h2 = head;
        while (h2 != null) {
            for (int i = 0; i < h2.count; ++i) {
                Operators.onDiscard(h2.array[i], this.actual.currentContext());
            }
            h2 = h2.next;
            if (h2 != null || this.head == originalHead) continue;
            h2 = originalHead = this.head;
        }
    }

    @Override
    public CoreSubscriber<? super T> actual() {
        return this.actual;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.s;
        }
        if (key == Scannable.Attr.ERROR) {
            return this.error;
        }
        if (key == Scannable.Attr.BUFFERED) {
            return this.producerCapacity();
        }
        if (key == Scannable.Attr.CAPACITY) {
            return 16;
        }
        if (key == Scannable.Attr.CANCELLED) {
            return this.cancelled;
        }
        if (key == Scannable.Attr.TERMINATED) {
            return this.done;
        }
        return InnerOperator.super.scanUnsafe(key);
    }

    int producerCapacity() {
        LinkedArrayNode<T> node = this.tail;
        if (node != null) {
            return node.count;
        }
        return 0;
    }

    static final class LinkedArrayNode<T> {
        static final int DEFAULT_CAPACITY = 16;
        final T[] array = new Object[16];
        int count;
        LinkedArrayNode<T> next;

        LinkedArrayNode(T value) {
            this.array[0] = value;
            this.count = 1;
        }
    }
}

