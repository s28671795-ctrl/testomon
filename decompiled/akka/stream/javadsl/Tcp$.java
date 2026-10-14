/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.stream.javadsl.Tcp;

public final class Tcp$
implements ExtensionId<Tcp>,
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

    @Override
    public Tcp get(ActorSystem system) {
        return (Tcp)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public Tcp get(ClassicActorSystemProvider system) {
        return (Tcp)ExtensionId.get$((ExtensionId)this, system);
    }

    public Tcp$ lookup() {
        return this;
    }

    @Override
    public Tcp createExtension(ExtendedActorSystem system) {
        return new Tcp(system);
    }

    private Tcp$() {
    }
}

