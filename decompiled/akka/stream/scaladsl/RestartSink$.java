/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.RestartSettings;
import akka.stream.RestartSettings$;
import akka.stream.scaladsl.RestartWithBackoffSink;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Sink$;
import scala.Function0;
import scala.concurrent.duration.FiniteDuration;

public final class RestartSink$ {
    public static final RestartSink$ MODULE$ = new RestartSink$();

    @Deprecated
    public <T> Sink<T, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Function0<Sink<T, ?>> sinkFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor);
        return this.withBackoff(settings, sinkFactory);
    }

    @Deprecated
    public <T> Sink<T, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Sink<T, ?>> sinkFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.withBackoff(settings, sinkFactory);
    }

    public <T> Sink<T, NotUsed> withBackoff(RestartSettings settings, Function0<Sink<T, ?>> sinkFactory) {
        return Sink$.MODULE$.fromGraph(new RestartWithBackoffSink<T>(sinkFactory, settings));
    }

    private RestartSink$() {
    }
}

