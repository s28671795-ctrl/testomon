/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.io.Serializable;
import java.util.concurrent.TimeoutException;
import scala.Function0;
import scala.concurrent.AwaitPermission$;
import scala.concurrent.Awaitable;
import scala.concurrent.BlockContext$;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public final class Await$ {
    public static final Await$ MODULE$ = new Await$();

    public final <T> Awaitable<T> ready(Awaitable<T> awaitable, Duration atMost) throws TimeoutException, InterruptedException {
        if (awaitable instanceof Future && ((Future)awaitable).isCompleted()) {
            return awaitable.ready(atMost, AwaitPermission$.MODULE$);
        }
        Function0<Awaitable> & Serializable blocking_body = (Function0<Awaitable> & Serializable)() -> awaitable.ready(atMost, AwaitPermission$.MODULE$);
        return (Awaitable)BlockContext$.MODULE$.current().blockOn(blocking_body, AwaitPermission$.MODULE$);
    }

    public final <T> T result(Awaitable<T> awaitable, Duration atMost) throws TimeoutException, InterruptedException {
        Future future;
        if (awaitable instanceof Future && (future = (Future)awaitable).isCompleted()) {
            return future.result(atMost, AwaitPermission$.MODULE$);
        }
        Function0<Object> & Serializable blocking_body = (Function0<Object> & Serializable)() -> awaitable.result(atMost, AwaitPermission$.MODULE$);
        return BlockContext$.MODULE$.current().blockOn(blocking_body, AwaitPermission$.MODULE$);
    }

    private Await$() {
    }
}

