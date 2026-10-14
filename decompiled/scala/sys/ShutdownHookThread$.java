/*
 * Decompiled with CFR 0.152.
 */
package scala.sys;

import scala.Function0;
import scala.runtime.BoxedUnit;
import scala.sys.ShutdownHookThread;

public final class ShutdownHookThread$ {
    public static final ShutdownHookThread$ MODULE$ = new ShutdownHookThread$();
    private static int hookNameCount = 0;

    private synchronized String hookName() {
        return new StringBuilder(12).append("shutdownHook").append(++hookNameCount).toString();
    }

    public ShutdownHookThread apply(Function0<BoxedUnit> body2) {
        ShutdownHookThread t2 = new ShutdownHookThread(() -> body2.apply$mcV$sp(), this.hookName());
        Runtime.getRuntime().addShutdownHook(t2);
        return t2;
    }

    private ShutdownHookThread$() {
    }
}

