/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.scaladsl.SinkQueueWithCancel;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import scala.Function1;
import scala.Option;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$FutureOps$;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionForJava8$;
import scala.runtime.LambdaDeserialize;

public final class SinkQueueWithCancel$ {
    public static final SinkQueueWithCancel$ MODULE$ = new SinkQueueWithCancel$();

    public final <T> SinkQueueWithCancel<T> QueueOps(SinkQueueWithCancel<T> queue) {
        return queue;
    }

    @InternalApi
    public <T> akka.stream.javadsl.SinkQueueWithCancel<T> asJava(SinkQueueWithCancel<T> queue) {
        return new akka.stream.javadsl.SinkQueueWithCancel<T>(queue){
            private final SinkQueueWithCancel queue$2;

            public CompletionStage<Optional<T>> pull() {
                return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(this.queue$2.pull().map((Function1<Option, Optional> & Serializable)x$1 -> OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(OptionConverters$.MODULE$.RichOptionForJava8(x$1)), ExecutionContexts$.MODULE$.parasitic())));
            }

            public void cancel() {
                this.queue$2.cancel();
            }
            {
                this.queue$2 = queue$2;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$pull$1(scala.Option )}, serializedLambda);
            }
        };
    }

    private SinkQueueWithCancel$() {
    }
}

