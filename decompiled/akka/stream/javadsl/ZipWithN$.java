/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.List;
import scala.Function1;
import scala.collection.immutable.Seq;

public final class ZipWithN$ {
    public static final ZipWithN$ MODULE$ = new ZipWithN$();

    public <A, O> Graph<UniformFanInShape<A, O>, NotUsed> create(Function<List<A>, O> zipper, int n) {
        return akka.stream.scaladsl.ZipWithN$.MODULE$.apply((Function1<Seq, Object> & Serializable)seq -> zipper.apply(package$JavaConverters$.MODULE$.SeqHasAsJava(seq).asJava()), n);
    }

    private ZipWithN$() {
    }
}

