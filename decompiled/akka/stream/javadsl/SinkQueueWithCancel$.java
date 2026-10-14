/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.dispatch.ExecutionContexts$;
import akka.stream.javadsl.SinkQueueWithCancel;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.Optional;
import scala.Function1;
import scala.Option;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionalGeneric$;
import scala.concurrent.Future;
import scala.runtime.LambdaDeserialize;

public final class SinkQueueWithCancel$ {
    public static final SinkQueueWithCancel$ MODULE$ = new SinkQueueWithCancel$();

    public <T> akka.stream.scaladsl.SinkQueueWithCancel<T> asScala(SinkQueueWithCancel<T> queue) {
        return new akka.stream.scaladsl.SinkQueueWithCancel<T>(queue){
            private final SinkQueueWithCancel queue$2;

            public Future<Option<T>> pull() {
                return FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(this.queue$2.pull())).map((Function1<Optional, Option> & Serializable)x$1 -> OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(OptionConverters$.MODULE$.RichOptionalGeneric(x$1)), ExecutionContexts$.MODULE$.parasitic());
            }

            public void cancel() {
                this.queue$2.cancel();
            }
            {
                this.queue$2 = queue$2;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$pull$1(java.util.Optional )}, serializedLambda);
            }
        };
    }

    private SinkQueueWithCancel$() {
    }
}

