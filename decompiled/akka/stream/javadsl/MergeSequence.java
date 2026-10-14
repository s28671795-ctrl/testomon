/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.javadsl.MergeSequence$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)<QAB\u0004\t\u000291Q\u0001E\u0004\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0002mAQAG\u0001\u0005\u0002\u0001CQAG\u0001\u0005\u00025\u000bQ\"T3sO\u0016\u001cV-];f]\u000e,'B\u0001\u0005\n\u0003\u001dQ\u0017M^1eg2T!AC\u0006\u0002\rM$(/Z1n\u0015\u0005a\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0010\u00035\tqAA\u0007NKJ<WmU3rk\u0016t7-Z\n\u0003\u0003I\u0001\"a\u0005\f\u000e\u0003QQ\u0011!F\u0001\u0006g\u000e\fG.Y\u0005\u0003/Q\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u000f\u0003\u0019\u0019'/Z1uKV\u0011AD\n\u000b\u0003;M\u0002BAH\u0010\"_5\t\u0011\"\u0003\u0002!\u0013\t)qI]1qQB!aD\t\u0013%\u0013\t\u0019\u0013BA\tV]&4wN]7GC:Len\u00155ba\u0016\u0004\"!\n\u0014\r\u0001\u0011)qe\u0001b\u0001Q\t\tA+\u0005\u0002*YA\u00111CK\u0005\u0003WQ\u0011qAT8uQ&tw\r\u0005\u0002\u0014[%\u0011a\u0006\u0006\u0002\u0004\u0003:L\bC\u0001\u00192\u001b\u0005Y\u0011B\u0001\u001a\f\u0005\u001dqu\u000e^+tK\u0012DQ\u0001N\u0002A\u0002U\nq\"\u001a=ue\u0006\u001cGoU3rk\u0016t7-\u001a\t\u0005mm\"S(D\u00018\u0015\tA\u0014(\u0001\u0005gk:\u001cG/[8o\u0015\tQ4\"\u0001\u0003kCBL\u0017B\u0001\u001f8\u0005!1UO\\2uS>t\u0007CA\n?\u0013\tyDC\u0001\u0003M_:<WCA!F)\r\u0011ei\u0013\t\u0005=}\u0019u\u0006\u0005\u0003\u001fE\u0011#\u0005CA\u0013F\t\u00159CA1\u0001)\u0011\u00159E\u00011\u0001I\u0003)Ig\u000e];u\u0007>,h\u000e\u001e\t\u0003'%K!A\u0013\u000b\u0003\u0007%sG\u000fC\u00035\t\u0001\u0007A\n\u0005\u00037w\u0011kTC\u0001(S)\u0011y5k\u001a5\u0011\tyy\u0002k\f\t\u0005=\t\n\u0016\u000b\u0005\u0002&%\u0012)q%\u0002b\u0001Q!)A+\u0002a\u0001+\u0006)1\r\\1{uB\u0019a+X)\u000f\u0005][\u0006C\u0001-\u0015\u001b\u0005I&B\u0001.\u000e\u0003\u0019a$o\\8u}%\u0011A\fF\u0001\u0007!J,G-\u001a4\n\u0005y{&!B\"mCN\u001c(B\u0001/\u0015Q\t\u0019\u0016\r\u0005\u0002cK6\t1M\u0003\u0002e\u0017\u0005!Q\u000f^5m\u0013\t17M\u0001\u0004v]V\u001cX\r\u001a\u0005\u0006\u000f\u0016\u0001\r\u0001\u0013\u0005\u0006i\u0015\u0001\r!\u001b\t\u0005mm\nV\b")
public final class MergeSequence {
    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int inputCount, Function<T, Object> extractSequence) {
        return MergeSequence$.MODULE$.create(clazz, inputCount, extractSequence);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputCount, Function<T, Object> extractSequence) {
        return MergeSequence$.MODULE$.create(inputCount, extractSequence);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Function<T, Object> extractSequence) {
        return MergeSequence$.MODULE$.create(extractSequence);
    }
}

