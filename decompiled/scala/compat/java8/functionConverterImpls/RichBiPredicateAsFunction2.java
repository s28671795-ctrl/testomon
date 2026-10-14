/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiPredicate;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichBiPredicateAsFunction2$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!Iq\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006q\u0001!\t!\u000f\u0005\u0006}\u0001!\ta\u0010\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000baBA\u0011A-\t\u000biCAQA.\t\u000f\u0019D\u0011\u0011!C\u0003O\"9q\u000eCA\u0001\n\u000b\u0001(A\u0007*jG\"\u0014\u0015\u000e\u0015:fI&\u001c\u0017\r^3Bg\u001a+hn\u0019;j_:\u0014$BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u0001Qc\u0001\r,kM\u0011\u0001!\u0007\t\u00035mi\u0011\u0001F\u0005\u00039Q\u0011a!\u00118z-\u0006d\u0017\u0001U:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"\u0014\u0015\u000e\u0015:fI&\u001c\u0017\r^3Bg\u001a+hn\u0019;j_:\u0014D\u0005J;oI\u0016\u0014H._5oOV\tq\u0004\u0005\u0003!O%\"T\"A\u0011\u000b\u0005\t\u001a\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\u0011*\u0013\u0001B;uS2T\u0011AJ\u0001\u0005U\u00064\u0018-\u0003\u0002)C\tY!)\u001b)sK\u0012L7-\u0019;f!\tQ3\u0006\u0004\u0001\u0005\u000b1\u0002!\u0019A\u0017\u0003\u0003Q\u000b\"AL\u0019\u0011\u0005iy\u0013B\u0001\u0019\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0007\u001a\n\u0005M\"\"aA!osB\u0011!&\u000e\u0003\u0006m\u0001\u0011\r!\f\u0002\u0002+\u0006\t6oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\u0005&\u0004&/\u001a3jG\u0006$X-Q:Gk:\u001cG/[8oe\u0011\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u0011!\b\u0010\t\u0005w\u0001IC'D\u0001\u000f\u0011\u0015i4\u00011\u0001 \u0003))h\u000eZ3sYfLgnZ\u0001\bCN\u001c6-\u00197b+\u0005\u0001\u0005#\u0002\u000eBSQ\u001a\u0015B\u0001\"\u0015\u0005%1UO\\2uS>t'\u0007\u0005\u0002\u001b\t&\u0011Q\t\u0006\u0002\b\u0005>|G.Z1oQ\t!q\t\u0005\u0002\u001b\u0011&\u0011\u0011\n\u0006\u0002\u0007S:d\u0017N\\3\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001\u0014\t\u000355K!A\u0014\u000b\u0003\u0007%sG/\u0001\u0004fcV\fGn\u001d\u000b\u0003\u0007FCqA\u0015\u0004\u0002\u0002\u0003\u0007\u0011'A\u0002yIE\n!DU5dQ\nK\u0007K]3eS\u000e\fG/Z!t\rVt7\r^5p]J\u0002\"a\u000f\u0005\u0014\u0005!1\u0006C\u0001\u000eX\u0013\tAFC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002)\u0006\t\u0012m]*dC2\fG%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0007q{\u0016\r\u0006\u0002^EB)!$\u00110a\u0007B\u0011!f\u0018\u0003\u0006Y)\u0011\r!\f\t\u0003U\u0005$QA\u000e\u0006C\u00025BQa\u0019\u0006A\u0002\u0011\fQ\u0001\n;iSN\u0004Ba\u000f\u0001_A\"\u0012!bR\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g.F\u0002iY:$\"aS5\t\u000b\r\\\u0001\u0019\u00016\u0011\tm\u00021.\u001c\t\u0003U1$Q\u0001L\u0006C\u00025\u0002\"A\u000b8\u0005\u000bYZ!\u0019A\u0017\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tWcA9xsR\u0011!\u000f\u001e\u000b\u0003\u0007NDqA\u0015\u0007\u0002\u0002\u0003\u0007\u0011\u0007C\u0003d\u0019\u0001\u0007Q\u000f\u0005\u0003<\u0001YD\bC\u0001\u0016x\t\u0015aCB1\u0001.!\tQ\u0013\u0010B\u00037\u0019\t\u0007Q\u0006")
public final class RichBiPredicateAsFunction2<T, U> {
    private final BiPredicate<T, U> scala$compat$java8$functionConverterImpls$RichBiPredicateAsFunction2$$underlying;

    public static <T, U> boolean equals$extension(BiPredicate<T, U> biPredicate, Object object) {
        return RichBiPredicateAsFunction2$.MODULE$.equals$extension(biPredicate, object);
    }

    public static <T, U> int hashCode$extension(BiPredicate<T, U> biPredicate) {
        return RichBiPredicateAsFunction2$.MODULE$.hashCode$extension(biPredicate);
    }

    public static <T, U> Function2<T, U, Object> asScala$extension(BiPredicate<T, U> biPredicate) {
        return RichBiPredicateAsFunction2$.MODULE$.asScala$extension(biPredicate);
    }

    public BiPredicate<T, U> scala$compat$java8$functionConverterImpls$RichBiPredicateAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichBiPredicateAsFunction2$$underlying;
    }

    public Function2<T, U, Object> asScala() {
        return RichBiPredicateAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichBiPredicateAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichBiPredicateAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichBiPredicateAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichBiPredicateAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichBiPredicateAsFunction2$$underlying(), x$1);
    }

    public RichBiPredicateAsFunction2(BiPredicate<T, U> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichBiPredicateAsFunction2$$underlying = underlying;
    }
}

