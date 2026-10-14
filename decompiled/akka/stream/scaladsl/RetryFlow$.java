/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.annotation.ApiMayChange;
import akka.stream.impl.RetryFlowCoordinator;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.BidiFlow$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.FlowWithContext;
import akka.stream.scaladsl.FlowWithContext$;
import akka.stream.scaladsl.Keep$;
import scala.Function2;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.duration.FiniteDuration;

public final class RetryFlow$ {
    public static final RetryFlow$ MODULE$ = new RetryFlow$();

    @ApiMayChange(issue="https://github.com/akka/akka/issues/27960")
    public <In, Out, Mat> Flow<In, Out, Mat> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRetries, Flow<In, Out, Mat> flow, Function2<In, Out, Option<In>> decideRetry) {
        BidiFlow retryCoordination = BidiFlow$.MODULE$.fromGraph(new RetryFlowCoordinator<In, Out>(minBackoff, maxBackoff, randomFactor, maxRetries, decideRetry));
        return Flow$.MODULE$.fromGraph(retryCoordination.joinMat(flow, Keep$.MODULE$.right()));
    }

    @ApiMayChange(issue="https://github.com/akka/akka/issues/27960")
    public <In, CtxIn, Out, CtxOut, Mat> FlowWithContext<In, CtxIn, Out, CtxOut, Mat> withBackoffAndContext(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRetries, FlowWithContext<In, CtxIn, Out, CtxOut, Mat> flow, Function2<Tuple2<In, CtxIn>, Tuple2<Out, CtxOut>, Option<Tuple2<In, CtxIn>>> decideRetry) {
        BidiFlow retryCoordination = BidiFlow$.MODULE$.fromGraph(new RetryFlowCoordinator<Tuple2<In, CtxIn>, Tuple2<Out, CtxOut>>(minBackoff, maxBackoff, randomFactor, maxRetries, decideRetry));
        return FlowWithContext$.MODULE$.fromTuples(retryCoordination.joinMat(flow, Keep$.MODULE$.right()));
    }

    private RetryFlow$() {
    }
}

