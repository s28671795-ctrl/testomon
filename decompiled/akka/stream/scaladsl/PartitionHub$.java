/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.scaladsl.PartitionHub;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source;
import java.io.Serializable;
import scala.Function0;
import scala.Function2;
import scala.runtime.BoxesRunTime;

public final class PartitionHub$ {
    public static final PartitionHub$ MODULE$ = new PartitionHub$();
    @InternalApi
    private static final int defaultBufferSize = 256;

    public int defaultBufferSize() {
        return defaultBufferSize;
    }

    public <T> Sink<T, Source<T, NotUsed>> statefulSink(Function0<Function2<PartitionHub.ConsumerInfo, T, Object>> partitioner, int startAfterNrOfConsumers, int bufferSize) {
        return Sink$.MODULE$.fromGraph(new PartitionHub<T>(partitioner, startAfterNrOfConsumers, bufferSize));
    }

    public <T> int statefulSink$default$3() {
        return this.defaultBufferSize();
    }

    public <T> Sink<T, Source<T, NotUsed>> sink(Function2<Object, T, Object> partitioner, int startAfterNrOfConsumers, int bufferSize) {
        Function2<PartitionHub.ConsumerInfo, Object, Object> & Serializable fun = (Function2<PartitionHub.ConsumerInfo, Object, Object> & Serializable)(info, elem) -> BoxesRunTime.boxToLong(PartitionHub$.$anonfun$sink$1(partitioner, info, elem));
        return this.statefulSink((Function0<Function2> & Serializable)() -> fun, startAfterNrOfConsumers, bufferSize);
    }

    public <T> int sink$default$3() {
        return this.defaultBufferSize();
    }

    public static final /* synthetic */ long $anonfun$sink$1(Function2 partitioner$1, PartitionHub.ConsumerInfo info, Object elem) {
        int idx = BoxesRunTime.unboxToInt(partitioner$1.apply(BoxesRunTime.boxToInteger(info.size()), elem));
        if (idx < 0) {
            return -1L;
        }
        return info.consumerIdByIdx(idx);
    }

    private PartitionHub$() {
    }
}

