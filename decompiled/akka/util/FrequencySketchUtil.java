/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.FrequencySketchUtil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}:a\u0001B\u0003\t\u0002\u001dIaAB\u0006\u0006\u0011\u00039A\u0002C\u0003\u0014\u0003\u0011\u0005Q\u0003C\u0003\u0017\u0003\u0011\u0005q#A\nGe\u0016\fX/\u001a8dsN[W\r^2i+RLGN\u0003\u0002\u0007\u000f\u0005!Q\u000f^5m\u0015\u0005A\u0011\u0001B1lW\u0006\u0004\"AC\u0001\u000e\u0003\u0015\u00111C\u0012:fcV,gnY=TW\u0016$8\r[+uS2\u001c\"!A\u0007\u0011\u00059\tR\"A\b\u000b\u0003A\tQa]2bY\u0006L!AE\b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}\r\u0001A#A\u0005\u0002\u0017\u0011,'-^4TiJLgn\u001a\u000b\u00071\rb\u0013gM\u001b\u0011\u0005e\u0001cB\u0001\u000e\u001f!\tYr\"D\u0001\u001d\u0015\tiB#\u0001\u0004=e>|GOP\u0005\u0003?=\ta\u0001\u0015:fI\u00164\u0017BA\u0011#\u0005\u0019\u0019FO]5oO*\u0011qd\u0004\u0005\u0006I\r\u0001\r!J\u0001\u0007[\u0006$(/\u001b=\u0011\u000791\u0003&\u0003\u0002(\u001f\t)\u0011I\u001d:bsB\u0019aBJ\u0015\u0011\u00059Q\u0013BA\u0016\u0010\u0005\u0011auN\\4\t\u000b5\u001a\u0001\u0019\u0001\u0018\u0002\u0011I|woV5ei\"\u0004\"AD\u0018\n\u0005Az!aA%oi\")!g\u0001a\u0001]\u0005)1\u000f\\8ug\")Ag\u0001a\u0001]\u0005a1m\\;oi\u0016\u0014x+\u001b3uQ\")ag\u0001a\u0001S\u0005Q1m\\;oi\u0016\u0014X*\u0019=)\u0005\u0005A\u0004CA\u001d=\u001b\u0005Q$BA\u001e\b\u0003)\tgN\\8uCRLwN\\\u0005\u0003{i\u00121\"\u00138uKJt\u0017\r\\!qS\"\u0012\u0001\u0001\u000f")
@InternalApi
public final class FrequencySketchUtil {
    public static String debugString(long[][] matrix, int rowWidth, int slots, int counterWidth, long counterMax) {
        return FrequencySketchUtil$.MODULE$.debugString(matrix, rowWidth, slots, counterWidth, counterMax);
    }
}

