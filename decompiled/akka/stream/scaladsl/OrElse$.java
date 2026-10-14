/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.OrElse;
import scala.runtime.Nothing$;

public final class OrElse$ {
    public static final OrElse$ MODULE$ = new OrElse$();
    private static final OrElse<Nothing$> singleton = new OrElse();

    private OrElse<Nothing$> singleton() {
        return singleton;
    }

    public <T> OrElse<T> apply() {
        return this.singleton();
    }

    private OrElse$() {
    }
}

