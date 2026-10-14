/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.javadsl.ZipWithN$;
import akka.util.ConstantFun$;
import java.util.List;

public final class ZipN$ {
    public static final ZipN$ MODULE$ = new ZipN$();

    public <A> Graph<UniformFanInShape<A, List<A>>, NotUsed> create(int n) {
        return ZipWithN$.MODULE$.create(ConstantFun$.MODULE$.javaIdentityFunction(), n);
    }

    private ZipN$() {
    }
}

