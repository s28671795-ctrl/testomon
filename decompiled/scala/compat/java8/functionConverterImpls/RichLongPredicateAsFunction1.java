/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongPredicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichLongPredicateAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0019\u0005!!A\u0005B\u0011;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b%BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&\u0001\b*jG\"duN\\4Qe\u0016$\u0017nY1uK\u0006\u001bh)\u001e8di&|g.\r\u0006\u0003\u001fA\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\n\u0003\u0001a\u0001\"!\u0007\u000e\u000e\u0003QI!a\u0007\u000b\u0003\r\u0005s\u0017PV1m\u0003I\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195M_:<\u0007K]3eS\u000e\fG/Z!t\rVt7\r^5p]F\"C%\u001e8eKJd\u00170\u001b8h+\u0005q\u0002CA\u0010'\u001b\u0005\u0001#BA\u0011#\u0003!1WO\\2uS>t'BA\u0012%\u0003\u0011)H/\u001b7\u000b\u0003\u0015\nAA[1wC&\u0011q\u0005\t\u0002\u000e\u0019>tw\r\u0015:fI&\u001c\u0017\r^3\u0002'N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b'p]\u001e\u0004&/\u001a3jG\u0006$X-Q:Gk:\u001cG/[8oc\u0011\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u00111&\f\t\u0003Y\u0001i\u0011A\u0004\u0005\u0006]\r\u0001\rAH\u0001\u000bk:$WM\u001d7zS:<\u0017aB1t'\u000e\fG.Y\u000b\u0002cA!\u0011D\r\u001b8\u0013\t\u0019DCA\u0005Gk:\u001cG/[8ocA\u0011\u0011$N\u0005\u0003mQ\u0011A\u0001T8oOB\u0011\u0011\u0004O\u0005\u0003sQ\u0011qAQ8pY\u0016\fg\u000e\u000b\u0002\u0005wA\u0011\u0011\u0004P\u0005\u0003{Q\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003\u0001\u0003\"!G!\n\u0005\t#\"aA%oi\u00061Q-];bYN$\"aN#\t\u000f\u00193\u0011\u0011!a\u0001\u000f\u0006\u0019\u0001\u0010J\u0019\u0011\u0005eA\u0015BA%\u0015\u0005\r\te._\u0001\u001d%&\u001c\u0007\u000eT8oOB\u0013X\rZ5dCR,\u0017i\u001d$v]\u000e$\u0018n\u001c82!\ta\u0003b\u0005\u0002\t\u001bB\u0011\u0011DT\u0005\u0003\u001fR\u0011a!\u00118z%\u00164G#A&\u0002#\u0005\u001c8kY1mC\u0012*\u0007\u0010^3og&|g\u000e\u0006\u00022'\")AK\u0003a\u0001W\u0005)A\u0005\u001e5jg\"\u0012!bO\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002@1\")Ak\u0003a\u0001W\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\u001c\u000b\u00037v#\"a\u000e/\t\u000f\u0019c\u0011\u0011!a\u0001\u000f\")A\u000b\u0004a\u0001W\u0001")
public final class RichLongPredicateAsFunction1 {
    private final LongPredicate scala$compat$java8$functionConverterImpls$RichLongPredicateAsFunction1$$underlying;

    public static boolean equals$extension(LongPredicate longPredicate, Object object) {
        return RichLongPredicateAsFunction1$.MODULE$.equals$extension(longPredicate, object);
    }

    public static int hashCode$extension(LongPredicate longPredicate) {
        return RichLongPredicateAsFunction1$.MODULE$.hashCode$extension(longPredicate);
    }

    public static Function1<Object, Object> asScala$extension(LongPredicate longPredicate) {
        return RichLongPredicateAsFunction1$.MODULE$.asScala$extension(longPredicate);
    }

    public LongPredicate scala$compat$java8$functionConverterImpls$RichLongPredicateAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichLongPredicateAsFunction1$$underlying;
    }

    public Function1<Object, Object> asScala() {
        return RichLongPredicateAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichLongPredicateAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichLongPredicateAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichLongPredicateAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichLongPredicateAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichLongPredicateAsFunction1$$underlying(), x$1);
    }

    public RichLongPredicateAsFunction1(LongPredicate underlying) {
        this.scala$compat$java8$functionConverterImpls$RichLongPredicateAsFunction1$$underlying = underlying;
    }
}

