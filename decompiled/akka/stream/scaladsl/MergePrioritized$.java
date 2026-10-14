/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.UniformFanInShape;
import akka.stream.scaladsl.MergePrioritized;
import akka.stream.stage.GraphStage;
import scala.collection.immutable.Seq;

public final class MergePrioritized$ {
    public static final MergePrioritized$ MODULE$ = new MergePrioritized$();

    public <T> GraphStage<UniformFanInShape<T, T>> apply(Seq<Object> priorities, boolean eagerComplete) {
        return new MergePrioritized(priorities, eagerComplete);
    }

    public <T> boolean apply$default$2() {
        return false;
    }

    private MergePrioritized$() {
    }
}

