/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.ZipWithN;
import scala.Function1;
import scala.collection.immutable.Seq;

public final class ZipWithN$ {
    public static final ZipWithN$ MODULE$ = new ZipWithN$();

    public <A, O> ZipWithN<A, O> apply(Function1<Seq<A>, O> zipper, int n) {
        return new ZipWithN<A, O>(zipper, n);
    }

    private ZipWithN$() {
    }
}

