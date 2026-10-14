/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.ReducerState;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}3Qa\u0003\u0007\u0003!IA\u0001\"\f\u0001\u0003\u0006\u0004%\tA\f\u0005\tq\u0001\u0011\t\u0011)A\u0005_!A\u0011\b\u0001BC\u0002\u0013\u0005!\b\u0003\u0005B\u0001\t\u0005\t\u0015!\u0003<\u0011!\u0011\u0005A!a\u0001\n\u0003\u0019\u0005\u0002\u0003#\u0001\u0005\u0003\u0007I\u0011A#\t\u0011-\u0003!\u0011!Q!\n\u001dBQ\u0001\u0014\u0001\u0005\u00025CQA\u0015\u0001\u0005\u0002MCQA\u0016\u0001\u0005\u0002]\u00131#T;uC\ndWMU3ek\u000e,'o\u0015;bi\u0016T!!\u0004\b\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u001fA\taa\u001d;sK\u0006l'\"A\t\u0002\t\u0005\\7.Y\u000b\u0004'\u0001Z3c\u0001\u0001\u00155A\u0011Q\u0003G\u0007\u0002-)\tq#A\u0003tG\u0006d\u0017-\u0003\u0002\u001a-\t1\u0011I\\=SK\u001a\u0004Ba\u0007\u000f\u001fU5\tA\"\u0003\u0002\u001e\u0019\ta!+\u001a3vG\u0016\u00148\u000b^1uKB\u0011q\u0004\t\u0007\u0001\t\u0015\t\u0003A1\u0001$\u0005\u0005!6\u0001A\t\u0003I\u001d\u0002\"!F\u0013\n\u0005\u00192\"a\u0002(pi\"Lgn\u001a\t\u0003+!J!!\u000b\f\u0003\u0007\u0005s\u0017\u0010\u0005\u0002 W\u0011)A\u0006\u0001b\u0001G\t\t!+A\u0005d_2dWm\u0019;peV\tq\u0006E\u00031my9#&D\u00012\u0015\ty!G\u0003\u00024i\u0005!Q\u000f^5m\u0015\u0005)\u0014\u0001\u00026bm\u0006L!aN\u0019\u0003\u0013\r{G\u000e\\3di>\u0014\u0018AC2pY2,7\r^8sA\u0005A1m\\7cS:,'/F\u0001<!\rathJ\u0007\u0002{)\u0011aHM\u0001\tMVt7\r^5p]&\u0011\u0001)\u0010\u0002\u000f\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s\u0003%\u0019w.\u001c2j]\u0016\u0014\b%A\u0004sK\u0012,8-\u001a3\u0016\u0003\u001d\n1B]3ek\u000e,Gm\u0018\u0013fcR\u0011a)\u0013\t\u0003+\u001dK!\u0001\u0013\f\u0003\tUs\u0017\u000e\u001e\u0005\b\u0015\u001a\t\t\u00111\u0001(\u0003\rAH%M\u0001\te\u0016$WoY3eA\u00051A(\u001b8jiz\"BAT(Q#B!1\u0004\u0001\u0010+\u0011\u0015i\u0003\u00021\u00010\u0011\u0015I\u0004\u00021\u0001<\u0011\u0015\u0011\u0005\u00021\u0001(\u0003\u0019)\b\u000fZ1uKR\u0011!\u0004\u0016\u0005\u0006+&\u0001\raJ\u0001\u0006E\u0006$8\r[\u0001\u0007M&t\u0017n\u001d5\u0015\u0003)B#\u0001A-\u0011\u0005ikV\"A.\u000b\u0005q\u0003\u0012AC1o]>$\u0018\r^5p]&\u0011al\u0017\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public final class MutableReducerState<T, R>
implements ReducerState<T, R> {
    private final Collector<T, Object, R> collector;
    private final BinaryOperator<Object> combiner;
    private Object reduced;

    public Collector<T, Object, R> collector() {
        return this.collector;
    }

    public BinaryOperator<Object> combiner() {
        return this.combiner;
    }

    public Object reduced() {
        return this.reduced;
    }

    public void reduced_$eq(Object x$1) {
        this.reduced = x$1;
    }

    @Override
    public ReducerState<T, R> update(Object batch) {
        this.reduced_$eq(this.combiner().apply(this.reduced(), batch));
        return this;
    }

    @Override
    public R finish() {
        return this.collector().finisher().apply(this.reduced());
    }

    public MutableReducerState(Collector<T, Object, R> collector, BinaryOperator<Object> combiner, Object reduced) {
        this.collector = collector;
        this.combiner = combiner;
        this.reduced = reduced;
    }
}

