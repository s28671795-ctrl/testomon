/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorPath;
import akka.actor.Address$;
import akka.actor.RootActorPath;
import akka.actor.RootActorPath$;
import akka.annotation.InternalApi;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class IgnoreActorRef$
implements Serializable {
    public static final IgnoreActorRef$ MODULE$ = new IgnoreActorRef$();
    private static final String fakeSystemName = "local";
    private static final ActorPath path = new RootActorPath(Address$.MODULE$.apply("akka", MODULE$.fakeSystemName()), RootActorPath$.MODULE$.apply$default$2()).$div("ignore");
    private static final String pathString = MODULE$.path().toString();

    private String fakeSystemName() {
        return fakeSystemName;
    }

    public ActorPath path() {
        return path;
    }

    private String pathString() {
        return pathString;
    }

    public boolean isIgnoreRefPath(String otherPath) {
        String string = this.pathString();
        String string2 = otherPath;
        return !(string != null ? !string.equals(string2) : string2 != null);
    }

    public boolean isIgnoreRefPath(ActorPath otherPath) {
        ActorPath actorPath = this.path();
        ActorPath actorPath2 = otherPath;
        return !(actorPath != null ? !actorPath.equals(actorPath2) : actorPath2 != null);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(IgnoreActorRef$.class);
    }

    private IgnoreActorRef$() {
    }
}

