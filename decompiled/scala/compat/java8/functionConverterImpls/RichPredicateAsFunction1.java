/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Predicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichPredicateAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000bUBA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'\u0001\u0007*jG\"\u0004&/\u001a3jG\u0006$X-Q:Gk:\u001cG/[8oc)\u0011q\u0002E\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011\u0001dK\n\u0003\u0001e\u0001\"AG\u000e\u000e\u0003QI!\u0001\b\u000b\u0003\r\u0005s\u0017PV1m\u00039\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Qe\u0016$\u0017nY1uK\u0006\u001bh)\u001e8di&|g.\r\u0013%k:$WM\u001d7zS:<W#A\u0010\u0011\u0007\u0001:\u0013&D\u0001\"\u0015\t\u00113%\u0001\u0005gk:\u001cG/[8o\u0015\t!S%\u0001\u0003vi&d'\"\u0001\u0014\u0002\t)\fg/Y\u0005\u0003Q\u0005\u0012\u0011\u0002\u0015:fI&\u001c\u0017\r^3\u0011\u0005)ZC\u0002\u0001\u0003\u0006Y\u0001\u0011\r!\f\u0002\u0002)F\u0011a&\r\t\u00035=J!\u0001\r\u000b\u0003\u000f9{G\u000f[5oOB\u0011!DM\u0005\u0003gQ\u00111!\u00118z\u0003=\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Qe\u0016$\u0017nY1uK\u0006\u001bh)\u001e8di&|g.\r\u0013%k:$WM\u001d7zS:<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003oe\u00022\u0001\u000f\u0001*\u001b\u0005q\u0001\"\u0002\u001e\u0004\u0001\u0004y\u0012AC;oI\u0016\u0014H._5oO\u00069\u0011m]*dC2\fW#A\u001f\u0011\tiq\u0014\u0006Q\u0005\u0003\u007fQ\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005i\t\u0015B\u0001\"\u0015\u0005\u001d\u0011un\u001c7fC:D#\u0001\u0002#\u0011\u0005i)\u0015B\u0001$\u0015\u0005\u0019Ig\u000e\\5oK\u0006A\u0001.Y:i\u0007>$W\rF\u0001J!\tQ\"*\u0003\u0002L)\t\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\t\u0001e\nC\u0004P\r\u0005\u0005\t\u0019A\u0019\u0002\u0007a$\u0013'\u0001\rSS\u000eD\u0007K]3eS\u000e\fG/Z!t\rVt7\r^5p]F\u0002\"\u0001\u000f\u0005\u0014\u0005!\u0019\u0006C\u0001\u000eU\u0013\t)FC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002#\u0006\t\u0012m]*dC2\fG%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005ecFC\u0001.^!\u0011Qbh\u0017!\u0011\u0005)bF!\u0002\u0017\u000b\u0005\u0004i\u0003\"\u00020\u000b\u0001\u0004y\u0016!\u0002\u0013uQ&\u001c\bc\u0001\u001d\u00017\"\u0012!\u0002R\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g.\u0006\u0002dOR\u0011\u0001\n\u001a\u0005\u0006=.\u0001\r!\u001a\t\u0004q\u00011\u0007C\u0001\u0016h\t\u0015a3B1\u0001.\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g.\u0006\u0002kaR\u00111.\u001c\u000b\u0003\u00012Dqa\u0014\u0007\u0002\u0002\u0003\u0007\u0011\u0007C\u0003_\u0019\u0001\u0007a\u000eE\u00029\u0001=\u0004\"A\u000b9\u0005\u000b1b!\u0019A\u0017")
public final class RichPredicateAsFunction1<T> {
    private final Predicate<T> scala$compat$java8$functionConverterImpls$RichPredicateAsFunction1$$underlying;

    public static <T> boolean equals$extension(Predicate<T> predicate, Object object) {
        return RichPredicateAsFunction1$.MODULE$.equals$extension(predicate, object);
    }

    public static <T> int hashCode$extension(Predicate<T> predicate) {
        return RichPredicateAsFunction1$.MODULE$.hashCode$extension(predicate);
    }

    public static <T> Function1<T, Object> asScala$extension(Predicate<T> predicate) {
        return RichPredicateAsFunction1$.MODULE$.asScala$extension(predicate);
    }

    public Predicate<T> scala$compat$java8$functionConverterImpls$RichPredicateAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichPredicateAsFunction1$$underlying;
    }

    public Function1<T, Object> asScala() {
        return RichPredicateAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichPredicateAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichPredicateAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichPredicateAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichPredicateAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichPredicateAsFunction1$$underlying(), x$1);
    }

    public RichPredicateAsFunction1(Predicate<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichPredicateAsFunction1$$underlying = underlying;
    }
}

