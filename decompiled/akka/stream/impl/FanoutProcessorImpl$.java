/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Deploy$;
import akka.actor.Props;
import akka.actor.Props$;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.FanoutProcessorImpl;
import java.io.Serializable;
import scala.Function0;
import scala.reflect.ClassTag$;

@InternalApi
public final class FanoutProcessorImpl$ {
    public static final FanoutProcessorImpl$ MODULE$ = new FanoutProcessorImpl$();

    public Props props(Attributes attributes) {
        return Props$.MODULE$.apply((Function0<FanoutProcessorImpl> & Serializable)() -> new FanoutProcessorImpl(attributes), ClassTag$.MODULE$.apply(FanoutProcessorImpl.class)).withDeploy(Deploy$.MODULE$.local());
    }

    private FanoutProcessorImpl$() {
    }
}

