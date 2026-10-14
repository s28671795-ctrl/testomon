/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import scala.math.Ordered;
import scala.math.Ordering;

public final class Ordered$ {
    public static final Ordered$ MODULE$ = new Ordered$();

    public <T> Ordered<T> orderingToOrdered(T x, Ordering<T> ord) {
        return new Ordered<T>(ord, x){
            private final Ordering ord$1;
            private final Object x$1;

            public boolean $less(T that) {
                return Ordered.$less$(this, that);
            }

            public boolean $greater(T that) {
                return Ordered.$greater$(this, that);
            }

            public boolean $less$eq(T that) {
                return Ordered.$less$eq$(this, that);
            }

            public boolean $greater$eq(T that) {
                return Ordered.$greater$eq$(this, that);
            }

            public int compareTo(T that) {
                return Ordered.compareTo$(this, that);
            }

            public int compare(T that) {
                return this.ord$1.compare(this.x$1, that);
            }
            {
                this.ord$1 = ord$1;
                this.x$1 = x$1;
            }
        };
    }

    private Ordered$() {
    }
}

