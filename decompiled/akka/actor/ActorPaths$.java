/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPath;
import akka.actor.ActorPath$;

public final class ActorPaths$ {
    public static final ActorPaths$ MODULE$ = new ActorPaths$();

    public ActorPath fromString(String s2) {
        return ActorPath$.MODULE$.fromString(s2);
    }

    public final void validatePathElement(String element) {
        ActorPath$.MODULE$.validatePathElement(element);
    }

    public final void validatePathElement(String element, String fullPath) {
        ActorPath$.MODULE$.validatePathElement(element, fullPath);
    }

    public final boolean isValidPathElement(String s2) {
        return ActorPath$.MODULE$.isValidPathElement(s2);
    }

    private ActorPaths$() {
    }
}

