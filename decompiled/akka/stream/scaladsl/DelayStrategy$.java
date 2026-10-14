/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.DelayStrategy;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.Duration$DurationIsOrdered$;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;

public final class DelayStrategy$ {
    public static final DelayStrategy$ MODULE$ = new DelayStrategy$();

    public DelayStrategy<Object> fixedDelay(FiniteDuration delay) {
        return new DelayStrategy<Object>(delay){
            private final FiniteDuration delay$1;

            public FiniteDuration nextDelay(Object elem) {
                return this.delay$1;
            }
            {
                this.delay$1 = delay$1;
            }
        };
    }

    public <T> DelayStrategy<T> linearIncreasingDelay(FiniteDuration increaseStep, Function1<T, Object> needsIncrease, FiniteDuration initialDelay, Duration maxDelay) {
        Predef$.MODULE$.require(increaseStep.$greater(Duration$.MODULE$.Zero()), (Function0<Object>)(Function0<String> & Serializable)() -> "Increase step must be positive");
        Predef$.MODULE$.require(maxDelay.$greater(initialDelay), (Function0<Object>)(Function0<String> & Serializable)() -> "Max delay must be bigger than initial delay");
        return new DelayStrategy<T>(initialDelay, needsIncrease, increaseStep, maxDelay){
            private FiniteDuration delay;
            private final FiniteDuration initialDelay$1;
            private final Function1 needsIncrease$1;
            private final FiniteDuration increaseStep$1;
            private final Duration maxDelay$1;

            public FiniteDuration nextDelay(T elem) {
                this.delay = BoxesRunTime.unboxToBoolean(this.needsIncrease$1.apply(elem)) ? (FiniteDuration)new $colon$colon<Nothing$>((Nothing$)((Object)this.delay.$plus(this.increaseStep$1)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)this.maxDelay$1), Nil$.MODULE$)).min(Duration$DurationIsOrdered$.MODULE$) : this.initialDelay$1;
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

    public <T> FiniteDuration linearIncreasingDelay$default$3() {
        return Duration$.MODULE$.Zero();
    }

    public <T> Duration linearIncreasingDelay$default$4() {
        return Duration$.MODULE$.Inf();
    }

    private DelayStrategy$() {
    }
}

