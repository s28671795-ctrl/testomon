/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Creator;
import akka.stream.RestartSettings;
import akka.stream.RestartSettings$;
import akka.stream.javadsl.Source;
import java.io.Serializable;
import java.time.Duration;
import scala.Function0;

public final class RestartSource$ {
    public static final RestartSource$ MODULE$ = new RestartSource$();

    @Deprecated
    public <T> Source<T, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, Creator<Source<T, ?>> sourceFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor);
        return this.withBackoff(settings, sourceFactory);
    }

    @Deprecated
    public <T> Source<T, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Source<T, ?>> sourceFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.withBackoff(settings, sourceFactory);
    }

    public <T> Source<T, NotUsed> withBackoff(RestartSettings settings, Creator<Source<T, ?>> sourceFactory) {
        return akka.stream.scaladsl.RestartSource$.MODULE$.withBackoff(settings, (Function0<akka.stream.scaladsl.Source> & Serializable)() -> ((Source)sourceFactory.create()).asScala()).asJava();
    }

    @Deprecated
    public <T> Source<T, NotUsed> onFailuresWithBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, Creator<Source<T, ?>> sourceFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor);
        return this.onFailuresWithBackoff(settings, sourceFactory);
    }

    @Deprecated
    public <T> Source<T, NotUsed> onFailuresWithBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Source<T, ?>> sourceFactory) {
        RestartSettings settings = RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor).withMaxRestarts(maxRestarts, minBackoff);
        return this.onFailuresWithBackoff(settings, sourceFactory);
    }

    public <T> Source<T, NotUsed> onFailuresWithBackoff(RestartSettings settings, Creator<Source<T, ?>> sourceFactory) {
        return akka.stream.scaladsl.RestartSource$.MODULE$.onFailuresWithBackoff(settings, (Function0<akka.stream.scaladsl.Source> & Serializable)() -> ((Source)sourceFactory.create()).asScala()).asJava();
    }

    private RestartSource$() {
    }
}

