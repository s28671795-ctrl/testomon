/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import akka.io.Dns;
import akka.io.DnsExt;
import akka.io.dns.DnsProtocol;
import scala.Option;

public final class Dns$
implements ExtensionId<DnsExt>,
ExtensionIdProvider {
    public static final Dns$ MODULE$ = new Dns$();

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

    public Option<Dns.Resolved> cached(String name, ActorSystem system) {
        return ((DnsExt)this.apply(system)).cache().cached(name);
    }

    public Option<Dns.Resolved> resolve(String name, ActorSystem system, ActorRef sender) {
        return ((DnsExt)this.apply(system)).cache().resolve(name, system, sender);
    }

    public Option<DnsProtocol.Resolved> cached(DnsProtocol.Resolve name, ActorSystem system) {
        return ((DnsExt)this.apply(system)).cache().cached(name);
    }

    public Option<DnsProtocol.Resolved> resolve(DnsProtocol.Resolve name, ActorSystem system, ActorRef sender) {
        return ((DnsExt)this.apply(system)).cache().resolve(name, system, sender);
    }

    public Dns$ lookup() {
        return this;
    }

    @Override
    public DnsExt createExtension(ExtendedActorSystem system) {
        return new DnsExt(system);
    }

    @Override
    public DnsExt get(ActorSystem system) {
        return (DnsExt)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public DnsExt get(ClassicActorSystemProvider system) {
        return (DnsExt)ExtensionId.get$((ExtensionId)this, system);
    }

    private Dns$() {
    }
}

