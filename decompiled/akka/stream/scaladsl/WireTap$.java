/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.WireTap;
import scala.runtime.Nothing$;

public final class WireTap$ {
    public static final WireTap$ MODULE$ = new WireTap$();
    private static final WireTap<Nothing$> singleton = new WireTap();

    private WireTap<Nothing$> singleton() {
        return singleton;
    }

    public <T> WireTap<T> apply() {
        return this.singleton();
    }

    private WireTap$() {
    }
}

