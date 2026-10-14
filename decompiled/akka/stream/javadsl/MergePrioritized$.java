/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import scala.Predef$;
import scala.collection.ArrayOps$;

public final class MergePrioritized$ {
    public static final MergePrioritized$ MODULE$ = new MergePrioritized$();

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int[] priorities) {
        return akka.stream.scaladsl.MergePrioritized$.MODULE$.apply(ArrayOps$.MODULE$.toIndexedSeq$extension(Predef$.MODULE$.intArrayOps(priorities)), akka.stream.scaladsl.MergePrioritized$.MODULE$.apply$default$2());
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int[] priorities) {
        return this.create(priorities);
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int[] priorities, boolean eagerComplete) {
        return akka.stream.scaladsl.MergePrioritized$.MODULE$.apply(ArrayOps$.MODULE$.toIndexedSeq$extension(Predef$.MODULE$.intArrayOps(priorities)), eagerComplete);
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int[] priorities, boolean eagerComplete) {
        return this.create(priorities, eagerComplete);
    }

    private MergePrioritized$() {
    }
}

