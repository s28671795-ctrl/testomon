/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.Scheduler;
import akka.japi.Function;
import akka.pattern.PipeToSupport;
import akka.pattern.extended.package$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.Timeout;
import akka.util.Timeout$;
import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.compat.java8.FutureConverters$FutureOps$;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionalGeneric$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.runtime.BoxesRunTime;

public final class Patterns$ {
    public static final Patterns$ MODULE$ = new Patterns$();

    public Future<Object> ask(ActorRef actor, Object message, Timeout timeout2) {
        return akka.pattern.package$.MODULE$.ask(actor, message, timeout2);
    }

    public CompletionStage<Object> ask(ActorRef actor, Object message, Duration timeout2) {
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(akka.pattern.package$.MODULE$.ask(actor, message, Timeout$.MODULE$.durationToTimeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2))))));
    }

    public CompletionStage<Object> askWithStatus(ActorRef actor, Object message, Duration timeout2) {
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(akka.pattern.package$.MODULE$.askWithStatus(actor, message, Timeout$.MODULE$.durationToTimeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2))))));
    }

    public Future<Object> askWithReplyTo(ActorRef actor, Function<ActorRef, Object> messageFactory, Timeout timeout2) {
        return package$.MODULE$.ask(actor, (Function1<ActorRef, Object> & Serializable)param2 -> messageFactory.apply((ActorRef)param2), timeout2);
    }

    public CompletionStage<Object> askWithReplyTo(ActorRef actor, akka.japi.function.Function<ActorRef, Object> messageFactory, Duration timeout2) {
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(package$.MODULE$.ask(actor, (Function1<ActorRef, Object> & Serializable)param2 -> messageFactory.apply((ActorRef)param2), Timeout$.MODULE$.create(timeout2))));
    }

    public Future<Object> ask(ActorRef actor, Object message, long timeoutMillis) {
        return akka.pattern.package$.MODULE$.ask(actor, message, new Timeout(timeoutMillis, TimeUnit.MILLISECONDS));
    }

    public Future<Object> askWithReplyTo(ActorRef actor, Function<ActorRef, Object> messageFactory, long timeoutMillis) {
        return package$.MODULE$.ask(actor, (Function1<ActorRef, Object> & Serializable)param2 -> messageFactory.apply((ActorRef)param2), new Timeout(new package.DurationLong(scala.concurrent.duration.package$.MODULE$.DurationLong(timeoutMillis)).millis()));
    }

    public Future<Object> ask(ActorSelection selection, Object message, Timeout timeout2) {
        return akka.pattern.package$.MODULE$.ask(selection, message, timeout2);
    }

    public CompletionStage<Object> ask(ActorSelection selection, Object message, Duration timeout2) {
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(akka.pattern.package$.MODULE$.ask(selection, message, Timeout$.MODULE$.durationToTimeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2))))));
    }

    public Future<Object> ask(ActorSelection selection, Object message, long timeoutMillis) {
        return akka.pattern.package$.MODULE$.ask(selection, message, new Timeout(timeoutMillis, TimeUnit.MILLISECONDS));
    }

    public Future<Object> askWithReplyTo(ActorSelection selection, Function<ActorRef, Object> messageFactory, long timeoutMillis) {
        return package$.MODULE$.ask(selection, (Function1<ActorRef, Object> & Serializable)param2 -> messageFactory.apply((ActorRef)param2), new Timeout(new package.DurationLong(scala.concurrent.duration.package$.MODULE$.DurationLong(timeoutMillis)).millis()));
    }

    public CompletionStage<Object> askWithReplyTo(ActorSelection selection, Function<ActorRef, Object> messageFactory, Duration timeout2) {
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(package$.MODULE$.ask(selection, (Function1<ActorRef, Object> & Serializable)param2 -> messageFactory.apply((ActorRef)param2), Timeout$.MODULE$.durationToTimeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2))))));
    }

    public <T> PipeToSupport.PipeableFuture<T> pipe(Future<T> future, ExecutionContext context) {
        return akka.pattern.package$.MODULE$.pipe(future, context);
    }

    public <T> PipeToSupport.PipeableCompletionStage<T> pipe(CompletionStage<T> future, ExecutionContext context) {
        return akka.pattern.package$.MODULE$.pipeCompletionStage(future, context);
    }

    public Future<Boolean> gracefulStop(ActorRef target, FiniteDuration timeout2) {
        return akka.pattern.package$.MODULE$.gracefulStop(target, timeout2, akka.pattern.package$.MODULE$.gracefulStop$default$3());
    }

    public CompletionStage<Boolean> gracefulStop(ActorRef target, Duration timeout2) {
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(akka.pattern.package$.MODULE$.gracefulStop(target, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)), akka.pattern.package$.MODULE$.gracefulStop$default$3())));
    }

    public Future<Boolean> gracefulStop(ActorRef target, FiniteDuration timeout2, Object stopMessage) {
        return akka.pattern.package$.MODULE$.gracefulStop(target, timeout2, stopMessage);
    }

    public CompletionStage<Boolean> gracefulStop(ActorRef target, Duration timeout2, Object stopMessage) {
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(akka.pattern.package$.MODULE$.gracefulStop(target, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)), stopMessage)));
    }

    public <T> Future<T> after(FiniteDuration duration, Scheduler scheduler, ExecutionContext context, Callable<Future<T>> value) {
        return akka.pattern.package$.MODULE$.after(duration, scheduler, (Function0<Future> & Serializable)() -> (Future)value.call(), context);
    }

    public <T> CompletionStage<T> after(Duration duration, ClassicActorSystemProvider system, Callable<CompletionStage<T>> value) {
        return this.after(duration, system.classicSystem().scheduler(), (ExecutionContext)system.classicSystem().dispatcher(), value);
    }

    public <T> CompletionStage<T> after(Duration duration, Scheduler scheduler, ExecutionContext context, Callable<CompletionStage<T>> value) {
        return akka.pattern.package$.MODULE$.afterCompletionStage(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(duration)), scheduler, (Function0<CompletionStage> & Serializable)() -> (CompletionStage)value.call(), context);
    }

    public <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, ExecutionContext ec) {
        Predef$.MODULE$.require(attempt != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter attempt should not be null.");
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(akka.pattern.package$.MODULE$.retry((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)attempt.call())), attempts, ec)));
    }

    public <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, Duration minBackoff, Duration maxBackoff, double randomFactor, ClassicActorSystemProvider system) {
        return this.retry(attempt, attempts, minBackoff, maxBackoff, randomFactor, system.classicSystem().scheduler(), system.classicSystem().dispatcher());
    }

    public <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, Duration minBackoff, Duration maxBackoff, double randomFactor, Scheduler scheduler, ExecutionContext ec) {
        Predef$.MODULE$.require(attempt != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter attempt should not be null.");
        Predef$.MODULE$.require(minBackoff != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter minBackoff should not be null.");
        Predef$.MODULE$.require(maxBackoff != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter minBackoff should not be null.");
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(akka.pattern.package$.MODULE$.retry((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)attempt.call())), attempts, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(minBackoff)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(maxBackoff)), randomFactor, ec, scheduler)));
    }

    public <T> Future<T> retry(Callable<Future<T>> attempt, int attempts, FiniteDuration delay, Scheduler scheduler, ExecutionContext context) {
        Predef$.MODULE$.require(attempt != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter attempt should not be null.");
        return akka.pattern.package$.MODULE$.retry((Function0<Future> & Serializable)() -> (Future)attempt.call(), attempts, delay, context, scheduler);
    }

    public <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, Duration delay, ClassicActorSystemProvider system) {
        return this.retry(attempt, attempts, delay, system.classicSystem().scheduler(), (ExecutionContext)system.classicSystem().dispatcher());
    }

    public <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, Duration delay, Scheduler scheduler, ExecutionContext ec) {
        Predef$.MODULE$.require(attempt != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter attempt should not be null.");
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(akka.pattern.package$.MODULE$.retry((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)attempt.call())), attempts, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)), ec, scheduler)));
    }

    public <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, IntFunction<Optional<Duration>> delayFunction, Scheduler scheduler, ExecutionContext context) {
        Predef$.MODULE$.require(attempt != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter attempt should not be null.");
        return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(akka.pattern.package$.MODULE$.retry((Function0<Future> & Serializable)() -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)attempt.call())), attempts, (Function1<Object, Option<FiniteDuration>>)(Function1<Object, Option> & Serializable)attempted -> Patterns$.$anonfun$retry$13(delayFunction, BoxesRunTime.unboxToInt(attempted)), context, scheduler)));
    }

    public static final /* synthetic */ Option $anonfun$retry$13(IntFunction delayFunction$1, int attempted) {
        return OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric((Optional)delayFunction$1.apply(attempted))).map((Function1<Duration, FiniteDuration> & Serializable)x$1 -> JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps((Duration)x$1)));
    }

    private Patterns$() {
    }
}

