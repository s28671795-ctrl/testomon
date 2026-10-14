/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiPredicate;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0001\u001d!Aq\u0006\u0001B\u0001B\u0003%\u0001\u0007C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005AHA\tBg*\u000bg/\u0019\"j!J,G-[2bi\u0016T!AB\u0004\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!\u0001C\u0005\u0002\u000b)\fg/\u0019\u001d\u000b\u0005)Y\u0011AB2p[B\fGOC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001)2a\u0004\u0012.'\r\u0001\u0001\u0003\u0007\t\u0003#Yi\u0011A\u0005\u0006\u0003'Q\tA\u0001\\1oO*\tQ#\u0001\u0003kCZ\f\u0017BA\f\u0013\u0005\u0019y%M[3diB!\u0011D\b\u0011-\u001b\u0005Q\"BA\u000e\u001d\u0003!1WO\\2uS>t'BA\u000f\u0015\u0003\u0011)H/\u001b7\n\u0005}Q\"a\u0003\"j!J,G-[2bi\u0016\u0004\"!\t\u0012\r\u0001\u0011)1\u0005\u0001b\u0001I\t\tA+\u0005\u0002&SA\u0011aeJ\u0007\u0002\u0017%\u0011\u0001f\u0003\u0002\b\u001d>$\b.\u001b8h!\t1#&\u0003\u0002,\u0017\t\u0019\u0011I\\=\u0011\u0005\u0005jC!\u0002\u0018\u0001\u0005\u0004!#!A+\u0002\u0005M4\u0007#\u0002\u00142A1\u001a\u0014B\u0001\u001a\f\u0005%1UO\\2uS>t'\u0007\u0005\u0002'i%\u0011Qg\u0003\u0002\b\u0005>|G.Z1o\u0003\u0019a\u0014N\\5u}Q\u0011\u0001H\u000f\t\u0005s\u0001\u0001C&D\u0001\u0006\u0011\u0015y#\u00011\u00011\u0003\u0011!Xm\u001d;\u0015\u0007Mjt\bC\u0003?\u0007\u0001\u0007\u0001%\u0001\u0002yc!)\u0001i\u0001a\u0001Y\u0005\u0011\u0001P\r")
public class AsJavaBiPredicate<T, U>
implements BiPredicate<T, U> {
    private final Function2<T, U, Object> sf;

    @Override
    public BiPredicate<T, U> and(BiPredicate<? super T, ? super U> x$1) {
        return BiPredicate.super.and(x$1);
    }

    @Override
    public BiPredicate<T, U> negate() {
        return BiPredicate.super.negate();
    }

    @Override
    public BiPredicate<T, U> or(BiPredicate<? super T, ? super U> x$1) {
        return BiPredicate.super.or(x$1);
    }

    @Override
    public boolean test(T x1, U x2) {
        return BoxesRunTime.unboxToBoolean(this.sf.apply(x1, x2));
    }

    public AsJavaBiPredicate(Function2<T, U, Object> sf) {
        this.sf = sf;
    }
}

