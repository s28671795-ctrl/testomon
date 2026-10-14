/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntConsumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichIntConsumerAsFunction1$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b%BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&A\u0007*jG\"Le\u000e^\"p]N,X.\u001a:Bg\u001a+hn\u0019;j_:\f$BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u00011C\u0001\u0001\u0019!\tI\"$D\u0001\u0015\u0013\tYBC\u0001\u0004B]f4\u0016\r\\\u0001Qg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007.\u00138u\u0007>t7/^7fe\u0006\u001bh)\u001e8di&|g.\r\u0013%k:$WM\u001d7zS:<W#\u0001\u0010\u0011\u0005}1S\"\u0001\u0011\u000b\u0005\u0005\u0012\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\r\"\u0013\u0001B;uS2T\u0011!J\u0001\u0005U\u00064\u0018-\u0003\u0002(A\tY\u0011J\u001c;D_:\u001cX/\\3s\u0003E\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195J]R\u001cuN\\:v[\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c82I\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\tYS\u0006\u0005\u0002-\u00015\ta\u0002C\u0003/\u0007\u0001\u0007a$\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\fq!Y:TG\u0006d\u0017-F\u00012!\u0011I\"\u0007N\u001c\n\u0005M\"\"!\u0003$v]\u000e$\u0018n\u001c82!\tIR'\u0003\u00027)\t\u0019\u0011J\u001c;\u0011\u0005eA\u0014BA\u001d\u0015\u0005\u0011)f.\u001b;)\u0005\u0011Y\u0004CA\r=\u0013\tiDC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\tA'\u0001\u0004fcV\fGn\u001d\u000b\u0003\u0005\u0016\u0003\"!G\"\n\u0005\u0011#\"a\u0002\"p_2,\u0017M\u001c\u0005\b\r\u001a\t\t\u00111\u0001H\u0003\rAH%\r\t\u00033!K!!\u0013\u000b\u0003\u0007\u0005s\u00170\u0001\u000eSS\u000eD\u0017J\u001c;D_:\u001cX/\\3s\u0003N4UO\\2uS>t\u0017\u0007\u0005\u0002-\u0011M\u0011\u0001\"\u0014\t\u000339K!a\u0014\u000b\u0003\r\u0005s\u0017PU3g)\u0005Y\u0015!E1t'\u000e\fG.\u0019\u0013fqR,gn]5p]R\u0011\u0011g\u0015\u0005\u0006)*\u0001\raK\u0001\u0006IQD\u0017n\u001d\u0015\u0003\u0015m\n!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]R\u0011q\b\u0017\u0005\u0006).\u0001\raK\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:$\"aW/\u0015\u0005\tc\u0006b\u0002$\r\u0003\u0003\u0005\ra\u0012\u0005\u0006)2\u0001\ra\u000b")
public final class RichIntConsumerAsFunction1 {
    private final IntConsumer scala$compat$java8$functionConverterImpls$RichIntConsumerAsFunction1$$underlying;

    public static boolean equals$extension(IntConsumer intConsumer, Object object) {
        return RichIntConsumerAsFunction1$.MODULE$.equals$extension(intConsumer, object);
    }

    public static int hashCode$extension(IntConsumer intConsumer) {
        return RichIntConsumerAsFunction1$.MODULE$.hashCode$extension(intConsumer);
    }

    public static Function1<Object, BoxedUnit> asScala$extension(IntConsumer intConsumer) {
        return RichIntConsumerAsFunction1$.MODULE$.asScala$extension(intConsumer);
    }

    public IntConsumer scala$compat$java8$functionConverterImpls$RichIntConsumerAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichIntConsumerAsFunction1$$underlying;
    }

    public Function1<Object, BoxedUnit> asScala() {
        return RichIntConsumerAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichIntConsumerAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichIntConsumerAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichIntConsumerAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichIntConsumerAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichIntConsumerAsFunction1$$underlying(), x$1);
    }

    public RichIntConsumerAsFunction1(IntConsumer underlying) {
        this.scala$compat$java8$functionConverterImpls$RichIntConsumerAsFunction1$$underlying = underlying;
    }
}

