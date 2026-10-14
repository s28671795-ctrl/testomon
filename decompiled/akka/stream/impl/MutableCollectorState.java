/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.CollectorState;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E3Q\u0001C\u0005\u0003\u001b=A\u0001B\u000b\u0001\u0003\u0002\u0003\u0006Ia\u000b\u0005\ti\u0001\u0011\t\u0011)A\u0005k!A1\b\u0001B\u0001B\u0003%A\u0005C\u0003=\u0001\u0011\u0005Q\bC\u0003C\u0001\u0011\u00053\tC\u0003E\u0001\u0011\u0005S\tC\u0003I\u0001\u0011\u0005\u0013JA\u000bNkR\f'\r\\3D_2dWm\u0019;peN#\u0018\r^3\u000b\u0005)Y\u0011\u0001B5na2T!\u0001D\u0007\u0002\rM$(/Z1n\u0015\u0005q\u0011\u0001B1lW\u0006,2\u0001E\u000f)'\r\u0001\u0011c\u0006\t\u0003%Ui\u0011a\u0005\u0006\u0002)\u0005)1oY1mC&\u0011ac\u0005\u0002\u0007\u0003:L(+\u001a4\u0011\taI2dJ\u0007\u0002\u0013%\u0011!$\u0003\u0002\u000f\u0007>dG.Z2u_J\u001cF/\u0019;f!\taR\u0004\u0004\u0001\u0005\u000by\u0001!\u0019\u0001\u0011\u0003\u0003Q\u001b\u0001!\u0005\u0002\"IA\u0011!CI\u0005\u0003GM\u0011qAT8uQ&tw\r\u0005\u0002\u0013K%\u0011ae\u0005\u0002\u0004\u0003:L\bC\u0001\u000f)\t\u0015I\u0003A1\u0001!\u0005\u0005\u0011\u0016!C2pY2,7\r^8s!\u0015a#g\u0007\u0013(\u001b\u0005i#B\u0001\u0007/\u0015\ty\u0003'\u0001\u0003vi&d'\"A\u0019\u0002\t)\fg/Y\u0005\u0003g5\u0012\u0011bQ8mY\u0016\u001cGo\u001c:\u0002\u0017\u0005\u001c7-^7vY\u0006$xN\u001d\t\u0005me\"3$D\u00018\u0015\tAd&\u0001\u0005gk:\u001cG/[8o\u0013\tQtG\u0001\u0006CS\u000e{gn];nKJ\fAbX1dGVlW\u000f\\1uK\u0012\fa\u0001P5oSRtD\u0003\u0002 @\u0001\u0006\u0003B\u0001\u0007\u0001\u001cO!)!\u0006\u0002a\u0001W!)A\u0007\u0002a\u0001k!)1\b\u0002a\u0001I\u0005Y\u0011mY2v[Vd\u0017\r^3e)\u0005!\u0013AB;qI\u0006$X\r\u0006\u0002\u0018\r\")qI\u0002a\u00017\u0005!Q\r\\3n\u0003\u00191\u0017N\\5tQR\tq\u0005\u000b\u0002\u0001\u0017B\u0011AjT\u0007\u0002\u001b*\u0011a*D\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001)N\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class MutableCollectorState<T, R>
implements CollectorState<T, R> {
    private final Collector<T, Object, R> collector;
    private final BiConsumer<Object, T> accumulator;
    private final Object _accumulated;

    @Override
    public Object accumulated() {
        return this._accumulated;
    }

    @Override
    public CollectorState<T, R> update(T elem) {
        this.accumulator.accept(this._accumulated, elem);
        return this;
    }

    @Override
    public R finish() {
        return this.collector.finisher().apply(this._accumulated);
    }

    public MutableCollectorState(Collector<T, Object, R> collector, BiConsumer<Object, T> accumulator, Object _accumulated) {
        this.collector = collector;
        this.accumulator = accumulator;
        this._accumulated = _accumulated;
    }
}

