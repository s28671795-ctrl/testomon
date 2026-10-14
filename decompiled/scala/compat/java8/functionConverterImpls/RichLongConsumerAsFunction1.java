/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongConsumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichLongConsumerAsFunction1$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u00054A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0019\u0005!!A\u0005B\u0011;q!\u0014\b\u0002\u0002#\u0005aJB\u0004\u000e\u001d\u0005\u0005\t\u0012A(\t\u000b%BA\u0011A*\t\u000bQCAQA+\t\u000feC\u0011\u0011!C\u00035\"9A\fCA\u0001\n\u000bi&a\u0007*jG\"duN\\4D_:\u001cX/\\3s\u0003N4UO\\2uS>t\u0017G\u0003\u0002\u0010!\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\u0012%\u0005)!.\u0019<bq)\u00111\u0003F\u0001\u0007G>l\u0007/\u0019;\u000b\u0003U\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u00011A\u0011\u0011DG\u0007\u0002)%\u00111\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002#N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b'p]\u001e\u001cuN\\:v[\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c82I\u0011*h\u000eZ3sYfLgnZ\u000b\u0002=A\u0011qDJ\u0007\u0002A)\u0011\u0011EI\u0001\tMVt7\r^5p]*\u00111\u0005J\u0001\u0005kRLGNC\u0001&\u0003\u0011Q\u0017M^1\n\u0005\u001d\u0002#\u0001\u0004'p]\u001e\u001cuN\\:v[\u0016\u0014\u0018AU:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"duN\\4D_:\u001cX/\\3s\u0003N4UO\\2uS>t\u0017\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002,[A\u0011A\u0006A\u0007\u0002\u001d!)af\u0001a\u0001=\u0005QQO\u001c3fe2L\u0018N\\4\u0002\u000f\u0005\u001c8kY1mCV\t\u0011\u0007\u0005\u0003\u001aeQ:\u0014BA\u001a\u0015\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002\u001ak%\u0011a\u0007\u0006\u0002\u0005\u0019>tw\r\u0005\u0002\u001aq%\u0011\u0011\b\u0006\u0002\u0005+:LG\u000f\u000b\u0002\u0005wA\u0011\u0011\u0004P\u0005\u0003{Q\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003\u0001\u0003\"!G!\n\u0005\t#\"aA%oi\u00061Q-];bYN$\"!\u0012%\u0011\u0005e1\u0015BA$\u0015\u0005\u001d\u0011un\u001c7fC:Dq!\u0013\u0004\u0002\u0002\u0003\u0007!*A\u0002yIE\u0002\"!G&\n\u00051#\"aA!os\u0006Y\"+[2i\u0019>twmQ8ogVlWM]!t\rVt7\r^5p]F\u0002\"\u0001\f\u0005\u0014\u0005!\u0001\u0006CA\rR\u0013\t\u0011FC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002\u001d\u0006\t\u0012m]*dC2\fG%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005E2\u0006\"B,\u000b\u0001\u0004Y\u0013!\u0002\u0013uQ&\u001c\bF\u0001\u0006<\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005}Z\u0006\"B,\f\u0001\u0004Y\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\tq\u0006\r\u0006\u0002F?\"9\u0011\nDA\u0001\u0002\u0004Q\u0005\"B,\r\u0001\u0004Y\u0003")
public final class RichLongConsumerAsFunction1 {
    private final LongConsumer scala$compat$java8$functionConverterImpls$RichLongConsumerAsFunction1$$underlying;

    public static boolean equals$extension(LongConsumer longConsumer, Object object) {
        return RichLongConsumerAsFunction1$.MODULE$.equals$extension(longConsumer, object);
    }

    public static int hashCode$extension(LongConsumer longConsumer) {
        return RichLongConsumerAsFunction1$.MODULE$.hashCode$extension(longConsumer);
    }

    public static Function1<Object, BoxedUnit> asScala$extension(LongConsumer longConsumer) {
        return RichLongConsumerAsFunction1$.MODULE$.asScala$extension(longConsumer);
    }

    public LongConsumer scala$compat$java8$functionConverterImpls$RichLongConsumerAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichLongConsumerAsFunction1$$underlying;
    }

    public Function1<Object, BoxedUnit> asScala() {
        return RichLongConsumerAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichLongConsumerAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichLongConsumerAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichLongConsumerAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichLongConsumerAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichLongConsumerAsFunction1$$underlying(), x$1);
    }

    public RichLongConsumerAsFunction1(LongConsumer underlying) {
        this.scala$compat$java8$functionConverterImpls$RichLongConsumerAsFunction1$$underlying = underlying;
    }
}

