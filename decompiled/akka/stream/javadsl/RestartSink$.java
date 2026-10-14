/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Creator;
import akka.stream.RestartSettings;
import akka.stream.RestartSettings$;
import akka.stream.javadsl.Sink;
import java.io.Serializable;
import java.time.Duration;
import scala.Function0;

public final class RestartSink$ {
    public static final RestartSink$ MODULE$ = new RestartSink$();

    @Deprecated
    public <T> Sink<T, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, Creator<Sink<T, ?>> sinkFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor);
        return this.withBackoff(settings, sinkFactory);
    }

    @Deprecated
    public <T> Sink<T, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Sink<T, ?>> sinkFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.withBackoff(settings, sinkFactory);
    }

    public <T> Sink<T, NotUsed> withBackoff(RestartSettings settings, Creator<Sink<T, ?>> sinkFactory) {
        return akka.stream.scaladsl.RestartSink$.MODULE$.withBackoff(settings, (Function0<akka.stream.scaladsl.Sink> & Serializable)() -> ((Sink)sinkFactory.create()).asScala()).asJava();
    }

    private RestartSink$() {
    }
}

