/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.scaladsl.Keep$;
import scala.Function2;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!<Qa\u0004\t\t\u0002]1Q!\u0007\t\t\u0002iAQ!I\u0001\u0005\u0002\tBqaI\u0001C\u0002\u0013%A\u0005\u0003\u0004,\u0003\u0001\u0006I!\n\u0005\bY\u0005\u0011\r\u0011\"\u0003%\u0011\u0019i\u0013\u0001)A\u0005K!9a&\u0001b\u0001\n\u0013y\u0003B\u0002\u001b\u0002A\u0003%\u0001\u0007C\u00046\u0003\t\u0007I\u0011\u0002\u001c\t\rq\n\u0001\u0015!\u00038\u0011\u0015i\u0014\u0001\"\u0001?\u0011\u0015a\u0015\u0001\"\u0001N\u0011\u0015!\u0016\u0001\"\u0001V\u0011\u0015i\u0016\u0001\"\u0001_\u0003\u0011YU-\u001a9\u000b\u0005E\u0011\u0012\u0001C:dC2\fGm\u001d7\u000b\u0005M!\u0012AB:ue\u0016\fWNC\u0001\u0016\u0003\u0011\t7n[1\u0004\u0001A\u0011\u0001$A\u0007\u0002!\t!1*Z3q'\t\t1\u0004\u0005\u0002\u001d?5\tQDC\u0001\u001f\u0003\u0015\u00198-\u00197b\u0013\t\u0001SD\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003]\tQa\u00187fMR,\u0012!\n\t\u00069\u0019B\u0003\u0006K\u0005\u0003Ou\u0011\u0011BR;oGRLwN\u001c\u001a\u0011\u0005qI\u0013B\u0001\u0016\u001e\u0005\r\te._\u0001\u0007?2,g\r\u001e\u0011\u0002\r}\u0013\u0018n\u001a5u\u0003\u001dy&/[4ii\u0002\nQa\u00182pi\",\u0012\u0001\r\t\u00069\u0019B\u0003&\r\t\u00059IB\u0003&\u0003\u00024;\t1A+\u001e9mKJ\naa\u00182pi\"\u0004\u0013!B0o_:,W#A\u001c\u0011\u000bq1\u0003\u0006\u000b\u001d\u000f\u0005eRT\"\u0001\u000b\n\u0005m\"\u0012a\u0002(piV\u001bX\rZ\u0001\u0007?:|g.\u001a\u0011\u0002\t1,g\r^\u000b\u0004\u007f\rSU#\u0001!\u0011\u000bq1\u0013)S!\u0011\u0005\t\u001bE\u0002\u0001\u0003\u0006\t.\u0011\r!\u0012\u0002\u0002\u0019F\u0011a\t\u000b\t\u00039\u001dK!\u0001S\u000f\u0003\u000f9{G\u000f[5oOB\u0011!I\u0013\u0003\u0006\u0017.\u0011\r!\u0012\u0002\u0002%\u0006)!/[4iiV\u0019a*U*\u0016\u0003=\u0003R\u0001\b\u0014Q%J\u0003\"AQ)\u0005\u000b\u0011c!\u0019A#\u0011\u0005\t\u001bF!B&\r\u0005\u0004)\u0015\u0001\u00022pi\",2AV-\\+\u00059\u0006#\u0002\u000f'1jc\u0006C\u0001\"Z\t\u0015!UB1\u0001F!\t\u00115\fB\u0003L\u001b\t\u0007Q\t\u0005\u0003\u001deaS\u0016\u0001\u00028p]\u0016,2a\u00182e+\u0005\u0001\u0007#\u0002\u000f'C\u000e,\u0007C\u0001\"c\t\u0015!eB1\u0001F!\t\u0011E\rB\u0003L\u001d\t\u0007Q\t\u0005\u0002:M&\u0011q\r\u0006\u0002\b\u001d>$Xk]3e\u0001")
public final class Keep {
    public static <L, R> Function2<L, R, NotUsed> none() {
        return Keep$.MODULE$.none();
    }

    public static <L, R> Function2<L, R, Tuple2<L, R>> both() {
        return Keep$.MODULE$.both();
    }

    public static <L, R> Function2<L, R, R> right() {
        return Keep$.MODULE$.right();
    }

    public static <L, R> Function2<L, R, L> left() {
        return Keep$.MODULE$.left();
    }
}

