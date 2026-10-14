/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.javadsl.DelayStrategy$;
import java.time.Duration;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m4qAC\u0006\u0011\u0002G\u0005!\u0003C\u0003\u001b\u0001\u0019\u00051dB\u00032\u0017!\u0005!GB\u0003\u000b\u0017!\u0005A\u0007C\u00036\u0007\u0011\u0005a\u0007\u0003\u00048\u0007\u0011\u00051\u0002\u000f\u0005\u0006\u001d\u000e!\ta\u0014\u0005\u0006-\u000e!\ta\u0016\u0005\u0006-\u000e!\tA\u001a\u0005\u0006-\u000e!\t\u0001\u001d\u0002\u000e\t\u0016d\u0017-_*ue\u0006$XmZ=\u000b\u00051i\u0011a\u00026bm\u0006$7\u000f\u001c\u0006\u0003\u001d=\taa\u001d;sK\u0006l'\"\u0001\t\u0002\t\u0005\\7.Y\u0002\u0001+\t\u0019\u0002f\u0005\u0002\u0001)A\u0011Q\u0003G\u0007\u0002-)\tq#A\u0003tG\u0006d\u0017-\u0003\u0002\u001a-\t1\u0011I\\=SK\u001a\f\u0011B\\3yi\u0012+G.Y=\u0015\u0005q!\u0003CA\u000f#\u001b\u0005q\"BA\u0010!\u0003\u0011!\u0018.\\3\u000b\u0003\u0005\nAA[1wC&\u00111E\b\u0002\t\tV\u0014\u0018\r^5p]\")Q%\u0001a\u0001M\u0005!Q\r\\3n!\t9\u0003\u0006\u0004\u0001\u0005\u000b%\u0002!\u0019\u0001\u0016\u0003\u0003Q\u000b\"a\u000b\u0018\u0011\u0005Ua\u0013BA\u0017\u0017\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!F\u0018\n\u0005A2\"aA!os\u0006iA)\u001a7bsN#(/\u0019;fOf\u0004\"aM\u0002\u000e\u0003-\u0019\"a\u0001\u000b\u0002\rqJg.\u001b;?)\u0005\u0011\u0014aB1t'\u000e\fG.Y\u000b\u0003s\r#\"A\u000f#\u0013\u0007m\"RH\u0002\u0003=\u000b\u0001Q$\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004c\u0001 B\u00056\tqH\u0003\u0002A\u001b\u0005A1oY1mC\u0012\u001cH.\u0003\u0002\u000b\u007fA\u0011qe\u0011\u0003\u0006S\u0015\u0011\rA\u000b\u0005\u0006\u000b\u0016\u0001\rAR\u0001\u000eI\u0016d\u0017-_*ue\u0006$XmZ=\u0011\u0007M\u0002!\t\u000b\u0002\u0006\u0011B\u0011\u0011\nT\u0007\u0002\u0015*\u00111jD\u0001\u000bC:tw\u000e^1uS>t\u0017BA'K\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u0015\u0019L\u00070\u001a3EK2\f\u00170\u0006\u0002Q'R\u0011\u0011\u000b\u0016\t\u0004g\u0001\u0011\u0006CA\u0014T\t\u0015IcA1\u0001+\u0011\u0015)f\u00011\u0001\u001d\u0003\u0015!W\r\\1z\u0003Ua\u0017N\\3be&s7M]3bg&tw\rR3mCf,\"\u0001W.\u0015\u0007ecf\fE\u00024\u0001i\u0003\"aJ.\u0005\u000b%:!\u0019\u0001\u0016\t\u000bu;\u0001\u0019\u0001\u000f\u0002\u0019%t7M]3bg\u0016\u001cF/\u001a9\t\u000b};\u0001\u0019\u00011\u0002\u001b9,W\rZ:J]\u000e\u0014X-Y:f!\u0011)\u0012MW2\n\u0005\t4\"!\u0003$v]\u000e$\u0018n\u001c82!\t)B-\u0003\u0002f-\t9!i\\8mK\u0006tWCA4k)\u0011A7\u000e\u001c8\u0011\u0007M\u0002\u0011\u000e\u0005\u0002(U\u0012)\u0011\u0006\u0003b\u0001U!)Q\f\u0003a\u00019!)q\f\u0003a\u0001[B!Q#Y5d\u0011\u0015y\u0007\u00021\u0001\u001d\u00031Ig.\u001b;jC2$U\r\\1z+\t\tH\u000fF\u0003skZD\u0018\u0010E\u00024\u0001M\u0004\"a\n;\u0005\u000b%J!\u0019\u0001\u0016\t\u000buK\u0001\u0019\u0001\u000f\t\u000b}K\u0001\u0019A<\u0011\tU\t7o\u0019\u0005\u0006_&\u0001\r\u0001\b\u0005\u0006u&\u0001\r\u0001H\u0001\t[\u0006DH)\u001a7bs\u0002")
public interface DelayStrategy<T> {
    public static <T> DelayStrategy<T> linearIncreasingDelay(Duration increaseStep, Function1<T, Object> needsIncrease, Duration initialDelay, Duration maxDelay) {
        return DelayStrategy$.MODULE$.linearIncreasingDelay(increaseStep, needsIncrease, initialDelay, maxDelay);
    }

    public static <T> DelayStrategy<T> linearIncreasingDelay(Duration increaseStep, Function1<T, Object> needsIncrease, Duration initialDelay) {
        return DelayStrategy$.MODULE$.linearIncreasingDelay(increaseStep, needsIncrease, initialDelay);
    }

    public static <T> DelayStrategy<T> linearIncreasingDelay(Duration increaseStep, Function1<T, Object> needsIncrease) {
        return DelayStrategy$.MODULE$.linearIncreasingDelay(increaseStep, needsIncrease);
    }

    public static <T> DelayStrategy<T> fixedDelay(Duration delay) {
        return DelayStrategy$.MODULE$.fixedDelay(delay);
    }

    public Duration nextDelay(T var1);
}

