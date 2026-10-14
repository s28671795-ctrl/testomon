/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.serialization.Serialization;

public final class SerializationExtension$
implements ExtensionId<Serialization>,
ExtensionIdProvider {
    public static final SerializationExtension$ MODULE$ = new SerializationExtension$();

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
    public Serialization get(ActorSystem system) {
        return (Serialization)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public Serialization get(ClassicActorSystemProvider system) {
        return (Serialization)ExtensionId.get$((ExtensionId)this, system);
    }

    public SerializationExtension$ lookup() {
        return this;
    }

    @Override
    public Serialization createExtension(ExtendedActorSystem system) {
        return new Serialization(system);
    }

    private SerializationExtension$() {
    }
}

