/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import scala.concurrent.Promise;
import scala.concurrent.impl.Promise;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

public final class Promise$ {
    public static final Promise$ MODULE$ = new Promise$();

    public final <T> Promise<T> apply() {
        return new Promise.DefaultPromise();
    }

    public final <T> Promise<T> failed(Throwable exception) {
        Failure fromTry_result = new Failure(exception);
        return new Promise.DefaultPromise(fromTry_result);
    }

    public final <T> Promise<T> successful(T result) {
        Success<T> fromTry_result = new Success<T>(result);
        return new Promise.DefaultPromise<T>(fromTry_result);
    }

    public final <T> Promise<T> fromTry(Try<T> result) {
        return new Promise.DefaultPromise<T>(result);
    }

    private Promise$() {
    }
}

