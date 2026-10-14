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
import akka.io.UdpConnectedExt;

public final class UdpConnected$
implements ExtensionId<UdpConnectedExt>,
ExtensionIdProvider {
    public static final UdpConnected$ MODULE$ = new UdpConnected$();

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

    public UdpConnected$ lookup() {
        return this;
    }

    @Override
    public UdpConnectedExt createExtension(ExtendedActorSystem system) {
        return new UdpConnectedExt(system);
    }

    @Override
    public UdpConnectedExt get(ActorSystem system) {
        return (UdpConnectedExt)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public UdpConnectedExt get(ClassicActorSystemProvider system) {
        return (UdpConnectedExt)ExtensionId.get$((ExtensionId)this, system);
    }

    private UdpConnected$() {
    }
}

