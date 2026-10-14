/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.annotation.ApiMayChange;
import akka.japi.Pair;
import akka.japi.function.Function2;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.FlowWithContext;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;
import scala.Function1;
import scala.Option;
import scala.Tuple2;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionalGeneric$;

public final class RetryFlow$ {
    public static final RetryFlow$ MODULE$ = new RetryFlow$();

    @ApiMayChange(issue="https://github.com/akka/akka/issues/27960")
    public <In, Out, Mat> Flow<In, Out, Mat> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRetries, Flow<In, Out, Mat> flow, Function2<In, Out, Optional<In>> decideRetry) {
        return akka.stream.scaladsl.RetryFlow$.MODULE$.withBackoff(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(minBackoff)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(maxBackoff)), randomFactor, maxRetries, flow.asScala(), (scala.Function2<Object, Object, Option> & Serializable)(In in, Out out) -> OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric((Optional)decideRetry.apply(in, out)))).asJava();
    }

    @ApiMayChange(issue="https://github.com/akka/akka/issues/27960")
    public <In, InCtx, Out, OutCtx, Mat> FlowWithContext<In, InCtx, Out, OutCtx, Mat> withBackoffAndContext(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRetries, FlowWithContext<In, InCtx, Out, OutCtx, Mat> flow, Function2<Pair<In, InCtx>, Pair<Out, OutCtx>, Optional<Pair<In, InCtx>>> decideRetry) {
        return akka.stream.scaladsl.RetryFlow$.MODULE$.withBackoffAndContext(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(minBackoff)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(maxBackoff)), randomFactor, maxRetries, flow.asScala(), (scala.Function2<Tuple2, Tuple2, Option> & Serializable)(Tuple2<In, CtxIn> in, Tuple2<Out, CtxOut> out) -> OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric((Optional)decideRetry.apply(new Pair(in._1(), in._2()), new Pair(out._1(), out._2())))).map((Function1<Pair, Tuple2> & Serializable)x$1 -> x$1.toScala())).asJava();
    }

    private RetryFlow$() {
    }
}

