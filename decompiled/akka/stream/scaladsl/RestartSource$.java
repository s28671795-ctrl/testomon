/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.RestartSettings;
import akka.stream.RestartSettings$;
import akka.stream.scaladsl.RestartWithBackoffSource;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import scala.Function0;
import scala.concurrent.duration.FiniteDuration;

public final class RestartSource$ {
    public static final RestartSource$ MODULE$ = new RestartSource$();

    @Deprecated
    public <T> Source<T, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Function0<Source<T, ?>> sourceFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor);
        return this.withBackoff(settings, sourceFactory);
    }

    @Deprecated
    public <T> Source<T, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Source<T, ?>> sourceFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.withBackoff(settings, sourceFactory);
    }

    public <T> Source<T, NotUsed> withBackoff(RestartSettings settings, Function0<Source<T, ?>> sourceFactory) {
        return Source$.MODULE$.fromGraph(new RestartWithBackoffSource<T>(sourceFactory, settings, false));
    }

    @Deprecated
    public <T> Source<T, NotUsed> onFailuresWithBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Function0<Source<T, ?>> sourceFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor);
        return this.onFailuresWithBackoff(settings, sourceFactory);
    }

    @Deprecated
    public <T> Source<T, NotUsed> onFailuresWithBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Source<T, ?>> sourceFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.onFailuresWithBackoff(settings, sourceFactory);
    }

    public <T> Source<T, NotUsed> onFailuresWithBackoff(RestartSettings settings, Function0<Source<T, ?>> sourceFactory) {
        return Source$.MODULE$.fromGraph(new RestartWithBackoffSource<T>(sourceFactory, settings, true));
    }

    private RestartSource$() {
    }
}

