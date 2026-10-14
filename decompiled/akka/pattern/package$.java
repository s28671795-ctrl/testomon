/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.Scheduler;
import akka.pattern.AskSupport;
import akka.pattern.FutureTimeoutSupport;
import akka.pattern.GracefulStopSupport;
import akka.pattern.PipeToSupport;
import akka.pattern.RetrySupport;
import akka.util.Timeout;
import java.util.concurrent.CompletionStage;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

public final class package$
implements PipeToSupport,
AskSupport,
GracefulStopSupport,
FutureTimeoutSupport,
RetrySupport {
    public static final package$ MODULE$ = new package$();

    static {
        PipeToSupport.$init$(MODULE$);
        AskSupport.$init$(MODULE$);
        GracefulStopSupport.$init$(MODULE$);
        FutureTimeoutSupport.$init$(MODULE$);
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

    @Override
    public <T> Future<T> after(FiniteDuration duration, Function0<Future<T>> value, ClassicActorSystemProvider system) {
        return FutureTimeoutSupport.after$(this, duration, value, system);
    }

    @Override
    public <T> CompletionStage<T> afterCompletionStage(FiniteDuration duration, Function0<CompletionStage<T>> value, ClassicActorSystemProvider system) {
        return FutureTimeoutSupport.afterCompletionStage$(this, duration, value, system);
    }

    @Override
    public <T> Future<T> after(FiniteDuration duration, Scheduler using2, Function0<Future<T>> value, ExecutionContext ec) {
        return FutureTimeoutSupport.after$(this, duration, using2, value, ec);
    }

    @Override
    public <T> CompletionStage<T> afterCompletionStage(FiniteDuration duration, Scheduler using2, Function0<CompletionStage<T>> value, ExecutionContext ec) {
        return FutureTimeoutSupport.afterCompletionStage$(this, duration, using2, value, ec);
    }

    @Override
    public Future<Object> gracefulStop(ActorRef target, FiniteDuration timeout2, Object stopMessage) {
        return GracefulStopSupport.gracefulStop$(this, target, timeout2, stopMessage);
    }

    @Override
    public Object gracefulStop$default$3() {
        return GracefulStopSupport.gracefulStop$default$3$(this);
    }

    @Override
    public ActorRef ask(ActorRef actorRef2) {
        return AskSupport.ask$((AskSupport)this, actorRef2);
    }

    @Override
    public Future<Object> ask(ActorRef actorRef2, Object message, Timeout timeout2) {
        return AskSupport.ask$((AskSupport)this, actorRef2, message, timeout2);
    }

    @Override
    public Future<Object> ask(ActorRef actorRef2, Object message, ActorRef sender, Timeout timeout2) {
        return AskSupport.ask$((AskSupport)this, actorRef2, message, sender, timeout2);
    }

    @Override
    public Future<Object> askWithStatus(ActorRef actorRef2, Object message, Timeout timeout2) {
        return AskSupport.askWithStatus$(this, actorRef2, message, timeout2);
    }

    @Override
    public Future<Object> askWithStatus(ActorRef actorRef2, Object message, ActorRef sender, Timeout timeout2) {
        return AskSupport.askWithStatus$(this, actorRef2, message, sender, timeout2);
    }

    @Override
    public ActorSelection ask(ActorSelection actorSelection) {
        return AskSupport.ask$((AskSupport)this, actorSelection);
    }

    @Override
    public Future<Object> ask(ActorSelection actorSelection, Object message, Timeout timeout2) {
        return AskSupport.ask$((AskSupport)this, actorSelection, message, timeout2);
    }

    @Override
    public Future<Object> ask(ActorSelection actorSelection, Object message, ActorRef sender, Timeout timeout2) {
        return AskSupport.ask$((AskSupport)this, actorSelection, message, sender, timeout2);
    }

    @Override
    public <T> PipeToSupport.PipeableFuture<T> pipe(Future<T> future, ExecutionContext executionContext) {
        return PipeToSupport.pipe$(this, future, executionContext);
    }

    @Override
    public <T> PipeToSupport.PipeableCompletionStage<T> pipeCompletionStage(CompletionStage<T> future, ExecutionContext executionContext) {
        return PipeToSupport.pipeCompletionStage$(this, future, executionContext);
    }

    private package$() {
    }
}

