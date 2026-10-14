/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;

public final class BaseSerializer$ {
    public static final BaseSerializer$ MODULE$ = new BaseSerializer$();

    public final String SerializationIdentifiers() {
        return "akka.actor.serialization-identifiers";
    }

    @InternalApi
    public int identifierFromConfig(Class<?> clazz, ExtendedActorSystem system) {
        return system.settings().config().getInt(new StringBuilder(39).append("akka.actor.serialization-identifiers").append(".\"").append(clazz.getName()).append("\"").toString());
    }

    @InternalApi
    public int identifierFromConfig(String bindingName, ExtendedActorSystem system) {
        return system.settings().config().getInt(new StringBuilder(39).append("akka.actor.serialization-identifiers").append(".\"").append(bindingName).append("\"").toString());
    }

    private BaseSerializer$() {
    }
}

