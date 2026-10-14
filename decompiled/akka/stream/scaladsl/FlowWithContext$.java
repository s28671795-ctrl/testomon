/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.FlowWithContext;
import scala.Tuple2;

public final class FlowWithContext$ {
    public static final FlowWithContext$ MODULE$ = new FlowWithContext$();

    public <In, Ctx> FlowWithContext<In, Ctx, In, Ctx, NotUsed> apply() {
        Flow under = Flow$.MODULE$.apply();
        return new FlowWithContext(under);
    }

    public <In, CtxIn, Out, CtxOut, Mat> FlowWithContext<In, CtxIn, Out, CtxOut, Mat> fromTuples(Flow<Tuple2<In, CtxIn>, Tuple2<Out, CtxOut>, Mat> flow) {
        return new FlowWithContext<In, CtxIn, Out, CtxOut, Mat>(flow);
    }

    private FlowWithContext$() {
    }
}

