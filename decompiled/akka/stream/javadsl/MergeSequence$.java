/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import java.io.Serializable;
import scala.Function1;
import scala.runtime.BoxesRunTime;

public final class MergeSequence$ {
    public static final MergeSequence$ MODULE$ = new MergeSequence$();

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Function<T, Object> extractSequence) {
        return akka.stream.scaladsl.MergeSequence$.MODULE$.apply(akka.stream.scaladsl.MergeSequence$.MODULE$.apply$default$1(), (Function1<Object, Object> & Serializable)param2 -> BoxesRunTime.boxToLong(MergeSequence$.$anonfun$create$6(extractSequence, param2)));
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputCount, Function<T, Object> extractSequence) {
        return akka.stream.scaladsl.MergeSequence$.MODULE$.apply(inputCount, (Function1<Object, Object> & Serializable)param2 -> BoxesRunTime.boxToLong(MergeSequence$.$anonfun$create$7(extractSequence, param2)));
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int inputCount, Function<T, Object> extractSequence) {
        return this.create(inputCount, extractSequence);
    }

    public static final /* synthetic */ long $anonfun$create$6(Function extractSequence$1, Object param2) {
        return BoxesRunTime.unboxToLong(extractSequence$1.apply(param2));
    }

    public static final /* synthetic */ long $anonfun$create$7(Function extractSequence$2, Object param2) {
        return BoxesRunTime.unboxToLong(extractSequence$2.apply(param2));
    }

    private MergeSequence$() {
    }
}

