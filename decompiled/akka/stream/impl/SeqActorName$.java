/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.SeqActorNameImpl;
import java.util.concurrent.atomic.AtomicLong;

@InternalApi
public final class SeqActorName$ {
    public static final SeqActorName$ MODULE$ = new SeqActorName$();

    public SeqActorNameImpl apply(String prefix) {
        return new SeqActorNameImpl(prefix, new AtomicLong(0L));
    }

    private SeqActorName$() {
    }
}

