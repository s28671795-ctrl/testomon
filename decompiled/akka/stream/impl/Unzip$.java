/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Deploy$;
import akka.actor.Props;
import akka.actor.Props$;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.Unzip;
import java.io.Serializable;
import scala.Function0;
import scala.reflect.ClassTag$;

@InternalApi
public final class Unzip$ {
    public static final Unzip$ MODULE$ = new Unzip$();

    public Props props(Attributes attributes) {
        return Props$.MODULE$.apply((Function0<Unzip> & Serializable)() -> new Unzip(attributes), ClassTag$.MODULE$.apply(Unzip.class)).withDeploy(Deploy$.MODULE$.local());
    }

    private Unzip$() {
    }
}

