/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.DelayStrategy$;
import scala.Function1;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00154q!\u0003\u0006\u0011\u0002G\u0005\u0011\u0003C\u0003\u001a\u0001\u0019\u0005!dB\u00031\u0015!\u0005\u0011GB\u0003\n\u0015!\u00051\u0007C\u00035\u0007\u0011\u0005Q\u0007C\u00037\u0007\u0011\u0005q\u0007C\u0003<\u0007\u0011\u0005A\bC\u0004S\u0007E\u0005I\u0011A*\t\u000f\u0001\u001c\u0011\u0013!C\u0001C\niA)\u001a7bsN#(/\u0019;fOfT!a\u0003\u0007\u0002\u0011M\u001c\u0017\r\\1eg2T!!\u0004\b\u0002\rM$(/Z1n\u0015\u0005y\u0011\u0001B1lW\u0006\u001c\u0001!\u0006\u0002\u0013OM\u0011\u0001a\u0005\t\u0003)]i\u0011!\u0006\u0006\u0002-\u0005)1oY1mC&\u0011\u0001$\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\u00139,\u0007\u0010\u001e#fY\u0006LHCA\u000e$!\ta\u0012%D\u0001\u001e\u0015\tqr$\u0001\u0005ekJ\fG/[8o\u0015\t\u0001S#\u0001\u0006d_:\u001cWO\u001d:f]RL!AI\u000f\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\")A%\u0001a\u0001K\u0005!Q\r\\3n!\t1s\u0005\u0004\u0001\u0005\r!\u0002\u0001R1\u0001*\u0005\u0005!\u0016C\u0001\u0016.!\t!2&\u0003\u0002-+\t9aj\u001c;iS:<\u0007C\u0001\u000b/\u0013\tySCA\u0002B]f\fQ\u0002R3mCf\u001cFO]1uK\u001eL\bC\u0001\u001a\u0004\u001b\u0005Q1CA\u0002\u0014\u0003\u0019a\u0014N\\5u}Q\t\u0011'\u0001\u0006gSb,G\rR3mCf$\"\u0001O\u001d\u0011\u0007I\u0002Q\u0006C\u0003;\u000b\u0001\u00071$A\u0003eK2\f\u00170A\u000bmS:,\u0017M]%oGJ,\u0017m]5oO\u0012+G.Y=\u0016\u0005u\u0002E#\u0002 B\u0007.k\u0005c\u0001\u001a\u0001\u007fA\u0011a\u0005\u0011\u0003\u0006Q\u0019\u0011\r!\u000b\u0005\u0006\u0005\u001a\u0001\raG\u0001\rS:\u001c'/Z1tKN#X\r\u001d\u0005\u0006\t\u001a\u0001\r!R\u0001\u000e]\u0016,Gm]%oGJ,\u0017m]3\u0011\tQ1u\bS\u0005\u0003\u000fV\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005QI\u0015B\u0001&\u0016\u0005\u001d\u0011un\u001c7fC:Dq\u0001\u0014\u0004\u0011\u0002\u0003\u00071$\u0001\u0007j]&$\u0018.\u00197EK2\f\u0017\u0010C\u0004O\rA\u0005\t\u0019A(\u0002\u00115\f\u0007\u0010R3mCf\u0004\"\u0001\b)\n\u0005Ek\"\u0001\u0003#ve\u0006$\u0018n\u001c8\u0002?1Lg.Z1s\u0013:\u001c'/Z1tS:<G)\u001a7bs\u0012\"WMZ1vYR$3'\u0006\u0002U?V\tQK\u000b\u0002\u001c-.\nq\u000b\u0005\u0002Y;6\t\u0011L\u0003\u0002[7\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u00039V\t!\"\u00198o_R\fG/[8o\u0013\tq\u0016LA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Q\u0001K\u0004C\u0002%\nq\u0004\\5oK\u0006\u0014\u0018J\\2sK\u0006\u001c\u0018N\\4EK2\f\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00135+\t\u0011G-F\u0001dU\tye\u000bB\u0003)\u0011\t\u0007\u0011\u0006")
public interface DelayStrategy<T> {
    public static <T> Duration linearIncreasingDelay$default$4() {
        return DelayStrategy$.MODULE$.linearIncreasingDelay$default$4();
    }

    public static <T> FiniteDuration linearIncreasingDelay$default$3() {
        return DelayStrategy$.MODULE$.linearIncreasingDelay$default$3();
    }

    public static <T> DelayStrategy<T> linearIncreasingDelay(FiniteDuration increaseStep, Function1<T, Object> needsIncrease, FiniteDuration initialDelay, Duration maxDelay) {
        return DelayStrategy$.MODULE$.linearIncreasingDelay(increaseStep, needsIncrease, initialDelay, maxDelay);
    }

    public static DelayStrategy<Object> fixedDelay(FiniteDuration delay) {
        return DelayStrategy$.MODULE$.fixedDelay(delay);
    }

    public FiniteDuration nextDelay(T var1);
}

