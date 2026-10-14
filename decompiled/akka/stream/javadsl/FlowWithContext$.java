/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Flow$;
import akka.stream.javadsl.FlowWithContext;

public final class FlowWithContext$ {
    public static final FlowWithContext$ MODULE$ = new FlowWithContext$();

    public <In, Ctx> FlowWithContext<In, Ctx, In, Ctx, NotUsed> create() {
        return new FlowWithContext(Flow$.MODULE$.create());
    }

    public <In, CtxIn, Out, CtxOut, Mat> FlowWithContext<In, CtxIn, Out, CtxOut, Mat> fromPairs(Flow<Pair<In, CtxIn>, Pair<Out, CtxOut>, Mat> under) {
        return new FlowWithContext<In, CtxIn, Out, CtxOut, Mat>(under);
    }

    private FlowWithContext$() {
    }
}

