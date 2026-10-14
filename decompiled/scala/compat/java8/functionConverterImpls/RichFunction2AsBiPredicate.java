/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiPredicate;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsBiPredicate$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I1\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006i\u0001!\t!\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bQBA\u0011A-\t\u000biCAQA.\t\u000f\u0019D\u0011\u0011!C\u0003O\"9q\u000eCA\u0001\n\u000b\u0001(A\u0007*jG\"4UO\\2uS>t''Q:CSB\u0013X\rZ5dCR,'BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u0001Qc\u0001\r%]M\u0011\u0001!\u0007\t\u00035mi\u0011\u0001F\u0005\u00039Q\u0011a!\u00118z-\u0006d\u0017\u0001U:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t''Q:CSB\u0013X\rZ5dCR,G\u0005J;oI\u0016\u0014H._5oOV\tq\u0004E\u0003\u001bA\tj\u0003'\u0003\u0002\")\tIa)\u001e8di&|gN\r\t\u0003G\u0011b\u0001\u0001B\u0003&\u0001\t\u0007aEA\u0001U#\t9#\u0006\u0005\u0002\u001bQ%\u0011\u0011\u0006\u0006\u0002\b\u001d>$\b.\u001b8h!\tQ2&\u0003\u0002-)\t\u0019\u0011I\\=\u0011\u0005\rrC!B\u0018\u0001\u0005\u00041#!A+\u0011\u0005i\t\u0014B\u0001\u001a\u0015\u0005\u001d\u0011un\u001c7fC:\f\u0011k]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u001a+hn\u0019;j_:\u0014\u0014i\u001d\"j!J,G-[2bi\u0016$C%\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"A\u000e\u001d\u0011\t]\u0002!%L\u0007\u0002\u001d!)\u0011h\u0001a\u0001?\u0005QQO\u001c3fe2L\u0018N\\4\u0002\r\u0005\u001c(*\u0019<b+\u0005a\u0004\u0003B\u001fEE5j\u0011A\u0010\u0006\u0003\u007f\u0001\u000b\u0001BZ;oGRLwN\u001c\u0006\u0003\u0003\n\u000bA!\u001e;jY*\t1)\u0001\u0003kCZ\f\u0017BA#?\u0005-\u0011\u0015\u000e\u0015:fI&\u001c\u0017\r^3)\u0005\u00119\u0005C\u0001\u000eI\u0013\tIEC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\tA\n\u0005\u0002\u001b\u001b&\u0011a\n\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u00021#\"9!KBA\u0001\u0002\u0004Q\u0013a\u0001=%c\u0005Q\"+[2i\rVt7\r^5p]J\n5OQ5Qe\u0016$\u0017nY1uKB\u0011q\u0007C\n\u0003\u0011Y\u0003\"AG,\n\u0005a#\"AB!osJ+g\rF\u0001U\u0003A\t7OS1wC\u0012*\u0007\u0010^3og&|g.F\u0002]?\u0006$\"!\u00182\u0011\tu\"e\f\u0019\t\u0003G}#Q!\n\u0006C\u0002\u0019\u0002\"aI1\u0005\u000b=R!\u0019\u0001\u0014\t\u000b\rT\u0001\u0019\u00013\u0002\u000b\u0011\"\b.[:\u0011\t]\u0002a\f\u0019\u0015\u0003\u0015\u001d\u000b!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V\u0019\u0001\u000e\u001c8\u0015\u0005-K\u0007\"B2\f\u0001\u0004Q\u0007\u0003B\u001c\u0001W6\u0004\"a\t7\u0005\u000b\u0015Z!\u0019\u0001\u0014\u0011\u0005\rrG!B\u0018\f\u0005\u00041\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\r\tx/\u001f\u000b\u0003eR$\"\u0001M:\t\u000fIc\u0011\u0011!a\u0001U!)1\r\u0004a\u0001kB!q\u0007\u0001<y!\t\u0019s\u000fB\u0003&\u0019\t\u0007a\u0005\u0005\u0002$s\u0012)q\u0006\u0004b\u0001M\u0001")
public final class RichFunction2AsBiPredicate<T, U> {
    private final Function2<T, U, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsBiPredicate$$underlying;

    public static <T, U> boolean equals$extension(Function2<T, U, Object> function2, Object object) {
        return RichFunction2AsBiPredicate$.MODULE$.equals$extension(function2, object);
    }

    public static <T, U> int hashCode$extension(Function2<T, U, Object> function2) {
        return RichFunction2AsBiPredicate$.MODULE$.hashCode$extension(function2);
    }

    public static <T, U> BiPredicate<T, U> asJava$extension(Function2<T, U, Object> function2) {
        return RichFunction2AsBiPredicate$.MODULE$.asJava$extension(function2);
    }

    public Function2<T, U, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsBiPredicate$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiPredicate$$underlying;
    }

    public BiPredicate<T, U> asJava() {
        return RichFunction2AsBiPredicate$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiPredicate$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsBiPredicate$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiPredicate$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsBiPredicate$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiPredicate$$underlying(), x$1);
    }

    public RichFunction2AsBiPredicate(Function2<T, U, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiPredicate$$underlying = underlying;
    }
}

