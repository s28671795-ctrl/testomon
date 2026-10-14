/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.event.Logging$;
import akka.japi.function.Function;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import akka.stream.Supervision;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.Serializable;
import java.time.Duration;
import scala.Function1;
import scala.concurrent.duration.FiniteDuration;

public final class ActorAttributes$ {
    public static final ActorAttributes$ MODULE$ = new ActorAttributes$();
    private static final ActorAttributes.Dispatcher IODispatcher = new ActorAttributes.Dispatcher("akka.stream.materializer.blocking-io-dispatcher");

    public ActorAttributes.Dispatcher IODispatcher() {
        return IODispatcher;
    }

    public Attributes dispatcher(String dispatcher) {
        return Attributes$.MODULE$.apply(new ActorAttributes.Dispatcher(dispatcher));
    }

    public Attributes supervisionStrategy(Function1<Throwable, Supervision.Directive> decider) {
        return Attributes$.MODULE$.apply(new ActorAttributes.SupervisionStrategy(decider));
    }

    public Attributes withSupervisionStrategy(Function<Throwable, Supervision.Directive> decider) {
        return this.supervisionStrategy((Function1<Throwable, Supervision.Directive> & Serializable)param2 -> (Supervision.Directive)decider.apply((Throwable)param2));
    }

    public Attributes createLogLevels(int onElement, int onFinish, int onFailure) {
        return this.logLevels(onElement, onFinish, onFailure);
    }

    public Attributes createLogLevels(int onElement) {
        return this.logLevels(onElement, this.logLevels$default$2(), this.logLevels$default$3());
    }

    public Attributes logLevels(int onElement, int onFinish, int onFailure) {
        return Attributes$.MODULE$.apply(new Attributes.LogLevels(onElement, onFinish, onFailure));
    }

    public int logLevels$default$1() {
        return Logging$.MODULE$.DebugLevel();
    }

    public int logLevels$default$2() {
        return Logging$.MODULE$.DebugLevel();
    }

    public int logLevels$default$3() {
        return Logging$.MODULE$.ErrorLevel();
    }

    public Attributes debugLogging(boolean enabled) {
        return Attributes$.MODULE$.apply(new ActorAttributes.DebugLogging(enabled));
    }

    public Attributes streamSubscriptionTimeout(FiniteDuration timeout2, StreamSubscriptionTimeoutTerminationMode mode) {
        return Attributes$.MODULE$.apply(new ActorAttributes.StreamSubscriptionTimeout(timeout2, mode));
    }

    public Attributes streamSubscriptionTimeout(Duration timeout2, StreamSubscriptionTimeoutTerminationMode mode) {
        return this.streamSubscriptionTimeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)), mode);
    }

    public Attributes outputBurstLimit(int limit) {
        return Attributes$.MODULE$.apply(new ActorAttributes.OutputBurstLimit(limit));
    }

    public Attributes fuzzingMode(boolean enabled) {
        return Attributes$.MODULE$.apply(new ActorAttributes.FuzzingMode(enabled));
    }

    public Attributes maxFixedBufferSize(int size) {
        return Attributes$.MODULE$.apply(new ActorAttributes.MaxFixedBufferSize(size));
    }

    public Attributes syncProcessingLimit(int limit) {
        return Attributes$.MODULE$.apply(new ActorAttributes.SyncProcessingLimit(limit));
    }

    private ActorAttributes$() {
    }
}

