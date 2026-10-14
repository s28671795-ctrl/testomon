/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.SubstreamCancelStrategy;
import akka.stream.impl.fusing.Split;
import akka.stream.impl.fusing.Split$SplitAfter$;
import akka.stream.impl.fusing.Split$SplitBefore$;
import akka.stream.scaladsl.Source;
import scala.Function1;

@InternalApi
public final class Split$ {
    public static final Split$ MODULE$ = new Split$();

    public <T> Graph<FlowShape<T, Source<T, NotUsed>>, NotUsed> when(Function1<T, Object> p, SubstreamCancelStrategy substreamCancelStrategy) {
        return new Split<T>(Split$SplitBefore$.MODULE$, p, substreamCancelStrategy);
    }

    public <T> Graph<FlowShape<T, Source<T, NotUsed>>, NotUsed> after(Function1<T, Object> p, SubstreamCancelStrategy substreamCancelStrategy) {
        return new Split<T>(Split$SplitAfter$.MODULE$, p, substreamCancelStrategy);
    }

    private Split$() {
    }
}

