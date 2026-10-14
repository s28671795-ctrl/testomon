/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.RestartWithBackoffFlow;
import scala.concurrent.duration.FiniteDuration;

public final class RestartWithBackoffFlow$ {
    public static final RestartWithBackoffFlow$ MODULE$ = new RestartWithBackoffFlow$();

    public <T> Flow<T, T, NotUsed> akka$stream$scaladsl$RestartWithBackoffFlow$$delayCancellation(FiniteDuration duration) {
        return Flow$.MODULE$.fromGraph(new RestartWithBackoffFlow.DelayCancellationStage(duration));
    }

    private RestartWithBackoffFlow$() {
    }
}

