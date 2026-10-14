/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.Done;
import akka.stream.QueueOfferResult;
import akka.stream.javadsl.SourceQueueWithComplete;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.concurrent.Future;

public final class SourceQueueWithComplete$ {
    public static final SourceQueueWithComplete$ MODULE$ = new SourceQueueWithComplete$();

    public <T> akka.stream.scaladsl.SourceQueueWithComplete<T> asScala(SourceQueueWithComplete<T> queue) {
        return new akka.stream.scaladsl.SourceQueueWithComplete<T>(queue){
            private final SourceQueueWithComplete queue$1;

            public Future<QueueOfferResult> offer(T elem) {
                return FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(this.queue$1.offer(elem)));
            }

            public Future<Done> watchCompletion() {
                return FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps(this.queue$1.watchCompletion()));
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

