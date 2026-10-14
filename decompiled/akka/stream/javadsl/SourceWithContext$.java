/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.japi.Pair;
import akka.stream.javadsl.Source;
import akka.stream.javadsl.SourceWithContext;
import java.io.Serializable;
import scala.Function1;
import scala.Tuple2;

public final class SourceWithContext$ {
    public static final SourceWithContext$ MODULE$ = new SourceWithContext$();

    public <Out, CtxOut, Mat> SourceWithContext<Out, CtxOut, Mat> fromPairs(Source<Pair<Out, CtxOut>, Mat> under) {
        return new SourceWithContext(akka.stream.scaladsl.SourceWithContext$.MODULE$.fromTuples((akka.stream.scaladsl.Source)under.asScala().map((Function1<Pair, Tuple2> & Serializable)x$1 -> x$1.toScala())));
    }

    private SourceWithContext$() {
    }
}

