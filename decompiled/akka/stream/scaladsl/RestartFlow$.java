/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.RestartSettings;
import akka.stream.RestartSettings$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.RestartWithBackoffFlow;
import scala.Function0;
import scala.concurrent.duration.FiniteDuration;

public final class RestartFlow$ {
    public static final RestartFlow$ MODULE$ = new RestartFlow$();

    @Deprecated
    public <In, Out> Flow<In, Out, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Function0<Flow<In, Out, ?>> flowFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor);
        return this.withBackoff(settings, flowFactory);
    }

    @Deprecated
    public <In, Out> Flow<In, Out, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Flow<In, Out, ?>> flowFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.withBackoff(settings, flowFactory);
    }

    public <In, Out> Flow<In, Out, NotUsed> withBackoff(RestartSettings settings, Function0<Flow<In, Out, ?>> flowFactory) {
        return Flow$.MODULE$.fromGraph(new RestartWithBackoffFlow<In, Out>(flowFactory, settings, false));
    }

    @Deprecated
    public <In, Out> Flow<In, Out, NotUsed> onFailuresWithBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Flow<In, Out, ?>> flowFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.onFailuresWithBackoff(settings, flowFactory);
    }

    public <In, Out> Flow<In, Out, NotUsed> onFailuresWithBackoff(RestartSettings settings, Function0<Flow<In, Out, ?>> flowFactory) {
        return Flow$.MODULE$.fromGraph(new RestartWithBackoffFlow<In, Out>(flowFactory, settings, true));
    }

    private RestartFlow$() {
    }
}

