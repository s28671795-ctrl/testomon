/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.event.AddressTerminatedTopic;

public final class AddressTerminatedTopic$
implements ExtensionId<AddressTerminatedTopic>,
ExtensionIdProvider {
    public static final AddressTerminatedTopic$ MODULE$ = new AddressTerminatedTopic$();

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
    public AddressTerminatedTopic get(ActorSystem system) {
        return (AddressTerminatedTopic)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public AddressTerminatedTopic get(ClassicActorSystemProvider system) {
        return (AddressTerminatedTopic)ExtensionId.get$((ExtensionId)this, system);
    }

    public AddressTerminatedTopic$ lookup() {
        return this;
    }

    @Override
    public AddressTerminatedTopic createExtension(ExtendedActorSystem system) {
        return new AddressTerminatedTopic();
    }

    private AddressTerminatedTopic$() {
    }
}

