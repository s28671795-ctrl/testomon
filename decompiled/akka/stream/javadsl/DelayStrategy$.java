/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.annotation.InternalApi;
import akka.stream.javadsl.DelayStrategy;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.Serializable;
import java.time.Duration;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxesRunTime;

public final class DelayStrategy$ {
    public static final DelayStrategy$ MODULE$ = new DelayStrategy$();

    @InternalApi
    public <T> akka.stream.scaladsl.DelayStrategy<T> asScala(DelayStrategy<T> delayStrategy) {
        return new akka.stream.scaladsl.DelayStrategy<T>(delayStrategy){
            private final DelayStrategy delayStrategy$1;

            public FiniteDuration nextDelay(T elem) {
                return JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(this.delayStrategy$1.nextDelay(elem)));
            }
            {
                this.delayStrategy$1 = delayStrategy$1;
            }
        };
    }

    public <T> DelayStrategy<T> fixedDelay(Duration delay) {
        return new DelayStrategy<T>(delay){
            private final Duration delay$1;

            public Duration nextDelay(T elem) {
                return this.delay$1;
            }
            {
                this.delay$1 = delay$1;
            }
        };
    }

    public <T> DelayStrategy<T> linearIncreasingDelay(Duration increaseStep, Function1<T, Object> needsIncrease) {
        return this.linearIncreasingDelay(increaseStep, needsIncrease, Duration.ZERO);
    }

    public <T> DelayStrategy<T> linearIncreasingDelay(Duration increaseStep, Function1<T, Object> needsIncrease, Duration initialDelay) {
        return this.linearIncreasingDelay(increaseStep, needsIncrease, initialDelay, Duration.ofNanos(Long.MAX_VALUE));
    }

    public <T> DelayStrategy<T> linearIncreasingDelay(Duration increaseStep, Function1<T, Object> needsIncrease, Duration initialDelay, Duration maxDelay) {
        Predef$.MODULE$.require(increaseStep.compareTo(Duration.ZERO) > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Increase step must be positive");
        Predef$.MODULE$.require(maxDelay.compareTo(initialDelay) >= 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Initial delay may not exceed max delay");
        return new DelayStrategy<T>(initialDelay, needsIncrease, increaseStep, maxDelay){
            private Duration delay;
            private final Duration initialDelay$1;
            private final Function1 needsIncrease$1;
            private final Duration increaseStep$1;
            private final Duration maxDelay$1;

            public Duration nextDelay(T elem) {
                Duration next;
                this.delay = BoxesRunTime.unboxToBoolean(this.needsIncrease$1.apply(elem)) ? ((next = this.delay.plus(this.increaseStep$1)).compareTo(this.maxDelay$1) < 0 ? next : this.maxDelay$1) : this.initialDelay$1;
                return this.delay;
            }
            {
                this.initialDelay$1 = initialDelay$1;
                this.needsIncrease$1 = needsIncrease$1;
                this.increaseStep$1 = increaseStep$1;
                this.maxDelay$1 = maxDelay$1;
                this.delay = initialDelay$1;
            }
        };
    }

    private DelayStrategy$() {
    }
}

