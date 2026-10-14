/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Scheduler;
import akka.pattern.RetrySupport;
import akka.pattern.package$;
import akka.util.ConstantFun$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.util.control.NonFatal$;

public final class RetrySupport$
implements RetrySupport {
    public static final RetrySupport$ MODULE$ = new RetrySupport$();

    static {
        RetrySupport.$init$(MODULE$);
    }

    @Override
    public <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, ExecutionContext ec) {
        return RetrySupport.retry$(this, attempt, attempts, ec);
    }

    @Override
    public <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, ExecutionContext ec, Scheduler scheduler) {
        return RetrySupport.retry$(this, attempt, attempts, minBackoff, maxBackoff, randomFactor, ec, scheduler);
    }

    @Override
    public <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, FiniteDuration delay, ExecutionContext ec, Scheduler scheduler) {
        return RetrySupport.retry$((RetrySupport)this, attempt, attempts, delay, ec, scheduler);
    }

    @Override
    public <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, Function1<Object, Option<FiniteDuration>> delayFunction, ExecutionContext ec, Scheduler scheduler) {
        return RetrySupport.retry$((RetrySupport)this, attempt, attempts, delayFunction, ec, scheduler);
    }

    public <T> Future<T> akka$pattern$RetrySupport$$retry(Function0<Future<T>> attempt, int maxAttempts, int attempted, ExecutionContext ec) {
        return this.akka$pattern$RetrySupport$$retry(attempt, maxAttempts, ConstantFun$.MODULE$.scalaAnyToNone(), attempted, ec, null);
    }

    public <T> Future<T> akka$pattern$RetrySupport$$retry(Function0<Future<T>> attempt, int maxAttempts, Function1<Object, Option<FiniteDuration>> delayFunction, int attempted, ExecutionContext ec, Scheduler scheduler) {
        Predef$.MODULE$.require(maxAttempts >= 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter maxAttempts must >= 0.");
        Predef$.MODULE$.require(attempt != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter attempt should not be null.");
        if (maxAttempts - attempted > 0) {
            Future result = RetrySupport$.tryAttempt$1(attempt);
            if (result == null) {
                return result;
            }
            int nextAttempt = attempted + 1;
            return result.recoverWith(new Serializable(delayFunction, nextAttempt, attempt, maxAttempts, ec, scheduler){
                private static final long serialVersionUID = 0L;
                private final Function1 delayFunction$1;
                private final int nextAttempt$1;
                private final Function0 attempt$1;
                private final int maxAttempts$1;
                private final ExecutionContext ec$1;
                private final Scheduler scheduler$1;

                public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    Option<Throwable> option;
                    A1 A1 = x1;
                    if (A1 != null && !(option = NonFatal$.MODULE$.unapply(A1)).isEmpty()) {
                        Option option2 = (Option)this.delayFunction$1.apply(BoxesRunTime.boxToInteger(this.nextAttempt$1));
                        if (option2 instanceof Some) {
                            Some some = (Some)option2;
                            FiniteDuration delay = (FiniteDuration)some.value();
                            if (delay.length() < 1L) {
                                return (B1)RetrySupport$.MODULE$.akka$pattern$RetrySupport$$retry(this.attempt$1, this.maxAttempts$1, this.delayFunction$1, this.nextAttempt$1, this.ec$1, this.scheduler$1);
                            }
                            return (B1)package$.MODULE$.after(delay, this.scheduler$1, (Function0<Future> & Serializable)() -> RetrySupport$.MODULE$.akka$pattern$RetrySupport$$retry($this.attempt$1, $this.maxAttempts$1, $this.delayFunction$1, $this.nextAttempt$1, $this.ec$1, $this.scheduler$1), this.ec$1);
                        }
                        if (None$.MODULE$.equals(option2)) {
                            return (B1)RetrySupport$.MODULE$.akka$pattern$RetrySupport$$retry(this.attempt$1, this.maxAttempts$1, this.delayFunction$1, this.nextAttempt$1, this.ec$1, this.scheduler$1);
                        }
                        if (option2 == null) {
                            return (B1)Future$.MODULE$.failed(new IllegalArgumentException("The delayFunction of retry should not return null."));
                        }
                        throw new MatchError(option2);
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Throwable x1) {
                    Option<Throwable> option;
                    Throwable throwable = x1;
                    return throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty();
                }
                {
                    this.delayFunction$1 = delayFunction$1;
                    this.nextAttempt$1 = nextAttempt$1;
                    this.attempt$1 = attempt$1;
                    this.maxAttempts$1 = maxAttempts$1;
                    this.ec$1 = ec$1;
                    this.scheduler$1 = scheduler$1;
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.pattern.RetrySupport$$anonfun$akka$pattern$RetrySupport$$retry$1 )}, serializedLambda);
                }
            }, ec);
        }
        return RetrySupport$.tryAttempt$1(attempt);
    }

    private static final Future tryAttempt$1(Function0 attempt$1) {
        Future future;
        try {
            future = (Future)attempt$1.apply();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable exc = option.get();
                future = Future$.MODULE$.failed(exc);
            }
            throw throwable;
        }
        return future;
    }

    private RetrySupport$() {
    }
}

