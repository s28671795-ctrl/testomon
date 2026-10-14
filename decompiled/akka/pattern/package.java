/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.Scheduler;
import akka.pattern.PipeToSupport;
import akka.pattern.package$;
import akka.util.Timeout;
import java.util.concurrent.CompletionStage;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0011:Qa\u0001\u0003\t\u0002%1Qa\u0003\u0003\t\u00021AQAI\u0001\u0005\u0002\r\nq\u0001]1dW\u0006<WM\u0003\u0002\u0006\r\u00059\u0001/\u0019;uKJt'\"A\u0004\u0002\t\u0005\\7.Y\u0002\u0001!\tQ\u0011!D\u0001\u0005\u0005\u001d\u0001\u0018mY6bO\u0016\u001cr!A\u0007\u0014-ear\u0004\u0005\u0002\u000f#5\tqBC\u0001\u0011\u0003\u0015\u00198-\u00197b\u0013\t\u0011rB\u0001\u0004B]f\u0014VM\u001a\t\u0003\u0015QI!!\u0006\u0003\u0003\u001bAK\u0007/\u001a+p'V\u0004\bo\u001c:u!\tQq#\u0003\u0002\u0019\t\tQ\u0011i]6TkB\u0004xN\u001d;\u0011\u0005)Q\u0012BA\u000e\u0005\u0005M9%/Y2fMVd7\u000b^8q'V\u0004\bo\u001c:u!\tQQ$\u0003\u0002\u001f\t\t!b)\u001e;ve\u0016$\u0016.\\3pkR\u001cV\u000f\u001d9peR\u0004\"A\u0003\u0011\n\u0005\u0005\"!\u0001\u0004*fiJL8+\u001e9q_J$\u0018A\u0002\u001fj]&$h\bF\u0001\n\u0001")
public final class package {
    public static <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, Function1<Object, Option<FiniteDuration>> delayFunction, ExecutionContext ec, Scheduler scheduler) {
        return package$.MODULE$.retry(attempt, attempts, delayFunction, ec, scheduler);
    }

    public static <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, FiniteDuration delay, ExecutionContext ec, Scheduler scheduler) {
        return package$.MODULE$.retry(attempt, attempts, delay, ec, scheduler);
    }

    public static <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, ExecutionContext ec, Scheduler scheduler) {
        return package$.MODULE$.retry(attempt, attempts, minBackoff, maxBackoff, randomFactor, ec, scheduler);
    }

    public static <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, ExecutionContext ec) {
        return package$.MODULE$.retry(attempt, attempts, ec);
    }

    public static <T> CompletionStage<T> afterCompletionStage(FiniteDuration duration, Scheduler using2, Function0<CompletionStage<T>> value, ExecutionContext ec) {
        return package$.MODULE$.afterCompletionStage(duration, using2, value, ec);
    }

    public static <T> Future<T> after(FiniteDuration duration, Scheduler using2, Function0<Future<T>> value, ExecutionContext ec) {
        return package$.MODULE$.after(duration, using2, value, ec);
    }

    public static <T> CompletionStage<T> afterCompletionStage(FiniteDuration duration, Function0<CompletionStage<T>> value, ClassicActorSystemProvider system) {
        return package$.MODULE$.afterCompletionStage(duration, value, system);
    }

    public static <T> Future<T> after(FiniteDuration duration, Function0<Future<T>> value, ClassicActorSystemProvider system) {
        return package$.MODULE$.after(duration, value, system);
    }

    public static Object gracefulStop$default$3() {
        return package$.MODULE$.gracefulStop$default$3();
    }

    public static Future<Object> gracefulStop(ActorRef target, FiniteDuration timeout2, Object stopMessage) {
        return package$.MODULE$.gracefulStop(target, timeout2, stopMessage);
    }

    public static Future<Object> ask(ActorSelection actorSelection, Object message, ActorRef sender, Timeout timeout2) {
        return package$.MODULE$.ask(actorSelection, message, sender, timeout2);
    }

    public static Future<Object> ask(ActorSelection actorSelection, Object message, Timeout timeout2) {
        return package$.MODULE$.ask(actorSelection, message, timeout2);
    }

    public static ActorSelection ask(ActorSelection actorSelection) {
        return package$.MODULE$.ask(actorSelection);
    }

    public static Future<Object> askWithStatus(ActorRef actorRef2, Object message, ActorRef sender, Timeout timeout2) {
        return package$.MODULE$.askWithStatus(actorRef2, message, sender, timeout2);
    }

    public static Future<Object> askWithStatus(ActorRef actorRef2, Object message, Timeout timeout2) {
        return package$.MODULE$.askWithStatus(actorRef2, message, timeout2);
    }

    public static Future<Object> ask(ActorRef actorRef2, Object message, ActorRef sender, Timeout timeout2) {
        return package$.MODULE$.ask(actorRef2, message, sender, timeout2);
    }

    public static Future<Object> ask(ActorRef actorRef2, Object message, Timeout timeout2) {
        return package$.MODULE$.ask(actorRef2, message, timeout2);
    }

    public static ActorRef ask(ActorRef actorRef2) {
        return package$.MODULE$.ask(actorRef2);
    }

    public static <T> PipeToSupport.PipeableCompletionStage<T> pipeCompletionStage(CompletionStage<T> future, ExecutionContext executionContext) {
        return package$.MODULE$.pipeCompletionStage(future, executionContext);
    }

    public static <T> PipeToSupport.PipeableFuture<T> pipe(Future<T> future, ExecutionContext executionContext) {
        return package$.MODULE$.pipe(future, executionContext);
    }
}

