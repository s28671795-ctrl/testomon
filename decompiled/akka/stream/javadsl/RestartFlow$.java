/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Creator;
import akka.stream.RestartSettings;
import akka.stream.RestartSettings$;
import akka.stream.javadsl.Flow;
import java.io.Serializable;
import java.time.Duration;
import scala.Function0;

public final class RestartFlow$ {
    public static final RestartFlow$ MODULE$ = new RestartFlow$();

    @Deprecated
    public <In, Out> Flow<In, Out, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, Creator<Flow<In, Out, ?>> flowFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor);
        return this.withBackoff(settings, flowFactory);
    }

    @Deprecated
    public <In, Out> Flow<In, Out, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Flow<In, Out, ?>> flowFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.withBackoff(settings, flowFactory);
    }

    public <In, Out> Flow<In, Out, NotUsed> withBackoff(RestartSettings settings, Creator<Flow<In, Out, ?>> flowFactory) {
        return akka.stream.scaladsl.RestartFlow$.MODULE$.withBackoff(settings, (Function0<akka.stream.scaladsl.Flow> & Serializable)() -> ((Flow)flowFactory.create()).asScala()).asJava();
    }

    @Deprecated
    public <In, Out> Flow<In, Out, NotUsed> onFailuresWithBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Flow<In, Out, ?>> flowFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.onFailuresWithBackoff(settings, flowFactory);
    }

    public <In, Out> Flow<In, Out, NotUsed> onFailuresWithBackoff(RestartSettings settings, Creator<Flow<In, Out, ?>> flowFactory) {
        return akka.stream.scaladsl.RestartFlow$.MODULE$.onFailuresWithBackoff(settings, (Function0<akka.stream.scaladsl.Flow> & Serializable)() -> ((Flow)flowFactory.create()).asScala()).asJava();
    }

    private RestartFlow$() {
    }
}

