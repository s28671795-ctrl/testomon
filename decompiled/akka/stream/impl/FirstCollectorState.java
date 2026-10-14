/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.CollectorState;
import akka.stream.impl.MutableCollectorState;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!3QAB\u0004\u0003\u00175A\u0001\u0002\u000b\u0001\u0003\u0002\u0003\u0006I!\u000b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006s\u0001!\tE\u000f\u0005\u0006{\u0001!\tE\u0010\u0005\u0006\u007f\u0001!\t\u0005\u0011\u0002\u0014\r&\u00148\u000f^\"pY2,7\r^8s'R\fG/\u001a\u0006\u0003\u0011%\tA![7qY*\u0011!bC\u0001\u0007gR\u0014X-Y7\u000b\u00031\tA!Y6lCV\u0019ab\u0007\u0014\u0014\u0007\u0001yQ\u0003\u0005\u0002\u0011'5\t\u0011CC\u0001\u0013\u0003\u0015\u00198-\u00197b\u0013\t!\u0012C\u0001\u0004B]f\u0014VM\u001a\t\u0005-]IR%D\u0001\b\u0013\tArA\u0001\bD_2dWm\u0019;peN#\u0018\r^3\u0011\u0005iYB\u0002\u0001\u0003\u00069\u0001\u0011\rA\b\u0002\u0002)\u000e\u0001\u0011CA\u0010#!\t\u0001\u0002%\u0003\u0002\"#\t9aj\u001c;iS:<\u0007C\u0001\t$\u0013\t!\u0013CA\u0002B]f\u0004\"A\u0007\u0014\u0005\u000b\u001d\u0002!\u0019\u0001\u0010\u0003\u0003I\u000b\u0001cY8mY\u0016\u001cGo\u001c:GC\u000e$xN]=\u0011\u0007AQC&\u0003\u0002,#\tIa)\u001e8di&|g\u000e\r\t\u0006[MJ\"%J\u0007\u0002])\u0011!b\f\u0006\u0003aE\nA!\u001e;jY*\t!'\u0001\u0003kCZ\f\u0017B\u0001\u001b/\u0005%\u0019u\u000e\u001c7fGR|'/\u0001\u0004=S:LGO\u0010\u000b\u0003oa\u0002BA\u0006\u0001\u001aK!)\u0001F\u0001a\u0001S\u00051Q\u000f\u001d3bi\u0016$\"!F\u001e\t\u000bq\u001a\u0001\u0019A\r\u0002\t\u0015dW-\\\u0001\fC\u000e\u001cW/\\;mCR,G\rF\u0001#\u0003\u00191\u0017N\\5tQR\tQ\u0005\u000b\u0002\u0001\u0005B\u00111IR\u0007\u0002\t*\u0011QiC\u0001\u000bC:tw\u000e^1uS>t\u0017BA$E\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class FirstCollectorState<T, R>
implements CollectorState<T, R> {
    private final Function0<Collector<T, Object, R>> collectorFactory;

    @Override
    public CollectorState<T, R> update(T elem) {
        Collector<T, Object, R> collector = this.collectorFactory.apply();
        BiConsumer<Object, Object> accumulator = collector.accumulator();
        Object accumulated = collector.supplier().get();
        accumulator.accept(accumulated, elem);
        return new MutableCollectorState<T, R>(collector, accumulator, accumulated);
    }

    @Override
    public Object accumulated() {
        Collector<T, Object, R> collector = this.collectorFactory.apply();
        return collector.supplier().get();
    }

    @Override
    public R finish() {
        Collector<T, Object, R> collector = this.collectorFactory.apply();
        return collector.finisher().apply(collector.supplier().get());
    }

    public FirstCollectorState(Function0<Collector<T, Object, R>> collectorFactory) {
        this.collectorFactory = collectorFactory;
    }
}

