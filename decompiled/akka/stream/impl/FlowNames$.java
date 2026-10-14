/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.annotation.InternalApi;
import akka.stream.impl.FlowNames;

@InternalApi
public final class FlowNames$
implements ExtensionId<FlowNames>,
ExtensionIdProvider {
    public static final FlowNames$ MODULE$ = new FlowNames$();

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
    public FlowNames get(ActorSystem system) {
        return (FlowNames)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public FlowNames get(ClassicActorSystemProvider system) {
        return (FlowNames)ExtensionId.get$((ExtensionId)this, system);
    }

    public FlowNames$ lookup() {
        return this;
    }

    @Override
    public FlowNames createExtension(ExtendedActorSystem system) {
        return new FlowNames();
    }

    private FlowNames$() {
    }
}

