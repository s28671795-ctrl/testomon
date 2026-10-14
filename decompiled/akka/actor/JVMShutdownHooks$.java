/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.JVMShutdownHooks;
import akka.annotation.InternalApi;

@InternalApi
public final class JVMShutdownHooks$
implements JVMShutdownHooks {
    public static final JVMShutdownHooks$ MODULE$ = new JVMShutdownHooks$();

    @Override
    public void addHook(Thread t2) {
        Runtime.getRuntime().addShutdownHook(t2);
    }

    @Override
    public boolean removeHook(Thread t2) {
        return Runtime.getRuntime().removeShutdownHook(t2);
    }

    private JVMShutdownHooks$() {
    }
}

