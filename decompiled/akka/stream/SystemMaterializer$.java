/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.stream.SystemMaterializer;

public final class SystemMaterializer$
implements ExtensionId<SystemMaterializer>,
ExtensionIdProvider {
    public static final SystemMaterializer$ MODULE$ = new SystemMaterializer$();

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
    public SystemMaterializer get(ActorSystem system) {
        return (SystemMaterializer)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public SystemMaterializer get(ClassicActorSystemProvider system) {
        return (SystemMaterializer)ExtensionId.get$((ExtensionId)this, system);
    }

    public SystemMaterializer$ lookup() {
        return this;
    }

    @Override
    public SystemMaterializer createExtension(ExtendedActorSystem system) {
        return new SystemMaterializer(system);
    }

    private SystemMaterializer$() {
    }
}

