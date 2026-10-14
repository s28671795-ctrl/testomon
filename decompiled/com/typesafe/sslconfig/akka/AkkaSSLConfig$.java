/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.akka;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.actor.ExtensionIdProvider;
import com.typesafe.config.Config;
import com.typesafe.sslconfig.akka.AkkaSSLConfig;
import com.typesafe.sslconfig.ssl.SSLConfigFactory$;
import com.typesafe.sslconfig.ssl.SSLConfigSettings;

public final class AkkaSSLConfig$
implements ExtensionId<AkkaSSLConfig>,
ExtensionIdProvider {
    public static final AkkaSSLConfig$ MODULE$ = new AkkaSSLConfig$();

    static {
        ExtensionId.$init$(MODULE$);
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
    public AkkaSSLConfig get(ActorSystem system) {
        return (AkkaSSLConfig)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public AkkaSSLConfig get(ClassicActorSystemProvider system) {
        return (AkkaSSLConfig)ExtensionId.get$((ExtensionId)this, system);
    }

    @Override
    public AkkaSSLConfig apply(ActorSystem system) {
        return (AkkaSSLConfig)ExtensionId.apply$((ExtensionId)this, system);
    }

    public AkkaSSLConfig$ lookup() {
        return this;
    }

    @Override
    public AkkaSSLConfig createExtension(ExtendedActorSystem system) {
        return new AkkaSSLConfig(system, this.defaultSSLConfigSettings(system));
    }

    public SSLConfigSettings defaultSSLConfigSettings(ActorSystem system) {
        Config akkaOverrides = system.settings().config().getConfig("akka.ssl-config");
        Config defaults = system.settings().config().getConfig("ssl-config");
        return SSLConfigFactory$.MODULE$.parse(akkaOverrides.withFallback(defaults));
    }

    private AkkaSSLConfig$() {
    }
}

