/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.annotation.InternalApi;
import akka.stream.impl.streamref.StreamRefsMaster;

@InternalApi
public final class StreamRefsMaster$
implements ExtensionId<StreamRefsMaster>,
ExtensionIdProvider {
    public static final StreamRefsMaster$ MODULE$ = new StreamRefsMaster$();

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
    public StreamRefsMaster createExtension(ExtendedActorSystem system) {
        return new StreamRefsMaster();
    }

    public StreamRefsMaster$ lookup() {
        return this;
    }

    @Override
    public StreamRefsMaster get(ActorSystem system) {
        return (StreamRefsMaster)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public StreamRefsMaster get(ClassicActorSystemProvider system) {
        return (StreamRefsMaster)ExtensionId.get$((ExtensionId)this, system);
    }

    private StreamRefsMaster$() {
    }
}

