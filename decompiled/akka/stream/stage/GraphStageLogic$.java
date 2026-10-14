/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.Done;
import akka.Done$;
import akka.annotation.InternalApi;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;

public final class GraphStageLogic$ {
    public static final GraphStageLogic$ MODULE$ = new GraphStageLogic$();
    @InternalApi
    private static final Promise<Done> NoPromise = Promise$.MODULE$.successful(Done$.MODULE$);

    public Promise<Done> NoPromise() {
        return NoPromise;
    }

    private GraphStageLogic$() {
    }
}

