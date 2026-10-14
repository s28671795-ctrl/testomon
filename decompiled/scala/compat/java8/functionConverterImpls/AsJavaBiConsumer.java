/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiConsumer;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0001\u001d!Aq\u0006\u0001B\u0001B\u0003%\u0001\u0007C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005AH\u0001\tBg*\u000bg/\u0019\"j\u0007>t7/^7fe*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0019qBI\u0017\u0014\u0007\u0001\u0001\u0002\u0004\u0005\u0002\u0012-5\t!C\u0003\u0002\u0014)\u0005!A.\u00198h\u0015\u0005)\u0012\u0001\u00026bm\u0006L!a\u0006\n\u0003\r=\u0013'.Z2u!\u0011Ib\u0004\t\u0017\u000e\u0003iQ!a\u0007\u000f\u0002\u0011\u0019,hn\u0019;j_:T!!\b\u000b\u0002\tU$\u0018\u000e\\\u0005\u0003?i\u0011!BQ5D_:\u001cX/\\3s!\t\t#\u0005\u0004\u0001\u0005\u000b\r\u0002!\u0019\u0001\u0013\u0003\u0003Q\u000b\"!J\u0015\u0011\u0005\u0019:S\"A\u0006\n\u0005!Z!a\u0002(pi\"Lgn\u001a\t\u0003M)J!aK\u0006\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\"[\u0011)a\u0006\u0001b\u0001I\t\tQ+\u0001\u0002tMB)a%\r\u0011-g%\u0011!g\u0003\u0002\n\rVt7\r^5p]J\u0002\"A\n\u001b\n\u0005UZ!\u0001B+oSR\fa\u0001P5oSRtDC\u0001\u001d;!\u0011I\u0004\u0001\t\u0017\u000e\u0003\u0015AQa\f\u0002A\u0002A\na!Y2dKB$HcA\u001a>\u007f!)ah\u0001a\u0001A\u0005\u0011\u00010\r\u0005\u0006\u0001\u000e\u0001\r\u0001L\u0001\u0003qJ\u0002")
public class AsJavaBiConsumer<T, U>
implements BiConsumer<T, U> {
    private final Function2<T, U, BoxedUnit> sf;

    @Override
    public BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> x$1) {
        return BiConsumer.super.andThen(x$1);
    }

    @Override
    public void accept(T x1, U x2) {
        this.sf.apply(x1, x2);
    }

    public AsJavaBiConsumer(Function2<T, U, BoxedUnit> sf) {
        this.sf = sf;
    }
}

