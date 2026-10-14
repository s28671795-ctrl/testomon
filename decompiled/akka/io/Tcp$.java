/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.io.TcpExt;

public final class Tcp$
implements ExtensionId<TcpExt>,
ExtensionIdProvider {
    public static final Tcp$ MODULE$ = new Tcp$();

    static {
        ExtensionId.$init$(MODULE$);
    }

    @Override
    public Extension apply(ActorSystem system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public Extension apply(ClassicActorSystemProvider system) {
        return ExtensionId.apply$((ExtensionId)this, system);
    }

    @Override
    public final int hashCode() {
        return ExtensionId.hashCode$(this);
    }

    @Override
    public final boolean equals(Object other) {
        return ExtensionId.equals$(this, other);
    }

    public Tcp$ lookup() {
        return this;
    }

    @Override
    public TcpExt createExtension(ExtendedActorSystem system) {
        return new TcpExt(system);
    }

    @Override
    public TcpExt get(ActorSystem system) {
        return (TcpExt)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public TcpExt get(ClassicActorSystemProvider system) {
        return (TcpExt)ExtensionId.get$((ExtensionId)this, system);
    }

    private Tcp$() {
    }
}

