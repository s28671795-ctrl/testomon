/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.actor.ActorSystem;
import akka.actor.Deploy$;
import akka.actor.Props;
import akka.actor.Props$;
import akka.annotation.InternalApi;
import akka.stream.TLSClosing;
import akka.stream.impl.io.TLSActor;
import java.io.Serializable;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.reflect.ClassTag$;
import scala.runtime.BoxedUnit;
import scala.util.Try;

@InternalApi
public final class TLSActor$ {
    public static final TLSActor$ MODULE$ = new TLSActor$();

    public Props props(int maxInputBufferSize, Function1<ActorSystem, SSLEngine> createSSLEngine, Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2, boolean tracing) {
        return Props$.MODULE$.apply((Function0<TLSActor> & Serializable)() -> new TLSActor(maxInputBufferSize, createSSLEngine, verifySession, closing2, tracing), ClassTag$.MODULE$.apply(TLSActor.class)).withDeploy(Deploy$.MODULE$.local());
    }

    public boolean props$default$5() {
        return false;
    }

    public final int TransportIn() {
        return 0;
    }

    public final int TransportOut() {
        return 0;
    }

    public final int UserOut() {
        return 1;
    }

    public final int UserIn() {
        return 1;
    }

    private TLSActor$() {
    }
}

