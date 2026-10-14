/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.stream.StreamRefResolver;
import akka.stream.impl.streamref.StreamRefResolverImpl;

public final class StreamRefResolver$
implements ExtensionId<StreamRefResolver> {
    public static final StreamRefResolver$ MODULE$ = new StreamRefResolver$();

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
    public StreamRefResolver get(ActorSystem system) {
        return (StreamRefResolver)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public StreamRefResolver get(ClassicActorSystemProvider system) {
        return (StreamRefResolver)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public StreamRefResolver createExtension(ExtendedActorSystem system) {
        return new StreamRefResolverImpl(system);
    }

    private StreamRefResolver$() {
    }
}

