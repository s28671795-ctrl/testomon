/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.Envelope;
import akka.dispatch.PriorityGenerator$;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0015;Q\u0001C\u0005\t\u000291Q\u0001E\u0005\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0002m1Q\u0001E\u0005\u0002\u0002uAQ\u0001\u0007\u0003\u0005\u0002=BQ\u0001\r\u0003\u0007\u0002EBQA\u000f\u0003\u0005\u0006m\n\u0011\u0003\u0015:j_JLG/_$f]\u0016\u0014\u0018\r^8s\u0015\tQ1\"\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005a\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0010\u00035\t\u0011BA\tQe&|'/\u001b;z\u000f\u0016tWM]1u_J\u001c\"!\u0001\n\u0011\u0005M1R\"\u0001\u000b\u000b\u0003U\tQa]2bY\u0006L!a\u0006\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\ta\"A\u0003baBd\u0017\u0010\u0006\u0002\u001d\u0001B\u0011q\u0002B\n\u0004\ty1\u0003CA\u0010%\u001b\u0005\u0001#BA\u0011#\u0003\u0011a\u0017M\\4\u000b\u0003\r\nAA[1wC&\u0011Q\u0005\t\u0002\u0007\u001f\nTWm\u0019;\u0011\u0007\u001dRC&D\u0001)\u0015\tI#%\u0001\u0003vi&d\u0017BA\u0016)\u0005)\u0019u.\u001c9be\u0006$xN\u001d\t\u0003\u001f5J!AL\u0005\u0003\u0011\u0015sg/\u001a7pa\u0016$\u0012\u0001H\u0001\u0004O\u0016tGC\u0001\u001a6!\t\u00192'\u0003\u00025)\t\u0019\u0011J\u001c;\t\u000bY2\u0001\u0019A\u001c\u0002\u000f5,7o]1hKB\u00111\u0003O\u0005\u0003sQ\u00111!\u00118z\u0003\u001d\u0019w.\u001c9be\u0016$2A\r\u001f?\u0011\u0015it\u00011\u0001-\u0003-!\b.[:NKN\u001c\u0018mZ3\t\u000b}:\u0001\u0019\u0001\u0017\u0002\u0017QD\u0017\r^'fgN\fw-\u001a\u0005\u0006\u0003\u000e\u0001\rAQ\u0001\u0011aJLwN]5us\u001a+hn\u0019;j_:\u0004BaE\"8e%\u0011A\t\u0006\u0002\n\rVt7\r^5p]F\u0002")
public abstract class PriorityGenerator
implements Comparator<Envelope> {
    public static PriorityGenerator apply(Function1<Object, Object> priorityFunction) {
        return PriorityGenerator$.MODULE$.apply(priorityFunction);
    }

    @Override
    public Comparator<Envelope> reversed() {
        return Comparator.super.reversed();
    }

    @Override
    public Comparator<Envelope> thenComparing(Comparator<? super Envelope> x$1) {
        return Comparator.super.thenComparing(x$1);
    }

    @Override
    public <U> Comparator<Envelope> thenComparing(Function<? super Envelope, ? extends U> x$1, Comparator<? super U> x$2) {
        return Comparator.super.thenComparing(x$1, x$2);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<Envelope> thenComparing(Function<? super Envelope, ? extends U> x$1) {
        return Comparator.super.thenComparing(x$1);
    }

    @Override
    public Comparator<Envelope> thenComparingInt(ToIntFunction<? super Envelope> x$1) {
        return Comparator.super.thenComparingInt(x$1);
    }

    @Override
    public Comparator<Envelope> thenComparingLong(ToLongFunction<? super Envelope> x$1) {
        return Comparator.super.thenComparingLong(x$1);
    }

    @Override
    public Comparator<Envelope> thenComparingDouble(ToDoubleFunction<? super Envelope> x$1) {
        return Comparator.super.thenComparingDouble(x$1);
    }

    public abstract int gen(Object var1);

    @Override
    public final int compare(Envelope thisMessage, Envelope thatMessage) {
        return this.gen(thisMessage.message()) - this.gen(thatMessage.message());
    }
}

