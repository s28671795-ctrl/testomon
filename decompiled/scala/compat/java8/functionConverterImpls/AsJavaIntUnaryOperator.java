/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntUnaryOperator;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QF\u0001\fBg*\u000bg/Y%oiVs\u0017M]=Pa\u0016\u0014\u0018\r^8s\u0015\t1q!\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\tA\u0011\"A\u0003kCZ\f\u0007H\u0003\u0002\u000b\u0017\u000511m\\7qCRT\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0002\u0001'\r\u0001qb\u0006\t\u0003!Ui\u0011!\u0005\u0006\u0003%M\tA\u0001\\1oO*\tA#\u0001\u0003kCZ\f\u0017B\u0001\f\u0012\u0005\u0019y%M[3diB\u0011\u0001$H\u0007\u00023)\u0011!dG\u0001\tMVt7\r^5p]*\u0011AdE\u0001\u0005kRLG.\u0003\u0002\u001f3\t\u0001\u0012J\u001c;V]\u0006\u0014\u0018p\u00149fe\u0006$xN]\u0001\u0003g\u001a\u0004B!\t\u0012%I5\t1\"\u0003\u0002$\u0017\tIa)\u001e8di&|g.\r\t\u0003C\u0015J!AJ\u0006\u0003\u0007%sG/\u0001\u0004=S:LGO\u0010\u000b\u0003S-\u0002\"A\u000b\u0001\u000e\u0003\u0015AQa\b\u0002A\u0002\u0001\n!\"\u00199qYf\f5/\u00138u)\t!c\u0006C\u00030\u0007\u0001\u0007A%\u0001\u0002yc\u0001")
public class AsJavaIntUnaryOperator
implements IntUnaryOperator {
    private final Function1<Object, Object> sf;

    @Override
    public IntUnaryOperator compose(IntUnaryOperator x$1) {
        return IntUnaryOperator.super.compose(x$1);
    }

    @Override
    public IntUnaryOperator andThen(IntUnaryOperator x$1) {
        return IntUnaryOperator.super.andThen(x$1);
    }

    @Override
    public int applyAsInt(int x1) {
        return this.sf.apply$mcII$sp(x1);
    }

    public AsJavaIntUnaryOperator(Function1<Object, Object> sf) {
        this.sf = sf;
    }
}

