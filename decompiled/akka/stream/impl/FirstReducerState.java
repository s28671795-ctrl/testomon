/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.MutableReducerState;
import akka.stream.impl.ReducerState;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00153Q!\u0002\u0004\u0003\u00151A\u0001b\n\u0001\u0003\u0002\u0003\u0006I\u0001\u000b\u0005\u0006i\u0001!\t!\u000e\u0005\u0006q\u0001!\t!\u000f\u0005\u0006y\u0001!\t!\u0010\u0002\u0012\r&\u00148\u000f\u001e*fIV\u001cWM]*uCR,'BA\u0004\t\u0003\u0011IW\u000e\u001d7\u000b\u0005%Q\u0011AB:ue\u0016\fWNC\u0001\f\u0003\u0011\t7n[1\u0016\u00075QReE\u0002\u0001\u001dQ\u0001\"a\u0004\n\u000e\u0003AQ\u0011!E\u0001\u0006g\u000e\fG.Y\u0005\u0003'A\u0011a!\u00118z%\u00164\u0007\u0003B\u000b\u00171\u0011j\u0011AB\u0005\u0003/\u0019\u0011ABU3ek\u000e,'o\u0015;bi\u0016\u0004\"!\u0007\u000e\r\u0001\u0011)1\u0004\u0001b\u0001;\t\tAk\u0001\u0001\u0012\u0005y\t\u0003CA\b \u0013\t\u0001\u0003CA\u0004O_RD\u0017N\\4\u0011\u0005=\u0011\u0013BA\u0012\u0011\u0005\r\te.\u001f\t\u00033\u0015\"QA\n\u0001C\u0002u\u0011\u0011AU\u0001\u0011G>dG.Z2u_J4\u0015m\u0019;pef\u00042aD\u0015,\u0013\tQ\u0003CA\u0005Gk:\u001cG/[8oaA)AF\r\r\"I5\tQF\u0003\u0002\n])\u0011q\u0006M\u0001\u0005kRLGNC\u00012\u0003\u0011Q\u0017M^1\n\u0005Mj#!C\"pY2,7\r^8s\u0003\u0019a\u0014N\\5u}Q\u0011ag\u000e\t\u0005+\u0001AB\u0005C\u0003(\u0005\u0001\u0007\u0001&\u0001\u0004va\u0012\fG/\u001a\u000b\u0003)iBQaO\u0002A\u0002\u0005\nQAY1uG\"\faAZ5oSNDG#\u0001\u0013)\u0005\u0001y\u0004C\u0001!D\u001b\u0005\t%B\u0001\"\u000b\u0003)\tgN\\8uCRLwN\\\u0005\u0003\t\u0006\u00131\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public final class FirstReducerState<T, R>
implements ReducerState<T, R> {
    private final Function0<Collector<T, Object, R>> collectorFactory;

    @Override
    public ReducerState<T, R> update(Object batch) {
        Collector<T, Object, R> collector = this.collectorFactory.apply();
        BinaryOperator<Object> combiner = collector.combiner();
        return new MutableReducerState<T, R>(collector, combiner, batch);
    }

    @Override
    public R finish() {
        Collector<T, Object, R> collector = this.collectorFactory.apply();
        return collector.finisher().apply(null);
    }

    public FirstReducerState(Function0<Collector<T, Object, R>> collectorFactory) {
        this.collectorFactory = collectorFactory;
    }
}

