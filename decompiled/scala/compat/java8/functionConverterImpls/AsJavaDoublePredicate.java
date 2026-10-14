/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoublePredicate;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00030\u0001\u0011\u0005\u0001GA\u000bBg*\u000bg/\u0019#pk\ndW\r\u0015:fI&\u001c\u0017\r^3\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001yq\u0003\u0005\u0002\u0011+5\t\u0011C\u0003\u0002\u0013'\u0005!A.\u00198h\u0015\u0005!\u0012\u0001\u00026bm\u0006L!AF\t\u0003\r=\u0013'.Z2u!\tAR$D\u0001\u001a\u0015\tQ2$\u0001\u0005gk:\u001cG/[8o\u0015\ta2#\u0001\u0003vi&d\u0017B\u0001\u0010\u001a\u0005=!u.\u001e2mKB\u0013X\rZ5dCR,\u0017AA:g!\u0011\t#\u0005J\u0014\u000e\u0003-I!aI\u0006\u0003\u0013\u0019+hn\u0019;j_:\f\u0004CA\u0011&\u0013\t13B\u0001\u0004E_V\u0014G.\u001a\t\u0003C!J!!K\u0006\u0003\u000f\t{w\u000e\\3b]\u00061A(\u001b8jiz\"\"\u0001\f\u0018\u0011\u00055\u0002Q\"A\u0003\t\u000b}\u0011\u0001\u0019\u0001\u0011\u0002\tQ,7\u000f\u001e\u000b\u0003OEBQAM\u0002A\u0002\u0011\n!\u0001_\u0019")
public class AsJavaDoublePredicate
implements DoublePredicate {
    private final Function1<Object, Object> sf;

    @Override
    public DoublePredicate and(DoublePredicate x$1) {
        return DoublePredicate.super.and(x$1);
    }

    @Override
    public DoublePredicate negate() {
        return DoublePredicate.super.negate();
    }

    @Override
    public DoublePredicate or(DoublePredicate x$1) {
        return DoublePredicate.super.or(x$1);
    }

    @Override
    public boolean test(double x1) {
        return this.sf.apply$mcZD$sp(x1);
    }

    public AsJavaDoublePredicate(Function1<Object, Object> sf) {
        this.sf = sf;
    }
}

