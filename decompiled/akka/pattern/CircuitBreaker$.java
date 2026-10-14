/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ExtendedActorSystem;
import akka.actor.Scheduler;
import akka.dispatch.ExecutionContexts$;
import akka.pattern.CircuitBreaker;
import akka.pattern.CircuitBreakersRegistry;
import akka.pattern.CircuitBreakersRegistry$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;
import java.util.function.BiFunction;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxesRunTime;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

public final class CircuitBreaker$ {
    public static final CircuitBreaker$ MODULE$ = new CircuitBreaker$();

    public CircuitBreaker apply(Scheduler scheduler, int maxFailures, FiniteDuration callTimeout, FiniteDuration resetTimeout) {
        return new CircuitBreaker(scheduler, maxFailures, callTimeout, resetTimeout, ExecutionContexts$.MODULE$.parasitic());
    }

    public CircuitBreaker apply(String id, ExtendedActorSystem system) {
        return ((CircuitBreakersRegistry)CircuitBreakersRegistry$.MODULE$.apply(system)).get(id);
    }

    public CircuitBreaker create(Scheduler scheduler, int maxFailures, Duration callTimeout, Duration resetTimeout) {
        return this.apply(scheduler, maxFailures, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(callTimeout)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(resetTimeout)));
    }

    public CircuitBreaker lookup(String id, ExtendedActorSystem system) {
        return this.apply(id, system);
    }

    public <T> Function1<Try<T>, Object> convertJavaFailureFnToScala(BiFunction<Optional<T>, Optional<Throwable>, Boolean> javaFn) {
        Function1<Try, Object> & Serializable failureFnInScala = (Function1<Try, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(CircuitBreaker$.$anonfun$convertJavaFailureFnToScala$1(javaFn, x0$1));
        return failureFnInScala;
    }

    public static final /* synthetic */ boolean $anonfun$convertJavaFailureFnToScala$1(BiFunction javaFn$1, Try x0$1) {
        Try try_ = x0$1;
        if (try_ instanceof Success) {
            Success success = (Success)try_;
            Object t2 = success.value();
            return Predef$.MODULE$.Boolean2boolean((Boolean)javaFn$1.apply(Optional.of(t2), Optional.empty()));
        }
        if (try_ instanceof Failure) {
            Failure failure = (Failure)try_;
            Throwable err = failure.exception();
            return Predef$.MODULE$.Boolean2boolean((Boolean)javaFn$1.apply(Optional.empty(), Optional.of(err)));
        }
        throw new MatchError(try_);
    }

    private CircuitBreaker$() {
    }
}

