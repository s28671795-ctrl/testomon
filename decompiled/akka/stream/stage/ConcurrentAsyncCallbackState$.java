/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.annotation.InternalApi;
import akka.stream.stage.ConcurrentAsyncCallbackState;
import scala.collection.immutable.Nil$;
import scala.runtime.Nothing$;

@InternalApi
public final class ConcurrentAsyncCallbackState$ {
    public static final ConcurrentAsyncCallbackState$ MODULE$ = new ConcurrentAsyncCallbackState$();
    private static final ConcurrentAsyncCallbackState.Pending<Nothing$> NoPendingEvents = new ConcurrentAsyncCallbackState.Pending(Nil$.MODULE$);

    public ConcurrentAsyncCallbackState.Pending<Nothing$> NoPendingEvents() {
        return NoPendingEvents;
    }

    private ConcurrentAsyncCallbackState$() {
    }
}

