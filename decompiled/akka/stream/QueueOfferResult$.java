/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.QueueOfferResult;
import akka.stream.QueueOfferResult$Dropped$;
import akka.stream.QueueOfferResult$Enqueued$;

public final class QueueOfferResult$ {
    public static final QueueOfferResult$ MODULE$ = new QueueOfferResult$();

    public QueueOfferResult enqueued() {
        return QueueOfferResult$Enqueued$.MODULE$;
    }

    public QueueOfferResult dropped() {
        return QueueOfferResult$Dropped$.MODULE$;
    }

    private QueueOfferResult$() {
    }
}

