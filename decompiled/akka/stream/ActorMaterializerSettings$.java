/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorSystem;
import akka.stream.ActorAttributes$;
import akka.stream.ActorMaterializerSettings;
import akka.stream.IOSettings$;
import akka.stream.StreamRefSettings$;
import akka.stream.StreamSubscriptionTimeoutSettings;
import akka.stream.StreamSubscriptionTimeoutSettings$;
import akka.stream.Supervision;
import akka.stream.Supervision$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.Function1;

public final class ActorMaterializerSettings$ {
    public static final ActorMaterializerSettings$ MODULE$ = new ActorMaterializerSettings$();

    public ActorMaterializerSettings apply(int initialInputBufferSize, int maxInputBufferSize, String dispatcher, Function1<Throwable, Supervision.Directive> supervisionDecider, StreamSubscriptionTimeoutSettings subscriptionTimeoutSettings, boolean debugLogging, int outputBurstLimit, boolean fuzzingMode, boolean autoFusing, int maxFixedBufferSize) {
        Config config = ConfigFactory.defaultReference();
        return new ActorMaterializerSettings(initialInputBufferSize, maxInputBufferSize, dispatcher, supervisionDecider, subscriptionTimeoutSettings, debugLogging, outputBurstLimit, fuzzingMode, autoFusing, maxFixedBufferSize, 1000, IOSettings$.MODULE$.apply(16384), StreamRefSettings$.MODULE$.apply(config.getConfig("akka.stream.materializer.stream-ref")), config.getString(ActorAttributes$.MODULE$.IODispatcher().dispatcher()));
    }

    public ActorMaterializerSettings apply(ActorSystem system) {
        return this.apply(system.settings().config().getConfig("akka.stream.materializer"));
    }

    public ActorMaterializerSettings apply(Config config) {
        return new ActorMaterializerSettings(config.getInt("initial-input-buffer-size"), config.getInt("max-input-buffer-size"), config.getString("dispatcher"), Supervision$.MODULE$.stoppingDecider(), StreamSubscriptionTimeoutSettings$.MODULE$.apply(config), config.getBoolean("debug-logging"), config.getInt("output-burst-limit"), config.getBoolean("debug.fuzzing-mode"), config.getBoolean("auto-fusing"), config.getInt("max-fixed-buffer-size"), config.getInt("sync-processing-limit"), IOSettings$.MODULE$.apply(config.getConfig("io")), StreamRefSettings$.MODULE$.apply(config.getConfig("stream-ref")), config.getString("blocking-io-dispatcher"));
    }

    public ActorMaterializerSettings create(int initialInputBufferSize, int maxInputBufferSize, String dispatcher, Function1<Throwable, Supervision.Directive> supervisionDecider, StreamSubscriptionTimeoutSettings subscriptionTimeoutSettings, boolean debugLogging, int outputBurstLimit, boolean fuzzingMode, boolean autoFusing, int maxFixedBufferSize) {
        Config config = ConfigFactory.defaultReference();
        return new ActorMaterializerSettings(initialInputBufferSize, maxInputBufferSize, dispatcher, supervisionDecider, subscriptionTimeoutSettings, debugLogging, outputBurstLimit, fuzzingMode, autoFusing, maxFixedBufferSize, 1000, IOSettings$.MODULE$.apply(16384), StreamRefSettings$.MODULE$.apply(config.getConfig("akka.stream.materializer.stream-ref")), config.getString(ActorAttributes$.MODULE$.IODispatcher().dispatcher()));
    }

    public ActorMaterializerSettings create(ActorSystem system) {
        return this.apply(system);
    }

    public ActorMaterializerSettings create(Config config) {
        return this.apply(config);
    }

    private ActorMaterializerSettings$() {
    }
}

