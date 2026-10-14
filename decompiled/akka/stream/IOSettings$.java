/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorSystem;
import akka.stream.IOSettings;
import com.typesafe.config.Config;
import scala.Predef$;
import scala.math.package$;

public final class IOSettings$ {
    public static final IOSettings$ MODULE$ = new IOSettings$();

    public IOSettings apply(ActorSystem system) {
        return this.apply(system.settings().config().getConfig("akka.stream.materializer.io"));
    }

    public IOSettings apply(Config config) {
        return new IOSettings((int)package$.MODULE$.min((long)Integer.MAX_VALUE, Predef$.MODULE$.Long2long(config.getBytes("tcp.write-buffer-size"))), config.getInt("tcp.coalesce-writes"));
    }

    public IOSettings apply(int tcpWriteBufferSize) {
        return new IOSettings(tcpWriteBufferSize);
    }

    public IOSettings create(Config config) {
        return this.apply(config);
    }

    public IOSettings create(ActorSystem system) {
        return this.apply(system);
    }

    public IOSettings create(int tcpWriteBufferSize) {
        return this.apply(tcpWriteBufferSize);
    }

    private IOSettings$() {
    }
}

