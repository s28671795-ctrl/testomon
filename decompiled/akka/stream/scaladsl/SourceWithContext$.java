/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.SourceWithContext;
import scala.Tuple2;

public final class SourceWithContext$ {
    public static final SourceWithContext$ MODULE$ = new SourceWithContext$();

    public <Out, CtxOut, Mat> SourceWithContext<Out, CtxOut, Mat> fromTuples(Source<Tuple2<Out, CtxOut>, Mat> source2) {
        return new SourceWithContext<Out, CtxOut, Mat>(source2);
    }

    private SourceWithContext$() {
    }
}

