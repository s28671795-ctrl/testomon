/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongConsumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsLongConsumer$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u00054A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!Iq\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006Q\u0001!\t!\u000b\u0005\u0006]\u0001!\ta\f\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0019\u0005!!A\u0005B\u0011;q!\u0014\b\u0002\u0002#\u0005aJB\u0004\u000e\u001d\u0005\u0005\t\u0012A(\t\u000b!BA\u0011A*\t\u000bQCAQA+\t\u000feC\u0011\u0011!C\u00035\"9A\fCA\u0001\n\u000bi&a\u0007*jG\"4UO\\2uS>t\u0017'Q:M_:<7i\u001c8tk6,'O\u0003\u0002\u0010!\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\u0012%\u0005)!.\u0019<bq)\u00111\u0003F\u0001\u0007G>l\u0007/\u0019;\u000b\u0003U\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u00011A\u0011\u0011DG\u0007\u0002)%\u00111\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002#N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003NduN\\4D_:\u001cX/\\3sI\u0011*h\u000eZ3sYfLgnZ\u000b\u0002=A!\u0011dH\u0011%\u0013\t\u0001CCA\u0005Gk:\u001cG/[8ocA\u0011\u0011DI\u0005\u0003GQ\u0011A\u0001T8oOB\u0011\u0011$J\u0005\u0003MQ\u0011A!\u00168ji\u0006\u00116oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\rVt7\r^5p]F\n5\u000fT8oO\u000e{gn];nKJ$C%\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"A\u000b\u0017\u0011\u0005-\u0002Q\"\u0001\b\t\u000b5\u001a\u0001\u0019\u0001\u0010\u0002\u0015UtG-\u001a:ms&tw-\u0001\u0004bg*\u000bg/Y\u000b\u0002aA\u0011\u0011\u0007O\u0007\u0002e)\u00111\u0007N\u0001\tMVt7\r^5p]*\u0011QGN\u0001\u0005kRLGNC\u00018\u0003\u0011Q\u0017M^1\n\u0005e\u0012$\u0001\u0004'p]\u001e\u001cuN\\:v[\u0016\u0014\bF\u0001\u0003<!\tIB(\u0003\u0002>)\t1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0001B\u0011\u0011$Q\u0005\u0003\u0005R\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011Q\t\u0013\t\u00033\u0019K!a\u0012\u000b\u0003\u000f\t{w\u000e\\3b]\"9\u0011JBA\u0001\u0002\u0004Q\u0015a\u0001=%cA\u0011\u0011dS\u0005\u0003\u0019R\u00111!\u00118z\u0003m\u0011\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001bHj\u001c8h\u0007>t7/^7feB\u00111\u0006C\n\u0003\u0011A\u0003\"!G)\n\u0005I#\"AB!osJ+g\rF\u0001O\u0003A\t7OS1wC\u0012*\u0007\u0010^3og&|g\u000e\u0006\u00021-\")qK\u0003a\u0001U\u0005)A\u0005\u001e5jg\"\u0012!bO\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002@7\")qk\u0003a\u0001U\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\u001c\u000b\u0003=\u0002$\"!R0\t\u000f%c\u0011\u0011!a\u0001\u0015\")q\u000b\u0004a\u0001U\u0001")
public final class RichFunction1AsLongConsumer {
    private final Function1<Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction1AsLongConsumer$$underlying;

    public static boolean equals$extension(Function1 function1, Object object) {
        return RichFunction1AsLongConsumer$.MODULE$.equals$extension(function1, object);
    }

    public static int hashCode$extension(Function1 function1) {
        return RichFunction1AsLongConsumer$.MODULE$.hashCode$extension(function1);
    }

    public static LongConsumer asJava$extension(Function1 function1) {
        return RichFunction1AsLongConsumer$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction1AsLongConsumer$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongConsumer$$underlying;
    }

    public LongConsumer asJava() {
        return RichFunction1AsLongConsumer$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongConsumer$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsLongConsumer$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongConsumer$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsLongConsumer$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongConsumer$$underlying(), x$1);
    }

    public RichFunction1AsLongConsumer(Function1<Object, BoxedUnit> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongConsumer$$underlying = underlying;
    }
}

