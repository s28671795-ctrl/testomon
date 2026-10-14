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
import akka.io.UdpExt;

public final class Udp$
implements ExtensionId<UdpExt>,
ExtensionIdProvider {
    public static final Udp$ MODULE$ = new Udp$();

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

    public Udp$ lookup() {
        return this;
    }

    @Override
    public UdpExt createExtension(ExtendedActorSystem system) {
        return new UdpExt(system);
    }

    @Override
    public UdpExt get(ActorSystem system) {
        return (UdpExt)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public UdpExt get(ClassicActorSystemProvider system) {
        return (UdpExt)ExtensionId.get$((ExtensionId)this, system);
    }

    private Udp$() {
    }
}

