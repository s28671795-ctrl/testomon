/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.StreamSubscriptionTimeoutSettings;
import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$CancelTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$NoopTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$WarnTermination$;
import akka.util.Helpers$;
import com.typesafe.config.Config;
import java.util.concurrent.TimeUnit;
import scala.MatchError;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;

public final class StreamSubscriptionTimeoutSettings$ {
    public static final StreamSubscriptionTimeoutSettings$ MODULE$ = new StreamSubscriptionTimeoutSettings$();

    public StreamSubscriptionTimeoutSettings create(StreamSubscriptionTimeoutTerminationMode mode, FiniteDuration timeout2) {
        return new StreamSubscriptionTimeoutSettings(mode, timeout2);
    }

    public StreamSubscriptionTimeoutSettings apply(StreamSubscriptionTimeoutTerminationMode mode, FiniteDuration timeout2) {
        return new StreamSubscriptionTimeoutSettings(mode, timeout2);
    }

    public StreamSubscriptionTimeoutSettings create(Config config) {
        return this.apply(config);
    }

    public StreamSubscriptionTimeoutSettings apply(Config config) {
        StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode;
        Config c;
        block13: {
            String string;
            block12: {
                c = config.getConfig("subscription-timeout");
                string = Helpers$.MODULE$.toRootLowerCase(c.getString("mode"));
                switch (string == null ? 0 : string.hashCode()) {
                    case -1367724422: {
                        if (!"cancel".equals(string)) break block12;
                        streamSubscriptionTimeoutTerminationMode = StreamSubscriptionTimeoutTerminationMode$CancelTermination$.MODULE$;
                        break block13;
                    }
                    case 3521: {
                        if ("no".equals(string)) {
                            break;
                        }
                        break block12;
                    }
                    case 109935: {
                        if ("off".equals(string)) {
                            break;
                        }
                        break block12;
                    }
                    case 3387234: {
                        if ("noop".equals(string)) {
                            break;
                        }
                        break block12;
                    }
                    case 3641990: {
                        if (!"warn".equals(string)) break block12;
                        streamSubscriptionTimeoutTerminationMode = StreamSubscriptionTimeoutTerminationMode$WarnTermination$.MODULE$;
                        break block13;
                    }
                    case 97196323: {
                        if ("false".equals(string)) {
                            break;
                        }
                        break block12;
                    }
                    default: {
                        break block12;
                    }
                }
                streamSubscriptionTimeoutTerminationMode = StreamSubscriptionTimeoutTerminationMode$NoopTermination$.MODULE$;
                break block13;
            }
            throw new MatchError((Object)string);
        }
        return this.apply(streamSubscriptionTimeoutTerminationMode, new package.DurationLong(package$.MODULE$.DurationLong(c.getDuration("timeout", TimeUnit.MILLISECONDS))).millis());
    }

    private StreamSubscriptionTimeoutSettings$() {
    }
}

