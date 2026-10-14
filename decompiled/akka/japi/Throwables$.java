/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import scala.util.control.NonFatal$;

public final class Throwables$ {
    public static final Throwables$ MODULE$ = new Throwables$();

    public boolean isNonFatal(Throwable throwable) {
        return NonFatal$.MODULE$.apply(throwable);
    }

    public boolean isFatal(Throwable throwable) {
        return !this.isNonFatal(throwable);
    }

    private Throwables$() {
    }
}

