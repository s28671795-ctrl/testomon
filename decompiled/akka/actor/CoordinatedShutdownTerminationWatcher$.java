/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.CoordinatedShutdownTerminationWatcher;
import akka.actor.Props;
import akka.actor.Props$;
import akka.annotation.InternalApi;
import java.io.Serializable;
import scala.Function0;
import scala.reflect.ClassTag$;

@InternalApi
public final class CoordinatedShutdownTerminationWatcher$ {
    public static final CoordinatedShutdownTerminationWatcher$ MODULE$ = new CoordinatedShutdownTerminationWatcher$();

    public Props props() {
        return Props$.MODULE$.apply((Function0<CoordinatedShutdownTerminationWatcher> & Serializable)() -> new CoordinatedShutdownTerminationWatcher(), ClassTag$.MODULE$.apply(CoordinatedShutdownTerminationWatcher.class));
    }

    private CoordinatedShutdownTerminationWatcher$() {
    }
}

