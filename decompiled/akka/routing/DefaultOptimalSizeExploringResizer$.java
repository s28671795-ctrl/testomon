/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.DefaultOptimalSizeExploringResizer;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple10;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.runtime.AbstractFunction10;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class DefaultOptimalSizeExploringResizer$
extends AbstractFunction10<Object, Object, Object, Duration, Object, Object, Object, Duration, Object, Object, DefaultOptimalSizeExploringResizer>
implements Serializable {
    public static final DefaultOptimalSizeExploringResizer$ MODULE$ = new DefaultOptimalSizeExploringResizer$();

    public int $lessinit$greater$default$1() {
        return 1;
    }

    public int $lessinit$greater$default$2() {
        return 30;
    }

    public double $lessinit$greater$default$3() {
        return 0.2;
    }

    public Duration $lessinit$greater$default$4() {
        return new package.DurationInt(package$.MODULE$.DurationInt(5)).seconds();
    }

    public int $lessinit$greater$default$5() {
        return 16;
    }

    public double $lessinit$greater$default$6() {
        return 0.1;
    }

    public double $lessinit$greater$default$7() {
        return 0.8;
    }

    public Duration $lessinit$greater$default$8() {
        return new package.DurationInt(package$.MODULE$.DurationInt(72)).hours();
    }

    public double $lessinit$greater$default$9() {
        return 0.4;
    }

    public double $lessinit$greater$default$10() {
        return 0.5;
    }

    @Override
    public final String toString() {
        return "DefaultOptimalSizeExploringResizer";
    }

    @Override
    public DefaultOptimalSizeExploringResizer apply(int lowerBound, int upperBound, double chanceOfScalingDownWhenFull, Duration actionInterval, int numOfAdjacentSizesToConsiderDuringOptimization, double exploreStepSize, double downsizeRatio, Duration downsizeAfterUnderutilizedFor, double explorationProbability, double weightOfLatestMetric) {
        return new DefaultOptimalSizeExploringResizer(lowerBound, upperBound, chanceOfScalingDownWhenFull, actionInterval, numOfAdjacentSizesToConsiderDuringOptimization, exploreStepSize, downsizeRatio, downsizeAfterUnderutilizedFor, explorationProbability, weightOfLatestMetric);
    }

    public int apply$default$1() {
        return 1;
    }

    public double apply$default$10() {
        return 0.5;
    }

    public int apply$default$2() {
        return 30;
    }

    public double apply$default$3() {
        return 0.2;
    }

    public Duration apply$default$4() {
        return new package.DurationInt(package$.MODULE$.DurationInt(5)).seconds();
    }

    public int apply$default$5() {
        return 16;
    }

    public double apply$default$6() {
        return 0.1;
    }

    public double apply$default$7() {
        return 0.8;
    }

    public Duration apply$default$8() {
        return new package.DurationInt(package$.MODULE$.DurationInt(72)).hours();
    }

    public double apply$default$9() {
        return 0.4;
    }

    public Option<Tuple10<Object, Object, Object, Duration, Object, Object, Object, Duration, Object, Object>> unapply(DefaultOptimalSizeExploringResizer x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple10<Object, Object, Object, Duration, Object, Object, Object, Duration, Object, Object>>(new Tuple10<Integer, Integer, Double, Duration, Integer, Double, Double, Duration, Double, Double>(BoxesRunTime.boxToInteger(x$0.lowerBound()), BoxesRunTime.boxToInteger(x$0.upperBound()), BoxesRunTime.boxToDouble(x$0.chanceOfScalingDownWhenFull()), x$0.actionInterval(), BoxesRunTime.boxToInteger(x$0.numOfAdjacentSizesToConsiderDuringOptimization()), BoxesRunTime.boxToDouble(x$0.exploreStepSize()), BoxesRunTime.boxToDouble(x$0.downsizeRatio()), x$0.downsizeAfterUnderutilizedFor(), BoxesRunTime.boxToDouble(x$0.explorationProbability()), BoxesRunTime.boxToDouble(x$0.weightOfLatestMetric())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DefaultOptimalSizeExploringResizer$.class);
    }

    private DefaultOptimalSizeExploringResizer$() {
    }
}

