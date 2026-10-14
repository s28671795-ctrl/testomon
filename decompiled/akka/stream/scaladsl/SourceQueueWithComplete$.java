/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.annotation.InternalApi;
import akka.stream.QueueOfferResult;
import akka.stream.scaladsl.SourceQueueWithComplete;
import java.util.concurrent.CompletionStage;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$FutureOps$;

public final class SourceQueueWithComplete$ {
    public static final SourceQueueWithComplete$ MODULE$ = new SourceQueueWithComplete$();

    public final <T> SourceQueueWithComplete<T> QueueOps(SourceQueueWithComplete<T> queue) {
        return queue;
    }

    @InternalApi
    public <T> akka.stream.javadsl.SourceQueueWithComplete<T> asJava(SourceQueueWithComplete<T> queue) {
        return new akka.stream.javadsl.SourceQueueWithComplete<T>(queue){
            private final SourceQueueWithComplete queue$1;

            public CompletionStage<QueueOfferResult> offer(T elem) {
                return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(this.queue$1.offer(elem)));
            }

            public CompletionStage<Done> watchCompletion() {
                return FutureConverters$FutureOps$.MODULE$.toJava$extension(FutureConverters$.MODULE$.FutureOps(this.queue$1.watchCompletion()));
            }

            public void complete() {
                this.queue$1.complete();
            }

            public void fail(Throwable ex) {
                this.queue$1.fail(ex);
            }
            {
                this.queue$1 = queue$1;
            }
        };
    }

    private SourceQueueWithComplete$() {
    }
}

