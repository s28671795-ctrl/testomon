/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.TLSClosing$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I2Qa\u0003\u0007\u0002\"EAQ\u0001\u0007\u0001\u0005\u0002eAQ\u0001\b\u0001\u0007\u0002uAQ!\t\u0001\u0007\u0002u9Qa\u000b\u0007\t\u000212Qa\u0003\u0007\t\u00025BQ\u0001G\u0003\u0005\u00029BQaL\u0003\u0005\u0002ABQ\u0001H\u0003\u0005\u0002ABQ!I\u0003\u0005\u0002ABQ!M\u0003\u0005\u0002A\u0012!\u0002\u0016'T\u00072|7/\u001b8h\u0015\tia\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001f\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\n\u0011\u0005M1R\"\u0001\u000b\u000b\u0003U\tQa]2bY\u0006L!a\u0006\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!\u0004\u0005\u0002\u001c\u00015\tA\"\u0001\u0007jO:|'/Z\"b]\u000e,G.F\u0001\u001f!\t\u0019r$\u0003\u0002!)\t9!i\\8mK\u0006t\u0017AD5h]>\u0014XmQ8na2,G/Z\u0015\u0006\u0001\r*s%K\u0005\u0003I1\u0011!\"R1hKJ\u001cEn\\:f\u0013\t1CB\u0001\u0006JO:|'/\u001a\"pi\"L!\u0001\u000b\u0007\u0003\u0019%;gn\u001c:f\u0007\u0006t7-\u001a7\n\u0005)b!AD%h]>\u0014XmQ8na2,G/Z\u0001\u000b)2\u001b6\t\\8tS:<\u0007CA\u000e\u0006'\t)!\u0003F\u0001-\u0003))\u0017mZ3s\u00072|7/Z\u000b\u00025\u0005Q\u0011n\u001a8pe\u0016\u0014u\u000e\u001e5")
public abstract class TLSClosing {
    public static TLSClosing ignoreBoth() {
        return TLSClosing$.MODULE$.ignoreBoth();
    }

    public static TLSClosing eagerClose() {
        return TLSClosing$.MODULE$.eagerClose();
    }

    public abstract boolean ignoreCancel();

    public abstract boolean ignoreComplete();
}

