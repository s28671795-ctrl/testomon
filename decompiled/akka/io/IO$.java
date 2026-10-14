/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ExtensionId;
import akka.io.IO;

public final class IO$ {
    public static final IO$ MODULE$ = new IO$();

    public <T extends IO.Extension> ActorRef apply(ExtensionId<T> key, ActorSystem system) {
        return ((IO.Extension)key.apply(system)).manager();
    }

    private IO$() {
    }
}

