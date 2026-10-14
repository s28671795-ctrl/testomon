/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.internal;

import akka.annotation.InternalApi;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$parasitic$;

@InternalApi
public final class SameThreadExecutionContext$ {
    public static final SameThreadExecutionContext$ MODULE$ = new SameThreadExecutionContext$();

    public ExecutionContext apply() {
        return ExecutionContext$parasitic$.MODULE$;
    }

    private SameThreadExecutionContext$() {
    }
}

