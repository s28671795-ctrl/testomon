/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoublePredicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.AsJavaDoublePredicate;
import scala.compat.java8.functionConverterImpls.RichFunction1AsDoublePredicate;

public final class RichFunction1AsDoublePredicate$ {
    public static final RichFunction1AsDoublePredicate$ MODULE$ = new RichFunction1AsDoublePredicate$();

    public final DoublePredicate asJava$extension(Function1 $this) {
        return new AsJavaDoublePredicate($this);
    }

    public final int hashCode$extension(Function1 $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(Function1 $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof RichFunction1AsDoublePredicate)) return false;
        boolean bl = true;
        if (!bl) return false;
        Function1<Object, Object> function1 = x$1 == null ? null : ((RichFunction1AsDoublePredicate)x$1).scala$compat$java8$functionConverterImpls$RichFunction1AsDoublePredicate$$underlying();
        Function1 function12 = $this;
        Function1<Object, Object> function13 = function1;
        if (function12 != null) {
            if (!function12.equals(function13)) return false;
            return true;
        }
        if (function13 == null) return true;
        return false;
    }

    private RichFunction1AsDoublePredicate$() {
    }
}

