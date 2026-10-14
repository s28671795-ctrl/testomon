/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.collection.IterableOnce;
import scala.compat.java8.converterImpl.AccumulateIntCollection$;
import scala.jdk.IntAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r4A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IQ\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006]\u0001!\ta\f\u0005\u0006i\u0001!\t!\u000e\u0005\b\t\u0002\t\t\u0011\"\u0011F\u0011\u001d1\u0005!!A\u0005B\u001d;q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000b9BA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000fmC\u0011\u0011!C\u00039\"9a\fCA\u0001\n\u000by&aF!dGVlW\u000f\\1uK&sGoQ8mY\u0016\u001cG/[8o\u0015\ty\u0001#A\u0007d_:4XM\u001d;fe&k\u0007\u000f\u001c\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\n\u0003\u0001a\u0001\"!\u0007\u000e\u000e\u0003QI!a\u0007\u000b\u0003\r\u0005s\u0017PV1m\u0003\u0011\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%G>tg/\u001a:uKJLU\u000e\u001d7%\u0003\u000e\u001cW/\\;mCR,\u0017J\u001c;D_2dWm\u0019;j_:$C%\u001e8eKJd\u00170\u001b8h+\u0005q\u0002cA\u0010(U9\u0011\u0001%\n\b\u0003C\u0011j\u0011A\t\u0006\u0003GY\ta\u0001\u0010:p_Rt\u0014\"A\u000b\n\u0005\u0019\"\u0012a\u00029bG.\fw-Z\u0005\u0003Q%\u0012A\"\u0013;fe\u0006\u0014G.Z(oG\u0016T!A\n\u000b\u0011\u0005eY\u0013B\u0001\u0017\u0015\u0005\rIe\u000e^\u0001Fg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\bJ2p]Z,'\u000f^3s\u00136\u0004H\u000eJ!dGVlW\u000f\\1uK&sGoQ8mY\u0016\u001cG/[8oI\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\t\u0001$\u0007\u0005\u00022\u00015\ta\u0002C\u00034\u0007\u0001\u0007a$\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\f!\"Y2dk6,H.\u0019;f+\u00051\u0004CA\u001cB\u001d\tAtH\u0004\u0002:{9\u0011!\b\u0010\b\u0003AmJ!a\u0005\u000b\n\u0005E\u0011\u0012B\u0001 \u0011\u00039\u0019w\u000e\u001c7fGRLwN\\%na2L!A\n!\u000b\u0005y\u0002\u0012B\u0001\"D\u00059Ie\u000e^!dGVlW\u000f\\1u_JT!A\n!\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012AK\u0001\u0007KF,\u0018\r\\:\u0015\u0005![\u0005CA\rJ\u0013\tQECA\u0004C_>dW-\u00198\t\u000f13\u0011\u0011!a\u0001\u001b\u0006\u0019\u0001\u0010J\u0019\u0011\u0005eq\u0015BA(\u0015\u0005\r\te._\u0001\u0018\u0003\u000e\u001cW/\\;mCR,\u0017J\u001c;D_2dWm\u0019;j_:\u0004\"!\r\u0005\u0014\u0005!\u0019\u0006CA\rU\u0013\t)FC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002#\u0006!\u0012mY2v[Vd\u0017\r^3%Kb$XM\\:j_:$\"AN-\t\u000biS\u0001\u0019\u0001\u0019\u0002\u000b\u0011\"\b.[:\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0003\u000bvCQAW\u0006A\u0002A\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005\u0001\u0014GC\u0001%b\u0011\u001daE\"!AA\u00025CQA\u0017\u0007A\u0002A\u0002")
public final class AccumulateIntCollection {
    private final IterableOnce<Object> scala$compat$java8$converterImpl$AccumulateIntCollection$$underlying;

    public static boolean equals$extension(IterableOnce iterableOnce, Object object) {
        return AccumulateIntCollection$.MODULE$.equals$extension(iterableOnce, object);
    }

    public static int hashCode$extension(IterableOnce iterableOnce) {
        return AccumulateIntCollection$.MODULE$.hashCode$extension(iterableOnce);
    }

    public static IntAccumulator accumulate$extension(IterableOnce iterableOnce) {
        return AccumulateIntCollection$.MODULE$.accumulate$extension(iterableOnce);
    }

    public IterableOnce<Object> scala$compat$java8$converterImpl$AccumulateIntCollection$$underlying() {
        return this.scala$compat$java8$converterImpl$AccumulateIntCollection$$underlying;
    }

    public IntAccumulator accumulate() {
        return AccumulateIntCollection$.MODULE$.accumulate$extension(this.scala$compat$java8$converterImpl$AccumulateIntCollection$$underlying());
    }

    public int hashCode() {
        return AccumulateIntCollection$.MODULE$.hashCode$extension(this.scala$compat$java8$converterImpl$AccumulateIntCollection$$underlying());
    }

    public boolean equals(Object x$1) {
        return AccumulateIntCollection$.MODULE$.equals$extension(this.scala$compat$java8$converterImpl$AccumulateIntCollection$$underlying(), x$1);
    }

    public AccumulateIntCollection(IterableOnce<Object> underlying) {
        this.scala$compat$java8$converterImpl$AccumulateIntCollection$$underlying = underlying;
    }
}

