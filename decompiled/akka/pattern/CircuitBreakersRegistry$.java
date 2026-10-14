/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.pattern.CircuitBreakersRegistry;

public final class CircuitBreakersRegistry$
implements ExtensionId<CircuitBreakersRegistry>,
ExtensionIdProvider {
    public static final CircuitBreakersRegistry$ MODULE$ = new CircuitBreakersRegistry$();

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
    public CircuitBreakersRegistry createExtension(ExtendedActorSystem system) {
        return new CircuitBreakersRegistry(system);
    }

    @Override
    public ExtensionId<? extends Extension> lookup() {
        return this;
    }

    @Override
    public CircuitBreakersRegistry get(ActorSystem system) {
        return (CircuitBreakersRegistry)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public CircuitBreakersRegistry get(ClassicActorSystemProvider system) {
        return (CircuitBreakersRegistry)ExtensionId.get$((ExtensionId)this, system);
    }

    private CircuitBreakersRegistry$() {
    }
}

